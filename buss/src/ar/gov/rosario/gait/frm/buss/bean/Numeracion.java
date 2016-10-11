package ar.gov.rosario.gait.frm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.model.Estado;

@Entity
@Table(name="for_numeracion")
public class Numeracion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idSerie")
	private Serie serie;

	@Column(nullable = false)
	private Integer valorDesde;

	@Column(nullable = false)
	private Integer valorHasta;

	@Column(nullable = false)
	private Integer valorRestante;


	// Constructores
	public Numeracion() {
		super();
	}

	// Metodos de Clase
	public static Numeracion getById(Long id) {
		return (Numeracion) FrmDAOFactory.getNumeracionDAO().getById(id);
	}

	public static Numeracion getByIdNull(Long id) {
		return (Numeracion) FrmDAOFactory.getNumeracionDAO().getByIdNull(id);
	}

	public static Numeracion getDisponibleByTipoFormulario(TipoFormulario tipoFormulario) {
		return FrmDAOFactory.getNumeracionDAO().getDisponibleByTipoFormulario(tipoFormulario);
	}

	public static List<Numeracion> getList() {
		return FrmDAOFactory.getNumeracionDAO().getList();
	}

	public static List<Numeracion> getListActivos() {			
		return FrmDAOFactory.getNumeracionDAO().getListActiva();
	}


	// Getters y Setters
	public Serie getSerie() {
		return serie;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public Integer getValorDesde() {
		return valorDesde;
	}

	public void setValorDesde(Integer valorDesde) {
		this.valorDesde = valorDesde;
	}

	public Integer getValorHasta() {
		return valorHasta;
	}

	public void setValorHasta(Integer valorHasta) {
		this.valorHasta = valorHasta;
	}

	public Integer getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(Integer valorRestante) {
		this.valorRestante = valorRestante;
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

		//	Validaciones        
		//		if (StringUtil.isNullOrEmpty(getCodNumeracion())) {
		//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ${Modulo}Error.${BEAN}_COD${BEAN} );
		//		}
		if (getTipoFormulario()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					FrmError.NUMERACION_TIPOFORMULARIO);
		}
		
		if (getSerie()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					FrmError.NUMERACION_SERIE);
		}
		
		if(getValorDesde() < 0){
			addRecoverableError(BaseError.MSG_VALORMENORQUECERO,
					FrmError.NUMERACION_VALORDESDE);
		}
		
		if(getValorHasta() < 0){
			addRecoverableError(BaseError.MSG_VALORMENORQUECERO,
					FrmError.NUMERACION_VALORHASTA);
		}

		
		if(getValorDesde() > getValorHasta()){
			addRecoverableError(BaseError.MSG_VALORMAYORQUE,
					FrmError.NUMERACION_VALORDESDE,FrmError.NUMERACION_VALORHASTA);
		}


		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		//		UniqueMap uniqueMap = new UniqueMap();
		//		uniqueMap.addString("codNumeracion");
		//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
		//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ${Modulo}Error.${BEAN}_COD${BEAN});			
		//		}

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el Numeracion. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		FrmDAOFactory.getNumeracionDAO().update(this);
	}

	/**
	 * Desactiva el Numeracion. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		FrmDAOFactory.getNumeracionDAO().update(this);
	}

	/**
	 * Valida la activacion del Numeracion
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del Numeracion
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}