<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/BuscarCampo.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
		
	<h1><bean:message bundle="apm" key="apm.campoSearchPage.title"/></h1>	
		<table class="tablabotones" width="100%">
		<tr>
			<td align="left">
				<p><bean:message bundle="apm" key="apm.campoSearchPage.legend"/></p>
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
				<!-- Etiqueta -->
				
				<td><label><bean:message bundle="apm" key="apm.campo.etiqueta.label"/>: </label></td>
				<td class="normal"><html:text name="campoSearchPageVO" property="campo.etiqueta" size="20" maxlength="100" styleClass="datos" /></td>
				
				<!-- Tratamiento-->
				
				<td><label><bean:message bundle="apm" key="apm.campo.tratamiento.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoSearchPageVO" property="campo.tratamiento.id" styleClass="select">
						<html:optionsCollection name="campoSearchPageVO" property="listTratamiento" label="value" value="id" />
					</html:select>
				</td> 
			</tr>
			<tr>
				<!-- Solo lectura -->
				<td><label><bean:message bundle="apm" key="apm.campo.soloLectura.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoSearchPageVO" property="campo.soloLectura.id" styleClass="select">
						<html:optionsCollection name="campoSearchPageVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
		
			<!-- Obligatorio -->
			
				<td><label><bean:message bundle="apm" key="apm.campo.obligatorio.label"/>: </label></td>
				<td class="normal">
					<html:select name="campoSearchPageVO" property="campo.obligatorio.id" styleClass="select">
						<html:optionsCollection name="campoSearchPageVO" property="listSiNo" label="value" value="id" />
					</html:select>
				</td>
			</tr>
			<tr>
			
			<!-- Tabla busqueda -->
			<td><label><bean:message bundle="apm" key="apm.campo.tablaBusqueda.label"/>: </label></td>
			<td class="normal"><html:text name="campoSearchPageVO" property="campo.tablaBusqueda" size="20" maxlength="100" styleClass="datos" /></td>
			
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
		
	<!-- Resultado Filtro -->
	<div id="resultadoFiltro">
		<logic:equal name="campoSearchPageVO" property="viewResult" value="true">
			<logic:notEmpty  name="campoSearchPageVO" property="listResult">	
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
	               	<tbody>
		               	<tr>
							<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
							<logic:notEqual name="campoSearchPageVO" property="modoSeleccionar" value="true">
								<th width="1">&nbsp;</th> <!-- Modificar -->
								<th width="1">&nbsp;</th> <!-- Eliminar -->
							</logic:notEqual>
							
							<th align="left"><bean:message bundle="apm" key="apm.campo.tratamiento.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.campo.etiqueta.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.campo.obligatorio.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.campo.soloLectura.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.campo.valorDefault.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.campo.codigo.label"/></th>
						
						</tr>
							
						<logic:iterate id="CampoVO" name="campoSearchPageVO" property="listResult">
							<tr>
								<!-- Seleccionar -->
								<logic:equal name="campoSearchPageVO" property="modoSeleccionar" value="true">
									<td>	
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('seleccionar', '<bean:write name="CampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</td>
								</logic:equal>									
								<logic:notEqual name="campoSearchPageVO" property="modoSeleccionar" value="true">
									<!-- Ver -->
									<td>
										<logic:equal name="campoSearchPageVO" property="verEnabled" value="enabled">									
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('ver', '<bean:write name="CampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
											</a>
										</logic:equal>
										<logic:notEqual name="CampoVO" property="verEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
										</logic:notEqual>
									</td>	
									<!-- Modificar-->								
									<td>
										<logic:equal name="campoSearchPageVO" property="modificarEnabled" value="enabled">
											<logic:equal name="CampoVO" property="modificarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificar', '<bean:write name="CampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif"/>
												</a>
											</logic:equal>
											<logic:notEqual name="CampoVO" property="modificarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="campoSearchPageVO" property="modificarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
										</logic:notEqual>
									</td>
	
									<!-- Eliminar-->								
									<td>
										<logic:equal name="campoSearchPageVO" property="eliminarEnabled" value="enabled">
											<logic:equal name="CampoVO" property="eliminarEnabled" value="enabled">
												<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminar', '<bean:write name="CampoVO" property="id" bundle="base" formatKey="general.format.id"/>');">
													<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif"/>
												</a>
											</logic:equal>	
											<logic:notEqual name="CampoVO" property="eliminarEnabled" value="enabled">
												<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="campoSearchPageVO" property="eliminarEnabled" value="enabled">										
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
										</logic:notEqual>
									</td>
								</logic:notEqual>
								<td><bean:write name="CampoVO" property="tratamiento.value"/>&nbsp;</td>
								<td><bean:write name="CampoVO" property="etiqueta"/>&nbsp;</td>
								<td><bean:write name="CampoVO" property="obligatorio.value"/>&nbsp;</td>
								<td><bean:write name="CampoVO" property="soloLectura.value"/>&nbsp;</td>
								<td><bean:write name="CampoVO" property="valorDefault"/>&nbsp;</td>
								<td><bean:write name="CampoVO" property="codigo"/>&nbsp;</td>
							</tr>
						</logic:iterate>
				
						<tr>
							<td class="paginador" align="center" colspan="20">
								<bean:define id="pager" name="campoSearchPageVO"/>
								<%@ include file="/base/pager.jsp" %>
							</td>
						</tr>
						
					</tbody>
				</table>
			</logic:notEmpty>
			
			<logic:empty name="campoSearchPageVO" property="listResult">
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
			<logic:equal name="campoSearchPageVO" property="viewResult" value="true">
  	    		<td align="right">
  	    			<logic:equal name="campoSearchPageVO" property="modoSeleccionar" value="false">
						<bean:define id="agregarEnabled" name="campoSearchPageVO" property="agregarEnabled"/>
						<input type="button" <%=agregarEnabled%> class="boton" 
							onClick="submitForm('agregar', '0');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
					</logic:equal>
  	    			<logic:equal name="campoSearchPageVO" property="modoSeleccionar" value="true">
  	    				<logic:equal name="campoSearchPageVO" property="agregarEnSeleccion" value="true">
							<bean:define id="agregarEnabled" name="campoSearchPageVO" property="agregarEnabled"/>
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
	<input type="hidden" name="name"         value="<bean:write name='campoSearchPageVO' property='name'/>" id="name"/>
	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/>
		
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->
