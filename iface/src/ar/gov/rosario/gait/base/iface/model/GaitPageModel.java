package ar.gov.rosario.gait.base.iface.model;

import coop.tecso.demoda.iface.model.PageModel;


public class GaitPageModel extends PageModel {

	
	public GaitPageModel(String sweActionName){
		super(sweActionName);
		try {
			this.getReport().setReportFileSharePath(GaitParam.getString("FileSharePath"));
		} catch (Exception e) {
			this.getReport().setReportFileSharePath("/mnt/gait");
		}
	}
	
	// Application Specific View flags
	public String getVerEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getVerBussEnabled(), ACCION_VER, METODO_VER);
	}
	
	public String getAgregarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getAgregarBussEnabled(), ACCION_AGREGAR, METODO_AGREGAR);
	}

	public String getModificarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getModificarBussEnabled(), ACCION_MODIFICAR, METODO_MODIFICAR);
	}
	
	public String getEliminarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getEliminarBussEnabled(), ACCION_ELIMINAR, METODO_ELIMINAR);
	}
	
	public String getActivarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getActivarBussEnabled(), ACCION_ACTIVAR, METODO_ACTIVAR);
	}
	
	public String getDesactivarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getDesactivarBussEnabled(), ACCION_DESACTIVAR, METODO_DESACTIVAR);
	}
	
	public String getAltaEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getAltaBussEnabled(), ACCION_ALTA, METODO_ALTA);
	}

	public String getBajaEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getBajaBussEnabled(), ACCION_BAJA, METODO_BAJA);
	}
}