# version 1.0.TRUNK

--Base aplmov:
-------------

--MSFGAEM-67 [DEV] - Agregar código de inspector en mantenedor
ALTER TABLE apm_usuarioapm ADD COLUMN numeroInspector varchar(50);

	
--MSFGAEM-58 [DEV] - Permitir relacionar Formulario con Tipo de Formulario 
alter table apm_aplicacionperfil add idTipoFormulario INTEGER;
update apm_aplicacionperfil set idTipoFormulario = 1;
ALTER TABLE apm_aplicacionperfil ADD CONSTRAINT fk_apm_aplicacionperfil_idTipoFormulario FOREIGN KEY (idTipoFormulario) REFERENCES for_tipoformulario (id);


-- MSFGAEM-33 [DEV] - Permitir excluir secciones de impresión 
ALTER TABLE apm_aplicacionPerfilSeccion ADD permiteImpresion smallint;



INSERT INTO swe_accmodapl (idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (3,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 3),'Mantenedor de Perfil Acceso Aplicación: agregar','ABM_PerfilAccesoAplicacion','agregar','gaem',CURRENT_DATE,1);
INSERT INTO swe_accmodapl (idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (3,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 3),'Mantenedor de  Perfil Acceso Aplicación:: Modificar','ABM_PerfilAccesoAplicacion','modificar','gaem',CURRENT_DATE,1);
INSERT INTO swe_accmodapl (idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (3,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 3),'Mantenedor de  Perfil Acceso Aplicación:: Ver','ABM_PerfilAccesoAplicacion','ver','gaem',CURRENT_DATE,1);
INSERT INTO swe_accmodapl (idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (3,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 3),'Mantenedor de  Perfil Acceso Aplicación:: Eliminar','ABM_PerfilAccesoAplicacion','eliminar','gaem',CURRENT_DATE,1);



