package front;

import model.ServicoAdicional;
import service.ClienteService;
import service.PacoteService;
import service.ServicoAdicionalService;
import db.ClienteDAO;
import db.PacoteDAO;
import db.ServicoAdicionalDAO;
import util.ConexaoBD;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Verificando a conexão com o banco de dados
        if (!ConexaoBD.testarConexao()) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados. O programa será encerrado.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Instanciando os DAOs e serviços
        ServicoAdicionalDAO servicoDAO = new ServicoAdicionalDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();

        ServicoAdicionalService servicoAdicionalService = new ServicoAdicionalService(servicoDAO, clienteDAO);
        ClienteService clienteService = new ClienteService(clienteDAO);
        PacoteService pacoteService = new PacoteService(pacoteDAO);

        // Menu de opções
        boolean continuar = true;
        while (continuar) {
            String[] opcoes = {"Cadastrar Cliente", "Listar Clientes", "Escolher Pacote", "Escolher Serviço Adicional", "Excluir Cliente", "Listar Pacotes de Cliente", "Sair"};
            int escolha = JOptionPane.showOptionDialog(null, "Bem-vindo à Agência de Viagens!", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0:
                    clienteService.cadastrarCliente();
                    break;

                case 1:
                    clienteService.listarClientes();
                    break;

                case 2:
                    clienteService.associarClientePacote(pacoteService);
                    break;

                case 3:
                    // Remover a linha abaixo e substituir por
                    servicoAdicionalService.escolherEAssociarServicoAoCliente(); // Chama o método correto
                    break;


                case 4:
                    clienteService.excluirCliente();
                    break;

                case 5:
                    clienteService.listarPacotesDeCliente();
                    break;

                default:
                    continuar = false;
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Obrigado por usar a Agência de Viagens!", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
    }
}
