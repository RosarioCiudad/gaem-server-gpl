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
package ar.gov.rosario.gait.frm.buss.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import ar.gov.rosario.gait.frm.buss.bean.MotivoAnulacionTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.MotivoCierreTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Numeracion;
import ar.gov.rosario.gait.frm.buss.bean.Serie;
import ar.gov.rosario.gait.frm.buss.bean.Talonario;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.model.TalonarioVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.model.Estado;

public class FrmFormularioManager {

	private static Logger log = Logger.getLogger(FrmFormularioManager.class);

	private static final FrmFormularioManager INSTANCE = new FrmFormularioManager();

	/**
	 * Constructor privado
	 */
	private FrmFormularioManager() {

	}

	/**
	 * Devuelve unica instancia
	 */
	public static FrmFormularioManager getInstance() {
		return INSTANCE;
	}

	public void saveFormulario(Formulario formulario) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Session session = null;
		Transaction tx = null; 
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			
			// Verifico si el formulario fue transmitido
			Date fechaCierre = FrmDAOFactory.getFormularioDAO().getFechaCierreByNumero(formulario.getNumero());
			if(fechaCierre != null){
				if(DateUtil.isDateEqual(fechaCierre, formulario.getFechaCierre()))
					// Formulario Duplicado
					formulario.setEstado(Estado.FORMULARIO_DUPLICADO.getId());
				else
					// Numeracion Duplicada
					formulario.setEstado(Estado.NUMERO_DUPLICADO.getId());
			}
			
			// Guardo los Datos del Formulario
			FrmDAOFactory.getFormularioDAO().update(formulario);
			// Guardo los Datos de los detalles
			for (FormularioDetalle formularioDetalle : formulario.getListFormularioDetalle()) {
				FrmDAOFactory.getFormularioDetalleDAO().update(formularioDetalle);
			}

			// Desactivo Talonario
			formulario.getTalonario().desactivar();

