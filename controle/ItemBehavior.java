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
import persistencia.ItemPersistencia;
import persistencia.PersonagemPersistencia;

public class ItemBehavior extends EntidadeBehavior{
    private static ItemBehavior itemBehavior = null;
    private ItemPersistencia itemPersistencia;
    private InventarioPersistencia inventarioPersistencia;
    private PersonagemPersistencia personagemPersistencia;

    private ItemBehavior() throws IOException {
        this.itemPersistencia = ItemPersistencia.getInstancia();
        this.inventarioPersistencia = InventarioPersistencia.getInstancia();
        this.personagemPersistencia = PersonagemPersistencia.getInstancia();
    }

    public static EntidadeBehavior getInstancia() throws IOException {
        if (itemBehavior == null)
            itemBehavior = new ItemBehavior();
        return itemBehavior;
    }

    @Override
    public boolean adicionar(Entidade entidade) throws IOException {
        // adiciona Item ao bd
        Item item = (Item) entidade;
        if (itemPersistencia.procurar(item.getId())) {
            return false;
        }
        boolean result = itemPersistencia.adicionar(item);
        itemPersistencia.salvar();
        return result;
    }

    @Override
    public Entidade retorna(int id) {
        // retorna o Item com o id expecificado
        Item Item = itemPersistencia.getItem(id);
        return Item;
    }

    @Override
    public boolean remover(int id) throws IOException {
        // remove o Item do bd juntamente com todas as suas cópias dos inventários
        ArrayList<Inventario> inventarios = inventarioPersistencia.getInventarios();
        for (Inventario inv : inventarios)
            inv.remItem(id);
        ArrayList<Personagem> personagens = personagemPersistencia.getPersonagens();
        for (Personagem per : personagens)
            per.getInventario().remItem(id);
        boolean result = itemPersistencia.remover(id);
        inventarioPersistencia.salvar();
        personagemPersistencia.salvar();
        itemPersistencia.salvar();
        return result;
    }

    @Override
    public ArrayList<Entidade> retorna_todos() {
        // retorna uma lista com todos os itens cadastrados
        ArrayList<Item> itens = itemPersistencia.getItens();
        ArrayList<Entidade> entidades = new ArrayList<>();
        for (Item i : itens)
            entidades.add(i);
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
