package model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String passaporte;
    private int idade;
    private String telefone;
    private String endereco;
    private String tipoCliente;  // 'nacional' ou 'estrangeiro'

    public Cliente() {}

    public Cliente(String nome, String cpf, String passaporte, int idade, String telefone, String endereco, String tipoCliente) {
        this.nome = nome;
        this.cpf = cpf;
        this.passaporte = passaporte;
        this.idade = idade;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getPassaporte() { return passaporte; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getTipoCliente() { return tipoCliente; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setPassaporte(String passaporte) { this.passaporte = passaporte; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }

    public boolean isValido() {
        if ("nacional".equals(tipoCliente)) {
            return cpf != null && !cpf.isEmpty();
        } else if ("estrangeiro".equals(tipoCliente)) {
            return passaporte != null && !passaporte.isEmpty();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                (tipoCliente.equals("nacional") ? "CPF: " + cpf : "Passaporte: " + passaporte) + "\n" +
                "Idade: " + idade + "\nTelefone: " + telefone + "\nEndereço: " + endereco;
    }
}
