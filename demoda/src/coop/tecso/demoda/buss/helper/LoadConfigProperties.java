
package coop.tecso.demoda.buss.helper;

import java.util.Properties;

public class LoadConfigProperties {
	
	private String archivoProperties = "";
	
	
	private Properties props = new Properties();
	
	public LoadConfigProperties(String archivoProperties) {
		this.archivoProperties = archivoProperties;
	}

	public  void loadConfig() throws Exception {
		props.load(this.getClass().getClassLoader().getResourceAsStream(archivoProperties));
    }
	
	public  String getValorPropiedad(String propiedad) throws Exception {
		return (String) props.getProperty(propiedad);
    }

}
