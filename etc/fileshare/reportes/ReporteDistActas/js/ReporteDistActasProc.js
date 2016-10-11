Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteDistActas = function () {
	var module = {};

	var Ds    = Grs.load("<grs>/ds.js");
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	//var Sql = Grs.sql(Ds.DsTmf2);
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		"CREATE TEMP TABLE tmpResultado AS " +
		"SELECT " +
		"form.numero || ' -- ' || to_char(form.fechacierre, 'DD/MM/YYYY') label, " +
//		"'actas' \"label\", " +
		"form.latitud lat, form.longitud lon, form.fechacierre, " +
			" 'Fecha Posici\u00f3n'||';'|| 'Hora Posici\u00f3n' descriptitles, " +
			" to_char(form.fechacierre, '%d/%m/%Y') ||';' descriptions, " +
			" usu.username title " +
			" FROM for_formulario form, apm_usuarioapm usu " +
			" WHERE form.idusuarioapmcierre = usu.id " +
			" AND form.estado = 1 " +
			" [[ AND form.idusuarioapmcierre = #i ]]" +
			" [[ AND DATE(form.fechacierre) >= #d ]]" +
			" [[ AND DATE(form.fechacierre) <= #d ]]" +
			" [[ AND form.idTipoFormulario = #i ]]" +
			" ORDER BY 1, 4 ";

	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado"; 
	

	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.idUsuarioApm, p.fechaDesde, p.fechaHasta, p.idTipoFormulario, p.leyenda];
	}

	// processReport genera el resultado del proceso 
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Grs Distribuci\u00f3n de Actas por Inspector");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}

	module.reporteDistActas = function() {
		var msg = {
				usuario : Adp.username(),
				fechaReporte : Util.formatDate(new Date()),
				titulo: "Reporte Resumen" ,
				fechaDesde : Util.formatDate(new Date(this.params[1].getTime())),
				fechaHasta : Util.formatDate(new Date(this.params[2].getTime()))
		};

		var points = Sql.list(SqlOut);

		var geojson;// = Util.mkGeoPoint(points); // Arma GeoJson default
		
		if(this.params[4]){
			geojson = Util.mkGeoPoint(points, true); // Arma GeoJson con labels en los puntos
		}else{
			geojson = Util.mkGeoPoint(points, false); // Arma GeoJson sin labels en los puntos
		}
		

		var data = { "msg" : msg, "geojson" : geojson};

		Util.mkOutput("<out>/reporteDistActas.json", data);
		Util.mkResume("<out>/reporteDistActas.html", "<work>/js/reporteDistActas.html");
		Util.addFile("<out>/reporteDistActas.html", "Reporte de distribuci\u00f3n de actas por inspector");
	}

	module.main = function() {
		var params = Adp.parameters();
		if(params.objRadio){
			var sqlInPart = Util.quote(params.objRadio.toString().split(',')).join(',');
			params.objRadio = "in ("+sqlInPart+")";
		}else{
			params.objRadio = "like '%'";
		}
		module.parametros(params);
		module.procesar();
		module.reporteDistActas();
	}
	return module;
}();

ReporteDistActas.main();