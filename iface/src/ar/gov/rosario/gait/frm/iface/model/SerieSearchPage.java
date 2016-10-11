package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

/**
 * SearchPage del Tipo Formulario
 * 
 * @author Tecso
 * 
 */

public class SerieSearchPage extends GaitPageModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "serieSearchPageVO";
	
	// Constructores
	public SerieSearchPage() {
		super(FrmSecurityConstants.ABM_SERIE);
	}
		
	// Getters y Setters
										
	public String getName() {
		return NAME;
	}

	private SerieVO serie = new SerieVO();

	public SerieVO getSerie() {
		return serie;
	}

	public void setSerie(SerieVO serie) {
		this.serie = serie;
	}

}
