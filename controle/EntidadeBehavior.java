package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;

public abstract class EntidadeBehavior {

    public abstract boolean adicionar(Entidade entidade) throws IOException;

    public abstract Entidade retorna(int id);

    public abstract boolean remover(int id) throws IOException;

    public abstract ArrayList<Entidade> retorna_todos();

    // metodos exclusivos de classes especificas

    public abstract void add_item (int id, Item item) throws IOException, CapacidadeException, UnsupportedOperationException;

    public abstract boolean remove_item (int id, Item item) throws IOException, UnsupportedOperationException;

    public abstract void add_inventario (int id, Inventario inventario) throws IOException, UnsupportedOperationException;

    public abstract void remove_inventario (int id) throws IOException, UnsupportedOperationException;

    public abstract Entidade get_usuario (String login) throws UnsupportedOperationException;

    public abstract Usuario login_usuario (String login, String senha) throws UnsupportedOperationException;

    public abstract void add_personagem (int id, Personagem personagem) throws IOException, UnsupportedOperationException;

    public abstract void remove_personagem (int id, int idPersonagem) throws IOException, UnsupportedOperationException;

}
