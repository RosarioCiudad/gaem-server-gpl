<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarFormulario.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="frm" key="frm.formularioViewAdapter.title"/></h1>	

		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
		   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- Formulario -->
		<fieldset>
			<legend><bean:message bundle="frm" key="frm.formulario.title"/></legend>
			
			<table class="tabladatos">
				<tr>
					<td><label><bean:message bundle="frm" key="frm.formulario.numero.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.numero"/></td>
					
					<td><label><bean:message bundle="frm" key="frm.tipoFormulario.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.tipoFormulario.descripcion"/></td>
				</tr>
								
				<tr>
					<td><label><bean:message bundle="frm" key="frm.formulario.fechaInicio.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.fechaInicioView"/></td>
					<td><label><bean:message bundle="frm" key="frm.formulario.fechaCierre.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.fechaCierreView" /></td>
				</tr>
			</table>
		</fieldset>	
		<!-- Formulario -->
		
		<!-- Cierre -->
		<fieldset>
			<legend><bean:message bundle="frm" key="frm.formulario.cierre.title"/></legend>
			<table class="tabladatos">
				<tr>
					<td><label><bean:message bundle="frm" key="frm.formulario.estadoTipoFormulario.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.estadoTipoFormulario.descripcion"/></td>
					<logic:notEqual name="formularioAdapterVO" property="formulario.motivoCierreTipoFormulario.idView" value="">
						<td><label><bean:message bundle="frm" key="frm.formulario.motivoCierreTipoFormulario.label"/>: </label></td>
						<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.motivoCierreTipoFormulario.descripcion"/></td>
					</logic:notEqual>
					<logic:notEqual name="formularioAdapterVO" property="formulario.motivoAnulacionTipoFormulario.idView" value="">
						<td><label><bean:message bundle="frm" key="frm.formulario.motivoAnulacionTipoFormulario.label"/>: </label></td>
						<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.motivoAnulacionTipoFormulario.descripcion"/></td>
					</logic:notEqual>
				</tr>
				<tr>
					<td><label><bean:message bundle="frm" key="frm.formulario.usuarioCierre.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.usuarioCierre.nombre"/></td>
<%-- 					<td><label><bean:message bundle="frm" key="frm.formulario.numeroInspector.label"/>: </label></td> --%>
<%-- 					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.numeroInspectorView"/></td> --%>
				</tr>
