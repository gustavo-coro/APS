package modelo;

public class Monstro extends Entidade {
    private int forca;
    private int nivel;
    private int vida;

    public Monstro(){
        this.nome = "";
        this.nivel = 0;
        this.forca = 0;
        this.vida = 0;
    }

    public Monstro(String nome, int nivel, int forca, int vida){
        this.nome = nome;
        this.nivel = nivel;
        this.forca = forca;
        this.vida = vida;
    }

    public String getNome(){
        return this.nome;
    }

    public int getForca(){
        return this.forca;
    }

    public int getNivel(){
        return this.nivel;
    }
    
    public int getVida() {
        return this.vida;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setForca(int forca){
        this.forca = forca;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Monstro clone() {
        return new Monstro(new String(this.nome), this.nivel, this.forca, this.vida);
    }
}
