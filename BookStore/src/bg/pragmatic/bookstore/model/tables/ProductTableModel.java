package bg.pragmatic.bookstore.model.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bg.pragmatic.bookstore.model.ProductStock;

@SuppressWarnings("serial")
public abstract class ProductTableModel extends AbstractTableModel {
	
	private List<ProductStock> productStocks;
	
	public void setProductStocks(List<ProductStock> productStocks) {
		this.productStocks = productStocks;
		fireTableDataChanged();
	}
	
	public ProductStock getProductStock(int index) {
		return productStocks.get(index);
	}
	
	@Override
	public int getRowCount() {
		return (productStocks != null ? productStocks.size() : 0);
	}
}