			if (formulario.validateUpdate()) {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			} else {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			}
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
	}

	/**
	 * 
	 * 
	 */
	public static TalonarioVO getTalonario(String codTipoFormulario, 
			String deviceId, Integer cantidad) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Session session = null;
		Transaction tx = null; 
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			//
			TalonarioVO talonarioVO = new TalonarioVO();
			// TipoFormulario
			TipoFormulario tipoFormulario = TipoFormulario.getByCodigo(codTipoFormulario);
			// DispositivoMovil
			DispositivoMovil dispositivoMovil = DispositivoMovil.getByNumeroIMEI(deviceId);

			//TODO:
			// Verifico si existe un talonario vigente
			Talonario talonarioExistente = Talonario.getByTipoFormularioAndDispositivoMovil(tipoFormulario, dispositivoMovil);
			if(talonarioExistente != null){
				Integer valorRestante = talonarioExistente.getValorHasta() - talonarioExistente.getValor();
				if(valorRestante > 0){
					talonarioVO =  (TalonarioVO) talonarioExistente.toVO(1, false);
					log.debug(String.format("Talonario con Margen ID: %s - TipoFormulario: %s DM-IMEI: %s",
							talonarioExistente.getId(), tipoFormulario.getCodigo(), dispositivoMovil.getNumeroIMEI()));
					//
					return talonarioVO;
				}
				talonarioExistente.desactivar();
			}

			//			
			List<Talonario> listTalonario = Talonario.getListBy(tipoFormulario, dispositivoMovil);
			if(ListUtil.isNullOrEmpty(listTalonario)){
				Integer valorRestante = talonarioExistente.getValorHasta() - talonarioExistente.getValor();
				if(valorRestante > 0){
					talonarioVO =  (TalonarioVO) talonarioExistente.toVO(1, false);
					log.debug(String.format("Talonario con Margen ID: %s - TipoFormulario: %s DM-IMEI: %s",
							talonarioExistente.getId(), tipoFormulario.getCodigo(), dispositivoMovil.getNumeroIMEI()));
					//
					return talonarioVO;
				}
				talonarioExistente.desactivar();
			}

			// Obtengo la siguiente Numeracion
			Numeracion numeracion = Numeracion.getDisponibleByTipoFormulario(tipoFormulario);
			Integer valorRestante = numeracion.getValorRestante() - cantidad;
			// TODO:
			if(valorRestante < 0){
				// No deberia pasar nunca
				String msg = "Error al gestionar numeración: Asignación iválida";
				talonarioVO.addMessageValue(msg);
				return talonarioVO;
			}
			numeracion.setValorRestante(valorRestante);
			FrmDAOFactory.getNumeracionDAO().update(numeracion);

			//Talonario
			Talonario talonario = new Talonario();
			talonario.setTipoFormulario(tipoFormulario);
			talonario.setSerie(numeracion.getSerie());
			talonario.setFechaAsignacion(new Date());
			talonario.setDispositivoMovil(dispositivoMovil);
			talonario.setNumeracion(numeracion);
			talonario.setValor(1);
			talonario.setValorDesde(1);
			talonario.setValorHasta(cantidad);

			talonarioVO = (TalonarioVO) talonarioExistente.toVO(1,false);
			if (talonarioExistente.validateUpdate()) {
				FrmDAOFactory.getTalonarioDAO().update(talonarioExistente);
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				talonarioVO = (TalonarioVO) talonarioExistente.toVO(0, false);
			} else {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			}
			// Seteo
			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return talonarioVO;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}

	/**
	 * 
	 * 
	 */
	public static List<TalonarioVO> getListTalonario(String codTipoFormulario, 
			String deviceId, Integer cantidad) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Session session = null;
		Transaction tx = null; 
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			// TipoFormulario
			TipoFormulario tipoFormulario = TipoFormulario.getByCodigo(codTipoFormulario);
			// DispositivoMovil
			DispositivoMovil dispositivoMovil = DispositivoMovil.getByNumeroIMEI(deviceId);

			//			
			List<Talonario> listTalonario = Talonario.getListBy(tipoFormulario, dispositivoMovil);
			if(listTalonario.size() > 20){
				// NO CAMBIAR 
				return ListUtilBean.toVO(listTalonario, 1, false);
			}

			// Obtengo la siguiente Numeracion
			Numeracion numeracion = Numeracion.getDisponibleByTipoFormulario(tipoFormulario);
			Integer valorRestante = numeracion.getValorRestante() - cantidad;
			if(valorRestante < 0){
				// No deberia pasar nunca
				return null;
			}

			Integer valorDesde = numeracion.getValorHasta() - valorRestante - cantidad;
			Integer valorRef = valorDesde + 1;
			
			//
			for (int i = 0; i < cantidad; i++) {
				//Talonario
				Talonario talonario = new Talonario();
				talonario.setTipoFormulario(tipoFormulario);
				talonario.setSerie(numeracion.getSerie());
				talonario.setFechaAsignacion(new Date());
				talonario.setDispositivoMovil(dispositivoMovil);
				talonario.setNumeracion(numeracion);
				talonario.setValor(valorRef++);
				talonario.setValorDesde(valorDesde);
				talonario.setValorHasta(valorDesde + cantidad);
				

				FrmDAOFactory.getTalonarioDAO().update(talonario);

				listTalonario.add(talonario);
			}

			numeracion.setValorRestante(valorRestante);
			FrmDAOFactory.getNumeracionDAO().update(numeracion);

			if (numeracion.validateUpdate()) {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			} else {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			}
			// Seteo
			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return ListUtilBean.toVO(listTalonario, 1, false);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}

	// ---> ABM TipoFormulario
	public TipoFormulario createTipoFormulario(TipoFormulario tipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!tipoFormulario.validateCreate()) {
			return tipoFormulario;
		}

		ApmDAOFactory.getTipoFormularioDAO().update(tipoFormulario);

		return tipoFormulario;
	}

	public TipoFormulario updateTipoFormulario(TipoFormulario tipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!tipoFormulario.validateUpdate()) {
			return tipoFormulario;
		}

		ApmDAOFactory.getTipoFormularioDAO().update(tipoFormulario);

		return tipoFormulario;
	}

	public TipoFormulario deleteTipoFormulario(TipoFormulario tipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!tipoFormulario.validateDelete()) {
			return tipoFormulario;
		}

		ApmDAOFactory.getTipoFormularioDAO().delete(tipoFormulario);

		return tipoFormulario;
	}
	// <--- ABM TipoFormulario

	// ---> ABM Serie
	public Serie createSerie(Serie serie) throws Exception {

		// Validaciones de negocio
		if (!serie.validateCreate()) {
			return serie;
		}

		ApmDAOFactory.getSerieDAO().update(serie);

		return serie;
	}

	public Serie updateSerie(Serie serie) throws Exception {

		// Validaciones de negocio
		if (!serie.validateUpdate()) {
			return serie;
		}

		ApmDAOFactory.getSerieDAO().update(serie);

		return serie;
	}

	public Serie deleteSerie(Serie serie) throws Exception {

		// Validaciones de negocio
		if (!serie.validateDelete()) {
			return serie;
		}

		ApmDAOFactory.getSerieDAO().delete(serie);

		return serie;
	}
	// <--- ABM Serie

	// ---> ABM MotivoCierreTipoFormulario
	public MotivoCierreTipoFormulario createMotivoCierreTipoFormulario(MotivoCierreTipoFormulario motivoCierreTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoCierreTipoFormulario.validateCreate()) {
			return motivoCierreTipoFormulario;
		}

		ApmDAOFactory.getMotivoCierreTipoFormularioDAO().update(motivoCierreTipoFormulario);

		return motivoCierreTipoFormulario;
	}

	public MotivoCierreTipoFormulario updateMotivoCierreTipoFormulario(MotivoCierreTipoFormulario motivoCierreTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoCierreTipoFormulario.validateUpdate()) {
			return motivoCierreTipoFormulario;
		}

		ApmDAOFactory.getMotivoCierreTipoFormularioDAO().update(motivoCierreTipoFormulario);

		return motivoCierreTipoFormulario;
	}

	public MotivoCierreTipoFormulario deleteMotivoCierreTipoFormulario(MotivoCierreTipoFormulario motivoCierreTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoCierreTipoFormulario.validateDelete()) {
			return motivoCierreTipoFormulario;
		}

		ApmDAOFactory.getMotivoCierreTipoFormularioDAO().delete(motivoCierreTipoFormulario);

		return motivoCierreTipoFormulario;
	}
	// <--- ABM MotivoCierreTipoFormulario


	// ---> ABM MotivoAnulacionTipoFormulario
	public MotivoAnulacionTipoFormulario createMotivoAnulacionTipoFormulario(MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoAnulacionTipoFormulario.validateCreate()) {
			return motivoAnulacionTipoFormulario;
		}

		ApmDAOFactory.getTipoFormularioDAO().update(motivoAnulacionTipoFormulario);

		return motivoAnulacionTipoFormulario;
	}

	public MotivoAnulacionTipoFormulario updateMotivoAnulacionTipoFormulario(MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoAnulacionTipoFormulario.validateUpdate()) {
			return motivoAnulacionTipoFormulario;
		}

		ApmDAOFactory.getMotivoAnulacionTipoFormularioDAO().update(motivoAnulacionTipoFormulario);

		return motivoAnulacionTipoFormulario;
	}

	public MotivoAnulacionTipoFormulario deleteMotivoAnulacionTipoFormulario(MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario) throws Exception {

		// Validaciones de negocio
		if (!motivoAnulacionTipoFormulario.validateDelete()) {
			return motivoAnulacionTipoFormulario;
		}

		ApmDAOFactory.getMotivoAnulacionTipoFormularioDAO().delete(motivoAnulacionTipoFormulario);

		return motivoAnulacionTipoFormulario;
	}
	// <--- ABM MotivoAnulacionTipoFormulario

	// ---> ABM Numeracion
	public Numeracion createNumeracion(Numeracion numeracion) throws Exception {
		// Validaciones de negocio
		if (!numeracion.validateCreate()) {
			return numeracion;
		}

		ApmDAOFactory.getNumeracionDAO().update(numeracion);
		return numeracion;
	}

	public Numeracion updateNumeracion(Numeracion numeracion) throws Exception {
		// Validaciones de negocio
		if (!numeracion.validateUpdate()) {
			return numeracion;
		}

		ApmDAOFactory.getNumeracionDAO().update(numeracion);
		return numeracion;
	}

	public Numeracion deleteNumeracion(Numeracion numeracion) throws Exception {
		// Validaciones de negocio
		if (!numeracion.validateDelete()) {
			return numeracion;
		}

		ApmDAOFactory.getNumeracionDAO().delete(numeracion);
		return numeracion;
	}
	// <--- ABM Numeracion

	// ---> ABM Formulario
	public Formulario createFormulario(Formulario formulario) throws Exception {
		// Validaciones de negocio
		if (!formulario.validateCreate()) {
			return formulario;
		}

		FrmDAOFactory.getFormularioDAO().update(formulario);
		return formulario;
	}

	public Formulario updateFormulario(Formulario formulario) throws Exception {
		// Validaciones de negocio
		if (!formulario.validateUpdate()) {
			return formulario;
		}

		FrmDAOFactory.getFormularioDAO().update(formulario);
		return formulario;
	}

	public Formulario deleteFormulario(Formulario formulario) throws Exception {
		// Validaciones de negocio
		if (!formulario.validateDelete()) {
			return formulario;
		}

		FrmDAOFactory.getFormularioDAO().delete(formulario);
		return formulario;
	}
	// <--- ABM Formulario

	// ---> ABM FormularioDetalle
	public FormularioDetalle updateFormularioDetalle(FormularioDetalle formularioDetalle) throws Exception {
		// Validaciones de negocio
		if (!formularioDetalle.validateUpdate()) {
			return formularioDetalle;
		}

		FrmDAOFactory.getFormularioDetalleDAO().update(formularioDetalle);
		return formularioDetalle;
	}

	public FormularioDetalle deleteFormularioDetalle(FormularioDetalle formularioDetalle) throws Exception {
		// Validaciones de negocio
		if (!formularioDetalle.validateDelete()) {
			return formularioDetalle;
		}

		FrmDAOFactory.getFormularioDetalleDAO().delete(formularioDetalle);
		return formularioDetalle;
	}
	// <--- ABM FormularioDetalle

	// ---> ABM EstadoTipoFormulario
	public EstadoTipoFormulario createEstadoTipoFormulario(EstadoTipoFormulario estadoTipoFormulario)
			throws Exception {

		// Validaciones de negocio
		if (!estadoTipoFormulario.validateCreate()) {
			return estadoTipoFormulario;
		}

		FrmDAOFactory.getEstadoTipoFormularioDAO().update(estadoTipoFormulario);

		return estadoTipoFormulario;
	}

	public EstadoTipoFormulario updateEstadoTipoFormulario(EstadoTipoFormulario estadoTipoFormulario)
			throws Exception {

		// Validaciones de negocio
		if (!estadoTipoFormulario.validateUpdate()) {
			return estadoTipoFormulario;
		}

		FrmDAOFactory.getEstadoTipoFormularioDAO().update(estadoTipoFormulario);

		return estadoTipoFormulario;
	}

	public EstadoTipoFormulario deleteEstadoTipoFormulario(EstadoTipoFormulario estadoTipoFormulario)
			throws Exception {

		// Validaciones de negocio
		if (!estadoTipoFormulario.validateDelete()) {
			return estadoTipoFormulario;
		}

		FrmDAOFactory.getEstadoTipoFormularioDAO().delete(estadoTipoFormulario);

		return estadoTipoFormulario;
	}

	public static List<EstadoTipoFormulario> getFrmEstadoTipoFormulario(String codApp)
			throws Exception {
		try {
			List<EstadoTipoFormulario> listEstadoTipoFormulario = FrmDAOFactory
					.getEstadoTipoFormularioDAO().getListByApp(codApp);

			return listEstadoTipoFormulario;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}
	// <--- ABM EstadoTipoFormulario
}