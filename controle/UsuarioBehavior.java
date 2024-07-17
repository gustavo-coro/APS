package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;
import persistencia.UsuarioPersistencia;

public class UsuarioBehavior extends EntidadeBehavior{
    private static UsuarioBehavior instancia = null;
    private UsuarioPersistencia usuarioPersistencia;

    private UsuarioBehavior() throws IOException {
        this.usuarioPersistencia = UsuarioPersistencia.getInstancia();
    }

    public static EntidadeBehavior getInstancia() throws IOException {
        if (instancia == null)
            instancia = new UsuarioBehavior();
        return instancia;
    }

    @Override
    public boolean adicionar(Entidade entidade) throws IOException {
        Usuario usuario = (Usuario) entidade;
        // adiciona usuario ao bd
        if (usuarioPersistencia.procurar(usuario.getLogin())) {
            return false;
        }
        boolean result =  usuarioPersistencia.adicionar(usuario);
        usuarioPersistencia.salvar();
        return result;
    }

    @Override
    public Entidade retorna(int id) {
        // retorna o usuario com o id expecificado
        Usuario usuario = usuarioPersistencia.getUsuario(id);
        return usuario;
    }

    @Override
    public Entidade get_usuario (String login) {
        // retorna o usuario com o login expecificado
        Usuario usuario = usuarioPersistencia.getUsuario(login);
        return usuario;
    }

    @Override
    public boolean remover(int id) throws IOException {
        // remove o usuario do bd
        boolean result = usuarioPersistencia.remover(id);
        usuarioPersistencia.salvar();
        return result;
    }

    @Override
    public ArrayList<Entidade> retorna_todos() {
        // retorna uma lista com todos os usuarios cadastrados
        ArrayList<Usuario> usuarios = usuarioPersistencia.getUsuarios();
        ArrayList<Entidade> entidades = new ArrayList<>();
        for (Usuario u : usuarios) 
            entidades.add(u);
        return entidades;
    }

    @Override
    public Usuario login_usuario (String login, String senha) {
        // verifica se o usuario pode fazer login e retorna o usuario
        Usuario usuario = (Usuario) get_usuario(login);
        if (usuario == null) {
            return null;
        }
        if (usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    @Override
    public void add_personagem (int id, Personagem personagem) throws IOException{
        // adiciona o personagem para sua lista
        Usuario usuario = (Usuario) retorna(id);
        usuario.getPersonagens().add(personagem);
        usuarioPersistencia.salvar();
    }

    @Override
    public void remove_personagem (int id, int idPersonagem) throws IOException{
        Usuario usuario = (Usuario) retorna(id);
        for (Personagem p : usuario.getPersonagens()) {
            if (p.getId() == idPersonagem) {
                usuario.getPersonagens().remove(p);
                break;
            }
        }
        usuarioPersistencia.salvar();
    }

    @Override
    public void add_item(int id, Item item) throws IOException, CapacidadeException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'add_item'");
    }

    @Override
    public boolean remove_item(int id, Item item) throws IOException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'remove_item'");
    }

    @Override
    public void add_inventario(int id, Inventario inventario) throws IOException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'add_inventario'");
    }

    @Override
    public void remove_inventario(int id) throws IOException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'remove_inventario'");
    }
    
}
