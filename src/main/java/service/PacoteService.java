package service;

import db.PacoteDAO;
import model.PacoteViagem;
import java.util.List;

public class PacoteService {

    private PacoteDAO pacoteDAO;

    public PacoteService(PacoteDAO pacoteDAO) {
        this.pacoteDAO = pacoteDAO;
    }

    /**
     * MANTIDO: Retorna a lista de todos os pacotes de viagem.
     * A exibição da lista será feita pela GUI.
     * @return Uma lista de objetos PacoteViagem.
     */
    public List<PacoteViagem> listarPacotes() {
        return pacoteDAO.listar(); // Assumindo que seu PacoteDAO já tem um método 'listar()'
    }

    /**
     * NOVO: Método para buscar um pacote por ID.
     * Essencial para a GUI obter o objeto PacoteViagem para associação.
     * VOCÊ PRECISARÁ ADICIONAR ESTE MÉTODO NO SEU PacoteDAO!
     * @param id O ID do pacote.
     * @return O objeto PacoteViagem encontrado, ou null se não for encontrado.
     */
    public PacoteViagem buscarPacotePorId(int id) {
        // Você precisará implementar um método no PacoteDAO que faça essa busca.
        // Exemplo de como poderia ser no PacoteDAO:
        // public PacoteViagem buscarPorId(int id) { ... }
        return pacoteDAO.buscarPorId(id);
    }
}