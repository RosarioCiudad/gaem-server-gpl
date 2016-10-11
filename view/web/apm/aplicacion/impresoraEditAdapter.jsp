<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarImpresora.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.impresoraEditAdapter.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Impresora -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.impresora.title"/></legend>
		<table class="tabladatos">
			
			<tr>
				<!-- Descripción -->
				<td><label>(*)&nbsp;<bean:message bundle="apm" key="apm.impresora.descripcion.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraAdapterVO" property="impresora.descripcion" size="20" maxlength="100"  /></td>
				<!-- Número de Serie -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroSerie.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraAdapterVO" property="impresora.numeroSerie" size="20" maxlength="100"  /></td>
			</tr>
			<tr>
				<!-- Número de UUID -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroUUID.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraAdapterVO" property="impresora.numeroUUID" size="20" maxlength="100"  /></td>
			
					<!-- Area -->
				<logic:equal name="impresoraAdapterVO" property="editarAreaEnabled" value="true">
					<td><label><bean:message bundle="def" key="def.area.label"/>: </label></td>
					<td class="normal">
						<html:select name="impresoraAdapterVO" property="impresora.area.id" styleClass="select">
							<html:optionsCollection name="impresoraAdapterVO" property="listArea" label="descripcion" value="id" />
						</html:select>
					</td>
				</logic:equal>
				<logic:equal name="impresoraAdapterVO" property="editarAreaEnabled" value="false">
					<td><label><bean:message bundle="def" key="def.area.label"/>: </label></td>
					<td class="normal"><bean:write name="impresoraAdapterVO" property="impresora.area.descripcion"/></td>
				</logic:equal>
			
			
			</tr>
		</table>
	</fieldset>	
	<!-- Impresora -->
	
	<table class="tablabotones" width="100%" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="50%">
				<logic:equal name="impresoraAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="impresoraAdapterVO" property="act" value="agregar">
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
