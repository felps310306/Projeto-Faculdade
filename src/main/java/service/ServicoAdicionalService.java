package service;

import db.ServicoAdicionalDAO;
import db.ClienteDAO;
import model.ServicoAdicional;
import model.Cliente;

import javax.swing.*;
import java.util.List;

public class ServicoAdicionalService {

    private ServicoAdicionalDAO dao;
    private ClienteDAO clienteDAO;

    public ServicoAdicionalService(ServicoAdicionalDAO dao, ClienteDAO clienteDAO) {
        this.dao = dao;
        this.clienteDAO = clienteDAO;
    }

    public void escolherEAssociarServicoAoCliente() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente registrado no momento.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String[] nomesClientes = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nomesClientes[i] = clientes.get(i).getNome(); // Exibindo nome do cliente
        }

        int escolhaCliente = JOptionPane.showOptionDialog(null, "Escolha um cliente:", "Escolher Cliente",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, nomesClientes, nomesClientes[0]);

        if (escolhaCliente >= 0) {
            Cliente clienteEscolhido = clientes.get(escolhaCliente);

            List<ServicoAdicional> servicos = dao.listarTodos();
            if (servicos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum serviço adicional disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] nomesServicos = new String[servicos.size()];
            for (int i = 0; i < servicos.size(); i++) {
                nomesServicos[i] = servicos.get(i).getNome() + " - " + servicos.get(i).getPreco() + " R$";
            }

            int escolhaServico = JOptionPane.showOptionDialog(null, "Escolha um serviço adicional para o cliente " + clienteEscolhido.getNome() + ":",
                    "Escolher Serviço Adicional", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, nomesServicos, nomesServicos[0]);


            if (escolhaServico >= 0) {
                ServicoAdicional servicoEscolhido = servicos.get(escolhaServico);


                clienteDAO.associarServicoAoCliente(clienteEscolhido, servicoEscolhido);

                JOptionPane.showMessageDialog(null, "Serviço " + servicoEscolhido.getNome() + " associado ao cliente " + clienteEscolhido.getNome(),
                        "Serviço Associado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}