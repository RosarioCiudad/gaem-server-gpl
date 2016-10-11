<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarUsuarioApmImpresora.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.usuarioApmImpresoraViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- UsuarioApmImpresora -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.usuarioApmImpresora.title"/></legend>
			<table class="tabladatos">
			<tr>
				<!-- Area -->
				<td><label><bean:message bundle="apm" key="apm.usuarioApmDM.area.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmImpresoraAdapterVO" property="usuarioApmImpresora.impresora.area.descripcion" /></td>
				<!-- Descripcion-->
				<td><label><bean:message bundle="apm" key="apm.impresora.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmImpresoraAdapterVO" property="usuarioApmImpresora.impresora.descripcion"  /></td>
			</tr>
			<tr>
				<!-- numero Imei -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroSerie.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmImpresoraAdapterVO" property="usuarioApmImpresora.impresora.numeroSerie"  /></td>
				<!-- numero numeroUUID -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroUUID.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmImpresoraAdapterVO" property="usuarioApmImpresora.impresora.numeroUUID"  /></td>
			</tr>
			</table>
		</fieldset>	
		<!-- UsuarioApmImpresora -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="usuarioApmImpresoraAdapterVO" property="act" value="eliminar">
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