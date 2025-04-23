package front;

import model.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class RegistroDeCliente {
    private ArrayList<Cliente> listaDeClientes = new ArrayList<>();

    public void registrarCliente() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) return;

        String tipoCliente = JOptionPane.showInputDialog(null, "Digite o tipo de cliente (nacional/estrangeiro):", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
        if (tipoCliente == null || tipoCliente.trim().isEmpty() || (!tipoCliente.equals("nacional") && !tipoCliente.equals("estrangeiro"))) {
            JOptionPane.showMessageDialog(null, "Tipo de cliente inválido. Escolha entre 'nacional' ou 'estrangeiro'.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpfOuPassaporte = "";
        if (tipoCliente.equals("nacional")) {
            cpfOuPassaporte = JOptionPane.showInputDialog(null, "Digite o CPF:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
            if (cpfOuPassaporte == null || cpfOuPassaporte.trim().isEmpty()) return;
            cpfOuPassaporte = cpfOuPassaporte.replaceAll("[^0-9]", "");  // Remove caracteres não numéricos
        } else {
            cpfOuPassaporte = JOptionPane.showInputDialog(null, "Digite o número do passaporte:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
            if (cpfOuPassaporte == null || cpfOuPassaporte.trim().isEmpty()) return;
        }

        String idadeStr = JOptionPane.showInputDialog(null, "Digite a idade:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
        if (idadeStr == null || idadeStr.trim().isEmpty()) return;
        int idade = Integer.parseInt(idadeStr);

        String telefone = JOptionPane.showInputDialog(null, "Digite o telefone:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
        if (telefone == null || telefone.trim().isEmpty()) return;

        String endereco = JOptionPane.showInputDialog(null, "Digite o endereço:", "Cadastro de Cliente", JOptionPane.QUESTION_MESSAGE);
        if (endereco == null || endereco.trim().isEmpty()) return;

        Cliente cliente = new Cliente(nome, cpfOuPassaporte, tipoCliente.equals("nacional") ? null : cpfOuPassaporte, idade, telefone, endereco, tipoCliente);
        if (!cliente.isValido()) {
            JOptionPane.showMessageDialog(null, "Cadastro inválido. Verifique os dados informados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listaDeClientes.add(cliente);
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listarClientes() {
        if (listaDeClientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente cadastrado.", "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder lista = new StringBuilder("Clientes cadastrados:\n\n");
        for (Cliente cliente : listaDeClientes) {
            lista.append(cliente.toString()).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deletarClientePorCpf() {
        String inputCpf = JOptionPane.showInputDialog(null, "Insira o CPF que deseja excluir:", "Deletar cliente", JOptionPane.QUESTION_MESSAGE);

        if (inputCpf == null || inputCpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operação cancelada ou CPF não informado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final String cpf = inputCpf.replaceAll("[^0-9]", "");
        boolean removido = listaDeClientes.removeIf(cliente -> cliente.getCpf().equals(cpf));

        if (removido) {
            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
