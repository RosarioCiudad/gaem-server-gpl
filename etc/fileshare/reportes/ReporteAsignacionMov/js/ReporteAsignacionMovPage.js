Grs.load("<grs>/grs.js")
Grs.setDebug(1);

var ReporteAsignacionMov = function () {
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

	var SqlUsrAsign = 
		"SELECT id, usuariogait ||' ['||usuariogait ||']' \"label\" " +
			"FROM seg_usuariogait WHERE estado = 1 ORDER BY usuariogait";
	
	var render = function (params) {
		Page.title("Reporte de Asignaci\u00f3n de Dispositivos M\u00f3viles");
		Page.p("Reporte de dispositivos asignados.");
	
		Page.fieldset({"label":"Par\u00e1metros"});
		
		Page.label({"label":"Fecha Asignaci\u00f3n Desde:"});
		Page.input({"name" : "fechaAsignDesde",
					"type": "date",
					"value": params.fechaAsignDesde
					});

		Page.label({"label":"Fecha Asignaci\u00f3n Hasta:"});
		Page.input({"name" : "fechaAsignHasta",
					"type": "date",
					"value": params.fechaAsignHasta
					});
		
		Page.label({"label":"Nombre dispositivo:"});
		Page.input({"name" : "nombreMov",
					"type": "text",
					"value" : params.nombreMov
					});
		
		Page.label({"label":"IMEI:"});
		Page.input({"name" : "IMEI",
					"type": "text",
					"value" : params.IMEI
					});
		
		Page.label({"label":"Usuario que asign\u00f3:"});
		Page.input({"name" : "usrAsign",
					"type": "select",
					"value" : params.usrAsign,
					"options": Util.join(Const.Todos, Sql.list(SqlUsrAsign))
					});
		
		Page.label({"label":"Inspector:"});
		Page.input({"name" : "idUsuarioApm",
					"type": "select",
					"value" : params.idUsuarioApm,
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
		var params = Page.parameters();
		

		if (params.idDireccion == null)
			params.idDireccion = "";
		
		if(!params.fechaHasta)
			params.fechaHasta = new Date();
		render(params);
	}
	
	module.accept = function () {
		var params = Page.parameters();
		
		// validaciones
//		if (params.fechaAsignDesde == "") {
//			Grs.printf("params.fechaAsignDesde: '%s'\n", params.fechaAsignDesde);
//			Page.message("Fecha Asignaci\u00f3n Desde es requerido.");
//		}
//		if (params.fechaAsignHasta == "") {
//			Grs.printf("params.fechaAsignHasta: '%s'\n", params.fechaAsignHasta);
//			Page.message("Fecha Asignaci\u00f3n Hasta es requerido.");
//		}
//		if (params.nombreMov == "") {
//			Grs.printf("params.nombreMov: '%s'\n", params.nombreDisp);
//			Page.message("Nombre de dispositivo es requerido.");
//		}
//		if (params.IMEI == "") {
//			Grs.printf("params.IMEI: '%s'\n", params.IMEI);
//			Page.message("IMEI es requerido.");
//		}
//		if (params.idUsuarioApm == "") {
//			Grs.printf("params.idUsuarioApm: '%s'\n", params.idUsuarioApm);
//			Page.message("Usuario es requerido.");
//		}
//		if (params.usrAsign == "") {
//			Grs.printf("params.usrAsign: '%s'\n", params.usrAsign);
//			Page.message("Usuario que asign\u00f3 es requerido.");
//		}
				
		// si no hay advertencias scheduleamos el reporte
		if (Page.messages().length == 0) {
			Adp.scheduleReport("ReporteAsignacionMov", "Iniciando Reporte", params);
		}
		
		render(params);
	}
	
	return module;

}();//end ReporteAsignacionMov

Grs.request(ReporteAsignacionMov);