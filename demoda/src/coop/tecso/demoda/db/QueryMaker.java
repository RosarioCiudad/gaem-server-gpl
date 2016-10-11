
package coop.tecso.demoda.db;

import java.util.List;

public interface QueryMaker {
	QueryMaker add(String part, Object... values);
	QueryMaker addIf(boolean cond, String part, Object... values);
	QueryMaker addIfNotNull(String part, Object... values);
	List<String> parts();
	String queryString();
	List<Object> parameters();
}