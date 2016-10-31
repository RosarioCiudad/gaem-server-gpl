package ar.gov.rosario.gait.base.iface.model;

import coop.tecso.demoda.iface.model.AdapterModel;

public class GaitAdapterModel extends AdapterModel {
	
	private static final long serialVersionUID = 1L;

	public GaitAdapterModel(){
		try {
			this.getReport().setReportFileSharePath(GaitParam.getString("FileSharePath"));
		} catch (Exception e) {
			this.getReport().setReportFileSharePath("/mnt/gaem");
		}		
	}
	
	public GaitAdapterModel(String sweActionName){
		super(sweActionName);
		try {
			this.getReport().setReportFileSharePath(GaitParam.getString("FileSharePath"));
		} catch (Exception e) {
			this.getReport().setReportFileSharePath("/mnt/gaem");
		}		
	}
	
	// Application Specific View flags
	public String getModificarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getModificarBussEnabled(), ACCION_MODIFICAR, METODO_MODIFICAR);
	}

	public String getModificarEstadoEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getModificarEstadoBussEnabled(), ACCION_MODIFICAR_ESTADO, METODO_MODIFICAR_ESTADO);
	}
	
	public String getEliminarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getEliminarBussEnabled(), ACCION_ELIMINAR, METODO_ELIMINAR);
	}

	public String getAltaEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getAltaBussEnabled(), ACCION_ALTA, METODO_ALTA);
	}

	public String getBajaEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getBajaBussEnabled(), ACCION_BAJA, METODO_BAJA);
	}
	
	public String getAgregarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getAgregarBussEnabled(), ACCION_AGREGAR, METODO_AGREGAR);
	}
	
	public String getVerEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getVerBussEnabled(), ACCION_VER, METODO_VER);
	}

	public String getModificarEncabezadoEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getModificarEncabezadoBussEnabled(), ACCION_MODIFICAR_ENCABEZADO, METODO_MODIFICAR );
	}
	
}
