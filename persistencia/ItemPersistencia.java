package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Item;

public class ItemPersistencia {
    private static ItemPersistencia instancia = null;
    private File arquivo = null;
    private ArrayList<Item> listaObjetos = null;

    private ItemPersistencia() throws IOException {
        // inicialização completa se o arquivo não tiver feito
        arquivo = new File("arquivoItem.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            var escrevedor = new FileWriter(arquivo);
            escrevedor.append("0\n");
            escrevedor.close();
            listaObjetos = new ArrayList<Item>();
            return;
        }

        //Variáveis auxiliares na leitura do arquivo existente
        var str = Files.readString(Path.of("arquivoItem.txt"));
        var tempStr = new StringBuilder();
        var i = 0;
        Item tempItem;

        //Leitura da quantidade de itens e alocação do espaço
        for (; str.charAt(i) != '\n'; i++)
            tempStr.append(str.charAt(i));
        listaObjetos = new ArrayList<Item>(Integer.parseInt(tempStr.toString()));
        i++;
        tempStr = new StringBuilder();

        //Laço de leitura geral do arquivo depois da leitura da quantidade
        for (; i < str.length(); i++) {
            //Check para detectar e garantir que o laço de leitura se inicie no início de um objeto
            if (str.charAt(i) != 'i')
                continue;
            i++;
            tempItem = new Item();

            //Leitura e set do id seguida da verificação do maior id salvo
            for (; str.charAt(i) != ':'; i++)
                tempStr.append(str.charAt(i));
            tempItem.setId(Integer.parseInt(tempStr.toString()));
            if (tempItem.getId() > Entidade.getProxId()) {
                Entidade.setProxId(tempItem.getId());
            }
            i += 2;
            tempStr = new StringBuilder();

            //Leitura e set do nome
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempItem.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do nível
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempItem.setNivel(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do multiplicador
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempItem.setMult(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da durabilidade
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempItem.setDurabilidade(Integer.parseInt(tempStr.toString()));
            tempStr = new StringBuilder();

            //adição do item no armazem
            listaObjetos.add(tempItem);
        }
    }

    public static ItemPersistencia getInstancia() throws IOException {
        if (instancia == null)
            instancia = new ItemPersistencia();
        return instancia;
    }

    public void salvar() throws IOException {
        var escrevedor = new FileWriter(arquivo);
        escrevedor.append(Integer.toString(listaObjetos.size()) + '\n');
        for (Item u : listaObjetos)
            escrevedor.append(
                "i" + u.getId() + ": " + u.getNome() + '\n' + 
                u.getNivel() + ' ' + u.getMult() + ' ' + u.getDurabilidade() + '\n'
            );
        escrevedor.close();
    }

    public boolean adicionar(Item i) {
        return listaObjetos.add(i);
    }

    public boolean remover(Item i) {
        return listaObjetos.remove(i);
    }

    public boolean remover(int id) {
        for (Item item : listaObjetos)
            if (item.getId() == id) 
                return listaObjetos.remove(item);
        return false;
    }

    public ArrayList<Item> getItens() {
        return listaObjetos;
    }

    public boolean procurar(Item i) {
        return listaObjetos.contains(i);
    }

    public boolean procurar(int id) {
        for (Item item : listaObjetos)
            if (item.getId() == id) 
                return true;
        return false;
    }

    public Item getItem(int id) {
        for (Item item : listaObjetos)
            if (item.getId() == id) 
                return item;
        return null;
    }

    public int getQuantidadeItems() {
        return listaObjetos.size();
    }

}
