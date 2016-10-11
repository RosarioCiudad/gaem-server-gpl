<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarCampoValorOpcion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.campoValorOpcionEditAdapter.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Campo Valor Opcion-->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.campoValorOpcion.title"/></legend>
		<table class="tabladatos">
			
			<tr>
				<!-- Etiqueta -->
				
				<td><label>(*)&nbsp;<bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><html:text name="campoValorOpcionAdapterVO" property="campoValorOpcion.etiqueta" size="20" maxlength="100" styleClass="datos" /></td>
				
				<!-- Tratamiento -->
				
				<td><label>(*)&nbsp;<bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoValorOpcionAdapterVO" property="campoValorOpcion.tratamiento.id" styleClass="select">
						<html:optionsCollection name="campoValorOpcionAdapterVO" property="listTratamiento" label="value" value="id" />
					</html:select>
				</td>
			</tr>
			<tr>
			<!-- Valor Default -->
			<td><label><bean:message bundle="apm" key="apm.campo.valorDefault.label"/>: </label></td>
			<td class="normal"><html:text name="campoValorOpcionAdapterVO" property="campoValorOpcion.valorDefault" size="20" maxlength="100" styleClass="datos" /></td>
			<!-- Obligatorio -->
			
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoValorOpcionAdapterVO" property="campoValorOpcion.obligatorio.id" styleClass="select">
						<html:optionsCollection name="campoValorOpcionAdapterVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
			</tr>
			<tr>
			<!-- Solo lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoValorOpcionAdapterVO" property="campoValorOpcion.soloLectura.id" styleClass="select">
						<html:optionsCollection name="campoValorOpcionAdapterVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
				<!-- Mascara Visual -->
				<td><label><bean:message bundle="apm" key="apm.campo.mascaraVisual.label"/>: </label></td>
				<td class="normal"><html:text name="campoValorOpcionAdapterVO" property="campoValorOpcion.mascaraVisual" size="20" maxlength="50" styleClass="datos" /></td>
			</tr>
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.campo.codigo.label"/>: </label></td>
				<td class="normal"><html:text name="campoValorOpcionAdapterVO" property="campoValorOpcion.codigo" size="20" maxlength="15" styleClass="datos" /></td>
			</tr>
		</table>
	</fieldset>	
	<!-- Campo Valor Opcion-->
	
	<table class="tablabotones" width="100%" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="50%">
				<logic:equal name="campoValorOpcionAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="campoValorOpcionAdapterVO" property="act" value="agregar">
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

	<!-- Inclusion del Codigo Javascript del Calendar-->
	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->
