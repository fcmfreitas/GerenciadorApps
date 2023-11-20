import javax.swing.table.AbstractTableModel;
import java.util.Date;

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

    private boolean checarAssinaturaVigente(int mesFim, int anoFim, int mesAtual, int anoAtual) {
        if (anoFim < anoAtual) {
            return false;
        } else if (anoFim > anoAtual) {
            return true;
        } else if (anoFim == anoAtual) {
            if (mesFim >= mesAtual)
                return true;
            else
                return false;
        }
        return false;
    }

    private double calcularFaturamentoAndroid(int codigoApp, double price, Aplicativo.SO so) {
        double faturamento = 0;
        Date data = new Date();
        int mesAtual = data.getMonth() + 1;
        int anoAtual = (data.getYear() + 1900) % 100;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                String[] dataCompletaFim = assAux.getFim().split("/");
                int mesFim = Integer.parseInt(dataCompletaFim[0]);
                int anoFim = Integer.parseInt(dataCompletaFim[1]);
                if (assAux.getFim().equals("00/00") || checarAssinaturaVigente(mesFim, anoFim, mesAtual, anoAtual)) {
                    if (so == Aplicativo.SO.Android)
                        faturamento = faturamento + price;
                }
            }
        }
        return faturamento;
    }

    private double calculaFaturamentoIOS(int codigoApp, double price, Aplicativo.SO so) {
        double faturamento = 0;
        Date data = new Date();
        int mesAtual = data.getMonth() + 1;
        int anoAtual = (data.getYear() + 1900) % 100;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                String[] dataCompletaFim = assAux.getFim().split("/");
                int mesFim = Integer.parseInt(dataCompletaFim[0]);
                int anoFim = Integer.parseInt(dataCompletaFim[1]);
                if (assAux.getFim().equals("00/00") || checarAssinaturaVigente(mesFim, anoFim, mesAtual, anoAtual)) {
                    if (so == Aplicativo.SO.IOS)
                        faturamento = faturamento + price;
                }
            }
        }
        return faturamento;
    }

    private double calculaFaturamentoTotal(int codigoApp, double price) {
        double faturamento = 0;
        Date data = new Date();
        int mesAtual = data.getMonth() + 1;
        int anoAtual = (data.getYear() + 1900) % 100;
        for (Assinatura assAux : assinaturas.getLista()) {
            if (assAux.getCodigoApp() == codigoApp) {
                String[] dataCompletaFim = assAux.getFim().split("/");
                int mesFim = Integer.parseInt(dataCompletaFim[0]);
                int anoFim = Integer.parseInt(dataCompletaFim[1]);
                if (assAux.getFim().equals("00/00") || checarAssinaturaVigente(mesFim, anoFim, mesAtual, anoAtual)) {
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
