package ar.gov.rosario.gait.pro.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.pro.buss.dao.ProDAOFactory;
import ar.gov.rosario.gait.pro.iface.util.ProError;
import coop.tecso.adpcore.AdpProcess;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * Bean correspondiente a Proceso
 * Son los tipos de procesos del GAIT.
 * 
 * @author tecso
 */

@Entity
@Table(name = "pro_proceso")
public class Proceso extends BaseBO {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "proceso";
	
	@Column(name = "codProceso")
	private String codProceso;
	@Column(name = "desProceso")
	private String desProceso;
	@Column(name = "esAsincronico")
	private Integer esAsincronico;
	@ManyToOne()  
    @JoinColumn(name="idTipoEjecucion")
	private TipoEjecucion tipoEjecucion;
	@Column(name = "directorioInput")
	private String directorioInput;
	@Column(name = "cantPasos")
	private Long cantPasos;
	@ManyToOne()  
    @JoinColumn(name="idTipoProgEjec")
	private TipoProgEjec tipoProgEjec;
	@Column(name = "classForName")
	private String classForName;
	@Column(name = "spValidate")
	private String spValidate;
	@Column(name = "spExecute")
	private String spExecute;
	@Column(name = "spResume")
	private String spResume;
	@Column(name = "spCancel")
	private String spCancel;
	@Column(name = "ejecNodo")
	private String ejecNodo;
	@Column(name = "locked")
	private Integer locked;
	@Column(name = "cronExpression")
	private String cronExpression;
	
	@Column(name = "cantCorridasPerm")
	private Integer cantCorridasPerm;
	
	// Constructores
	public Proceso(){
		super();
	}
	// Getters y Setters
	public String getCodProceso(){
		return codProceso;
	}
	public void setCodProceso(String codProceso){
		this.codProceso = codProceso;
	}
	public String getDesProceso(){
		return desProceso;
	}
	public void setDesProceso(String desProceso){
		this.desProceso = desProceso;
	}
	public Integer getEsAsincronico(){
		return esAsincronico;
	}
	public void setEsAsincronico(Integer esAsincronico){
		this.esAsincronico = esAsincronico;
	}
	public TipoEjecucion getTipoEjecucion(){
		return tipoEjecucion;
	}
	public void setTipoEjecucion(TipoEjecucion tipoEjecucion){
		this.tipoEjecucion = tipoEjecucion;
	}
	public String getDirectorioInput(){
		return directorioInput;
	}
	public void setDirectorioInput(String directorioInput){
		this.directorioInput = directorioInput;
	}
	public Long getCantPasos(){
		return cantPasos;
	}
	public void setCantPasos(Long cantPasos){
		this.cantPasos = cantPasos;
	}
	public TipoProgEjec getTipoProgEjec(){
		return tipoProgEjec;
	}
	public void setTipoProgEjec(TipoProgEjec tipoProgEjec){
		this.tipoProgEjec = tipoProgEjec;
	}
	public String getClassForName(){
		return classForName;
	}
	public void setClasForName(String classForName){
		this.classForName = classForName;
	}
	public String getSpValidate(){
		return spValidate;
	}
	public void setSpValidate(String spValidate){
		this.spValidate = spValidate;
	}
	public String getSpExecute(){
		return spExecute;
	}
	public void setSpExecute(String spExecute){
		this.spExecute = spExecute;
	}
	public String getSpResume(){
		return spResume;
	}
	public void setSpResume(String spResume){
		this.spResume = spResume;
	}
	public String getSpCancel(){
		return spCancel;
	}
	public void setSpCancel(String spCancel){
		this.spCancel = spCancel;
	}

	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	
	public String getEjecNodo() {
		return ejecNodo;
	}
	public void setEjecNodo(String ejecNodo) {
		this.ejecNodo = ejecNodo;
	}
	public void setClassForName(String classForName) {
		this.classForName = classForName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Integer getCantCorridasPerm() {
		return cantCorridasPerm;
	}
	public void setCantCorridasPerm(Integer cantCorridasPerm) {
		this.cantCorridasPerm = cantCorridasPerm;
	}
	
	// Metodos de Clase
	public static Proceso getById(Long id) {
		return (Proceso) ProDAOFactory.getProcesoDAO().getById(id);  
	}
	
	public static Proceso getByIdNull(Long id) {
		return (Proceso) ProDAOFactory.getProcesoDAO().getByIdNull(id);
	}
	
	/** Recupera un proceso a partir de su codigo
	 * 
	 * @param id
	 * @return
	 */
	public static Proceso getByCodigo(String codigo) throws Exception {
		return ProDAOFactory.getProcesoDAO().getByCodigo(codigo);  
	}
	
	public static List<Proceso> getList() {
		return (List<Proceso>) ProDAOFactory.getProcesoDAO().getList();
	}
	
	public static List<Proceso> getListActivos() {			
		return ProDAOFactory.getProcesoDAO().getListActiva();
	}

	/**
	 * Obtiene todos los Codigos de los diferentes procesos cargados
	 * @return
	 */
	public static List<String> getListCodigos(){
		return (List<String>) ProDAOFactory.getProcesoDAO().getListCodigos();
	}
	
	// Metodos de Instancia
	// Validaciones
	/**
	 * Valida la creacion
	 * @author
	 */
	public boolean validateCreate() throws Exception{
		//limpiamos la lista de errores
		clearError();
		
		this.validate();
		
		if (hasError()) {
			return false;
		}

				
		return !hasError();
	}
	/**
	 * Valida la actualizacion
	 * @author
	 */
	public boolean validateUpdate() throws Exception{
		//limpiamos la lista de errores
		clearError();
		
		this.validate();
	
		if (hasError()) {
			return false;
		}

			
		return !hasError();
	}

	private boolean validate() throws Exception{
		
		//limpiamos la lista de errores
		clearError();
		
		//UniqueMap uniqueMap = new UniqueMap();

		//Validaciones de Requeridos
		if (StringUtil.isNullOrEmpty(getCodProceso())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_CODPROCESO);
		}
		if (StringUtil.isNullOrEmpty(getDesProceso())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_DESPROCESO);
		}
		if(getEsAsincronico()==null){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_ESASINCRONICO);
		}
		if (getTipoEjecucion()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_TIPOEJECUCION);
		}
		if (getCantPasos()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_CANTPASOS);
		}
		if (getTipoProgEjec()==null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_TIPOPROGEJEC);
		}
		
		if (hasError()) {
			return false;
		}
		
		
		if(getTipoEjecucion().getId().longValue()==AdpProcess.TIPO_PERIODIC){
			if(StringUtil.isNullOrEmpty(getCronExpression())){
				addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.PROCESO_CRONEXPRESSION);
			}
			
			if (hasError()) {
				return false;
			}
			
			//String validateResult = CronProcessor.validateCronExpression(getCronExpression());
			//if(!StringUtil.isNullOrEmpty(validateResult)) addRecoverableValueError(validateResult);
			
		}
		
		if (hasError()) {
			return false;
		}
		// Validaciones de Unicidad
		
		// Otras Validaciones

		return !hasError();
	}

	
	/**
	 * Valida la eliminacion
	 * @author
	 */
	public boolean validateDelete() {
		//limpiamos la lista de errores
		clearError();
		
		//Valida que no tenga registros de corrida asociados
		if(ProDAOFactory.getProcesoDAO().hasReferenceGen(this, Corrida.class, "proceso"))
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS, ProError.PROCESO_LABEL ,
					ProError.CORRIDA_LABEL);

		if (hasError()) {
			return false;
		}

		// Validaciones de Negocio
		
		
		return true;
	}

	// Metodos de negocio


}
