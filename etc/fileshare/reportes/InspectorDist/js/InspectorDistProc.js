Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var InspectorDist = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};
	
	//Sqls
	var SqlTmpResultado  = 
		"CREATE TEMP TABLE tmpResultado AS SELECT usu.username \"label\" , pos.latitud lat, pos.longitud lon, pos.fechaposicion, " +
			" 'Fecha Posici\u00f3n'||';'|| 'Hora Posici\u00f3n' descriptitles, " +
			" to_char(fechaposicion, '%d/%m/%Y') ||';'|| to_timestamp(to_char(pos.fechaposicion, 'HH24:MI'), 'HH24:MI')  descriptions, " +
			" usu.username title " +
			" FROM gps_historialubicacion pos, apm_usuarioapm usu " +
			" WHERE pos.idusuarioapm = usu.id " +
			" AND pos.estado = 1 " +
			" [[ AND pos.idusuarioapm = #i ]]" +
			" [[ AND DATE(pos.fechaposicion) >= #d ]]" +
			" [[ AND DATE(pos.fechaposicion) <= #d ]]" +
			//" [[ AND EXTEND(pos.fechaposicion, hour to minute) >= EXTEND(#s, hour to minute) ]]" +
			//" [[ AND EXTEND(pos.fechaposicion, hour to minute) <= EXTEND(#s, hour to minute) ]]" +
			" [[ AND to_timestamp(to_char(pos.fechaposicion, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND to_timestamp(to_char(pos.fechaposicion, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND pos.idarea = #i ]]" +
			" ORDER BY 1, 4 ";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado"; 
	
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.idUsuarioApm, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.idArea];
	}

	// processReport genera el resultado del proceso 
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Grs Distribuci\u00f3n de Inspectores");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}

	module.inspectorDist = function() {
		var msg = {
				usuario : Adp.username(),
                fechaReporte : Util.formatDate(new Date()),
				titulo: "Reporte Resumen" ,
				fechaDesde : Util.formatDate(new Date(this.params[1].getTime()))+" "+this.params[3],
				fechaHasta : Util.formatDate(new Date(this.params[2].getTime()))+" "+this.params[4]
		};
		
		var points = Sql.list(SqlOut);
		
		var geojson = Util.mkGeoPoint(points); // Arma GeoJson default
		
		var data = { "msg" : msg, "geojson" : geojson};
		
		Util.mkOutput("<out>/inspectorDist.json", data);
		Util.mkResume("<out>/inspectorDist.html", "<work>/js/inspectorDist.html");
		Util.addFile("<out>/inspectorDist.html", "Reporte de distribuci\u00f3n de inspectores");
	}
	
	module.main = function() {
		module.parametros(Adp.parameters());
		module.procesar();
		module.inspectorDist();
	}
	return module;
}();

InspectorDist.main();