package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;
import persistencia.InventarioPersistencia;
import persistencia.PersonagemPersistencia;

public class InventarioBehavior extends EntidadeBehavior{
    private static InventarioBehavior instancia = null;
    private InventarioPersistencia inventarioPersistencia;
    private PersonagemPersistencia personagemPersistencia;

    private InventarioBehavior() throws IOException {
        this.inventarioPersistencia = InventarioPersistencia.getInstancia();
        this.personagemPersistencia = PersonagemPersistencia.getInstancia();
    }

    public static EntidadeBehavior getInstancia() throws IOException {
        if (instancia == null)
            instancia = new InventarioBehavior();
        return instancia;
    }

    @Override
    public boolean adicionar(Entidade entidade) throws IOException {
        // adiciona inventario ao bd
        Inventario inventario = (Inventario) entidade;
        if (inventarioPersistencia.procurar(inventario.getId())) {
            return false;
        }
        boolean result =  inventarioPersistencia.adicionar(inventario);
        inventarioPersistencia.salvar();
        return result;
    }

    @Override
    public Entidade retorna(int id) {
        // retorna o inventario com o id expecificado
        Inventario inventario = inventarioPersistencia.getInventario(id);
        return inventario;
    }

    @Override
    public boolean remover(int id) throws IOException {
        // remove o inventario do bd
        ArrayList<Personagem> personagens = personagemPersistencia.getPersonagens();
        for (Personagem p : personagens)
            p.remInventario(id);
        boolean result = inventarioPersistencia.remover(id);
        personagemPersistencia.salvar();
        inventarioPersistencia.salvar();
        return result;
    }

    @Override
    public ArrayList<Entidade> retorna_todos() {
        // retorna uma lista com todos os inventarios cadastrados
        ArrayList<Inventario> inventarios = inventarioPersistencia.getInventarios();
        ArrayList<Entidade> entidades = new ArrayList<>();
        for (Inventario i : inventarios) {
            entidades.add(i);
        }
        return entidades;
    }

    @Override
    public void add_item (int id, Item item) throws IOException, CapacidadeException{
        // adiciona o personagem para sua lista
        Inventario inventario = (Inventario) retorna(id);
        if (inventario.getCapacidade() == inventario.getItens().size()) {
            throw new CapacidadeException(1);
        }
        inventario.getItens().add(item);
        inventarioPersistencia.salvar();
    }

    @Override
    public boolean remove_item (int id, Item item) throws IOException{
        // remove item da lista
        Inventario inventario = (Inventario) retorna(id);
        boolean result = inventario.getItens().remove(item);
        inventarioPersistencia.salvar();
        return result;
    }

    @Override
    public void add_inventario(int id, Inventario inventario) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public void remove_inventario(int id) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public Entidade get_usuario(String login) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public Usuario login_usuario(String login, String senha) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public void add_personagem(int id, Personagem personagem) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public void remove_personagem(int id, int idPersonagem) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method");
    }
    
}