INSERT INTO pro_proceso VALUES ( default,'InspectorDist','Reporte de Distribución de Inspectores',0,1,'/mnt/grs/reporte/gait/InspectorDist',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-09 16:36:53.0',1,'',0,null,null,null,null);
INSERT INTO pro_proceso VALUES ( default,'InspectorDesp','Reporte de Desplazamiento de Inspectores',0,1,'/mnt/grs/reporte/gait/InspectorDesp',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-06 14:56:52.0',1,'',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteTotalActas','Reporte Total de actas por Inspector',0,1,'/mnt/grs/reporte/gait/ReporteTotalActas',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-09 18:10:53.0',1,'',0,null,null,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteAsignacionMov','Reporte asignación de dispositivos móviles',0,1,'/mnt/grs/reporte/gait/ReporteAsignacionMov',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2014-01-21 17:41:44.0',1,'tecso',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteAsignacionImp','Reporte asignación de impresoras',0,1,'/mnt/grs/reporte/gait/ReporteAsignacionImp',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-09 16:43:41.0',1,'',0,null,null,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteDistActas','Reporte distribución de actas',0,1,'/mnt/grs/reporte/gait/ReporteDistActas',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-09 17:30:09.0',1,'',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteTotalActasInfr','Reporte total de actas por infraccion',0,1,'/mnt/grs/reporte/gait/ReporteTotalActasInfr',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2013-12-11 16:00:18.0',1,'',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteSeguimientoDisp','Reporte de seguimiento de dispositivos móviles',0,1,'/mnt/grs/reporte/gait/ReporteSeguimientoDisp',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-09 18:42:37.0',1,'',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteDistInf','Reporte de Distribución de Infracciones',0,1,'/mnt/grs/reporte/gait/ReporteDistInf',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2013-12-23 13:00:31.0',1,'tecso',0,null,1,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteConectividad','Reporte de Conectividad',0,1,'/mnt/grs/reporte/gait/ReporteConectividad',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2014-01-22 09:40:17.0',1,'tecso',0,null,null,null,null);
INSERT INTO pro_proceso VALUES ( default,'ReporteTotalActasPorArea','Reporte Total de Actas por Area',0,1,'/mnt/grs/reporte/gait/ReporteTotalActasPorArea',1,1,'ar.gov.rosario.gait.pro.buss.GrsWorker',null,null,null,null,'admin','2015-03-18 13:15:06.0',1,'',0,null,null,null,null);


-- MSFGAEM-17: [DEV] - Servidor: Desarrollar servicio de login Manager y Móvil vía SIMGEI
INSERT INTO def_parametro (codparam,desparam,valor,usuario,fechaultmdf,estado,version) 
VALUES ('UriSimgei','URL consulta SIMGEI','http://10.20.12.26:8080/simgeiWS/AuthenticationWebServiceBean?wsdl','tecso',CURRENT_TIMESTAMP,1,1);

-- MSFGAEM-9: [TASK] - Servidor: Analizar factibilidad técnica cierre de certificados
INSERT INTO def_parametro (codparam,desparam,valor,usuario,fechaultmdf,estado,version) 
VALUES ('CertificatePath','Path certificado PKSC12','/mnt/gait/privado/Cert/lfagana0.p12','tecso',CURRENT_TIMESTAMP,1,1);



# version 1.0.20140507.RC
--Base aplmov:
-------------
-- [Jira MRGAIT-389]:[DEV] - Móvil.AIDigital - Verificar time-out cierre de sesión 
-- Parametro Time Out de Sesion
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'SessionTimeout','Time-Out de Sesion -en horas-','6','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';

--Base swe:
-----------

--Menu swe:
-----------

-- [Jira MRGAIT-312]: [DEV] - Servidor - Gestión de avisos de pánico 
--Menu: Aplicación
-------	   |-> Administración de Panicos
--						|-> Gestión de Pánicos
--		url:'	/apm/BuscarPanico.do?method=inicializar'
--		Modulo / Accion / Metodo: apm / ABM_Panico / buscar



# version 1.0.20140203.RC
--Base aplmov:
-------------

-- Parametro Mensaje Aleta de Pánico
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'PanicoMessage','Mensage SMS utilizado en alerta de pánico',
'ALERTA\nhttp://infomapa.rosario.gov.ar/emapa/movil.html?latitud=${latitud}&longitud=${longitud}&nivelZoom=10\n${inspector}\n${fecha}-${hora}','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';

-- Parametro Mensaje Alerta de Pánico sin Ubicación
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'LocationNotFoundMessage','Mensaje SMS utilizado en alerta de pánico cuando no se encontró la ubicación',
'ALERTA\nNO SE PUDO OBTENER LA UBICACION\n${inspector}\n${fecha}-${hora}','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';

--Base swe:
-----------

--Menu swe:
-----------

# version 1.0.20140115.RC
--Base aplmov:
-------------
-- [Jira MRGAIT-348]: [DEV] - Servidor - Ajuste visualización avisos de pánico 
ALTER TABLE apm_panico ADD fechaRecepcion DATETIME YEAR TO SECOND NOT NULL;

-- [Jira MRGAIT-323]: [DEV] - Servidor - WS Consultar licencias (pto. Ws 1) 
INSERT INTO def_parametro (id,codparam,desparam,valor,usuario,fechaultmdf,estado,version) 
VALUES (0,'uriConsultaAPSV','URL consulta APSV',' http://websrv1-test.pm.rosario.gov.ar:9010/apsv_ws/spring-ws/apsv.wsdl','tecso',CURRENT,1,1);

-- [Jira MRGAIT-323]: [DEV] - Servidor - WS Consultar licencias (pto. Ws 1)
-- Parametro Tipo Documento
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'CampoTipoDocumentoID','Id Campo Tipo Documento - Acta de Infraccion de Transito ','24','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';
-- Parametro Sexo
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'CampoSexoID','Id Campo Sexo - Acta de Infraccion de Transito ','26','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';
-- Parametro Clase Licencia
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'CampoClaseLicenciaID','Id Campo Clase Licencia - Acta de Infraccion de Transito ','27','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';
-- Cambio en conf de tratamiento
UPDATE apm_campo SET tratamiento = 82 WHERE codigo LIKE 'nroDocumentoConductor'

--Base swe:
-----------

--Menu swe:
-----------


# version 1.0.20131223.RC
--Base aplmov:
-------------

-- [Jira MRGAIT-306]: [DEV] - GRS - Listado técnico 
ALTER TABLE gps_historialubicacion ADD nivelBateria DECIMAL(20,10);
ALTER TABLE gps_historialubicacion ADD nivelSenial DECIMAL(20,10);

-- [Jira MRGAIT-65]: [DEV] - Móvil.ADigital - Envío alertas de pánico
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'UmbralEliminacion','Umbral de Tiempo (en horas) para eliminacion de actas','6','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';

-- [Jira MRGAIT-312]: [DEV] - Servidor - Gestión de avisos de pánico 
CREATE TABLE apm_estadoPanico ( 
	ID SERIAL NOT NULL,
	descripcion VARCHAR(250),
	esInicial SMALLINT,
	transiciones VARCHAR(50),
	usuario VARCHAR(30) NOT NULL,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	estado SMALLINT NOT NULL,
	version INTEGER
);

CREATE TABLE apm_hisEstPan ( 
	ID SERIAL NOT NULL,
	idEstadoPanico INTEGER,
	idPanico INTEGER,
	logCambios VARCHAR(250),
	fecha DATETIME YEAR TO SECOND,
	observaciones VARCHAR(250),
	usuario VARCHAR(30) NOT NULL,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	estado SMALLINT NOT NULL,
	version INTEGER
);

CREATE TABLE apm_panico ( 
	ID SERIAL NOT NULL,
	idDispositivoMovil INTEGER,
	idEstadoPanico INTEGER,
	idUsuarioPanico INTEGER,
	idArea INTEGER,
	fechaPosicion DATETIME YEAR TO SECOND NOT NULL,
	latitud DECIMAL(12,8) NOT NULL,
	longitud DECIMAL(12,8) NOT NULL,
	precision DECIMAL(12,8) NOT NULL,
	origen VARCHAR(20) NOT NULL,
	observacion VARCHAR(250),
	fechaAtencion DATETIME YEAR TO SECOND,
	fechaPanico DATETIME YEAR TO SECOND NOT NULL,
	version INTEGER,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	usuario VARCHAR(30) NOT NULL,
	estado SMALLINT NOT NULL
);

ALTER TABLE apm_estadoPanico ADD CONSTRAINT	PRIMARY KEY (ID);
ALTER TABLE apm_hisEstPan ADD CONSTRAINT PRIMARY KEY (ID);
ALTER TABLE apm_panico ADD CONSTRAINT PRIMARY KEY (ID);
ALTER TABLE apm_hisEstPan ADD CONSTRAINT 
	FOREIGN KEY (idEstadoPanico) REFERENCES apm_estadoPanico (ID);
ALTER TABLE apm_hisEstPan ADD CONSTRAINT  
	FOREIGN KEY (idPanico) REFERENCES apm_panico (ID);
ALTER TABLE apm_panico ADD CONSTRAINT  
	FOREIGN KEY (idDispositivoMovil) REFERENCES apm_dispositivoMovil (ID);
ALTER TABLE apm_panico ADD CONSTRAINT  
	FOREIGN KEY (idEstadoPanico) REFERENCES apm_estadoPanico (ID);
ALTER TABLE apm_panico ADD CONSTRAINT  
	FOREIGN KEY (idArea) REFERENCES def_area (ID);
ALTER TABLE apm_panico ADD CONSTRAINT  
	FOREIGN KEY (idUsuarioPanico) REFERENCES apm_usuarioApm (ID);
	
INSERT INTO apm_estadopanico VALUES(1,'En Curso',1,'2,3','Tecso',CURRENT,1,1);
INSERT INTO apm_estadopanico VALUES(2,'Atendido',0,'','Tecso',CURRENT,1,1);
INSERT INTO apm_estadopanico VALUES(3,'Anulado',0,'','Tecso',CURRENT,1,1);


-- [Jira MRGAIT-311]: [DEV] - Servidor - Gestión de números de pánico 
CREATE TABLE apm_telefonoPanico ( 
	ID SERIAL NOT NULL,
	idArea INTEGER,
	numero VARCHAR(20) NOT NULL,
	descripcion VARCHAR(50),
	usuario VARCHAR(30) NOT NULL,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	estado SMALLINT NOT NULL,
	version INTEGER NOT NULL
);
ALTER TABLE apm_telefonoPanico ADD CONSTRAINT  
	PRIMARY KEY (ID);
ALTER TABLE apm_telefonoPanico ADD CONSTRAINT  
	FOREIGN KEY (idArea) REFERENCES def_area (ID);
	
INSERT INTO apm_tablaversion VALUES(0,'TelefonoPanico',1,'Tecso',CURRENT,1,1);
INSERT INTO apm_aplicaciontabla (id,idaplicacion,idtablaversion,usuario,fechaultmdf,estado,version)
SELECT 0,2,id, 'Tecso',CURRENT,1,1 FROM apm_tablaversion WHERE tabla like 'TelefonoPanico';

-- 
RENAME COLUMN for_formulario.fechaTrasmision TO fechaTransmision;

--Base swe:
-----------

INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Aplicacion  Parametro: Eliminar','ABM_AplicacionParametro','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Aplicacion  Parametro: Ver','ABM_AplicacionParametro','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Aplicacion  Parametro: Modificar','ABM_AplicacionParametro','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Aplicacion  Parametro: Agregar','ABM_AplicacionParametro','agregar','admin',CURRENT,1);


INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor de Pánico:  Cambiar Estado','ABM_Panico','cambiarEstado','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Histórico de Estado Pánico:  Eliminar','ABM_HisEstPan','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Histórico de Estado Pánico:  Modificar','ABM_HisEstPan','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Histórico de Estado Pánico:  Agregar','ABM_HisEstPan','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Histórico de Estado Pánico:  Ver','ABM_HisEstPan','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Panico','ABM_Panico','eliminar','admin', CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Panico','ABM_Panico','buscar','admin', CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Panico','ABM_Panico','modificar','admin', CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Panico','ABM_Panico','agregar','admin', CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),'Mantenedor Panico','ABM_Panico','ver','admin', CURRENT,1);

