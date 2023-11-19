import javax.swing.table.AbstractTableModel;

public class CatalogoAssinaturasViewModel extends AbstractTableModel{
    private CatalogoAssinaturas assinaturas;
    private final String[] nomesDasColunas = {
        "Codigo",
        "Codigo App",
        "CPF Cliente",
        "Inicio",
        "Fim"
    };

    public CatalogoAssinaturasViewModel(CatalogoAssinaturas assinaturas){
        this.assinaturas = assinaturas;
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return assinaturas.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Assinatura s = assinaturas.getAssinaturaNaLinha(row);
        switch (col) {
            case 0 : return (Object)(s.getCodigo());
            case 1 : return (Object)(s.getCodigoApp());
            case 2 : return (Object)(s.getCpfCliente());
            case 3 : return (Object)(s.getInicio()); 
            case 4 : return (Object) (s.getFim());       
            default: return (Object)"none";
        }
    }
}
