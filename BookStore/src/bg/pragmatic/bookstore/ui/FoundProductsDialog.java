package bg.pragmatic.bookstore.ui;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import java.util.List;

import javax.swing.JTable;

import bg.pragmatic.bookstore.model.ProductStock;
import bg.pragmatic.bookstore.model.tables.FoundProductsTableModel;

@SuppressWarnings("serial")
public class FoundProductsDialog extends JDialog {

	private JTable table;
	private FoundProductsTableModel tableModel;

	/**
	 * Create the dialog.
	 */
	public FoundProductsDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 438, 266);
		getContentPane().add(scrollPane);
		
		tableModel = new FoundProductsTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
	}
	
	public void setFoundProductStocks(List<ProductStock> productStocks) {
		tableModel.setProductStocks(productStocks);
	}
}
