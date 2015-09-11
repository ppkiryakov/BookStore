package bg.pragmatic.bookstore.model.tables;

import bg.pragmatic.bookstore.model.Product;
import bg.pragmatic.bookstore.model.ProductStock;

@SuppressWarnings("serial")
public class FoundProductsTableModel extends ProductTableModel {

	private static final int COLUMNS_COUNT = 4;

	@Override
	public int getColumnCount() {
		return COLUMNS_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductStock productStock = getProductStock(rowIndex);
		Product product = productStock.getProduct();
		switch(columnIndex) {
		case 0: return String.valueOf(productStock.getCount());
		case 1: return product.getTitle();
		case 2: return product.getAuthor();
		case 3: return String.valueOf(product.getPrice());
		}
		
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "Count";
		case 1: return "Title";
		case 2: return "Author";
		case 3: return "Price";
		}
		
		return null;
	}
}
