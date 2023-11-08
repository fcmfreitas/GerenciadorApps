public class Cliente {
    String cpf, email, nome;

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
    
    @Override
    public String toString() {
        return "Cliente [cpf=" + cpf + ", email=" + email + ", nome=" + nome + "]";
    }
}
