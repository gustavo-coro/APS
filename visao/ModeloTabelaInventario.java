package visao;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controle.Controle;
import controle.InventarioBehavior;
import modelo.Entidade;
import modelo.Inventario;

public class ModeloTabelaInventario extends AbstractTableModel{
    //pequeno vetor usado para guardar o nome de cada coluna
    private String[] columnName = {"NOME", "CAPACIDADE", "DURABILIDADE"};
    //ArrayList responsável por guardar as informações da tabela
    private ArrayList<Inventario> rowData;
    private Controle controle;

    public ModeloTabelaInventario() throws IOException{
        this.controle = new Controle(InventarioBehavior.getInstancia());
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Inventario> inventarios = new ArrayList<>();
        for (Entidade e : entidades)
            inventarios.add((Inventario) e);
        this.rowData = inventarios;
    }

    public void setRowData() {
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Inventario> inventarios = new ArrayList<>();
        for (Entidade e : entidades)
            inventarios.add((Inventario) e);
        this.rowData = inventarios;
    }

    public void setControle() throws IOException {
        this.controle = new Controle(InventarioBehavior.getInstancia());
    } 

    @Override
    public int getRowCount() {
        return rowData.size(); //o numero de linhas da tabela corresponde ao número de objetos criados
    }

    @Override
    public int getColumnCount() {
        return columnName.length; //o numero de colunas da tabela corresponde ao número de colunas criadas acima
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Inventario inventario = (Inventario) rowData.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return inventario.getNome();
            }
            case 1: {
                return inventario.getCapacidade();
            }
            case 2: {
                return inventario.getDurabilidade();
            }
            default: {
                return null;
            }
        }
    }

    public Inventario getInventarioAt(int rowIndex) {
        Inventario inventario = (Inventario) rowData.get(rowIndex);
        return inventario;
    }

    public int getIdAt(int rowIndex) {
        Inventario inventario = (Inventario) rowData.get(rowIndex);
        return inventario.getId();
    }

    //define quais celulas são editáveis
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    public void removeInventario(int i) {
        if (rowData.size() >= 0 && !rowData.isEmpty()) {
            boolean remove;
            try {
                remove = controle.remover(rowData.get(i).getId());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            }
            if (remove) {
                fireTableDataChanged();
                return;
            }
            JOptionPane.showMessageDialog(null, "Não foi possível remover o usuário!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changedTable() {
        fireTableDataChanged();
    }
    
}