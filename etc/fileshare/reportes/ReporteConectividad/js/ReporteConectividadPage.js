Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var ReporteConectividad = function () {
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
		
	var render = function (params) {
		Page.title("Reporte de Conectividad");
		Page.p("Reporte de conectividad en un per\u00edodo.");
	
		Page.fieldset({"label":"Par\u00e1metros"});
		
		Page.label({"label":"Fecha Cierre Desde:*"});
		Page.input({"name" : "fechaDesde",
					"type": "date",
					"value": params.fechaDesde
					});
		
		Page.label({"label":"Hora Cierre Desde: (HH:mm)"});
		Page.input({"name" : "horaDesde",
					"type": "text",
					"value": params.horaDesde
					});
		
		Page.label({"label":"Fecha Cierre Hasta:*"});
		Page.input({"name" : "fechaHasta",
					"type": "date",
					"value": params.fechaHasta
					});
		
		Page.label({"label":"Hora Cierre Hasta: (HH:mm)"});
		Page.input({"name" : "horaHasta",
					"type": "text",
					"value": params.horaHasta
					});
		
		Page.label({"label":"Dispositivo:"});
		Page.input({"name" : "idDispositivoMovil",
					"type": "select",
					"value" : params.idDispositivoMovil,
					"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlDispositivo))
					});
	
		Page.endfieldset();
	}
	
	module.get = function () {
		var params = Page.parameters();
		
		if(!params.fechaHasta)
			params.fechaHasta = new Date();
		render(params);
	}
	
	module.accept = function () {
		var params = Page.parameters();
		var validateDesde = false;
		var validateHasta = false;
		
		// validaciones
		if (params.fechaDesde == "") {
			Grs.printf("params.fechaDesde: '%s'\n", params.fechaDesde);
			Page.message("Fecha Desde es requerido.");
		}else{
			validateDesde = true;
		}
		
		if (params.fechaHasta == "") {
			Grs.printf("params.fechaHasta: '%s'\n", params.fechaHasta);
			Page.message("Fecha Hasta es requerido.");
		}else{
			validateHasta = true;
		}
		
		if(validateDesde && !Util.validateDate(params.fechaDesde)){
			Grs.printf("params.fechaDesde: '%s'\n", params.fechaDesde);
			Page.message("Fecha Desde tiene un formato inv\u00e1lido. Escriba la fecha en el formato dd/mm/YYYY");
			validateDesde = false;
		}
		
		if(validateHasta && !Util.validateDate(params.fechaHasta)){
			Grs.printf("params.fechaHasta: '%s'\n", params.fechaHasta);
			Page.message("Fecha Hasta tiene un formato inv\u00e1lido. Escriba la fecha en el formato dd/mm/YYYY");
			validateHasta = false;
		}
		
		
		if (validateDesde && validateHasta && (params.fechaDesde.getTime() > params.fechaHasta.getTime())) {
			Grs.printf("params.fechaDesde: '%s' mayor a params.fechaHasta: '%s'\n", params.fechaDesde.getTime(), params.fechaHasta.getTime());
			Page.message("Fecha Desde no puede ser mayor a Fecha Hasta.");
		}

		var newDate = new Date(); 
		
		if (validateDesde && (params.fechaDesde.getTime() > newDate.getTime())) {
			Grs.printf("params.fechaDesde: '%s' mayor a Hoy: '%s'\n", params.fechaDesde.getTime(), newDate.getTime());
			Page.message("Fecha Desde no puede ser mayor a la fecha del d\u00eda.");
		}
		if (validateHasta && (params.fechaHasta.getTime() > newDate.getTime())) {
			Grs.printf("params.fechaHasta: '%s' mayor a Hoy: '%s'\n", params.fechaHasta.getTime(), newDate.getTime());
			Page.message("Fecha Hasta no puede ser mayor a la fecha del d\u00eda.");
		}
		
		if(params.horaDesde != "" && !Util.validateHHMm(params.horaDesde)){
			Grs.printf("params.horaDesde: '%s'\n", params.horaDesde);
			Page.message("Formato \"Hora Desde\" inv\u00e1lido.");
		}
		
		if(params.horaHasta != "" && !Util.validateHHMm(params.horaHasta)){
			Grs.printf("params.horaHasta: '%s'\n", params.horaHasta);
			Page.message("Formato \"Hora Hasta\" inv\u00e1lido.");
		}
		
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("ReporteConectividad", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end ReporteConectividad

Grs.request(ReporteConectividad);