<!-- 				<tr> -->
<%-- 					<td><label><bean:message bundle="frm" key="frm.formulario.reparticion.label"/>: </label></td> --%>
<%-- 					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.reparticionView" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td><label><bean:message bundle="apm" key="apm.dispositivoMovil.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.dispositivoMovil.descripcion"/></td>
					<td><label><bean:message bundle="apm" key="apm.dispositivoMovil.numeroIMEI.label"/>: </label></td>
					<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.dispositivoMovil.numeroIMEI"/></td>
				</tr>
			</table>
		</fieldset>	
		<!-- Cierre -->
		
		<logic:notEqual name="formularioAdapterVO" property="formulario.estado.bussId" value="1">
			<!-- Proceso -->
			<fieldset>
				<legend><bean:message bundle="frm" key="frm.formulario.proceso.title"/></legend>
				<table class="tabladatos">
					<tr>
						<td><label><bean:message bundle="frm" key="frm.formulario.codigoRespuesta.label"/>: </label></td>
						<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.codigoRespuesta"/></td>
						
						<td><label><bean:message bundle="frm" key="frm.formulario.fechaUltMdf.label"/>: </label></td>
						<td class="normal"><bean:write name="formularioAdapterVO" property="formulario.fechaUltMdfView"/></td>
					</tr>
					<tr>
						<td><label><bean:message bundle="frm" key="frm.formulario.observacion.label"/>: </label></td>
						<td class="normal" colspan="2"><bean:write name="formularioAdapterVO" property="formulario.observacion" /></td>
					</tr>
				</table>
			</fieldset>	
			<!-- Proceso -->
		</logic:notEqual>
		
		<!-- FormularioDetalle -->		
		<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">            
			<caption><bean:message bundle="frm" key="frm.formulario.listFormularioDetalle.label"/></caption>
	    	<tbody>
				<logic:notEmpty  name="formularioAdapterVO" property="formulario.listFormularioDetalle">	    	
			    	<tr>
						<th width="1">&nbsp;</th> <!-- Ver -->
						<th align="left"><bean:message bundle="apm" key="apm.campo.label"/></th>
						<th align="left"><bean:message bundle="frm" key="frm.formularioDetalle.codigo.label"/></th>
						<th align="left"><bean:message bundle="frm" key="frm.formularioDetalle.valor.label"/></th>						
					</tr>
					<logic:iterate id="FormularioDetalleVO" name="formularioAdapterVO" property="formulario.listFormularioDetalle">
						<tr>
							<!-- Ver -->
							<td>
								<logic:equal name="formularioAdapterVO" property="verFormularioDetalleEnabled" value="enabled">
									<logic:equal name="FormularioDetalleVO" property="verEnabled" value="enabled">
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verFormularioDetalle', '<bean:write name="FormularioDetalleVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</logic:equal>	
									<logic:notEqual name="FormularioDetalleVO" property="verEnabled" value="enabled">
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual name="formularioAdapterVO" property="verFormularioDetalleEnabled" value="enabled">										
									<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
								</logic:notEqual>
							</td>
							<td><bean:write name="FormularioDetalleVO" property="tipoFormularioDefSeccionCampo.campo.etiqueta"/>&nbsp;</td>
							<td><bean:write name="FormularioDetalleVO" property="tipoFormularioDefSeccionCampo.campo.codigo"/>&nbsp;</td>
							<logic:notEqual name="FormularioDetalleVO" property="imagen" value="">										
								<bean:define id="imagen" name="FormularioDetalleVO" property="imagen"/>
								<td class="normal"><img width="40%" src="data:image/png;base64,<%=imagen%>"></td>
							</logic:notEqual>
							<logic:equal name="FormularioDetalleVO" property="imagen" value="">										
								<td><bean:write name="FormularioDetalleVO" property="valor"/>&nbsp;</td>
							</logic:equal>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty  name="formularioAdapterVO" property="formulario.listFormularioDetalle">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
				</logic:empty>
			</tbody>
		</table>
		<!-- FormularioDetalle -->
		
		<!-- Recreacion Digital -->
		<fieldset>
			<legend><bean:message bundle="frm" key="frm.formulario.formularioDigital.label"/></legend>
			<table class="tabladatos">
				<tr>
					<bean:define id="formularioDigital" name="formularioAdapterVO" property="formulario.formularioDigital"/>
					<td></td>
					<td class="normal" colspan="2"><img src="data:image/png;base64,<%=formularioDigital%>"></td>
				</tr>
			</table>
		</fieldset>	
		<!-- Recreacion Digital -->

		<table class="tablabotones" style="width: 100%;">
	    	<tr>
  	    		<td align="left">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" style="width: 100%;">
					<logic:equal name="formularioAdapterVO" property="act" value="eliminar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('eliminar', '');">
							<bean:message bundle="base" key="abm.button.eliminar"/>
						</html:button>
					</logic:equal>
					<logic:equal name="formularioAdapterVO" property="act" value="activar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('activar', '');">
							<bean:message bundle="base" key="abm.button.activar"/>
						</html:button>
					</logic:equal>
					<logic:equal name="formularioAdapterVO" property="act" value="desactivar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('desactivar', '');">
							<bean:message bundle="base" key="abm.button.desactivar"/>
						</html:button>
					</logic:equal>
	   	    	</td>
	   	    </tr>
	   	 </table>
	    <input type="hidden" name="name"  value="<bean:write name='formularioAdapterVO' property='name'/>" id="name"/>
	   	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/> 	
	   	 		
		<input type="hidden" name="method" value=""/>
        <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
        <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

		<input type="hidden" name="selectedId" value=""/>
		<input type="hidden" name="isSubmittedForm" value="true"/>
		
	</html:form>
	<!-- Fin Tabla que contiene todos los formularios -->
