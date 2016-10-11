<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacionPerfil.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.aplicacionPerfilViewAdapter.title"/></h1>	
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
				<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.descripcion" /></td>
			</tr>
			<tr>
				<!-- Aplicacion-->
					<td><label><bean:message bundle="not" key="not.notificacion.aplicacion.label"/>: </label></td>
					<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.aplicacion.descripcion"/></td>
				</tr>
				
			<tr>
				<!-- Tipo Formulario-->
					<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.tipoformulario.label"/>: </label></td>
					<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.tipoFormulario.descripcion"/></td>
				</tr>	
				
				
			</table>
		</fieldset>	
		<!-- AplicacionPerfil -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="aplicacionPerfilAdapterVO" property="act" value="eliminar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('eliminar', '');">
							<bean:message bundle="base" key="abm.button.eliminar"/>
						</html:button>
					</logic:equal>
	   	    	</td>
	   	    		 
	    		 
	   	    	<td align="right" width="50%">
					<logic:equal name="aplicacionPerfilAdapterVO" property="act" value="clonar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('clonar', '');">
							<bean:message bundle="base" key="abm.button.clonar"/>
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
