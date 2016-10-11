
package coop.tecso.demoda.iface.model;



public class UserContext extends Common {
	
	private static final long serialVersionUID = -7017840944558942495L;
	
	private Long idUsuarioSwe = 0L;
	private String userName = "";
    private String longUserName = "";
	private String ipRequest = "";
	private Boolean permiteWeb = false;

    private String idsAccionesModuloUsuario = ""; // Concatenacion de "," + id de las accion permitada para el usuario + ",".
    private String codsRolUsuario = ""; // Lista separada por "," de los codigos de/los roles del usuario
    private String accionSWE = "";
    private String metodoSWE = "";

	private Long idUsuarioGait;    
	private Long idArea;
	private String desArea = "";
	private Long idAplicacion;
	private String desAplicacion = "";
	private Long idTablaVersion;
	private String desTablaVersion = "";
	private Long idDireccion;
	private String desDireccion = "";
	private Boolean esDGI = false;

	private Boolean isAnonimo = false; // no anonimo
	private String urlReComenzar = "";
	
	
	// Constructor
    public UserContext() {
    }   
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getIpRequest() {
		return ipRequest;
	}
	public void setIpRequest(String ipRequest) {
		this.ipRequest = ipRequest;
	}

	public String getIdsAccionesModuloUsuario() {
		return this.idsAccionesModuloUsuario;
	}
	public void setIdsAccionesModuloUsuario(String idsAccionesModuloUsuario) {
		this.idsAccionesModuloUsuario = idsAccionesModuloUsuario;
	}

    public String getCodsRolUsuario() {
		return codsRolUsuario;
	}
    public void setCodsRolUsuario(String codsRolUsuario) {
		this.codsRolUsuario = codsRolUsuario;
	}

	public String getAccionSWE() {
		return accionSWE;
	}
	public void setAccionSWE(String accionActual) {
		this.accionSWE = accionActual;
	}

	public String getMetodoSWE() {
		return metodoSWE;
	}
	public void setMetodoSWE(String metodoActual) {
		this.metodoSWE = metodoActual;
	}

	public Long getIdUsuarioGait() {
		return idUsuarioGait;
	}
	public void setIdUsuarioGait(Long idUsuarioGait) {
		this.idUsuarioGait = idUsuarioGait;
	}

	public Long getIdArea() {
		return idArea;
	}
	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public String getDesArea() {
		return desArea;
	}

	public String getLongUserName() {
		return longUserName;
	}

	public void setLongUserName(String longUserName) {
		this.longUserName = longUserName;
	}
	
	public String getIsAnonimoView() {
		return isAnonimo ? "1" : "0";
	}
	
	public String getUrlReComenzar() {
		return urlReComenzar;
	}

	public void setUrlReComenzar(String urlReComenzar) {
		this.urlReComenzar = urlReComenzar;
	}

	public Boolean getIsAnonimo() {
		return isAnonimo;
	}

	public void setIsAnonimo(Boolean isAnonimo) {
		this.isAnonimo = isAnonimo;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getDesAplicacion() {
		return desAplicacion;
	}

	public void setDesAplicacion(String desAplicacion) {
		this.desAplicacion = desAplicacion;
	}

	public Long getIdTablaVersion() {
		return idTablaVersion;
	}

	public void setIdTablaVersion(Long idTablaVersion) {
		this.idTablaVersion = idTablaVersion;
	}

	public String getDesTablaVersion() {
		return desTablaVersion;
	}

	public void setDesTablaVersion(String desTablaVersion) {
		this.desTablaVersion = desTablaVersion;
	}

	// Metodos para utilizar en el seteo de permiso de Liquidacion Deuda y en otros lugares mas adelante
	public Boolean getEsAnonimo(){
		return isAnonimo;
	}
	
	
	public Boolean getEsAdmin(){
		if (getCodsRolUsuario() != null && getCodsRolUsuario().toLowerCase().contains(",admin,")){
			return true;
		} else {
			return false;
		}		
	}

	public Long getIdUsuarioSwe() {
		return idUsuarioSwe;
	}

	public void setIdUsuarioSwe(Long idUsuarioSwe) {
		this.idUsuarioSwe = idUsuarioSwe;
	}

	public Boolean getPermiteWeb() {
		return permiteWeb;
	}

	public void setPermiteWeb(Boolean permiteWeb) {
		this.permiteWeb = permiteWeb;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getDesDireccion() {
		return desDireccion;
	}

	public void setDesDireccion(String desDireccion) {
		this.desDireccion = desDireccion;
	}

	public Boolean getEsDGI() {
		return esDGI;
	}

	public void setEsDGI(Boolean esDGI) {
		this.esDGI = esDGI;
	}

	public void setDesArea(String desArea) {
		this.desArea = desArea;
	}
}