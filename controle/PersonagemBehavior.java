package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;
import persistencia.PersonagemPersistencia;

public class PersonagemBehavior extends EntidadeBehavior{
    private static PersonagemBehavior instancia = null;
    private PersonagemPersistencia personagemPersistencia;

    private PersonagemBehavior() throws IOException {
        this.personagemPersistencia = PersonagemPersistencia.getInstancia();
    }

    public static EntidadeBehavior getInstancia() throws IOException {
        if (instancia == null)
            instancia = new PersonagemBehavior();
        return instancia;
    }

    @Override
    public boolean adicionar(Entidade entidade) throws IOException {
        // adiciona personagem ao bd
        Personagem personagem = (Personagem) entidade;
        if (personagemPersistencia.procurar(personagem.getId())) {
            return false;
        }
        boolean result =  personagemPersistencia.adicionar(personagem);
        personagemPersistencia.salvar();
        return result;
    }

    @Override
    public Entidade retorna(int id) {
        // retorna o personagem com o id expecificado
        Personagem personagem = personagemPersistencia.getPersonagem(id);
        return personagem;
    }

    @Override
    public boolean remover(int id) throws IOException {
        // remove o personagem do bd
        boolean result = personagemPersistencia.remover(id);
        personagemPersistencia.salvar();
        return result;
    }

    @Override
    public ArrayList<Entidade> retorna_todos() {
        // retorna uma lista com todos os personagens cadastrados
        ArrayList<Personagem> personagens = personagemPersistencia.getPersonagens();
        ArrayList<Entidade> entidades = new ArrayList<>();
        for (Personagem p : personagens)
            entidades.add(p);
        return entidades;
    }

    @Override
    public void add_inventario (int id, Inventario inventario) throws IOException{
        // adiciona o personagem para sua lista
        Personagem personagem = (Personagem) retorna(id);
        personagem.setItens(inventario);
        personagemPersistencia.salvar();
    }

    @Override
    public void remove_inventario (int id) throws IOException{
        // remove inventario do personagem
        Personagem personagem = (Personagem) retorna(id);
        Inventario invVazio = new Inventario(null, 0, 0);
        personagem.setItens(invVazio);
        personagemPersistencia.salvar();
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
