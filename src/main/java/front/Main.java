package front;

import db.ClienteDAO;
import db.PacoteDAO;
import model.Cliente;
import model.PacoteViagem;
import util.ConexaoBD;

import javax.swing.*;
import java.util.List;

public class  Main {

    public static void main(String[] args) {
        // Testar conexão com o banco
        if (!ConexaoBD.testarConexao()) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados. O programa será encerrado.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();

        //*Criando pacotes fixos ja estao no banco nao precisa criar mais.

        // pacoteDAO.salvar(new PacoteViagem("Aventura Amazônica", "Amazonas", 7, "Aventura", 2500.00f, "Passeios em trilhas e visita a comunidades indígenas."));
        //  pacoteDAO.salvar(new PacoteViagem("Romance em Gramado", "Gramado - RS", 5, "Romântico", 3200.00f, "Pacote especial para casais com fondue e city tour."));
        // pacoteDAO.salvar(new PacoteViagem("Cultura em Ouro Preto", "Ouro Preto - MG", 3, "Cultural", 1500.00f, "Visitas guiadas aos museus e igrejas históricas."));
        // pacoteDAO.salvar(new PacoteViagem("Sol e Praia em Fortaleza", "Fortaleza - CE", 6, "Praia", 2800.00f, "Pacote com hotel à beira-mar e passeios de buggy."));
        //pacoteDAO.salvar(new PacoteViagem("Natureza em Bonito", "Bonito - MS", 5, "Ecoturismo", 2900.00f, "Flutuação nos rios cristalinos e visita às grutas."));

        // Menu principal
        boolean continuar = true;
        while (continuar) {
            String[] opcoes = {"Cadastrar Cliente", "Listar Clientes", "Escolher Pacote", "Excluir Cliente", "Listar Pacotes de Cliente", "Sair"};
            int escolha = JOptionPane.showOptionDialog(null, "Bem-vindo à Agência de Viagens!", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0:
                    // Cadastrar Cliente
                    cadastrarCliente(clienteDAO);
                    break;

                case 1:
                    // Listar Clientes
                    listarClientes(clienteDAO);
                    break;

                case 2:
                    // Associar Cliente a Pacote
                    associarClientePacote(clienteDAO, pacoteDAO);
                    break;

                case 3:
                    // Excluir Cliente
                    excluirCliente(clienteDAO);
                    break;

                case 4:
                    // Listar Pacotes de Cliente
                    listarPacotesDeCliente(clienteDAO);
                    break;

                default:
                    continuar = false;
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Obrigado por usar a Agência de Viagens!", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void cadastrarCliente(ClienteDAO clienteDAO) {
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
        int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite a idade do cliente:"));
        String telefone = JOptionPane.showInputDialog("Digite o telefone do cliente:");
        String endereco = JOptionPane.showInputDialog("Digite o endereço do cliente:");
        String tipoCliente = JOptionPane.showInputDialog("Digite o tipo de cliente (nacional/estrangeiro):");
        String passaporte = tipoCliente.equals("estrangeiro") ? JOptionPane.showInputDialog("Digite o passaporte do cliente:") : null;

        Cliente cliente = new Cliente(nome, cpf, passaporte, idade, telefone, endereco, tipoCliente);
        clienteDAO.inserirCliente(cliente);
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void listarClientes(ClienteDAO clienteDAO) {
        List<Cliente> clientes = clienteDAO.listarClientes();
        StringBuilder clientesList = new StringBuilder("Lista de Clientes:\n");
        for (Cliente c : clientes) {
            clientesList.append(c).append("\n");
        }
        JOptionPane.showMessageDialog(null, clientesList.toString(), "Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void associarClientePacote(ClienteDAO clienteDAO, PacoteDAO pacoteDAO) {
        // Listar pacotes disponíveis
        List<PacoteViagem> pacotesDisponiveis = pacoteDAO.listar();
        StringBuilder pacotesList = new StringBuilder("Pacotes Disponíveis:\n");
        for (int i = 0; i < pacotesDisponiveis.size(); i++) {
            PacoteViagem p = pacotesDisponiveis.get(i);
            pacotesList.append(i + 1).append(" - ").append(p.getNome()).append(" - ").append(p.getDestino()).append("\n");
        }

        String pacoteEscolhidoStr = JOptionPane.showInputDialog(null, pacotesList.toString() + "\nDigite o número do pacote que deseja associar:");
        int pacoteEscolhido = Integer.parseInt(pacoteEscolhidoStr) - 1;
        PacoteViagem pacoteSelecionado = pacotesDisponiveis.get(pacoteEscolhido);

        // Solicitar CPF ou passaporte do cliente
        String identificacaoCliente = JOptionPane.showInputDialog("Digite o CPF ou Passaporte do cliente para associar a um pacote:");
        clienteDAO.associarClientePacote(identificacaoCliente, pacoteSelecionado.getId());
        JOptionPane.showMessageDialog(null, "Cliente associado ao pacote com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void excluirCliente(ClienteDAO clienteDAO) {
        String cpfExcluir = JOptionPane.showInputDialog("Digite o CPF do cliente para excluir:");
        clienteDAO.deletarCliente(cpfExcluir);
        JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void listarPacotesDeCliente(ClienteDAO clienteDAO) {
        String identificacaoClientePacote = JOptionPane.showInputDialog("Digite o CPF ou Passaporte do cliente para listar seus pacotes:");

        // Listar pacotes do cliente baseado em CPF ou Passaporte
        List<PacoteViagem> pacotesCliente = clienteDAO.listarPacotesClientePorCpf(identificacaoClientePacote);

        if (pacotesCliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este cliente não possui pacotes associados.", "Pacotes de Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder pacotesClienteList = new StringBuilder("Pacotes de Viagem do Cliente:\n");
            for (PacoteViagem p : pacotesCliente) {
                pacotesClienteList.append(p).append("\n");
            }
            JOptionPane.showMessageDialog(null, pacotesClienteList.toString(), "Pacotes de Cliente", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
