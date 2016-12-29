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
package ar.gov.rosario.gait.base.buss.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.bean.BaseManager;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.GenericAbstractDAO;
import coop.tecso.demoda.buss.dao.SqlUtil;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.model.Estado;


/**
 * Metodos para el uso de DAOs basados en Hibernate
 * @author tecso
 *
 */
public class GenericDAO extends GenericAbstractDAO {

	private SqlUtil sqlUtil = new SqlUtil(GaitHibernateUtil.getSqlDialect());

	public GenericDAO(Class boClass) {
		super(boClass);
	}

	protected Session currentSession() {
		return GaitHibernateUtil.currentSession();
	}

	/* -- update, create, delete para Versionables */

	/* Si es Versionable, lo marcamos en la seg */
	@Override
	public synchronized Long update(VersionableBO o) throws HibernateException {
		updateVersionable(o);
		return super.update(o);
	}

	/* Si es Versionable, los delete son logicos */
	@Override
	public synchronized Long delete(VersionableBO o) throws HibernateException {
		updateVersionable(o);
		return super.delete(o);
	}

	private void updateVersionable(VersionableBO o) {
		o.setVersion(BaseManager.getInstance().updateTablaVersion(bOClass.getSimpleName()));
	}
	/* -- FIN update, create, delete para Versionables */

	/**
	 *  Wrapper estatico de GenericAbstractDAO.hasReferenceGen() para los DAO de Gait
	 */
	static public boolean hasReference(BaseBO bo, Class joinClass, String joinProperty) {
		GenericDAO dao = new GenericDAO(BaseBO.class);
		return dao.hasReferenceGen(bo, joinClass, joinProperty);
	}

	/**
	 *  Wrapper estatico de GenericAbstractDAO.checkIsUniqueGen() para los DAO de Gait
	 */
	static public boolean checkIsUnique(BaseBO obj, UniqueMap um) throws Exception {
		GenericDAO dao = new GenericDAO(BaseBO.class);
		return dao.checkIsUniqueGen(obj, um);
	}

	/**
	 *  Wrapper estatico de GenericAbstractDAO.hasReferenceActivoGen() para los DAO de Gait
	 */
	static public boolean hasReferenceActivo(BaseBO bo, Class joinClass, String joinProperty) {
		GenericDAO dao = new GenericDAO(BaseBO.class);
		return dao.hasReferenceActivoGen(bo, joinClass, joinProperty);
	}

	/**
	 * Wrapper a SqlDate.sqlDate
	 */
	public String sqlDate(Date date) {
		return this.sqlUtil.sqlDate(date);
	}

	/**
	 * Wrapper a SqlDate.sqlDate
	 */
	public String sqlConcatDate(String anio, String mes, String dia) {
		return this.sqlUtil.sqlConcatDate(anio, mes, dia);
	}

	/**
	 * Wrapper a SqlDate.sqlDate
	 */
	public String sqlConcatDate(Integer anio, Integer mes, Integer dia) {
		return this.sqlUtil.sqlConcatDate("" + anio, "" + mes, "" + dia);
	}

	/**
	 * Wrapper a SqlDate.sqlDateTime
	 */
	public String sqlDateTime(Date date) {
		return this.sqlUtil.sqlDateTime(date);
	}

	@SuppressWarnings("unchecked")
	public static List<? extends VersionableBO> entityDelta(String className, Integer version) {
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery("FROM " + className + " t WHERE t.version > :version ORDER BY t.version ");
		query.setInteger("version", version);
		return query.list();
	}

	public static Object getEntity(String className, Integer id) {
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery("FROM " + className + " t WHERE t.estado= :estado AND t.id = :id ");
		query.setInteger("id", id);
		query.setInteger("estado", Estado.ACTIVO.getId());
		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public static List<? extends VersionableBO> entityDelta(String className, Integer version, String codigoApp, String join) {
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery("FROM " + className + " t WHERE t.version > :version AND " + join + "= :codigoApp ORDER BY t.version ");
		query.setInteger("version", version).setString("codigoApp", codigoApp);
		return query.list();
	}


}