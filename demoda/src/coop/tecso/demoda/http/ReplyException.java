package coop.tecso.demoda.http;

public class ReplyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReplyException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	final private Integer code;
	final private String message;
	
	public Reply<?> reply() {
		return new Reply<Object>(code, message, "");
	}
}