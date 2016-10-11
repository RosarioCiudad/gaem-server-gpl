/*
Modulo de funcionales utiles de grs
 */

(function () { 
	var util = {};

	// quote, pone entre comillas simples al parametro 'v'.
	// Si v es un array, retorna una nuevo array con cada elemento entre comillas. 
	util.quote = function (v) {
		if (v == null)
			return v;

		///XXX optimizar para String	
		if (v[0] != null) { //es una array o algo iterable
			var t = [];

			for(var i=0;i < v.length; i++) {
				t.push("'" + v[i] + "'");
			}
			return t;
		}

		return v;
	}
	
	// Join, genera multiples push, por cada elemento de la lista ingresada en el segundo parametro.
	// d = array destino.
	// l = lista a agregar.
	util.join = function (d,v){
		var ret = d.slice();
		for(var i=0;i < v.length; i++){
			ret.push(v[i]);
		}
		return ret;
	}
		
	// MaxDate, retorna la mayor entre dos fechas
	// d1 = date1
	// d2 = date2
	util.maxDate = function(d1,d2){
		if(null == d1)
			return d2;
		if(null == d2)
			return d1;
		
		if(d1 > d2)
			return d1;
		else
			return d2;
	}
	
	// Obtiene la fecha en formato dd/mm/aaaa
	util.formatDate = function(date) {
		var ret = [];
		var day = date.getDate();
		ret[0] = day < 10 ? '0' + day : '' + day;
		
		var month = date.getMonth() + 1;
		ret[1] = month < 10 ? '0' + month : '' + month;
		
		ret[2] = date.getFullYear();
		
		return ret.join("/");
	}
	
	//mkReport simplifica la generacion de reportes. Crea el archivo, da de alta el archivo en los
	//files de adp, e invoca a la funcion freport, con params.
	//mkRerport unifica las llamadas a los moudlos de Grs.Out, Grs.Adp
	//	path: string: directorio y prefijo del nombre de archivo a generar. ej: salida/Reporte1
	//	type: string: "csv" o "html". (ver modulo Grs.Out)
	//	options: object: con opciones de renderizacon. (ver modulo Grs.Out)
	//	title: title de Adp.addFile()
	//	desc: description de Adp.addFile()
	//	freport: funcion que genera el reporte de la forma: freport(out, params)
	//	params: parametros para pasar como segundo argumento a freport.
	util.mkReport = function(path, type, options, title, desc, freport, params) {
			var Adp = Grs.adp();
			
			var tpath, tout, tcount;
			tpath = Adp.makeFilePath(path, type);
			if (type == "html")
				tout = Grs.outHtml(tpath, options);
			if (type == "csv")
				tout = Grs.outCsv(tpath, options);
			tcount = freport(tout, params);
			Adp.addFile(tpath, title, desc, tcount);
			tout.close();
	}
	
	//empty return true if each item of arguments or array argument is empty
	//a item is empty if it is null, undefined or if is a ''
	//if you path an array as arguments its check evey item in the array
	util.empty = function() {
		// iterate through non-separator arguments
		for (var i = 0; i < arguments.length; i++) {
			var item = arguments[i];
			if (item === 0)
				return false;
			
			if (Array.isArray(item) || (item != null && typeof item != 'string' && typeof item.length != 'undefined')) {				
				for (var j = 0; j < item.length; j++) {
					if (!this.empty(item[j]))
						return false;
				}
				return true;
			}
			
			if ((item != null || item != undefined) && !(item === ''))
				return false;
		}
		
		return true;
	}
	
	//debuSql emite la salida del sql al debug.
	//si debug esta en false no hace nada
	util.debugSql = function(Sql, sql, limit) {
		if (!Grs.debugflg)
			return;
			
		var cr = Sql.cursor(sql);
		var head = true;
		var n = 0;
		while (row = cr.read()) {
			if (n > limit)
				break;
			
			if (head) {
				for (var i in row)
					Grs.printf("%s", i + "|");
				Grs.printf("\n");
				head = false;
			}
			for (var i in row)
				Grs.printf("%s", row[i] + "|");
			Grs.printf("\n");
			n++;
		}
		cr.close();
	}
	
	util.addFile = function (outfiles, description, count) {
		var files = (outfiles instanceof Array) ? outfiles : [outfiles];   
		
		files.map(function (file) {
			var outpath = Grs.fspath(file);
			if (count == null)
				count = 1;
			
			Grs.adp().addFile(outpath, file, description, count);
		});
	}
	
	util.mkOutput = function(outfiles, fnout, data, options) {
		var files = (outfiles instanceof Array) ? outfiles : [outfiles];   
		var count = 0;
		files.map(function (file) {
			var outpath = Grs.fspath(file);
			var out = Grs.out(outpath, options || {separator:","});
			
			if (typeof(fnout) === "function")
				count = fnout(out, data);
			else if (fnout != null)
				out.data(fnout);
			
			out.close();
		});
		
		return count;
	}
	
	util.mkResume = function(outfile, sourcefile, options) {
		var out = Grs.template(outfile, options || {});
		out.include('<grs>/html/rshead.html')
			.include(sourcefile)
			.include('<grs>/html/rsfoot.html')
			.close();
	}
	
	/**
	 * 
	 */
	util.validateHHMm = function(input){
		var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(input);
		return isValid;
	}
	
	/**
	 * valida la fecha en el formato dd/mm/YYYY
	 */
	util.validateDate = function(input){
		//var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\/]\d{4}$/;
		// Obtiene la fecha en formato dd/mm/aaaa
		try{
			//formatea la fecha
			var ret = [];
			var day = input.getDate();
			ret[0] = day < 10 ? '0' + day : '' + day;
			
			var month = input.getMonth() + 1;
			ret[1] = month < 10 ? '0' + month : '' + month;
			
			ret[2] = input.getYear() + 1900;
			
			input = ret.join("/");
		}catch(err){
			return false;
		}
		// Match the date format through regular expression
		if (dateformat.test(input)){
			//Test which seperator is used '/' or '-'
			var opera1 = input.split('/');
			var opera2 = input.split('-');
			lopera1 = opera1.length;
			lopera2 = opera2.length;
			// Extract the string into month, date and year
			if (lopera1>1)
			{
				var pdate = input.split('/');
			}
			else if (lopera2>1)
			{
				var pdate = input.split('-');
			}
			var dd = parseInt(pdate[0]);
			var mm  = parseInt(pdate[1]);
			var yy = parseInt(pdate[2]);
			// Create list of days of a month [assume there is no leap year by default]
			var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
			if (mm==1 || mm>2)
			{
				if (dd>ListofDays[mm-1])
				{
					return false;
				}
			}
			if (mm==2)
			{
				var lyear = false;
				if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
				{
					lyear = true;
				}
				if ((lyear==false) && (dd>=29))
				{
					return false;
				}
				if ((lyear==true) && (dd>29))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	util.isNumber = function(n) {
		return !isNaN(parseFloat(n)) && isFinite(n);
	}

	var colorMap = [];
	util.defaultColor = function(layer) {
		var color =	colorMap[layer];
		if(!color){
			//color = "#"+Math.floor(Math.random()*16777215).toString(16);
			color = '#'+ ('000000' + Math.floor(Math.random()*16777215).toString(16)).slice(-6);
			colorMap[layer] = color;
		}
		return color;
	}
	
	/**
	 * 
	 */
	util.mkGeoPoint = function(values, showLabel) {
		var r = {};
		r.type = "FeatureCollection";
		r.features = [];
		for (var i = 0; i < values.length; i++) {
			//
			var value = values[i];
			var color = util.defaultColor(value.label);
			var feature = {};
			feature.type = "Feature";
			feature.properties = {
					label: value.label,
					showLabel : showLabel,
					style : {
						fillColor : color,
						strokeColor : '#000000',
						weight: 15,
			            opacity: 0.65,
			            pointRadius: 23,
			            strokeWidth: 3
					},
					title: value.title?value.title:'',
					descriptitles:value.descriptitles?value.descriptitles.split(";"):'',
					descriptions:value.descriptions?value.descriptions.split(";"):''
			};
			feature.geometry = {
					type: "Point",
					coordinates: [value.lon, value.lat]
			};
			r.features.push(feature);
		}
		return r;
	}
	
	/**
	 * 
	 */
	util.mkGeoLine = function(values, showLabel, showPoints, suavizar) {
		var coorMap = {};
		var orderedValues = {};
		//
		for (var i = 0; i < values.length; i++) {
			var value = values[i];
			var coordinates = [value.lon, value.lat];
			var data = [value.title, value.descriptitles, value.descriptions];
			if(coorMap[value.label]){
				coorMap[value.label].push(coordinates);
				orderedValues[value.label].push(data);
			}else{
				coorMap[value.label] = [coordinates];
				orderedValues[value.label] = [data];
			}
		}
		var r = {};
		r.type = "FeatureCollection";
		r.features = [];
		for (var prop in coorMap) {
			if (suavizar == '1'){
				//comienza simplify
				var coorMapNew = [];
				for(var k = 0; k < coorMap[prop].length; k = k + 9){

					var arr10 = [];
					for(var z = k; z < k+10; z++){
						if(coorMap[prop][z] != undefined)
							arr10.push(coorMap[prop][z]);
					}
					//convierto puntos a obj
					var allPointObjects = pointsConversion(arr10, 'toObject');
					//simplifico
					allPointObjects = simplifyPath(allPointObjects,0.1);
					//convierto objeto a array
					allPointObjects = pointsConversion(allPointObjects, 'toArray');
					for(var z = 0; z < allPointObjects.length; z++){
						coorMapNew.push(allPointObjects[z]);
					}
				}
				coorMap[prop] = coorMapNew; 
				//end simplify
			}
			
			var color = util.defaultColor(prop); 
			var feature = {};
			feature.type = "Feature";
			feature.properties = {
					label: prop,
					showLabel : showLabel,
					style : {
						fillColor : color,
						strokeColor : color,
						weight: 5,
			            opacity: 0.65
					},
					title:'',
					descriptitles:'',
					descriptions:''
			};
			feature.geometry = {
					type: "LineString",
					coordinates: coorMap[prop]
			};
			r.features.push(feature);
			if(showPoints){
				for(var j = 0; j < coorMap[prop].length; j++){
					var showPointLabel = false;
					//muestra los puntos cada 5 posiciones
//					if(j%5==0)
//						showPointLabel = true;

					var featurePoint = {};
					featurePoint.type = "Feature";
					featurePoint.properties = {
							label: prop,
							showLabel : false,
							style : {
								fillColor : oscurecerColor(color, 50),
								strokeColor : color, 
								weight: 5,
					            opacity: 0.65,
					            labelYOffset: 10
							},
							pointLabel: j,
							showPointLabel: true, //showPointLabel,   
//							title: value.title?value.title:'',
//							descriptitles:value.descriptitles?value.descriptitles.split(";"):'',
//							descriptions:value.descriptions?value.descriptions.split(";"):''
							title: orderedValues[prop][j][0]?orderedValues[prop][j][0]:'',
							descriptitles:orderedValues[prop][j][1]?orderedValues[prop][j][1].split(";"):'',
							descriptions:orderedValues[prop][j][2]?orderedValues[prop][j][2].split(";"):''
					};
					featurePoint.geometry = {
							type: "Point",
							coordinates: coorMap[prop][j]
					};
					r.features.push(featurePoint);
				}
			}
		}
		return r;
	}
	
	//oscurece un color en hexadecimal
	function oscurecerColor(color, cant){
		 //voy a extraer las tres partes del color
		 var rojo = color.substr(1,2);
		 var verd = color.substr(3,2);
		 var azul = color.substr(5,2);
		 
		 //voy a convertir a enteros los string, que tengo en hexadecimal
		 var introjo = parseInt(rojo,16);
		 var intverd = parseInt(verd,16);
		 var intazul = parseInt(azul,16);
		 
		 //ahora verifico que no quede como negativo y resto
		 if (introjo-cant>=0) introjo = introjo-cant;
		 if (intverd-cant>=0) intverd = intverd-cant;
		 if (intazul-cant>=0) intazul = intazul-cant;
		 
		 //voy a convertir a hexadecimal, lo que tengo en enteros
		 rojo = introjo.toString(16);
		 verd = intverd.toString(16);
		 azul = intazul.toString(16);
		 
		 //voy a validar que los string hexadecimales tengan dos caracteres
		 if (rojo.length<2) rojo = "0"+rojo;
		 if (verd.length<2) verd = "0"+verd;
		 if (azul.length<2) azul = "0"+azul;
		 
		 //voy a construir el color hexadecimal
		 var oscuridad = "#"+rojo+verd+azul;
		 
		 //la funci\u00f3n devuelve el valor del color hexadecimal resultante
		 return oscuridad;
	}
	
	
	/*convierte los puntos en objeto o array según sea necesario para usar simplify*/

	function pointsConversion(points, direction){
		var cache;
		if(direction=='toObject'){
			cache = points.map(function(point){if(point != undefined) return {'x':point[0],'y':point[1]}});
		}
		else if(direction=='toArray'){
			cache = points.map(function(point){if(point != undefined) return [point.x,point.y]});
		}
		return cache;
	}

	
	

	/*
	 * douglas-pecker 
	 */
	var simplifyPath = function( points, tolerance ) {

		// helper classes
		var Vector = function( x, y ) {
			this.x = x;
			this.y = y;
		};
		var Line = function( p1, p2 ) {
			this.p1 = p1;
			this.p2 = p2;
			this.distanceToPoint = function( point ) {
				// slope
				var m = ( this.p2.y - this.p1.y ) / ( this.p2.x - this.p1.x ),
				// y offset
				b = this.p1.y - ( m * this.p1.x ),
				d = [];
				// distance to the linear equation
				d.push( Math.abs( point.y - ( m * point.x ) - b ) / Math.sqrt( Math.pow( m, 2 ) + 1 ) );
				// distance to p1
				d.push( Math.sqrt( Math.pow( ( point.x - this.p1.x ), 2 ) + Math.pow( ( point.y - this.p1.y ), 2 ) ) );
				// distance to p2
				d.push( Math.sqrt( Math.pow( ( point.x - this.p2.x ), 2 ) + Math.pow( ( point.y - this.p2.y ), 2 ) ) );
				// return the smallest distance
				return d.sort( function( a, b ) {
					return ( a - b ); //causes an array to be sorted numerically and ascending
				} )[0];
			};
		};
		var douglasPeucker = function( points, tolerance ) {
			if ( points.length <= 2 ) {
				return [points[0]];
			}
			var returnPoints = [],
			// make line from start to end
			line = new Line( points[0], points[points.length - 1] ),
			// find the largest distance from intermediate poitns to this line
			maxDistance = 0,
			maxDistanceIndex = 0,
			p;
			for( var i = 1; i <= points.length - 2; i++ ) {
				var distance = line.distanceToPoint( points[ i ] );
				if( distance > maxDistance ) {
					maxDistance = distance;
					maxDistanceIndex = i;
				}
			}
			// check if the max distance is greater than our tollerance allows
			if ( maxDistance >= tolerance ) {
				p = points[maxDistanceIndex];
				line.distanceToPoint( p, true );
				// include this point in the output
				returnPoints = returnPoints.concat( douglasPeucker( points.slice( 0, maxDistanceIndex + 1 ), tolerance ) );
				// returnPoints.push( points[maxDistanceIndex] );
				returnPoints = returnPoints.concat( douglasPeucker( points.slice( maxDistanceIndex, points.length ), tolerance ) );
			} else {
				// ditching this point
				p = points[maxDistanceIndex];
				line.distanceToPoint( p, true );
				returnPoints = [points[0]];
			}
			return returnPoints;
		};
		var arr = douglasPeucker( points, tolerance );
		// always have to push the very last point on so it doesn't get left off
		arr.push( points[points.length - 1 ] );
		return arr;
	};

	/*
	 * fin douglas-pecker 
	 */
	
	
	return util;
}());
