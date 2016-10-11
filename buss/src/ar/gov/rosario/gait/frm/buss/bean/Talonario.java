package ar.gov.rosario.gait.frm.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.model.Estado;

@Entity
@Table(name="for_talonario")
public class Talonario extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDispositivoMovil")
	private DispositivoMovil dispositivoMovil;
	
	@ManyToOne(optional = false)  
	@JoinColumn(name = "idNumeracion")
	private Numeracion numeracion;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idSerie")
	private Serie serie;

	@Column
	private Integer valorDesde;

	@Column
	private Integer valorHasta;

	@Column
	private Integer valor;

	@Column
	private Date fechaAsignacion;

	// Constructores
	public Talonario() {
		super();
	}
	
	// Metodos de Clase
	public static Talonario getById(Long id) {
		return (Talonario) FrmDAOFactory.getTalonarioDAO().getById(id);
	}

	public static Talonario getByIdNull(Long id) {
		return (Talonario) FrmDAOFactory.getTalonarioDAO().getByIdNull(id);
	}
	
	public static Talonario getByTipoFormularioAndDispositivoMovil(
			TipoFormulario tipoFormulario, DispositivoMovil dispositivoMovil) {
		return FrmDAOFactory.getTalonarioDAO().getByTipoFormularioAndDispositivoMovil(tipoFormulario, dispositivoMovil);
	}
	
	public static List<Talonario> getListBy(TipoFormulario tipoFormulario, DispositivoMovil dispositivoMovil) {
		return FrmDAOFactory.getTalonarioDAO().getListBy(tipoFormulario, dispositivoMovil);
	}

	public static List<Talonario> getList() {
		return FrmDAOFactory.getTalonarioDAO().getList();
	}

	public static List<Talonario> getListActivos() {
		return FrmDAOFactory.getTalonarioDAO().getListActiva();
	}

	// Getters y Setters
	public Numeracion getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(Numeracion numeracion) {
		this.numeracion = numeracion;
	}

	public Serie getSerie() {
		return serie;
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

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public DispositivoMovil getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovil dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}
	
	/**
	 * Activa el Talonario Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		FrmDAOFactory.getTalonarioDAO().update(this);
	}

	/**
	 * Desactiva el Talonario. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		FrmDAOFactory.getTalonarioDAO().update(this);
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
	
	/**
	 * Valida la activacion del Talonario
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
	
	/**
	 * Valida la desactivacion del Talonario
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
	
	
	private boolean validate() throws Exception {
		//	Validaciones   
		if(getValorDesde() == null){
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ${Modulo}Error.${BEAN}_COD${BEAN} );
		}
		
		if (getValorHasta() == null){
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ${Modulo}Error.${BEAN}_DES${BEAN});
		}
		
		if (hasError()) {
			return false;
		}
		
		return true;
	}
}