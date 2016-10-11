package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class NumeracionAdapter extends GaitAdapterModel {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "numeracionAdapterVO";

	private NumeracionVO numeracion = new NumeracionVO();
	
	private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();
	
	private List<SerieVO> listSerie = new ArrayList<>();
	
	private Integer valorDesde=0;

	private Integer valorHasta=0;


	public NumeracionAdapter() {
		super(FrmSecurityConstants.ABM_NUMERACION);
	}
	
	// Getters y Setters
	
	public static String getName() {
		return NAME;
	}
	
	public List<TipoFormularioVO> getListTipoFormulario() {
		return listTipoFormulario;
	}

	public void setListTipoFormulario(List<TipoFormularioVO> listTipoFormulario) {
		this.listTipoFormulario = listTipoFormulario;
	}

	public List<SerieVO> getListSerie() {
		return listSerie;
	}

	public void setListSerie(List<SerieVO> listSerie) {
		this.listSerie = listSerie;
	}
	
	public NumeracionVO getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(NumeracionVO numeracion) {
		this.numeracion = numeracion;
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
	
}
