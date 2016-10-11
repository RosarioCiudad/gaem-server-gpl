
package coop.tecso.demoda.buss.bean;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Bean con columna version
 */
@MappedSuperclass
public class VersionableBO extends BaseBO {

	private static final long serialVersionUID = 1L;

	@Column
	private Integer version; 
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