-- [Jira MRGAIT-311]: [DEV] - Servidor - Gestión de números de pánico 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor Teléfono de Pánico : agregar','ABM_TelefonoPanico','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor Teléfono de Pánico : eliminar','ABM_TelefonoPanico','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor Teléfono de Pánico : modificar','ABM_TelefonoPanico','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor Teléfono de Pánico : ver','ABM_TelefonoPanico','ver','admin',CURRENT,1);

--Menu swe:
-----------



# version 1.0.20131011.RC
--Base aplmov:
-------------

--Base swe:
-----------

--Menu swe:
-----------

# version 1.0.20130926.RC
--Base aplmov:
-------------

--Base swe:
-----------

--Menu swe:
-----------

# version 1.0.20130919.RC
--Base aplmov:
-------------

--Base swe:
-----------

--Menu swe:
-----------

# version 1.0.20130918.RC
--Base aplmov:
-------------

-- [Jira MRGAIT-284]: [DEV] - Móvil.ADigital - Definir parametro de eliminación de actas transmitidas 
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'UmbralEliminacion','Umbral de Tiempo (en horas) para eliminacion de actas','6','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';

-- [JIRA MRGAIT-283]: [DEV] - Móvil.ADigital - Mostrar Dominio en listado general
INSERT INTO apm_aplicacionparametro (id,idaplicacion,codigo,descripcion,valor,usuario,fechaultmdf,estado,version) 
SELECT 0,2,'CampoDominioID','Id Campo Dominio - Acta de Infraccion de Transito','28','admin',CURRENT,1, lastversion + 1 
FROM apm_tablaversion WHERE tabla LIKE 'AplicacionParametro';
--
UPDATE apm_tablaversion SET lastversion = lastversion + 1 WHERE tabla LIKE 'AplicacionParametro';
--
INSERT INTO apm_aplicaciontabla (id,idaplicacion,idtablaversion,usuario,fechaultmdf,estado,version)
SELECT 0,2,id, 'Tecso',CURRENT,1,1 FROM apm_tablaversion WHERE tabla like 'AplicacionParametro';

