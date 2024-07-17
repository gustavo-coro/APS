package modelo;
public class Item extends Entidade {
    private int nivelItem;
    private int multForca;
    private int durabilidade;

    public Item(){
        this.nome = "";
        this.nivelItem = 0;
        this.multForca = 1;
        this.durabilidade = 0;
    }

    public Item(String nome, int nivel, int mult, int durabilidade){
        this.nome = nome;
        this.nivelItem = nivel;
        this.multForca = mult;
        this.durabilidade = durabilidade;
    }

    public int getNivel(){
        return this.nivelItem;
    }

    public int getMult(){
        return this.multForca;
    }
    public int getDurabilidade() {
        return this.durabilidade;
    }

    public void setNivel(int nivel){
        this.nivelItem = nivel;
    }

    public void setMult(int mult){
        this.multForca = mult;
    }

    public void setDurabilidade(int durabilidade) {
        this.durabilidade = durabilidade;
    }

    public Item clone() {
        return new Item(new String(this.nome), this.nivelItem, this.multForca, this.durabilidade);
    }
}
