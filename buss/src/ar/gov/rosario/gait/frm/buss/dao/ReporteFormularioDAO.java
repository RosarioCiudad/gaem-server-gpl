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
package ar.gov.rosario.gait.frm.buss.dao;

import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.iface.model.ReporteFormularioSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;

public class ReporteFormularioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(ReporteFormularioDAO.class);

	public ReporteFormularioDAO() {
		//TODO: De momento no hay entidad ReporteFormulario
		super(Formulario.class);
	}

	@SuppressWarnings("unchecked")
	public List<Formulario> getBySearchPage(ReporteFormularioSearchPage reporteFormularioSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		QueryMaker query = HibernateQueryMaker.make();

		query.add("FROM Formulario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado < 4 ");
		//		query.addIfNotNull(" AND t.estado = ?", reporteFormularioSearchPage.getFormulario().getEstado().getBussId());
		// fechaImpresionDesde
		//		query.addIfNotNull(" AND t.fechaImpresion >= ? ", StringUtil.toNull(sqlDate(formularioSearchPage.getFechaImpresionDesde())));
		query.addIf(!StringUtil.isNullOrEmpty(reporteFormularioSearchPage.getFechaImpresionDesdeView()),
				" AND t.fechaImpresion >=" +sqlDate(reporteFormularioSearchPage.getFechaImpresionDesde()));
		// fechaImpresionHasta
		//		query.addIfNotNull(" AND t.fechaImpresion <= ? ", StringUtil.toNull(sqlDate(formularioSearchPage.getFechaImpresionHasta())));
		query.addIf(null != reporteFormularioSearchPage.getFechaImpresionHasta(),
				" AND t.fechaImpresion <= " +sqlDate(reporteFormularioSearchPage.getFechaImpresionHasta()));
		//numero	
		query.addIfNotNull(" AND UPPER(TRIM(t.numero)) LIKE ?",
				StringUtil.toUpperLike(reporteFormularioSearchPage.getFormulario().getNumero()));
		//fecha cierre desde
		query.addIf(!StringUtil.isNullOrEmpty(reporteFormularioSearchPage.getFechaCierreDesdeView()),
				" AND t.fechaCierre >=" +sqlDate(reporteFormularioSearchPage.getFechaCierreDesde()));
		// fecha cierre hasta
		//por ser un timestamp, se agrega un día a la fechaHasta y se saca el = de la comparación
		if (!StringUtil.isNullOrEmpty(reporteFormularioSearchPage.getFechaCierreHastaView()))
			query.add(" AND t.fechaCierre < " +sqlDate(DateUtil.addDaysToDate(reporteFormularioSearchPage.getFechaCierreHasta(), 1)));
		// usuario
		query.addIfNotNull(" AND UPPER(TRIM(t.usuarioCierre.username)) LIKE ?",
				StringUtil.toUpperLike(reporteFormularioSearchPage.getFormulario().getUsuarioCierre().getUsername()));
		// area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(reporteFormularioSearchPage.getFormulario().getArea()));
		
		// AplicacionPerfil
		query.addIfNotNull(" AND t.tipoFormularioDef.id = ?",
				ModelUtil.bussId(reporteFormularioSearchPage.getFormulario().getTipoFormularioDef()));
		
		// Retiene Licencia
		query.addIfNotNull(" AND t.retieneLicencia = ?",
				reporteFormularioSearchPage.getFormulario().getRetieneLicencia().getBussId());
		
		// Impreso?
		if(reporteFormularioSearchPage.getTipoReporte() == 1){
			// Registros sin Imprimir
			query.add(" AND t.fechaImpresion IS NULL ");
		}else{
			// Registros Impresos
			query.add(" AND t.fechaImpresion IS NOT NULL ");
		}
		// Order by
		if(reporteFormularioSearchPage.isPaged())
			query.add(" ORDER BY t.fechaCierre DESC");
		else
			query.add(" ORDER BY t.numeroInspector, t.fechaCierre ");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<Formulario> list = executeCountedSearch(query, reporteFormularioSearchPage);
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return list;
	}
}