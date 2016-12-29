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
package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import coop.tecso.demoda.iface.model.ReportTableVO;
import coop.tecso.demoda.iface.model.ReportVO;

/**
 * SearchPage del Parametro
 * 
 * @author Tecso
 *
 */
public class ParametroSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "parametroSearchPageVO";
	
	private ParametroVO parametro= new ParametroVO();
	
	// Constructores
	public ParametroSearchPage() {       
       super(DefSecurityConstants.ABM_PARAMETRO);        
    }
	
	// Getters y Setters
	public ParametroVO getParametro() {
		return parametro;
	}
	public void setParametro(ParametroVO parametro) {
		this.parametro = parametro;
	}

	// View getters
	
	public String getName(){
		return NAME;
	}
	
	public void prepareReport(Long format) {
		 
		ReportVO report = this.getReport(); // no instanciar una nueva
		 report.setReportFormat(format);	
		 report.setReportTitle("Reporte de Parámetros");
		 report.setReportBeanName("Parametros");
		 report.setReportFileName(this.getClass().getName());
		 
		 // carga de filtros
		 report.addReportFiltro("Código", this.getParametro().getCodParam());
		 report.addReportFiltro("Descripción", this.getParametro().getDesParam());
		 
		 // Order by
		 report.setReportOrderBy("codParam ASC");
		 
	     ReportTableVO rtParametro = new ReportTableVO("Parametro");
	     rtParametro.setTitulo("Listado de Parámetros");
	     
		 // carga de columnas
	     rtParametro.addReportColumn("Código", "codParam");
	     rtParametro.addReportColumn("Descripción", "desParam");
	     rtParametro.addReportColumn("Valor", "valor");
	     
	     report.getReportListTable().add(rtParametro);
	}

}
