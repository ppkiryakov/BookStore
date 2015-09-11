package bg.pragmatic.bookstore.model;

/**
 * This class keeps both a product and the number of available copies of this product (the number
 * of items in the book store for this product)
 *
 */
public class ProductStock {
	
	private Product product;
	private int count;
	
	/**
	 * @throws IllegalArgumentException if count < 0
	 */
	public ProductStock(Product product, int count) throws IllegalArgumentException {
		super();
		if(count < 0) {
			throw new IllegalArgumentException();
		}
		
		this.product = product;
		this.count = count;		
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}

	/**
	 * @throws IllegalArgumentException if count < 0
	 */
	public void setCount(int count) throws IllegalArgumentException {
		if(count < 0) {
			throw new IllegalArgumentException();
		}
		
		this.count = count;
	}

	public int getCount() {
		return count;
	}	
}
