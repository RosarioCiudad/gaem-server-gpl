<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

	<!-- Tabla que contiene todos los formularios -->
	<html:form styleId="filter" action="/apm/AdministrarPanico.do">

		<!-- Mensajes y/o Advertencias -->
		<%@ include file="/base/warning.jsp" %>
		<!-- Errors  -->
		<html:errors bundle="base"/>
		
		<h1><bean:message bundle="apm" key="apm.panicoAdapter.title"/></h1>	
		
		<table class="tablabotones" style="width: 100%;">
			<tr>			
				<td align="right">
		   			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>
			</tr>
		</table>
		
		<!-- Info Panico -->
		<fieldset>
			<legend><bean:message bundle="apm" key="apm.panico.label"/></legend>
			<table class="tabladatos">
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.fechaRecepcion.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.fechaRecepcionView"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.horaRecepcion.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.horaRecepcionView"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.fecha.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.fechaPanicoView"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.hora.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.horaPanicoView"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.area.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.area.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.area.direccion.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.area.direccion.descripcion"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.descripcion.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.dispositivoMovil.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.numeroSerie.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.dispositivoMovil.numeroSerie"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.estado.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.estadoPanico.descripcion"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.numeroLinea.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.dispositivoMovil.numeroLinea"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.inspector.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.usuarioPanico.nombre"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.origen.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.origen"/></td>
				</tr>
				<tr>
					<td><label><bean:message bundle="apm" key="apm.panico.longitud.label"/>: </label></td>
					<td class="normal"><bean:write name="panicoAdapterVO" property="panico.longitudView"/></td>
					
					<td><label><bean:message bundle="apm" key="apm.panico.latitud.label"/>: </label></td>
					<td class="normal"><bean:write  name="panicoAdapterVO" property="panico.latitudView"/></td>
				</tr>
				<logic:equal name="panicoAdapterVO" property="panico.latitud" value="0.0">
					<logic:equal name="panicoAdapterVO" property="panico.longitud" value="0.0">
						<tr>
							<td class="error" colspan="4" style="text-align:center">
								No se pudo establecer la ubicación.
							</td>
						</tr>
					</logic:equal>	
				</logic:equal>	
			</table>
		</fieldset>
		
		<!-- Mapa -->
		<fieldset>
			<div style="height:600px;" id="map"></div>
		</fieldset>
		
		<!-- HisEstPan -->		
		<logic:notEmpty  name="panicoAdapterVO" property="panico.listHisEstPan">
			<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">            
				<caption><bean:message bundle="apm" key="apm.panico.listHisEstPan.label"/></caption>
		    	<tbody>
					<logic:notEmpty  name="panicoAdapterVO" property="panico.listHisEstPan">	    	
				    	<tr>
							<th width="1">&nbsp;</th> <!-- Ver -->
							<th width="1">&nbsp;</th> <!-- Modificar -->
							<th width="1">&nbsp;</th> <!-- Eliminar -->
							<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.fecha.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.hora.label"/></th>
							<th align="left"><bean:message bundle="base" key="base.estado.label"/></th>
							<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.usuario.label"/></th>						
							<th align="left"><bean:message bundle="apm" key="apm.hisEstPan.observaciones.label"/></th>						
						</tr>
						<logic:iterate id="HisEstPanVO" name="panicoAdapterVO" property="panico.listHisEstPan">
				
							<tr>
								<!-- Ver -->
								<td>
									<logic:equal name="panicoAdapterVO" property="verHisEstPanEnabled" value="enabled">
										<logic:equal name="HisEstPanVO" property="verEnabled" value="enabled">
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('verHisEstPan', '<bean:write name="HisEstPanVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.ver"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
											</a>
										</logic:equal>	
										<logic:notEqual name="HisEstPanVO" property="verEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="panicoAdapterVO" property="verHisEstPanEnabled" value="enabled">										
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/selec1.gif"/>
									</logic:notEqual>
								</td>
								
								<!-- Modificar-->								
								<td>
									<logic:equal name="panicoAdapterVO" property="modificarHisEstPanEnabled" value="enabled">
										<logic:equal name="HisEstPanVO" property="modificarEnabled" value="enabled">
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('modificarHisEstPan', '<bean:write name="HisEstPanVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.modificar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/modif0.gif"/>
											</a>
										</logic:equal>	
										<logic:notEqual name="HisEstPanVO" property="modificarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="panicoAdapterVO" property="modificarHisEstPanEnabled" value="enabled">
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/modif1.gif"/>
									</logic:notEqual>
								</td>
								
								<!-- Eliminar-->								
								<td>
									<logic:equal name="panicoAdapterVO" property="eliminarHisEstPanEnabled" value="enabled">
										<logic:equal name="HisEstPanVO" property="eliminarEnabled" value="enabled">
											<a style="cursor: pointer; cursor: hand;" onclick="submitForm('eliminarHisEstPan', '<bean:write name="HisEstPanVO" property="id" bundle="base" formatKey="general.format.id"/>');">
												<img title="<bean:message bundle="base" key="abm.button.eliminar"/>" border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar0.gif"/>
											</a>
										</logic:equal>	
										<logic:notEqual name="HisEstPanVO" property="eliminarEnabled" value="enabled">
											<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="panicoAdapterVO" property="eliminarHisEstPanEnabled" value="enabled">
										<img border="0" src="<%=request.getContextPath()%>/images/iconos/eliminar1.gif"/>
									</logic:notEqual>
								</td>
								<td><bean:write name="HisEstPanVO" property="fechaView"/>&nbsp;</td>
								<td><bean:write name="HisEstPanVO" property="horaView"/>&nbsp;</td>
								<td><bean:write name="HisEstPanVO" property="estadoPanico.descripcion"/>&nbsp;</td>
								<td><bean:write name="HisEstPanVO" property="usuario"/>&nbsp;</td>
								<td><bean:write name="HisEstPanVO" property="observaciones"/>&nbsp;</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="panicoAdapterVO" property="panico.listHisEstPan">
						<tr>
							<td align="center">
								<bean:message bundle="base" key="base.noExistenRegitros"/>
							</td>
						</tr>
					</logic:empty>
				</tbody>
			</table>
		</logic:notEmpty>
		<!-- HisEstPan -->
				
		<table class="tablabotones"  style="width: 100%;">
			<tr>				
				<td align="left" width="50%" >
	    			<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
						<bean:message bundle="base" key="abm.button.volver"/>
					</html:button>
				</td>  
				
				<!-- Cambiar Estado -->								
				<td>
					<logic:notEmpty name="panicoAdapterVO" property="listEstadoPanico">
						<td align="right" width="100%" > 
						<bean:define id="cambiarEstadoEnabled" name="panicoAdapterVO" property="cambiarEstadoEnabled"/>
						<input type="button" class="boton" <%=cambiarEstadoEnabled%> onClick="submitForm('cambiarEstado', 
							'<bean:write name="panicoAdapterVO" property="panico.id" bundle="base" formatKey="general.format.id"/>');" 
							value="<bean:message bundle="apm" key="apm.panico.cambiarEstado.label"/>"/>		
						</td>
					</logic:notEmpty>
				</td>	
			</tr>
		</table>
		
		<script>
			var ol = ol.openlayer(); 
			//--
			// Zoom
			var lonlat = new OpenLayers.LonLat('<bean:write name="panicoAdapterVO" property="panico.longitudView" filter="false" />', 
			   		'<bean:write name="panicoAdapterVO" property="panico.latitudView" filter="false" />');
			lonlat.transform(new OpenLayers.Projection("EPSG:4326"), 
			 		new OpenLayers.Projection("EPSG:22185")); 
			// Crear la Feature
			var point = new OpenLayers.Geometry.Point(
					'<bean:write name="panicoAdapterVO" property="panico.longitudView" filter="false" />', 
			   		'<bean:write name="panicoAdapterVO" property="panico.latitudView" filter="false" />');
			point.transform(new OpenLayers.Projection("EPSG:4326"), 
					 		new OpenLayers.Projection("EPSG:22185")); 
	
			var mStyle = OpenLayers.Util.extend(
			    {},
			    OpenLayers.Feature.Vector.style['default']
			);
			mStyle.pointRadius = 20;
			mStyle.fillColor = "#FF3333";
			mStyle.fillOpacity = 0.4;
			mStyle.strokeColor = "#FF0000";
			mStyle.strokeWidth = 2;
			mStyle.strokeLinecap = "butt";
	
			var pointFeature = new OpenLayers.Feature.Vector(point, null, mStyle);
	
			// Crear una capa vectorial
			var vectorLayer = new OpenLayers.Layer.Vector("Pánico Seleccionado", {
				style : mStyle
			});
			// Añadir las features a la capa vectorial
			vectorLayer.addFeatures([ pointFeature ]);
			// Añadir la capa vectorial al mapa
			map.addLayer(vectorLayer);
			//map.zoomToExtent(vectorLayer.getDataExtent());
			map.setCenter(lonlat, 10);
			
			//--
			//***********
			var listAnulado = '<bean:write name="panicoAdapterVO" property="listAnulado" filter="false" />';
			var anulados = JSON.parse(listAnulado);
			ol.draw(anulados, true)
	
			var listAtendido = '<bean:write name="panicoAdapterVO" property="listAtendido" filter="false" />';
			var atendidos = JSON.parse(listAtendido);
			ol.draw(atendidos, true)
			
			var listPendiente = '<bean:write name="panicoAdapterVO" property="listPendiente" filter="false" />';
			var pendientes = JSON.parse(listPendiente);
			ol.draw(pendientes, true)
	
			var listPosicion = '<bean:write name="panicoAdapterVO" property="listPosicion" filter="false" />';
			var posicion = JSON.parse(listPosicion);
			ol.draw(posicion, true)
//			console.log('<bean:write name="panicoAdapterVO" property="listPendiente" filter="false" />');
//			console.log('<bean:write name="panicoAdapterVO" property="listPosicion" filter="false" />');
			
			//***********
		</script>
	   	
		<input type="hidden" name="method" value=""/>
        <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
        <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

		<input type="hidden" name="selectedId" value=""/>
		<input type="hidden" name="isSubmittedForm" value="true"/>
		
	</html:form>
	<!-- Fin Tabla que contiene todos los formularios -->