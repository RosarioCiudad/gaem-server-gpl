
package coop.tecso.demoda.iface.model;




/**
 * @author tecso:
 * Este objeto solo posee un ID. 
 * Es generico y sirve para todos los casos que se necesite enviar algun ID como argumento
 * @version 2.0
 */
public class CommonKey extends Common {
	static final long serialVersionUID = 0;
	
	
	public CommonKey(String id) {
		this.setId(new Long(id));
	}
	
	public CommonKey(Long id) {
		this.setId(id);
	}
	
	
}
