package ar.gov.rosario.gait.frm.iface.model;

import java.util.Date;

import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class TalonarioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "talonarioVO";
	
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();

	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
	
	private NumeracionVO numeracion = new NumeracionVO();

	private SerieVO serie = new SerieVO();
	
	private Integer valorDesde;

	private Integer valorHasta;

	private Integer valor;

	private Date fechaAsignacion;

	// Getters y setters
	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public NumeracionVO getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(NumeracionVO numeracion) {
		this.numeracion = numeracion;
	}

	public SerieVO getSerie() {
		return serie;
	}

	public void setSerie(SerieVO serie) {
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

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
}