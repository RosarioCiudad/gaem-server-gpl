Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteTotalActasInfr = function () {
	var module = {};
	
	var Ds    = Grs.load("<grs>/ds.js");
	var Util  = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js");
	var App   = Grs.load("<grs>/app/app.js");

	var Adp    = Grs.adp();
	var Sql    = Grs.sql(Const.DsGait);
	var SqlTmf = Grs.sql(Ds.DsTmf2);

	var params = {};

	//Sqls
	var SqlTmpResultado  = 
		"SELECT inf.cod_infrac || ' - ' || infrac.descripcion infraccion, COUNT(*) actascorrectas" +
			" FROM actas_tran act, inf_actas inf, infracciones infrac " +
			" WHERE act.t_acta = inf.t_acta" +
			"   AND act.nro_acta = inf.nro_acta" +
			"   AND act.serie = inf.serie" +
			"   AND act.t_acta = 62" +
			"   AND inf.cod_infrac = infrac.cod_infrac" +
			" [[ AND DATE(act.fecha_acta) >= #d ]]" +
			" [[ AND DATE(act.fecha_acta) <= #d ]]" +
			" [[ AND inf.cod_infrac = #s ]]" + 
			" [[ AND act.cod_calle = #i ]]" + 
			" [[ AND act.numero >= #i ]]" + 
			" [[ AND act.numero <= #i ]]" + 
			" GROUP BY 1" +
			" ORDER BY 2 DESC" +
			" INTO TEMP tmpResultado";
	
	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";
	var SqlOut = "SELECT * from tmpResultado ORDER BY 2 DESC";
		
		
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.fechaDesde, p.fechaHasta, p.idInfraccion, p.idCalle, p.alturaDesde, p.alturaHasta];
	}
	
	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Total de Actas por Infraccion");
		SqlTmf.exec(SqlTmpResultadoDrop);
		SqlTmf.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}
	
	// outputReport: Recorre las tablas de resultado y genera los archivos de salida.
	module.output = function (params) {
		Adp.log("Comienza construccion de salida\n");
		
		//cuentas
		Util.mkReport("salida/ReporteActasInfr", "csv", {separator:"|"}, "ReporteActasInfr (csv)", "Reporte de Actas por Infracci\u00f3n", report, params);
		Util.mkReport("salida/ReporteActasInfr", "html", {}, "ReporteActasInfr (html)", "Reporte de Actas por Infracci\u00f3n", report, params);

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
//			out.include("<grs>/html/stdhead.html");
			out.include("<grs>/html/exthead.html", dataMap);
		
		//formatter
		out.fmt({props:{
			actascorrectas:'r%d'
		}, types:{}});
		
		//titulos
		out.title("Listado Total Actas por Infracci\u00f3n");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		
		if(param.fechaDesde)
			out.field("Fecha Desde: ", Util.formatDate(new Date(param.fechaDesde.getTime())));
		else
			out.field("Fecha Desde: ", "Todas");
		
		if(param.fechaHasta)
			out.field("Fecha Hasta: ", Util.formatDate(new Date(param.fechaHasta.getTime())));
		else
			out.field("Fecha Hasta: ", "Todas");

		if(param.idInfraccion)
			out.field("Infracci\u00f3n: ", Sql.value("select descripcion from aid_infraccion where codigo = #s", param.idInfraccion));
		else
			out.field("Infracci\u00f3n: ", "Todas");
		
		if(param.idCalle)
			out.field("Calle: ", SqlTmf.value("select nom_calle from calles where cod_calle = #i", param.idCalle));
		else
			out.field("Calle: ", "Todas");

		if(param.alturaDesde)
			out.field("Altura Desde: ", param.alturaDesde.toString().replace(",", " ", "g"));
		else
			out.field("Altura Desde: ", "Todas");

		if(param.alturaHasta)
			out.field("Altura Hasta: ", param.alturaHasta.toString().replace(",", " ", "g"));
		else
			out.field("Altura Hasta: ", "Todas");
		
		out.endfieldset();

		out.table("Total Actas por Infracci\u00f3n");
		// titulos de las columnas
		out.thead(["Infracci\u00f3n", "Cantidad de actas"]);
		// ordenar segun estos key del row
		out.order(["infraccion", "actascorrectas"]); 

		var count = 0;
		var totalcorrectas = 0;
		var cursor = SqlTmf.cursor(SqlOut);
		while (row = cursor.read()) {
		    
		    if (row.actascorrectas) 
		    	row.actascorrectas = row.actascorrectas;
			
		    out.row(row);
			++count;
			totalcorrectas = (row.actascorrectas*1) + totalcorrectas;
		}
		
		if (totalcorrectas) 
			totalcorrectas = totalcorrectas.toString().replace(",", " ", "g");
		
		if (count == 0)
			out.td("Sin Resultados");

		out.endtable();
		out.fieldset();
		out.field("Total Actas:", totalcorrectas.toString().replace(",", " ", "g"));
		out.endfieldset();
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

ReporteTotalActasInfr.main();
