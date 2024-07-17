package modelo;

import java.util.ArrayList;

public class Inventario extends Entidade {
    private int capacidade;
    private ArrayList<Item> itens;
    private int durabilidade;

    public Inventario() {
        this.nome = "";
        this.capacidade = 0;
        this.itens = new ArrayList<Item>();
        this.durabilidade = 0;
    }

    public Inventario(String nome, int capacidade, int durabilidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.itens = new ArrayList<Item>();
        this.durabilidade = durabilidade;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    public ArrayList<Item> getItens() {
        return this.itens;
    }

    public int getDurabilidade() {
        return this.durabilidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }

    public void setDurabilidade(int durabilidade) {
        this.durabilidade = durabilidade;
    }

    public boolean remItem(Item item) {
        for (Item i : itens) {
            if (i.equals(item)) {
                itens.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addItem(Item item) {
        return itens.add(item);
    }

    public boolean remItem(int id) {
        for (Item i : itens) {
            if (i.getId() == id) {
                itens.remove(i);
                return true;
            }
        }
        return false;
    }
}
