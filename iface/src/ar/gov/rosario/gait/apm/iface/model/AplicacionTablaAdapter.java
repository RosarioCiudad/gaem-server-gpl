package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import coop.tecso.demoda.iface.model.SiNo;

public class AplicacionTablaAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionTablaAdapterVO";

	private AplicacionTablaVO aplicacionTabla = new AplicacionTablaVO();

	private List<SiNo> listSiNo = new ArrayList<SiNo>();
    private List<AplicacionVO> listAplicacion = new ArrayList<>(); 
    private List<TablaVersionVO> listTablaVersion = new ArrayList<>();
	
	// Constructores
	public AplicacionTablaAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONTABLA);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public AplicacionTablaVO getAplicacionTabla() {
		return aplicacionTabla;
	}

	public void setAplicacionTabla(AplicacionTablaVO aplicacionTabla) {
		this.aplicacionTabla = aplicacionTabla;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public List<TablaVersionVO> getListTablaVersion() {
		return listTablaVersion;
	}

	public void setListTablaVersion(List<TablaVersionVO> listTablaVersion) {
		this.listTablaVersion = listTablaVersion;
	}

}
