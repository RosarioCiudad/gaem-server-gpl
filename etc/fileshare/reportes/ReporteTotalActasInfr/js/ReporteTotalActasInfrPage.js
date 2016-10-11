Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var ReporteTotalActasInfr = function () {
	var module = {};

	var Ds    = Grs.load("<grs>/ds.js");
	var Util  = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js");
	var App   = Grs.load("<grs>/app/app.js");
	
	var Page   = Grs.page();
	var Adp    = Grs.adp();
	var Sql    = Grs.sql(Const.DsGait);
	var SqlTmf = Grs.sql(Ds.DsTmf2);

	
	var SqlInfraccion = 
		"SELECT codigo id, descripcion ||' ['||codigo ||']' \"label\" " +
			"FROM aid_infraccion WHERE estado = 1 ORDER BY descripcion";
	
	var SqlCalle = 
		"SELECT cod_calle id, nom_calle \"label\" " +
		"FROM	 calles " +
		"ORDER BY nom_calle ";
	
	var render = function (params) {
		Page.title("Reporte Total de actas por Infracci\u00f3n");
		Page.p("Reporte sobre el total de actas realizadas por infracci\u00f3n.");
	
		Page.fieldset({"label":"Par\u00e1metros"});
		
		Page.label({"label":"Fecha Desde:"});
		Page.input({"name" : "fechaDesde",
					"type": "date",
					"value": params.fechaDesde
					});
		
		Page.label({"label":"Fecha Hasta:"});
		Page.input({"name" : "fechaHasta",
					"type": "date",
					"value": params.fechaHasta
					});
		
		Page.label({"label":"Infraccion:"});
		Page.input({"name" : "idInfraccion",
					"type": "select",
					"value" : params.idInfraccion,
					"colspan" : "3",
					"options": Util.join({"id":'', "label":"Todos"}, Sql.list(SqlInfraccion))
					});

		Page.label({"label":"Calle:"});
		Page.input({"name" : "idCalle",
					"type": "select",
					"value" : params.idCalle,
					"colspan" : "3",
					"options": Util.join(Const.Todos, SqlTmf.list(SqlCalle))
					});

		Page.label({"label":"Altura Desde:"});
		Page.input({"name" : "alturaDesde",
					"type": "integer",
					"value": params.alturaDesde
					});
		
		Page.label({"label":"Altura Hasta:"});
		Page.input({"name" : "alturaHasta",
					"type": "integer",
					"value": params.alturaHasta
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
		if (!(params.fechaDesde == ""))
			validateDesde = true;
		
		if (!(params.fechaHasta == ""))
			validateHasta = true;
		
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
		
		if (!(params.alturaDesde == "") && !Util.isNumber(params.alturaDesde)){
			Grs.printf("params.alturaDesde: '%s' \n", params.alturaDesde);
			Page.message("Altura Desde contiene caracteres no v\u00e1lidos. Ingrese s\u00f3lo números.");
		}

		if (!(params.alturaHasta == "") && !Util.isNumber(params.alturaHasta)){
			Grs.printf("params.alturaHasta: '%s' \n", params.alturaHasta);
			Page.message("Altura Hasta contiene caracteres no v\u00e1lidos. Ingrese s\u00f3lo números.");
		}

		
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("ReporteTotalActasInfr", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end ReporteTotalActasInfr

Grs.request(ReporteTotalActasInfr);
