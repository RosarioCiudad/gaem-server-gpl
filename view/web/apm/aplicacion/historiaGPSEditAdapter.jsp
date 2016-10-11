<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarHistoriaGPS.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.historiaGPSEditAdapter.title"/></h1>	

	<table class="tablabotones" style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.historiaGPS.title"/></legend>
		<table class="tabladatos">
			<tr>
				<td><label><bean:message bundle="apm" key="apm.historiaGPS.idDispositivoMovil.label"/>: </label></td>
				<td class="normal"> 
					<logic:equal name="historiaGPSAdapterVO" property="act" value="modificar">
						<bean:write name="historiaGPSAdapterVO" property="historiaGPS.idDispositivoMovilView"/>
				 	</logic:equal>
					<logic:equal name="historiaGPSAdapterVO" property="act" value="agregar">
						<html:text name="historiaGPSAdapterVO" property="historiaGPS.idDispositivoMovil" size="15" maxlength="20" />
				 	</logic:equal>	
				</td>
				<td><label><bean:message bundle="apm" key="apm.historiaGPS.fechaCaptura.label"/>: </label></td>
				<td class="normal">
					<html:text name="historiaGPSAdapterVO" property="historiaGPS.fechaCapturaView" styleId="fechaCapturaView" size="15" maxlength="10" styleClass="datos" />
					<a class="link_gait" onclick="return show_calendar('fechaCapturaView');" id="a_fechaCapturaView">
						<img border="0" src="<%= request.getContextPath()%>/images/calendario.gif"/></a>
				</td>
			</tr>
			<tr>
				<td><label><bean:message bundle="apm" key="apm.historiaGPS.tipoCaptura.label"/>: </label></td>
				<td class="normal"><html:text name="historiaGPSAdapterVO" property="historiaGPS.tipoCaptura" size="20" maxlength="100"/></td>			
				<td><label><bean:message bundle="apm" key="apm.historiaGPS.datos.label"/>: </label></td>
				<td class="normal"><html:text name="historiaGPSAdapterVO" property="historiaGPS.datos" size="20" maxlength="100"/></td>			
			</tr>
		</table>
	</fieldset>	
	<table class="tablabotones" style="width: 100%;" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
   	    	<td align="right" width="100%">
				<logic:equal name="historiaGPSAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificar', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				<logic:equal name="historiaGPSAdapterVO" property="act" value="agregar">
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

	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
</html:form>