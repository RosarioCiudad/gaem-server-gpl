package coop.tecso.demoda.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class MapTo {
	final private Map<String, Object> from ;
	final private ArrayList<String> keys = new ArrayList<String>();
	final private ArrayList<Object> values = new ArrayList<Object>();
	final private ArrayList<Class<?>> types = new ArrayList<Class<?>>();
	
	final private To to = new To();
	
	public MapTo(Map<String, Object> from ) {
		super();
		this.from = from;
	}
	
	public static MapTo from(Map<String, Object> map) {
		return new MapTo(map);
	}

	public MapTo String(String key) {
		return to(key, to.String(from.get(key), null), String.class);
	}

	public MapTo Long(String key) {
		return to(key, to.Long(from.get(key), null), Long.class);
	}

	public MapTo Integer(String key) {
		return to(key, to.Integer(from.get(key), null), Integer.class);
	}

	public MapTo Double(String key) {
		return to(key, to.Double(from.get(key), null), Double.class);
	}

	public MapTo Float(String key) {
		return to(key, to.Float(from.get(key), null), Float.class);
	}

	public MapTo DateEpoch(String key) {
		return to(key, to.DateEpoch(from.get(key), null), Date.class);
	}

	public Object[] array() {
		return values.toArray();
	}

	@SuppressWarnings("unchecked")
	public <T> T construct(Class<T> klass) {
		try {
			Constructor<?> cstr = klass.getConstructor(types.toArray(new Class<?>[types.size()]));
			cstr.setAccessible(true);
			return (T) cstr.newInstance(values.toArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private MapTo to(String key, Object value, Class<?> klass) {
		keys.add(key);
		values.add(value);
		types.add(klass);
		return this;
	}
	
}
