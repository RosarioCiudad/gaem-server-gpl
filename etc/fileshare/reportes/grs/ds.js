/*
Modulo de constantes de conextiones a datasources
*/
(function () {
	var ds = {};
	
	//Conexiones a DB
	ds.DsTmf = "java:comp/env/ds/tmf";
	ds.DsSiat = "java:comp/env/ds/siat";
	ds.DsSwe = "java:comp/env/ds/swe";
	ds.DsGrs = "java:comp/env/ds/grs";

	// Replicas
	ds.DsTmf2 = "java:comp/env/ds/tmf";
	ds.DsSiat2 = "java:comp/env/ds/siat";
	ds.DsSwe2 = "java:comp/env/ds/swe";
	ds.DsGrs2 = "java:comp/env/ds/grs";

	return ds;
}());
