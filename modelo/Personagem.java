package modelo;
public class Personagem extends Entidade {
    private int forca;
    private int nivel;
    private Inventario inventario;

    public Personagem(){
        this.nome = "";
        this.forca = 0;
        this.nivel = 0;
        this.inventario = new Inventario();
    }

    public Personagem(String nome, int forca, int nivel){
        this.nome = nome;
        this.forca = forca;
        this.nivel = nivel;
        this.inventario = new Inventario();
    }

    public Personagem(String nome, int forca, int nivel, Inventario inventario){
        this.nome = nome;
        this.forca = forca;
        this.nivel = nivel;
        this.inventario = inventario;
    }

    public int getForca(){
        return this.forca;
    }

    public int getNivel(){
        return this.nivel;
    }

    public Inventario getInventario(){
        return this.inventario;
    }

    public void setForca(int forca){
        this.forca = forca;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public void setItens(Inventario inventario){
        this.inventario = inventario;
    }

    public boolean remInventario(int id) {
        if (inventario.getId() == id) {
            inventario = new Inventario();
            return true;
        }
        return false;
    }

}
