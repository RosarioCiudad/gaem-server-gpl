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
package ar.gov.rosario.gait.frm.iface.service;

import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.FormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleVO;
import ar.gov.rosario.gait.frm.iface.model.FormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.FormularioVO;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;


public interface IFrmFormularioService {
	
	// ---> ABM Formulario
	public FormularioSearchPage getFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public FormularioSearchPage getFormularioSearchPageResult(UserContext usercontext, FormularioSearchPage formularioSearchPage) throws DemodaServiceException;
	public FormularioSearchPage imprimirFormularioSearchPageResult(UserContext usercontext, FormularioSearchPage formularioSearchPage) throws DemodaServiceException;

	public FormularioAdapter getFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public FormularioAdapter getFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public FormularioAdapter getFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public FormularioVO createFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException;
	public FormularioVO updateFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException;
	public FormularioVO deleteFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException;
	public FormularioVO activarFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException;
	public FormularioVO desactivarFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException;	
	public FormularioAdapter imprimirFormulario(UserContext userContext, FormularioAdapter formularioAdapterVO) throws DemodaServiceException;
	// <--- ABM Formulario
	
	// ---> ABM FormularioDetalle
	public FormularioDetalleAdapter getFormularioDetalleAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public FormularioDetalleAdapter getFormularioDetalleAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public FormularioDetalleVO updateFormularioDetalle(UserContext userContext, FormularioDetalleVO formularioDetalleVO) throws DemodaServiceException;
	public FormularioDetalleVO deleteFormularioDetalle(UserContext userContext, FormularioDetalleVO formularioDetalleVO) throws DemodaServiceException;
	// <--- ABM FormularioDetalle
	
	// ---> ABM TipoFormulario
	public TipoFormularioSearchPage getTipoFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public TipoFormularioSearchPage getTipoFormularioSearchPageResult(UserContext usercontext, TipoFormularioSearchPage tipoFormularioSearchPage) throws DemodaServiceException;

	public TipoFormularioAdapter getTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public TipoFormularioAdapter getTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public TipoFormularioAdapter getTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public TipoFormularioVO createTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO ) throws DemodaServiceException;
	public TipoFormularioVO updateTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO ) throws DemodaServiceException;
	public TipoFormularioVO deleteTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO ) throws DemodaServiceException;
	// <--- ABM TipoFormulario
	
	// ---> ABM MotivoCierreTipoFormulario
	public MotivoCierreTipoFormularioSearchPage getMotivoCierreTipoFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public MotivoCierreTipoFormularioSearchPage getMotivoCierreTipoFormularioSearchPageResult(UserContext usercontext, MotivoCierreTipoFormularioSearchPage motivoCierreTipoFormularioSearchPage) throws DemodaServiceException;

	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public MotivoCierreTipoFormularioVO createMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO ) throws DemodaServiceException;
	public MotivoCierreTipoFormularioVO updateMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO ) throws DemodaServiceException;
	public MotivoCierreTipoFormularioVO deleteMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO ) throws DemodaServiceException;
	// <--- ABM MotivoCierreTipoFormulario
	
	// ---> ABM MotivoAnulacionTipoFormulario
	public MotivoAnulacionTipoFormularioSearchPage getMotivoAnulacionTipoFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public MotivoAnulacionTipoFormularioSearchPage getMotivoAnulacionTipoFormularioSearchPageResult(UserContext usercontext, MotivoAnulacionTipoFormularioSearchPage motivoAnulacionTipoFormularioSearchPage) throws DemodaServiceException;

	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public MotivoAnulacionTipoFormularioVO createMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO ) throws DemodaServiceException;
	public MotivoAnulacionTipoFormularioVO updateMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO ) throws DemodaServiceException;
	public MotivoAnulacionTipoFormularioVO deleteMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO ) throws DemodaServiceException;
	// <--- ABM MotivoAnulacionTipoFormulario
	
	// ---> ABM EstadoTipoFormulario
	public EstadoTipoFormularioSearchPage getEstadoTipoFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public EstadoTipoFormularioSearchPage getEstadoTipoFormularioSearchPageResult(UserContext usercontext, EstadoTipoFormularioSearchPage estadoTipoFormularioSearchPage) throws DemodaServiceException;

	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;

	public EstadoTipoFormularioVO createEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO ) throws DemodaServiceException;
	public EstadoTipoFormularioVO updateEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO ) throws DemodaServiceException;
	public EstadoTipoFormularioVO deleteEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO ) throws DemodaServiceException;
	// <--- ABM EstadoTipoFormulario
}