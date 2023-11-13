public class Cliente {
    String cpf, email, nome;

    public Cliente(String cpf, String email, String nome) {
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String toLineFile() {
        return cpf + "," + email + "," + nome;
    }

    public static Cliente fromLineFile(String line){
        String[] tokens = line.split(",");
        String cpf = (tokens[0]);
        String nome = tokens[1];
        String email = (tokens[2]);

        return new Cliente(cpf,nome,email);
    }
}
