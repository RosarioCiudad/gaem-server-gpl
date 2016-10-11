package ar.gov.rosario.gait.sidom.data;

/**
 * 
 * @author tecso.coop
 *
 */
public class Documento {
	
	private Integer id;
	private String url;
	private String extension;
	private String pid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}