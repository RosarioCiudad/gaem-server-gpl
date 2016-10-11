<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarUsuarioApm.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.usuarioApmEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- UsuarioApm -->
	<fieldset>
		<legend><bean:message bundle="apm" key="apm.usuarioApm.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Nombre -->
				<td><label><bean:message bundle="apm" key="apm.usuarioApm.nombre.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmAdapterVO" property="usuarioApm.nombre" /></td>
				<!-- userName -->
				<td><label><bean:message bundle="apm" key="apm.usuarioApm.userName.label"/>: </label></td>
				<td class="normal"><bean:write name="usuarioApmAdapterVO" property="usuarioApm.username"  /></td>
			</tr>
			<tr>
				<!-- userToken -->
				<td><label><bean:message bundle="apm" key="apm.usuarioApm.userToken.label"/>: </label></td>
				<td class="normal" colspan="3"><bean:write name="usuarioApmAdapterVO" property="usuarioApm.usertoken"  /></td>
			</tr>			
		</table>
		<table class="tablabotones" style="width: 100%;" >
		 	<tr>
				<logic:equal name="usuarioApmAdapterVO" property="act" value="modificar">
		   	   		<td align="right" width="50%">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
		   	    	</td>   	    	
				</logic:equal>
	   	   	 </tr>
   		</table>
	</fieldset>	
	<!-- UsuarioApm -->

	<!--Perfil Acceso USuario -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.perfilAccesoUsuario.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="usuarioApmAdapterVO" property="usuarioApm.listPerfilAccesoUsuario">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAccesoUsuario.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.perfilAccesoUsuario.area.label" /></th>
			</tr>
			<logic:iterate id="PerfilAccesoUsuarioVO" name="usuarioApmAdapterVO" 	property="usuarioApm.listPerfilAccesoUsuario">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="usuarioApmAdapterVO" property="modoSeleccionar"
						value="true">
						<td>
							<a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="PerfilAccesoUsuarioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
							</a>
						</td>
					</logic:equal>
					<logic:notEqual name="usuarioApmAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td>
							<logic:equal name="usuarioApmAdapterVO" property="verPerfilAccesoUsuarioEnabled" value="enabled">
								<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verPerfilAccesoUsuario', '<bean:write name="PerfilAccesoUsuarioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal>
							 <logic:notEqual name="usuarioApmAdapterVO" property="verPerfilAccesoUsuarioEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO" property="modificarPerfilAccesoUsuarioEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="modificarPerfilAccesoUsuarioEnabled"	value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarPerfilAccesoUsuario', '<bean:write name="PerfilAccesoUsuarioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="modificarPerfilAccesoUsuarioEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="usuarioApmAdapterVO" property="modificarPerfilAccesoUsuarioEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td> 
						<!-- Eliminar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO"	property="eliminarPerfilAccesoUsuarioEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="eliminarPerfilAccesoUsuarioEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarPerfilAccesoUsuario', '<bean:write name="PerfilAccesoUsuarioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>"border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="eliminarPerfilAccesoUsuarioEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="usuarioApmAdapterVO" property="eliminarPerfilAccesoUsuarioEnabled" value="enabled">
								<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual>
						</td>
						</logic:notEqual>
						<td><bean:write name="PerfilAccesoUsuarioVO" property="perfilAcceso.descripcion" />&nbsp;</td>
						<td><bean:write name="PerfilAccesoUsuarioVO" property="perfilAcceso.area.descripcion" />&nbsp;</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="usuarioApmAdapterVO" property="usuarioApm.listPerfilAccesoUsuario">
				<tr>
					<td align="center">
						<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td>
				</tr>
			</logic:empty>
			<tr>
				<logic:equal name="usuarioApmAdapterVO" property="agregarPerfilAccesoUsuarioEnabled" value="enabled">
			   	   	<td align="right" colspan="10">
						 <input type="button" class="boton" 
								onClick="submitForm('agregarPerfilAccesoUsuario', '<bean:write name="usuarioApmAdapterVO" 
								property="usuarioApm.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>   	    	      
				</logic:equal>
			</tr>
		</tbody>
	</table>


	<!-- UsuarioApmDM -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.usuarioApmDM.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmDM">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.numeroIMEI.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.usuarioApmDM.area.label" /></th>
			</tr>
			<logic:iterate id="UsuarioApmDMVO" name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmDM">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="usuarioApmAdapterVO" property="modoSeleccionar" value="true">
						<td>
							<a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="UsuarioApmDMVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
							</a>
						</td>
					</logic:equal>
					<logic:notEqual name="usuarioApmAdapterVO" property="modoSeleccionar" value="true">
						<!-- Ver -->
						<td>
							<logic:equal name="usuarioApmAdapterVO" property="verUsuarioApmDMEnabled" value="enabled">
								<a style="cursor: pointer; cursor: hand;"onclick="submitForm('verUsuarioApmDM', '<bean:write name="UsuarioApmDMVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="usuarioApmAdapterVO" property="verUsuarioApmDMEnabled"	value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO"	property="modificarUsuarioApmDMEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="modificarUsuarioApmDMEnabled"	value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarUsuarioApmDM', '<bean:write name="UsuarioApmDMVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="modificarUsuarioApmDMEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="usuarioApmAdapterVO" property="modificarUsuarioApmDMEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td>
						<!-- Eliminar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO"	property="eliminarUsuarioApmDMEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="eliminarUsuarioApmDMEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarUsuarioApmDM', '<bean:write name="UsuarioApmDMVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="eliminarUsuarioApmDMEnabled" value="enabled">
									<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="usuarioApmAdapterVO" property="eliminarUsuarioApmDMEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="UsuarioApmDMVO" property="dispositivoMovil.descripcion" />&nbsp;</td>
						<td><bean:write name="UsuarioApmDMVO" property="dispositivoMovil.numeroIMEI" />&nbsp;</td>
						<td><bean:write name="UsuarioApmDMVO" property="dispositivoMovil.area.descripcion" />&nbsp;</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmDM">
					<tr>
						<td align="center">
						<bean:message bundle="base" key="base.noExistenRegitros"/>
						</td>
					</tr>
			</logic:empty>
			<tr>
				<logic:equal name="usuarioApmAdapterVO" property="agregarUsuarioApmDMEnabled" value="enabled">
			   	  	<td align="right" colspan="10">
						 <input type="button" class="boton" onClick="submitForm('agregarUsuarioApmDM', '<bean:write name="usuarioApmAdapterVO" 
								property="usuarioApm.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>    	    	      
				</logic:equal>
			</tr>
		</tbody>
	</table>

	
	<!-- UsuarioApmImpresora -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.usuarioApmImpresora.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmImpresora">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.numeroIMEI.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.usuarioApmDM.area.label" /></th>
			</tr>
			<logic:iterate id="UsuarioApmImpresoraVO" name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmImpresora">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="usuarioApmAdapterVO" property="modoSeleccionar" value="true">
						<td>
							<a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="UsuarioApmDMVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
							</a>
						</td>
					</logic:equal>
					<logic:notEqual name="usuarioApmAdapterVO" property="modoSeleccionar" value="true">
						<!-- Ver -->
						<td>
							<logic:equal name="usuarioApmAdapterVO" property="verUsuarioApmImpresoraEnabled" value="enabled">
								<a style="cursor: pointer; cursor: hand;"onclick="submitForm('verUsuarioApmImpresora', '<bean:write name="UsuarioApmImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="usuarioApmAdapterVO" property="verUsuarioApmImpresoraEnabled"	value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO"	property="modificarUsuarioApmImpresoraEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="modificarUsuarioApmImpresoraEnabled"	value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarUsuarioApmImpresora', '<bean:write name="UsuarioApmImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="modificarUsuarioApmImpresoraEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="usuarioApmAdapterVO" property="modificarUsuarioApmImpresoraEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td> 
						<!-- Eliminar-->
						<td>
							<logic:equal name="usuarioApmAdapterVO"	property="eliminarUsuarioApmImpresoraEnabled" value="enabled">
								<logic:equal name="usuarioApmAdapterVO" property="eliminarUsuarioApmImpresoraEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarUsuarioApmImpresora', '<bean:write name="UsuarioApmImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="usuarioApmAdapterVO" property="eliminarUsuarioApmImpresoraEnabled" value="enabled">
									<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="usuarioApmAdapterVO" property="eliminarUsuarioApmImpresoraEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="UsuarioApmImpresoraVO" property="impresora.descripcion" />&nbsp;</td>
						<td><bean:write name="UsuarioApmImpresoraVO" property="impresora.numeroSerie" />&nbsp;</td>
						<td><bean:write name="UsuarioApmImpresoraVO" property="impresora.area.descripcion" />&nbsp;</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="usuarioApmAdapterVO" property="usuarioApm.listUsuarioApmImpresora">
					<tr>
						<td align="center">
						<bean:message bundle="base" key="base.noExistenRegitros"/>
						</td>
					</tr>
			</logic:empty>
			<tr>
				<logic:equal name="usuarioApmAdapterVO" property="agregarUsuarioApmImpresoraEnabled" value="enabled">
		   	   		<td align="right" colspan="10">
						 <input type="button" class="boton" onClick="submitForm('agregarUsuarioApmImpresora', '<bean:write name="usuarioApmAdapterVO" 
								property="usuarioApm.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>    	    	      
				</logic:equal>
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