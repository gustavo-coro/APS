package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Monstro;

public class MonstroPersistencia {
    private static MonstroPersistencia instancia = null;
    private File arquivo = null;
    private ArrayList<Monstro> listaObjetos = null;

    private MonstroPersistencia() throws IOException {
        //Inicialização completa se o arquivo não tiver feito
        arquivo = new File("arquivoMonstro.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            var escrevedor = new FileWriter(arquivo);
            escrevedor.append("0\n");
            escrevedor.close();
            listaObjetos = new ArrayList<Monstro>();
            return;
        }

        //Variáveis auxiliares
        var str = Files.readString(Path.of("arquivoMonstro.txt"));
        var tempStr = new StringBuilder();
        var i = 0;
        Monstro tempMonstro;

        //Leitura e alocação de memória da quantidade de monstros
        for (; str.charAt(i) != '\n'; i++)
            tempStr.append(str.charAt(i));
        listaObjetos = new ArrayList<Monstro>(Integer.parseInt(tempStr.toString()));
        i++;
        tempStr = new StringBuilder();

        //Laço de leitura do arquivo
        for (; i < str.length(); i++) {
            //Check de leitura se iniciando a partir do início do objeto
            if (str.charAt(i) != 'i')
                continue;
            i++;
            tempMonstro = new Monstro();

            //Leitura e set do id, além de verificação do id
            for (; str.charAt(i) != ':'; i++)
                tempStr.append(str.charAt(i));
            tempMonstro.setId(Integer.parseInt(tempStr.toString()));
            if (tempMonstro.getId() > Entidade.getProxId())
                Entidade.setProxId(tempMonstro.getId());
            i += 2;
            tempStr = new StringBuilder();

            //Leitura e set do nome
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempMonstro.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do nível
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempMonstro.setNivel(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da força
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempMonstro.setForca(Integer.parseInt(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da vida
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempMonstro.setVida(Integer.parseInt(tempStr.toString()));
            tempStr = new StringBuilder();

            //Adição do objeto ao armazém
            listaObjetos.add(tempMonstro);
        }
    }

    public static MonstroPersistencia getInstancia() throws IOException {
        if (instancia == null)
            instancia = new MonstroPersistencia();
        return instancia;
    }

    public void salvar() throws IOException {
        var escrevedor = new FileWriter(arquivo);
        escrevedor.append(Integer.toString(listaObjetos.size()) + '\n');
        for (Monstro u : listaObjetos)
            escrevedor.append(
                "i" + u.getId() + ": " + u.getNome() + '\n' +
                u.getNivel() + ' ' + u.getForca() + ' ' + u.getVida() + '\n'
            );
        escrevedor.close();
    }

    public boolean adicionar(Monstro i) {
        return listaObjetos.add(i);
    }

    public boolean remover(Monstro i) {
        return listaObjetos.remove(i);
    }

    public boolean remover(int id) {
        for (Monstro monstro : listaObjetos)
            if (monstro.getId() == id) 
                return listaObjetos.remove(monstro);
        return false;
    }

    public ArrayList<Monstro> getMonstros() {
        return listaObjetos;
    }

    public boolean procurar(Monstro i) {
        return listaObjetos.contains(i);
    }

    public boolean procurar(int id) {
        for (Monstro monstro : listaObjetos)
            if (monstro.getId() == id) 
                return true;
        return false;
    }

    public Monstro getMonstro(int id) {
        for (Monstro monstro : listaObjetos)
            if (monstro.getId() == id) 
                return monstro;
        return null;
    }

    public int getQuantidadeMonstros() {
        return listaObjetos.size();
    }

}
