<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/def/AdministrarDireccion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="def" key="def.direccionEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Direccion -->
	<fieldset>
		<legend><bean:message bundle="def" key="def.direccion.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="def" key="def.direccion.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="direccionAdapterVO" property="direccion.descripcion" /></td>
				<!-- esDGI -->
				<td><label><bean:message bundle="def" key="def.direccion.esDGI.label"/>: </label></td>
				<td class="normal"><bean:write name="direccionAdapterVO" property="direccion.esDGI.value"  /></td>								
			</table>
			<table class="tablabotones" style="width: 100%;" >
		  	 	<tr>
	   	    		<td align="right" width="50%">
					<logic:equal name="direccionAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    		</td>   	    	
	   	   		 </tr>
   			</table>
	</fieldset>	
	<!-- Direccion -->
	
	

<!-- Area -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="def" key="def.direccion.area.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="direccionAdapterVO" property="direccion.listArea">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="def"	key="def.area.desArea.label" /></th>
				
			</tr>
			<logic:iterate id="AreaVO" name="direccionAdapterVO" 	property="direccion.listArea">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="direccionAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AreaVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="direccionAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="direccionAdapterVO" property="verAreaEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verArea', '<bean:write name="AreaVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="direccionAdapterVO" property="verAreaEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="direccionAdapterVO"
								property="modificarAreaEnabled" value="enabled">
								<logic:equal name="direccionAdapterVO" property="modificarAreaEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarArea', '<bean:write name="AreaVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="direccionAdapterVO" property="modificarAreaEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="direccionAdapterVO"
								property="modificarAreaEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td>
							<logic:equal name="direccionAdapterVO"	property="eliminarAreaEnabled" value="enabled">
								<logic:equal name="direccionAdapterVO" property="eliminarAreaEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarArea', '<bean:write name="AreaVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="direccionAdapterVO" property="eliminarAreaEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="direccionAdapterVO" property="eliminarAreaEnabled"
								value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual>
							</td>
						</logic:notEqual>
						<td><bean:write name="AreaVO" property="descripcion" />&nbsp;</td>
						
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="direccionAdapterVO" property="direccion.listArea">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="direccionAdapterVO" property="agregarAreaEnabled" value="enabled">
						 <input type="button" class="boton" 
								onClick="submitForm('agregarArea', '<bean:write name="direccionAdapterVO" 
								property="direccion.id" bundle="base" formatKey="general.format.id"/>');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</logic:equal>
				</td>   	    	      
			</tr>
		</tbody>
	</table>

	<!-- Direccion Apl Perfil -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="def" key="def.direccion.direccionAplicacionPerfil.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="direccionAdapterVO" property="direccion.listDireccionAplicacionPerfil">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left">
				
				<bean:message bundle="def"	key="def.direccion.descripcion.label" /></th>
				
			</tr>
			<logic:iterate id="DireccionAplicacionPerfilVO" name="direccionAdapterVO" 	property="direccion.listDireccionAplicacionPerfil">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="direccionAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="DireccionAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="direccionAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="direccionAdapterVO" property="verAreaEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verDireccionAplicacionPerfil', '<bean:write name="DireccionAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="direccionAdapterVO" property="verDireccionAplicacionPerfilEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="direccionAdapterVO"
								property="modificarDireccionAplicacionPerfilEnabled" value="enabled">
								<logic:equal name="direccionAdapterVO" property="modificarDireccionAplicacionPerfilEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarDireccionAplicacionPerfil', '<bean:write name="DireccionAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="direccionAdapterVO" property="modificarDireccionAplicacionPerfilEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="direccionAdapterVO"
								property="modificarDireccionAplicacionPerfilEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="direccionAdapterVO"
								property="eliminarDireccionAplicacionPerfilEnabled" value="enabled">
								<logic:equal name="direccionAdapterVO" property="eliminarDireccionAplicacionPerfilEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarDireccionAplicacionPerfil', '<bean:write name="DireccionAplicacionPerfilVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="direccionAdapterVO" property="eliminarDireccionAplicacionPerfilEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="direccionAdapterVO" property="eliminarDireccionAplicacionPerfilEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="DireccionAplicacionPerfilVO" property="aplicacionPerfil.descripcion" />&nbsp;</td>
						
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="direccionAdapterVO" property="direccion.listDireccionAplicacionPerfil">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
			<tr>
		   	   	<td align="right" colspan="10">
					<logic:equal name="direccionAdapterVO" property="agregarDireccionAplicacionPerfilEnabled" value="enabled">
						 <input type="button" class="boton" 
								onClick="submitForm('agregarDireccionAplicacionPerfil', '<bean:write name="direccionAdapterVO" 
								property="direccion.id" bundle="base" formatKey="general.format.id"/>');" 
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