<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarCampo.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.campoEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Campo -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.campo.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Etiqueta -->
				<td><label><bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.etiqueta" /></td>
				<!-- Tratamiento -->
				<td><label><bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.tratamiento.value"  /></td>
			</tr>
			<tr>
				<!-- Obligatorio -->
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.obligatorio.value"  /></td>
				<!-- Solo Lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.soloLectura.value"  /></td>
			</tr>			
			<tr>
				<!-- Valor Default -->
				<td><label><bean:message bundle="apm" key="apm.campo.valorDefault.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.valorDefault"  /></td>
				
				<!-- Mascara Visual -->
				<td><label><bean:message bundle="apm" key="apm.campo.mascaraVisual.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.mascaraVisual"/></td>
			</tr>
		
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.campo.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="campoAdapterVO" property="campo.codigo"/></td>
			</tr>
		
			
			</table>
			<table class="tablabotones" style="width: 100%;" >
		  	 	<tr>
	   	    		<td align="right" width="50%">
					<logic:equal name="campoAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    		</td>   	    	
	   	   		 </tr>
   			</table>
	</fieldset>	
	<!-- Campo -->
	
	

<!-- Campo Valor -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.campoValor.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="campoAdapterVO" property="campo.listCampoValor">
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
			<logic:iterate id="CampoValorVO" name="campoAdapterVO" 	property="campo.listCampoValor">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="campoAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="CampoValorVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="campoAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="campoAdapterVO" property="verCampoValorEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verCampoValor', '<bean:write name="CampoValorVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="campoAdapterVO" property="verCampoValorEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="campoAdapterVO"
								property="modificarCampoValorEnabled" value="enabled">
								<logic:equal name="campoAdapterVO" property="modificarCampoValorEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarCampoValor', '<bean:write name="CampoValorVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="campoAdapterVO" property="modificarCampoValorEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="campoAdapterVO"
								property="modificarCampoValorEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="campoAdapterVO"
								property="eliminarCampoValorEnabled" value="enabled">
								<logic:equal name="campoAdapterVO" property="eliminarCampoValorEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarCampoValor', '<bean:write name="CampoValorVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="campoAdapterVO" property="eliminarCampoValorEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="campoAdapterVO" property="eliminarCampoValorEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="CampoValorVO" property="tratamiento.value" />&nbsp;</td>
						<td><bean:write name="CampoValorVO" property="etiqueta" />&nbsp;</td>
						<td><bean:write name="CampoValorVO" property="obligatorio.value" />&nbsp;</td>
						<td><bean:write name="CampoValorVO" property="soloLectura.value" />&nbsp;</td>
						<td><bean:write name="CampoValorVO" property="valorDefault" />&nbsp;</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="campoAdapterVO" property="campo.listCampoValor">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table>


	<table class="tablabotones" style="width: 100%;" >
		<tr>
	  		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
	   	    		<bean:message bundle="base" key="abm.button.volver"/>
	   	    	</html:button>
   	    	</td>
	   	   	<td align="right" width="50%">
				<logic:equal name="campoAdapterVO" property="agregarCampoValorEnabled" value="enabled">
					 <input type="button" class="boton" 
							onClick="submitForm('agregarCampoValor', '<bean:write name="campoAdapterVO" 
							property="campo.id" bundle="base" formatKey="general.format.id"/>');" 
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