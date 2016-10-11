package coop.tecso.demoda.db;

import java.util.ArrayList;
import java.util.List;

public class QueryMakerBase implements QueryMaker {
	
	List<String> parts = new ArrayList<String>();
	List<Object> params = new ArrayList<Object>();
	
	@Override
	public QueryMaker add(String part, Object... values) {
		parts.add(part);
		for(Object v : values) {
			params.add(v);
		}
		
		return this;
	}

	@Override
	public QueryMaker addIf(boolean cond, String part, Object... values) {
		if (cond) {
			add(part, values);
		}
		
		return this;
	}

	@Override
	public QueryMaker addIfNotNull(String part, Object... values) {
		for(Object v : values) {
			if (v != null) {
				add(part, values);
				break;
			}
		}
		
		return this;
	}

	@Override
	public List<String> parts() {
		return new ArrayList<String>(parts);
	}

	@Override
	public List<Object> parameters() {
		return new ArrayList<Object>(params);
	}

	@Override
	public String queryString() {
		StringBuilder sb = new StringBuilder();
		for(String part : parts) {
			sb.append(part).append(" ");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Object value : params) {
			sb.append(", ").append(value);
		}
		
		return queryString() + (sb.length() > 0 ? "; --" + sb.substring(1) : "");
	}
	
}