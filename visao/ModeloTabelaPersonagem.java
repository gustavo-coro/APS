package visao;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controle.Controle;
import controle.PersonagemBehavior;
import controle.UsuarioBehavior;
import modelo.Entidade;
import modelo.Personagem;
import modelo.Usuario;

public class ModeloTabelaPersonagem extends AbstractTableModel{
    //pequeno vetor usado para guardar o nome de cada coluna
    private String[] columnName = {"NOME", "FORÇA", "NÍVEL"};
    //ArrayList responsável por guardar as informações da tabela
    private ArrayList<Personagem> rowData;
    private Controle controle;

    public ModeloTabelaPersonagem() throws IOException {
        this.controle = new Controle(PersonagemBehavior.getInstancia());
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Personagem> personagens = new ArrayList<>();
        for (Entidade e : entidades)
            personagens.add((Personagem) e);
        this.rowData = personagens;
    }

    public void setRowData() {
        ArrayList<Entidade> entidades = controle.retorna_todos();
        ArrayList<Personagem> personagens = new ArrayList<>();
        for (Entidade e : entidades)
            personagens.add((Personagem) e);
        this.rowData = personagens;
    }

    public void setRowData(Usuario usuarioLogin) {
        if (usuarioLogin.getPersonagens() == null) {
            this.rowData = new ArrayList<>();
        } else {
            this.rowData = usuarioLogin.getPersonagens();
        }
        fireTableDataChanged();
    }

    public void setControle() throws IOException {
        this.controle = new Controle(PersonagemBehavior.getInstancia());
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
        Personagem personagem = (Personagem) rowData.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return personagem.getNome();
            }
            case 1: {
                return personagem.getForca();
            }
            case 2: {
                return personagem.getNivel();
            }
            default: {
                return null;
            }
        }
    }

    public Personagem getPersonagemAt(int rowIndex) {
        Personagem personagem = (Personagem) rowData.get(rowIndex);
        return personagem;
    }

    public int getIdAt(int rowIndex) {
        Personagem personagem = (Personagem) rowData.get(rowIndex);
        return personagem.getId();
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

    // remove o personagem da lista e do bd
    public void removePersonagem(int i, int idUsuario) {
        if (rowData.size() >= 0 && !rowData.isEmpty()) {
            boolean remove;
            try {
                int idPersonagem = rowData.get(i).getId();
                this.controle.setEntidadeBehavior(UsuarioBehavior.getInstancia());
                controle.getEntidadeBehavior().remove_personagem(idUsuario, idPersonagem);
                this.controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
                remove = controle.remover(idPersonagem);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                remove = false;
            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(null, "Erro na implementação!", "ERRO",
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
