<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Formulario filter -->
<html:form styleId="filter" action="/frm/AdministrarEncFormulario.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
	
	<h1><bean:message bundle="frm" key="frm.formularioEditAdapter.title"/></h1>		

	<table class="tablabotones" style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Formulario -->
	<fieldset>
		<legend><bean:message bundle="frm" key="frm.formulario.title"/></legend>
		
		<table class="tabladatos">
			<tr>
				<td><label><bean:message bundle="frm" key="frm.formulario.serie.label"/>: </label></td>
				<td class="normal">
					<logic:equal name="encFormularioAdapterVO" property="act" value="modificar">
						<bean:write name="encFormularioAdapterVO" property="formulario.serie"/>
				 	</logic:equal>
					<logic:equal name="encFormularioAdapterVO" property="act" value="agregar">
						<html:text name="encFormularioAdapterVO" property="formulario.serie" size="15" maxlength="20" />
				 	</logic:equal>	
				</td>
			</tr>
			<tr>
				<td><label><bean:message bundle="frm" key="frm.formulario.observacion.label"/>: </label></td>
				<td class="normal" colspan="3">
					<html:textarea name="encFormularioAdapterVO" property="formulario.observacion" cols="80" rows="15"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- Formulario -->
	
	<table class="tablabotones" style="width: 100%;">
		<tr>				
			<td align="left">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
			<td align="right">
				<logic:equal name="encFormularioAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="encFormularioAdapterVO" property="act" value="agregar">
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
<!-- Fin formulario -->