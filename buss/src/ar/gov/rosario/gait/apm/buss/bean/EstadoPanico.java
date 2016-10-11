package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.model.Estado;

@Entity
@Table(name="apm_estadoPanico")
public class EstadoPanico extends VersionableBO {

	private static final long serialVersionUID = 1L;
	
	public static final Long ID_PENDIENTE = 1L;
	public static final Long ID_ATENDIDO = 2L;
	public static final Long ID_ANULADO = 3L;

	@Column
	private String transiciones;

	@Column
	private Integer esInicial;
	
	@Column(nullable = false)
	private String descripcion;

	// Constructores
	public EstadoPanico() {
		super();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(String transiciones) {
		this.transiciones = transiciones;
	}

	public Integer getEsInicial() {
		return esInicial;
	}

	public void setEsInicial(Integer esInicial) {
		this.esInicial = esInicial;
	}

	// Metodos de Clase
	public static EstadoPanico getById(Long id) {
		return (EstadoPanico) ApmDAOFactory.getEstadoPanicoDAO().getById(id);
	}

	public static EstadoPanico getByIdNull(Long id) {
		return (EstadoPanico) ApmDAOFactory.getEstadoPanicoDAO().getByIdNull(id);
	}

	public static List<EstadoPanico> getList() {
		return ApmDAOFactory.getEstadoPanicoDAO().getList();
	}

	public static List<EstadoPanico> getListActivos() {			
		return ApmDAOFactory.getEstadoPanicoDAO().getListActiva();
	}

	public static List<EstadoPanico> getListTransicionesForEstado(EstadoPanico estadoPanico) {		
		return ApmDAOFactory.getEstadoPanicoDAO().getListTransicionesForEstado(estadoPanico);
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

//		if (GenericDAO.hasReference(this, HisEstPan.class, "estadoPanico")) {
//			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
//					ApmError.${BEAN}_LABEL, ApmError.${BEAN_RELACIONADO}_LABEL );
//		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		//	Validaciones        
//		if (StringUtil.isNullOrEmpty(getCodEstadoPanico())) {
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_COD${BEAN} );
//		}
//
//		if (StringUtil.isNullOrEmpty(getDesEstadoPanico())){
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_DES${BEAN});
//		}

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
//		UniqueMap uniqueMap = new UniqueMap();
//		uniqueMap.addString("codEstadoPanico");
//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.${BEAN}_COD${BEAN});			
//		}

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el EstadoPanico. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ApmDAOFactory.getEstadoPanicoDAO().update(this);
	}

	/**
	 * Desactiva el EstadoPanico. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ApmDAOFactory.getEstadoPanicoDAO().update(this);
	}

	/**
	 * Valida la activacion del EstadoPanico
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del EstadoPanico
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}