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
package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import coop.tecso.demoda.iface.DemodaServiceException;

public class ApmAplicacionManager {

	private static Logger log = Logger.getLogger(ApmAplicacionManager.class);

	private static final ApmAplicacionManager INSTANCE = new ApmAplicacionManager();

	/**
	 * Constructor privado
	 */
	private ApmAplicacionManager() {

	}

	/**
	 * Devuelve unica instancia
	 */
	public static ApmAplicacionManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM Seccion
	public Seccion createSeccion(Seccion seccion) throws Exception {

		// Validaciones de negocio
		if (!seccion.validateCreate()) {
			return seccion;
		}

		ApmDAOFactory.getSeccionDAO().update(seccion);

		return seccion;
	}

	public Seccion updateSeccion(Seccion seccion) throws Exception {

		// Validaciones de negocio
		if (!seccion.validateUpdate()) {
			return seccion;
		}

		ApmDAOFactory.getSeccionDAO().update(seccion);

		return seccion;
	}

	public Seccion deleteSeccion(Seccion seccion) throws Exception {

		// Validaciones de negocio
		if (!seccion.validateDelete()) {
			return seccion;
		}

		ApmDAOFactory.getSeccionDAO().delete(seccion);

		return seccion;
	}
	// <--- ABM Seccion

	// ------> Dispositivo Movil
	public DispositivoMovil createDispositivoMovil(
			DispositivoMovil dispositivoMovil) throws Exception {

		// Validaciones de negocio
		if (!dispositivoMovil.validateCreate()) {
			return dispositivoMovil;
		}

		ApmDAOFactory.getDispositivoMovilDAO().update(dispositivoMovil);

		return dispositivoMovil;
	}

	public DispositivoMovil updateDispositivoMovil(
			DispositivoMovil dispositivoMovil) throws Exception {

		// Validaciones de negocio
		if (!dispositivoMovil.validateUpdate()) {
			return dispositivoMovil;
		}

		ApmDAOFactory.getDispositivoMovilDAO().update(dispositivoMovil);

		return dispositivoMovil;
	}

	public DispositivoMovil deleteDispositivoMovil(
			DispositivoMovil dispositivoMovil) throws Exception {

		// Validaciones de negocio
		if (!dispositivoMovil.validateDelete()) {
			return dispositivoMovil;
		}

		ApmDAOFactory.getDispositivoMovilDAO().delete(dispositivoMovil);

		return dispositivoMovil;
	}
	// <--- ABM Dispositivo Movil

	// ------> Campo
	public Campo createCampo(Campo campo) throws Exception {

		// Validaciones de negocio
		if (!campo.validateCreate()) {
			return campo;
		}

		ApmDAOFactory.getCampoDAO().update(campo);

		return campo;
	}

	public Campo updateCampo(Campo campo) throws Exception {

		// Validaciones de negocio
		if (!campo.validateUpdate()) {
			return campo;
		}

		ApmDAOFactory.getCampoDAO().update(campo);

		return campo;
	}

	public Campo deleteCampo(Campo campo) throws Exception {

		// Validaciones de negocio
		if (!campo.validateDelete()) {
			return campo;
		}

		ApmDAOFactory.getCampoDAO().delete(campo);

		return campo;
	}
	// <--- ABM Campo

	// ------> Campo Valor
	public CampoValor createCampoValor(CampoValor campoValor) throws Exception {

		// Validaciones de negocio
		if (!campoValor.validateCreate()) {
			return campoValor;
		}

		ApmDAOFactory.getCampoValorDAO().update(campoValor);

		return campoValor;
	}

	public CampoValor updateCampoValor(CampoValor campoValor) throws Exception {

		// Validaciones de negocio
		if (!campoValor.validateUpdate()) {
			return campoValor;
		}

		ApmDAOFactory.getCampoValorDAO().update(campoValor);

		return campoValor;
	}

	public CampoValor deleteCampoValor(CampoValor campoValor) throws Exception {

		// Validaciones de negocio
		if (!campoValor.validateDelete()) {
			return campoValor;
		}

		ApmDAOFactory.getCampoValorDAO().delete(campoValor);

		return campoValor;
	}
	// <--- ABM Campo VAlor

