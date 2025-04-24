package model;

import java.util.List;

public class PacotesEServicosCliente {
    private List<PacoteViagem> pacotes;
    private List<ServicoAdicional> servicos;

    public PacotesEServicosCliente(List<PacoteViagem> pacotes, List<ServicoAdicional> servicos) {
        this.pacotes = pacotes;
        this.servicos = servicos;
    }

    public List<PacoteViagem> getPacotes() {
        return pacotes;
    }

    public List<ServicoAdicional> getServicos() {
        return servicos;
    }
}
