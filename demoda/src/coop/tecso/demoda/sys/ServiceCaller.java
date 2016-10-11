package coop.tecso.demoda.sys;


public interface ServiceCaller {
	Object call(Class<?> klass, String methodName, Object params);
}