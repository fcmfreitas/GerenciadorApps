import javax.swing.table.AbstractTableModel;

public class CatalogoClientesViewModel extends AbstractTableModel {
    private CatalogoClientes clientes;
    private final String[] nomeColunas = {
        "CPF",
        "Email",
        "Nome"
    };

    public CatalogoClientesViewModel(CatalogoClientes clientes) {
        this.clientes = clientes;
    }

    public String getColumnName(int col) {
        return nomeColunas[col];
    }

    public int getRowCount() { 
        return clientes.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomeColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Cliente c = clientes.getClientesNaLinha(row);
        switch (col) {
            case 0 : return (Object)(c.getCpf());
            case 1 : return (Object)(c.getEmail());
            case 2 : return (Object)(c.getNome());     
            default: return (Object)"none";
        }
    }

    public boolean isCellEditable(int row, int col) { 
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        // Não será acionado porque isCellEditable retorna false para todas as celulas
        // Se possível é necessário colocar o dado no catálogo de prdutos
        // Indicar a alteração acionando: fireTableCellUpdated(row, col);
    }    
}
