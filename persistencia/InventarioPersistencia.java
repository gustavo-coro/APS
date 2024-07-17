package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;

public class InventarioPersistencia {
    private static InventarioPersistencia instancia = null;
    private File arquivo = null;
    private ArrayList<Inventario> listaObjetos = null;
    private ItemPersistencia refItemBd = ItemPersistencia.getInstancia();

    private InventarioPersistencia() throws IOException {
        //Inicialização completa se o arquivo não tiver feito
        arquivo = new File("arquivoInventario.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            var escrevedor = new FileWriter(arquivo);
            escrevedor.append("0\n");
            escrevedor.close();
            listaObjetos = new ArrayList<Inventario>();
            return;
        }

        //Variáveis auxiliares para a leitura
        var str = Files.readString(Path.of("arquivoInventario.txt"));
        var tempStr = new StringBuilder();
        var i = 0;
        Inventario tempInventario;

        //Leitura e alocação de memória da quantidade de inventários
        for (; str.charAt(i) != '\n'; i++)
            tempStr.append(str.charAt(i));
        listaObjetos = new ArrayList<Inventario>(Integer.parseInt(tempStr.toString()));
        i++;
        tempStr = new StringBuilder();

        //Laço de leitura de objetos
        for (; i < str.length(); i++) {
            //Check de início de leitura correto
            if (str.charAt(i) != 'i')
                continue;
            i++;
            tempInventario = new Inventario();

            //Leitura e set do id e verificação
            for (; str.charAt(i) != ':'; i++)
                tempStr.append(str.charAt(i));
            tempInventario.setId(Integer.parseInt(tempStr.toString()));
            if (tempInventario.getId() > Entidade.getProxId())
                Entidade.setProxId(tempInventario.getId());
            i += 2;
            tempStr = new StringBuilder();

            //Leitura e set do nome
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempInventario.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da capacidade
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempInventario.setCapacidade(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da durabilidade
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempInventario.setDurabilidade(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set dos itens de acordo com o ItemPersistência. Se o item não existir, o inventário será salvo sem o item.
            for(; i < str.length() && str.charAt(i) != '\n'; i++) {
                //Laço de leitura individual de um item
                for(; i < str.length() && str.charAt(i) != ','; i++) {
                    if(str.charAt(i) == '\n') {
                        i--;
                        break;
                    }
                    tempStr.append(str.charAt(i));
                }
                //Procura no banco de itens, check de existência e adição no inventário
                var tempItem = refItemBd.getItem(Integer.parseInt(tempStr.toString()));
                tempStr = new StringBuilder();
                if(tempItem == null)
                    continue;
                tempInventario.addItem(tempItem);
            }
            tempStr = new StringBuilder();

            //Adicionando o inventário no armazém
            listaObjetos.add(tempInventario);
        }
    }

    public static InventarioPersistencia getInstancia() throws IOException {
        if (instancia == null)
            instancia = new InventarioPersistencia();
        return instancia;
    }

    public void salvar() throws IOException {
        var escrevedor = new FileWriter(arquivo);
        escrevedor.append(Integer.toString(listaObjetos.size()) + '\n');
        for (var tempInv : listaObjetos) {
            var tempStr = new StringBuilder();
            for(var i : tempInv.getItens())
                tempStr.append(Integer.toString(i.getId()) + ",");
            if(tempStr.length() > 0)
                tempStr.deleteCharAt(tempStr.length() - 1);
            //tempStr.deleteCharAt(tempStr.length() - 1);
            escrevedor.append(
                "i" + tempInv.getId() + ": " + tempInv.getNome() + '\n' +
                tempInv.getCapacidade() + ' ' + tempInv.getDurabilidade() + ' ' + tempStr + '\n'
            );
        }  
        escrevedor.close();
    }

    public boolean adicionar(Inventario i) {
        return listaObjetos.add(i);
    }

    public boolean remover(Inventario i) {
        return listaObjetos.remove(i);
    }

    public boolean remover(int id) {
        for (Inventario inv : listaObjetos)
            if (inv.getId() == id) 
                return listaObjetos.remove(inv);
        return false;
    }

    public ArrayList<Inventario> getInventarios() {
        return listaObjetos;
    }

    public boolean procurar(Inventario i) {
        return listaObjetos.contains(i);
    }

    public boolean procurar(int id) {
        for (Inventario inv : listaObjetos)
            if (inv.getId() == id) 
                return true;
        return false;
    }

    public Inventario getInventario(int id) {
        for (Inventario inv : listaObjetos)
            if (inv.getId() == id) 
                return inv;
        return null;
    }

    public int getQuantidadeInventarios() {
        return listaObjetos.size();
    }

}