package bg.pragmatic.bookstore.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import bg.pragmatic.bookstore.model.exceptions.LoadingFailedException;

public class Book extends Product {

	private String publishersName;
	private boolean isForeignLiterature;
	
	public Book() {
		super(ProductType.BOOK);
	}
	
	public void setPublishersName(String publishersName) {
		this.publishersName = publishersName;
	}
		
	public String getPublishersName() {
		return publishersName;
	}

	public void setForeignLiterature(boolean isForeignLiterature) {
		this.isForeignLiterature = isForeignLiterature;
	}

	public boolean isForeignLiterature() {
		return isForeignLiterature;
	}

	@Override
	protected List<String> convertAdditionalProperties() {
		List<String> values = new ArrayList<>();		
		values.add(publishersName);
		values.add(String.valueOf(isForeignLiterature));
		
		return values;
	}

	@Override
	protected void loadAdditionalProperties(List<String> values) throws LoadingFailedException {
		final int propertiesCount = 2;
		if(values.size() < propertiesCount) {
			throw new LoadingFailedException("Insufficient values");
		}
		
		Iterator<String> iterator = values.iterator();
		publishersName = iterator.next();
		isForeignLiterature = Boolean.valueOf(iterator.next());
	}
}
