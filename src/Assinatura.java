public class Assinatura {
    private int codigo, codigoApp;
    private String cpfCliente, inicio, fim;

    public Assinatura(int codigo, int codigoApp, String cpfCliente, String inicio, String fim) {
        this.codigo = codigo;
        this.codigoApp = codigoApp;
        this.cpfCliente = cpfCliente;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigoApp() {
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

    public String toLineFile(){
        return codigo+","+codigoApp+","+cpfCliente+","+inicio+","+fim;
    }

    public static Assinatura fromLineFile(String line){
        String[] tokens = line.split(",");
        int codigo = Integer.parseInt(tokens[0]);
        int codigoApp = Integer.parseInt(tokens[1]);
        String cpfCliente = tokens[2];
        String inicio = tokens[3];
        String fim = tokens[4];
        return new Assinatura(codigo,codigoApp,cpfCliente,inicio,fim);
    }
}
