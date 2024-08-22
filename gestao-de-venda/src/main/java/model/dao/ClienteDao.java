package model.dao;

import model.conexao.Conexao;
import model.conexao.ConexaoMysql;
import model.entidades.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private final Conexao conexao;

    public ClienteDao() {
        this.conexao = new ConexaoMysql();
    }

    public String salvar(Cliente cliente) {
        if(cliente.getId() == 0L) {
            String sql = "INSERT INTO cliente(nome, telefone, morada, status, cpf) VALUES (?, ?, ?, ?, ?)";
            return registar(cliente, sql, true);
        } else {
            String sql = "UPDATE cliente SET nome = ?, telefone = ?, morada = ?, status = ? WHERE id = ?";
            return registar(cliente, sql, false);
        }
    }

    private String registar(Cliente cliente, String sql, boolean isAdicionar) {
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, cliente, isAdicionar);
            int resultado = preparedStatement.executeUpdate();
            return resultado == 1 ? "Cliente salvo com sucesso." : "Não foi possível salvar cliente";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private void preparedStatementSet(PreparedStatement preparedStatement, Cliente cliente, boolean isAdicionar) throws SQLException {
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(2, cliente.getTelefone());
        preparedStatement.setString(3, cliente.getEndereco());
        preparedStatement.setString(4, cliente.getStatus());

        if (isAdicionar) {
            preparedStatement.setString(5, cliente.getCpf());
        } else {
            preparedStatement.setLong(5, cliente.getId());
        }
    }

    public List<Cliente> todosCliente() {
        String sql = "SELECT * FROM cliente WHERE status = 'ativo'";
        List<Cliente> clientes = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while(result.next()) {
                clientes.add(getCliente(result));
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return clientes;
    }

    public Cliente buscarClientePeloId(Long id) {
        String sql = String.format("SELECT * FROM cliente WHERE id=%d AND status = 'ativo'", id);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while(result.next()) {
                return getCliente(result);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return null;
    }

    public Cliente buscarClientePeloCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ? AND status = 'ativo'";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preparedStatement.setString(1, cpf);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                return getCliente(result);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return null;
    }
    
    public List<Cliente> pesquisarClientePorNomeOuId(String texto) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE nome LIKE ? OR id = ?";
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preparedStatement.setString(1, "%" + texto + "%");
            preparedStatement.setString(2, texto);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getLong("id"));
                cliente.setNome(resultSet.getString("nome"));
                // Preencher outros campos do cliente
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientes;
    }

    public Cliente buscarUltimoCliente() {
        String sql = "SELECT * FROM cliente WHERE id= (SELECT max(id) FROM cliente) AND status = 'ativo'";
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while(result.next()) {
                return getCliente(result);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return null;
    }

    private Cliente getCliente(ResultSet result) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(result.getLong("id"));
        cliente.setNome(result.getString("nome"));
        cliente.setTelefone(result.getString("telefone"));
        cliente.setEndereco(result.getString("morada"));
        cliente.setStatus(result.getString("status"));
        cliente.setCpf(result.getString("cpf")); // Adiciona o CPF
        return cliente;
    }

    public String transferirVendasParaClienteGenerico(Long clienteId) {
        String sql = "UPDATE venda SET cliente_id = ? WHERE cliente_id = ?";
        try (PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql)) {
            preparedStatement.setLong(1, 999999999L); // ID do cliente genérico
            preparedStatement.setLong(2, clienteId);
            preparedStatement.executeUpdate();
            return "Vendas transferidas com sucesso.";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    public String deleteClientePeloId(Long id) {
        String mensagem = transferirVendasParaClienteGenerico(id);
        if (mensagem.startsWith("Error")) {
            return mensagem;
        }

        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int resultado = preparedStatement.executeUpdate();
            return resultado == 1 ? "Cliente excluído com sucesso" : "Não foi possível excluir cliente";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
    
    
}
