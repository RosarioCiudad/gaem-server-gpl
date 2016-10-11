
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarMotivoCierreTipoFormulario.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="frm" key="frm.motivoCierreTipoFormularioViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- MotivoCierreTipoFormulario -->
		<fieldset>
			<legend><bean:message bundle="frm" key="frm.motivoCierreTipoFormulario.adapter.view.title"/></legend>
			<table class="tabladatos">
				<tr>
					<!-- TipoFormulario -->
					<td><label><bean:message bundle="frm" key="frm.motivoCierreTipoFormulario.tipoFormulario.label"/>: </label></td>
					<td class="normal"><bean:write name="motivoCierreTipoFormularioAdapterVO" property="motivoCierreTipoFormulario.tipoFormulario.descripcion"/></td>
				
					<!-- Descripcion-->			
					<td><label><bean:message bundle="frm" key="frm.motivoCierreTipoFormulario.descripcion.label"/>: </label></td>
					<td class="normal"><bean:write name="motivoCierreTipoFormularioAdapterVO" property="motivoCierreTipoFormulario.descripcion"/></td>
				</tr>
			</table>
		</fieldset>	
		<!-- MotivoCierreTipoFormulario -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="motivoCierreTipoFormularioAdapterVO" property="act" value="eliminar">
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
