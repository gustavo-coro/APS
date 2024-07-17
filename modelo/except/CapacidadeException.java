package modelo.except;

public class CapacidadeException extends Exception {
    public CapacidadeException(int diff) {
        super("A capacidade de itens excedeu em: " + diff);
    }
}