	// ------> Campo Valor Opcion
	public CampoValorOpcion createCampoValorOpcion(
			CampoValorOpcion campoValorOpcion) throws Exception {

		// Validaciones de negocio
		if (!campoValorOpcion.validateCreate()) {
			return campoValorOpcion;
		}

		ApmDAOFactory.getCampoValorDAO().update(campoValorOpcion);

		return campoValorOpcion;
	}

	public CampoValorOpcion updateCampoValorOpcion(
			CampoValorOpcion campoValorOpcion) throws Exception {

		// Validaciones de negocio
		if (!campoValorOpcion.validateUpdate()) {
			return campoValorOpcion;
		}

		ApmDAOFactory.getCampoValorDAO().update(campoValorOpcion);

		return campoValorOpcion;
	}

	public CampoValorOpcion deleteCampoValorOpcion(
			CampoValorOpcion campoValorOpcion) throws Exception {

		// Validaciones de negocio
		if (!campoValorOpcion.validateDelete()) {
			return campoValorOpcion;
		}

		ApmDAOFactory.getCampoValorDAO().delete(campoValorOpcion);

		return campoValorOpcion;
	}
	// <--- ABM Campo

	// ------> Aplicacion Perfil
	public AplicacionPerfil createAplicacionPerfil(
			AplicacionPerfil aplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfil.validateCreate()) {
			return aplicacionPerfil;
		}

		ApmDAOFactory.getAplicacionPerfilDAO().update(aplicacionPerfil);

