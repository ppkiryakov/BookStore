package bg.pragmatic.bookstore.users;

public class NormalUser implements User {
	
	@Override
	public boolean canAddProducts() {
		return false;
	}
	
	@Override
	public boolean canDeleteProducts() {
		return false;
	}
	
	@Override
	public boolean canSellProducts() {
		return true;
	}
	
	@Override
	public boolean canEditProducts() {
		return false;
	}
}
