package ar.gov.rosario.gait.apm.iface.model;



import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.Tratamiento;

public class CampoValorOpcionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "campoValorOpcionVO";

	private Tratamiento tratamiento = Tratamiento.OpcionTodo;

	private String etiqueta;	
	private String valorDefault;
	private String tablaBusqueda;
	private SiNo obligatorio = SiNo.OpcionTodo;
	private SiNo soloLectura = SiNo.OpcionTodo;
	private CampoValorVO campoValor = new CampoValorVO();
	private String mascaraVisual;
	private String codigo;

	public CampoValorOpcionVO() {
		super();
	}
	
	public CampoValorOpcionVO(int id) {
		super();
		setId(new Long(id));
	}
	
	public CampoValorOpcionVO(int id, String descripcion) {
		super();
		setId(new Long(id));
		setEtiqueta(descripcion);
	}

	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getValorDefault() {
		return valorDefault;
	}
	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}
	public String getTablaBusqueda() {
		return tablaBusqueda;
	}
	public void setTablaBusqueda(String tablaBusqueda) {
		this.tablaBusqueda = tablaBusqueda;
	}
	public SiNo getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(SiNo obligatorio) {
		this.obligatorio = obligatorio;
	}
	public SiNo getSoloLectura() {
		return soloLectura;
	}
	public void setSoloLectura(SiNo soloLectura) {
		this.soloLectura = soloLectura;
	}
	public Tratamiento getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}
	public CampoValorVO getCampoValor() {
		return campoValor;
	}
	public void setCampoValor(CampoValorVO campoValor) {
		this.campoValor = campoValor;
	}
	public String getMascaraVisual() {
		return mascaraVisual;
	}
	public void setMascaraVisual(String mascaraVisual) {
		this.mascaraVisual = mascaraVisual;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}