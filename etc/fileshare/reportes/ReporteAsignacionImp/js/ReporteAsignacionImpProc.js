Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteAsignacionImp = function () {
	var module = {};
	
	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		" CREATE TEMP TABLE tmpResultado AS SELECT usu.nombre inspector, imp.descripcion descripcion, usuimp.fechaultmdf fechaasignacion, imp.numeroserie numeroserie" +
			" FROM apm_impresora imp, apm_usuarioapm usu, apm_usuarioapmimpresora usuimp " +
			" WHERE imp.id = usuimp.idimpresora " +
			" AND usuimp.idusuario = usu.id" +
			" [[ AND DATE(usuimp.fechaultmdf) >= #d ]]" +
			" [[ AND DATE(usuimp.fechaultmdf) <= #d ]]" +
			" [[ AND imp.descripcion = #s ]]" +
			" [[ AND imp.numeroserie = #s ]]" +
			" [[ AND usuimp.usuario = #s ]]" +
			" [[ AND usu.id = #i ]]" +
			" [[ AND imp.idarea = #i ]]" +
			" [[ AND usuimp.estado = 1 ]]";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado";
		
		
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.fechaAsignDesde, p.fechaAsignHasta, p.nombreImp, p.nroSerie, p.usrAsign, p.idUsuarioApm, p.idArea];
	}
	
	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte de Asignaci\u00f3n de Impresoras");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}
	// outputReport: Recorre las tablas de resultado y genera los archivos de salida.
	module.output = function (params) {
		Adp.log("Comienza construccion de salida\n");
		
		//cuentas
		Util.mkReport("salida/ReporteAsignacionImp", "csv", {separator:"|"}, "ReporteAsignacionImp (csv)", "Reporte de Asignaci\u00f3n de Impresoras", report, params);
		Util.mkReport("salida/ReporteAsignacionImp", "html", {}, "ReporteAsignacionImp (html)", "Reporte de Asignaci\u00f3n de Impresoras", report, params);

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
	//		out.include("<grs>/html/exthead.html", dataMap);

		// Formatter
		out.fmt({props:{fechaasignacion:'%1$td/%1$tm/%1$tY'}});
		
		//titulos
		out.title("Listado Impresoras Asignadas");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		
		if(param.fechaAsignDesde)
			out.field("Fecha Asignacion Desde: ",  Util.formatDate(new Date(param.fechaAsignDesde.getTime())));
		else
			out.field("Fecha Asignacion Desde: ",  "");
		
		if(param.fechaAsignHasta)
			out.field("Fecha Asignacion Hasta: ", Util.formatDate(new Date(param.fechaAsignHasta.getTime())));
		else
			out.field("Fecha Asignacion Hasta: ", "");

		if(param.nombreImp)
			out.field("Impresora: ", param.nombreImp);
		else
			out.field("Impresora: ", "Todos");

		if(param.nroSerie)
			out.field("Numero de serie: ", param.nroSerie);
		else
			out.field("Numero de serie: ", "Todos");
		
		if(param.usrAsign)
			out.field("Usuario que asign\u00f3: ", param.usrAsign);
		else
			out.field("Usuario que asign\u00f3: ", "Todos");

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
		// titulos de las columnas
		out.thead(["Inspector", "Impresora","Fecha Asignacion", "Numero Serie"]);
		// ordenar segun estos key del row
		out.order(["inspector","descripcion","fechaasignacion","numeroserie"]); 

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

ReporteAsignacionImp.main();