<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarAplicacion.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="apm" key="apm.aplicacionEditAdapter.title"/></h1>	

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
		<legend><bean:message bundle="apm" key="apm.aplicacion.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Codigo -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.codigo.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.codigo" /></td>
				<!-- Descripcion -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.descripcion.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.descripcion"  /></td>
			</tr>
			<tr>
				<!-- packageName -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.packageName.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.packageName"  /></td>
				<!-- className -->
				<td><label><bean:message bundle="apm" key="apm.aplicacion.className.label"/>: </label></td>
				<td class="normal"><bean:write name="aplicacionAdapterVO" property="aplicacion.className"  /></td>
			</tr>			
			
		
			
			</table>
			<table class="tablabotones" style="width: 100%;" >
		  	 	<tr>
	   	    		<td align="right" width="50%">
					<logic:equal name="aplicacionAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    		</td>   	    	
	   	   		 </tr>
   			</table>
	</fieldset>	
	<!-- Aplicacion -->
	
	

<!-- Aplicacion Parametro -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionParametro.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionParametro">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionParametro.codigo.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionParametro.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionParametro.valor.label" /></th>
			</tr>
			<logic:iterate id="AplicacionParametroVO" name="aplicacionAdapterVO" 	property="aplicacion.listAplicacionParametro">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="aplicacionAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AplicacionParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="aplicacionAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="aplicacionAdapterVO" property="verAplicacionParametroEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verAplicacionParametro', '<bean:write name="AplicacionParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO" property="verAplicacionParametroEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="aplicacionAdapterVO"
								property="modificarAplicacionParametroEnabled" value="enabled">
								<logic:equal name="aplicacionAdapterVO" property="modificarAplicacionParametroEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarAplicacionParametro', '<bean:write name="AplicacionParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionAdapterVO" property="modificarAplicacionParametroEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO"
								property="modificarAplicacionParametroEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="aplicacionAdapterVO"
								property="eliminarAplicacionParametroEnabled" value="enabled">
								<logic:equal name="aplicacionAdapterVO" property="eliminarAplicacionParametroEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarAplicacionParametro', '<bean:write name="AplicacionParametroVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionAdapterVO" property="eliminarAplicacionParametroEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO" property="eliminarAplicacionParametroEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
						<td><bean:write name="AplicacionParametroVO" property="codigo" />&nbsp;</td>
						<td><bean:write name="AplicacionParametroVO" property="descripcion" />&nbsp;</td>
						<td><bean:write name="AplicacionParametroVO" property="valor" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionParametro">
					<tr>
						<td align="center"><bean:message bundle="base" key="base.noExistenRegitros"/></td>
					</tr>
			</logic:empty>
			<logic:equal name="aplicacionAdapterVO" property="agregarAplicacionParametroEnabled" value="enabled">
				<tr>
			   	   	<td align="right" colspan="10">
							 <input type="button" class="boton" onClick="submitForm('agregarAplicacionParametro', '<bean:write name="aplicacionAdapterVO"
							  property="aplicacion.id" bundle="base" formatKey="general.format.id"/>');" value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>   	    	      
				</tr>
			</logic:equal>
		</tbody>
	</table>


<!-- Aplicacion BinarioVersion -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionBinarioVersion.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionBinarioVersion">
			<tr>
				<th width="1">&nbsp;</th>
				<!-- Ver -->
				<th width="1">&nbsp;</th>
				<!-- Modificar -->
				<th width="1">&nbsp;</th>
				<!-- Eliminar -->

				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionBinarioVersion.descripcion.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionBinarioVersion.fecha.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionBinarioVersion.tipo.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionBinarioVersion.ubicacion.label" /></th>
			</tr>
			<logic:iterate id="AplicacionBinarioVersionVO" name="aplicacionAdapterVO" 	property="aplicacion.listAplicacionBinarioVersion">
				<tr>
					<!-- Seleccionar -->
					<logic:equal name="aplicacionAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="AplicacionBinarioVersionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="aplicacionAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="aplicacionAdapterVO" property="verAplicacionBinarioVersionEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verAplicacionBinarioVersion', '<bean:write name="AplicacionBinarioVersionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO" property="verAplicacionBinarioVersionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="aplicacionAdapterVO"
								property="modificarAplicacionBinarioVersionEnabled" value="enabled">
								<logic:equal name="aplicacionAdapterVO" property="modificarAplicacionBinarioVersionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarAplicacionBinarioVersion', '<bean:write name="AplicacionBinarioVersionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionAdapterVO" property="modificarAplicacionBinarioVersionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO"
								property="modificarAplicacionBinarioVersionEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="aplicacionAdapterVO"
								property="eliminarAplicacionBinarioVersionEnabled" value="enabled">
								<logic:equal name="aplicacionAdapterVO" property="eliminarAplicacionBinarioVersionEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarAplicacionBinarioVersion', '<bean:write name="AplicacionBinarioVersionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="aplicacionAdapterVO" property="eliminarAplicacionBinarioVersionEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="aplicacionAdapterVO" property="eliminarAplicacionBinarioVersionEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual>
			
						<td><bean:write name="AplicacionBinarioVersionVO" property="descripcion" />&nbsp;</td>
						<td><bean:write name="AplicacionBinarioVersionVO" property="fechaView" />&nbsp;</td>
						<td><bean:write name="AplicacionBinarioVersionVO" property="aplicacionTipoBinario.descripcion" />&nbsp;</td>
						<td><bean:write name="AplicacionBinarioVersionVO" property="ubicacion" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionBinarioVersion">
				<tr>
					<td align="center">
						<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td>
				</tr>
			</logic:empty>
			<logic:equal name="aplicacionAdapterVO" property="agregarAplicacionBinarioVersionEnabled" value="enabled">
				<tr>
		   		   	<td align="right" colspan="10">
						 <input type="button" class="boton" onClick="submitForm('agregarAplicacionBinarioVersion', '<bean:write name="aplicacionAdapterVO" 
								property="aplicacion.id" bundle="base" formatKey="general.format.id"/>');" value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>   	    	      
				</tr>
			</logic:equal>
		</tbody>
	</table>

	
	<!-- Aplicacion Tabla -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionTabla.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionTabla">
			<tr>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionTabla.tabla.label" /></th>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionTabla.lastVersion.label" /></th>
				
			</tr>
			<logic:iterate id="AplicacionTablaVO" name="aplicacionAdapterVO" 	property="aplicacion.listAplicacionTabla">
				<tr>
					<td><bean:write name="AplicacionTablaVO" property="tablaVersion.tabla" />&nbsp;</td>
					<td><bean:write name="AplicacionTablaVO" property="tablaVersion.lastVersion"  bundle="base" formatKey="general.format.id" />&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionTabla">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table>
	
	<!-- Aplicacion Perfil -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.aplicacionPerfil.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionPerfil">
			<tr>
				<th align="left"><bean:message bundle="apm"	key="apm.aplicacionPerfil.descripcion.label" /></th>
			</tr>
			<logic:iterate id="AplicacionPerfilVO" name="aplicacionAdapterVO" 	property="aplicacion.listAplicacionPerfil">
				<tr>
						<td><bean:write name="AplicacionPerfilVO" property="descripcion" />&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="aplicacionAdapterVO" property="aplicacion.listAplicacionPerfil">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table>
	<table class="tablabotones" style="width: 100%;" >
		<tr>
	  		<td align="left" width="100%">
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