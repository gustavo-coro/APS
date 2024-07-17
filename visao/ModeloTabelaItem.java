package visao;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controle.Controle;
import controle.InventarioBehavior;
import controle.ItemBehavior;
import modelo.Entidade;
import modelo.Inventario;
import modelo.Item;

public class ModeloTabelaItem  extends AbstractTableModel{
    //pequeno vetor usado para guardar o nome de cada coluna
    private String[] columnName = {"NOME", "NÍVEL", "FORÇA", "DURABILIDADE"};
    //ArrayList responsável por guardar as informações da tabela
    private ArrayList<Item> rowData;
    private Controle controle;
    private Inventario inventario;

    public ModeloTabelaItem() throws IOException {
        this.controle = new Controle(ItemBehavior.getInstancia());
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Item> itens = new ArrayList<>();
        for (Entidade e : entidades)
            itens.add((Item) e);
        this.rowData = itens;
    }

    public ModeloTabelaItem(int id) throws IOException {
        this.controle = new Controle(InventarioBehavior.getInstancia());
        this.inventario = (Inventario) controle.retorna(id);
        this.controle.setEntidadeBehavior(ItemBehavior.getInstancia());
        if (inventario == null) {
            this.rowData = new ArrayList<>();
        } else {
            this.rowData = inventario.getItens();
        }
    }

    public void setRowData() {
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Item> itens = new ArrayList<>();
        for (Entidade e : entidades)
            itens.add((Item) e);
        this.rowData = itens;
    }

    public void setControle() throws IOException {
        this.controle = new Controle(ItemBehavior.getInstancia());
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
        Item item = (Item) rowData.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return item.getNome();
            }
            case 1: {
                return item.getNivel();
            }
            case 2: {
                return item.getMult();
            }
            case 3: {
                return item.getDurabilidade();
            }
            default: {
                return null;
            }
        }
    }

    public Item getItemAt(int rowIndex) {
        Item item = (Item) rowData.get(rowIndex);
        return item;
    }

    public int getIdAt(int rowIndex) {
        Item item = (Item) rowData.get(rowIndex);
        return item.getId();
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

    // remove o Item da lista e do bd
    public void removeItem(int i) {
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
            JOptionPane.showMessageDialog(null, "Não foi possível remover o item!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // remove o Item do inventario e do bd
    public void removeItemInventario(int i, int idInventario) {
        if (rowData.size() >= 0 && !rowData.isEmpty()) {
            boolean remove;
            try {
                this.controle.setEntidadeBehavior(InventarioBehavior.getInstancia());
                remove = controle.getEntidadeBehavior().remove_item(idInventario, rowData.get(i));
                this.controle.setEntidadeBehavior(ItemBehavior.getInstancia());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(null, "Erro na implementação da operação!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            }
            if (remove) {
                fireTableDataChanged();
                return;
            }
            JOptionPane.showMessageDialog(null, "Não foi possível remover o item!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changedTable() {
        fireTableDataChanged();
    }
    
}
