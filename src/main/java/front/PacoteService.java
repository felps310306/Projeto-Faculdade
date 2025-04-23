package front;

import model.PacoteViagem;

import javax.swing.*;
import java.util.ArrayList;

public class PacoteService {
    private ArrayList<PacoteViagem> listaDePacotes;

    public PacoteService() {
        this.listaDePacotes = new ArrayList<>();
    }

    public void adicionarPacote(PacoteViagem pacote) {
        listaDePacotes.add(pacote);
        JOptionPane.showMessageDialog(null, "Pacote adicionado: " + pacote.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listarPacotes() {
        if (listaDePacotes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pacote cadastrado.", "Lista de Pacotes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder lista = new StringBuilder("Pacotes disponíveis:\n\n");
        for (PacoteViagem pacote : listaDePacotes) {
            lista.append("Nome: ").append(pacote.getNome()).append("\n")
                    .append("Destino: ").append(pacote.getDestino()).append("\n")
                    .append("Duração: ").append(pacote.getDuracao()).append(" dias\n")
                    .append("Tipo: ").append(pacote.getTipo()).append("\n")
                    .append("Preço: R$").append(pacote.getPreco()).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Pacotes", JOptionPane.INFORMATION_MESSAGE);
    }

    public PacoteViagem selecionarPacote() {
        if (listaDePacotes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pacote cadastrado.", "Seleção de Pacotes", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        StringBuilder menu = new StringBuilder("Selecione um pacote digitando o número:\n\n");

        for (int i = 0; i < listaDePacotes.size(); i++) {
            PacoteViagem pacote = listaDePacotes.get(i);
            menu.append(i + 1).append(" - ").append(pacote.getNome())
                    .append(" | Destino: ").append(pacote.getDestino())
                    .append(" | Duração: ").append(pacote.getDuracao()).append(" dias")
                    .append(" | Preço: R$").append(pacote.getPreco())
                    .append("\n");
        }

        String input = JOptionPane.showInputDialog(null, menu.toString(), "Seleção de Pacotes", JOptionPane.QUESTION_MESSAGE);

        if (input == null || input.isEmpty()) {
            return null;
        }

        try {
            int escolha = Integer.parseInt(input);

            if (escolha >= 1 && escolha <= listaDePacotes.size()) {
                return listaDePacotes.get(escolha - 1); // Retorna o pacote escolhido
            } else {
                JOptionPane.showMessageDialog(null, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public PacoteViagem buscarPacotePorNome(String nome) {
        for (PacoteViagem pacote : listaDePacotes) {
            if (pacote.getNome().equalsIgnoreCase(nome)) {
                return pacote;
            }
        }
        return null;
    }
}
