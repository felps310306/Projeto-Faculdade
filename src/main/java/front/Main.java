// front/Main.java (FINALMENTE ATUALIZADO PARA GUI!)
package front;

import db.ClienteDAO;
import db.PacoteDAO;
import db.ServicoAdicionalDAO;
import service.ClienteService;
import service.PacoteService;
import service.ServicoAdicionalService;
import util.ConexaoBD;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // 1. Testar conexão com o banco de dados antes de iniciar a GUI
        if (!ConexaoBD.testarConexao()) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados. O programa será encerrado.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return; // Encerra o aplicativo se a conexão falhar
        }

        // 2. Inicializa os DAOs (Data Access Objects)
        // São responsáveis pela comunicação direta com o banco de dados.
        ServicoAdicionalDAO servicoDAO = new ServicoAdicionalDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();

        // 3. Inicializa os Services (Camada de Lógica de Negócio)
        // Eles encapsulam as regras de negócio e orquestram as operações dos DAOs.
        // O construtor de ServicoAdicionalService foi ajustado para receber apenas os DAOs que ele utiliza.
        ServicoAdicionalService servicoAdicionalService = new ServicoAdicionalService(servicoDAO, clienteDAO);
        ClienteService clienteService = new ClienteService(clienteDAO);
        PacoteService pacoteService = new PacoteService(pacoteDAO);


        // 4. Inicia a interface gráfica (GUI) em um thread seguro.
        // Swing é single-threaded e todas as operações de GUI devem ser feitas na Event Dispatch Thread (EDT).
        // SwingUtilities.invokeLater garante que a criação e exibição da GUI ocorra na EDT.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Cria uma instância da sua janela principal (AgenciaViagensGUI),
                // passando as instâncias dos serviços para que a GUI possa interagir com a lógica de negócio.
                AgenciaViagensGUI gui = new AgenciaViagensGUI(clienteService, pacoteService, servicoAdicionalService);
                gui.setVisible(true); // Torna a janela principal visível para o usuário.
            }
        });

        // NOTA: O loop 'while (continuar)' e todos os JOptionPane de menu
        // foram removidos daqui, pois a interface gráfica (AgenciaViagensGUI)
        // agora é quem gerencia toda a interação com o usuário através de botões e campos.
    }
}