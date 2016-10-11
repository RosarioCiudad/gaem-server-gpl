<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacionPerfil.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.aplicacionPerfilEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
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
		<legend><bean:message bundle="apm" key="apm.aplicacionPerfil.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.descripcion" /></td>
				<!-- Aplicacion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.aplicacion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.aplicacion.descripcion"  /></td>
			</tr>
			<tr>
				<!-- Tipo Formulario-->
					<td><label><bean:message bundle="apm" key="apm.aplicacionPerfil.tipoformulario.label"/>: </label></td>
					<td class="normal"><bean:write name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.tipoFormulario.descripcion"/></td>
			</tr>
		</table>
			<table class="tablabotones" style="width: 100%;" >
		  	 	<tr>
	   	    		<td align="right" width="50%">
					<logic:equal name="aplicacionPerfilAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    		</td>   	    	
	   	   		 </tr>
   			</table>
	</fieldset>	
	<!-- AplicacionPerfil -->

	<!-- AplicacionPerfil Seccion -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.aplicacionPerfilSeccion.legend" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.listAplicacionPerfilSeccion">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfil.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfilSeccion.orden.label" /></th>
			</tr>
			<logic:iterate id="AplicacionPerfilSeccionVO" name="aplicacionPerfilAdapterVO" 	property="aplicacionPerfil.listAplicacionPerfilSeccion">
				<tr>
					<!-- Seleccionar -->																				
					<logic:equal name="aplicacionPerfilAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AplicacionPerfilSeccionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="aplicacionPerfilAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="aplicacionPerfilAdapterVO" property="verAplicacionPerfilSeccionEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verAplicacionPerfilSeccion', '<bean:write name="AplicacionPerfilSeccionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO" property="verAplicacionPerfilSeccionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="aplicacionPerfilAdapterVO"
								property="modificarAplicacionPerfilSeccionEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilAdapterVO" property="modificarAplicacionPerfilSeccionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarAplicacionPerfilSeccion', '<bean:write name="AplicacionPerfilSeccionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilAdapterVO" property="modificarAplicacionPerfilSeccionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO"
								property="modificarAplicacionPerfilSeccionEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="aplicacionPerfilAdapterVO"
								property="eliminarAplicacionPerfilSeccionEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilSeccionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarAplicacionPerfilSeccion', '<bean:write name="AplicacionPerfilSeccionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilSeccionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilSeccionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="AplicacionPerfilSeccionVO" property="seccion.descripcion" />&nbsp;</td>
						<td><bean:write name="AplicacionPerfilSeccionVO" property="orden" bundle="base" formatKey="general.format.id" />&nbsp;</td>

					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.listAplicacionPerfilSeccion">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="aplicacionPerfilAdapterVO" property="agregarAplicacionPerfilSeccionEnabled" value="enabled">
						 <input type="button" class="boton" 
								onClick="submitForm('agregarAplicacionPerfilSeccion', '<bean:write name="aplicacionPerfilAdapterVO" 
								property="aplicacionPerfil.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</logic:equal>
				</td>   	    	      
			</tr>
		</tbody>
	</table>
	
	<!-- AplicacionPerfil Parametro -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionPerfilSeccion.aplicacionPerfilParametro.legend" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.listAplicacionPerfilParametro">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfilParametro.codigo.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfilParametro.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfilParametro.valor.label" /></th>
			</tr>
			<logic:iterate id="AplicacionPerfilParametroVO" name="aplicacionPerfilAdapterVO" 	property="aplicacionPerfil.listAplicacionPerfilParametro">
				<tr>
					<!-- Seleccionar -->																				
					<logic:equal name="aplicacionPerfilAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AplicacionPerfilParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="aplicacionPerfilAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="aplicacionPerfilAdapterVO" property="verAplicacionPerfilParametroEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verAplicacionPerfilParametro', '<bean:write name="AplicacionPerfilParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"	border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO" property="verAplicacionPerfilParametroEnabled"
								value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td><logic:equal name="aplicacionPerfilAdapterVO"
								property="modificarAplicacionPerfilParametroEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilAdapterVO" property="modificarAplicacionPerfilParametroEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarAplicacionPerfilParametro', '<bean:write name="AplicacionPerfilParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilAdapterVO" property="modificarAplicacionPerfilParametroEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO"
								property="modificarAplicacionPerfilParametroEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="aplicacionPerfilAdapterVO"
								property="eliminarAplicacionPerfilParametroEnabled" value="enabled">
								<logic:equal name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilParametroEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarAplicacionPerfilParametro', '<bean:write name="AplicacionPerfilParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilParametroEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionPerfilAdapterVO" property="eliminarAplicacionPerfilParametroEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="AplicacionPerfilParametroVO" property="codigo" />&nbsp;</td>
						<td><bean:write name="AplicacionPerfilParametroVO" property="descripcion" bundle="base" formatKey="general.format.id" />&nbsp;</td>
						<td><bean:write name="AplicacionPerfilParametroVO" property="valor" bundle="base" formatKey="general.format.id" />&nbsp;</td>
	
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionPerfilAdapterVO" property="aplicacionPerfil.listAplicacionPerfilParametro">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="aplicacionPerfilAdapterVO" property="agregarAplicacionPerfilParametroEnabled" value="enabled">
						 <input type="button" class="boton" 
								onClick="submitForm('agregarAplicacionPerfilParametro', '<bean:write name="aplicacionPerfilAdapterVO" 
								property="aplicacionPerfil.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</logic:equal>
				</td>   	    	      
			</tr>
	</tbody>
	</table>


	<table class="tablabotones" style="width: 100%;" >
		<tr>
	  		<td align="left" width="50%">
	   	    	<html:button property="btnVolver" styleClass="boton" onclick="submitForm('volver', '');">
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