package ar.gov.rosario.gait.apm.iface.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Aplicacion PErfil
 * 
 * @author tecso
 */

public class AplicacionBinarioVersionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionBinarioVersionAdapterVO";

	private Date fecha; 
	private AplicacionBinarioVersionVO aplicacionBinarioVersion = new AplicacionBinarioVersionVO();
	private List<AplicacionVO> listAplicacion = new ArrayList<>();
	private List<AplicacionTipoBinarioVO> listAplicacionTipoBinario = new ArrayList<>();
	
	// Constructores
	public AplicacionBinarioVersionAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}



	public AplicacionBinarioVersionVO getAplicacionBinarioVersion() {
		return aplicacionBinarioVersion;
	}

	public void setAplicacionBinarioVersion(AplicacionBinarioVersionVO aplicacionBinarioVersion) {
		this.aplicacionBinarioVersion = aplicacionBinarioVersion;
	}

	/**
	 * @return the listAplicacionTipoBinario
	 */
	public List<AplicacionTipoBinarioVO> getListAplicacionTipoBinario() {
		return listAplicacionTipoBinario;
	}

	/**
	 * @param listAplicacionTipoBinario the listAplicacionTipoBinario to set
	 */
	public void setListAplicacionTipoBinario(
			List<AplicacionTipoBinarioVO> listAplicacionTipoBinario) {
		this.listAplicacionTipoBinario = listAplicacionTipoBinario;
	}

	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}