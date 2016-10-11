<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacionBinarioVersion.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.aplicacionBinarioVersionViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- AplicacionBinarioVersion -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.aplicacionBinarioVersion.title"/></legend>
			<table class="tabladatos">
				
			<tr>
				<!-- Descripción -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionBinarioVersion.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionBinarioVersionAdapterVO" property="aplicacionBinarioVersion.descripcion" /></td>
				<!-- Fecha-->
				<td><label><bean:message bundle="apm" key="apm.aplicacionBinarioVersion.fecha.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionBinarioVersionAdapterVO" property="aplicacionBinarioVersion.fechaView"  /></td>
			</tr>
			<tr>
				<!-- Ubicacion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionBinarioVersion.ubicacion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionBinarioVersionAdapterVO" property="aplicacionBinarioVersion.ubicacion"  /></td>
			
				<!-- Tipo Version -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionBinarioVersion.tipo.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionBinarioVersionAdapterVO" property="aplicacionBinarioVersion.aplicacionTipoBinario.descripcion"  /></td>
			</tr>
			</table>
		</fieldset>	
		<!-- AplicacionBinarioVersion -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="aplicacionBinarioVersionAdapterVO" property="act" value="eliminar">
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
