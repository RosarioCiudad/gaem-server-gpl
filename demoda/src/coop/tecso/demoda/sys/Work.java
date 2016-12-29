/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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