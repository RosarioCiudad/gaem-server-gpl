<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/BuscarPanico.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
		
	<h1><bean:message bundle="apm" key="apm.panicoSearchPage.title"/></h1>	
		
	<table class="tablabotones" style="width: 100%;">
		<tr>
			<td align="left">
				<p>
					<logic:equal name="panicoSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="base" key="base.busquedaLegendBusqueda"/>
						<bean:message bundle="apm" key="apm.panico.label"/>
					</logic:equal>
					<logic:notEqual name="panicoSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="apm" key="apm.panicoSearchPage.legend"/>
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
				<!-- Fecha Desde -->
				<td><label><bean:message bundle="apm" key="apm.panico.fechaDesde.label"/>: </label></td>
				<td class="normal">
					<html:text name="panicoSearchPageVO" property="panico.fechaPanicoDesdeView" styleId="fechaPanicoDesdeView" size="15" maxlength="10" styleClass="datos" />
					<a class="link_gait" onclick="return show_calendar('fechaPanicoDesdeView');" id="a_fechaPanicoDesdeView">
						<img border="0" src="<%= request.getContextPath()%>/images/calendario.gif"/></a>
				</td>				
				<!-- Fecha Hasta -->
				<td><label><bean:message bundle="apm" key="apm.panico.fechaHasta.label"/>: </label></td>
				<td class="normal">
					<html:text name="panicoSearchPageVO" property="panico.fechaPanicoHastaView" styleId="fechaPanicoHastaView" size="15" maxlength="10" styleClass="datos" />
					<a class="link_gait" onclick="return show_calendar('fechaPanicoHastaView');" id="a_fechaPanicoHastaView">
						<img border="0" src="<%= request.getContextPath()%>/images/calendario.gif"/></a>
				</td>
			</tr>
			<tr>
				<!-- Combo Area -->
				<td><label><bean:message bundle="apm" key="apm.panico.area.label"/>: </label></td>
				<td class="normal">
					<html:select name="panicoSearchPageVO" property="dispositivoMovil.area.id" styleClass="select">
						<html:optionsCollection name="panicoSearchPageVO" property="listArea" label="descripcion" value="id" />
					</html:select>
				</td>
				<!-- Combo Inspector -->
				<td><label><bean:message bundle="apm" key="apm.panico.inspector.label"/>: </label></td>
				<td class="normal">
					<html:select name="panicoSearchPageVO" property="usuarioPanico.id" styleClass="select">
						<html:optionsCollection name="panicoSearchPageVO" property="listUsuarioPanico" label="nombre" value="id" />
					</html:select>
				</td>
			</tr>
			<tr>
				<!-- Combo Estado -->
				<td><label><bean:message bundle="apm" key="apm.panico.estado.label"/>: </label></td>
				<td class="normal">
					<html:select name="panicoSearchPageVO" property="panico.estadoPanico.id" styleClass="select">
						<html:optionsCollection name="panicoSearchPageVO" property="listEstadoPanico" label="descripcion" value="id" />
					</html:select>
				</td>
			</tr>
		</table>
			
		<p align="center">
		  	<html:button property="btnLimpiar"  styleClass="boton" onclick="submitForm('limpiar', '');">
				<bean:message bundle="base" key="abm.button.limpiar"/>
			</html:button>
			&nbsp;
		  	<html:button property="btnBuscar"  styleClass="boton" onclick="submitForm('buscar', '');">
				<bean:message bundle="base" key="abm.button.buscar"/>
			</html:button>
		</p>
	</fieldset>	
	<!-- Fin Filtro -->
	
	<script>
		window.setInterval(function(){submitForm('buscar', '');}, 15000);
	</script>
		
	<!-- Resultado Filtro -->
	<div id="resultadoFiltro">
		<logic:equal name="panicoSearchPageVO" property="viewResult" value="true">
			<logic:notEmpty  name="panicoSearchPageVO" property="listResult">	
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
	               	<tbody>
		               	<tr>
							<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
							<logic:notEqual name="panicoSearchPageVO" property="modoSeleccionar" value="true">
								<th width="1">&nbsp;</th> <!-- Modificar -->
							</logic:notEqual>
							<th align="left"><bean:message bundle="apm" key="apm.panico.fechaRecepcion.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.horaRecepcion.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.fecha.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.hora.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.area.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.descripcion.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.estado.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.panico.inspector.label"/></th>
						</tr>
							
						<logic:iterate id="PanicoVO" name="panicoSearchPageVO" property="listResult">
							<tr>
								<!-- Ver -->
								<td>
									<logic:equal name="panicoSearchPageVO" property="verEnabled" value="enabled">									
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('ver', '<bean:write name="PanicoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</logic:equal>
									<logic:notEqual name="PanicoVO" property="verEnabled" value="enabled">
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
									</logic:notEqual>
								</td>	
								
								<!-- Modificar-->								
								<td>
									<logic:equal name="panicoSearchPageVO" property="modificarEnabled" value="enabled">
										<logic:equal name="PanicoVO" property="modificarEnabled" value="enabled">
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificar', '<bean:write name="PanicoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif"/>
											</a>
										</logic:equal>
										<logic:notEqual name="PanicoVO" property="modificarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="panicoSearchPageVO" property="modificarEnabled" value="enabled">
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
									</logic:notEqual>
								</td>
	
								<td><bean:write name="PanicoVO" property="fechaRecepcionView"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="horaRecepcionView"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="fechaPanicoView"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="horaPanicoView"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="area.descripcion"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="dispositivoMovil.descripcion"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="estadoPanico.descripcion"/>&nbsp;</td>
								<td><bean:write name="PanicoVO" property="usuarioPanico.nombre"/>&nbsp;</td>
							</tr>
						</logic:iterate>
				
						<tr>
							<td class="paginador" align="center" colspan="20">
								<bean:define id="pager" name="panicoSearchPageVO"/>
								<%@ include file="/base/pager.jsp" %>
							</td>
						</tr>
						
					</tbody>
				</table>
			</logic:notEmpty>
			
			<logic:empty name="panicoSearchPageVO" property="listResult">
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
		</tr>
	</table>
	<input type="hidden" name="name"  value="<bean:write name='panicoSearchPageVO' property='name'/>" id="name"/>
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