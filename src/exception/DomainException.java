package exception;

public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// Exce��o personalizada
	public DomainException(String msg) {
		super(msg);
	}
}
