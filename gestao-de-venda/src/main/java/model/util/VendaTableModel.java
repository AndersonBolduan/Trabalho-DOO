package model.util;

import model.entidades.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo da tabela para gerenciar as vendas.
 */
public class VendaTableModel extends AbstractTableModel {
    
    private List<Venda> vendas;
    private String[] colunas = {"ID", "CLIENTE", "TOTAL", "VALOR PAGO", "DESCONTO", "TROCO", "DATA", "VENDIDO POR"};

    public VendaTableModel(List<Venda> vendas) {
        this.vendas = new ArrayList<>(vendas);
    }

    @Override
    public int getRowCount() {
        return vendas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Venda venda = vendas.get(linha);
        
        switch(coluna) {
            case 0: return venda.getId();
            case 1: return venda.getCliente().getNome();
            case 2: return venda.getTotalVenda();
            case 3: return venda.getValorPago();
            case 4: return venda.getDesconto();
            case 5: return venda.getTroco();
            case 6: return venda.getDataHoraCriacao();
            case 7: return venda.getUsuario().getNome();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
        fireTableDataChanged(); // Notifica a tabela de que os dados foram atualizados
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; 
    }

    // Adicionar uma nova venda
    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
        int row = vendas.size() - 1;
        fireTableRowsInserted(row, row); // Notifica a tabela de que uma nova linha foi inserida
    }

    // Remover uma venda existente
    public void removerVenda(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < vendas.size()) {
            vendas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex); // Notifica a tabela de que uma linha foi removida
        }
    }

    // Atualizar uma venda existente
    public void atualizarVenda(int rowIndex, Venda vendaAtualizada) {
        if (rowIndex >= 0 && rowIndex < vendas.size()) {
            vendas.set(rowIndex, vendaAtualizada);
            fireTableRowsUpdated(rowIndex, rowIndex); // Notifica a tabela de que uma linha foi atualizada
        }
    }
}
