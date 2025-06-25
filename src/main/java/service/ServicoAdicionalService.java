package service;

import db.ServicoAdicionalDAO;
import db.ClienteDAO; // Ainda necessário para algumas operações (se houver)
import model.ServicoAdicional;
import model.Cliente;

import java.util.List;

public class ServicoAdicionalService {

    private ServicoAdicionalDAO servicoAdicionalDAO; // Renomeado 'dao' para clareza
    private ClienteDAO clienteDAO; // Mantido, pois ainda há uma associação com ClienteDAO

    // ATENÇÃO: Seu construtor no Main está passando 3 DAOs, mas este só recebe 2.
    // Ajuste o construtor do ServicoAdicionalService no Main.java se não precisar do PacoteDAO aqui.
    // Ou adicione um PacoteDAO aqui se for usar.
    public ServicoAdicionalService(ServicoAdicionalDAO servicoAdicionalDAO, ClienteDAO clienteDAO) {
        this.servicoAdicionalDAO = servicoAdicionalDAO;
        this.clienteDAO = clienteDAO;
    }

    /**
     * MANTIDO: Retorna a lista de todos os serviços adicionais.
     * A exibição da lista será feita pela GUI.
     * @return Uma lista de objetos ServicoAdicional.
     */
    public List<ServicoAdicional> listarServicosAdicionais() {
        return servicoAdicionalDAO.listarTodos(); // Assumindo que seu ServicoAdicionalDAO tem um método 'listarTodos()'
    }

    /**
     * NOVO: Método para associar um serviço a um cliente, recebendo os objetos diretamente da GUI.
     * @param cliente O objeto Cliente a ser associado.
     * @param servico O objeto ServicoAdicional a ser associado.
     * @return true se a associação foi bem-sucedida, false caso contrário.
     */
    public boolean associarServicoAoClienteGUI(Cliente cliente, ServicoAdicional servico) {
        try {
            if (cliente == null || servico == null) {
                System.err.println("Erro: Cliente ou Serviço fornecido é nulo para associação.");
                return false;
            }
            clienteDAO.associarServicoAoCliente(cliente, servico); // Chama o método no ClienteDAO
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao associar serviço ao cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * NOVO: Método para buscar um serviço adicional por ID.
     * Essencial para a GUI obter o objeto ServicoAdicional para associação.
     * VOCÊ PRECISARÁ ADICIONAR ESTE MÉTODO NO SEU ServicoAdicionalDAO!
     * @param id O ID do serviço adicional.
     * @return O objeto ServicoAdicional encontrado, ou null se não for encontrado.
     */
    public ServicoAdicional buscarServicoPorId(int id) {
        // Você precisará implementar um método no ServicoAdicionalDAO que faça essa busca.
        // Exemplo de como poderia ser no ServicoAdicionalDAO:
        // public ServicoAdicional buscarPorId(int id) { ... }
        return servicoAdicionalDAO.buscarPorId(id);
    }
}