<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacionParametro.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.aplicacionParametroViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- AplicacionParametro -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.aplicacionParametro.title"/></legend>
			<table class="tabladatos">
				
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionParametro.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionParametroAdapterVO" property="aplicacionParametro.codigo" /></td>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionParametro.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionParametroAdapterVO" property="aplicacionParametro.descripcion"  /></td>
			</tr>
			<tr>
				<!-- valor -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionParametro.valor.label"/>: </label></td>
				<td colspan="4" class="normal"><bean:write name="aplicacionParametroAdapterVO" property="aplicacionParametro.valor"  /></td>
			</tr>
		
			</table>
		</fieldset>	
		<!-- AplicacionParametro -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="100%">
					<logic:equal name="aplicacionParametroAdapterVO" property="act" value="eliminar">
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
