package front;

import db.ClienteDAO;
import db.PacoteDAO;
import model.Cliente;
import model.PacoteViagem;

public class Main {

    public static void main(String[] args) {
        // Criando a instância do ClienteDAO e PacoteDao
        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();

        // Inserir Cliente
        Cliente cliente1 = new Cliente("Maria Oliveira", "98765432100", null, 30, "999999999", "Rua ABC, 123", "nacional");
        Cliente cliente2 = new Cliente("João Silva", "12345678901", null, 28, "888888888", "Avenida XYZ, 456", "nacional");
        Cliente cliente3 = new Cliente("Ana Souza", "11223344556", null, 25, "777777777", "Rua 456, 789", "nacional");

        clienteDAO.inserirCliente(cliente1);
        clienteDAO.inserirCliente(cliente2);
        clienteDAO.inserirCliente(cliente3);

        // Listando todos os clientes após inserção
        System.out.println("Lista de Clientes após inserção:");
        clienteDAO.listarClientes().forEach(cliente -> System.out.println(cliente));

        // Inserir Pacote de Viagem
        PacoteViagem pacote1 = new PacoteViagem("Pacote de Férias para Paris", "Paris, França", 7, "Romântico", 4500.00f, "Inclui passagem aérea e hotel 5 estrelas.");
        PacoteViagem pacote2 = new PacoteViagem("Viagem Cultural para Roma", "Roma, Itália", 10, "Cultural", 5500.00f, "Inclui ingressos para museus e passeios históricos.");

        pacoteDAO.salvar(pacote1);
        pacoteDAO.salvar(pacote2);

        // Listando todos os pacotes após inserção
        System.out.println("\nLista de Pacotes de Viagem após inserção:");
        pacoteDAO.listar().forEach(pacote -> System.out.println(pacote));

        // Excluindo um Cliente (pelo CPF)
        System.out.println("\nExcluindo cliente com CPF 12345678901...");
        clienteDAO.deletarCliente("12345678901");

        // Listando todos os clientes após exclusão
        System.out.println("\nLista de Clientes após exclusão:");
        clienteDAO.listarClientes().forEach(cliente -> System.out.println(cliente));
    }
}
