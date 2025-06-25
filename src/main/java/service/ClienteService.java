package service;

import db.ClienteDAO;
import model.Cliente;
import model.PacoteViagem;
import model.PacotesEServicosCliente;
import model.ServicoAdicional;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;
    // O PacoteService não precisa ser um campo aqui para 'associarClientePacote'
    // Ele será passado como parâmetro quando necessário, ou a lógica de listagem de pacotes
    // pode ser feita na própria GUI ou em um novo método de PacoteService se for o caso.

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * NOVO: Método para cadastrar cliente recebendo o objeto Cliente diretamente da GUI.
     * @param cliente O objeto Cliente a ser inserido.
     * @return true se o cliente foi inserido com sucesso, false caso contrário.
     */
    public boolean cadastrarClienteGUI(Cliente cliente) {
        try {
            clienteDAO.inserirCliente(cliente);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MANTIDO: Retorna a lista de clientes.
     * A exibição da lista será feita pela GUI.
     * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    /**
     * NOVO: Método para excluir cliente recebendo a identificação e o tipo diretamente da GUI.
     * @param identificacao O CPF ou Passaporte do cliente.
     * @param tipoIdentificacao "CPF" ou "Passaporte".
     * @return true se o cliente foi excluído com sucesso, false caso contrário.
     */
    public boolean deletarClientePorIdentificacao(String identificacao, String tipoIdentificacao) {
        try {
            return clienteDAO.deletarClientePorIdentificacao(identificacao, tipoIdentificacao);
        } catch (Exception e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * NOVO: Método para associar cliente a pacote recebendo a identificação do cliente e o ID do pacote.
     * A listagem de pacotes e a escolha agora ficam a cargo da GUI.
     * @param identificacaoCliente CPF ou Passaporte do cliente.
     * @param pacoteId ID do pacote de viagem a ser associado.
     * @return true se a associação foi bem-sucedida, false caso contrário.
     */
    public boolean associarClientePacote(String identificacaoCliente, int pacoteId) {
        try {
            clienteDAO.associarClientePacote(identificacaoCliente, pacoteId);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao associar cliente ao pacote: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * NOVO: Método para listar pacotes e serviços de um cliente recebendo a identificação.
     * A exibição da lista agora será feita pela GUI.
     * @param identificacao CPF ou Passaporte do cliente.
     * @return Objeto PacotesEServicosCliente contendo as listas de pacotes e serviços.
     */
    public PacotesEServicosCliente listarPacotesDeClienteGUI(String identificacao) {
        return clienteDAO.listarPacotesClientePorCpf(identificacao);
    }

    /**
     * NOVO: Método para buscar um cliente por identificação (CPF ou Passaporte).
     * Essencial para a GUI obter o objeto Cliente para associar a serviços.
     * VOCÊ PRECISARÁ ADICIONAR ESTE MÉTODO NO SEU ClienteDAO!
     * @param identificacao CPF ou Passaporte do cliente.
     * @return O objeto Cliente encontrado, ou null se não for encontrado.
     */
    public Cliente buscarClientePorIdentificacao(String identificacao) {
        // Você precisará implementar um método no ClienteDAO que faça essa busca.
        // Exemplo de como poderia ser no ClienteDAO:
        // public Cliente buscarClientePorCpfOuPassaporte(String identificacao) { ... }
        return clienteDAO.buscarClientePorCpfOuPassaporte(identificacao);
    }
}