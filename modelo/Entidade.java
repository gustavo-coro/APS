package modelo;

public abstract class Entidade {
    private static int proxId = 1;
    private int id;
    protected String nome;

    protected Entidade(){
        this.id = proxId;
        proxId++;
    }

    public int getId(){
        return this.id;
    }

    public static int getProxId() {
        return proxId;
    }

    public void setId(int id){
        this.id = id;
    }

    public static void setProxId(int id) {
        proxId = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String str) {
        this.nome = str;
    }
}