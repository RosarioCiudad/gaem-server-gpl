<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/seg/AdministrarUsuarioGait.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="seg" key="seg.usuarioGaitViewAdapter.title"/></h1>	

		<table class="tablabotones" width="100%">
			<tr>			
				<td align="right">
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>

		
		<!-- UsuarioGait -->
		<fieldset>
			<legend><bean:message bundle="seg" key="seg.usuarioGait.title"/></legend>
			<table class="tabladatos">
				<tr>
					<!-- nombre -->
					<td><label><bean:message bundle="seg" key="seg.usuarioGait.usuarioGAIT.label"/>: </label></td>
					<td class="normal"><bean:write name="usuarioGaitAdapterVO" property="usuarioGait.usuarioGAIT"/></td>
					
					<!-- Estado -->
					<td><label><bean:message bundle="base" key="base.estado.label"/>: </label></td>
					<td class="normal"><bean:write name="usuarioGaitAdapterVO" property="usuarioGait.estado.value"/></td>
				</tr>
				<tr>
					<!-- direccion -->
					<td><label><bean:message bundle="def" key="def.direccion.label"/>: </label></td>
					<td class="normal"><bean:write name="usuarioGaitAdapterVO" property="usuarioGait.direccion.descripcion"/></td>
					
					<!-- area -->
					<td><label><bean:message bundle="def" key="def.area.label"/>: </label></td>
					<td class="normal"><bean:write name="usuarioGaitAdapterVO" property="usuarioGait.area.descripcion"/></td>
				</tr>
			</table>
		</fieldset>	
		<!-- UsuarioGait -->

		<table class="tablabotones" width="100%">
	    	<tr>
  	    		<td align="left" width="50%">
		   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>	   	    			
	   	    	</td>
	   	    	<td align="right" width="50%">
					<logic:equal name="usuarioGaitAdapterVO" property="act" value="activar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('activar', '');">
							<bean:message bundle="base" key="abm.button.activar"/>
						</html:button>
					</logic:equal>
					<logic:equal name="usuarioGaitAdapterVO" property="act" value="desactivar">
						<html:button property="btnAccionBase"  styleClass="boton" onclick="submitForm('desactivar', '');">
							<bean:message bundle="base" key="abm.button.desactivar"/>
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