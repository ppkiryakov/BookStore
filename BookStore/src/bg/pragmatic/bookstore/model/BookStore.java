package bg.pragmatic.bookstore.model;

import java.util.*;

import bg.pragmatic.bookstore.fileservice.FileService;
import bg.pragmatic.bookstore.model.exceptions.LoadingFailedException;
import bg.pragmatic.bookstore.model.exceptions.SavingFailedException;

public class BookStore {
	
	/**
	 * Keep all products in a map that maps product type to list of product stocks with this type.
	 * For example if have books and music added then the map will have 2 keys - BOOK and MUSIC
	 * The value for the BOOK key will be the list of all stocks that contain books 
	 * and the value for the MUSIC key will be the list of all stocks that contain musics.
	 */
	private Map<ProductType, List<ProductStock>> allProductStocks;
	private FileService fileService;
	
	public BookStore() {
		fileService = new FileService();
	}
	
	/**
	 * Loads all the products
	 * @throws LoadingFailedException
	 */
	public void loadAllProducts() throws LoadingFailedException {
		allProductStocks = fileService.loadAllProductStocks();
	}
	
	/**
	 * Saves all the products with the given type
	 * @param productType type of the products to save
	 * @throws SavingFailedException
	 */
	public void save(ProductType productType) throws SavingFailedException {
		fileService.saveProductStocks(allProductStocks.get(productType), productType);
	}
	
	/**
	 * @throws IllegalArgumentException if count < 0
	 */
	public void addProduct(Product product, int count) throws IllegalArgumentException {
		ProductStock productStock = new ProductStock(product, count);
		
		// Check if contains already a list of products with the current product type 
		List<ProductStock> currentProductTypeStocks = allProductStocks.get(product.getType());
		if(currentProductTypeStocks == null) {
			currentProductTypeStocks = new ArrayList<>();
			allProductStocks.put(product.getType(), currentProductTypeStocks);
		}
		
		currentProductTypeStocks.add(productStock);
	}
	
	/**
	 * Deletes the provided product stock
	 * @param productStock
	 */
	public void deleteProductStock(ProductStock productStock) {
		List<ProductStock> productStocks = allProductStocks.get(productStock.getProduct().getType());
		productStocks.remove(productStock);
	}
	
	/**
	 * Searches all products that has title that contains the provided 'text'
	 * @param text Text to search for
	 * @return All product stocks that contain a product with title containing the provided 'text'
	 */
	public List<ProductStock> search(String text) {
		List<ProductStock> foundProductStocks = new ArrayList<>();
		
		// Search the whole allProductStocks map 
		Set<ProductType> productTypes = allProductStocks.keySet();
		for(ProductType productType : productTypes) {
			List<ProductStock> productStocks = allProductStocks.get(productType);
			
			for(ProductStock productStock : productStocks) {
				if(productStock.getProduct().getTitle().contains(text)) {
					foundProductStocks.add(productStock);
				}
			}
		}
		
		return foundProductStocks;
	}
	
	/**
	 * @param productType
	 * @return All the product stocks for the provided product type 
	 */
	public List<ProductStock> getProductStocks(ProductType productType) {
		return allProductStocks.get(productType);
	}
	
	/**
	 * Sell the provided product stock
	 * @param productStock
	 */
	public void sellProductFromStock(ProductStock productStock) {
		int count = productStock.getCount();
		if(count > 0) {
			productStock.setCount(count - 1);
		}
	}
}
