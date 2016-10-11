package coop.tecso.demoda.http;

/**
 * Response Envelope Standard OpenPhoto API response envelope
 * 
 * Every API returns a JSON response adhering to the following format.
 * 
 * { 
 *   message: (string), 
 *   code: (int), 
 *   result: (mixed) 
 * }
 * 
 * Message
 * 
 * The message is a string which describes the action taken. It's purely for
 * informational purposes and should never be used in your code or relied on.
 *
 * Code
 * 
 * The code is an integer representing the status of the API call. Typically the
 * code value should be 200 but anything between 200 and 299 indicates a
 * successful response. The API, for example, will return a 202 response indicating
 * that the resource has been created.
 * 
 * Below are some common codes:
 * 
 * 200, The API call was successful 
 * 202, Resource was created successfully 
 * 403, Authentication failed when trying to complete the API call 
 * 404, The requested endpoint could not be found 
 * 500, An unknown error occured and hopefully the message has more information
 * 
 * Result
 * 
 * The result can be any simple or complex value. Consult the endpoint you're
 * using for information on what the result will be.
 **/
public class Reply<T> {
	public Reply(T result) {
		this.code = 200;
		this.message = "ok";
		this.result = result;
	}

	public Reply(Integer code, String message, T result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	final Integer code;
	final String message;
	final T result;
}
