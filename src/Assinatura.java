public class Assinatura {
    private String codigo, codigoApp, cpfCliente;
    private int inicioMes, inicioAno, encerramentoMes, encerramentoAno;

    public Assinatura(String codigo, String codigoApp, String cpfCliente, int inicioMes, int inicioAno, int encerramentoMes, int encerramentoAno) {
        this.codigo = codigo;
        this.codigoApp = codigoApp;
        this.cpfCliente = cpfCliente;
        this.inicioMes = inicioMes;
        this.inicioAno = inicioAno;
        this.encerramentoMes = encerramentoMes;
        this.encerramentoAno = encerramentoAno;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoAplicativo() {
        return codigoApp;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public int getInicioMes() {
        return inicioMes;
    }

    public int getInicioAno() {
        return inicioAno;
    }

    public int getEncerramentoMes() {
        return encerramentoMes;
    }

    public int getEncerramentoAno() {
        return encerramentoAno;
    }

    @Override
    public String toString() {
        return "Assinatura [codigo=" + codigo + ", codigoApp=" + codigoApp + ", cpfCliente=" + cpfCliente
                + ", inicioMes=" + inicioMes + ", inicioAno=" + inicioAno + ", encerramentoMes=" + encerramentoMes
                + ", encerramentoAno=" + encerramentoAno + "]";
    }
}
