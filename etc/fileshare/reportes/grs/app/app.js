// modulo app
// funciones que hacen referencia a la aplicaci\u00f3n
(function () {
	// init
	var app = {};

	// privado
	
	// lee el valor de un parametro
	var parametro = function(name){
		var LSql = Grs.sql(this.DsApp);
		var value = LSql.value("select valor from def_parametro where codParam = #s", name);
		LSql.close();
		return value;
	}

	// publico
	
	//llamar esto desde entornos fuera del tomcat.
	app.initialize = function() {
   		Packages.ar.gov.rosario.grs.def.iface.service.DefServiceLocator.getConfiguracionService().initializeGrs();
   	}

	
	//public const
	//app.SiatDbName = parametro("SiatDbName");
	
	//retorna el usercontext que ejecuta el reporte
	app.currentUserContext = function() {
		return Packages.coop.tecso.demoda.iface.helper.DemodaUtil.currentUserContext();
   	}
	
	//---
	
	//---
	
	return app;
}());
