Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteConectividad = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		"CREATE TEMP TABLE tmpResultado AS " +
		"SELECT form.fechainicio, form.fechacierre, disp.descripcion dispositivo," +
		" form.fechatransmision, " +
		" to_char(form.fechatransmision - form.fechacierre, 'HH24 hrs MI \"min\" SS sec') tiempodemora, " +
		" '['||form.numeroinspector||']: '||usu.nombre inspector , form.numero " +
			" FROM for_formulario form, apm_dispositivomovil disp, apm_usuarioApm usu " +
			" WHERE 1=1 " +
			" AND form.iddispositivomovil = disp.id " +
			" AND form.idUsuarioApmCierre = usu.id " +
			//" AND form.estado = 2" +
			" [[ AND form.iddispositivomovil = #i ]]" +
			" [[ AND DATE(form.fechacierre) >= #d ]]" +
			" [[ AND DATE(form.fechacierre) <= #d ]]" +
			" [[ AND to_timestamp(to_char(form.fechainicio, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND to_timestamp(to_char(form.fechacierre, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" ;
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado ORDER BY 1 DESC";
	
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
		this.params = [p.idDispositivoMovil, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta];
	}
	
	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Conectividad");
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
		Util.mkReport("salida/ReporteConectividad", "csv", {separator:"|"}, "ReporteConectividad (csv)", "Reporte Conectividad", report, params);
		Util.mkReport("salida/ReporteConectividad", "html", {}, "ReporteConectividad (html)", "Reporte Conectividad", report, params);

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

		
		// Formatter
//		out.fmt({props:{
//			fechainicio:'%1$td/%1$tm/%1$tY',
//			fechacierre:'%1$tH:%1$tM:%1$tS',
//			fechatransmision:'%1$tH:%1$tM:%1$tS'
//		}, types:{}});
		out.fmt({types:{date:'%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS'}});

		//titulos
		out.title("Listado de Conectividad");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		out.field("Fecha Cierre Desde: ", Util.formatDate(new Date(param.fechaDesde.getTime())));
		out.field("Hora Cierre Desde: ", param.horaDesde);
		out.field("Fecha Cierre Hasta: ", Util.formatDate(new Date(param.fechaHasta.getTime())));
		out.field("Hora Cierre Hasta: ", param.horaHasta);
		if(param.idDispositivoMovil)
			out.field("Dispositivo: ", Sql.value("SELECT descripcion FROM apm_dispositivomovil where id = #i", param.idDispositivoMovil));
		else
			out.field("Dispositivo: ", "Todos");
		 
		out.endfieldset();

		out.table("Total Conexiones");
		// titulos de las columnas
		out.thead(["Dispositivo","Inspector","Acta","Fecha Inicio", "Fecha Cierre", "Fecha Transmision", "Demora"]);
		// ordenar segun estos key del row
		out.order(["dispositivo","inspector","numero","fechainicio","fechacierre","fechatransmision","tiempodemora"]);

		var count = 0;
		var total = 0;
		var totalcorrectas = 0;
		var totalanuladas = 0;
		var cursor = Sql.cursor(SqlOut);
		while (row = cursor.read()) { 
			out.row(row);
			++count;
		}
		
		var rs = Sql.row(SqlOutPromedio);
		
		if (count == 0)
			out.td("Sin Resultados");

		out.endtable();
		out.fieldset();
		out.field("Mayor Demora:", rs.max);
		out.field("Menor Demora:", rs.min);
		out.field("Demora Promedio:", rs.prom);
		out.endfieldset();
		
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

ReporteConectividad.main();