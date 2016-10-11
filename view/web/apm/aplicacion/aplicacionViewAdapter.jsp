<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacion.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.aplicacionViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- Aplicacion -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.aplicacion.title"/></legend>
			<table class="tabladatos">
				
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.codigo" /></td>
				<!-- Decripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.descripcion"  /></td>
			</tr>
			<tr>
				<!-- Package  -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.packageName.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.packageName"  /></td>
				<!-- Class Name -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.className.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.className"  /></td>
			</tr>
			<%--<tr>
			 	<!-- AplicacionTabla -->
				<td><label><bean:message bundle="def" key="apm.aplicacion.aplicacionTabla.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.aplicacionTabla.tablaVersion.tabla"/></td>
			</tr> --%>
			</table>
		</fieldset>	
		<!-- Aplicacion -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="aplicacionAdapterVO" property="act" value="eliminar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('eliminar', '');">
							<bean:message bundle="base" key="abm.button.eliminar"/>
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
		
	</html:form>
	<!-- Fin Tabla que contiene todos los formularios -->
