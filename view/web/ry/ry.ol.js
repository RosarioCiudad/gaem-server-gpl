ol = function() {
	var ol = {
			version: "1.0"
	};

	var fpControl = new OpenLayers.Control.FeaturePopups({boxSelectionOptions: {}});
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

		// Layer style
		layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);            

		map = new OpenLayers.Map(
				{ 	div: "map",
					units: 'm', 
					controls:[
					          new OpenLayers.Control.MousePosition(),
					          new OpenLayers.Control.PanZoomBar(),
					          new OpenLayers.Control.NavToolbar(),
					          new OpenLayers.Control.LayerSwitcher(),
					          new OpenLayers.Control.Attribution()
					          ],
				theme: null // se agrega para que no tome los estilos por defecto
				});
		var bounds = new OpenLayers.Bounds(-60.730019 , -33.017684 , -60.616722 , -32.872475);

		//definimos las proyecciones
		var lonlat = new OpenLayers.LonLat(-60.7012926, -31.6207752).transform(
				new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
				new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator (definida por google)
		);

		var zoom = 13;

		this.personalizarControlLayer(map); 

//		var markers = new OpenLayers.Layer.Markers( "Markers" );
//		map.addLayer(markers);
//		markers.addMarker(new OpenLayers.Marker(lonlat));
		var mapnik = new OpenLayers.Layer.OSM();
		map.addLayer(mapnik);

		map.setCenter(lonlat, zoom);//, false, false);

		/**
		 * 
		 */
		this.draw = function(geojson, multilayer, showArrow, showInfo) {
			//-- COMMON
			// Geometry Formatter
			var format = new OpenLayers.Format.GeoJSON({
				'internalProjection' : new OpenLayers.Projection("EPSG:900913"), 
				'externalProjection' : new OpenLayers.Projection("EPSG:4326") 
			});
			//var format = new OpenLayers.Format.GeoJSON();
			// Context element
			var context = {
					getFillColor : function(feature) {
						if (feature.attributes.style) {
							if (feature.attributes.style.fillColor) {
								return feature.attributes.style.fillColor;
							}
						}
						//return "#ee9900";
						return "#000000"; 
					},
					getStrokeColor : function(feature) {
						if (feature.attributes.style) {
							if (feature.attributes.style.strokeColor) {
								return feature.attributes.style.strokeColor;
							}
						}
//						return "#ee9900";
						return "#FFFFFF"; 
					},
					getLabel : function(feature) {
						if (feature.attributes.label && 
								feature.attributes.showLabel) {
							return feature.attributes.label;

						}else if (feature.attributes.pointLabel && 
								feature.attributes.showPointLabel){
							return feature.attributes.pointLabel;
						}
						return "";
					},
					getYOffset : function(feature) {
						if (feature.attributes.style) {
							if (feature.attributes.style.labelYOffset) {
								return feature.attributes.style.labelYOffset;
							}
						}
						return "0";
					},
					getGraphicName : function(feature) {
						if (feature.attributes.angle) { //si es una flecha de direcci\u00f3n
							return "triangle";
						}
						return "";
					},
					getFillOpacity : function(feature){
						if (feature.attributes.angle) { //si es una flecha de direcci\u00f3n
							return "0.5";
						}
						return "1";
					},
					getAngle : function(feature){
						if (feature.attributes.angle) { //si es una flecha de direcci\u00f3n
							return feature.attributes.angle;
						}
						return "0";
					},
					getPointRadius : function(feature){
						if(map.getScale() > 30000){
							if (feature.attributes.angle) { //si es una flecha de direcci\u00f3n
								return "4";
							}
							return "2";
						}else{
							if (feature.attributes.angle) { //si es una flecha de direcci\u00f3n
								return "6";
							}
							return "3";
						}
					}
			};
			// Template element
//			var template = {
//			fillColor : "${getFillColor}",
//			strokeColor : "${getStrokeColor}",
//			label : "${getLabel}",
//			pointRadius : 4,
//			labelYOffset: "${getYOffset}",
//			};
			//var style = new OpenLayers.Style(template, {
			var style = new OpenLayers.Style({}, {
				context : context,
				rules: [ 
				        new OpenLayers.Rule({
				        	maxScaleDenominator: 30000,
				        	symbolizer: {
				        		fillColor : "${getFillColor}",
				        		strokeColor : "${getStrokeColor}",
				        		label : "${getLabel}",
				        		pointRadius : "${getPointRadius}",//3,
				        		labelYOffset: "${getYOffset}",
				        		graphicName:"${getGraphicName}",
				        		fontSize: "12px",
				        		strokeWidth: 2, 
				        		fillOpacity: "${getFillOpacity}",
				        		rotation : "${getAngle}"

				        	}
				        }),
				        new OpenLayers.Rule({
				        	minScaleDenominator: 30000,
				        	symbolizer: {
				        		fillColor : "${getFillColor}",
				        		strokeColor : "${getStrokeColor}",
				        		label : "${getLabel}",
				        		pointRadius : "${getPointRadius}",//2,
				        		labelYOffset: "${getYOffset}",
				        		graphicName:"${getGraphicName}",
				        		fontSize: "9px",
				        		strokeWidth: 1, 
				        		fillOpacity: "${getFillOpacity}",
				        		rotation : "${getAngle}"
				        	}
				        })
				        ]
			});
			// allow testing of specific renderers via "?renderer=Canvas", etc 
			var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
			renderer = (renderer) ? [ renderer ] : OpenLayers.Layer.Vector.prototype.renderers;


			if(multilayer){
				var layers = {};
				var features = geojson.features;
				var color;
				for ( var i = 0; i < features.length; i++) {
					key = features[i].properties.label;
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

					color = "";
					if(r.features[0].properties.style.fillColor){
						color = r.features[0].properties.style.fillColor;
						color = '<span style="background-color:'+color+';color:'+color+'">' + "-----"+'</span>&nbsp;&nbsp;';
					}
					/*transforma las coordenadas si su valor absoluto supera 180*/
					for (var k in r.features) {
						var datapoint = new OpenLayers.LonLat(r.features[k].geometry.coordinates[0], r.features[k].geometry.coordinates[1]);
						var proj_1 = new OpenLayers.Projection("EPSG:4326")
						var proj_2 = new OpenLayers.Projection("EPSG:900913");

						if (Math.abs(r.features[k].geometry.coordinates[0])>180){
							datapoint.transform(proj_1, proj_2);
							r.features[k].geometry.coordinates[0] = datapoint.lon;
							r.features[k].geometry.coordinates[1] = datapoint.lat;
						}
					}


					var layer = new OpenLayers.Layer.Vector(color+ key, {
						styleMap : new OpenLayers.StyleMap(style),
						renderers : renderer
					});
					map.addLayer(layer);
					layer.addFeatures(format.read(r));

					if(showArrow)
						this.addDirection(map, key, layer, false);

					if (showInfo)
						fpControl.addLayer(layer,
								{templates: {
									hover: '${getTitle}',
									single: "${getDescription}",
									item: '<li><a href="#" ${showPopup()}>${getTitle}</a></li>'
								},
								featureContext: {
									getDescription : function(feature) {
										var descrip = "";
										if (feature.attributes.descriptitles) {
											for(var i = 0; i < feature.attributes.descriptitles.length; i++) {
												descrip += '<u>'+feature.attributes.descriptitles[i]+'</u>: '+feature.attributes.descriptions[i]+' <br />';
											}
										}
										return descrip;
									},
									getTitle : function(feature){
										var title = "";
										if (feature.attributes.title){
											title = attributes.title;
										}
										return title;
									}
								}
								});
				}

				return;
			}

			var layer = new OpenLayers.Layer.Vector('Layer', {
				styleMap : new OpenLayers.StyleMap(style),
				renderers : renderer
			});

			map.addLayer(layer);
			layer.addFeatures(format.read(geojson));

			if(showArrow)
				this.addDirection(map, 'Layer', layer, false);

			if (showInfo)
				fpControl.addLayer(layer,
						{templates: {
							hover: '${getTitle}',
							single: "${getDescription}",
							item: '<li><a href="#" ${showPopup()}>${getTitle}</a></li>'
						},
						featureContext: {
							getDescription : function(feature) {
								var descrip = "";
								if (feature.attributes.descriptitles){
									for(var i = 0; i < feature.attributes.descriptitles.length; i++) {
										descrip += '<u>'+feature.attributes.descriptitles[i]+'</u>: '+feature.attributes.descriptions[i]+' <br />';
									}
								}
								return descrip;
							},
							getTitle : function(feature){
								var title = "";
								if (feature.attributes.title){
									title = attributes.title;
								}
								return title;
							}
						}
						});
		};

		return this;
	}

	/**
	 * Funcion que sirve para añadir flechas direccionadas a los trayectos en un mapa.
	 * @param map
	 * @param key
	 * @param layer
	 * @param onNewLayer
	 */
	ol.addDirection = function(map, key, layer, onNewLayer) {

		var points=[];
		var features =layer.features;
		for (var i=0;i<features.length ;i++ ){
			var linePoints = createDirection(features[i].geometry,'middle',true) ;
			for (var j=0;j<linePoints.length ;j++ ) {
				linePoints[j].attributes.lineFid = features[i].fid;
			}
			points =points.concat(linePoints);
		}

		if(onNewLayer){
			var styleMapTUP = new OpenLayers.StyleMap({ pointRadius:6, 
				graphicName:"triangle",
				rotation : "${angle}",
				strokeWidth: 1, 
				strokeColor: "#FFFFFF", 
				fillColor: "#000000", 
				fillOpacity: 0.5});
			var dirLayer = new OpenLayers.Layer.Vector(key + " Sentido", {styleMap: styleMapTUP});

			map.addLayer(dirLayer);
			dirLayer.addFeatures(points);
		}else{
			layer.addFeatures(points);
		}
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

