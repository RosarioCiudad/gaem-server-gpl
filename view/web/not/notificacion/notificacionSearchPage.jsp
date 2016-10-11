<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/not/BuscarNotificacion.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
		
	<h1><bean:message bundle="not" key="not.title"/></h1>	
		
	<table class="tablabotones" width="100%">
		<tr>
			<td align="left">
				<p><bean:message bundle="not" key="not.notificacionSearchPage.legend"/></p>
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
				<td><label><bean:message bundle="not" key="not.notificacion.descripcionReducida.label"/>: </label></td>
				<td class="normal"><html:text name="notificacionSearchPageVO" property="notificacion.descripcionReducida" size="15" maxlength="20" styleClass="datos" /></td>
			
				<td><label><bean:message bundle="not" key="not.notificacion.descripcionAmpliada.label"/>: </label></td>
				<td class="normal"><html:text name="notificacionSearchPageVO" property="notificacion.descripcionAmpliada" size="20" maxlength="100" styleClass="datos" /></td>
			</tr>
			
			<tr>	
				<td><label><bean:message bundle="not" key="not.notificacion.estadoNotificacion.label"/>: </label></td>
				<td class="normal">
					<html:select name="notificacionSearchPageVO" property="notificacion.estadoNotificacion.id" styleClass="select">
						<html:optionsCollection name="notificacionSearchPageVO" property="listEstadoNotificacion" label="descripcion" value="id" />
					</html:select>
				</td>	
				
				
				 <td><label><bean:message bundle="not" key="not.notificacion.aplicacion.label"/>: </label></td>
				<td class="normal">
					<html:select name="notificacionSearchPageVO" property="notificacion.aplicacion.id" styleClass="select">
						<html:optionsCollection name="notificacionSearchPageVO" property="listAplicacion" label="descripcion" value="id" />
					</html:select>
				</td>	 
							
			</tr>
			
			<tr>
				
				<td><label><bean:message bundle="not" key="not.notificacion.tipoNotificacion.label"/>: </label></td>
				<td class="normal">
					<html:select name="notificacionSearchPageVO" property="notificacion.tipoNotificacion.id" styleClass="select">
						<html:optionsCollection name="notificacionSearchPageVO" property="listTipoNotificacion" label="descripcion" value="id" />
					</html:select>
				</td>
				
				
				<td><label><bean:message bundle="not" key="not.notificacion.dispositivoMovil.label"/>: </label></td>
				<td class="normal">
					<html:select name="notificacionSearchPageVO" property="notificacion.dispositivoMovil.id" styleClass="select">
						<html:optionsCollection name="notificacionSearchPageVO" property="listDispositivoMovil" label="descripcion" value="id" />
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
		
	<!-- Resultado Filtro -->
	<div id="resultadoFiltro">
		<logic:equal name="notificacionSearchPageVO" property="viewResult" value="true">
			<logic:notEmpty  name="notificacionSearchPageVO" property="listResult">	
				<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
					<caption><bean:message bundle="base" key="base.resultadoBusqueda"/></caption>
	               	<tbody>
		               	<tr>
							<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
							
							<th align="left"><bean:message bundle="not" key="not.notificacion.tipoNotificacion.label"/></th>
							<th align="left"><bean:message bundle="not" key="not.notificacion.descripcionReducida.label"/></th>
							<th align="left"><bean:message bundle="not" key="not.notificacion.aplicacion.label"/></th>
							<th align="left"><bean:message bundle="not" key="not.notificacion.dispositivoMovil.label"/></th>
							<th align="left"><bean:message bundle="not" key="not.notificacion.estadoNotificacion.label"/></th>
						</tr>
							
						<logic:iterate id="notificacionVO" name="notificacionSearchPageVO" property="listResult">
							<tr>
								<!-- Seleccionar -->
								<logic:equal name="notificacionSearchPageVO" property="modoSeleccionar" value="true">
									<td>	
										<a style="cursor: pointer; cursor: hand;" onclick="submitForm('seleccionar', '<bean:write name="notificacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
											<img title="<bean:message bundle="base" key="abm.button.seleccionar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
										</a>
									</td>
								</logic:equal>									
								<logic:notEqual name="notificacionSearchPageVO" property="modoSeleccionar" value="true">
									<!-- Ver -->
									<td>
										<logic:equal name="notificacionSearchPageVO" property="verEnabled" value="enabled">									
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('ver', '<bean:write name="notificacionVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
											</a>
										</logic:equal>
										<logic:notEqual name="notificacionVO" property="verEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
										</logic:notEqual>
									</td>	

								</logic:notEqual>
								<td><bean:write name="notificacionVO" property="tipoNotificacion.descripcion"/>&nbsp;</td>
								<td><bean:write name="notificacionVO" property="descripcionReducida"/>&nbsp;</td>
								<td><bean:write name="notificacionVO" property="aplicacion.descripcion"/>&nbsp;</td>
								<td><bean:write name="notificacionVO" property="dispositivoMovil.descripcion"/>&nbsp;</td>
								<td><bean:write name="notificacionVO" property="estadoNotificacion.descripcion"/>&nbsp;</td>
							</tr>
						</logic:iterate>
				
						<tr>
							<td class="paginador" align="center" colspan="20">
								<bean:define id="pager" name="notificacionSearchPageVO"/>
								<%@ include file="/base/pager.jsp" %>
							</td>
						</tr>
						
					</tbody>
				</table>
			</logic:notEmpty>
			
			<logic:empty name="notificacionSearchPageVO" property="listResult">
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

	<table class="tablabotones" width="100%" >
		<tr>				
			<td align="left" width="50%">
    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>
			</td>
			<logic:equal name="notificacionSearchPageVO" property="viewResult" value="true">
				<td align="right" width="50%">
  	    			<logic:equal name="notificacionSearchPageVO" property="modoSeleccionar" value="false">
						<bean:define id="agregarEnabled" name="notificacionSearchPageVO" property="agregarEnabled"/>
						<input type="button" <%=agregarEnabled%> class="boton" 
							onClick="submitForm('agregar', '0');" 
							value="<bean:message bundle="base" key="abm.button.agregar"/>"/>
					</logic:equal>
  	    			<logic:equal name="notificacionSearchPageVO" property="modoSeleccionar" value="true">
  	    				<logic:equal name="notificacionSearchPageVO" property="agregarEnSeleccion" value="true">
							<bean:define id="agregarEnabled" name="notificacionSearchPageVO" property="agregarEnabled"/>
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
	
	<input type="hidden" name="name"         value="<bean:write name='notificacionSearchPageVO' property='name'/>" id="name"/>
	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/>
	

	<!-- Inclusion del Codigo Javascript del Calendar-->
	<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>		
</html:form>
<!-- Fin Tabla que contiene todos los formularios -->