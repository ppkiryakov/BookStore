package bg.pragmatic.bookstore.model.exceptions;

@SuppressWarnings("serial")
public class LoadingFailedException extends Exception {
	
	public LoadingFailedException() {
		super();
	}
	
	public LoadingFailedException(String message) {
		super(message);
	}
}
