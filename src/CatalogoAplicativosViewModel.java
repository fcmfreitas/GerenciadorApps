import javax.swing.table.AbstractTableModel;

public class CatalogoAplicativosViewModel extends AbstractTableModel {
    private CatalogoAplicativos aplicativos;
    private final String[] nomesDasColunas = {
        "Codigo",
        "Nome",
        "Licença",
        "Sist. Operacional"
    };

    public CatalogoAplicativosViewModel(CatalogoAplicativos aplicativos){
        this.aplicativos = aplicativos;
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return aplicativos.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Aplicativo app = aplicativos.getProdutoNaLinha(row);
        switch (col) {
            case 0 : return (Object)(app.getCodigo());
            case 1 : return (Object)(app.getNome());
            case 2 : return (Object)(app.getPreco());
            case 3 : return (Object)(app.getSo());        
            default: return (Object)"none";
        }
    }
}
