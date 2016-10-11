Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var ReporteSeguimientoDisp = function () {
	var module = {};

	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js");
	var App = Grs.load("<grs>/app/app.js");

	var Page = Grs.page();
	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);
	
	var SqlDispositivo = 
		"SELECT id, descripcion \"label\" " +
			"FROM apm_dispositivomovil WHERE estado = 1 ORDER BY descripcion";

	var SqlUsuario = 
		"SELECT id, nombre ||' ['||username ||']' \"label\" " +
			"FROM apm_usuarioapm WHERE estado = 1 ORDER BY username";
	
	var SqlDireccion = 
		"SELECT id, descripcion AS \"label\" " +
			"FROM def_direccion WHERE estado = 1 ORDER BY descripcion";
	
	var SqlArea = 
		"SELECT id, descripcion AS \"label\" " +
			"FROM def_area WHERE 1=1  [[AND idDireccion = #i ]] AND estado = 1 ORDER BY descripcion";

	
	var render = function (params) {
		Page.title("Reporte de Seguimiento de Dispositivos");
		Page.p("Reporte de seguimiento de dispositivos m\u00f3viles.");
	
		Page.fieldset({"label":"Par\u00e1metros"});
		
		Page.label({"label":"Fecha Desde:"});
		Page.input({"name" : "fechaPosDesde",
					"type": "date",
					"value": params.fechaPosDesde
					});

		Page.label({"label":"Fecha Hasta:"});
		Page.input({"name" : "fechaPosHasta",
					"type": "date",
					"value": params.fechaPosHasta
					});
		
		Page.label({"label":"Dispositivo:"});
		Page.input({"name" : "dispositivo",
					"type": "select",
					"value" : params.dispositivo,
					"options": Util.join(Const.Todos, Sql.list(SqlDispositivo))
					});
		
		Page.label({"label":"Inspector:"});
		Page.input({"name" : "idUsuarioApm",
					"type": "select",
					"value" : params.idUsuarioApm,
					//"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlUsuario))
					});
		
		Page.label({"label":"Direccion:"});
		Page.input({"name" : "idDireccion",
					"type": "select",
					"value" : params.idDireccion,
					"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlDireccion)),
					"onChange": "grsfunc('get', '')"
					});
		
		Page.label({"label":"Area:"});
		Page.input({"name" : "idArea",
					"type": "select",
					"value" : params.idArea,
					//"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlArea, params.idDireccion))
					});
		
	
		Page.endfieldset();
	}
	
	module.get = function () {
		var params = Page.parameters();
		
		if(!params.fechaPosHasta)
			params.fechaPosHasta = new Date();

		if (params.idDireccion == null)
			params.idDireccion = "";
		
		render(params);
	}
	
	module.accept = function () {
		var params = Page.parameters();
		
		var validateDesde = false;
		var validateHasta = false;
		
		// validaciones
		if (!(params.fechaPosDesde == ""))
			validateDesde = true;
		
		if (!(params.fechaPosHasta == ""))
			validateHasta = true;
		
		if(validateDesde && !Util.validateDate(params.fechaPosDesde)){
			Grs.printf("params.fechaPosDesde: '%s'\n", params.fechaPosDesde);
			Page.message("Fecha Desde tiene un formato inv\u00e1lido. Escriba la fecha en el formato dd/mm/YYYY");
			validateDesde = false;
		}
		
		if(validateHasta && !Util.validateDate(params.fechaPosHasta)){
			Grs.printf("params.fechaPosHasta: '%s'\n", params.fechaPosHasta);
			Page.message("Fecha Hasta tiene un formato inv\u00e1lido. Escriba la fecha en el formato dd/mm/YYYY");
			validateHasta = false;
		}
		
		
		if (validateDesde && validateHasta && (params.fechaPosDesde.getTime() > params.fechaPosHasta.getTime())) {
			Grs.printf("params.fechaPosDesde: '%s' mayor a params.fechaPosHasta: '%s'\n", params.fechaPosDesde.getTime(), params.fechaPosHasta.getTime());
			Page.message("Fecha Desde no puede ser mayor a Fecha Hasta.");
		}

		var newDate = new Date(); 
		
		if (validateDesde && (params.fechaPosDesde.getTime() > newDate.getTime())) {
			Grs.printf("params.fechaPosDesde: '%s' mayor a Hoy: '%s'\n", params.fechaPosDesde.getTime(), newDate.getTime());
			Page.message("Fecha Desde no puede ser mayor a la fecha del d\u00eda.");
		}
		if (validateHasta && (params.fechaPosHasta.getTime() > newDate.getTime())) {
			Grs.printf("params.fechaPosHasta: '%s' mayor a Hoy: '%s'\n", params.fechaPosHasta.getTime(), newDate.getTime());
			Page.message("Fecha Hasta no puede ser mayor a la fecha del d\u00eda.");
		}
				
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("ReporteSeguimientoDisp", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end ReporteSeguimientoDisp

Grs.request(ReporteSeguimientoDisp);