		return aplicacionPerfil;
	}

	public AplicacionPerfil updateAplicacionPerfil(
			AplicacionPerfil aplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfil.validateUpdate()) {
			return aplicacionPerfil;
		}

		ApmDAOFactory.getAplicacionPerfilDAO().update(aplicacionPerfil);

		return aplicacionPerfil;
	}

	public AplicacionPerfil deleteAplicacionPerfil(
			AplicacionPerfil aplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfil.validateDelete()) {
			return aplicacionPerfil;
		}

		ApmDAOFactory.getAplicacionPerfilDAO().delete(aplicacionPerfil);

		return aplicacionPerfil;
	}
	// <--- ABM Aplicacion Perfil

	// ------> Aplicacion Perfil Seccion
	public AplicacionPerfilSeccion createAplicacionPerfilSeccion(
			AplicacionPerfilSeccion aplicacionPerfilSeccion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilSeccion.validateCreate()) {
			return aplicacionPerfilSeccion;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().update(
				aplicacionPerfilSeccion);

		return aplicacionPerfilSeccion;
	}

	public AplicacionPerfilSeccion updateAplicacionPerfilSeccion(
			AplicacionPerfilSeccion aplicacionPerfilSeccion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilSeccion.validateUpdate()) {
			return aplicacionPerfilSeccion;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().update(
				aplicacionPerfilSeccion);

		return aplicacionPerfilSeccion;
	}

	public AplicacionPerfilSeccion deleteAplicacionPerfilSeccion(
			AplicacionPerfilSeccion aplicacionPerfilSeccion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilSeccion.validateDelete()) {
			return aplicacionPerfilSeccion;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().delete(
				aplicacionPerfilSeccion);

		return aplicacionPerfilSeccion;
	}
	// <--- ABM Aplicacion Perfil

	// ------> Aplicacion Perfil Seccion Campo
	public AplPerfilSeccionCampo createAplPerfilSeccionCampo(
			AplPerfilSeccionCampo aplPerfilSeccionCampo) throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampo.validateCreate()) {
			return aplPerfilSeccionCampo;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoDAO().update(
				aplPerfilSeccionCampo);

		return aplPerfilSeccionCampo;
	}

	public AplPerfilSeccionCampo updateAplPerfilSeccionCampo(
			AplPerfilSeccionCampo aplPerfilSeccionCampo) throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampo.validateUpdate()) {
			return aplPerfilSeccionCampo;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoDAO().update(
				aplPerfilSeccionCampo);

		return aplPerfilSeccionCampo;
	}

	public AplPerfilSeccionCampo deleteAplPerfilSeccionCampo(
			AplPerfilSeccionCampo aplPerfilSeccionCampo) throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampo.validateDelete()) {
			return aplPerfilSeccionCampo;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoDAO().delete(
				aplPerfilSeccionCampo);

		return aplPerfilSeccionCampo;
	}
	// <--- ABM Aplicacion Perfil Campo

	// ------> Aplicacion Perfil Seccion Campo Valor
	public AplPerfilSeccionCampoValor createAplPerfilSeccionCampoValor(
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValor.validateCreate()) {
			return aplPerfilSeccionCampoValor;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().update(
				aplPerfilSeccionCampoValor);

		return aplPerfilSeccionCampoValor;
	}

	public AplPerfilSeccionCampoValor updateAplPerfilSeccionCampoValor(
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValor.validateUpdate()) {
			return aplPerfilSeccionCampoValor;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().update(
				aplPerfilSeccionCampoValor);

		return aplPerfilSeccionCampoValor;
	}

	public AplPerfilSeccionCampoValor deleteAplPerfilSeccionCampoValor(
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValor.validateDelete()) {
			return aplPerfilSeccionCampoValor;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().delete(
				aplPerfilSeccionCampoValor);

		return aplPerfilSeccionCampoValor;
	}
	// <--- ABM Aplicacion Perfil Valor

	// ------> Aplicacion Perfil Seccion Campo Valor Opcion
	public AplPerfilSeccionCampoValorOpcion createAplPerfilSeccionCampoValorOpcion(
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValorOpcion.validateCreate()) {
			return aplPerfilSeccionCampoValorOpcion;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().update(
				aplPerfilSeccionCampoValorOpcion);

		return aplPerfilSeccionCampoValorOpcion;
	}

	public AplPerfilSeccionCampoValorOpcion updateAplPerfilSeccionCampoValorOpcion(
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValorOpcion.validateUpdate()) {
			return aplPerfilSeccionCampoValorOpcion;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().update(
				aplPerfilSeccionCampoValorOpcion);

		return aplPerfilSeccionCampoValorOpcion;
	}

	public AplPerfilSeccionCampoValorOpcion deleteAplPerfilSeccionCampoValorOpcion(
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion)
					throws Exception {

		// Validaciones de negocio
		if (!aplPerfilSeccionCampoValorOpcion.validateDelete()) {
			return aplPerfilSeccionCampoValorOpcion;
		}

		ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().delete(
				aplPerfilSeccionCampoValorOpcion);

		return aplPerfilSeccionCampoValorOpcion;
	}
	// <--- ABM Aplicacion Perfil Valor Opcion

	// ------> Aplicacion Perfil Parametro
	public AplicacionPerfilParametro createAplicacionPerfilParametro(
			AplicacionPerfilParametro aplicacionPerfilParametro)
					throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilParametro.validateCreate()) {
			return aplicacionPerfilParametro;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().update(
				aplicacionPerfilParametro);

		return aplicacionPerfilParametro;
	}

	public AplicacionPerfilParametro updateAplicacionPerfilParametro(
			AplicacionPerfilParametro aplicacionPerfilParametro)
					throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilParametro.validateUpdate()) {
			return aplicacionPerfilParametro;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().update(
				aplicacionPerfilParametro);

		return aplicacionPerfilParametro;
	}

	public AplicacionPerfilParametro deleteAplicacionPerfilParametro(
			AplicacionPerfilParametro aplicacionPerfilParametro)
					throws Exception {

		// Validaciones de negocio
		if (!aplicacionPerfilParametro.validateDelete()) {
			return aplicacionPerfilParametro;
		}

		ApmDAOFactory.getAplicacionPerfilSeccionDAO().delete(
				aplicacionPerfilParametro);

		return aplicacionPerfilParametro;
	}
	// <--- ABM Aplicacion Perfil Parametro

	// ---> ABM TablaVersion
	public TablaVersion createTablaVersion(TablaVersion tablaVersion)
			throws Exception {

		// Validaciones de negocio
		if (!tablaVersion.validateCreate()) {
			return tablaVersion;
		}

		ApmDAOFactory.getTablaVersionDAO().update(tablaVersion);

		return tablaVersion;
	}

	public TablaVersion updateTablaVersion(TablaVersion tablaVersion)
			throws Exception {

		// Validaciones de negocio
		if (!tablaVersion.validateUpdate()) {
			return tablaVersion;
		}

		ApmDAOFactory.getTablaVersionDAO().update(tablaVersion);

		return tablaVersion;
	}

	public TablaVersion deleteTablaVersion(TablaVersion tablaVersion)
			throws Exception {

		// Validaciones de negocio
		if (!tablaVersion.validateDelete()) {
			return tablaVersion;
		}

		ApmDAOFactory.getTablaVersionDAO().delete(tablaVersion);

		return tablaVersion;
	}

	public static List<TablaVersion> getApmAplicacion(String codApp)
			throws Exception {
		try {
			List<TablaVersion> listTablaVersion = ApmDAOFactory
					.getTablaVersionDAO().getListByApp(codApp);

			return listTablaVersion;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}
	// <--- ABM TablaVersion

	// ------> Impresora
	public Impresora createImpresora(Impresora impresora) throws Exception {

		// Validaciones de negocio
		if (!impresora.validateCreate()) {
			return impresora;
		}

		ApmDAOFactory.getImpresoraDAO().update(impresora);

		return impresora;
	}

	public Impresora updateImpresora(Impresora impresora) throws Exception {

		// Validaciones de negocio
		if (!impresora.validateUpdate()) {
			return impresora;
		}

		ApmDAOFactory.getImpresoraDAO().update(impresora);

		return impresora;
	}

	public Impresora deleteImpresora(Impresora impresora) throws Exception {

		// Validaciones de negocio
		if (!impresora.validateDelete()) {
			return impresora;
		}

		ApmDAOFactory.getImpresoraDAO().delete(impresora);

		return impresora;
	}
	// <--- ABM Impresora

	// ------> Aplicacion
	public Aplicacion createAplicacion(Aplicacion aplicacion) throws Exception {

		// Validaciones de negocio
		if (!aplicacion.validateCreate()) {
			return aplicacion;
		}

		ApmDAOFactory.getAplicacionDAO().update(aplicacion);

		return aplicacion;
	}

	public Aplicacion updateAplicacion(Aplicacion aplicacion) throws Exception {

		// Validaciones de negocio
		if (!aplicacion.validateUpdate()) {
			return aplicacion;
		}

		ApmDAOFactory.getAplicacionDAO().update(aplicacion);

		return aplicacion;
	}

	public Aplicacion deleteAplicacion(Aplicacion aplicacion) throws Exception {

		// Validaciones de negocio
		if (!aplicacion.validateDelete()) {
			return aplicacion;
		}

		ApmDAOFactory.getAplicacionDAO().delete(aplicacion);

		return aplicacion;
	}
	// <--- ABM Aplicacion

	// ------> AplicacionParametro
	public AplicacionParametro createAplicacionParametro(
			AplicacionParametro aplicacionParametro) throws Exception {

		// Validaciones de negocio
		if (!aplicacionParametro.validateCreate()) {
			return aplicacionParametro;
		}

		ApmDAOFactory.getAplicacionParametroDAO().update(aplicacionParametro);

		return aplicacionParametro;
	}

	public AplicacionParametro updateAplicacionParametro(
			AplicacionParametro aplicacionParametro) throws Exception {

		// Validaciones de negocio
		if (!aplicacionParametro.validateUpdate()) {
			return aplicacionParametro;
		}

		ApmDAOFactory.getAplicacionParametroDAO().update(aplicacionParametro);

		return aplicacionParametro;
	}

	public AplicacionParametro deleteAplicacionParametro(
			AplicacionParametro aplicacionParametro) throws Exception {

		// Validaciones de negocio
		if (!aplicacionParametro.validateDelete()) {
			return aplicacionParametro;
		}

		ApmDAOFactory.getAplicacionParametroDAO().delete(aplicacionParametro);

		return aplicacionParametro;
	}
	// <--- ABM AplicacionParametro

	// ------> AplicacionBinarioVersion
	public AplicacionBinarioVersion createAplicacionBinarioVersion(
			AplicacionBinarioVersion aplicacionBinarioVersion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionBinarioVersion.validateCreate()) {
			return aplicacionBinarioVersion;
		}

		ApmDAOFactory.getAplicacionBinarioVersionDAO().update(
				aplicacionBinarioVersion);

		return aplicacionBinarioVersion;
	}

	public AplicacionBinarioVersion updateAplicacionBinarioVersion(
			AplicacionBinarioVersion aplicacionBinarioVersion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionBinarioVersion.validateUpdate()) {
			return aplicacionBinarioVersion;
		}

		ApmDAOFactory.getAplicacionBinarioVersionDAO().update(
				aplicacionBinarioVersion);

		return aplicacionBinarioVersion;
	}

	public AplicacionBinarioVersion deleteAplicacionBinarioVersion(
			AplicacionBinarioVersion aplicacionBinarioVersion) throws Exception {

		// Validaciones de negocio
		if (!aplicacionBinarioVersion.validateDelete()) {
			return aplicacionBinarioVersion;
		}

		ApmDAOFactory.getAplicacionBinarioVersionDAO().delete(
				aplicacionBinarioVersion);

		return aplicacionBinarioVersion;
	}
	// <--- ABM AplicacionBinarioVersion

	// ---> UsuarioApm
	public UsuarioApm createUsuarioApm(UsuarioApm usuarioApm) throws Exception {

		// Validaciones de negocio
		if (!usuarioApm.validateCreate()) {
			return usuarioApm;
		}

		ApmDAOFactory.getUsuarioApmDAO().update(usuarioApm);

		return usuarioApm;
	}

	public UsuarioApm updateUsuarioApm(UsuarioApm usuarioApm) throws Exception {

		// Validaciones de negocio
		if (!usuarioApm.validateUpdate()) {
			return usuarioApm;
		}

		ApmDAOFactory.getUsuarioApmDAO().update(usuarioApm);

		return usuarioApm;
	}

	public UsuarioApm deleteUsuarioApm(UsuarioApm usuarioApm) throws Exception {

		// Validaciones de negocio
		if (!usuarioApm.validateDelete()) {
			return usuarioApm;
		}

		ApmDAOFactory.getUsuarioApmDAO().delete(usuarioApm);

		return usuarioApm;
	}
	// <--- ABM UsuarioApm

	// ------> UsuarioApmDM
	public UsuarioApmDM createUsuarioApmDM(UsuarioApmDM usuarioApmDM)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmDM.validateCreate()) {
			return usuarioApmDM;
		}

		ApmDAOFactory.getUsuarioApmDMDAO().update(usuarioApmDM);

		return usuarioApmDM;
	}

	public UsuarioApmDM updateUsuarioApmDM(UsuarioApmDM usuarioApmDM)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmDM.validateUpdate()) {
			return usuarioApmDM;
		}

		ApmDAOFactory.getUsuarioApmDMDAO().update(usuarioApmDM);

		return usuarioApmDM;
	}

	public UsuarioApmDM deleteUsuarioApmDM(UsuarioApmDM usuarioApmDM)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmDM.validateDelete()) {
			return usuarioApmDM;
		}

		ApmDAOFactory.getUsuarioApmDMDAO().delete(usuarioApmDM);

		return usuarioApmDM;
	}
	// <--- ABM UsuarioApmDM

	// ------> PerfilAccesoUsuario
	public PerfilAccesoUsuario createPerfilAccesoUsuario(
			PerfilAccesoUsuario perfilAccesoUsuario) throws Exception {

		// Validaciones de negocio
		if (!perfilAccesoUsuario.validateCreate()) {
			return perfilAccesoUsuario;
		}

		ApmDAOFactory.getPerfilAccesoUsuarioDAO().update(perfilAccesoUsuario);

		return perfilAccesoUsuario;
	}

	public PerfilAccesoUsuario updatePerfilAccesoUsuario(
			PerfilAccesoUsuario perfilAccesoUsuario) throws Exception {

		// Validaciones de negocio
		if (!perfilAccesoUsuario.validateUpdate()) {
			return perfilAccesoUsuario;
		}

		ApmDAOFactory.getPerfilAccesoUsuarioDAO().update(perfilAccesoUsuario);

		return perfilAccesoUsuario;
	}

	public PerfilAccesoUsuario deletePerfilAccesoUsuario(
			PerfilAccesoUsuario perfilAccesoUsuario) throws Exception {

		// Validaciones de negocio
		if (!perfilAccesoUsuario.validateDelete()) {
			return perfilAccesoUsuario;
		}

		ApmDAOFactory.getPerfilAccesoUsuarioDAO().delete(perfilAccesoUsuario);

		return perfilAccesoUsuario;
	}
	// <--- ABM PerfilAccesoUsuario

	// ------> Aplicacion Tabla
	public AplicacionTabla createAplicacionTabla(AplicacionTabla aplicacionTabla)
			throws Exception {

		// Validaciones de negocio
		if (!aplicacionTabla.validateCreate()) {
			return aplicacionTabla;
		}

		ApmDAOFactory.getAplicacionTablaDAO().update(aplicacionTabla);

		return aplicacionTabla;
	}

	public AplicacionTabla updateAplicacionTabla(AplicacionTabla aplicacionTabla)
			throws Exception {

		// Validaciones de negocio
		if (!aplicacionTabla.validateUpdate()) {
			return aplicacionTabla;
		}

		ApmDAOFactory.getAplicacionTablaDAO().update(aplicacionTabla);

		return aplicacionTabla;
	}

	public AplicacionTabla deleteAplicacionTabla(AplicacionTabla aplicacionTabla)
			throws Exception {

		// Validaciones de negocio
		if (!aplicacionTabla.validateDelete()) {
			return aplicacionTabla;
		}

		ApmDAOFactory.getAplicacionTablaDAO().delete(aplicacionTabla);

		return aplicacionTabla;
	}

	public static List<AplicacionTabla> getApmAplicacionTabla(String codApp) throws Exception {
		try {
			List<AplicacionTabla> listTablaVersion =
					ApmDAOFactory.getAplicacionTablaDAO().getListByApp(codApp);

			return listTablaVersion;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}
	// <--- ABM Aplicacion Tabla

	// ---> ABM AplicacionTipoBinario
	public AplicacionTipoBinario createAplicacionTipoBinario(
			AplicacionTipoBinario aplicacionTipoBinario) throws Exception {

		// Validaciones de negocio
		if (!aplicacionTipoBinario.validateCreate()) {
			return aplicacionTipoBinario;
		}

		ApmDAOFactory.getAplicacionTipoBinarioDAO().update(aplicacionTipoBinario);

		return aplicacionTipoBinario;
	}

	public AplicacionTipoBinario updateAplicacionTipoBinario(
			AplicacionTipoBinario aplicacionTipoBinario) throws Exception {

		// Validaciones de negocio
		if (!aplicacionTipoBinario.validateUpdate()) {
			return aplicacionTipoBinario;
		}

		ApmDAOFactory.getAplicacionTipoBinarioDAO().update(aplicacionTipoBinario);

		return aplicacionTipoBinario;
	}

	public AplicacionTipoBinario deleteAplicacionTipoBinario(
			AplicacionTipoBinario aplicacionTipoBinario) throws Exception {

		// Validaciones de negocio
		if (!aplicacionTipoBinario.validateDelete()) {
			return aplicacionTipoBinario;
		}

		ApmDAOFactory.getAplicacionTipoBinarioDAO().delete(aplicacionTipoBinario);

		return aplicacionTipoBinario;
	}

	public static List<AplicacionTipoBinario> getApmAplicacionTipoBinario(String codApp) throws Exception {
		try {
			List<AplicacionTipoBinario> listAplicacionTipoBinario = 
					ApmDAOFactory.getAplicacionTipoBinarioDAO().getListByApp(codApp);

			return listAplicacionTipoBinario;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}
	// <--- ABM AplicacionTipoBinario

	// ------> PerfilAcceso
	public PerfilAcceso createPerfilAcceso(PerfilAcceso perfilAcceso) throws Exception {
		// Validaciones de negocio
		if (!perfilAcceso.validateCreate()) {
			return perfilAcceso;
		}

		ApmDAOFactory.getPerfilAccesoDAO().update(perfilAcceso);

		return perfilAcceso;
	}

	public PerfilAcceso updatePerfilAcceso(PerfilAcceso perfilAcceso) throws Exception {
		// Validaciones de negocio
		if (!perfilAcceso.validateUpdate()) {
			return perfilAcceso;
		}

		ApmDAOFactory.getPerfilAccesoDAO().update(perfilAcceso);

		return perfilAcceso;
	}

	public PerfilAcceso deletePerfilAcceso(PerfilAcceso perfilAcceso) throws Exception {
		// Validaciones de negocio
		if (!perfilAcceso.validateDelete()) {
			return perfilAcceso;
		}

		ApmDAOFactory.getPerfilAccesoDAO().delete(perfilAcceso);
		return perfilAcceso;
	}

	public static List<PerfilAcceso> getApmPerfilAcceso(String codApp) throws Exception {
		try {
			List<PerfilAcceso> listPerfilAcceso = ApmDAOFactory.getPerfilAccesoDAO().getListByApp(codApp);
			return listPerfilAcceso;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}
	// <--- ABM PerfilAcceso

	// ------> UsuarioApmImpresora
	public UsuarioApmImpresora createUsuarioApmImpresora(UsuarioApmImpresora usuarioApmImpresora)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmImpresora.validateCreate()) {
			return usuarioApmImpresora;
		}

		ApmDAOFactory.getUsuarioApmImpresoraDAO().update(usuarioApmImpresora);

		return usuarioApmImpresora;
	}

	public UsuarioApmImpresora updateUsuarioApmImpresora(UsuarioApmImpresora usuarioApmImpresora)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmImpresora.validateUpdate()) {
			return usuarioApmImpresora;
		}

		ApmDAOFactory.getUsuarioApmImpresoraDAO().update(usuarioApmImpresora);

		return usuarioApmImpresora;
	}

	public UsuarioApmImpresora deleteUsuarioApmImpresora(UsuarioApmImpresora usuarioApmImpresora)
			throws Exception {

		// Validaciones de negocio
		if (!usuarioApmImpresora.validateDelete()) {
			return usuarioApmImpresora;
		}

		ApmDAOFactory.getUsuarioApmImpresoraDAO().delete(usuarioApmImpresora);

		return usuarioApmImpresora;
	}
	// <--- ABM UsuarioApmImpresora
	
	// ------> PerfilAccesoAplicacion
	public PerfilAccesoAplicacion createPerfilAccesoAplicacion(PerfilAccesoAplicacion perfilAccesoAplicacion) throws Exception {
		// Validaciones de negocio
		if (!perfilAccesoAplicacion.validateCreate()) {
			return perfilAccesoAplicacion;
		}

		ApmDAOFactory.getPerfilAccesoAplicacionDAO().update(perfilAccesoAplicacion);

		return perfilAccesoAplicacion;
	}

	public PerfilAccesoAplicacion updatePerfilAccesoAplicacion(PerfilAccesoAplicacion perfilAccesoAplicacion) throws Exception {
		// Validaciones de negocio
		if (!perfilAccesoAplicacion.validateUpdate()) {
			return perfilAccesoAplicacion;
		}

		ApmDAOFactory.getPerfilAccesoAplicacionDAO().update(perfilAccesoAplicacion);

		return perfilAccesoAplicacion;
	}

	public PerfilAccesoAplicacion deletePerfilAccesoAplicacion(PerfilAccesoAplicacion perfilAccesoAplicacion) throws Exception {
		// Validaciones de negocio
		if (!perfilAccesoAplicacion.validateDelete()) {
			return perfilAccesoAplicacion;
		}

		ApmDAOFactory.getPerfilAccesoAplicacionDAO().delete(perfilAccesoAplicacion);
		return perfilAccesoAplicacion;
	}
	// <--- ABM PerfilAccesoAplicacion
}