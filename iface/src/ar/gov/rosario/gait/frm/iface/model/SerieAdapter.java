package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class SerieAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "serieAdapterVO";

	private SerieVO serie = new SerieVO();
	

	// Constructores
	public SerieAdapter() {
		super(FrmSecurityConstants.ABM_SERIE);
	}

	// Getters y Setters
	public SerieVO getSerie() {
		return serie;
	}

	public void setSerie(SerieVO serie) {
		this.serie = serie;
	}

	public String getName() {
		return NAME;
	}


}