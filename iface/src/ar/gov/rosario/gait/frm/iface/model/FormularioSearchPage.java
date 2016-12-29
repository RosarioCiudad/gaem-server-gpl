/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.model.ReportTableVO;
import coop.tecso.demoda.iface.model.ReportVO;
import coop.tecso.demoda.iface.model.ValueVO;

/**
 * SearchPage del Formulario
 * 
 * @author Tecso
 *
 */
public class FormularioSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "formularioSearchPageVO";
	
	private FormularioVO formulario= new FormularioVO();
	private List<ValueVO> listEstado = new ArrayList<>();
	private List<AreaVO> listArea = new ArrayList<>();
	private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();

	private Date fechaCierreDesde;
	private Date fechaCierreHasta;
	
	// Constructores
	public FormularioSearchPage() {       
       super(FrmSecurityConstants.ABM_FORMULARIO);        
    }
	
	// Getters y Setters
	public FormularioVO getFormulario() {
		return formulario;
	}
	public void setFormulario(FormularioVO formulario) {
		this.formulario = formulario;
	}           

    public String getName(){    
		return NAME;
	}
	
	public List<ValueVO> getListEstado() {
		return listEstado;
	}

	public void setListEstado(List<ValueVO> listEstado) {
		this.listEstado = listEstado;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}
	
	public List<TipoFormularioVO> getListTipoFormulario() {
		return listTipoFormulario;
	}

	public void setListTipoFormulario(List<TipoFormularioVO> listTipoFormulario) {
		this.listTipoFormulario = listTipoFormulario;
	}

	public Date getFechaCierreDesde() {
		return fechaCierreDesde;
	}

	public void setFechaCierreDesde(Date fechaCierreDesde) {
		this.fechaCierreDesde = fechaCierreDesde;
	}

	public Date getFechaCierreHasta() {
		return fechaCierreHasta;
	}

	public void setFechaCierreHasta(Date fechaCierreHasta) {
		this.fechaCierreHasta = fechaCierreHasta;
	}

	public void prepareReport(Long format) {

		ReportVO report = this.getReport(); // no instanciar una nueva
		report.setReportFormat(format);	
		report.setReportTitle("Listados de Formulario");
		report.setReportBeanName("Formulario");
		report.setReportFileName(this.getClass().getName());

        /* Codigo de ejemplo para mostrar filtros de Combos en los imprimir
		String desRecurso = "";

		RecursoVO recursoVO = (RecursoVO) ModelUtil.getBussImageModelByIdForList(
				this.getReclamo().getRecurso().getId(),
				this.getListRecurso());
		if (recursoVO != null){
			desRecurso = recursoVO.getDesRecurso();
		}
		report.addReportFiltro("Recurso", desRecurso);*/

		//C�digo
//		report.addReportFiltro("Codigo", this.getFormulario().getCodFormulario());
       //Descripci�n
//		report.addReportFiltro("Descripcion", this.getFormulario().getDesFormulario());
		

		ReportTableVO rtFormulario = new ReportTableVO("rtFormulario");
		rtFormulario.setTitulo("B\u00FAsqueda de Formulario");

		// carga de columnas
		rtFormulario.addReportColumn("C�digo","codFormulario");
		rtFormulario.addReportColumn("Descripci�n", "desFormulario");
		
		 
	    report.getReportListTable().add(rtFormulario);
	}
	
	// View getters
	public String getFechaCierreDesdeView() {
		return DateUtil.formatDate(fechaCierreDesde, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaCierreHastaView() {
		return DateUtil.formatDate(fechaCierreHasta, DateUtil.ddSMMSYYYY_MASK);
	}
}