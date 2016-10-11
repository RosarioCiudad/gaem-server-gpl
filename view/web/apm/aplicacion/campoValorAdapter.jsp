<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarCampoValor.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.campoValorEditAdapter.title"/></h1>	

	<table class="tablabotones" width="100%">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Campo Valor-->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.campoValor.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Etiqueta -->
				<td><label><bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.etiqueta" /></td>
				
				<!-- Tratamiento -->
				<td><label><bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.tratamiento.value"  /></td>
			</tr>
			<tr>
				<!-- Obligatorio -->
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.obligatorio.value"  /></td>
				<!-- Valor Default -->
				<td><label><bean:message bundle="apm" key="apm.campo.valorDefault.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.valorDefault"  /></td>
			</tr>
			<tr>
				<!-- Solo Lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.soloLectura.value"  /></td>
			
				<!-- Mascara Visual -->
				<td><label><bean:message bundle="apm" key="apm.campo.mascaraVisual.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.mascaraVisual"/></td>
			</tr>
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.campo.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="campoValorAdapterVO" property="campoValor.codigo"/></td>
			</tr>
		</table>
		
			<table class="tablabotones" width="100%" >
	   	<tr>
  	   	
   	    	<td align="right" width="50%">
				<logic:equal name="campoValorAdapterVO" property="act" value="modificar">
					<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
						<bean:message bundle="base" key="abm.button.modificar"/>
					</html:button>
				</logic:equal>
				
   	    	</td>   	    	
   	    </tr>
   	</table>
		
	</fieldset>	
	<!-- Campo Valor-->
	
	
   	<!-- Campo Valor Opcion-->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1"width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.campoValorOpcion.title" />
		</caption>
		<logic:notEmpty  name="campoValorAdapterVO" property="campoValor.listCampoValorOpcion">
		<tbody>
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.campo.tratamiento.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.etiqueta.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.obligatorio.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.soloLectura.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.campo.valorDefault.label" /></th>
			</tr>
			
			<logic:iterate id="CampoValorOpcionVO" name="campoValorAdapterVO" 	property="campoValor.listCampoValorOpcion">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="campoValorAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="CampoValorOpcionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="campoValorAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="campoValorAdapterVO" property="verCampoValorOpcionEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verCampoValorOpcion', '<bean:write name="CampoValorOpcionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="campoValorAdapterVO" property="verCampoValorOpcionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="campoValorAdapterVO"
								property="modificarCampoValorOpcionEnabled" value="enabled">
								<logic:equal name="campoValorAdapterVO" property="modificarCampoValorOpcionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarCampoValorOpcion', '<bean:write name="CampoValorOpcionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="campoValorAdapterVO" property="modificarCampoValorOpcionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="campoValorAdapterVO"
								property="modificarCampoValorOpcionEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual></td>

						<!-- Eliminar-->
						<td><logic:equal name="campoValorAdapterVO"
								property="eliminarCampoValorOpcionEnabled" value="enabled">
								<logic:equal name="campoValorAdapterVO" property="eliminarCampoValorOpcionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarCampoValorOpcion', '<bean:write name="CampoValorOpcionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="campoValorAdapterVO" property="eliminarCampoValorOpcionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="campoValorAdapterVO" property="eliminarCampoValorOpcionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
					</logic:notEqual>
					<td><bean:write name="CampoValorOpcionVO" property="tratamiento.value" />&nbsp;</td>
					<td><bean:write name="CampoValorOpcionVO" property="etiqueta" />&nbsp;</td>
					<td><bean:write name="CampoValorOpcionVO" property="obligatorio.value" />&nbsp;</td>
					<td><bean:write name="CampoValorOpcionVO" property="soloLectura.value" />&nbsp;</td>
					<td><bean:write name="CampoValorOpcionVO" property="valorDefault" />&nbsp;</td>
					
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="campoValorAdapterVO" property="campoValor.listCampoValorOpcion">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table>


	<table class="tablabotones" width="100%" >
		   	<tr>
	  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    		</td>
	   	    	<td align="right" width="50%">
					
					<logic:equal name="campoValorAdapterVO" property="agregarCampoValorOpcionEnabled" value="enabled">
							 <input type="button" class="boton" 
							onClick="submitForm('agregarCampoValorOpcion', '<bean:write name="campoValorAdapterVO" 
							property="campoValor.id" bundle="base" formatKey="general.format.id"/>');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>" />
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