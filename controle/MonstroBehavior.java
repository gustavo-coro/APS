package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;
import modelo.Monstro;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;
import persistencia.MonstroPersistencia;

public class MonstroBehavior extends EntidadeBehavior{

    private static MonstroBehavior instancia = null;
    private MonstroPersistencia monstroPersistencia;

    private MonstroBehavior() throws IOException {
        this.monstroPersistencia = MonstroPersistencia.getInstancia();
    }

    public static EntidadeBehavior getInstancia() throws IOException {
        if (instancia == null)
            instancia = new MonstroBehavior();
        return instancia;
    }

    @Override
    public boolean adicionar(Entidade entidade) throws IOException {
        // adiciona Monstro ao bd
        Monstro monstro = (Monstro) entidade;
        if (monstroPersistencia.procurar(monstro.getId())) {
            return false;
        }
        boolean result =  monstroPersistencia.adicionar(monstro);
        monstroPersistencia.salvar();
        return result;
    }

    @Override
    public Entidade retorna(int id) {
        // retorna o Monstro com o id expecificado
        Monstro monstro = monstroPersistencia.getMonstro(id);
        return monstro;
    }

    @Override
    public boolean remover(int id) throws IOException {
        // remove o Monstro do bd
        boolean result = monstroPersistencia.remover(id);
        monstroPersistencia.salvar();
        return result;
    }

    @Override
    public ArrayList<Entidade> retorna_todos() {
        // retorna uma lista com todos os Monstros cadastrados
        ArrayList<Monstro> monstros = monstroPersistencia.getMonstros();
        ArrayList<Entidade> entidades = new ArrayList<>();
        for (Monstro m : monstros)
            entidades.add(m);
        return entidades;
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

    @Override
    public Entidade get_usuario(String login) throws UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'get_usuario'");
    }

    @Override
    public Usuario login_usuario(String login, String senha) throws UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'login_usuario'");
    }

    @Override
    public void add_personagem(int id, Personagem personagem) throws IOException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'add_personagem'");
    }

    @Override
    public void remove_personagem(int id, int idPersonagem) throws IOException, UnsupportedOperationException {
        
        throw new UnsupportedOperationException("Unimplemented method 'remove_personagem'");
    }
    
}
