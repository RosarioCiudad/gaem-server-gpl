<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter"
	action="/apm/AdministrarEncAplPerfilSeccionCampoValor.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp"%>
	<!-- Errors  -->
	<html:errors bundle="base" />

	<h1>
		<bean:message bundle="apm"
			key="apm.aplPerfilSeccionCampoValorEditAdapter.title" />
	</h1>
	<table class="tablabotones" width="100%">
		<tr>
			<td align="right"><html:button property="btnVolver"
					styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver" />
				</html:button></td>
		</tr>
	</table>

	<!-- Campo Valor -->
	<fieldset>
		<legend>
			<bean:message bundle="apm"
				key="apm.aplPerfilSeccionCampoValorView.title" />
		</legend>
		<table class="tabladatos">

			<tr>
				<!-- Descripcion Perfil-->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.label" />: </label></td>
				<td class="normal">
					<bean:write name="encAplPerfilSeccionCampoValorAdapterVO" 
					property="aplPerfilSeccionCampoValor.aplPerfilSeccionCampo.aplicacionPerfilSeccion.aplicacionPerfil.descripcion" />
				</td>

				<!-- Descripcion Seccion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccion.label" />: </label></td>
				<td class="normal">
					<bean:write name="encAplPerfilSeccionCampoValorAdapterVO" 
					property="aplPerfilSeccionCampoValor.aplPerfilSeccionCampo.aplicacionPerfilSeccion.seccion.descripcion" />
				</td>
			</tr>
			<tr>
				<!-- Campo -->
				<td><label><bean:message bundle="apm" key="apm.campo.label" />: </label></td>
				<td class="normal">
					<bean:write	name="encAplPerfilSeccionCampoValorAdapterVO"
						property="aplPerfilSeccionCampoValor.aplPerfilSeccionCampo.campo.etiqueta" />
				</td>
			</tr>
			<tr>
				<!-- Orden -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.orden.label" />: </label></td>
				<td class="normal"><html:text	name="encAplPerfilSeccionCampoValorAdapterVO" property="aplPerfilSeccionCampoValor.orden" size="20"	maxlength="100" styleClass="datos" />
				</td>
				<!-- Valor -->
				<td><label><bean:message bundle="apm" key="apm.aplPerfilSeccionCampoValor.valor.title" />: </label></td>
				<td class="normal">
					<html:select	name="encAplPerfilSeccionCampoValorAdapterVO" property="aplPerfilSeccionCampoValor.campoValor.id" styleClass="select">
						<html:optionsCollection	name="encAplPerfilSeccionCampoValorAdapterVO" property="listCampoValor" label="etiqueta" value="id" />
					</html:select>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- Campo Valor-->

	<table class="tablabotones" width="100%">
		<tr>
			<td align="left" width="50%"><html:button property="btnVolver"
					styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver" />
				</html:button></td>
			<td align="right" width="50%"><logic:equal
					name="encAplPerfilSeccionCampoValorAdapterVO" property="act"
					value="modificar">
					<html:button property="btnAceptar" styleClass="boton"
						onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar" />
					</html:button>
				</logic:equal> <logic:equal name="encAplPerfilSeccionCampoValorAdapterVO"
					property="act" value="agregar">
					<html:button property="btnAceptar" styleClass="boton"
						onclick="submitForm('agregar', '');">
						<bean:message bundle="base" key="abm.button.agregar" />
					</html:button>
				</logic:equal></td>
		</tr>
	</table>

	<input type="hidden" name="method" value="" />
	<input type="hidden" name="anonimo"
		value="<bean:write name="userSession" property="isAnonimoView"/>" />
	<input type="hidden" name="urlReComenzar"
		value="<bean:write name="userSession" property="urlReComenzar"/>" />

	<input type="hidden" name="selectedId" value="" />
	<input type="hidden" name="isSubmittedForm" value="true" />

	<!-- Inclusion del Codigo Javascript del Calendar-->
	<div id="calendarDiv"
		style="position: absolute; visibility: hidden; background-color: white; layer-background-color: white;"></div>
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->
