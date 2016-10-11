<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarNumeracion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="frm" key="frm.numeracionEditAdapter.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>

	<!-- Numeracion -->
	<fieldset>
		<legend>
			<bean:message bundle="frm" key="frm.numeracion.title" />
		</legend>

		<table class="tabladatos">
			<tr>
				<td><label><bean:message bundle="frm"
							key="frm.numeracion.tipoFormulario.label" />: </label></td>
				<td class="normal"><html:select name="numeracionAdapterVO" property="numeracion.tipoFormulario.id" styleClass="select">
						<html:optionsCollection name="numeracionAdapterVO" property="listTipoFormulario" label="descripcion" value="id" />
					</html:select></td>
				<td><label><bean:message bundle="frm" key="frm.numeracion.serie.label" />: </label></td>
				<td class="normal"><html:select name="numeracionAdapterVO" property="numeracion.serie.id" styleClass="select">
						<html:optionsCollection name="numeracionAdapterVO" property="listSerie" label="codigo" value="id" />
					</html:select></td>
			</tr>
			<tr>
				<!-- desde -->
				<td><label><bean:message bundle="frm" key="frm.numeracion.valorDesde.label" />: </label></td>
				<td class="normal"><html:text name="numeracionAdapterVO" property="numeracion.valorDesdeView"/></td>
				<!-- hasta -->
				<td><label><bean:message bundle="frm" key="frm.numeracion.valorHasta.label"/>: </label></td>
				<td class="normal"><html:text name="numeracionAdapterVO" property="numeracion.valorHastaView"/></td>	
			</tr>
		</table>
	</fieldset>
	<!-- Numeracion -->

	<table class="tablabotones" width="100%" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="50%">
				<logic:equal name="numeracionAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="numeracionAdapterVO" property="act" value="agregar">
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




