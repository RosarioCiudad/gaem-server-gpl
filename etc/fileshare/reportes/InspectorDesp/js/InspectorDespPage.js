Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var InspectorDesp = function () {
	var module = {};

	var Util = Grs.load("<grs>/util.js");
	var Const = Grs.load("<grs>/app/const.js");
	var App = Grs.load("<grs>/app/app.js");

	var Page = Grs.page();
	var Adp = Grs.adp();
	var Sql = Grs.sql(Const.DsGait);
	
	var SqlUsuario = 
		"SELECT id, nombre ||' ['||username ||']'  \"label\" " +
			"FROM apm_usuarioapm WHERE estado = 1 ORDER BY username"; 
	
	var SqlDireccion = 
		"SELECT id, descripcion AS \"label\" " +
			"FROM def_direccion WHERE estado = 1 ORDER BY descripcion";
	
	var SqlArea = 
		"SELECT id, descripcion AS \"label\" " +
			"FROM def_area WHERE 1=1  [[AND idDireccion = #i ]] AND estado = 1 ORDER BY descripcion";
		
	var render = function (params) {
		Page.title("Reporte de Desplazamiento de Inspectores");
		Page.p("Reporte sobre infomapa con el desplazamiento de los distintos inspectores.");
	
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
					//"colspan" : "3",
					"options": Util.join(Const.Todos, Sql.list(SqlUsuario))
					});

		Page.label({"label":"Suavizar recorrido:"});
		Page.input({"name" : "suavizar",
					"type": "select",
					"value" : params.suavizar,
					"options": Const.NoSi
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

//		Page.label({"label":"Tolerancia (0.0 ~ 5.0):"});
//		Page.input({"name" : "tolerancia",
//					"type": "text",
//					"value" : params.tolerancia
//					});
	
		Page.endfieldset();
	}
	
	module.get = function () {
		var params = Page.parameters();
		
		if(!params.fechaHasta)
			params.fechaHasta = new Date();
		
		if (params.idDireccion == null)
			params.idDireccion = "";
		
//		if(!params.tolerancia)
//			params.tolerancia = "0";
		
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
		
//		if(Util.isNumber(params.tolerancia)){
//			if(params.tolerancia > 5 || params.tolerancia < 0){
//				Grs.printf("params.tolerancia: '%s'\n", params.tolerancia);
//				Page.message("\"Tolerancia\" debe estar entre 0.0 y 5.0");
//			}
//		}else{
//			Grs.printf("params.tolerancia: '%s'\n", params.tolerancia);
//			Page.message("Formato \"Tolerancia\" inv\u00e1lido. Ingrese s\u00f3lo números.");
//		}
		
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("InspectorDesp", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end InspectorDesp

Grs.request(InspectorDesp);