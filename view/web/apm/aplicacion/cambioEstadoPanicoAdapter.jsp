<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarCambioEstadoPanico.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.panicoAdapter.title"/></h1>	

	<table class="tablabotones" style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Info Panico -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.panico.label"/></legend>
			<table class="tabladatos">
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.fecha.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.fechaPanicoView"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.hora.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.horaPanicoView"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.area.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.area.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.area.direccion.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.area.direccion.descripcion"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.descripcion.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.dispositivoMovil.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.numeroSerie.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.dispositivoMovil.numeroSerie"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.estado.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.estadoPanico.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.numeroLinea.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.dispositivoMovil.numeroLinea"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.inspector.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.usuarioPanico.nombre"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.origen.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.origen"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.longitud.label"/>: </label></td>
					<td class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.longitudView"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.latitud.label"/>: </label></td>
					<td class="normal"><bean:write  name="cambioEstadoPanicoAdapterVO" property="panico.latitudView"/></td>
				</tr>
		</table>
	</fieldset>
		 	
		<!-- HisEstPan -->		
		<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">            
			<caption><bean:message bundle="apm" key="apm.panico.listHisEstPan.label"/></caption>
	    	<tbody>
				<logic:notEmpty  name="cambioEstadoPanicoAdapterVO" property="panico.listHisEstPan">	    	
			    	<tr>
						<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.fecha.label"/></th>
						<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.hora.label"/></th>
						<th align="left"><bean:message bundle="base" key="base.estado.label"/></th>
						<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.usuario.label"/></th>								
						<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.observaciones.label"/></th>								
					</tr>
					<logic:iterate id="HisEstPanVO" name="cambioEstadoPanicoAdapterVO" property="panico.listHisEstPan">
						<tr>
							<td><bean:write name="HisEstPanVO" property="fechaView"/>&nbsp;</td>
							<td><bean:write name="HisEstPanVO" property="horaView"/>&nbsp;</td>
							<td><bean:write name="HisEstPanVO" property="estadoPanico.descripcion"/>&nbsp;</td>
							<td><bean:write name="HisEstPanVO" property="usuario"/>&nbsp;</td>
							<td><bean:write name="HisEstPanVO" property="observaciones"/>&nbsp;</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty  name="cambioEstadoPanicoAdapterVO" property="panico.listHisEstPan">
					<tr>
						<td align="center">
							<bean:message bundle="base" key="base.noExistenRegitros"/>
						</td>
					</tr>
				</logic:empty>
			</tbody>
		</table>
		<!-- HisEstPan -->
	
	<!-- Cambio de Estado -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.panicoAdapter.nuevoEstado.title"/></legend>
		<table style="width: 100%;" class="tabladatos">
			<tr>
				<!-- Estado Actual -->
				<td width="50%"><label><bean:message bundle="apm" key="apm.panicoAdapter.estadoActual.label"/>: </label></td>
				<td width="50%" class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="panico.estadoPanico.descripcion"/></td>					
				<!-- Fecha -->
				<td width="50%"><label><bean:message bundle="apm" key="apm.hisEstPan.fecha.label"/>: </label></td>
				<td width="50%" class="normal"><bean:write name="cambioEstadoPanicoAdapterVO" property="hisEstPan.fechaView"/></td>
			</tr>
			<tr>
				<!-- Observaciones -->
				<td><label><bean:message bundle="apm" key="apm.hisEstPan.observaciones.label"/>: </label></td>
				<td class="normal" colspan="3">
					<html:textarea name="cambioEstadoPanicoAdapterVO" property="hisEstPan.observaciones"
						 style="width: 500px; height: 150px;" cols="80" rows="10"/>
				</td>
			</tr>
			<tr>
				<!-- Estado -->
				<td width="50%"><label>(*)&nbsp;<bean:message bundle="base" key="base.estado.label"/>: </label></td>
				<td width="50%" class="normal">
					<html:select name="cambioEstadoPanicoAdapterVO" property="hisEstPan.estadoPanico.id" styleClass="select">
						<html:optionsCollection name="cambioEstadoPanicoAdapterVO" property="listEstadoPanico" label="descripcion" value="id" />
					</html:select>
				</td>	
			</tr>
		</table>		
	</fieldset>
	<!-- Cambio de Estado -->
	
	<table class="tablabotones" style="width: 100%;" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="100%">
				<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('cambiarEstado', '');">
					<bean:message bundle="apm" key="apm.panicoAdapter.button.cambiarEstado"/>
				</html:button>
   	    	</td>   	    	
   	    </tr>
   	</table>

	
	<input type="hidden" name="method" value=""/>
    <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
    <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

	<input type="hidden" name="selectedId" value=""/>
	<input type="hidden" name="isSubmittedForm" value="true"/>

	<!-- Inclusion del Codigo Javascript del Calendar-->
	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->