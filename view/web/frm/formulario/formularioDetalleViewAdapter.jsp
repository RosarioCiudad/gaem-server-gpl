<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/AdministrarFormularioDetalle.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="frm" key="frm.formularioDetalleViewAdapter.title"/></h1>	

		<table class="tablabotones" style="width: 100%;">
			<tr>			
				<td align="right">
		   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- FormularioDetalle -->
		<fieldset>
			<legend><bean:message bundle="frm" key="frm.formularioDetalle.title"/></legend>
			<table class="tabladatos">
				<tr>
					<!-- Etiqueta -->
					<td><label><bean:message bundle="frm" key="frm.formularioDetalle.campo.label"/>: </label></td>
					<td class="normal">
							<bean:write name="formularioDetalleAdapterVO" 
							property="formularioDetalle.tipoFormularioDefSeccionCampo.campo.etiqueta"/>
					</td>
					<!-- Codigo -->
					<td><label><bean:message bundle="frm" key="frm.formularioDetalle.codigo.label"/>: </label></td>
					<td class="normal">
							<bean:write name="formularioDetalleAdapterVO" 
							property="formularioDetalle.tipoFormularioDefSeccionCampo.campo.codigo"/>
					</td>
				</tr>
				<tr>
					<!-- Valor -->
					<td><label><bean:message bundle="frm" key="frm.formularioDetalle.valor.label"/>: </label></td>
					<logic:notEqual name="formularioDetalleAdapterVO" property="formularioDetalle.imagen" value="">										
						<bean:define id="imagen" name="formularioDetalleAdapterVO" property="formularioDetalle.imagen"/>
						<td class="normal"><img width="40%" src="data:image/png;base64,<%=imagen%>"></td>
					</logic:notEqual>
					<logic:equal name="formularioDetalleAdapterVO" property="formularioDetalle.imagen" value="">										
						<td><bean:write name="formularioDetalleAdapterVO" property="formularioDetalle.valor"/>&nbsp;</td>
					</logic:equal>	
				</tr>
			</table>
		</fieldset>	
		<!-- FormularioDetalle -->

		<table class="tablabotones" style="width: 100%;">
	    	<tr>
  	    		<td align="left">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="left" width="100%;">
	   	    	   <logic:equal name="formularioDetalleAdapterVO" property="act" value="ver">
		   	    	    <html:button property="btnImprimir"  styleClass="boton" onclick="submitImprimir('imprimirReportFromAdapter', '1');">
						    <bean:message bundle="base" key="abm.button.imprimir"/>
					    </html:button>
					</logic:equal>
					<logic:equal name="formularioDetalleAdapterVO" property="act" value="eliminar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('eliminar', '');">
							<bean:message bundle="base" key="abm.button.eliminar"/>
						</html:button>
					</logic:equal>
					<logic:equal name="formularioDetalleAdapterVO" property="act" value="activar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('activar', '');">
							<bean:message bundle="base" key="abm.button.activar"/>
						</html:button>
					</logic:equal>
					<logic:equal name="formularioDetalleAdapterVO" property="act" value="desactivar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('desactivar', '');">
							<bean:message bundle="base" key="abm.button.desactivar"/>
						</html:button>
					</logic:equal>
	   	    	</td>
	   	    </tr>
	   	 </table>
	    <input type="hidden" name="name"  value="<bean:write name='formularioDetalleAdapterVO' property='name'/>" id="name"/>
	   	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/> 	
	   	 		
		<input type="hidden" name="method" value=""/>
        <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
        <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

		<input type="hidden" name="selectedId" value=""/>
		<input type="hidden" name="isSubmittedForm" value="true"/>
		
	</html:form>
	<!-- Fin Tabla que contiene todos los formularios -->