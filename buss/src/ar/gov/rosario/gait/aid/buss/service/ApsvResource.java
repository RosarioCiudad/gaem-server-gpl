/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.aid.buss.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ar.gov.rosario.gait.apsv.ApsvfWebServiceManager;
import ar.gov.rosario.gait.apsv.Licencia;
import ar.gov.rosario.gait.apsv.ObtenerLicenciaResponse;
import ar.gov.rosario.gait.apsv.ObtenerPersonaResponse;
import ar.gov.rosario.gait.apsv.Persona;
import ar.gov.rosario.gait.apsv.Solicitud;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

/**
 * 
 */
public class ApsvResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "gaem/api/apsv/estadoLicencia.json", this.getClass(), "estado")
		};
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Reply<String> estado(RestRequest<String> req) throws Exception {
		try {
			Integer tipoDocumento = to.Integer(req.parameters.get("tipoDocumento"));
			Integer numeroDocumento = to.Integer(req.parameters.get("numeroDocumento"));
			String sexo = to.String(req.parameters.get("sexo"));
			String claseLicencia = to.String(req.parameters.get("claseLicencia"));

			ApsvfWebServiceManager apsv =  ApsvfWebServiceManager.getInstance();

			System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true"); 
			System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

			//ObtenerPersonaResponse obtenerPersonaResponse = apsv.obtenerPersona(tipoDocumento, numeroDocumento, sexo);
			ObtenerPersonaResponse obtenerPersonaResponse = apsv.obtenerPersona(tipoDocumento, numeroDocumento);

			List<Persona> listPersona = obtenerPersonaResponse.getPersonas();
			//
			if(listPersona.isEmpty()){
				return new Reply<String>("No existen datos para los valores ingresados");
			}
			//Filtro "manual" para el sexo, que puede venir sin datos. 
			List<Persona> listPersonaFiltro = new ArrayList<Persona>();
			
			for(Persona persona: listPersona){
				// TODO: averiguar si el cód de doc puede venir sin datos.
				// if(persona.getCodDoc()!=0){} 
				if ((sexo == null) || sexo.isEmpty() || (persona.getSexo() == null) || persona.getSexo().isEmpty()){
					listPersonaFiltro.add(persona);
				}else{
					if(persona.getSexo().equals(sexo)){
						listPersonaFiltro.add(persona);
					}
				}
			}
			
			if(listPersonaFiltro.isEmpty()){
				return new Reply<String>("No existen datos para los valores ingresados");
			}
			
			StringBuilder builder = new StringBuilder();
			for (Persona persona : listPersonaFiltro) {
				Integer idPersona = persona.getIdPersona().getIdPersona();
				
				ObtenerLicenciaResponse obtenerLicenciaResponse = apsv.obtenerLicencia(idPersona, claseLicencia);
				
				List<Licencia> listLicencias = obtenerLicenciaResponse.getLicencias();
				//
				if(listLicencias.isEmpty()){
					return new Reply<String>("No existen datos para los valores ingresados");
				}
				for (Licencia licencia : listLicencias) {
					
					XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
					GregorianCalendar now = new GregorianCalendar();
					xgc.setYear(now.get(Calendar.YEAR));
					xgc.setMonth(now.get(Calendar.MONTH) + 1);
					xgc.setDay(now.get(Calendar.DAY_OF_MONTH));
					
					if (!(licencia.getFechaVencimiento().compare(xgc)== DatatypeConstants.LESSER)){
						XMLGregorianCalendar fechaSolicitud = null;
						String comuna = "";
						for (Solicitud solicitud: licencia.getSolicitudes()) {
							//log.debug("fechaSol: "+solicitud.getFecha() + "|| comuna: "+ solicitud.getCentroEmision());
							if (fechaSolicitud==null){fechaSolicitud = solicitud.getFecha(); comuna = solicitud.getCentroEmision();}
							if (solicitud.getFecha().compare(fechaSolicitud)==DatatypeConstants.GREATER){
								fechaSolicitud = solicitud.getFecha(); 
								comuna = solicitud.getCentroEmision();
							}
						}
						//0. Localidad (Comuna otorgante) 
						if (!comuna.equals("")){
							builder.append("Comuna: " + comuna);
							builder.append("\n");
						}
						//1. Licencia Nro y Clase
						builder.append("Lic. Nº: " +licencia.getNumeroLicencia() + " Clase: "+licencia.getClaseLicencia());
						builder.append("\n");
						//2. Nombres 
						builder.append("Nombres: " + persona.getNombres());
						builder.append("\n");
						//3. Apellidos 
						builder.append("Apellidos: " + persona.getApellido() +" "+ persona.getApellidoMaterno());
						builder.append("\n");
						if (persona.getDireccion() != null){
							//4. Direccion (todo junto)
							builder.append("Domicilio: " + persona.getDireccion().getNombreCalle() + " " + persona.getDireccion().getNumero() + " " + persona.getDireccion().getPiso() + " " + persona.getDireccion().getDepartamento());
							builder.append("\n");
							//5. Localidad (Persona)
							builder.append("Localidad: " + persona.getDireccion().getNombreLocalidad());
							builder.append("\n");
						}
						//6. Fecha Vencimiento
						if (licencia.getFechaVencimiento() != null){
							builder.append("Vto.: "+licencia.getFechaVencimiento().getDay()+"/"+licencia.getFechaVencimiento().getMonth()+"/"+licencia.getFechaVencimiento().getYear());
							builder.append("\n");	
						}
						//7. Fecha Próxima Reválida
						if (licencia.getFechaProximaRevalida() != null){
							builder.append("Próx. Reválida: "+licencia.getFechaProximaRevalida().getDay()+"/"+licencia.getFechaProximaRevalida().getMonth()+"/"+licencia.getFechaProximaRevalida().getYear());
							builder.append("\n");
						}
						//8. Observaciones
						builder.append("Obs.: "+licencia.getObservaciones());
						builder.append("\n");
						//9. Restricciones
						builder.append("Restricciones: "+licencia.getRestricciones());
						builder.append("\n");
						//10. Inhabilitada
						String inhabilitada = "";
						if(licencia.isInhabilitada()) 
							inhabilitada = "INHABILITADA";
						else
							inhabilitada = "HABILITADA";
						builder.append("Inhabilitada: "+ inhabilitada);
						builder.append("\n");
						builder.append("\n");

					}else{
						//builder.append("Licencia Vencida el: "+licencia.getFechaVencimiento().getDay()+"/"+licencia.getFechaVencimiento().getMonth()+"/"+licencia.getFechaVencimiento().getYear());
						//builder.append("\n");
					}
					
					
				}
/*
				ObtenerNoAptitudResponse obtenerNoAptitudResponse = apsv.obtenerNoAptitud(idPersona);
				for (NoAptitud noAptitud : obtenerNoAptitudResponse.getNoAptitudes()) {
					builder.append("No apto: ");
					builder.append(noAptitud.getObservaciones());
					builder.append("\n");
				}

				ObtenerInhabilitacionResponse obtenerInhabilitacionResponse =
						apsv.obtenerInhabilitacion(idPersona, claseLicencia);
				if(!StringUtil.isNullOrEmpty(obtenerInhabilitacionResponse.getInhabilitado())){
					builder.append("Inhabilitado: ");
					builder.append(obtenerInhabilitacionResponse.getInhabilitado());
					builder.append("\n");
				}
*/
			}
			if (builder.toString().isEmpty())
				return new Reply<String>("No existen datos para los valores ingresados");

			return new Reply<String>(builder.toString());
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}