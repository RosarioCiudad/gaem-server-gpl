package ar.gov.rosario.gait.seg.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.bean.Direccion;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.seg.buss.dao.SegDAOFactory;
import ar.gov.rosario.gait.seg.iface.util.SegError;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;


/**
 * Bean correspondiente a UsuarioGait
 * 
 * 
 * @author tecso
 */
@Entity
@Table(name = "seg_usuarioGAIT")
public class UsuarioGait extends BaseBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String usuarioGAIT;

	@Column
	private Date  fechaUltLogin; 

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDireccion")
	private Direccion direccion;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	// Constructores
	public UsuarioGait(){
		super();
	}

	public UsuarioGait(Long id){
		super();
		setId(id);
	}

	// Metodos de Clase
	public static UsuarioGait getById(Long id) {
		return (UsuarioGait) SegDAOFactory.getUsuarioGaitDAO().getById(id);
	}

	public static UsuarioGait getByIdNull(Long id) {
		return (UsuarioGait) SegDAOFactory.getUsuarioGaitDAO().getByIdNull(id);
	}

	public static List<UsuarioGait> getList() {
		return SegDAOFactory.getUsuarioGaitDAO().getList();
	}

	public static List<UsuarioGait> getListActivos() {			
		return SegDAOFactory.getUsuarioGaitDAO().getListActiva();
	}

	public static UsuarioGait getByUserName(String userName) throws Exception {
		return SegDAOFactory.getUsuarioGaitDAO().getByUserName(userName);
	}

	public String getUsuarioGAIT() {
		return usuarioGAIT;
	}

	public void setUsuarioGAIT(String usuarioGAIT) {
		this.usuarioGAIT = usuarioGAIT;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Date getFechaUltLogin() {
		return fechaUltLogin;
	}

	public void setFechaUltLogin(Date fechaUltLogin) {
		this.fechaUltLogin = fechaUltLogin;
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

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		if (StringUtil.isNullOrEmpty(getUsuarioGAIT())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, SegError.USUARIOGAIT_USUARIOGAIT);
		}

		if (this.getDireccion() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, DefError.DIRECCION_LABEL);
		}

		if (this.getArea() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, DefError.AREA_LABEL);
		}

		// TODO : agregar validacion que el usuario exista en SWE

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addString("usuarioGAIT");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, SegError.USUARIOGAIT_USUARIOGAIT);			
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	/**
	 * Activa el UsuarioGait. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		SegDAOFactory.getUsuarioGaitDAO().update(this);
	}

	/**
	 * Desactiva el UsuarioGait. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		SegDAOFactory.getUsuarioGaitDAO().update(this);
	}

	/**
	 * Valida la activacion del UsuarioGait
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del UsuarioGait
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}