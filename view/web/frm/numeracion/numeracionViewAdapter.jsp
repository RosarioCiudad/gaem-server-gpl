
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarNumeracion.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="frm" key="frm.numeracionViewAdapter.title"/></h1>	
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
			<legend><bean:message bundle="frm" key="frm.numeracion.adapter.view.title"/></legend>
			<table class="tabladatos">
				<tr>
					<!-- TipoFormulario -->
					<td><label><bean:message bundle="frm" key="frm.numeracion.tipoFormulario.label"/>: </label></td>
					<td class="normal"><bean:write name="numeracionAdapterVO" property="numeracion.tipoFormulario.descripcion"/></td>
				
					<!-- Serie-->			
					<td><label><bean:message bundle="frm" key="frm.numeracion.serie.label"/>: </label></td>
					<td class="normal"><bean:write name="numeracionAdapterVO" property="numeracion.serie.codigo"/></td>
				</tr>
				<tr>
					<!-- desde -->
					<td><label><bean:message bundle="frm" key="frm.numeracion.valorDesde.label" />: </label></td>
					<td class="normal"><bean:write name="numeracionAdapterVO"
						property="numeracion.valorDesde" bundle="base" formatKey="general.format.id"  /></td>
					<!-- hasta -->
					<td><label><bean:message bundle="frm" key="frm.numeracion.valorHasta.label" />: </label></td>
					<td class="normal"><bean:write name="numeracionAdapterVO"
						property="numeracion.valorHasta" bundle="base" formatKey="general.format.id"  /></td>
				</tr>
			</table>
		</fieldset>	
		<!-- Numeracion -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="numeracionAdapterVO" property="act" value="eliminar">
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


