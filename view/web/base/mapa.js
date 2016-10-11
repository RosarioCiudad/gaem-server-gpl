ol = function() {
	var ol = {
			version: "1.0"
	};

	/**
	 */
	ol.openlayer = function() {
		//-->> GRS Custom Configuration -->>
		// Image Path
		OpenLayers.ImgPath = "/gait/images/openlayers/";
		// EPSG:22185
		Proj4js.defs["EPSG:22185"] = 
			"+proj=tmerc +lat_0=-90 +lon_0=-60 +k=1 +x_0=5500000 +y_0=0 +ellps=WGS84 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs";
		//<<-- GRS Custom Configuration <<--

//		var urlMapa ='http://infomapa.rosario.gov.ar';
		var urlMapa ='http://openstreetmap.org.ar';

		// Layer style
		layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);            

		//definicion del mapa
		var scales = [500, 1000, 2000, 4000, 10000, 25000, 50000, 75000, 100000, 150000, 200000];
		var resolutions = [];
		for(var i = 0; i < scales.length; i++) {
			resolutions.push(OpenLayers.Util.getResolutionFromScale(scales[i], 'm'));
		}

		var bounds = new OpenLayers.Bounds(5408191 , 6328836 , 5460856 , 6382958);
		map = new OpenLayers.Map('map', 
				{ maxExtent: bounds , 
			projection:'EPSG:22185', 
			units: 'm', 
			minScale: scales[scales.length - 1], 
			maxScale: scales[0], 
			resolution: resolutions, 
			numZoomLevels: 15, 
			controls:[
			          new OpenLayers.Control.MousePosition(),
			          new OpenLayers.Control.PanZoomBar(),
			          new OpenLayers.Control.NavToolbar(),
			          new OpenLayers.Control.LayerSwitcher(),
			          new OpenLayers.Control.Attribution()
			          ],
			          theme: null // se agrega para que no tome los estilos por defecto
				});

		this.personalizarControlLayer(map);

		var rosarioTematico = new OpenLayers.Layer.WMS('Tem\u00e1tico', urlMapa
				+ "/wms/planobase?",
				{
			layers : [ 'distritos_descentralizados',
			           'rural_metropolitana', 'manzanas_metropolitana',
			           'limites_metropolitana', 'limite_municipio',
			           'sin_manzanas', 'manzanas', 'parcelas',
			           'manzanas_no_regularizada', 'espacios_verdes',
			           'canteros', 'av_circunvalacion',
			           'avenidas_y_boulevares', 'sentidos_de_calle',
			           'via_ferroviaria', 'puentes', 'hidrografia',
			           'islas_del_parana', 'bancos_de_arena',
			           'autopistas', 'nombres_de_calles',
			           'numeracion_de_calles' ],
			           format : 'image/jpeg',
			           srs : 'EPSG:22185',
			           map_imagetype : 'jpeg'
				}, {
					singleTile : false,
					transitionEffect : 'resize',
					attribution : "© Municipalidad de Rosario"
				});
		var rosarioParcelario = new OpenLayers.Layer.WMS('Parcelario', urlMapa
				+ "/wms/planobase?", {
			layers : [ 'rural_metropolitana', 'manzanas_metropolitana',
			           'limites_metropolitana', 'limite_municipio',
			           'numeros_de_manzana', 'sin_manzanas', 'manzanas',
			           'parcelas', 'manzanas_no_regularizada', 'espacios_verdes',
			           'canteros', 'av_circunvalacion', 'avenidas_y_boulevares',
			           'segmentos_de_calle', 'sentidos_de_calle',
			           'via_ferroviaria', 'puentes', 'hidrografia',
			           'islas_del_parana', 'bancos_de_arena', 'autopistas',
			           'nombres_de_calles', 'numeracion_de_calles' ],
			           format : 'image/jpeg',
			           srs : 'EPSG:22185',
			           map_imagetype : 'jpeg'
		}, {
			singleTile : false,
			transitionEffect : 'resize',
			attribution : "© Municipalidad de Rosario"
		});
		var rosarioFoto2005 = new OpenLayers.Layer.WMS(
				'Fotos A\u00e9reas 2005',
				urlMapa + "/wms/planobase?",
				{
					layers : [ 'rural_metropolitana', 'manzanas_metropolitana',
					           'limites_metropolitana', 'FotosAreas2005',
					           'avenidas_y_boulevares', 'sentidos_de_calle',
					           'nombres_de_calles', 'numeracion_de_calles' ],
					           format : 'image/jpeg',
					           srs : 'EPSG:22185',
					           map_imagetype : 'jpeg'
				},
				{
					singleTile : false,
					transitionEffect : 'resize',
					attribution : "© Municipalidad de Rosario-Fotos A\u00e9reas 2005"
				});
		var rosarioImg2011 = new OpenLayers.Layer.WMS(
				'Imagen Satelital 2011',
				urlMapa + "/wms/planobase?",
				{
					layers : [ 'rural_metropolitana', 'manzanas_metropolitana',
					           'limites_metropolitana', 'ImagenesSatelitales2011',
					           'avenidas_y_boulevares', 'sentidos_de_calle',
					           'nombres_de_calles', 'numeracion_de_calles' ],
					           format : 'image/jpeg',
					           srs : 'EPSG:22185',
					           map_imagetype : 'jpeg'
				},
				{
					singleTile : false,
					transitionEffect : 'resize',
					attribution : "© Municipalidad de Rosario-Imagen Satelital 2011"
				});

		map.addLayers([rosarioTematico,rosarioParcelario,rosarioFoto2005,rosarioImg2011]);
		map.setBaseLayer(rosarioTematico);

		// ajuste de zoom
		var centro = bounds.getCenterLonLat();
		map.setCenter(centro,110000, false, false);
		map.zoomToScale(75000, false);
		
		
		/**
		 * 
		 */
		this.draw = function(geojson, multilayer) {
			//-- COMMON
			// Geometry Formatter
			var format = new OpenLayers.Format.GeoJSON({
				'internalProjection' : new OpenLayers.Projection("EPSG:22185"), 
				'externalProjection' : new OpenLayers.Projection("EPSG:4326") 
			});
			// Context element
			var context = {
					getFillColor : function(feature) {
						if (feature.attributes.style.fillColor) {
							return feature.attributes.style.fillColor;
						}
						return "#ee9900";
					},
					getStrokeColor : function(feature) {
						if (feature.attributes.style.strokeColor) {
							return feature.attributes.style.strokeColor;
						}
						return "#ee9900";
					},
					getLabel : function(feature) {
						if (feature.attributes.label && 
								feature.attributes.showLabel) {
							return feature.attributes.label;
						}
						return "";
					}
			};
			// Template element
			var template = {
					fillColor : "${getFillColor}",
					strokeColor : "${getStrokeColor}",
					label : "${getLabel}",
					pointRadius : 4
			};
			var style = new OpenLayers.Style(template, {
				context : context
			});
			// allow testing of specific renderers via "?renderer=Canvas", etc 
			var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
			renderer = (renderer) ? [ renderer ] : OpenLayers.Layer.Vector.prototype.renderers;
			
			
			if(multilayer){
				var layers = {};
				var features = geojson.features;
				for ( var i = 0; i < features.length; i++) {
					var key;
					if (features[i].properties.groupLabel != null){
						key = features[i].properties.groupLabel;	
					}else{
						key = features[i].properties.label;
					}
					
					if (layers[key]) {
						layers[key].push(features[i]);
					} else {
						layers[key] = [features[i]];
					}
				}
				for ( var key in layers) {
					var r = {};
					r.type = "FeatureCollection";
					r.features = layers[key];
					var layer = new OpenLayers.Layer.Vector(key, {
						styleMap : new OpenLayers.StyleMap(style),
						renderers : renderer
					});
					map.addLayer(layer);
					layer.addFeatures(format.read(r));
				}
				
				return;
			}
			
			var layer = new OpenLayers.Layer.Vector('Layer', {
				styleMap : new OpenLayers.StyleMap(style),
				renderers : renderer
			});
			
			map.addLayer(layer);
			layer.addFeatures(format.read(geojson));
		};
		
		return this;
	}
	
	/**
	 * 
	 */
	this.drawPoint = function(lon,lat) {
		var lonlat = new OpenLayers.LonLat(lon,lat); 
		console.log(lonlat);
		lonlat.transform(new OpenLayers.Projection("EPSG:4326"), 
				new OpenLayers.Projection("EPSG:22185")); 
		console.log(lonlat);
//		map.panTo(lonlat);
//		map.zoomTo(lonlat.getDataExtent().getZoomExtent());
//		--
//		allow testing of specific renderers via "?renderer=Canvas", etc 
		var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
		renderer = (renderer) ? [ renderer ] : OpenLayers.Layer.Vector.prototype.renderers;

//		Create a styleMap to style your features for two different
//		render intents.  The style for the 'default' render intent will
//		be applied when the feature is first drawn.  The style for the
//		'select' render intent will be applied when the feature is
//		selected.
		var myStyles = new OpenLayers.StyleMap({
			"default": new OpenLayers.Style({
				pointRadius: 5, // sized according to type attribute
				fillColor: "#ffcc66",
				strokeColor: "#ff9933",
				strokeWidth: 2
			}),
			"select": new OpenLayers.Style({
				fillColor: "#66ccff",
				strokeColor: "#3399ff"
			})
		});

//		Create a vector layer and give it your style map.
		var point = new OpenLayers.Layer.Vector("Point", {
			styleMap: myStyles,
			renderers : renderer
		});
		map.addLayer(point);
		point.addFeatures([new OpenLayers.Geometry.Point(lonlat)]);

		map.zoomToExtent(point.getDataExtent());

		return this;
	}


	/**
	 * Funcion que sirve para personalizar el estilo del menu que cambia entre los distintos mapas de fondo
	 * @param map
	 */
	ol.personalizarControlLayer = function(map) {
		var overlaySwitcher = map.getControlsByClass("OpenLayers.Control.LayerSwitcher")[0];

		// Set the position of OverlaySwitcher
		overlaySwitcher.div.style.right = "0px";
		overlaySwitcher.div.style.top = "5px";
		overlaySwitcher.layersDiv.style.paddingRight = "0px";
		// Set the foreground color for base layer
		overlaySwitcher.layersDiv.style.backgroundColor = "#F3831D";
		overlaySwitcher.layersDiv.style.opacity =  1;
		overlaySwitcher.baseLbl.style.color = "white";
		overlaySwitcher.baseLbl.style.fontWeight = "bolder";
		overlaySwitcher.baseLayersDiv.style.color = "white";

		// Set the forground color for none base layers
		overlaySwitcher.dataLbl.style.color = "white";
		overlaySwitcher.dataLbl.style.fontWeight = "bold";
		overlaySwitcher.dataLayersDiv.style.color = "black";
	}
	
	return ol;
}();
