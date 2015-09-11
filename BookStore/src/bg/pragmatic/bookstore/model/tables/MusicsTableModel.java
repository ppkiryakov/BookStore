package bg.pragmatic.bookstore.model.tables;

import bg.pragmatic.bookstore.model.Music;
import bg.pragmatic.bookstore.model.ProductStock;

@SuppressWarnings("serial")
public class MusicsTableModel extends ProductTableModel {

	private static final int COLUMNS_COUNT = 6;

	@Override
	public int getColumnCount() {
		return COLUMNS_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductStock musicStock = getProductStock(rowIndex);
		Music music = (Music)musicStock.getProduct();
		
		switch(columnIndex) {
		case 0: return String.valueOf(musicStock.getCount());
		case 1: return music.getTitle();
		case 2: return music.getAuthor();
		case 3: return String.valueOf(music.getPrice());
		case 4: return String.valueOf(music.getYearOfPublish());
		case 5: return String.valueOf(music.getSongsCount());
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
		case 4: return "Year";
		case 5: return "Songs count";
		}
		
		return null;
	}
}
