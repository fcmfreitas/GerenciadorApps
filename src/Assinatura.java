public class Assinatura {
    private String codigo, codigoApp, cpfCliente, inicio, fim;

    public Assinatura(String codigo, String codigoApp, String cpfCliente, String inicio, String fim) {
        this.codigo = codigo;
        this.codigoApp = codigoApp;
        this.cpfCliente = cpfCliente;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoApp() {
        return codigoApp;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFim() {
        return fim;
    }

    @Override
    public String toString() {
        return "Assinatura [codigo=" + codigo + ", codigoApp=" + codigoApp + ", cpfCliente=" + cpfCliente + ", inicio="
                + inicio + ", fim=" + fim + "]";
    }
}
