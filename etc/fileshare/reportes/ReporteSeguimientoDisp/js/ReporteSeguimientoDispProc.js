Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteSeguimientoDisp = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		" CREATE TEMP TABLE tmpResultado AS " +
		" SELECT ubi.fechaposicion fecha, ubi.fechaposicion hora, disp.descripcion descripcion, " +
		    " coalesce(ubi.nivelBateria,0) bateria, coalesce(ubi.nivelSenial,0) senial, ubi.latitud latitud, ubi.longitud longitud "+
			", usu.username \"label\" , ubi.iddispositivomovil" +
			" FROM 	gps_historialubicacion ubi, apm_dispositivomovil disp " +
			", apm_usuarioapm usu " +
			" WHERE ubi.iddispositivomovil = disp.id " +
			" AND 	ubi.idusuarioapm = usu.id " +
			" AND 	ubi.estado = 1 " +
			" [[ AND DATE(ubi.fechaPosicion) >= #d ]]" +
			" [[ AND DATE(ubi.fechaPosicion) <= #d ]]" +
			" [[ AND ubi.iddispositivomovil = #i ]]" +
			" [[ AND ubi.idusuarioapm = #i ]]" +
			" [[ AND ubi.idarea = #i ]]" +
			" ORDER BY ubi.fechaPosicion, ubi.iddispositivomovil ";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado ORDER BY 1, 9";

	var SqlTmpPromedio = 
		" CREATE TEMP TABLE tmpPromedio AS " +
			"SELECT " +
			"sum(ubi.nivelBateria)/count(*) prom," +
			" max(ubi.nivelBateria) max," +
			" min(ubi.nivelBateria) min" +
		" FROM gps_historialubicacion ubi" +
		" WHERE ubi.estado = 1" +
		" [[ AND DATE(ubi.fechaPosicion) >= #d ]]" +
		" [[ AND DATE(ubi.fechaPosicion) <= #d ]]" +
		" [[ AND ubi.iddispositivomovil = #i ]]" +
		" [[ AND ubi.idusuarioapm = #i ]]" +
		" [[ AND ubi.idarea = #i ]]";
	
	var SqlTmpPromedio = 
		"CREATE TEMP TABLE tmpPromedio AS " +
		"SELECT" +
		" to_char(sum(form.fechatransmision - form.fechacierre)/count(*), 'HH24 hrs MI \"min\" SS sec') prom, " +
		" to_char(max(form.fechatransmision - form.fechacierre), 'HH24 hrs MI \"min\" SS sec') max, " +
		" to_char(min(form.fechatransmision - form.fechacierre), 'HH24 hrs MI \"min\" SS sec') min " +
			" FROM for_formulario form" +
			" WHERE 1=1" +
