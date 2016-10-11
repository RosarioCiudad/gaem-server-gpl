package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class NumeracionSearchPage extends GaitPageModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "numeracionSearchPageVO";
										
	private NumeracionVO numeracion = new NumeracionVO();

	private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();
	
	private List<SerieVO> listSerie = new ArrayList<>();

	// Constructores
	public NumeracionSearchPage() {
		super(FrmSecurityConstants.ABM_NUMERACION);
	}
	
	//getters y setters
	public String getName() {
		return NAME;
	}
	
	public NumeracionVO getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(NumeracionVO numeracion) {
		this.numeracion = numeracion;
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

}
