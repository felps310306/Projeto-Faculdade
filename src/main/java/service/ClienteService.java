package service;

import db.ClienteDAO;
import model.Cliente;
import model.PacoteViagem;
import model.PacotesEServicosCliente;
import model.ServicoAdicional;

import javax.swing.*;
import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void cadastrarCliente() {
        String tipoCliente = JOptionPane.showInputDialog("Digite o tipo de cliente (nacional/estrangeiro):");

        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite a idade do cliente:"));
        String telefone = JOptionPane.showInputDialog("Digite o telefone do cliente:");
        String endereco = JOptionPane.showInputDialog("Digite o endereço do cliente:");

        String cpfOuPassaporte = "";
        if ("nacional".equalsIgnoreCase(tipoCliente)) {
            cpfOuPassaporte = JOptionPane.showInputDialog("Digite o CPF do cliente:");
        } else if ("estrangeiro".equalsIgnoreCase(tipoCliente)) {
            cpfOuPassaporte = JOptionPane.showInputDialog("Digite o Passaporte do cliente:");
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de cliente inválido. O cadastro será cancelado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, cpfOuPassaporte, tipoCliente.equals("estrangeiro") ? cpfOuPassaporte : null, idade, telefone, endereco, tipoCliente);

        clienteDAO.inserirCliente(cliente);
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        StringBuilder clientesList = new StringBuilder("Lista de Clientes:\n");
        for (Cliente c : clientes) {
            clientesList.append(c).append("\n");
        }
        JOptionPane.showMessageDialog(null, clientesList.toString(), "Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void excluirCliente() {
        String tipoIdentificacao = JOptionPane.showInputDialog("Digite o tipo de identificação para excluir (CPF/Passaporte):");

        if (!tipoIdentificacao.equalsIgnoreCase("CPF") && !tipoIdentificacao.equalsIgnoreCase("Passaporte")) {
            JOptionPane.showMessageDialog(null, "Tipo de identificação inválido. O cadastro será cancelado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String identificacaoExcluir = JOptionPane.showInputDialog("Digite o " + tipoIdentificacao + " do cliente para excluir:");

        // Excluir cliente com base no tipo de identificação
        boolean sucesso = clienteDAO.deletarClientePorIdentificacao(identificacaoExcluir, tipoIdentificacao);

        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado. Verifique a identificação e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void associarClientePacote(PacoteService pacoteService) {
        // Listar pacotes disponíveis
        List<PacoteViagem> pacotesDisponiveis = pacoteService.listarPacotes();
        StringBuilder pacotesList = new StringBuilder("Pacotes Disponíveis:\n");
        for (int i = 0; i < pacotesDisponiveis.size(); i++) {
            PacoteViagem p = pacotesDisponiveis.get(i);
            pacotesList.append(i + 1).append(" - ").append(p.getNome()).append(" - ").append(p.getDestino()).append("\n");
        }

        String pacoteEscolhidoStr = JOptionPane.showInputDialog(null, pacotesList.toString() + "\nDigite o número do pacote que deseja associar:");
        int pacoteEscolhido = Integer.parseInt(pacoteEscolhidoStr) - 1;
        PacoteViagem pacoteSelecionado = pacotesDisponiveis.get(pacoteEscolhido);

        String identificacaoCliente = JOptionPane.showInputDialog("Digite o CPF ou Passaporte do cliente para associar a um pacote:");
        clienteDAO.associarClientePacote(identificacaoCliente, pacoteSelecionado.getId());
        JOptionPane.showMessageDialog(null, "Cliente associado ao pacote com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listarPacotesDeCliente() {
        String identificacaoClientePacote = JOptionPane.showInputDialog("Digite o CPF ou Passaporte do cliente para listar seus pacotes:");

        // Listar pacotes e serviços do cliente baseado em CPF ou Passaporte
        PacotesEServicosCliente pacotesEServicos = clienteDAO.listarPacotesClientePorCpf(identificacaoClientePacote);

        if (pacotesEServicos.getPacotes().isEmpty() && pacotesEServicos.getServicos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este cliente não possui pacotes ou serviços associados.", "Pacotes e Serviços de Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder pacotesClienteList = new StringBuilder("Pacotes de Viagem do Cliente:\n");

            for (PacoteViagem p : pacotesEServicos.getPacotes()) {
                pacotesClienteList.append(p).append("\n");
            }

            if (!pacotesEServicos.getServicos().isEmpty()) {
                pacotesClienteList.append("\nServiços Adicionais do Cliente:\n");
                for (ServicoAdicional s : pacotesEServicos.getServicos()) {
                    pacotesClienteList.append(s).append("\n");
                }
            }

            JOptionPane.showMessageDialog(null, pacotesClienteList.toString(), "Pacotes e Serviços de Cliente", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
