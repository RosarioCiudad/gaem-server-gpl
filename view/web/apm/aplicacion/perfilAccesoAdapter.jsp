<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarPerfilAcceso.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.perfilAccesoEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Aplicacion -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.perfilAcceso.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.perfilAcceso.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="perfilAccesoAdapterVO" property="perfilAcceso.descripcion"  /></td>
				<!-- Area -->
				<td><label><bean:message bundle="apm" key="apm.perfilAcceso.area.label"/>: </label></td>
				<td class="normal"><bean:write name="perfilAccesoAdapterVO" property="perfilAcceso.area.descripcion"  /></td>
			</tr>		
		</table>
		<table class="tablabotones" style="width: 100%;" >
		 	<tr>
	   	    	<td align="right" width="50%">
					<logic:equal name="perfilAccesoAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    	</td>   	    	
	   		</tr>
   		</table>
	</fieldset>	
	<!-- Aplicacion -->
	
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.perfilAccesoAplicacion.formulario.legend" />
		</caption>
		<tbody>
		<logic:notEmpty  name="perfilAccesoAdapterVO" property="perfilAcceso.listAreaAplicacionPerfil">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAcceso.areaAplicacionPerfil.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAcceso.areaAplicacionPerfil.aplicacion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAcceso.areaAplicacionPerfil.fechaUltMdf.label" /></th>
			</tr>
			<logic:iterate id="AreaAplicacionPerfilVO" name="perfilAccesoAdapterVO" property="perfilAcceso.listAreaAplicacionPerfil">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="perfilAccesoAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AreaAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="perfilAccesoAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="perfilAccesoAdapterVO" property="verAreaAplicacionPerfilEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verAreaAplicacionPerfil', '<bean:write name="AreaAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"	border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="perfilAccesoAdapterVO" property="verAreaAplicacionPerfilEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td>
							<logic:equal name="perfilAccesoAdapterVO"
								property="modificarAreaAplicacionPerfilEnabled" value="enabled">
								<logic:equal name="perfilAccesoAdapterVO" property="modificarAreaAplicacionPerfilEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarAreaAplicacionPerfil', '<bean:write name="AreaAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="perfilAccesoAdapterVO" property="modificarAreaAplicacionPerfilEnabled" value="enabled">
									<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="perfilAccesoAdapterVO" property="modificarAreaAplicacionPerfilEnabled" value="enabled">
								<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td>
						<!-- Eliminar-->
						<td>
							<logic:equal name="perfilAccesoAdapterVO" property="eliminarAreaAplicacionPerfilEnabled" value="enabled">
								<logic:equal name="perfilAccesoAdapterVO" property="eliminarAreaAplicacionPerfilEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarAreaAplicacionPerfil', '<bean:write name="AreaAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="perfilAccesoAdapterVO" property="eliminarAreaAplicacionPerfilEnabled"
									value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="perfilAccesoAdapterVO" property="eliminarAreaAplicacionPerfilEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual>
						</td>
						</logic:notEqual>
						<td><bean:write name="AreaAplicacionPerfilVO" property="aplicacionPerfil.descripcion" />&nbsp;</td>
						<td><bean:write name="AreaAplicacionPerfilVO" property="aplicacionPerfil.aplicacion.descripcion" />&nbsp;</td>
						<td><bean:write name="AreaAplicacionPerfilVO" property="aplicacionPerfil.fechaUltMdfView" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="perfilAccesoAdapterVO" property="perfilAcceso.listAreaAplicacionPerfil">
					<tr><td align="center"><bean:message bundle="base" key="base.noExistenRegitros"/></td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="perfilAccesoAdapterVO" property="agregarAreaAplicacionPerfilEnabled" value="enabled">
						 <input type="button" class="boton" onClick="submitForm('agregarAreaAplicacionPerfil', '<bean:write name="perfilAccesoAdapterVO" 
								property="perfilAcceso.id" bundle="base" formatKey="general.format.id"/>');" value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</logic:equal>
				</td>   	    	      
			</tr>
		</tbody>
	</table>
	
	<!-- Perfil Acceso Aplicacion -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.perfilAccesoAplicacion.aplicacion.legend" />
		</caption>
		<tbody>
		<logic:notEmpty  name="perfilAccesoAdapterVO" property="perfilAcceso.listPerfilAccesoAplicacion">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacion.codigo.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAcceso.areaAplicacionPerfil.fechaUltMdf.label" /></th>
			</tr>
			<logic:iterate id="PerfilAccesoAplicacionVO" name="perfilAccesoAdapterVO" property="perfilAcceso.listPerfilAccesoAplicacion">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="perfilAccesoAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="PerfilAccesoAplicacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="perfilAccesoAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="perfilAccesoAdapterVO" property="verPerfilAccesoAplicacionEnabled"	value="enabled">
								<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verPerfilAccesoAplicacion', '<bean:write name="PerfilAccesoAplicacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"	border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="perfilAccesoAdapterVO" property="verPerfilAccesoAplicacionEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td>
							<logic:equal name="perfilAccesoAdapterVO" property="modificarPerfilAccesoAplicacionEnabled" value="enabled">
								<logic:equal name="perfilAccesoAdapterVO" property="modificarPerfilAccesoAplicacionEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarPerfilAccesoAplicacion', '<bean:write name="PerfilAccesoAplicacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="perfilAccesoAdapterVO" property="modificarPerfilAccesoAplicacionEnabled" value="enabled">
									<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="perfilAccesoAdapterVO" property="modificarPerfilAccesoAplicacionEnabled" value="enabled">
								<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td>
						<!-- Eliminar-->
						<td>
							<logic:equal name="perfilAccesoAdapterVO" property="eliminarPerfilAccesoAplicacionEnabled" value="enabled">
								<logic:equal name="perfilAccesoAdapterVO" property="eliminarPerfilAccesoAplicacionEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarPerfilAccesoAplicacion', '<bean:write name="PerfilAccesoAplicacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="perfilAccesoAdapterVO" property="eliminarPerfilAccesoAplicacionEnabled"
									value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="perfilAccesoAdapterVO" property="eliminarPerfilAccesoAplicacionEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual>
						</td>
						</logic:notEqual>
						<td><bean:write name="PerfilAccesoAplicacionVO" property="aplicacion.codigo" />&nbsp;</td>
						<td><bean:write name="PerfilAccesoAplicacionVO" property="aplicacion.descripcion" />&nbsp;</td>
						<td><bean:write name="PerfilAccesoAplicacionVO" property="aplicacion.fechaUltMdfView" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="perfilAccesoAdapterVO" property="perfilAcceso.listPerfilAccesoAplicacion">
					<tr><td align="center"><bean:message bundle="base" key="base.noExistenRegitros"/></td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="perfilAccesoAdapterVO" property="agregarPerfilAccesoAplicacionEnabled" value="enabled">
						 <input type="button" class="boton" onClick="submitForm('agregarPerfilAccesoAplicacion', '<bean:write name="perfilAccesoAdapterVO" 
								property="perfilAcceso.id" bundle="base" formatKey="general.format.id"/>');" value="<bean:message bundle="base" key="abm.button.agregar"/>" />
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