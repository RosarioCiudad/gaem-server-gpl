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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.MotivoAnulacionTipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class MotivoAnulacionTipoFormularioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(MotivoAnulacionTipoFormularioDAO.class);
	
	public MotivoAnulacionTipoFormularioDAO() {
		super(MotivoAnulacionTipoFormulario.class);
	}
	
	public MotivoAnulacionTipoFormulario getByCodigo(String codigo) {
		MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario;
		String queryString = "from MotivoAnulacionTipoFormulario t where t.codigo = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		motivoAnulacionTipoFormulario = (MotivoAnulacionTipoFormulario) query.uniqueResult();	

		return motivoAnulacionTipoFormulario; 
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoAnulacionTipoFormulario> getBySearchPage(MotivoAnulacionTipoFormularioSearchPage motivoAnulacionTipoFormularioSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM MotivoAnulacionTipoFormulario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//tratamiento
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion	)) LIKE ?", StringUtil.toUpperLike(motivoAnulacionTipoFormularioSearchPage.getMotivoAnulacionTipoFormulario().getDescripcion()));
		// tipoFormulario
		query.addIfNotNull(" AND t.tipoFormulario.id = ?", ModelUtil.bussId(motivoAnulacionTipoFormularioSearchPage.getMotivoAnulacionTipoFormulario().getTipoFormulario()));
		
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		List<MotivoAnulacionTipoFormulario> listTipoFormulario = (ArrayList<MotivoAnulacionTipoFormulario>) executeCountedSearch(query, motivoAnulacionTipoFormularioSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listTipoFormulario;
	}


}
