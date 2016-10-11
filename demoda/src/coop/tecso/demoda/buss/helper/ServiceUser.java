

package coop.tecso.demoda.buss.helper;




public class ServiceUser {

	private static ThreadLocal usr = new ThreadLocal();
	
	public void setUsr(String usuario) {
		usr.set(usuario);
	}
	
	public String getUsr() {
		return (String)usr.get();
	}
	
	}
