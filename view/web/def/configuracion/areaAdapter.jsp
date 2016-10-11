<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/def/AdministrarArea.do">
	
	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>				
	
	<h1><bean:message bundle="def" key="def.areaEditAdapter.title"/></h1>	

	<table class="tablabotones"  style="width: 100%;">
		<tr>			
			<td align="right">
	   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
	
	<!-- Area -->
	<fieldset>
		<legend><bean:message bundle="def" key="def.area.title"/></legend>
		
		<table class="tabladatos">		
			<tr>
				<!-- Descripcion -->
				<td><label><bean:message bundle="def" key="def.area.desArea.label"/>: </label></td>
				<td class="normal"><bean:write name="areaAdapterVO" property="area.descripcion" /></td>
			</tr>
			<tr>
				<!-- Direccion -->
				<td><label><bean:message bundle="def" key="def.area.direccion.label"/>: </label></td>
				<td class="normal"><bean:write name="areaAdapterVO" property="area.direccion.descripcion"  /></td>
			</tr>
			
			</table>
			<table class="tablabotones" style="width: 100%;" >
		  	 	<tr>
	   	    		<td align="right" width="50%">
					<logic:equal name="areaAdapterVO" property="act" value="modificar">
						<html:button property="btnAceptar"  styleClass="boton" onclick="submitForm('modificarEncabezado', '');">
							<bean:message bundle="base" key="abm.button.modificar"/>
						</html:button>
					</logic:equal>
	   	    		</td>   	    	
	   	   		 </tr>
   			</table>
	</fieldset>	
	<!-- area -->
	
	<!-- TelefonoPanico -->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="apm" key="apm.telefonoPanico.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="areaAdapterVO" property="area.listTelefonoPanico">
			<tr>
				<th width="1">&nbsp;</th>
				<th width="1">&nbsp;</th>
				<th width="1">&nbsp;</th>
			    <th align="left"><bean:message bundle="apm"	key="apm.telefonoPanico.descripcion.label" /></th>
			    <th align="left"><bean:message bundle="apm"	key="apm.telefonoPanico.numero.label" /></th>
			</tr>
			<logic:iterate id="TelefonoPanicoVO" name="areaAdapterVO" property="area.listTelefonoPanico">
				<tr>
					<!-- Seleccionar -->
					 <logic:equal name="areaAdapterVO" property="modoSeleccionar" value="true">
						<td>
							<a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
							</a>
						</td>
					</logic:equal>
					<logic:notEqual name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td>
							<logic:equal name="areaAdapterVO" property="verTelefonoPanicoEnabled" value="enabled">
								<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verTelefonoPanico', '<bean:write name="TelefonoPanicoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="verTelefonoPanicoEnabled"	value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual>
						</td>
						<!-- Modificar-->
						<td>
							<logic:equal name="areaAdapterVO" property="modificarTelefonoPanicoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="modificarTelefonoPanicoEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarTelefonoPanico', '<bean:write name="TelefonoPanicoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.modificar"/>"border="0"	src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="modificarTelefonoPanicoEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="areaAdapterVO"	property="modificarTelefonoPanicoEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
							</logic:notEqual>
						</td>
						<!-- Eliminar-->
						<td>
							<logic:equal name="areaAdapterVO" property="eliminarTelefonoPanicoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="eliminarTelefonoPanicoEnabled" value="enabled">
									<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarTelefonoPanico', '<bean:write name="TelefonoPanicoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img title="<bean:message bundle="base" key="abm.button.eliminar"/>"border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="eliminarTelefonoPanicoEnabled" value="enabled">
									<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> 
							<logic:notEqual name="areaAdapterVO" property="eliminarTelefonoPanicoEnabled" value="enabled">
								<img border="0"	src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual>
						</td>
						</logic:notEqual>
						<td><bean:write name="TelefonoPanicoVO" property="descripcion" />&nbsp;</td>
						<td><bean:write name="TelefonoPanicoVO" property="numero" />&nbsp;</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="areaAdapterVO" property="area.listTelefonoPanico">
				<tr>
					<td align="center">
						<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td>
				</tr>
			</logic:empty>
			<logic:equal name="areaAdapterVO" property="agregarTelefonoPanicoEnabled" value="enabled">
				<tr>
			   	   	<td align="right" colspan="10">
						<input type="button" class="boton"	onClick="submitForm('agregarTelefonoPanico', '<bean:write name="areaAdapterVO" 
							property="area.id" bundle="base" formatKey="general.format.id"/>');" value="<bean:message bundle="base" key="abm.button.agregar"/>" />
					</td>   	    	      
				</tr>
			</logic:equal>
		</tbody>
	</table> 

  	<!--PerfilAcceso-->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="def" key="apm.perfilAcceso.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="areaAdapterVO" property="listAreaAplicacionPerfil">
			<tr>
				<!-- <th width="1">&nbsp;</th>
				Ver
				<th width="1">&nbsp;</th>
				Modificar
				<th width="1">&nbsp;</th>
				Eliminar -->
				
			<th align="left"><bean:message bundle="def"	key="def.direccion.descripcion.label" /></th>
						</tr>
			<logic:iterate id="PerfilAccesoVO" name="areaAdapterVO" 	property="listAreaAplicacionPerfil">
				<tr>
					<!-- Seleccionar -->
					<%-- <logic:equal name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="areaAdapterVO"
								property="eliminarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual> --%>
						<td><bean:write name="PerfilAccesoVO" property="perfilAcceso.descripcion" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="areaAdapterVO" property="listAreaAplicacionPerfil">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table> 


	<%-- <table class="tablabotones" style="width: 100%;" >
		<tr>
	  	
	   	   	<td align="right" width="100%">
				<logic:equal name="areaAdapterVO" property="agregarPerfilAccesoEnabled" value="enabled">
					 <input type="button" class="boton" 
							onClick="submitForm('agregarPerfilAcceso', '<bean:write name="areaAdapterVO" 
							property="area.id" bundle="base" formatKey="general.format.id"/>');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>" />
				</logic:equal>
			</td>   	    	      
		</tr>
	</table> --%>


	  <!--Impresora-->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="def" key="apm.impresora.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="areaAdapterVO" property="area.listImpresora">
			<tr>
				<!-- <th width="1">&nbsp;</th>
				Ver
				<th width="1">&nbsp;</th>
				Modificar
				<th width="1">&nbsp;</th>
				Eliminar -->
				
			<th align="left"><bean:message bundle="apm"	key="apm.impresora.descripcion.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.impresora.numeroSerie.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.impresora.numeroUUID.label" /></th>
			</tr>
			<logic:iterate id="ImpresoraVO" name="areaAdapterVO" 	property="area.listImpresora">
				<tr>
				<%-- 		<!-- Seleccionar -->
					<logic:equal name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar',
								 '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
				<logic:notEqual name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="areaAdapterVO"
								property="eliminarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual> --%>
						<td><bean:write name="ImpresoraVO" property="descripcion" />&nbsp;</td>
						<td><bean:write name="ImpresoraVO" property="numeroSerie" />&nbsp;</td>
						<td><bean:write name="ImpresoraVO" property="numeroUUID" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="areaAdapterVO" property="area.listImpresora">
					<tr><td align="center">
					<bean:message bundle="base" key="base.noExistenRegitros"/>
					</td></tr>
			</logic:empty>
		</tbody>
	</table> 

  <!--Dispositivo Movil-->
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
		<caption>
			<bean:message bundle="def" key="apm.dispositivoMovil.title" />
		</caption>
		<tbody>
		<logic:notEmpty  name="areaAdapterVO" property="area.listDispositivoMovil">
			<tr>
				<!-- <th width="1">&nbsp;</th>
				Ver
				<th width="1">&nbsp;</th>
				Modificar
				<th width="1">&nbsp;</th>
				Eliminar -->
				
			<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.descripcion.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.numeroSerie.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.numeroLinea.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.numeroIMEI.label" /></th>
			<th align="left"><bean:message bundle="apm"	key="apm.dispositivoMovil.email.label" /></th>
			</tr>
			<logic:iterate id="DispositivoMovilVO" name="areaAdapterVO" 	property="area.listDispositivoMovil">
				<tr>
					<!-- Seleccionar -->
					<%-- <logic:equal name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<td><a style="cursor: pointer; cursor: hand;"onclick="submitForm('seleccionar', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
								<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>"
								border="0"src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
						</a></td>
					</logic:equal>
					<logic:notEqual name="areaAdapterVO" property="modoSeleccionar"
						value="true">
						<!-- Ver -->
						<td><logic:equal name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<a style="cursor: pointer; cursor: hand;"
									onclick="submitForm('verPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
									<img title="<bean:message bundle="base" key="abm.button.ver"/>"
									border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec0.gif" />
								</a>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="verPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/selec1.gif" />
							</logic:notEqual></td>
						<!-- Modificar-->
						<td><logic:equal name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('modificarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.modificar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="modificarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO"
								property="modificarPerfilAccesoEnabled" value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/modif1.gif" />
						</logic:notEqual></td>
						<!-- Eliminar-->
						<td><logic:equal name="areaAdapterVO"
								property="eliminarPerfilAccesoEnabled" value="enabled">
								<logic:equal name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<a style="cursor: pointer; cursor: hand;"
										onclick="submitForm('eliminarPerfilAcceso', '<bean:write name="PerfilAccesoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
										<img
										title="<bean:message bundle="base" key="abm.button.eliminar"/>"
										border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif" />
									</a>
								</logic:equal>
								<logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
									value="enabled">
									<img border="0"
										src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
								</logic:notEqual>
							</logic:equal> <logic:notEqual name="areaAdapterVO" property="eliminarPerfilAccesoEnabled"
								value="enabled">
								<img border="0"
									src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif" />
							</logic:notEqual></td>
						</logic:notEqual> --%>
						<td><bean:write name="DispositivoMovilVO" property="descripcion" />&nbsp;</td>
						<td><bean:write name="DispositivoMovilVO" property="numeroSerie" />&nbsp;</td>
						<td><bean:write name="DispositivoMovilVO" property="numeroLinea" />&nbsp;</td>
						<td><bean:write name="DispositivoMovilVO" property="numeroIMEI" />&nbsp;</td>
						<td><bean:write name="DispositivoMovilVO" property="emailAddress" />&nbsp;</td>
						</tr>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="areaAdapterVO" property="area.listDispositivoMovil">
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