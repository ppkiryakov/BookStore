package bg.pragmatic.bookstore.model.tables;

import bg.pragmatic.bookstore.model.ProductStock;
import bg.pragmatic.bookstore.model.Book;


@SuppressWarnings("serial")
public class BooksTableModel extends ProductTableModel {

	private static final int COLUMNS_COUNT = 6;

	@Override
	public int getColumnCount() {
		return COLUMNS_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductStock bookStock = getProductStock(rowIndex);
		Book book = (Book)bookStock.getProduct();
		
		switch(columnIndex) {
		case 0: return String.valueOf(bookStock.getCount());
		case 1: return book.getTitle();
		case 2: return book.getAuthor();
		case 3: return String.valueOf(book.getPrice());
		case 4: return book.getPublishersName();
		case 5: return (book.isForeignLiterature() ? "yes" : "no");
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
		case 4: return "Publisher";
		case 5: return "Foreign";
		}
		
		return null;
	}
}
