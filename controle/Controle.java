package controle;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Entidade;

public class Controle {

    EntidadeBehavior entidadeBehavior;

    public Controle(EntidadeBehavior entidadeBehavior) throws IOException {
        this.entidadeBehavior = entidadeBehavior;
    }

    public EntidadeBehavior getEntidadeBehavior() {
        return this.entidadeBehavior;
    }

    public void setEntidadeBehavior(EntidadeBehavior entidadeBehavior) {
        this.entidadeBehavior = entidadeBehavior;
    }

    public boolean adicionar(Entidade entidade) throws IOException {
        return entidadeBehavior.adicionar(entidade);
    }

    public Entidade retorna(int id) {
        return entidadeBehavior.retorna(id);
    }

    public boolean remover(int id) throws IOException {
        return entidadeBehavior.remover(id);
    }

    public ArrayList<Entidade> retorna_todos() {
        return entidadeBehavior.retorna_todos();
    }

}