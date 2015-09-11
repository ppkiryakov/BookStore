package bg.pragmatic.bookstore.model.exceptions;

@SuppressWarnings("serial")
public class SavingFailedException extends Exception {
	
	public SavingFailedException() {
		super();
	}
	
	public SavingFailedException(String message) {
		super(message);
	}
}
