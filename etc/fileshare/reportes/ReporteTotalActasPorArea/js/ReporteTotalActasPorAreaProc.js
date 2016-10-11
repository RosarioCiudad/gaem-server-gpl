Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteTotalActasPorArea = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		"CREATE TEMP TABLE tmpResultado AS "+
		"SELECT area.descripcion nombreArea, SUM(CASE WHEN form.idEstadoTipoFormulario = 3 THEN 1 ELSE 0 END) actascorrectas," +
		"SUM(CASE WHEN form.idEstadoTipoFormulario = 4 THEN 1 ELSE 0 END)  actasanuladas," +
		"SUM(form.fechacierre - form.fechainicio)/count(*)  promedio " +
			" FROM for_formulario form, def_area area " +
			" WHERE form.estado = 2" +
			" AND form.idarea = area.id" +
			" [[ AND form.idtipoformulario = #i ]]" +
			" [[ AND DATE(form.fechacierre) >= #d ]]" +
			" [[ AND DATE(form.fechacierre) <= #d ]]" +
			" [[ AND to_timestamp(to_char(form.fechainicio, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND to_timestamp(to_char(form.fechacierre, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND area.iddireccion = #i ]]" +
			" GROUP BY 1";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado";
	
	var SqlTmpPromedio = 
		"CREATE TEMP TABLE tmpPromedio AS " +
		"SELECT sum(form.fechacierre - form.fechainicio)/count(*) prom," +
		" max(form.fechacierre - form.fechainicio) max, " +
		" min(form.fechacierre - form.fechainicio) min" +
			" FROM for_formulario form, def_area area " +
			" WHERE 1=1 " +
			" AND form.idarea = area.id" +
			" AND form.estado = 2" +
			" [[ AND form.idtipoformulario = #i ]]" +
			" [[ AND DATE(form.fechacierre) >= #d ]]" +
			" [[ AND DATE(form.fechacierre) <= #d ]]" +
			" [[ AND to_timestamp(to_char(form.fechainicio, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND to_timestamp(to_char(form.fechacierre, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" +
			" [[ AND area.iddireccion = #i ]]";
	
	var SqlTmpPromedioDrop = "[noerr] drop table tmpPromedio";
	
	var SqlOutPromedio = "SELECT * from tmpPromedio";
		
		
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.idTipoFormulario, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.idDireccion];
	}
	
	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Total de Actas");
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
		Util.mkReport("salida/ReporteActas", "csv", {separator:"|"}, "ReporteActas (csv)", "Reporte de Actas", report, params);
		Util.mkReport("salida/ReporteActas", "html", {}, "ReporteActas (html)", "Reporte de Actas", report, params);

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
		out.fmt({props:{
			actascorrectas:'c%d',
			actasanuladas:'c%d'
		}, types:{}});
		
		//titulos
		out.title("Listado Total Actas");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		if(param.idUsuarioApm)
			out.field("Direccion: ", Sql.value("select descripcion from def_direccion where id = #i", param.idDireccion));
		else
			out.field("Direccion: ", "Todas");
		
		if(param.idTipoFormulario)
			out.field("Tipo Formulario: ", Sql.value("select descripcion from for_tipoformulario where id = #i", param.idTipoFormulario));
		else
			out.field("Tipo Formulario: ", "Todos");
		out.field("Fecha Desde: ", Util.formatDate(new Date(param.fechaDesde.getTime())));
		out.field("Hora Desde: ", param.horaDesde);
		out.field("Fecha Hasta: ", Util.formatDate(new Date(param.fechaHasta.getTime())));
		out.field("Hora Hasta: ", param.horaHasta);
		 
		out.endfieldset();

		out.table("Total Actas");
		// titulos de las columnas
		out.thead(["Area", "Actas Correctas","Actas Anuladas", "Tiempo Promedio de Labrado "]);
		// ordenar segun estos key del row
		out.order(["inspector","actascorrectas","actasanuladas","promedio"]); 

		var count = 0;
		var total = 0;
		var totalcorrectas = 0;
		var totalanuladas = 0;
		var cursor = Sql.cursor(SqlOut);
		while (row = cursor.read()) {
		    
		        if (row.nombreArea) row.nombreArea = row.nombreArea.toString();//.replace(",", " ", "g");
		        if (row.actascorrectas) row.actascorrectas = row.actascorrectas;
		        if (row.actasanuladas) row.actasanuladas = row.actasanuladas;
		        if (row.promedio) row.promedio = row.promedio.toString().replace(",", " ", "g");
			out.row(row);
			++count;
			totalcorrectas = (row.actascorrectas*1) + totalcorrectas;
			totalanuladas = (row.actasanuladas*1) + totalanuladas;
			total = totalcorrectas + totalanuladas;
		}
		
		var rs = Sql.row(SqlOutPromedio);
		
		if (count == 0)
			out.td("Sin Resultados");

		out.endtable();
		out.fieldset();
		out.field("Total Actas Correctas:", totalcorrectas.toString().replace(",", " ", "g"));
		out.field("Total Actas Anuladas:", totalanuladas.toString().replace(",", " ", "g"));
		out.field("Mayor Tiempo de Labrado:", rs.max);
		out.field("Menor Tiempo de Labrado:", rs.min);
		out.field("Tiempo Promedio General:", rs.prom);
		out.endfieldset();
		
		out.table("Total Actas:" + total.toString().replace(",", " ", "g"));
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

ReporteTotalActasPorArea.main();