import javax.swing.table.AbstractTableModel;

public class CatalogoAplicativosViewModel extends AbstractTableModel {
    private CatalogoAplicativos aplicativos;
    private CatalogoAssinaturas assinaturas;
    private final String[] nomesDasColunas = {
            "Codigo",
            "Nome",
            "Licença",
            "Sist. Operacional", "Faturamento total", "Faturamento IOS", "Faturamento Android"
    };

    public CatalogoAplicativosViewModel(CatalogoAplicativos aplicativos, CatalogoAssinaturas assinaturas) {
        this.aplicativos = aplicativos;
        this.assinaturas = assinaturas;
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

    private double calcularFaturamentoAndroid(int codigoApp, double price, Aplicativo.SO so) {
        double faturamento = 0;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                if (assAux.getFim().equals("00/00")) {
                    if (so == Aplicativo.SO.Android)
                        faturamento = faturamento + price;
                }
            }
        }
        return faturamento;
    }

    private double calculaFaturamentoIOS(int codigoApp, double price, Aplicativo.SO so) {
        double faturamento = 0;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                if (assAux.getFim().equals("00/00")) {
                    if (so == Aplicativo.SO.IOS)
                        faturamento = faturamento + price;
                }
            }
        }
        return faturamento;
    }

    private double calculaFaturamentoTotal(int codigoApp, double price) {
        double faturamento = 0;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                if (assAux.getFim().equals("00/00")) {
                    faturamento = faturamento + price;
                }
            }
        }
        return faturamento;
    }

    public Object getValueAt(int row, int col) {
        Aplicativo app = aplicativos.getProdutoNaLinha(row);
        double faturamentoTotal = calculaFaturamentoTotal(app.getCodigo(), app.getPreco());
        double faturamentoIOS = calculaFaturamentoIOS(app.getCodigo(), app.getPreco(), app.getSo());
        double faturamentoAndroid = calcularFaturamentoAndroid(app.getCodigo(), app.getPreco(), app.getSo());

        switch (col) {
            case 0:
                return (Object) (app.getCodigo());
            case 1:
                return (Object) (app.getNome());
            case 2:
                return (Object) (app.getPreco());
            case 3:
                return (Object) (app.getSo());
            case 4:
                return faturamentoTotal;
            case 5:
                return faturamentoIOS;
            case 6:
                return faturamentoAndroid;

            default:
                return (Object) "none";
        }
    }
}
