
-- apm_usuarioApm
INSERT INTO apm_usuarioapm (nombre,username,password,usertoken,usuario,fechaultmdf,estado,version) VALUES ('Tester Tecso','lfagana1','**','**','admin','2013-04-25 14:00:00',1,1);

-- apm_dispositivoMovil
INSERT INTO apm_dispositivomovil (descripcion,numeroserie,numerolinea,emailaddress,forzaractualizacion,usuario,fechaultmdf,estado,version) VALUES ('TECSO-RAZRi','*****','0341-153707282','leofagnano@gmail.com',0,'admin','2013-04-25 14:00:00',1,1);

-- not_tiponotificacion
INSERT INTO not_tiponotificacion (descripcion,usuario,fechaultmdf,estado,version) VALUES ('HTTPS','admin','2013-04-25 14:00:00',1,1);
INSERT INTO not_tiponotificacion (descripcion,usuario,fechaultmdf,estado,version) VALUES ('SMS','admin','2013-04-25 14:00:00',1,2);

-- not_estadoNotificacion
INSERT INTO not_estadonotificacion (descripcion,usuario,fechaultmdf,estado,version) VALUES ('Pendiente','admin','2013-04-25 14:00:00',1,1);
INSERT INTO not_estadonotificacion (descripcion,usuario,fechaultmdf,estado,version) VALUES ('Enviada','admin','2013-04-25 14:00:00',1,2);
INSERT INTO not_estadonotificacion (descripcion,usuario,fechaultmdf,estado,version) VALUES ('Pendiente con Error','admin','2013-04-25 14:00:00',1,3);


-- apm_tablaVersion - (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version)
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (1, 'PerfilSeccionCampo', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (2, 'PerfilSeccionCampoValor', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (3, 'PerfilSeccionCampoValorOpcion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (4, 'Aplicacion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (5, 'AplicacionBinarioVersion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (6, 'AplicacionParametro', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (7, 'AplicacionPerfil', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (8, 'AplicacionPerfilSeccion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (9, 'AplicacionTabla', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (10, 'AplicacionTipoBinario', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (11, 'Campo', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (12, 'CampoValor', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (13, 'CampoValorOpcion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (14, 'DispositivoMovil', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (15, 'PerfilAcceso', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (16, 'PerfilAccesoAplicacion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (17, 'PerfilAccesoUsuario', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (18, 'Seccion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (19, 'UsuarioApm', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (25, 'TipoNotificacion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (26, 'EstadoNotificacion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (27, 'Notificacion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (28, 'UsuarioApmDM', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (29, 'AplicacionPerfilSeccion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (30, 'AplPerfilSeccionCampo', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (31, 'AplPerfilSeccionCampoValor', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_tablaVersion (id, tabla, lastVersion, usuario, fechaUltMdf , estado, version) Values (32, 'AplPerfilSeccionCampoValorOpcion', 0, 'Tecso', '2013-04-25 14:00:00', 1, 0);

-- apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version)
INSERT INTO apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version) Values (1, 4, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version) Values (1, 10, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version) Values (1, 15, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version) Values (1, 16, 'Tecso', '2013-04-25 14:00:00', 1, 0);
INSERT INTO apm_aplicaciontabla (idaplicacion,idtablaversion, usuario, fechaUltMdf , estado, version) Values (1, 17, 'Tecso', '2013-04-25 14:00:00', 1, 0);

-- apm_aplicacion (codigo,descripcion,package,classname,usuario,fechaultmdf,estado,version)
INSERT INTO apm_aplicacion (codigo,descripcion,package,classname,usuario,fechaultmdf,estado,version) VALUES ('AIDigital','Acta de Infraccion Digital','coop.tecso.aid','coop.tecso.aid.activities.MainActivity','admin','2013-04-25 14:00:00',1,1);

-- def_direccion (id, descripcion,esdgi,usuario,fechaultmdf,estado,version)
INSERT INTO def_direccion (id,descripcion,esdgi,usuario,fechaultmdf,estado,version) VALUES (1, 'Dirección General de Informtica', 1 ,'admin','2013-04-25 14:00:00',1,1);
INSERT INTO def_direccion (id,descripcion,esdgi,usuario,fechaultmdf,estado,version) VALUES (2, 'Dirección de Tránsito', 0 ,'admin','2013-04-25 14:00:00',1,1);

-- def_area (id, iddireccion, descripcion,usuario,fechaultmdf,estado,version)
INSERT INTO def_area (id, iddireccion, descripcion,usuario,fechaultmdf,estado,version) VALUES (1, 2, 'Control Municipal', 'admin','2013-04-25 14:00:00',1,1);
INSERT INTO def_area (id, iddireccion, descripcion,usuario,fechaultmdf,estado,version) VALUES (2, 1, 'Informática', 'admin','2013-04-25 14:00:00',1,1);

-- apm_aplicacionperfil (id, idaplicacion, descripcion,usuario,fechaultmdf,estado,version)
INSERT INTO apm_aplicacionperfil (id, idaplicacion, descripcion,usuario,fechaultmdf,estado,version) VALUES (1, 2,'Control Municipal', 'admin','2013-04-25 14:00:00',1,1);
