package bg.pragmatic.bookstore.fileservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bg.pragmatic.bookstore.model.*;
import bg.pragmatic.bookstore.model.exceptions.*;

public class FileService {
	
	private static final String BOOKS_FILE_NAME = "books.data";
	private static final String MUSICS_FILE_NAME = "musics.data";
	
	/**
	 * Saves all product stocks with the given type to the corresponding file (books will be saved to "books.data" file, musics to "musics.data" file)
	 * @param stocks List of product stocks to save. All products in the stock must have the same product type as the provided product type
	 * @param type The product type of all products
	 * @throws SavingFailedException if save has failed
	 */
	public void saveProductStocks(List<ProductStock> stocks, ProductType type) throws SavingFailedException {
		String fileName = getFileName(type);
		
		// Check first if all products have the same product type as the provided one
		for(ProductStock productStock : stocks) {
			if(productStock.getProduct().getType() != type) {
				throw new SavingFailedException("Not all products are of type: " + type);
			}
		}
		
		try(PrintWriter writer = new PrintWriter(fileName)) {
			for(ProductStock productStock : stocks) {
				// Gets the list of strings that represents the values of the current product stock
				List<String> values = productStock.getProduct().convertToList();
				values.add(String.valueOf(productStock.getCount()));
				
				writer.println(createLine(values));
			}
		} catch (FileNotFoundException e) {
			throw new SavingFailedException("Cannot create file for writing");
		}
	}
	
	public Map<ProductType, List<ProductStock>> loadAllProductStocks() throws LoadingFailedException {
		Map<ProductType, List<ProductStock>> allProductStocks = new Hashtable<>();
		
		allProductStocks.put(ProductType.BOOK, loadProductStocks(ProductType.BOOK));
		allProductStocks.put(ProductType.MUSIC, loadProductStocks(ProductType.MUSIC));
		
		return allProductStocks;
	}
	
	private String getFileName(ProductType productType) {
		switch (productType) {
		case BOOK: return BOOKS_FILE_NAME;
		case MUSIC: return MUSICS_FILE_NAME;
		default: return null;
		}
	}
	
	private Product createProduct(ProductType productType) {
		switch (productType) {
		case BOOK: return new Book();
		case MUSIC: return new Music();
		default: return null;
		}
	}
	
	/**
	 * Creates CSV format line for the given list of strings
	 * @param values list of strings
	 * @return string that is in CSV format (i.e. "values[0] , values[1] , values[2], ...")
	 */
	private String createLine(List<String> values) {
		StringBuilder builder = new StringBuilder();
		int valuesCount = values.size();
		int index = 0;
		for(String value : values) {
			builder.append(value);
			// Add comma after all values except the last value
			if(index != valuesCount - 1) {
				builder.append(",");
			}
			
			index++;
		}
		
		return builder.toString();
	}
	
	private List<ProductStock> loadProductStocks(ProductType productType) throws LoadingFailedException {		
		String fileName = getFileName(productType);
		try(Scanner sc = new Scanner(new File(fileName))) {
			List<ProductStock> productStocks = new ArrayList<ProductStock>();
			while(sc.hasNext()) {
				String line = sc.nextLine();
				List<String> tokens = Arrays.asList(line.split(","));
				ProductStock productStock = loadSingleProductStock(productType, tokens);
				productStocks.add(productStock);
			}
			
			return productStocks;
		} catch (FileNotFoundException e) {
			return new ArrayList<ProductStock>();
		}
	}
	
	private ProductStock loadSingleProductStock(ProductType productType, List<String> values) throws LoadingFailedException {
		if(values.size() == 0) {
			throw new LoadingFailedException("No values to load from");
		}
		
		// The count of products is stored in the last element of the list (see saveProductStocks())
		int count = Integer.valueOf(values.get(values.size() - 1));
		
		Product product = createProduct(productType);
		product.loadFromList(values.subList(0, values.size() - 1));
		
		return new ProductStock(product, count);
	}
}