//			" AND form.estado = 2" +
			" [[ AND form.iddispositivomovil = #i ]]" +
			" [[ AND DATE(form.fechacierre) >= #d ]]" +
			" [[ AND DATE(form.fechacierre) <= #d ]]" +
			" [[ AND to_timestamp(to_char(form.fechainicio, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND to_timestamp(to_char(form.fechacierre, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" ;
	
	var SqlTmpPromedioDrop = "[noerr] drop table tmpPromedio";
	
	var SqlOutPromedio = "SELECT * from tmpPromedio";
		
		
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.fechaPosDesde, p.fechaPosHasta, p.dispositivo, p.idUsuarioApm, p.idArea];
	}
	
	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte de Seguimiento de Dispositivos M\u00f3viles");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Sql.exec(SqlTmpPromedioDrop);
		Sql.exec(SqlTmpPromedio, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}
	// outputReport: Recorre las tablas de resultado y genera los archivos de salida.
	module.output = function (params) {
		Adp.log("Comienza construccion de salida\n");
		
		//cuentas
		Util.mkReport("salida/ReporteSeguimientoDisp", "csv", {separator:"|"}, "ReporteSeguimientoDisp (csv)", "Reporte de Seguimiento de Dispositivos M\u00f3viles", report, params);
		Util.mkReport("salida/ReporteSeguimientoDisp", "html", {}, "ReporteSeguimientoDisp (html)", "Reporte de Seguimiento de Dispositivos M\u00f3viles", report, params);

		Adp.log("Finaliza construccion de salida");
	}
	
	// report: Genera un reporte en el archivo independiente del formato.
	// retorna la cantidad de registros procesados.
	var report = function (out, param) {
		var dataMap = {};
		dataMap["#usuario"] = Adp.username();
		dataMap["#fechaReporte"] = Util.formatDate(new Date());
		
		//cabecera para html
		if (out.type() == "html")
			out.include("<grs>/html/stdhead.html");
//			out.include("<grs>/html/exthead.html", dataMap);

		//titulos
		out.title("Listado Seguimiento Dispositivos M\u00f3viles");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		
		if(param.fechaPosDesde)
			out.field("Fecha Desde: ",  Util.formatDate(new Date(param.fechaPosDesde.getTime())));
		else
			out.field("Fecha Desde: ",  "");
		
		if(param.fechaPosHasta)
			out.field("Fecha Hasta: ", Util.formatDate(new Date(param.fechaPosHasta.getTime())));
		else
			out.field("Fecha Hasta: ", "");

		if(param.dispositivo)
			out.field("Dispositivo: ", Sql.value("select descripcion from apm_dispositivomovil where id = #i", param.dispositivo));
		else
			out.field("Dispositivo: ", "Todos");

		if(param.idUsuarioApm)
			out.field("Inspector: ", Sql.value("select nombre from apm_usuarioapm where id = #i", param.idUsuarioApm));
		else
			out.field("Inspector: ", "Todos");
		
		if(param.idArea)
			out.field("Area: ", Sql.value("select descripcion from def_area where id = #i", param.idArea));
		else
			out.field("Area: ", "Todos");
		


		out.endfieldset();

		out.table("Total Asignaciones");
		// titulos de las columnas Fecha, Hora, Dispositivo, Nivel de Señal, Nivel de bater\u00eda, Posici\u00f3n . 
		out.thead(["Fecha", "Hora", "Inspector", "Dispositivo", "Nivel de Señal", "Nivel de Bater\u00eda", "Latitud", "Longitud"]);
		// ordenar segun estos key del row
		out.order(["fecha", "hora", "label", "descripcion", "senial","bateria", "latitud", "longitud"]); 


		// Formatter
		out.fmt({props:{
			fecha:'%1$td/%1$tm/%1$tY',
			hora:'%1$tH:%1$tM:%1$tS',
			senial:'r%.2f',
			bateria:'r%.2f',
			latitud:'r%.8f',
			longitud:'r%.8f'
		}, types:{}});
		//out.fmt({types:{date:'%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS'}});

		var count = 0;
		var total = 0;
		var totalcorrectas = 0;
		var totalanuladas = 0;
		var cursor = Sql.cursor(SqlOut);
		while (row = cursor.read()) {
			out.row(row);
			++count;
			totalcorrectas = (row.actascorrectas*1) + totalcorrectas;
			totalanuladas = (row.actasanuladas*1) + totalanuladas;
			total = totalcorrectas + totalanuladas;
		}
		
		if (count == 0)
			out.td("Sin Resultados");

		var rs = Sql.row(SqlOutPromedio);
		
		if (count == 0)
			out.td("Sin Resultados");

		out.endtable();
		out.fieldset();
		out.field("Duraci\u00f3n bater\u00eda mayor:", rs.max);
		out.field("Duraci\u00f3n bater\u00eda menor:", rs.min);
		out.field("Duraci\u00f3n bater\u00eda promedio:", rs.prom);

		out.endtable();
		
		//pie para html
		if (out.type() == "html")
			out.include("<grs>/html/stdfoot.html");

		return count;
	}

	module.main = function() {
		var params = Adp.parameters();
		module.parametros(params);
		module.procesar();
		module.output(params);
	}
	return module;
}();

ReporteSeguimientoDisp.main();
