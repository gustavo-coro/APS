package modelo;

import java.util.ArrayList;

public class Usuario extends Entidade {
    private String login;
    private String senha;
    private boolean isAdmin;
    private ArrayList<Personagem> personagens;

    public Usuario() {
        this.nome = "";
        this.login = "";
        this.senha = "";
        this.isAdmin = false;
        this.personagens = null;
    }

    public Usuario(String nome, String login, String senha, boolean isAdmin, ArrayList<Personagem> personagens) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.personagens = personagens;
    }

    public ArrayList<Personagem> getPersonagens() {
        return this.personagens;
    }

    public String getLogin() {
        return this.login;
    }

    public String getSenha() {
        return this.senha;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setPersonagens(ArrayList<Personagem> personagens) {
        this.personagens = personagens;
    }

    public Usuario clone() {
        return new Usuario(new String(this.nome), this.login, this.senha, this.isAdmin, this.personagens);
    }
}
