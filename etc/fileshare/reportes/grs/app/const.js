/*
Modulo de constantes funcionales de la aplicacion Grs

Convensiones:

* Para constantes que representan valores en tablas
  usar la siguiente convencion:

	[Tabla][Columna][Valor]

  Las partes deben recordar los nombres de tablas
  colunas y valores, puden ser iguales o si existe un 
  mejor nombre se puede usar.
  pej:
	RecursoIdTgi = 14
	RecursoCodTgi = 'TGI'
	EstadoDeudaIdJud = 2
*/
(function () {
	var cons = {};
	
	//Conexiones a DB
	cons.DsSiat = "java:comp/env/ds/siat";
	cons.DsSwe = "java:comp/env/ds/swe";
	cons.DsGrs = "java:comp/env/ds/grs";
	cons.DsGait = "java:comp/env/ds/gait";

	
	cons.DsApp = cons.DsGrs;
	// Entidades de Siat
	
	cons.RecursoIdTgi = 14;
	cons.RecursoIdDrei = 15;
	cons.RecursoIdEtur = 16;
	cons.RecursoIdDP = 21;
	
    
	// Leyendas comunes para ComboBox
	
	cons.Seleccionar = [{"id":'', "label":"Seleccionar..."}];
	cons.Ninguno = [{"id":'', "label":"Ninguno"}];
	cons.Ninguna = [{"id":'', "label":"Ninguna"}];
	cons.Todos = [{"id":'', "label":"Todos"}];
	cons.Todas = [{"id":'', "label":"Todas"}];
	cons.Vacio = [{"id":'', "label":"Vacio"}];
	cons.NoSi = [{"id":'0', "label":"No"}, {"id":'1', "label":"Si"}];
	
	return cons;
}());