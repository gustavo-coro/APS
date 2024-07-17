package visao;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controle.Controle;
import controle.MonstroBehavior;
import modelo.Entidade;
import modelo.Monstro;

public class ModeloTabelaMonstro  extends AbstractTableModel{
    //pequeno vetor usado para guardar o nome de cada coluna
    private String[] columnName = {"NOME", "NÍVEL", "FORÇA", "VIDA"};
    //ArrayList responsável por guardar as informações da tabela
    private ArrayList<Monstro> rowData;
    private Controle controle;

    public ModeloTabelaMonstro() throws IOException {
        this.controle = new Controle(MonstroBehavior.getInstancia());
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Monstro> monstros = new ArrayList<>();
        for (Entidade e : entidades)
            monstros.add((Monstro) e);
        this.rowData = monstros;
    }

    public void setRowData() {
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Monstro> monstros = new ArrayList<>();
        for (Entidade e : entidades)
            monstros.add((Monstro) e);
        this.rowData = monstros;
    }

    public void setMonstroControle() throws IOException {
        this.controle = new Controle(MonstroBehavior.getInstancia());
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
        Monstro monstro = (Monstro) rowData.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return monstro.getNome();
            }
            case 1: {
                return monstro.getNivel();
            }
            case 2: {
                return monstro.getForca();
            }
            case 3: {
                return monstro.getVida();
            }
            default: {
                return null;
            }
        }
    }

    public Monstro getMonstroAt(int rowIndex) {
        Monstro monstro = (Monstro) rowData.get(rowIndex);
        return monstro;
    }

    public int getIdAt(int rowIndex) {
        Monstro monstro = (Monstro) rowData.get(rowIndex);
        return monstro.getId();
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

    // remove o Monstro da lista e do bd
    public void removeMonstro(int i) {
        if (rowData.size() >= 0 && !rowData.isEmpty()) {
            boolean remove;
            try {
                remove = controle.remover(rowData.get(i).getId());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro no arquivo de remoção!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            }
            if (remove) {
                fireTableDataChanged();
                return;
            }
            JOptionPane.showMessageDialog(null, "Não foi possível remover o monstro!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changedTable() {
        fireTableDataChanged();
    }
    
}
