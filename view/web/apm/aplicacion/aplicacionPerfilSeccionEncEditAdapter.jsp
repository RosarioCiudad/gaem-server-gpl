<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarEncAplicacionPerfilSeccion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.aplicacionPerfilSeccionEditAdapter.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Perfil Seccion -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.aplicacionPerfilSeccionAdapter.title"/></legend>
		<table class="tabladatos">
			
			<tr>
				<!-- Seccion -->
				<td>
				<label>(*)&nbsp;<bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccion.label"/>: </label></td>
				<td class="normal">
					<html:select name="encAplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.seccion.id" styleClass="select">
						<html:optionsCollection name="encAplicacionPerfilSeccionAdapterVO" property="listSeccion" label="descripcion" value="id" />
					</html:select>
				</td>				
				
				
				<!-- Orden -->
				
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.orden.label"/>: </label></td>
				<td class="normal"><html:text name="encAplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.orden" size="20" maxlength="100" styleClass="datos" /></td>
		
			</tr>
			<tr>
				
			
			<!-- Sin Desplegar -->
			<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.sinDesplegar.label"/>: </label></td>
			<td class="normal">
				<html:select name="encAplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.noDesplegar.id" styleClass="select">
					<html:optionsCollection name="encAplicacionPerfilSeccionAdapterVO" property="listSiNo" label="value" value="id" />
				</html:select>
			</td>
	
			<!-- Seccion Opcional -->
			
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccionOpcional.label"/>: </label></td>
				<td class="normal">
					<html:select name="encAplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.opcional.id" styleClass="select">
						<html:optionsCollection name="encAplicacionPerfilSeccionAdapterVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
			</tr>
			
						<!-- Seccion PermiteImpresion -->
			
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.permiteImpresion.label"/>: </label></td>
				<td class="normal">
					<html:select name="encAplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.permiteImpresion.id" styleClass="select">
						<html:optionsCollection name="encAplicacionPerfilSeccionAdapterVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
			</tr>
		
		</table>
	</fieldset>	
	<!-- Perfil Seccion -->
	
	<table class="tablabotones" width="100%" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="50%">
				<logic:equal name="encAplicacionPerfilSeccionAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="encAplicacionPerfilSeccionAdapterVO" property="act" value="agregar">
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
