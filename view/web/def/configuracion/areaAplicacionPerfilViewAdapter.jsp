<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/def/AdministrarAreaAplicacionPerfil.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="def" key="def.areaAplicacionPerfilViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- AreaAplicacionPerfil -->
		<fieldset>
			<legend><bean:message bundle="def" key="def.areaAplicacionPerfil.title"/></legend>
			<table class="tabladatos">
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.perfilAcceso.areaAplicacionPerfil.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="areaAplicacionPerfilAdapterVO" property="areaAplicacionPerfil.aplicacionPerfil.descripcion" /></td>
				<!-- Aplicacion -->
				<td><label><bean:message bundle="apm" key="apm.perfilAcceso.areaAplicacionPerfil.aplicacion.label"/>: </label></td>
				<td class="normal"><bean:write name="areaAplicacionPerfilAdapterVO" property="areaAplicacionPerfil.aplicacionPerfil.aplicacion.descripcion"  /></td>
			</tr>
			<tr>
				<!-- FechaUltMdf -->
				<td><label><bean:message bundle="apm" key="apm.perfilAcceso.areaAplicacionPerfil.fechaUltMdf.label"/>: </label></td>
				<td class="normal"><bean:write name="areaAplicacionPerfilAdapterVO" property="areaAplicacionPerfil.aplicacionPerfil.fechaUltMdfView"  /></td>
			</tr>
		
			</table>
		</fieldset>	
		<!-- AreaAplicacionPerfil -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="areaAplicacionPerfilAdapterVO" property="act" value="eliminar">
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
