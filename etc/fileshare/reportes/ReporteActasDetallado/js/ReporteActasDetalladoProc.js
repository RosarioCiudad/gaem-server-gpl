Grs.load("<grs>/grs.js");
Grs.setDebug(1);

var ReporteActasDetallado = function () {
	var module = {};

	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js")
	var App = Grs.load("<grs>/app/app.js")

	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);

	var params = {};

	// fecha acta | nro acta | usuario | formularioDigital

	//Sqls
	var SqlTmpResultado  = 
		"CREATE TEMP TABLE tmpResultado AS " +
		" SELECT f.fechacierre, f.numero AS numero, f.usuario AS inspector, fd1.valor as lugar, fd2.valor as observacion, fd3.imagen as imagen, f.fechacierre as fecha "+
		" FROM for_formulario f " +
		" LEFT JOIN for_formulariodetalle fd1 ON f.id = fd1.idformulario and fd1.idtipoformulariodefseccioncampo IN (select id from apm_aplperfilseccioncampo where idcampo = (select id from apm_campo where codigo like 'lugar_infraccion')) "+
		" LEFT JOIN for_formulariodetalle fd2 ON f.id = fd2.idformulario and fd2.idtipoformulariodefseccioncampo IN (select id from apm_aplperfilseccioncampo where idcampo = (select id from apm_campo where codigo like 'observaciones')) "+
		" LEFT JOIN for_formulariodetalle fd3 ON f.id = fd3.idformulario and fd3.idtipoformulariodefseccioncampo IN (select id from apm_aplperfilseccioncampo where idcampo = (select id from apm_campo where codigo like 'imagen_anexo')) "+
		" WHERE 1 = 1 "+
		" AND f.estado = 1"+
		" AND f.idestadotipoformulario = 3 "+
		" [[ AND f.idusuarioapmcierre = #i ]]" +
		" [[ AND f.idtipoformulario = #i ]]" +
		" [[ AND DATE(f.fechacierre) >= #d ]]" +
		" [[ AND DATE(f.fechacierre) <= #d ]]" +
		" [[ AND to_timestamp(to_char(f.fechainicio, 'HH24:MI'), 'HH24:MI') >= to_timestamp(#s, 'HH24:MI') ]]" +
		" [[ AND to_timestamp(to_char(f.fechacierre, 'HH24:MI'), 'HH24:MI') <= to_timestamp(#s, 'HH24:MI') ]]" +
		" [[ AND f.idarea = #i ]]" +
		" ORDER BY 1 ";


	var SqlTmpResultadoDrop = "[noerr] drop table tmpResultado";

	var SqlOut = "SELECT * from tmpResultado";

	// processReport genera el resultado del proceso 
	module.parametros = function (p) {
		this.params = [p.idUsuarioApm,p.idTipoFormulario, p.fechaDesde, p.fechaHasta, p.horaDesde, p.horaHasta, p.idArea];
	}

	// processReport genera el resultado del proceso
	module.procesar = function () {
		Adp.message("Comienza Procesamiento Reporte Total de Actas");
		Sql.exec(SqlTmpResultadoDrop);
		Sql.exec(SqlTmpResultado, this.params);
		Adp.message("Finalizando Procesamiento\n");
	}
	// outputReport: Recorre las tablas de resultado y genera los archivos de salida.
	module.output = function (params) {
		Adp.log("Comienza construccion de salida\n");

		//cuentas
//		Util.mkReport("salida/ReporteActas", "csv", {separator:"|"}, "ReporteActas (csv)", "Reporte de Actas", report, params);
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
//		out.include("<grs>/html/exthead.html", dataMap);

		out.fmt({types:{date:'%1$td/%1$tm/%1$tY %1$tH:%1$tM'}});

		//titulos
		out.title("Listado Detallado Actas");

		// Seccion de campos
		out.fieldset("Par\u00E1metros");
		if(param.idUsuarioApm)
			out.field("Inspector: ", Sql.value("select nombre from apm_usuarioapm where id = #i", param.idUsuarioApm));
		else
			out.field("Inspector: ", "Todos");

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
		out.write("<col width='30'><col width='90'>");
		out.thead(["Datos","Imagenes"]);
		// ordenar segun estos key del row
		out.order(["inspector","imagen"]); 

		var total = 0;
		var count  = Sql.count(SqlOut);
		var cursor = Sql.cursor(SqlOut);

		var imagenes = "";
		var rowRef = null;
		var i = 0;

		while (row = cursor.read()) {
			//
			if(rowRef == null){
				rowRef = row;
			}

			if (count == ++i && row.imagen) {
				//
				var imageB64 = Packages.coop.tecso.demoda.util.Base64.encodeToString(row.imagen, 0);
				imagenes += "<img  src='data:image/png;base64,"+imageB64+"'></img>&nbsp;&nbsp;" ;
				//
				rowRef = row;
			}

			if(rowRef.numero != row.numero || count == i){
				rowRef.imagen = imagenes;
				
				// INSPECTOR
				rowRef.inspector = "<b>Inspector: </b>" + rowRef.inspector + "<br>";
				// NUMERO
				rowRef.inspector += "<b>N\u00FAmero: </b>" + rowRef.numero + "<br>";
				// FECHA
				rowRef.inspector += "<b>Fecha: </b>" + Util.formatDate(new Date(rowRef.fecha.getTime())) + "<br>";
				// LUGAR
				rowRef.inspector += "<b>Lugar: </b>";
				if (rowRef.lugar) rowRef.inspector += rowRef.lugar.split("|")[1]+" "+rowRef.lugar.split("|")[2];
				// OBSERVACIONES
				rowRef.inspector += "<br><b>Observaciones: </b>";
				if (rowRef.observacion)	rowRef.inspector += rowRef.observacion;

				out.row(rowRef);
				rowRef = row;
				imagenes = "";
				++total;	
			}

			if (row.imagen && i < count) {
				//
				var imageB64 = Packages.coop.tecso.demoda.util.Base64.encodeToString(row.imagen, 0);
				imagenes += "<img  src='data:image/png;base64,"+imageB64+"'></img>&nbsp;&nbsp;" ;
			}
		}

		if (count == 0){
			out.td("Sin Resultados")
		};

		out.table("Total Actas: " + total.toString().replace(",", " ", "g"));
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

ReporteActasDetallado.main();