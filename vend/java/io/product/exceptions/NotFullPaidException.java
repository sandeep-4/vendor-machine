package vend.java.io.product.exceptions;

public class NotFullPaidException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public NotFullPaidException(String message) {
		super(message);	
	}
}
