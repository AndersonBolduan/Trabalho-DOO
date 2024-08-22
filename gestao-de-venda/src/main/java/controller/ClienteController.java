package controller;

import model.dao.ClienteDao;
import model.entidades.Cliente;
import model.util.ClienteTableModel;
import view.formulario.Dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class ClienteController implements ActionListener, KeyListener {

    private Dashboard dashboard;
    private ClienteDao clienteDao;
    private ClienteTableModel clienteTableModel;

    public ClienteController(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.clienteDao = new ClienteDao();
        actualizarTabelaCliente();
        this.dashboard.getTxtClientePesquisar().addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String accao = ae.getActionCommand().toLowerCase();

        switch (accao) {
            case "adicionar":
                adicionar();
                break;
            case "editar":
                editar();
                break;
            case "apagar":
                remover();
                break;
            case "salvar":
                salvar();
                break;
            case "cancelar":
                cancelar();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        String pesquisar = this.dashboard.getTxtClientePesquisar().getText();

        if (pesquisar.isEmpty()) {
            actualizarTabelaCliente();
        } else {
            List<Cliente> clientesTemp = this.clienteDao.todosCliente().stream()
                    .filter(c -> c.getNome().toLowerCase().contains(pesquisar.toLowerCase())
                    || c.getTelefone().toLowerCase().contains(pesquisar.toLowerCase())
                    || c.getEndereco().toLowerCase().contains(pesquisar.toLowerCase())
                    || c.getCpf().toLowerCase().contains(pesquisar.toLowerCase()))
                    .collect(Collectors.toList());

            actualizarTabelaCliente(clientesTemp);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    private void salvar() {
        String nome = this.dashboard.getTxtClienteNome().getText();
        String telefone = this.dashboard.getTxtClienteTelefone().getText();
        String endereco = this.dashboard.getTxtClienteEndereco().getText();
        String cpf = this.dashboard.getTxtClienteCpf().getText(); // Obtendo CPF

        int selectedRow = this.dashboard.getTabelaCliente().getSelectedRow();
        Long id;
        if (selectedRow == -1) {
            // Novo cliente
            id = 0L;
        } else {
            // Cliente existente
            id = (Long) this.dashboard.getTabelaCliente().getValueAt(selectedRow, 0);
        }

        Cliente cliente;
        if (id == 0L) {
            // Novo cliente
            cliente = new Cliente(id, nome, telefone, endereco, cpf); // Incluindo CPF
        } else {
            // Cliente existente, manter o CPF original
            Cliente clienteExistente = clienteDao.buscarClientePeloId(id);
            cliente = new Cliente(id, nome, telefone, endereco, clienteExistente.getCpf()); // Mantendo o CPF original
        }

        String mensagem = clienteDao.salvar(cliente);

        if (mensagem.startsWith("Cliente")) {
            cancelar();
            actualizarTabelaCliente();
            JOptionPane.showMessageDialog(dashboard, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dashboard, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        int selectedRow = this.dashboard.getTabelaCliente().getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) this.dashboard.getTabelaCliente().getValueAt(selectedRow, 0); // Assume que a primeira coluna é o ID
            Cliente cliente = clienteDao.buscarClientePeloId(id);
            if (cliente != null) {
                preencherFormulario(cliente);
                this.dashboard.getTxtClienteCpf().setEnabled(false); // Desabilita o campo de CPF ao editar
                mostrarTela();
            } else {
                JOptionPane.showMessageDialog(dashboard, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(dashboard, "Deves selecionar um cliente na tabela", "Seleciona um cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void remover() {
        int selectedRow = this.dashboard.getTabelaCliente().getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) this.dashboard.getTabelaCliente().getValueAt(selectedRow, 0); // Assume que a primeira coluna é o ID
            Cliente cliente = clienteDao.buscarClientePeloId(id);
            if (cliente != null) {
                int confirmar = JOptionPane.showConfirmDialog(dashboard,
                        String.format("Tens certeza que desejas apagar? \nNome: %s", cliente.getNome()),
                        "Apagar cliente", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    String mensagem = clienteDao.deleteClientePeloId(cliente.getId());
                    JOptionPane.showMessageDialog(dashboard, mensagem);
                    actualizarTabelaCliente();
                }
            } else {
                JOptionPane.showMessageDialog(dashboard, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(dashboard, "Deves selecionar um cliente na tabela", "Seleciona um cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void preencherFormulario(Cliente cliente) {
        this.dashboard.getTxtClienteNome().setText(cliente.getNome());
        this.dashboard.getTxtClienteTelefone().setText(cliente.getTelefone());
        this.dashboard.getTxtClienteEndereco().setText(cliente.getEndereco());
        this.dashboard.getTxtClienteCpf().setText(cliente.getCpf()); // Preenchendo CPF
    }

    private void cancelar() {
        limpar();
        this.dashboard.getDialogCliente().setVisible(false);
    }

    private void limpar() {
        this.dashboard.getTxtClienteNome().setText("");
        this.dashboard.getTxtClienteTelefone().setText("");
        this.dashboard.getTxtClienteEndereco().setText("");
        this.dashboard.getTxtClienteCpf().setText(""); // Limpando CPF
        this.dashboard.getTxtClienteCpf().setEnabled(true); // Habilitando o campo de CPF para novos clientes
    }

    private void mostrarTela() {
        this.dashboard.getDialogCliente().pack();
        this.dashboard.getDialogCliente().setLocationRelativeTo(dashboard);
        this.dashboard.getDialogCliente().setVisible(true);
    }

    private void adicionar() {
        limpar(); // Limpa o formulário e habilita o campo de CPF
        mostrarTela();
    }

    private void actualizarTabelaCliente() {
        List<Cliente> clientes = clienteDao.todosCliente();
        this.clienteTableModel = new ClienteTableModel(clientes);
        this.dashboard.getTabelaCliente().setModel(clienteTableModel);
        this.dashboard.getLabelHomeCliente().setText(String.format("%d", clientes.size()));
    }

    private void actualizarTabelaCliente(List<Cliente> clientes) {
        this.clienteTableModel = new ClienteTableModel(clientes);
        this.dashboard.getTabelaCliente().setModel(clienteTableModel);
        this.dashboard.getLabelHomeCliente().setText(String.format("%d", clientes.size()));
    }
}
