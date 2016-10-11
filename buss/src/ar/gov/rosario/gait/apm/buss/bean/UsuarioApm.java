package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_usuarioApm")
public class UsuarioApm extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String username;

	@Column
	private String usertoken;
	
	@Column(nullable= true)
	private String numeroInspector;
	
	@OneToMany()
	@JoinColumn(name="idUsuario")
	@Where(clause = "estado = 1")
	private List<PerfilAccesoUsuario> listPerfilAccesoUsuario;
	
	@OneToMany()
	@JoinColumn(name="idUsuario")
	@Where(clause = "estado = 1")
	private List<UsuarioApmDM> listUsuarioApmDM;
	
	@OneToMany()
	@JoinColumn(name="idUsuario")
	@Where(clause = "estado = 1")
	private List<UsuarioApmImpresora> listUsuarioApmImpresora;

	// Constructores
	public UsuarioApm() {
		super();
	}
	
	public UsuarioApm(Long id){
		super();
		setId(id);
	}
	
	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}
	
	public String getNumeroInspector() {
		return numeroInspector;
	}

	public void setNumeroInspector(String numeroInspector) {
		this.numeroInspector = numeroInspector;
	}
	
	public List<PerfilAccesoUsuario> getListPerfilAccesoUsuario() {
		return listPerfilAccesoUsuario;
	}

	public void setListPerfilAccesoUsuario(List<PerfilAccesoUsuario> listPerfilAccesoUsuario) {
		this.listPerfilAccesoUsuario = listPerfilAccesoUsuario;
	}

	public List<UsuarioApmDM> getListUsuarioApmDM() {
		return listUsuarioApmDM;
	}

	public void setListUsuarioApmDM(List<UsuarioApmDM> listUsuarioApmDM) {
		this.listUsuarioApmDM = listUsuarioApmDM;
	}

	public List<UsuarioApmImpresora> getListUsuarioApmImpresora() {
		return listUsuarioApmImpresora;
	}

	public void setListUsuarioApmImpresora(List<UsuarioApmImpresora> listUsuarioApmImpresora) {
		this.listUsuarioApmImpresora = listUsuarioApmImpresora;
	}
	
	// Metodos de Clase
	public static UsuarioApm getById(Long id) {
		return (UsuarioApm) ApmDAOFactory.getUsuarioApmDAO().getById(id);
	}
	
	public static UsuarioApm getByIdNull(Long id) {
		return (UsuarioApm) ApmDAOFactory.getUsuarioApmDAO().getByIdNull(id);
	}
	
	public static List<UsuarioApm> getList() {
		return ApmDAOFactory.getUsuarioApmDAO().getList();
	}
	
	public static List<UsuarioApm> getListActivos() {			
		return ApmDAOFactory.getUsuarioApmDAO().getListActiva();
	}
	
	public static UsuarioApm getByUserName(String username) {
		return ApmDAOFactory.getUsuarioApmDAO().getByUserName(username);
	}
	
	public static boolean canAccess(String username, String usertoken) {
		return ApmDAOFactory.getUsuarioApmDAO().canAccess(username, usertoken);
	}
	
	// Validaciones 
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}
		
		// Validaciones de Negocio

		return true;
	}

	public boolean validateUpdate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}
		
		// Validaciones de Negocio

		return true;		
	}

	public boolean validateDelete() {
		//limpiamos la lista de errores
		clearError();
	
//		//<#ValidateDelete#>
//		<#ValidateDelete.Bean#>
//			if (GenericDAO.hasReference(this, ${BeanRelacionado}.class, "usuarioApm")) {
//				addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
//								ApmError.${BEAN}_LABEL, ApmError.${BEAN_RELACIONADO}_LABEL );
//			}
//		<#ValidateDelete.Bean#>
//		//<#ValidateDelete#>
		
		if (hasError()) {
			return false;
		}

		return true;
	}
	
	private boolean validate() throws Exception {
		
		//	Validaciones        
		if (StringUtil.isNullOrEmpty(getUsername())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.USUARIOAPM_USERNAME);
		}
		
		if (StringUtil.isNullOrEmpty(getNombre())){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.USUARIOAPM_NOMBRE);
		}
		
		if (hasError()) {
			return false;
		}
		
		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addString("username");
		uniqueMap.addInteger("estado");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.USUARIOAPM_USERNAME);			
		}
		
		return true;
	}
	
	// Metodos de negocio
	
	/**
	 * Activa el UsuarioApm. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ApmDAOFactory.getUsuarioApmDAO().update(this);
	}

	/**
	 * Desactiva el UsuarioApm. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ApmDAOFactory.getUsuarioApmDAO().update(this);
	}
	
	/**
	 * Valida la activacion del UsuarioApm
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
	
	/**
	 * Valida la desactivacion del UsuarioApm
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
}