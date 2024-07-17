package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Personagem;

public class PersonagemPersistencia {
    private static PersonagemPersistencia instancia = null;
    private File arquivo = null;
    private ArrayList<Personagem> listaObjetos = null;
    private ItemPersistencia refItemBd = ItemPersistencia.getInstancia();

    private PersonagemPersistencia() throws IOException {
        //Inicialização completa se o arquivo não tiver feito
        arquivo = new File("arquivoPersonagem.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            var escrevedor = new FileWriter(arquivo);
            escrevedor.append("0\n");
            escrevedor.close();
            listaObjetos = new ArrayList<Personagem>();
            return;
        }

        //Variáveis auxiliares
        var str = Files.readString(Path.of("arquivoPersonagem.txt"));
        var tempStr = new StringBuilder();
        var i = 0;
        Personagem tempPersonagem;

        //Leitura e alocação de memória da quantidade de personagens
        for (; str.charAt(i) != '\n'; i++)
            tempStr.append(str.charAt(i));
        listaObjetos = new ArrayList<Personagem>(Integer.parseInt(tempStr.toString()));
        i++;
        tempStr = new StringBuilder();
        
        //Laço de leitura de objetos
        for (; i < str.length(); i++) {
            //Check de início de leitura corretos
            if(str.charAt(i) != 'i')
                continue;
            i++;
            tempPersonagem = new Personagem();

            //Leitura, set e verificação de id
            for(; str.charAt(i) != ':'; i++)
                tempStr.append(str.charAt(i));
            tempPersonagem.setId(Integer.parseInt(tempStr.toString()));
            i += 2;
            tempStr = new StringBuilder();
            if (tempPersonagem.getId() > Entidade.getProxId())
                Entidade.setProxId(tempPersonagem.getId());

            //Leitura e set do nome
            for(; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempPersonagem.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da força
            for(; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempPersonagem.setForca(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do nível
            for(; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempPersonagem.setNivel(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Inicialização, leitura e set do id do inventário do personagem
            var tempInv = tempPersonagem.getInventario();
            for(; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempInv.setId(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do nome do inventário do personagem
            for(; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempInv.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da capacidade do inventário do personagem
            for(; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempInv.setCapacidade(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();
            
            //Leitura e set da durabilidade do inventário do personagem
            for(; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempInv.setDurabilidade(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set dos itens no inventário do personagem. Se o item não existir no banco de itens, ele é ignorado
            for(; i < str.length() && str.charAt(i) != '\n'; i++) {
                //Laço de leitura individual de um item
                for(; i < str.length() && str.charAt(i) != ','; i++) {
                    if(str.charAt(i) == '\n') {
                        i--;
                        break;
                    }
                    tempStr.append(str.charAt(i));
                }
                //procura no banco de itens, validação do item e adição no inventário
                var tempItem = refItemBd.getItem(Integer.parseInt(tempStr.toString()));
                tempStr = new StringBuilder();
                if(tempItem == null)
                    continue;
                tempInv.addItem(tempItem);
            }
            tempStr = new StringBuilder();

            //Adicionando o personagem no armazem
            listaObjetos.add(tempPersonagem);
        }
    }

    public static PersonagemPersistencia getInstancia() throws IOException {
        if (instancia == null)
            instancia = new PersonagemPersistencia();
        return instancia;
    }

    public void salvar() throws IOException {
        var escrevedor = new FileWriter(arquivo);
        escrevedor.append(Integer.toString(listaObjetos.size()) + '\n');
        for (Personagem u : listaObjetos) {
            var strTemp = new StringBuilder();
            var ui = u.getInventario();
            escrevedor.append(
                "i" + u.getId() + ": " + u.getNome() + '\n' +
                u.getNivel() + ' ' + u.getForca() + '\n' +
                ui.getId() + ' ' + ui.getNome() + ' ' + ui.getCapacidade() + ' ' + ui.getDurabilidade() + '\n'
            );
            for(var uit : u.getInventario().getItens())
                strTemp.append(Integer.toString(uit.getId()) + ',');
            if(strTemp.length() > 0)
                strTemp.deleteCharAt(strTemp.length() - 1);
            strTemp.append('\n');
            escrevedor.append(strTemp.toString());
        }
        escrevedor.close();
    }

    public boolean adicionar(Personagem i) {
        return listaObjetos.add(i);
    }

    public boolean remover(Personagem i) {
        return listaObjetos.remove(i);
    }

    public boolean remover(int id) {
        for (Personagem personagem : listaObjetos)
            if (personagem.getId() == id) 
                return listaObjetos.remove(personagem);
        return false;
    }

    public ArrayList<Personagem> getPersonagens() {
        return listaObjetos;
    }

    public boolean procurar(Personagem i) {
        return listaObjetos.contains(i);
    }

    public boolean procurar(int id) {
        for (Personagem personagem : listaObjetos)
            if (personagem.getId() == id) 
                return true;
        return false;
    }

    public Personagem getPersonagem(int id) {
        for (Personagem personagem : listaObjetos)
            if (personagem.getId() == id) 
                return personagem;
        return null;
    }

    public int getQuantidadePersonagem() {
        return listaObjetos.size();
    }

}