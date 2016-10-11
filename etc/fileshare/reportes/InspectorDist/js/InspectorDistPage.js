Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var InspectorDist = function () {
	var module = {};

	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js");
	var App = Grs.load("<grs>/app/app.js");

	var Page = Grs.page();
	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);
	
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
		Page.title("Reporte de Distribuci\u00f3n de Inspectores");
		Page.p("Reporte sobre infomapa con distribuci\u00f3n de inspectores.");
	
		Page.fieldset({"label":"Par\u00e1metros"});	
		
		Page.label({"label":"Fecha Desde:*"});
		Page.input({"name" : "fechaDesde",
					"type": "date",
					"value": params.fechaDesde
					});
		
		Page.label({"label":"Hora Desde: (HH:mm)"});
		Page.input({"name" : "horaDesde",
					"type": "text",
					"value": params.horaDesde
					});
		
		Page.label({"label":"Fecha Hasta:*"});
		Page.input({"name" : "fechaHasta",
					"type": "date",
					"value": params.fechaHasta
					});
		
		Page.label({"label":"Hora Hasta: (HH:mm)"});
		Page.input({"name" : "horaHasta",
					"type": "text",
					"value": params.horaHasta
					});
		
		Page.label({"label":"Inspector:"});
		Page.input({"name" : "idUsuarioApm",
					"type": "select",
					"value" : params.idUsuarioApm,
					"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlUsuario))
					});
		
		Page.label({"label":"Direccion:"});
		Page.input({"name" : "idDireccion",
					"type": "select",
					"value" : params.idDireccion,
					//"colspan" : "3",
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
		//For the time now
		Date.prototype.timeNow = function(){
		     return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes();
		};

		var params = Page.parameters();
		
		var newDate = new Date();
		var valorFecha = newDate.valueOf();
		
		if (params.idDireccion == null)
			params.idDireccion = "";
		
		if(!params.fechaDesde)
			params.fechaDesde = newDate;

		if(!params.fechaHasta)
			params.fechaHasta = newDate;
		
		var MS_PER_MINUTE = 60000;
		var myStartDate = new Date(valorFecha - 15 * MS_PER_MINUTE);


		var myEndDate = new Date(valorFecha + 5 * MS_PER_MINUTE);
		
		if(!params.horaDesde)
			params.horaDesde = myStartDate.timeNow();
		
		if(!params.horaHasta)
			params.horaHasta = myEndDate.timeNow();
		
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

		
		if(params.horaDesde != "" && !Util.validateHHMm(params.horaDesde)){Hasta
			Grs.printf("params.horaDesde: '%s'\n", params.horaDesde);
			Page.message("Formato \"Hora Desde\" inv\u00e1lido.");
		}
		
		if(params.horaHasta != "" && !Util.validateHHMm(params.horaHasta)){
			Grs.printf("params.horaHasta: '%s'\n", params.horaHasta);
			Page.message("Formato \"Hora Hasta\" inv\u00e1lido.");
		}
		
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("InspectorDist", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end InspectorDist

Grs.request(InspectorDist);