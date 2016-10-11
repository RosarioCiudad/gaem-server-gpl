Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteDistInf = function () {
	var module = {};
	
	var Ds    = Grs.load("<grs>/ds.js");
	var Util  = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App   = Grs.load("<grs>/app/app.js")

	var Adp    = Grs.adp();
	var Sql    = Grs.sql(Const.DsGait);
	var SqlTmf = Grs.sql(Ds.DsTmf2);

	var params = {};
	
	//Sqls
	var SqlTmpResultado  = 
		"SELECT inf.cod_infrac || ' - ' || infrac.descripcion \"label\" , lug.posicion_latitud lat, lug.posicion_longitud lon, act.fecha_acta fecha" +
			" FROM actas_tran act, inf_actas inf, acta_lugar lug, inspector_captor inspc, infracciones infrac " +
			" WHERE act.t_acta        = inf.t_acta" +
			"   AND act.nro_acta      = inf.nro_acta" +
			"   AND act.serie         = inf.serie" +
			"   AND act.t_acta        = lug.t_acta" +
			"   AND act.nro_acta      = lug.nro_acta" +
			"   AND act.serie         = lug.serie" +
			"   AND act.t_acta        = 62" +
			"   AND act.nro_inspector = inspc.nro_inspector" +
			"   AND inf.cod_infrac    = infrac.cod_infrac" +
			" [[ AND DATE(act.fecha_acta) >= #d ]]" +
			" [[ AND DATE(act.fecha_acta) <= #d ]]" +
			" [[ AND inspc.login           = #s ]]" +
			" [[ AND inf.cod_infrac        #m ]]" + 
			" [[ AND pos.idarea = #i ]]" +
			" ORDER BY 1, 4" +
			" INTO TEMP tmpResultado";
	
	var SqlTmpResultadoResumen  = 
		"SELECT inf.cod_infrac || ' - ' || infrac.descripcion \"label\" _infracciones, COUNT(*) total_infracciones " + 
			" FROM actas_tran act, inf_actas inf, acta_lugar lug, inspector_captor inspc, infracciones infrac " +
			" WHERE act.t_acta        = inf.t_acta" +
			"   AND act.nro_acta      = inf.nro_acta" +
			"   AND act.serie         = inf.serie" +
			"   AND act.t_acta        = lug.t_acta" +
			"   AND act.nro_acta      = lug.nro_acta" +
			"   AND act.serie         = lug.serie" +
			"   AND act.t_acta        = 62" +
			"   AND act.nro_inspector = inspc.nro_inspector" +
			"   AND inf.cod_infrac    = infrac.cod_infrac" +
			" [[ AND DATE(act.fecha_acta) >= #d ]]" +
			" [[ AND DATE(act.fecha_acta) <= #d ]]" +
			" [[ AND inspc.login           = #s ]]" +
			" [[ AND inf.cod_infrac        #m ]]" + 
			" [[ AND pos.idarea = #i ]]" +
			" GROUP BY 1" +
			" ORDER BY 2 DESC, 1 ASC" +
			" INTO TEMP tmpResultadoResumen";

	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlTmpResultadoResumenDrop = "[noerr] drop table tmpResultadoResumen";

	var SqlOut = "SELECT * from tmpResultado"; 

	var SqlResumenOut = "SELECT * from tmpResultadoResumen ORDER BY 2 DESC"; 
	
	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.fechaDesde, p.fechaHasta, p.idUsuarioApm, p.objRadio, p.idArea];
	}

	// processReport genera el resultado del proceso 
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Grs Distribuci\u00f3n de Infracciones");
		SqlTmf.exec(SqlTmpResultadoDrop);
		SqlTmf.exec(SqlTmpResultado, this.params);
		SqlTmf.exec(SqlTmpResultadoResumenDrop);
		SqlTmf.exec(SqlTmpResultadoResumen, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}

	module.reporteDistInf = function() {
		var msg = {
			usuario : Adp.username(),
                	fechaReporte : Util.formatDate(new Date()),
			titulo: "Reporte Resumen" ,
			fechaDesde : Util.formatDate(new Date(this.params[0].getTime())),
			fechaHasta : Util.formatDate(new Date(this.params[1].getTime()))
		};
		
		var points = SqlTmf.list(SqlOut);
		
		var geojson = Util.mkGeoPoint(points); // Arma GeoJson default
		
		var data = { "msg" : msg, "geojson" : geojson};
		
		Util.mkOutput("<out>/reporteDistInf.json", data);
		Util.mkResume("<out>/reporteDistInf.html", "<work>/js/reporteDistInf.html");
		Util.addFile("<out>/reporteDistInf.html", "Reporte de distribuci\u00f3n de infracciones");
		
	}

	module.output = function (params) {
		Adp.log("Comienza construccion de salida\n");
		Util.mkReport("salida/ReporteDistInf", "csv", {separator:"|"}, "ReporteDistInf (csv)", "Reporte de Distribuci\u00f3n de Infracciones", report, params);
		Util.mkReport("salida/ReporteDistInf", "html", {}, "ReporteDistInf (html)", "Reporte de Distribuci\u00f3n de Infracciones", report, params);
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
			total_infracciones:'r%d'
		}, types:{}});
		
		//titulos
		out.title("Reporte de Distribuci\u00f3n de Infracciones");
		
		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		
		if(param.fechaDesde)
			out.field("Fecha Desde: ",  Util.formatDate(new Date(param.fechaDesde.getTime())));
		else
			out.field("Fecha Desde: ",  "");
		
		if(param.fechaHasta)
			out.field("Fecha Hasta: ", Util.formatDate(new Date(param.fechaHasta.getTime())));
		else
			out.field("Fecha Hasta: ", "");

		if(param.idUsuarioApm)
			out.field("Inspector: ", Sql.value("SELECT '['||username ||']: '|| nombre FROM apm_usuarioapm WHERE username = #s", param.idUsuarioApm));
		
		else
			out.field("Inspector: ", "Todos");

		if(param.objRadio != "like '%'"){
			var resultado = Sql.list("select descripcion from aid_infraccion where codigo #m", param.objRadio);
			for(ind=0; ind<resultado.length; ind++){
				out.field("Infracci\u00f3n: ", resultado[ind].descripcion);
			}
			
			Grs.printf("resultado: %s", resultado.descripcion);
		}
		else
			out.field("Infracci\u00f3n: ", "Todos");



		out.endfieldset();

		out.table("Total Infracciones");
		// titulos de las columnas 
		out.thead(["Infracci\u00f3n", "Total"]);
		// ordenar segun estos key del row
		out.order(["label_infracciones", "total_infracciones"]);

		var count = 0;
		var cursor = SqlTmf.cursor(SqlResumenOut);
		while (row = cursor.read()) {
			if (row.total_infracciones) 
		    	row.total_infracciones = row.total_infracciones;
			out.row(row);
			++count;
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
		if(params.objRadio){
			var sqlInPart = Util.quote(params.objRadio.toString().split(',')).join(',');
			params.objRadio = "in ("+sqlInPart+")";
		}else{
			params.objRadio = "like '%'";
		}
		module.parametros(params);
		module.procesar();
		module.reporteDistInf();
		module.output(params);

	}
	return module;
}();

ReporteDistInf.main();
