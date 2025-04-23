package model;

public class ServicoAdicional {
    private int id;
    private String nome;
    private String descricao;
    private float preco;

    public ServicoAdicional() {}

    public ServicoAdicional(String nome, String descricao, float preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    @Override
    public String toString() {
        return "Serviço: " + nome + "\nDescrição: " + descricao + "\nPreço: R$" + preco;
    }
}