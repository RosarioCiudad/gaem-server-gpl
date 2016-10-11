<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarEstadoTipoFormulario.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="frm" key="frm.estadoTipoFormularioEditAdapter.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- EstadoTipoFormulario -->
	<fieldset>
		<legend><bean:message bundle="frm" key="frm.estadoTipoFormulario.title"/></legend>
		
		<table class="tabladatos">
			<!-- TipoFormulario -->
			<tr>
				<td><label><bean:message bundle="frm" key="frm.tipoFormulario.label"/>: </label></td>
				<td class="normal">
					<html:select name="estadoTipoFormularioAdapterVO" property="estadoTipoFormulario.tipoFormulario.id" styleClass="select">
						<html:optionsCollection name="estadoTipoFormularioAdapterVO" property="listTipoFormulario" label="descripcion" value="id" />
					</html:select>
				</td>				
			
			<!-- Descripcion -->
			
				<td><label>(*)&nbsp;<bean:message bundle="frm" key="frm.estadoTipoFormulario.descripcion.label"/>: </label></td>
				<td class="normal"><html:textarea name="estadoTipoFormularioAdapterVO" property="estadoTipoFormulario.descripcion" cols="50" rows="5"/></td>			
			</tr>
		</table>
	</fieldset>	
	<!-- EstadoTipoFormulario -->
	
	<table class="tablabotones" width="100%" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="50%">
				<logic:equal name="estadoTipoFormularioAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="estadoTipoFormularioAdapterVO" property="act" value="agregar">
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