ALTER TABLE gps_historialUbicacion MODIFY longitud DECIMAL(10,8);
ALTER TABLE gps_historialUbicacion MODIFY precision DECIMAL(10,8);
ALTER TABLE gps_historialUbicacion MODIFY velocidad DECIMAL(10,8);
ALTER TABLE gps_historialUbicacion MODIFY precision DECIMAL(10,8);
ALTER TABLE gps_historialUbicacion MODIFY radio DECIMAL(10,8);
ALTER TABLE gps_historialUbicacion MODIFY altitud DECIMAL(10,8);

--Base swe:
-----------

-- Menus swe:
------------

# version 1.0.20130909.RC
--Base aplmov:
-------------

-- Jira MRGAIT-279: [DEV] - Servidor - Ajuste impresión listado Digital de Acta 
ALTER TABLE for_formulario ADD retienelicencia SMALLINT;
ALTER TABLE for_formulario ADD resumen LVARCHAR(20000);

- [Jira MRGAIT-63]: [DEV] - Movil.DAA - Georeferenciar y transmitir ubicación de inspectores 
CREATE TABLE gps_historialUbicacion ( 
	ID SERIAL NOT NULL,
	idDispositivoMovil INTEGER NOT NULL,
	idUsuarioApm INTEGER NOT NULL,
	idArea INTEGER NOT NULL,
	fechaPosicion DATETIME YEAR TO SECOND NOT NULL,
	fechaUbicacion DATETIME YEAR TO SECOND NOT NULL,
	latitud DECIMAL(8,3) NOT NULL,
	longitud DECIMAL(8,3) NOT NULL,
	precision DECIMAL(8,3) NOT NULL,
	origen VARCHAR(20) NOT NULL,
	velocidad DECIMAL(8,3) NOT NULL,
	radio DECIMAL(8,3) NOT NULL,
	altitud DECIMAL(8,3) NOT NULL,
	estado SMALLINT NOT NULL,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	version SMALLINT,
	usuario VARCHAR(30) NOT NULL
);

