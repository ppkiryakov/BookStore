package bg.pragmatic.bookstore.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import bg.pragmatic.bookstore.model.exceptions.LoadingFailedException;

public abstract class Product {
	
	private ProductType type;
	private String id;
	private String title;
	private String author;
	private double price;
	
	public Product(ProductType type) {
		super();
		this.type = type;
		this.id = UUID.randomUUID().toString();
	}

	public ProductType getType() {
		return type;
	}
	
	public String getId() {
		return id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return price;
	}
	
	/**
	 * Converts the product properties to a List 
	 * @return  List representing the converted product properties converted as strings
	 * @see load()
	 */
	public List<String> convertToList() {
		List<String> values = new ArrayList<>();
		values.add(id);
		values.add(title);
		values.add(author);
		values.add(String.valueOf(price));
		values.addAll(convertAdditionalProperties());
		
		return values;
	}
	/**
	 * Loads the product properties from a List. The order of the elements in the list must be the same
	 * as returned in save() method 
	 * @param values stores the values of properties converted as strings
	 * @throws LoadingFailedException when format of the list is not correct 
	 * @see save()
	 */
	public void loadFromList(List<String> values) throws LoadingFailedException {
		// Check if list size is smaller then the number of the properties
		final int propertiesCount = 4;
		if(values.size() < propertiesCount) {
			throw new LoadingFailedException("Insufficient values");
		}
		
		Iterator<String> iterator = values.iterator();
		id = iterator.next();
		title = iterator.next();
		author = iterator.next();
		price = Double.valueOf(iterator.next());
		
		loadAdditionalProperties(values.subList(propertiesCount, values.size()));
	}
	
	protected abstract List<String> convertAdditionalProperties();
	protected abstract void loadAdditionalProperties(List<String> values) throws LoadingFailedException;
}
