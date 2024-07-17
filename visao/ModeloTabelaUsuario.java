package visao;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controle.Controle;
import controle.UsuarioBehavior;
import modelo.Entidade;
import modelo.Usuario;

public class ModeloTabelaUsuario  extends AbstractTableModel{
    //pequeno vetor usado para guardar o nome de cada coluna
    private String[] columnName = {"NOME", "USUÁRIO", "MESTRE"};
    //ArrayList responsável por guardar as informações da tabela
    private ArrayList<Usuario> rowData;
    private Controle controle;

    public ModeloTabelaUsuario() throws IOException {
        this.controle = new Controle(UsuarioBehavior.getInstancia());
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (Entidade e : entidades)
            usuarios.add((Usuario) e);
        this.rowData = usuarios;
    }

    public void setRowData() {
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (Entidade e : entidades)
            usuarios.add((Usuario) e);
        this.rowData = usuarios;
    }

    public void setControle() throws IOException{
        this.controle = new Controle(UsuarioBehavior.getInstancia());
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
        Usuario usuario = (Usuario) rowData.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return usuario.getNome();
            }
            case 1: {
                return usuario.getLogin();
            }
            case 2: {
                if (usuario.getIsAdmin()) {
                    return "SIM";
                } else {
                    return "NÃO";
                }
            }
            default: {
                return null;
            }
        }
    }

    public Usuario getUsuarioAt(int rowIndex) {
        Usuario usuario = (Usuario) rowData.get(rowIndex);
        return usuario;
    }

    public int getIdAt(int rowIndex) {
        Usuario usuario = (Usuario) rowData.get(rowIndex);
        return usuario.getId();
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

    // remove o usuario da lista e do bd, retorna o id do usuario removido ou -1 em caso de erro
    public int removeUsuario(int i) {
        if (rowData.size() >= 0 && !rowData.isEmpty()) {
            boolean remove;
            int idRemovido = rowData.get(i).getId();
            try {
                remove = controle.remover(rowData.get(i).getId());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            }
            if (remove) {
                fireTableDataChanged();
                return idRemovido;
            }
            JOptionPane.showMessageDialog(null, "Não foi possível remover o usuário!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    public void changedTable() {
        fireTableDataChanged();
    }
    
}