ALTER TABLE gps_historialUbicacion ADD CONSTRAINT  
	PRIMARY KEY (ID);
ALTER TABLE gps_historialUbicacion ADD CONSTRAINT  
	FOREIGN KEY (idDispositivoMovil) REFERENCES apm_dispositivoMovil (ID);
ALTER TABLE gps_historialUbicacion ADD CONSTRAINT  
	FOREIGN KEY (idUsuarioApm) REFERENCES apm_usuarioApmDM (ID);
ALTER TABLE gps_historialUbicacion ADD CONSTRAINT  
	FOREIGN KEY (idArea) REFERENCES def_area (ID);


--Base swe:
-----------
-- [Jira MRGAIT-122](http://jira.tecso.coop/browse/MRGAIT-122):[DEV] - Servidor - FOR - Mantenedor de Numeración de Tipo de Formulario 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Numeración : agregar','ABM_Numeracion','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Numeración : buscar','ABM_Numeracion','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Numeración : eliminar','ABM_Numeracion','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Numeración : modificar','ABM_Numeracion','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Numeración : ver','ABM_Numeracion','ver','admin',CURRENT,1);

-- [Jira MRGAIT-120](http://jira.tecso.coop/browse/MRGAIT-120):[DEV] - Servidor - FOR - Mantenedor de Motivos de Anulación de Tipo de Formulario
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Anulación Tipo Formulario : Agregar','ABM_MotivoAnulacionTipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Anulación Tipo Formulario : Buscar','ABM_MotivoAnulacionTipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Anulación Tipo Formulario : Eliminar','ABM_MotivoAnulacionTipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Anulación Tipo Formulario : Modificar','ABM_MotivoAnulacionTipoFormulario','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Anulación Tipo Formulario : Ver','ABM_MotivoAnulacionTipoFormulario','ver','admin',CURRENT,1);

-- [Jira MRGAIT-119](http://jira.tecso.coop/browse/MRGAIT-119):[DEV] - Servidor - FOR - Mantenedor de Motivos de Cierre de Tipo de Formulario 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Cierre Tipo Formulario : Agregar','ABM_MotivoCierreTipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Cierre Tipo Formulario : Buscar','ABM_MotivoCierreTipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Cierre Tipo Formulario : Eliminar','ABM_MotivoCierreTipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Cierre Tipo Formulario : Modificar','ABM_MotivoCierreTipoFormulario','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Motivo Cierre Tipo Formulario : Ver','ABM_MotivoCierreTipoFormulario','ver','admin',CURRENT,1);

-- [Jira MRGAIT-121](http://jira.tecso.coop/browse/MRGAIT-121):[DEV] - Servidor - FOR - Mantenedor de Series de Numeración de Tipo de Formulario
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Serie : Agregar','ABM_Serie','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Serie : Buscar','ABM_Serie','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Serie : Eliminar','ABM_Serie','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Serie : Modificar','ABM_Serie','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Serie :Ver','ABM_Serie','ver','admin',CURRENT,1);

-- [Jira MRGAIT-118](http://jira.tecso.coop/browse/MRGAIT-118):[DEV] - Servidor - FOR - Mantenedor de Tipos de Formularios 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Tipo Formulario : Agregar','ABM_TipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Tipo Formulario : Buscar','ABM_TipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Tipo Formulario : Eliminar','ABM_TipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Tipo Formulario : Ver','ABM_TipoFormulario','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),'Mantenedor Tipo Formulario : Modificar','ABM_TipoFormulario','modificar','admin',CURRENT,1);

-- Menus swe:
------------

-- [Jira MRGAIT-118](http://jira.tecso.coop/browse/MRGAIT-118):[DEV] - Servidor - FOR - Mantenedor de Tipos de Formularios 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Tipos de Formularios
--		url:'	/frm/BuscarTipoFormulario.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_TipoFormulario / buscar

-- [Jira MRGAIT-121](http://jira.tecso.coop/browse/MRGAIT-121):[DEV] - Servidor - FOR - Mantenedor de Series de Numeración de Tipo de Formulario 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Series de Numeración de Tipo de Formulario 
--		url:'	/frm/BuscarSerie.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_Serie / buscar

---- [Jira MRGAIT-119](http://jira.tecso.coop/browse/MRGAIT-119):[DEV] - Servidor - FOR - Mantenedor de Motivos de Cierre de Tipo de Formulario
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Motivos de Cierre de Tipo de Formulario
--		url:'	/frm/BuscarMotivoCierreTipoFormulario.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_MotivoCierreTipoFormulario / buscar

-- [Jira MRGAIT-122](http://jira.tecso.coop/browse/MRGAIT-122):[DEV] - Servidor - FOR - Mantenedor de Numeración de Tipo de Formulario 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Numeracion
--		url:'	/frm/BuscarNumeracion.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_Numeracion / buscar  


# version 1.0.20130829.RC
--Base aplmov:
-------------
-- [Jira MRGAIT-112]: [DEV] - Servidor - Generar Impresión Digital de Actas
ALTER TABLE for_formulario ADD idArea INTEGER;
UPDATE for_formulario SET idArea = 1 WHERE 1=1;
ALTER TABLE for_formulario ADD CONSTRAINT FOREIGN KEY (idArea) REFERENCES def_area (ID);
-- 
CREATE INDEX campo_codigo ON apm_campo (codigo) USING btree ONLINE;

-- [Jira MRGAIT-264]: [DEV] - Móvil.DAA - Sincronizar MAC Address de impresora
INSERT INTO apm_tablaversion VALUES(0,'UsuarioApmImpresora',1,'Tecso',CURRENT,1,1);
--
INSERT INTO apm_aplicaciontabla (id,idaplicacion,idtablaversion,usuario,fechaultmdf,estado,version)
SELECT 0,1,id, 'Tecso',CURRENT,1,1 FROM apm_tablaversion WHERE tabla like 'UsuarioApmImpresora';
--
INSERT INTO apm_tablaversion VALUES(0,'Impresora',1,'Tecso',CURRENT,1,1);

--Base swe:
-----------
-- [Jira MRGAIT-261]: [DEV] - Servidor - Asignación de Impresora/Usuario 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
 VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
 'Mantenedor de Usuario Impresora:  Ver','ABM_UsuarioApmImpresora','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Usuario Impresora:  Agregar','ABM_UsuarioApmImpresora','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
 VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
 'Mantenedor de Usuario Impresora:  Modificar','ABM_UsuarioApmImpresora','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'apm' AND idaplicacion = 4),
'Mantenedor de Usuario Impresora:  Eliminar','ABM_UsuarioApmImpresora','eliminar','admin',CURRENT,1);

-- [Jira MRGAIT-112]: [DEV] - Servidor - Generar Impresión Digital de Actas
INSERT INTO swe_accmodapl (0,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
 VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),
 'Mantenedor de Reportes de Formulario: Buscar','ABM_ReporteFormulario','buscar','admin',CURRENT,1);
 INSERT INTO swe_accmodapl (0,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
 VALUES (0,4,(SELECT id FROM swe_modapl WHERE nombremodulo LIKE 'frm' AND idaplicacion = 4),
 'Mantenedor de Reportes de Formulario: Ver','ABM_ReporteFormulario','ver','admin',CURRENT,1);

-- Menus swe:
----------
-- [Jira MRGAIT-112]: [DEV] - Servidor - Generar Impresión Digital de Actas
--Menu: Reportes
-------	   |-> Administración de Reportes
--						|-> Reporte de Actas
--		url:'	/frm/BuscarReporteFormulario.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_ReporteFormulario / buscar



# version 1.0.20130806.BUILD

# version 1.0.20130731.RC

# version 1.0.20130719.RC
--Base gait:
-----------

--Base swe:
-----------
- [Jira MRGAIT-99](http://jira.tecso.coop/browse/MRGAIT-99):[DEV] - Servidor - APM - Mantenedor de Perfiles de Acceso de Aplicaciones Móviles
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de Perfil Acceso: agregar','ABM_PerfilAcceso','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
VALUES (0,3,60,'Mantenedor de  Perfil Acceso: Modificar','ABM_PerfilAcceso','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
VALUES (0,3,60,'Mantenedor de  Perfil Acceso: Ver','ABM_PerfilAcceso','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de  Perfil Acceso: Eliminar','ABM_PerfilAcceso','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de PerfilAcceso: Buscar','ABM_PerfilAcceso','buscar','admin',CURRENT,1);

INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de PerfilAccesoEnc: Agregar','ABM_PerfilAccesoEnc','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de PerfilAccesoEnc: Eliminar','ABM_PerfilAccesoEnc','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de PerfilAccesoEnc: Modificar','ABM_PerfilAccesoEnc','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de PerfilAccesoEnc: Ver','ABM_PerfilAccesoEnc','ver','admin',CURRENT,1);

INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de AreaAplicacionPerfil: Agregar','ABM_AreaAplicacionPerfil','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de AreaAplicacionPerfil: ver','ABM_AreaAplicacionPerfil','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de AreaAplicacionPerfil: Modificar','ABM_AreaAplicacionPerfil','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,60,'Mantenedor de AreaAplicacionPerfil: Eliminar','ABM_AreaAplicacionPerfil','eliminar','admin',CURRENT,1);

- Menus swe:
----------
- [Jira MRGAIT-99](http://jira.tecso.coop/browse/MRGAIT-99):[DEV] - Servidor - APM - Mantenedor de Perfiles de Acceso de Aplicaciones Móviles
--Menu: Aplicación
-------	   |-> Administración de Perfiles
--					|-> Administración de Perfiles de Acceso
--		url:'	/apm/BuscarPerfilAcceso.do?method=inicializar'
--		Modulo / Accion / Metodo: apm / ABM_PerfilAcceso / buscar



# version 1.0.20130711.BUILD
--Base gait:
-----------
-- [Jira MRGAIT-171](http://jira.tecso.coop/browse/MRGAIT-171):[SOP] - Soporte configuración entorno de producción
-- Elimino Detalles de Acta
delete from for_formulariodetalle where 1=1;
-- Elimino Actas
delete from for_formulario where 1=1;
-- Elimino Talonarios generados 
delete from for_talonario where 1=1;

-- Reinicio Numeracion 
update for_numeracion set valordesde = 1 where 1=1;
update for_numeracion set valorhasta = 999999 where 1=1;
update for_numeracion set valorrestante = 999999 where 1=1;
-- Actualiz. parametro URL de TMF de Testing a Produccion
update def_parametro set valor = "https://direccion-del-tribunal-municipal-de-faltas" where codparam like "uriRegistrarActaTMF";
-- Actualiz. nodo de ejecucion proceson sincronismo de actas
update pro_proceso set ejecnodo = "" where codproceso like 'ProcesoRegistrarActa'

# version 1.0.20130705.RC
--Base gait:
-----------
-- [Jira MRGAIT-130](http://jira.tecso.coop/browse/MRGAIT-130):[DEV] - Servidor - Proceso ADP para incorporar actas a TMF 
ALTER TABLE for_formulario ADD codigoRespuesta VARCHAR(15);

-- [Jira MRGAIT-150](http://jira.tecso.coop/browse/MRGAIT-150):[DEV] - Móvil.ADigital - Sincronismo de tabla TUP 
CREATE TABLE aid_lineaTUP ( 
	ID SERIAL NOT NULL,
	codigo VARCHAR(25) NOT NULL,
	descripcion VARCHAR(40) NOT NULL,
	usuario VARCHAR(30) NOT NULL,
	fechaUltMdf DATETIME YEAR TO SECOND NOT NULL,
	estado SMALLINT NOT NULL,
	version INTEGER NOT NULL
);

ALTER TABLE aid_lineaTUP ADD CONSTRAINT  
	PRIMARY KEY (ID);

INSERT INTO apm_tablaversion VALUES(49,'LineaTUP',1,'Tecso',CURRENT,1,1);
INSERT INTO apm_aplicaciontabla VALUES(0,2,49,'Tecso',CURRENT,1,1);

ALTER TABLE for_formulario ADD fechaImpresion DATETIME YEAR TO SECOND;

--Base swe:
----------

# version 1.0.20130628.RC

# version 1.0.20130619.RC

# version 1.0.20130404.RC

--Base swe:
----------

-- [Jira MRGAIT-122](http://jira.tecso.coop/browse/MRGAIT-122):[DEV] - Servidor - FOR - Mantenedor de Numeración de Tipo de Formulario 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (2376,3,61,'Mantenedor Numeración : agregar','ABM_Numeracion','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (2377,3,61,'Mantenedor Numeración : buscar','ABM_Numeracion','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (2378,3,61,'Mantenedor Numeración : eliminar','ABM_Numeracion','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (2379,3,61,'Mantenedor Numeración : modificar','ABM_Numeracion','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (2380,3,61,'Mantenedor Numeración : ver','ABM_Numeracion','ver','admin',CURRENT,1);


-- [Jira MRGAIT-120](http://jira.tecso.coop/browse/MRGAIT-120):[DEV] - Servidor - FOR - Mantenedor de Motivos de Anulación de Tipo de Formulario
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Anulación Tipo Formulario : Agregar','ABM_MotivoAnulacionTipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Anulación Tipo Formulario : Buscar','ABM_MotivoAnulacionTipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Anulación Tipo Formulario : Eliminar','ABM_MotivoAnulacionTipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Anulación Tipo Formulario : Modificar','ABM_MotivoAnulacionTipoFormulario','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Anulación Tipo Formulario : Ver','ABM_MotivoAnulacionTipoFormulario','ver','admin',CURRENT,1);
 

-- [Jira MRGAIT-119](http://jira.tecso.coop/browse/MRGAIT-119):[DEV] - Servidor - FOR - Mantenedor de Motivos de Cierre de Tipo de Formulario 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Cierre Tipo Formulario : Agregar','ABM_MotivoCierreTipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Cierre Tipo Formulario : Buscar','ABM_MotivoCierreTipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Cierre Tipo Formulario : Eliminar','ABM_MotivoCierreTipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Cierre Tipo Formulario : Modificar','ABM_MotivoCierreTipoFormulario','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Motivo Cierre Tipo Formulario : Ver','ABM_MotivoCierreTipoFormulario','ver','admin',CURRENT,1);


-- [Jira MRGAIT-121](http://jira.tecso.coop/browse/MRGAIT-121):[DEV] - Servidor - FOR - Mantenedor de Series de Numeración de Tipo de Formulario
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Serie : Agregar','ABM_Serie','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Serie : Buscar','ABM_Serie','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Serie : Eliminar','ABM_Serie','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado)
VALUES (0,3,61,'Mantenedor Serie : Modificar','ABM_Serie','modificar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Serie :Ver','ABM_Serie','ver','admin',CURRENT,1);


-- [Jira MRGAIT-118](http://jira.tecso.coop/browse/MRGAIT-118):[DEV] - Servidor - FOR - Mantenedor de Tipos de Formularios 
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Tipo Formulario : Agregar','ABM_TipoFormulario','agregar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Tipo Formulario : Buscar','ABM_TipoFormulario','buscar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Tipo Formulario : Eliminar','ABM_TipoFormulario','eliminar','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Tipo Formulario : Ver','ABM_TipoFormulario','ver','admin',CURRENT,1);
INSERT INTO swe_accmodapl (id,idaplicacion,idmodapl,descripcion,nombreaccion,nombremetodo,usuario,fechaultmdf,estado) 
VALUES (0,3,61,'Mantenedor Tipo Formulario : Modificar','ABM_TipoFormulario','modificar','admin',CURRENT,1);


- Menus swe:
----------
-- [Jira MRGAIT-118](http://jira.tecso.coop/browse/MRGAIT-118):[DEV] - Servidor - FOR - Mantenedor de Tipos de Formularios 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Tipos de Formularios
--		url:'	/frm/BuscarTipoFormulario.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_TipoFormulario / buscar

-- [Jira MRGAIT-121](http://jira.tecso.coop/browse/MRGAIT-121):[DEV] - Servidor - FOR - Mantenedor de Series de Numeración de Tipo de Formulario 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Series de Numeración de Tipo de Formulario 
--		url:'	/frm/BuscarSerie.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_Serie / buscar

---- [Jira MRGAIT-119](http://jira.tecso.coop/browse/MRGAIT-119):[DEV] - Servidor - FOR - Mantenedor de Motivos de Cierre de Tipo de Formulario
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Motivos de Cierre de Tipo de Formulario
--		url:'	/frm/BuscarMotivoCierreTipoFormulario.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_MotivoCierreTipoFormulario / buscar

-- [Jira MRGAIT-122](http://jira.tecso.coop/browse/MRGAIT-122):[DEV] - Servidor - FOR - Mantenedor de Numeración de Tipo de Formulario 
--Menu: Aplicación
-------	   |-> Administración de Formularios
--						|-> Mantenedor de Numeracion
--		url:'	/frm/BuscarNumeracion.do?method=inicializar'
--		Modulo / Accion / Metodo: frm / ABM_Numeracion / buscar  

