package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Personagem;
import modelo.Usuario;

public class UsuarioPersistencia {
    private static UsuarioPersistencia instancia = null;
    private File arquivo = null;
    private ArrayList<Usuario> listaObjetos = null;
    private PersonagemPersistencia refPerBd = PersonagemPersistencia.getInstancia();

    private UsuarioPersistencia() throws IOException {
        //Inicialização completa se o arquivo não tiver feito
        arquivo = new File("arquivoUsuario.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            var escrevedor = new FileWriter(arquivo);
            escrevedor.append("0\n");
            escrevedor.close();
            listaObjetos = new ArrayList<Usuario>();
            return;
        }

        //Variáveis auxiliares
        var str = Files.readString(Path.of("arquivoUsuario.txt"));
        var tempStr = new StringBuilder();
        var i = 0;
        Usuario tempUsuario;

        //Leitura e alocação de memória da quantidade de usuários
        for (; str.charAt(i) != '\n'; i++)
            tempStr.append(str.charAt(i));
        listaObjetos = new ArrayList<Usuario>(Integer.parseInt(tempStr.toString()));
        i++;
        tempStr = new StringBuilder("");

        //Laço de leitura de objetos
        for (; i < str.length(); i++) {
            if (str.charAt(i) != 'i')
                continue;
            i++;
            tempUsuario = new Usuario();

            //Leitura, set e verificação do id
            for (; str.charAt(i) != ':'; i++)
                tempStr.append(str.charAt(i));
            tempUsuario.setId(Integer.parseInt(tempStr.toString()));
            if (tempUsuario.getId() > Entidade.getProxId())
                Entidade.setProxId(tempUsuario.getId());
            i += 2;
            tempStr = new StringBuilder();

            //Leitura e set do nome
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempUsuario.setNome(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do login
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempUsuario.setLogin(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set da senha
            for (; str.charAt(i) != ' '; i++)
                tempStr.append(str.charAt(i));
            tempUsuario.setSenha(tempStr.toString());
            i++;
            tempStr = new StringBuilder();

            //Leitura e set do admin
            for (; str.charAt(i) != '\n'; i++)
                tempStr.append(str.charAt(i));
            tempUsuario.setAdmin(Boolean.parseBoolean(tempStr.toString()));
            i++;
            tempStr = new StringBuilder();

            var tempPerLista = new ArrayList<Personagem>();
            for(; i < str.length() && str.charAt(i) != '\n'; i++) {
                for(; i < str.length() && str.charAt(i) != ' '; i++) {
                    if(str.charAt(i) == '\n') {
                        i--;
                        break;
                    }
                    tempStr.append(str.charAt(i));
                }
                var tempPer = refPerBd.getPersonagem(Integer.parseInt(tempStr.toString()));
                tempStr = new StringBuilder();
                if(tempPer == null)
                    continue;
                tempPerLista.add(tempPer);
            }
            tempStr = new StringBuilder();
            tempUsuario.setPersonagens(tempPerLista);

            //Adicionando usuario no armazem
            listaObjetos.add(tempUsuario);
        }
    }

    public static UsuarioPersistencia getInstancia() throws IOException {
        if (instancia == null)
            instancia = new UsuarioPersistencia();
        return instancia;
    }

    public void salvar() throws IOException {
        StringBuilder tempStr;
        var escrevedor = new FileWriter(arquivo);
        escrevedor.append(Integer.toString(listaObjetos.size()) + '\n');
        for (var u : listaObjetos) {
            tempStr = new StringBuilder();
            for(var up : u.getPersonagens())
                tempStr.append(Integer.toString(up.getId()) + ' ');
            if(tempStr.length() > 0)
                tempStr.deleteCharAt(tempStr.length() - 1);
            escrevedor.append(
                "i" + u.getId() + ": " + u.getNome() + '\n' + 
                u.getLogin() + ' ' + u.getSenha() + ' ' + u.getIsAdmin() + '\n' +
                tempStr.toString() + '\n'
            );
        } 
        escrevedor.close();
    }

    public boolean adicionar(Usuario u) {
        return listaObjetos.add(u);
    }

    public boolean remover(Usuario u) {
        return listaObjetos.remove(u);
    }

    public boolean remover(String u) {
        for (Usuario user : listaObjetos)
            if (user.getLogin().equals(u)) 
                return listaObjetos.remove(user);
        return false;
    }

    public boolean remover(int u) {
        for (Usuario user : listaObjetos)
            if (user.getId() == u) 
                return listaObjetos.remove(user);
        return false;
    }

    public ArrayList<Usuario> getUsuarios() {
        return listaObjetos;
    }

    public boolean procurar(Usuario u) {
        return listaObjetos.contains(u);
    }

    public boolean procurar(String u) {
        for (Usuario user : listaObjetos)
            if (user.getLogin().equals(u)) 
                return true;
        return false;
    }

    public Usuario getUsuario(int u) {
        for (Usuario user : listaObjetos)
            if (user.getId() == u) 
                return user;
        return null;
    }

    public Usuario getUsuario(String u) {
        for (Usuario user : listaObjetos)
            if (user.getLogin().equals(u)) 
                return user;
        return null;
    }

    public int getQuantidadeUsuarios() {
        return listaObjetos.size();
    }

}
