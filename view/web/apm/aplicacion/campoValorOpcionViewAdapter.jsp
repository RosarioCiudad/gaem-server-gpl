<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarCampoValorOpcion.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.campoViewAdapter.title"/></h1>	
		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- Campo -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.campo.title"/></legend>
			<table class="tabladatos">
				
			<tr>
				<!-- Etiqueta -->
				<td><label><bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.etiqueta" /></td>
				<!-- Obligatorio -->
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.obligatorio.value"  /></td>
			</tr>
			<tr>
				<!-- Tratamiento -->
				<td><label><bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.tratamiento.value"  /></td>
				<!-- Valor Default -->
				<td><label><bean:message bundle="apm" key="apm.campo.valorDefault.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.valorDefault"  /></td>
			</tr>
			<tr>
				<!-- Solo Lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.soloLectura.value"  /></td>
			
				<!-- Mascara Visual -->
				<td><label><bean:message bundle="apm" key="apm.campo.mascaraVisual.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.mascaraVisual"/></td>
			</tr>
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.campo.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorOpcionAdapterVO" property="campoValorOpcion.codigo"/></td>
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
					<logic:equal name="campoValorOpcionAdapterVO" property="act" value="eliminar">
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
