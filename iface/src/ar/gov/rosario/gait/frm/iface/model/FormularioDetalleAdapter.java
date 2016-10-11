package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

/**
 * Adapter del FormularioDetalle
 * 
 * @author tecso
 */
public class FormularioDetalleAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "formularioDetalleAdapterVO";
	
    private FormularioDetalleVO formularioDetalle = new FormularioDetalleVO();
    
    // Constructores
    public FormularioDetalleAdapter(){
    	super(FrmSecurityConstants.ABM_FORMULARIODETALLE);
    }
    
    //  Getters y Setters
	public FormularioDetalleVO getFormularioDetalle() {
		return formularioDetalle;
	}

	public void setFormularioDetalle(FormularioDetalleVO formularioDetalleVO) {
		this.formularioDetalle = formularioDetalleVO;
	}

	public String getName(){
		return NAME;
	}
}