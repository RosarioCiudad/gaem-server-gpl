<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/frm/BuscarFormulario.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
		
	<h1><bean:message bundle="frm" key="frm.formularioSearchPage.title"/></h1>	
		
	<table class="tablabotones" style="width: 100%;">
		<tr>
			<td align="left">
				<p>
					<logic:equal name="formularioSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="base" key="base.busquedaLegendBusqueda"/>
						<bean:message bundle="frm" key="frm.formulario.label"/>
					</logic:equal>
					<logic:notEqual name="formularioSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="frm" key="frm.formularioSearchPage.legend"/>
					</logic:notEqual>		
				</p>
			</td>				
			<td align="right">
	 			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
		</tr>
	</table>
			
	<!-- Filtro -->
	<fieldset>
	<legend><bean:message bundle="base" key="base.parametrosBusqueda"/></legend>
		<table class="tabladatos">
			<tr>
				<td><label><bean:message bundle="frm" key="frm.formularioSearchPage.fechaCierreDesde.label"/>: </label></td>
				<td class="normal">
					<html:text name="formularioSearchPageVO" property="fechaCierreDesdeView" styleId="fechaCierreDesdeView" size="15" maxlength="10" styleClass="datos" />
					<a class="link_gait" onclick="return show_calendar('fechaCierreDesdeView');" id="a_fechaCierreDesdeView">
						<img border="0" src="<%= request.getContextPath()%>/images/calendario.gif"/></a>
				</td>
				<td><label><bean:message bundle="frm" key="frm.formularioSearchPage.fechaCierreHasta.label"/>: </label></td>
				<td class="normal">
					<html:text name="formularioSearchPageVO" property="fechaCierreHastaView" styleId="fechaCierreHastaView" size="15" maxlength="10" styleClass="datos" />
					<a class="link_gait" onclick="return show_calendar('fechaCierreHastaView');" id="a_fechaCierreHastaView">
						<img border="0" src="<%= request.getContextPath()%>/images/calendario.gif"/></a>
				</td>
			</tr>
			<tr>	
				<td><label><bean:message bundle="frm" key="frm.formulario.numero.label"/>: </label></td>
				<td class="normal"><html:text name="formularioSearchPageVO" property="formulario.numero" size="15" maxlength="20" /></td>
				<td><label><bean:message bundle="base" key="base.estado.label"/>: </label></td>
				<td class="normal">
					<html:select name="formularioSearchPageVO" property="formulario.estado.id" styleClass="select">
						<html:optionsCollection name="formularioSearchPageVO" property="listEstado" label="descripcion" value="id" />
					</html:select>
				</td>					
			</tr>
			<tr>	
				<td><label><bean:message bundle="frm" key="frm.formulario.usuarioCierre.label"/>: </label></td>
				<td class="normal"><html:text name="formularioSearchPageVO" property="formulario.usuarioCierre.username" size="15" maxlength="20" /></td>
				<td><label><bean:message bundle="def" key="def.area.label"/>: </label></td>
				<td class="normal">
					<html:select name="formularioSearchPageVO" property="formulario.area.id" styleClass="select">
						<html:optionsCollection name="formularioSearchPageVO" property="listArea" label="descripcion" value="id" />
					</html:select>
				</td>	
			</tr>
			
			<tr>
				<td></td>
				<td></td>
				<td><label><bean:message bundle="frm" key="frm.formulario.tipoFormulario.label"/>: </label></td>
				<td class="normal">
					<html:select name="formularioSearchPageVO" property="formulario.tipoFormulario.id" styleClass="select">
						<html:optionsCollection name="formularioSearchPageVO" property="listTipoFormulario" label="descripcion" value="id" />
					</html:select>
				</td>	

			</tr>
			
		</table>
			
		<p align="center">
		  	<html:button property="btnLimpiar"  styleClass="boton" onclick="submitForm('limpiar', '');">
				<bean:message bundle="base" key="abm.button.limpiar"/>
			</html:button>
			&nbsp;
			<html:button property="btnImprimir"  styleClass="boton" onclick="submitImprimir('baseImprimir', '1');">
				<bean:message bundle="base" key="abm.button.imprimir"/>
			</html:button>	
			&nbsp;
		  	<html:button property="btnBuscar"  styleClass="boton" onclick="submitForm('buscar', '');">
				<bean:message bundle="base" key="abm.button.buscar"/>
			</html:button>
		</p>
	</fieldset>	
	<!-- Fin Filtro -->
		
	<!-- Resultado Filtro -->
	<div id="resultadoFiltro">
		<logic:equal name="formularioSearchPageVO" property="viewResult" value="true">
			<logic:notEmpty  name="formularioSearchPageVO" property="listResult">	
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
	               	<tbody>
		               	<tr>
							<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
							<logic:notEqual name="formularioSearchPageVO" property="modoSeleccionar" value="true">
								<th width="1">&nbsp;</th> <!-- Modificar -->
								<th width="1">&nbsp;</th> <!-- Eliminar -->
								<!-- <th width="1">&nbsp;</th>  Activar Desactivar -->
							</logic:notEqual>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.numero.label"/></th>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.fechaCierre.label"/></th>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.horaCierre.label"/></th>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.tipoFormulario.ref"/></th>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.usuarioCierre.label"/></th>
							<!--<th align="left"><bean:message bundle="frm" key="frm.formulario.observacion.label"/></th>
								<th align="left"><bean:message bundle="frm" key="frm.formulario.codigoRespuesta.label"/></th> -->
								<th align="left"><bean:message bundle="base" key="base.estado.label"/></th>
						</tr>
							
						<logic:iterate id="FormularioVO" name="formularioSearchPageVO" property="listResult">
							<tr>
								<!-- Seleccionar -->
								<logic:equal name="formularioSearchPageVO" property="modoSeleccionar" value="true">
									<td>	
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('seleccionar', '<bean:write name="FormularioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</td>
								</logic:equal>									
								<logic:notEqual name="formularioSearchPageVO" property="modoSeleccionar" value="true">
									<!-- Ver -->
									<td>
										<logic:equal name="formularioSearchPageVO" property="verEnabled" value="enabled">									
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('ver', '<bean:write name="FormularioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
											</a>
										</logic:equal>
										<logic:notEqual name="FormularioVO" property="verEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
										</logic:notEqual>
									</td>	
									<!-- Modificar-->								
									<td>
										<logic:equal name="formularioSearchPageVO" property="modificarEnabled" value="enabled">
											<logic:equal name="FormularioVO" property="modificarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificar', '<bean:write name="FormularioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif"/>
												</a>
											</logic:equal>
											<logic:notEqual name="FormularioVO" property="modificarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="formularioSearchPageVO" property="modificarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
										</logic:notEqual>
									</td>
									<!-- Eliminar-->								
									<td>
										<logic:equal name="formularioSearchPageVO" property="eliminarEnabled" value="enabled">
											<logic:equal name="FormularioVO" property="eliminarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminar', '<bean:write name="FormularioVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif"/>
												</a>
											</logic:equal>	
											<logic:notEqual name="FormularioVO" property="eliminarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="formularioSearchPageVO" property="eliminarEnabled" value="enabled">										
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
										</logic:notEqual>
									</td>
									<!-- 	<td>
										Procesado Error
										<logic:equal name="FormularioVO" property="estado.id" value="3">
											<logic:equal name="formularioSearchPageVO" property="activarEnabled" value="enabled">
												<logic:equal name="FormularioVO" property="activarEnabled" value="enabled">
													<a style="cursor: pointer; cursor: hand;" onclick="submitForm('activar', '<bean:write name="FormularioVO" property="idView"/>');">
														<img title="<bean:message bundle="base" key="abm.button.activar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar0.gif"/>
													</a>
												</logic:equal> 
												<logic:notEqual name="FormularioVO" property="activarEnabled" value="enabled">
													<img border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar1.gif"/>
												</logic:notEqual>
											</logic:equal>
											<logic:notEqual name="formularioSearchPageVO" property="activarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar1.gif"/>
											</logic:notEqual>
										</logic:equal>  -->
										<!-- Otros Estados  
										<logic:notEqual name="FormularioVO" property="estado.id" value="3">
											<logic:equal name="formularioSearchPageVO" property="desactivarEnabled" value="enabled">
												<logic:equal name="FormularioVO" property="desactivarEnabled" value="enabled">
													<a style="cursor: pointer; cursor: hand;" onclick="submitForm('desactivar', '<bean:write name="FormularioVO" property="idView"/>');">
														<img title="<bean:message bundle="base" key="abm.button.desactivar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar0.gif"/>
													</a>
												</logic:equal>
												<logic:notEqual name="FormularioVO" property="desactivarEnabled" value="enabled">
													<img border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar1.gif"/>
												</logic:notEqual>
											</logic:equal>
											<logic:notEqual name="formularioSearchPageVO" property="desactivarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/reingresar1.gif"/>
											</logic:notEqual>										
										</logic:notEqual>
									</td>  -->
								</logic:notEqual>
									<td><bean:write name="FormularioVO" property="numero"/>&nbsp;</td>
									<td><bean:write name="FormularioVO" property="fechaCierreView"/>&nbsp;</td>
									<td><bean:write name="FormularioVO" property="horaCierreView"/>&nbsp;</td>
									<td><bean:write name="FormularioVO" property="tipoFormulario.descripcion"/>&nbsp;</td>
									<td><bean:write name="FormularioVO" property="usuarioCierre.username"/>&nbsp;</td>
									<!-- <td><bean:write name="FormularioVO" property="observacion"/>&nbsp;</td>
									<td><bean:write name="FormularioVO" property="codigoRespuesta"/>&nbsp;</td>  -->
									<td><bean:write name="FormularioVO" property="estado.value"/>&nbsp;</td>
							</tr>
						</logic:iterate>
				
						<tr>
							<td class="paginador" align="center" colspan="20">
								<bean:define id="pager" name="formularioSearchPageVO"/>
								<%@ include file="/base/pager.jsp" %>
							</td>
						</tr>
						
					</tbody>
				</table>
			</logic:notEmpty>
			
			<logic:empty name="formularioSearchPageVO" property="listResult">
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
                	<tbody>
						<tr><td align="center">
							<bean:message bundle="base" key="base.resultadoVacio"/>
						</td></tr>
					</tbody>			
				</table>
			</logic:empty>
		</logic:equal>			
	</div>
	<!-- Fin Resultado Filtro -->

	<table class="tablabotones">
		<tr>				
			<td align="left">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
			<logic:equal name="formularioSearchPageVO" property="viewResult" value="true">
  	    		<td align="right" width="100%">
  	    			<logic:equal name="formularioSearchPageVO" property="modoSeleccionar" value="false">
						<bean:define id="agregarEnabled" name="formularioSearchPageVO" property="agregarEnabled"/>
						<input type="button" <%=agregarEnabled%> class="boton" 
							onClick="submitForm('agregar', '0');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
					</logic:equal>
  	    			<logic:equal name="formularioSearchPageVO" property="modoSeleccionar" value="true">
  	    				<logic:equal name="formularioSearchPageVO" property="agregarEnSeleccion" value="true">
							<bean:define id="agregarEnabled" name="formularioSearchPageVO" property="agregarEnabled"/>
							<input type="button" <%=agregarEnabled%> class="boton" 
								onClick="submitForm('agregar', '0');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
							</logic:equal>
					</logic:equal>
				</td>
			</logic:equal>
		</tr>
	</table>
	<input type="hidden" name="name"  value="<bean:write name='formularioSearchPageVO' property='name'/>" id="name"/>
   	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/>
		
	<input type="hidden" name="method" value=""/>
    <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
    <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

	<input type="hidden" name="selectedId" value=""/>
	<input type="hidden" name="pageNumber" value="1" id="pageNumber">
	<input type="hidden" name="pageMethod" value="buscar" id="pageMethod">
	<input type="hidden" name="isSubmittedForm" value="true"/>

	<!-- Inclusion del Codigo Javascript del Calendar-->
	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>		
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->