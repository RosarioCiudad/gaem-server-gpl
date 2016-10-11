package coop.tecso.demoda.http;

import java.util.Collections;
import java.util.Map;

public class RestRequest<T> {
	
	public RestRequest(Map<String, Object> parameters, T data) {
		super();
		this.parameters = Collections.unmodifiableMap(parameters);
		this.data = data;
	}
	
	public final Map<String,Object> parameters;
	public final T data;
}
