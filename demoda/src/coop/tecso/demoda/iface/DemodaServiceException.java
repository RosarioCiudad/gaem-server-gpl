
package coop.tecso.demoda.iface;



public class DemodaServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public DemodaServiceException() {
		super();
	}

	public DemodaServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DemodaServiceException(String message) {
		super(message);
	}

	public DemodaServiceException(Throwable cause) {
		super(cause);
	}
}
