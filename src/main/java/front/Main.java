package front;

import model.PacoteViagem;
import util.ConexaoBD;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Testar conexão com o banco
        if (!ConexaoBD.testarConexao()) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados. O programa será encerrado.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PacoteService pacoteService = new PacoteService();

        // Criando pacotes fixos (exemplo)
        pacoteService.adicionarPacote(new PacoteViagem("Aventura Amazônica", "Amazonas", 7, "Aventura", 2500.00f, "Passeios em trilhas e visita a comunidades indígenas."));
        pacoteService.adicionarPacote(new PacoteViagem("Romance em Gramado", "Gramado - RS", 5, "Romântico", 3200.00f, "Pacote especial para casais com fondue e city tour."));
        pacoteService.adicionarPacote(new PacoteViagem("Cultura em Ouro Preto", "Ouro Preto - MG", 3, "Cultural", 1500.00f, "Visitas guiadas aos museus e igrejas históricas."));
        pacoteService.adicionarPacote(new PacoteViagem("Sol e Praia em Fortaleza", "Fortaleza - CE", 6, "Praia", 2800.00f, "Pacote com hotel à beira-mar e passeios de buggy."));
        pacoteService.adicionarPacote(new PacoteViagem("Natureza em Bonito", "Bonito - MS", 5, "Ecoturismo", 2900.00f, "Flutuação nos rios cristalinos e visita às grutas."));

        // Menu principal
        boolean continuar = true;
        while (continuar) {
            String[] opcoes = {"Listar Pacotes", "Selecionar Pacote", "Sair"};
            int escolha = JOptionPane.showOptionDialog(null, "Bem-vindo à Agência de Viagens!", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0:
                    pacoteService.listarPacotes();
                    break;
                case 1:
                    PacoteViagem selecionado = pacoteService.selecionarPacote();
                    if (selecionado != null) {
                        JOptionPane.showMessageDialog(null, "Você escolheu:\n\n" + selecionado.toString(), "Pacote Selecionado", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                default:
                    continuar = false;
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Obrigado por usar a Agência de Viagens!", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
    }
}
