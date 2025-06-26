// front/AgenciaViagensGUI.java
package front;

import service.ClienteService;
import service.PacoteService;
import service.ServicoAdicionalService;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;
import model.PacotesEServicosCliente;
import util.Validacoes; // IMPORTANTE: Adicione esta linha para importar sua classe de validação

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgenciaViagensGUI extends JFrame {

    // Instâncias dos serviços que serão utilizadas pela interface
    private ClienteService clienteService;
    private PacoteService pacoteService;
    private ServicoAdicionalService servicoAdicionalService;

    // Componentes da interface
    private JPanel mainPanel;
    private JButton btnCadastrarCliente;
    private JButton btnListarClientes;
    private JButton btnAssociarPacote;
    private JButton btnAssociarServico;
    private JButton btnExcluirCliente;
    private JButton btnListarPacotesServicos;
    private JTextArea displayArea; // Para exibir informações de listagem, por exemplo
    private JScrollPane scrollPane; // Para a JTextArea, para permitir rolagem

    public AgenciaViagensGUI(ClienteService clienteService, PacoteService pacoteService, ServicoAdicionalService servicoAdicionalService) {
        this.clienteService = clienteService;
        this.pacoteService = pacoteService;
        this.servicoAdicionalService = servicoAdicionalService;

        // Configurações básicas da janela
        setTitle("Agência de Viagens - Menu Principal");
        setSize(800, 600); // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null); // Centraliza a janela na tela

        initComponents(); // Método para inicializar e adicionar os componentes
        addListeners();     // Método para adicionar os ouvintes de eventos aos botões
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 botões (removi o "Sair" do menu)
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaçamento interno

        // Inicializa os botões
        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnListarClientes = new JButton("Listar Clientes");
        btnAssociarPacote = new JButton("Associar Pacote a Cliente");
        btnAssociarServico = new JButton("Associar Serviço Adicional a Cliente");
        btnExcluirCliente = new JButton("Excluir Cliente");
        btnListarPacotesServicos = new JButton("Listar Pacotes e Serviços do Cliente");


        // Adiciona os botões ao painel principal
        mainPanel.add(btnCadastrarCliente);
        mainPanel.add(btnListarClientes);
        mainPanel.add(btnAssociarPacote);
        mainPanel.add(btnAssociarServico);
        mainPanel.add(btnExcluirCliente);
        mainPanel.add(btnListarPacotesServicos);

        // Área de exibição de texto
        displayArea = new JTextArea(15, 50); // 15 linhas, 50 colunas
        displayArea.setEditable(false); // Não permite edição pelo usuário
        scrollPane = new JScrollPane(displayArea); // Adiciona barra de rolagem

        // Adiciona o painel principal e a área de exibição à janela
        // Usamos um JPanel wrapper para ter um layout mais flexível
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.NORTH); // Botões na parte superior
        contentPanel.add(scrollPane, BorderLayout.CENTER); // Área de texto no centro

        add(contentPanel); // Adiciona o painel de conteúdo à janela
    }

    private void addListeners() {
        btnCadastrarCliente.addActionListener(e -> cadastrarClienteGUI());
        btnListarClientes.addActionListener(e -> listarClientesGUI());
        btnAssociarPacote.addActionListener(e -> associarPacoteGUI());
        btnAssociarServico.addActionListener(e -> associarServicoGUI());
        btnExcluirCliente.addActionListener(e -> excluirClienteGUI());
        btnListarPacotesServicos.addActionListener(e -> listarPacotesServicosGUI());
    }

    // --- Métodos para cada funcionalidade da GUI ---

    private void cadastrarClienteGUI() {
        // Coleta e valida o Nome
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        if (!Validacoes.validarCampoObrigatorio(nome)) {
            JOptionPane.showMessageDialog(this, "Nome do cliente é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta e valida o CPF
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do cliente:");
        if (!Validacoes.validarCampoObrigatorio(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF do cliente é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validacoes.validarCpf(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Deve conter 11 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta e valida a Idade
        String idadeStr = JOptionPane.showInputDialog(this, "Digite a idade do cliente:");
        int idade = 0;
        if (!Validacoes.validarCampoObrigatorio(idadeStr)) {
            JOptionPane.showMessageDialog(this, "Idade do cliente é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            idade = Integer.parseInt(idadeStr);
            if (!Validacoes.validarIdade(idade)) {
                JOptionPane.showMessageDialog(this, "Idade inválida. Digite um número entre 1 e 119.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade inválida. Digite um número inteiro.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta e valida o Telefone
        String telefone = JOptionPane.showInputDialog(this, "Digite o telefone do cliente (ex: XXXXXXXXXX):");
        if (!Validacoes.validarCampoObrigatorio(telefone)) {
            JOptionPane.showMessageDialog(this, "Telefone do cliente é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validacoes.validarTelefone(telefone)) {
            JOptionPane.showMessageDialog(this, "Telefone inválido. Formato esperado: (XX) 9XXXX-XXXX.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta e valida o Endereço
        String endereco = JOptionPane.showInputDialog(this, "Digite o endereço do cliente:");
        if (!Validacoes.validarCampoObrigatorio(endereco)) {
            JOptionPane.showMessageDialog(this, "Endereço do cliente é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta e valida o Email
        String email = JOptionPane.showInputDialog(this, "Digite o email do cliente:");
        if (!Validacoes.validarCampoObrigatorio(email)) {
            JOptionPane.showMessageDialog(this, "Email do cliente é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validacoes.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido. Verifique o formato (ex: nome@dominio.com).", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta o Tipo de Cliente
        String[] tiposCliente = {"nacional", "estrangeiro"};
        String tipoCliente = (String) JOptionPane.showInputDialog(this,
                "Selecione o tipo de cliente:",
                "Tipo de Cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tiposCliente,
                tiposCliente[0]);

        if (tipoCliente == null) { // Usuário cancelou
            return;
        }

        // Coleta e valida o Passaporte (apenas se for estrangeiro)
        String passaporte = null;
        if (tipoCliente.equals("estrangeiro")) {
            passaporte = JOptionPane.showInputDialog(this, "Digite o passaporte do cliente estrangeiro (9 dígitos):");
            if (!Validacoes.validarCampoObrigatorio(passaporte)) {
                JOptionPane.showMessageDialog(this, "Passaporte é obrigatório para cliente estrangeiro.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Validacoes.validarPassaporte(passaporte)) {
                JOptionPane.showMessageDialog(this, "Passaporte inválido. Deve conter 9 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Se todas as validações passarem, cria o objeto Cliente e tenta cadastrar
        Cliente novoCliente = new Cliente(nome, cpf, tipoCliente.equals("nacional") ? null : passaporte, idade, telefone, endereco, tipoCliente, email); // AGORA PASSA O EMAIL

        // Chama o método no ClienteService e verifica o resultado
        if (clienteService.cadastrarClienteGUI(novoCliente)) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao cadastrar cliente. Verifique o console para mais detalhes.", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }

        listarClientesGUI(); // Atualiza a lista após o cadastro
    }


    private void listarClientesGUI() {
        displayArea.setText(""); // Limpa a área de exibição
        List<Cliente> clientes = clienteService.listarClientes(); // Obtém a lista de clientes

        if (clientes.isEmpty()) {
            displayArea.append("Nenhum cliente cadastrado.\n");
        } else {
            displayArea.append("--- Lista de Clientes ---\n");
            for (Cliente cliente : clientes) {
                displayArea.append("ID: " + cliente.getId() + "\n");
                displayArea.append("  Nome: " + cliente.getNome() + "\n");
                displayArea.append("  CPF: " + cliente.getCpf() + "\n");
                displayArea.append("  Idade: " + cliente.getIdade() + "\n");
                displayArea.append("  Telefone: " + cliente.getTelefone() + "\n");
                displayArea.append("  Endereço: " + cliente.getEndereco() + "\n");
                displayArea.append("  Tipo Cliente: " + cliente.getTipoCliente() + "\n");
                if (cliente.getPassaporte() != null && !cliente.getPassaporte().isEmpty()) {
                    displayArea.append("  Passaporte: " + cliente.getPassaporte() + "\n");
                }
                displayArea.append("  Email: " + cliente.getEmail() + "\n"); // EXIBE O EMAIL
                displayArea.append("-------------------------\n");
            }
        }
    }

    private void associarPacoteGUI() {
        String identificacaoCliente = JOptionPane.showInputDialog(this, "Digite o CPF ou Passaporte do cliente:");
        if (!Validacoes.validarCampoObrigatorio(identificacaoCliente)) {
            JOptionPane.showMessageDialog(this, "Identificação do cliente não pode ser vazia.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Listar pacotes disponíveis para o usuário escolher
        displayArea.setText("");
        List<PacoteViagem> pacotesDisponiveis = pacoteService.listarPacotes();
        if (pacotesDisponiveis.isEmpty()) {
            displayArea.append("Nenhum pacote de viagem disponível para associação.\n");
            JOptionPane.showMessageDialog(this, "Nenhum pacote de viagem disponível.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        displayArea.append("--- Pacotes Disponíveis ---\n");
        StringBuilder pacotesStr = new StringBuilder();
        for (PacoteViagem p : pacotesDisponiveis) {
            displayArea.append("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Destino: " + p.getDestino() + ", Preço: R$" + String.format("%.2f", p.getPreco()) + "\n");
            pacotesStr.append(p.getId()).append(" - ").append(p.getNome()).append(" (").append(p.getDestino()).append(")\n");
        }
        displayArea.append("---------------------------\n");


        String pacoteIdStr = JOptionPane.showInputDialog(this, "Pacotes Disponíveis:\n" + pacotesStr.toString() + "\n\nDigite o ID do pacote a ser associado:");
        int pacoteId = 0;
        if (!Validacoes.validarCampoObrigatorio(pacoteIdStr)) {
            JOptionPane.showMessageDialog(this, "ID do pacote é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            pacoteId = Integer.parseInt(pacoteIdStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID do pacote inválido. Digite um número.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (clienteService.associarClientePacote(identificacaoCliente, pacoteId)) {
            JOptionPane.showMessageDialog(this, "Associação de pacote realizada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao associar pacote. Verifique o console para mais detalhes.", "Erro na Associação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void associarServicoGUI() {
        String identificacaoCliente = JOptionPane.showInputDialog(this, "Digite o CPF ou Passaporte do cliente:");
        if (!Validacoes.validarCampoObrigatorio(identificacaoCliente)) {
            JOptionPane.showMessageDialog(this, "Identificação do cliente não pode ser vazia.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Listar serviços adicionais disponíveis
        displayArea.setText("");
        List<ServicoAdicional> servicosDisponiveis = servicoAdicionalService.listarServicosAdicionais();
        if (servicosDisponiveis.isEmpty()) {
            displayArea.append("Nenhum serviço adicional disponível para associação.\n");
            JOptionPane.showMessageDialog(this, "Nenhum serviço adicional disponível.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        displayArea.append("--- Serviços Adicionais Disponíveis ---\n");
        StringBuilder servicosStr = new StringBuilder();
        for (ServicoAdicional s : servicosDisponiveis) {
            displayArea.append("ID: " + s.getId() + ", Nome: " + s.getNome() + ", Preço: R$" + String.format("%.2f", s.getPreco()) + "\n");
            servicosStr.append(s.getId()).append(" - ").append(s.getNome()).append(" (R$").append(String.format("%.2f", s.getPreco())).append(")\n");
        }
        displayArea.append("---------------------------------------\n");

        String servicoIdStr = JOptionPane.showInputDialog(this, "Serviços Adicionais Disponíveis:\n" + servicosStr.toString() + "\n\nDigite o ID do serviço a ser associado:");
        int servicoId = 0;
        if (!Validacoes.validarCampoObrigatorio(servicoIdStr)) {
            JOptionPane.showMessageDialog(this, "ID do serviço é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            servicoId = Integer.parseInt(servicoIdStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID do serviço inválido. Digite um número.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Recupera o objeto Cliente e ServicoAdicional
        Cliente clienteParaAssociar = clienteService.buscarClientePorIdentificacao(identificacaoCliente);
        ServicoAdicional servicoParaAssociar = servicoAdicionalService.buscarServicoPorId(servicoId);

        if (clienteParaAssociar != null && servicoParaAssociar != null) {
            if (servicoAdicionalService.associarServicoAoClienteGUI(clienteParaAssociar, servicoParaAssociar)) {
                JOptionPane.showMessageDialog(this, "Associação de serviço realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao associar serviço. Verifique o console para mais detalhes.", "Erro na Associação", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cliente ou Serviço não encontrado. Verifique os IDs.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void excluirClienteGUI() {
        String identificacao = JOptionPane.showInputDialog(this, "Digite o CPF ou Passaporte do cliente a ser excluído:");
        if (!Validacoes.validarCampoObrigatorio(identificacao)) {
            JOptionPane.showMessageDialog(this, "Identificação não pode ser vazia.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] tiposIdentificacao = {"CPF", "Passaporte"};
        String tipoIdentificacao = (String) JOptionPane.showInputDialog(this,
                "Selecione o tipo de identificação:",
                "Tipo de Identificação",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tiposIdentificacao,
                tiposIdentificacao[0]);

        if (tipoIdentificacao == null) { // Usuário cancelou
            return;
        }

        // Validação adicional se for CPF ou Passaporte
        if (tipoIdentificacao.equalsIgnoreCase("CPF") && !Validacoes.validarCpf(identificacao)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Deve conter 11 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tipoIdentificacao.equalsIgnoreCase("Passaporte") && !Validacoes.validarPassaporte(identificacao)) {
            JOptionPane.showMessageDialog(this, "Passaporte inválido. Deve conter 9 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }


        boolean sucesso = clienteService.deletarClientePorIdentificacao(identificacao, tipoIdentificacao);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
            listarClientesGUI(); // Atualiza a lista
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente ou cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarPacotesServicosGUI() {
        String identificacao = JOptionPane.showInputDialog(this, "Digite o CPF ou Passaporte do cliente para listar pacotes e serviços:");
        if (!Validacoes.validarCampoObrigatorio(identificacao)) {
            JOptionPane.showMessageDialog(this, "Identificação não pode ser vazia.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        displayArea.setText(""); // Limpa a área de exibição
        PacotesEServicosCliente pacotesEServicos = clienteService.listarPacotesDeClienteGUI(identificacao);

        if (pacotesEServicos.getPacotes().isEmpty() && pacotesEServicos.getServicos().isEmpty()) {
            displayArea.append("Nenhum pacote ou serviço encontrado para o cliente com identificação: " + identificacao + "\n");
        } else {
            displayArea.append("--- Pacotes do Cliente (" + identificacao + ") ---\n");
            if (pacotesEServicos.getPacotes().isEmpty()) {
                displayArea.append("Nenhum pacote associado.\n");
            } else {
                for (PacoteViagem pacote : pacotesEServicos.getPacotes()) {
                    displayArea.append("  ID: " + pacote.getId() + ", Nome: " + pacote.getNome() + ", Destino: " + pacote.getDestino() + ", Preço: R$" + String.format("%.2f", pacote.getPreco()) + "\n");
                }
            }
            displayArea.append("--------------------------------------------------\n\n");

            displayArea.append("--- Serviços Adicionais do Cliente (" + identificacao + ") ---\n");
            if (pacotesEServicos.getServicos().isEmpty()) {
                displayArea.append("Nenhum serviço adicional associado.\n");
            } else {
                for (ServicoAdicional servico : pacotesEServicos.getServicos()) {
                    displayArea.append("  ID: " + servico.getId() + ", Nome: " + servico.getNome() + ", Preço: R$" + String.format("%.2f", servico.getPreco()) + "\n");
                }
            }
            displayArea.append("------------------------------------------------------\n");
        }
    }
}