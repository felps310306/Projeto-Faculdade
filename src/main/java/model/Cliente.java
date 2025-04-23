package model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private String telefone;
    private String endereco;

    public Cliente() {}

    public Cliente(String nome, String cpf, int idade, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\nIdade: " + idade + "\nTelefone: " + telefone + "\nEndereço: " + endereco;
    }
}
