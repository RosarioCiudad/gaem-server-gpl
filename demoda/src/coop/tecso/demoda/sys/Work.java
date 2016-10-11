package coop.tecso.demoda.sys;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Work {
	
	public final String wid;
	private final Map<String, Object> attrs;
	
	public Work(Map<String, Object> attrs) {
		this.wid = Long.toString(wc.incrementAndGet());
		this.attrs = Collections.unmodifiableMap(attrs);
	}

	/* Utils */
	private static ThreadLocal<Work> _work = new ThreadLocal<Work>();	
	private static AtomicLong wc = new AtomicLong(1);
	static public Work get() {
		return _work.get();
	}
	
	static public void set(Work work) {
		_work.set(work);
	}

	@SuppressWarnings("unchecked")
	public static <T> T attr(String key) {
		return (T) _work.get().attrs.get(key);
	}
}