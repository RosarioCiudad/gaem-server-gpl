<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form styleId="filter" action="/not/AdministrarTipoNotificacion.do">

		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="not" key="not.tipoNotificacionViewAdapter.title"/></h1>	

		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
		   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<fieldset>
			<legend><bean:message bundle="not" key="not.tipoNotificacion.title"/></legend>
			<table class="tabladatos">
				<tr>
					<td><label><bean:message bundle="not" key="not.tipoNotificacion.descripcion.label"/>: </label></td>
					<td class="normal"><bean:write name="tipoNotificacionAdapterVO" property="tipoNotificacion.descripcion"/></td>
					<td><label><bean:message bundle="not" key="not.tipoNotificacion.ubicacionSonido.label"/>: </label></td>
					<td class="normal"><bean:write name="tipoNotificacionAdapterVO" property="tipoNotificacion.ubicacionSonido"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="not" key="not.tipoNotificacion.ubicacionIcono.label"/>: </label></td>
					<td class="normal"><bean:write name="tipoNotificacionAdapterVO" property="tipoNotificacion.ubicacionIcono"/></td>
					<td><label><bean:message bundle="base" key="base.estado.label"/>: </label></td>
					<td class="normal"><bean:write name="tipoNotificacionAdapterVO" property="tipoNotificacion.estado.value"/></td>
				</tr>
			</table>
		</fieldset>	

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="tipoNotificacionAdapterVO" property="act" value="eliminar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('eliminar', '');">
							<bean:message bundle="base" key="abm.button.eliminar"/>
						</html:button>
					</logic:equal>
	   	    	</td>
	   	    </tr>
	   	 </table>		
	   	 
	    <input type="hidden" name="name"  value="<bean:write name='tipoNotificacionAdapterVO' property='name'/>" id="name"/>
	   	 		
		<input type="hidden" name="method" value=""/>
        <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
        <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

		<input type="hidden" name="selectedId" value=""/>
		<input type="hidden" name="isSubmittedForm" value="true"/>
		
	</html:form>