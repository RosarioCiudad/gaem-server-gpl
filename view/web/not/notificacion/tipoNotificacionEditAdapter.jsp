<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/not/AdministrarTipoNotificacion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="not" key="not.tipoNotificacionEditAdapter.title"/></h1>	

	<table class="tablabotones" style="width: 100%;">
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
				<td class="normal"><html:text name="tipoNotificacionAdapterVO" property="tipoNotificacion.descripcion" size="20" maxlength="100"/></td>			
				<td><label><bean:message bundle="not" key="not.tipoNotificacion.ubicacionSonido.label"/>: </label></td>
				<td class="normal"><html:text name="tipoNotificacionAdapterVO" property="tipoNotificacion.ubicacionSonido" size="20" maxlength="100"/></td>			
			</tr>
			<tr>
				<td><label><bean:message bundle="not" key="not.tipoNotificacion.ubicacionIcono.label"/>: </label></td>
				<td class="normal"><html:text name="tipoNotificacionAdapterVO" property="tipoNotificacion.ubicacionIcono" size="20" maxlength="100"/></td>			
			</tr>
		</table>
	</fieldset>	
	<table class="tablabotones" style="width: 100%;" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="100%">
				<logic:equal name="tipoNotificacionAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="tipoNotificacionAdapterVO" property="act" value="agregar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('agregar', '');">
						<bean:message bundle="base" key="abm.button.agregar"/>
					</html:button>
				</logic:equal>
   	    	</td>   	    	
   	    </tr>
   	</table>
	
	<input type="hidden" name="method" value=""/>
	<input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
	<input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

	<input type="hidden" name="selectedId" value=""/>
	<input type="hidden" name="isSubmittedForm" value="true"/>

	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
</html:form>