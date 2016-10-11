<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/BuscarImpresora.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
		
	<h1><bean:message bundle="apm" key="apm.impresoraSearchPage.title"/></h1>	
	<table class="tablabotones" width="100%">
		<tr>
			<td align="left">				
				<p>
					<logic:equal name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="base" key="base.busquedaLegend"/>
						<bean:message bundle="apm" key="apm.impresora.label"/>
					</logic:equal>
					<logic:notEqual name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
						<bean:message bundle="apm" key="apm.impresoraSearchPage.legend"/>
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
				<!-- Descripción -->
				<td><label><bean:message bundle="apm" key="apm.impresora.descripcion.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraSearchPageVO" property="impresora.descripcion" size="20" maxlength="100" styleClass="datos" /></td>
				<!-- Número de Serie -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroSerie.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraSearchPageVO" property="impresora.numeroSerie" size="20" maxlength="100" styleClass="datos" /></td>
			</tr>
			<tr>
				<!-- Número UUID -->
				<td><label><bean:message bundle="apm" key="apm.impresora.numeroUUID.label"/>: </label></td>
				<td class="normal"><html:text name="impresoraSearchPageVO" property="impresora.numeroUUID" size="20" maxlength="100" styleClass="datos" /></td>
				<!-- Area -->	
				<logic:equal name="impresoraSearchPageVO" property="editarAreaEnabled" value="true">
				<td><label><bean:message bundle="def" key="def.area.label"/>: </label></td>
				<td class="normal">
					<html:select name="impresoraSearchPageVO" property="impresora.area.id" styleClass="select">
						<html:optionsCollection name="impresoraSearchPageVO" property="listArea" label="descripcion" value="id" />
					</html:select>
				</td>
				</logic:equal>
			
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
		
	<!-- Resultado Filtro -->
	<div id="resultadoFiltro">
		<logic:equal name="impresoraSearchPageVO" property="viewResult" value="true">
			<logic:notEmpty  name="impresoraSearchPageVO" property="listResult">	
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
	               	<tbody>
		               	<tr>
							<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
							<logic:notEqual name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
								<th width="1">&nbsp;</th> <!-- Modificar -->
								<th width="1">&nbsp;</th> <!-- Eliminar -->
							</logic:notEqual>
							<th align="left"><bean:message bundle="apm" key="apm.impresora.descripcion.label"/></th>					
							<th align="left"><bean:message bundle="apm" key="apm.impresora.numeroSerie.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.impresora.numeroUUID.label"/></th>
							<!-- Area -->
							<logic:equal name="impresoraSearchPageVO" property="editarAreaEnabled" value="true">
								<th align="left"><bean:message bundle="def" key="def.area.label"/></th>
							</logic:equal>
						</tr>
							
						<logic:iterate id="ImpresoraVO" name="impresoraSearchPageVO" property="listResult">
							<tr>
								<!-- Seleccionar -->
								<logic:equal name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
									<td>	
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('seleccionar', '<bean:write name="ImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</td>
								</logic:equal>									
								<logic:notEqual name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
									<!-- Ver -->
									<td>
										<logic:equal name="impresoraSearchPageVO" property="verEnabled" value="enabled">									
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('ver', '<bean:write name="ImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
											</a>
										</logic:equal>
										<logic:notEqual name="ImpresoraVO" property="verEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
										</logic:notEqual>
									</td>	
									<!-- Modificar-->								
									<td>
										<logic:equal name="impresoraSearchPageVO" property="modificarEnabled" value="enabled">
											<logic:equal name="ImpresoraVO" property="modificarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificar', '<bean:write name="ImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif"/>
												</a>
											</logic:equal>
											<logic:notEqual name="ImpresoraVO" property="modificarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="impresoraSearchPageVO" property="modificarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
										</logic:notEqual>
									</td>
	
									<!-- Eliminar-->								
									<td>
										<logic:equal name="impresoraSearchPageVO" property="eliminarEnabled" value="enabled">
											<logic:equal name="ImpresoraVO" property="eliminarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminar', '<bean:write name="ImpresoraVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif"/>
												</a>
											</logic:equal>	
											<logic:notEqual name="ImpresoraVO" property="eliminarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="impresoraSearchPageVO" property="eliminarEnabled" value="enabled">										
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
										</logic:notEqual>
									</td>
								</logic:notEqual>
								<td><bean:write name="ImpresoraVO" property="descripcion"/>&nbsp;</td>						
								<td><bean:write name="ImpresoraVO" property="numeroSerie"/>&nbsp;</td>
								<td><bean:write name="ImpresoraVO" property="numeroUUID"/>&nbsp;</td>
								<logic:equal name="impresoraSearchPageVO" property="editarAreaEnabled" value="true">
									<td><bean:write name="ImpresoraVO" property="area.descripcion"/>&nbsp;</td>
								</logic:equal>
							</tr>
						</logic:iterate>
				
						<tr>
							<td class="paginador" align="center" colspan="20">
								<bean:define id="pager" name="impresoraSearchPageVO"/>
								<%@ include file="/base/pager.jsp" %>
							</td>
						</tr>
						
					</tbody>
				</table>
			</logic:notEmpty>
			
			<logic:empty name="impresoraSearchPageVO" property="listResult">
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

	<table class="tablabotones" width="100%">
		<tr >				
			<td align="left">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
			<logic:equal name="impresoraSearchPageVO" property="viewResult" value="true">
  	    		<td align="right">
  	    			<logic:equal name="impresoraSearchPageVO" property="modoSeleccionar" value="false">
						<bean:define id="agregarEnabled" name="impresoraSearchPageVO" property="agregarEnabled"/>
						<input type="button" <%=agregarEnabled%> class="boton" 
							onClick="submitForm('agregar', '0');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
					</logic:equal>
  	    			<logic:equal name="impresoraSearchPageVO" property="modoSeleccionar" value="true">
  	    				<logic:equal name="impresoraSearchPageVO" property="agregarEnSeleccion" value="true">
							<bean:define id="agregarEnabled" name="impresoraSearchPageVO" property="agregarEnabled"/>
							<input type="button" <%=agregarEnabled%> class="boton" 
								onClick="submitForm('agregar', '0');" 
								value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
							</logic:equal>
					</logic:equal>
				</td>
			</logic:equal>
		</tr>
	</table>
		
	<input type="hidden" name="method" value=""/>
<input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
<input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

	<input type="hidden" name="selectedId" value=""/>
	<input type="hidden" name="pageNumber" value="1" id="pageNumber">
	<input type="hidden" name="pageMethod" value="buscar" id="pageMethod">
	<input type="hidden" name="isSubmittedForm" value="true"/>
	<input type="hidden" name="name"         value="<bean:write name='impresoraSearchPageVO' property='name'/>" id="name"/>
	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/>
		
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->
