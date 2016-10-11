<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplPerfilSeccionCampo.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.aplPerfilSeccionCampoViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
			
	<!-- AplicacionPerfil -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.aplicacionPerfilView.title"/></legend>
			<table class="tabladatos">
				
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.aplicacionPerfil.descripcion" /></td>
			</tr>
			<tr>
			<!-- Aplicacion-->
				<td><label><bean:message bundle="not" key="not.notificacion.aplicacion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.aplicacionPerfil.aplicacion.descripcion"/></td>
			</tr>
				
				
				
			</table>
		</fieldset>	
		<!-- AplicacionPerfil -->
		
		
	<!-- AplicacionPerfil Secion-->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.aplicacionPerfilSeccionView.title"/></legend>
			
			<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.seccion.descripcion" /></td>				
				<!-- Orden -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.orden.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.orden" bundle="base" formatKey="general.format.id"  /></td>			
			</tr>
			<tr>
				<!-- Sin Desplegar -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.sinDesplegar.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.noDesplegar.value"  /></td>
				<!-- Seccion Opcional -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccionOpcional.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.aplicacionPerfilSeccion.opcional.value"  /></td>			
			</tr>
			
		</table>
	
	</fieldset>
	
	
	<!-- Campo -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.aplPerfilSeccionCampoView.title"/></legend>
		
		<table class="tabladatos">		
			
			
			<tr>
				<!-- Etiqueta -->
				<td><label><bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.campo.etiqueta" /></td>
				
				<!-- Tratamiento -->
				<td><label><bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.campo.tratamiento.value"  /></td>
				
			</tr>
			<tr>
				<!-- Obligatorio -->
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.campo.obligatorio.value"  /></td>
				<!-- Valor Default -->
				<td><label><bean:message bundle="apm" key="apm.campo.valorDefault.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.campo.valorDefault"  /></td>
			</tr>
			
			<tr>
				<!-- Orden -->
				
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.orden.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.orden"  bundle="base" formatKey="general.format.id"  /></td>
				
				<!-- Solo lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal"><bean:write name="aplPerfilSeccionCampoAdapterVO" property="aplPerfilSeccionCampo.campo.soloLectura.value"  /></td>
			</tr>
		</table>
		
		
		
	</fieldset>	
	<!-- Campo -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="aplPerfilSeccionCampoAdapterVO" property="act" value="eliminar">
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
