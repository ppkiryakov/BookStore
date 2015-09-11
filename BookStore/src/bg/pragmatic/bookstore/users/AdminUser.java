package bg.pragmatic.bookstore.users;

public class AdminUser implements User {

	@Override
	public boolean canAddProducts() {
		return true;
	}
	
	@Override
	public boolean canDeleteProducts() {
		return true;
	}
	
	@Override
	public boolean canSellProducts() {
		return true;
	}
	
	@Override
	public boolean canEditProducts() {
		return true;
	}
}
