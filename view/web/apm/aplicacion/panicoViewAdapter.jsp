<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!-- Tabla que contiene todos los formularios -->
<html:form styleId="filter" action="/apm/AdministrarPanico.do">

	<!-- Mensajes y/o Advertencias -->
	<%@ include file="/base/warning.jsp" %>
	<!-- Errors  -->
	<html:errors bundle="base"/>
	
	<h1><bean:message bundle="apm" key="apm.panicoViewAdapter.title"/></h1>	

	<table class="tablabotones" style="width: 100%;" >
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

		var style = OpenLayers.Util.extend(
		    {},
		    OpenLayers.Feature.Vector.style['default']
		);
		style.pointRadius = 20;
		style.fillColor = "#FF3333";
		style.fillOpacity = 0.4;
		style.strokeColor = "#FF0000";
		style.strokeWidth = 2;
		style.strokeLinecap = "butt";

		var pointFeature = new OpenLayers.Feature.Vector(point, null, style);

		// Crear una capa vectorial
		var vectorLayer = new OpenLayers.Layer.Vector("Pánico Seleccionado", {
			style : style
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
		//***********
	</script>

	<table class="tablabotones" style="width: 100%;" >
	   	<tr>
  	   		<td align="left" width="50%">
	   	    	<html:button property="btnVolver"  styleClass="boton" onclick="submitForm('volver', '');">
					<bean:message bundle="base" key="abm.button.volver"/>
				</html:button>	   	    			
   	    	</td>
	    </tr>
	</table>

   	<input type="hidden" name="report.reportFormat" value="1" id="reportFormat"/> 	

	<input type="hidden" name="method" value=""/>
    <input type="hidden" name="anonimo" value="<bean:write name="userSession" property="isAnonimoView"/>"/>
    <input type="hidden" name="urlReComenzar" value="<bean:write name="userSession" property="urlReComenzar"/>"/>

	<input type="hidden" name="selectedId" value=""/>
	<input type="hidden" name="isSubmittedForm" value="true"/>

</html:form>
<!-- Fin Tabla que contiene todos los formularios -->