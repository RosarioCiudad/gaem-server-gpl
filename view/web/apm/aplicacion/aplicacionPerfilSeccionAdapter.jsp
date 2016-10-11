<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacionPerfilSeccion.do">
	
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

	<!-- AplicacionPerfil -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.aplicacionPerfilView.title"/></legend>
		<table class="tabladatos">
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.aplicacionPerfil.descripcion" /></td>
			</tr>
			<tr>
			<!-- Aplicacion-->
				<td><label><bean:message bundle="not" key="not.notificacion.aplicacion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.aplicacionPerfil.aplicacion.descripcion"/></td>
			</tr>
		</table>
	</fieldset>	
	<!-- AplicacionPerfil -->
			
	<!-- AplicacionPerfil Secion-->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.aplicacionPerfilSeccionView.title"/></legend>
	
			<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.seccion.descripcion" /></td>				
				<!-- Orden -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.orden.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.orden" bundle="base" formatKey="general.format.id"  /></td>			
			</tr>
			<tr>
				<!-- Sin Desplegar -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.sinDesplegar.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.noDesplegar.value"  /></td>
				<!-- Seccion Opcional -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.seccionOpcional.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.opcional.value"  /></td>			
			</tr>
			<tr>
				<!-- Seccion PermiteImpresion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.permiteImpresion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.permiteImpresion.value"  /></td>	
			</tr>
		</table>
	
		<table class="tablabotones" style="width: 100%;" >
	  	 	<tr>
	    		<td align="right" width="50%">
				<logic:equal name="aplicacionPerfilSeccionAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
	    		</td>   	    	
			</tr>
   		</table>
	</fieldset>	
	<!-- AplicacionPerfil Seccion-->

	<table class="tramonline" border="0" cellpadding="0" cellspacing="1"width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.aplPerfilSeccionCampo.legend" />
		</caption>
		<logic:notEmpty  name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.listAplPerfilSeccionCampo">
		<tbody>
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.campo.etiqueta.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfilSeccion.orden.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.tratamiento.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.obligatorio.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.soloLectura.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.valorDefault.label" /></th>
			</tr>
			
			<logic:iterate id="AplPerfilSeccionCampoVO" name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.listAplPerfilSeccionCampo">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="aplicacionPerfilSeccionAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AplPerfilSeccionCampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="aplicacionPerfilSeccionAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="aplicacionPerfilSeccionAdapterVO" property="verAplPerfilSeccionCampoEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verAplPerfilSeccionCampo', '<bean:write name="AplPerfilSeccionCampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="aplicacionPerfilSeccionAdapterVO" property="verAplPerfilSeccionCampoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="aplicacionPerfilSeccionAdapterVO"
								property="modificarAplPerfilSeccionCampoEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilSeccionAdapterVO" property="modificarAplPerfilSeccionCampoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarAplPerfilSeccionCampo', '<bean:write name="AplPerfilSeccionCampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilSeccionAdapterVO" property="modificarAplPerfilSeccionCampoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilSeccionAdapterVO"
								property="modificarAplPerfilSeccionCampoEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual></td>

						<!-- Eliminar-->
						<td><logic:equal name="aplicacionPerfilSeccionAdapterVO"
								property="eliminarAplPerfilSeccionCampoEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilSeccionAdapterVO" property="eliminarAplPerfilSeccionCampoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarAplPerfilSeccionCampo', '<bean:write name="AplPerfilSeccionCampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilSeccionAdapterVO" property="eliminarAplPerfilSeccionCampoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilSeccionAdapterVO" property="eliminarAplPerfilSeccionCampoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
					</logic:notEqual>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="campo.etiqueta" />&nbsp;</td>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="orden" bundle="base" formatKey="general.format.id" />&nbsp;</td>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="campo.tratamiento.value" />&nbsp;</td>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="campo.obligatorio.value" />&nbsp;</td>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="campo.soloLectura.value" />&nbsp;</td>
					<td><bean:write name="AplPerfilSeccionCampoVO" property="campo.valorDefault" />&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionPerfilSeccionAdapterVO" property="aplicacionPerfilSeccion.listAplPerfilSeccionCampo">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="aplicacionPerfilSeccionAdapterVO" property="agregarAplPerfilSeccionCampoEnabled" value="enabled">
							 <input type="button" class="boton" 
							onClick="submitForm('agregarAplPerfilSeccionCampo', '<bean:write name="aplicacionPerfilSeccionAdapterVO" 
							property="aplicacionPerfilSeccion.id" bundle="base" formatKey="general.format.id"/>');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</logic:equal>
				</td>   	    	      
			</tr>
		</tbody>
	</table>
	
	<table class="tablabotones" style="width: 100%;">
		<tr >				
			<td align="left">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
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