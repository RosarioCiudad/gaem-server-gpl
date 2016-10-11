Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var InspectorDesp = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")
	var Simplify = Grs.load("<grs>/simplify.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);
	
	var params = {};
	
	//Sqls
	var SqlTmpResultado  = 
		" CREATE TEMP TABLE tmpResultado AS SELECT usu.username \"label\" , pos.latitud lat, pos.longitud lon, pos.fechaposicion, " +
			" 'Fecha Posici\u00f3n'||';'|| 'Hora Posici\u00f3n' descriptitles, " +
			" to_char(pos.fechaposicion, 'DD/MM/YYYY') ||';'|| to_char(pos.fechaposicion, 'HH:MI') descriptions, " +
			" usu.username title " +
			" FROM gps_historialubicacion pos, apm_usuarioapm usu " +
			" WHERE pos.idusuarioapm = usu.id " +
			" AND pos.estado = 1 " +
			" [[ AND pos.idusuarioapm = #i ]]" +
			" [[ AND DATE(pos.fechaposicion) >= #d ]]" +
			" [[ AND DATE(pos.fechaposicion) <= #d ]]" +
//			" [[ AND to_char(pos.fechaposicion, hour to minute) >= EXTEND(#s, hour to minute) ]]" +
//			" [[ AND to_char(pos.fechaposicion, hour to minute) <= EXTEND(#s, hour to minute) ]]" +
			" [[ AND pos.idarea = #i ]]" +
			" ORDER BY 1, 4 ";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado ORDER BY 1, 4"; 
	
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		//this.params = [p.idUsuarioApm, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.suavizar, p.tolerancia];
		//this.params = [p.idUsuarioApm, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.suavizar];
		this.params = [p.idUsuarioApm, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.idArea, p.suavizar];
	}

	// processReport genera el resultado del proceso 
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Grs Desplazamiento de Inspectores");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}

	module.inspectorDesp = function() {
		var msg = {
				usuario : Adp.username(),
                fechaReporte : Util.formatDate(new Date()),
				titulo: "Reporte Resumen" ,
				fechaDesde : Util.formatDate(new Date(this.params[1].getTime()))+" "+this.params[3],
				fechaHasta : Util.formatDate(new Date(this.params[2].getTime()))+" "+this.params[4]
		};
		
		var points = Sql.list(SqlOut);
		
		//var tolerancia = this.params[5]; 
		
//		if (tolerancia > 0){
//			points = Simplify.simplify(points, tolerancia, false);
//		}
		
		var suavizar = this.params[5];
		
		var showLabel = true;
		var showPoints = true;
		var geojson = Util.mkGeoLine(points, showLabel, showPoints, suavizar); 
		
		//var data = { "msg" : msg, "geojson" : geojson, "tolerancia" : tolerancia};
		var data = { "msg" : msg, "geojson" : geojson, "suavizar": suavizar};
		
		Util.mkOutput("<out>/inspectorDesp.json", data);
		Util.mkResume("<out>/inspectorDesp.html", "<work>/js/inspectorDesp.html");
		Util.addFile("<out>/inspectorDesp.html", "Reporte de desplazamiento de inspectores");
	}
	
	module.main = function() {
		module.parametros(Adp.parameters());
		module.procesar();
		module.inspectorDesp();
	}
	return module;
}();

InspectorDesp.main();