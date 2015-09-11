package bg.pragmatic.bookstore.users;

public interface User {
	boolean canAddProducts();
	boolean canDeleteProducts();
	boolean canSellProducts();
	boolean canEditProducts();
}
