package ar.gov.rosario.gait.frm.buss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import coop.tecso.demoda.iface.helper.NumberUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Tratamiento;

public class Formatter {


	private Formulario formulario;
	
	public Formatter(Formulario formulario) {
		this.formulario = formulario;
	}
	
	
	public String getString(String codigo){
		
		List<FormularioDetalle> listFormularioDetalle = 
				formulario.getListFormularioDetalleByCodigo(codigo);
		
		if(listFormularioDetalle.isEmpty()) return "-";
		
		StringBuilder result = new StringBuilder();
		
		for (FormularioDetalle formularioDetalle : listFormularioDetalle) {
			Object value;
			try {
				value = formatDetalle(formularioDetalle);
			} catch (Exception e) {
				e.printStackTrace();
				value = "--";
			}
			
			if(value instanceof Map){
				Map<String,Object> mValue = (Map<String, Object>) value;
				result.append(mValue.get("descripcion"));
			}else{
				result.append(value);
			}
			if(listFormularioDetalle.size() > 1) result.append("\n");
		}
		return result.toString();
	}
	
	/**
	 * 
	 * @param campoGUI
	 * @return
	 */
//	public Object getFormatedValue(FormularioDetalle detalle){
//		return this.getFormatedValue(campoID, 0);
//	}

	/**
	 * 
	 * @param campoGUI
	 * @return
	 */
	public static Object formatDetalle(FormularioDetalle formularioDetalle){
		
		Tratamiento tratamiento;
		
		AplPerfilSeccionCampoValor campoValorDef = formularioDetalle.getTipoFormularioDefSeccionCampoValor();
		if(campoValorDef == null){
			tratamiento = Tratamiento.getById(formularioDetalle.getTipoFormularioDefSeccionCampo().getCampo().getTratamiento());
		}else{
			tratamiento = Tratamiento.getById(campoValorDef.getCampoValor().getTratamiento());
		}
		
		// Lugar Infraccion
		if(Tratamiento.DOMI.equals(tratamiento)){
			// codigoCalle|altura|bis|letra|codigoInterseccion|calle|interseccion|sentido
			Map<String,Object> result = new HashMap<>();
			String[] values = formularioDetalle.getValor().split("\\|");
			// Calle
			Integer calle = NumberUtil.getInteger(values[0]);
			if(calle != null && calle > 0){
				result.put("li_codCalle", calle);
			}
			result.put("li_altura", values[1]);
			result.put("li_bis", values[2]);
			result.put("li_letra", values[3]);
			// Interseccion
			Integer interseccion = NumberUtil.getInteger(values[4]);
			if(interseccion != null && interseccion > 0){
				result.put("li_codigoInterseccion", values[4]);
			}
			result.put("li_calle", values[5]);
			result.put("li_interseccion", values[6]);
			// Sentido
			Integer sentido = NumberUtil.getInteger(values[7]);
			if(sentido != null && sentido > 0){
				result.put("li_sentido", values[7]);
			}
			/* Formato LugarInfraccion.Descripcion 
			Formar una cadena con una de estas opciones según sea una dirección o una intersección: 
				1) Nombre abreviado + numero + bis + letra 
				2) Nombre abreviado + “ Y “ + Nombre abreviado 
			Máxima longitud 33 caracteres. */ 
			StringBuilder descripcion = new StringBuilder();
			if(result.get("li_codigoInterseccion") == null ){
				// Caso 1)
				descripcion.append(result.get("li_calle"));
				descripcion.append(" ");
				descripcion.append(result.get("li_altura"));
				if(new Boolean(result.get("li_bis").toString())) descripcion.append(" bis ");
				if(!StringUtil.isNullOrEmpty(result.get("li_letra").toString())){
					descripcion.append(" ");
					descripcion.append(result.get("li_letra"));
				}
			}else{
				// Caso 2)
				descripcion.append(result.get("li_calle"));
				descripcion.append(" Y ");
				descripcion.append(result.get("li_interseccion"));
			}
			result.put("descripcion", descripcion.toString());
			
			return result;
		}
		
		// Dominio
		if(Tratamiento.DOM.equals(tratamiento)){
			if(formularioDetalle.getValor().contains("|")){ 
				String[] values = formularioDetalle.getValor().split("\\|");
				if(Boolean.valueOf(values[2])){
					return "Sin Identificar";
				}else{
//				values[1]; copia?
					return values[0];
				}
			}
		}
		
		// 
		if(Tratamiento.OP2.equals(tratamiento) 
				|| Tratamiento.OP3.equals(tratamiento)){
			if(formularioDetalle.getValor().contains("|")){
				String[] values = formularioDetalle.getValor().split("\\|");
				return values[1];
			}
			return "";
		}
		
		if(Tratamiento.SBX.equals(tratamiento)){
			return formularioDetalle.getValor().replace("|", " : ");
		}

		return formularioDetalle.getValor().trim();
	}


//	/**
//	 * Retorna un Object formateado dependiendo del Tratamiento.
//	 * Todos l
//	 * @param tratamiento
//	 * @param value
//	 * @return
//	 */
//	private Object formatValue(Tratamiento tratamiento, Value value){
//
//		// Numerico Entero
//		if(tratamiento.equals(Tratamiento.TNE)){
//			return Integer.parseInt(value.getValor());
//		}
//		// Numerico Decimal
//		if(tratamiento.equals(Tratamiento.TND)){
//			return Double.parseDouble(value.getValor());
//		}
//		// Fecha
//		if(tratamiento.equals(Tratamiento.TF)){
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
//			try { return sdf.parse(value.getValor());
//			} catch (ParseException e) {return null;} 
//		}
//		// Opciones simple seleccion (Combo)
//		if(tratamiento.equals(Tratamiento.OP)){
//			if(Utils.isNotNull(value.getCampoValorOpcion())){
//				return value.getCampoValorOpcion().getId();
//			}else{
//				return value.getCampoValor().getId();
//			}
//		} 
//		// Opciones simple seleccion (Combo)
//		if(tratamiento.equals(Tratamiento.SOC)){
//			if(Utils.isNotNull(value.getCampoValorOpcion())){
//				return value.getCampoValorOpcion().getId();
//			}else{
//				return value.getCampoValor().getId();
//			}
//		} 
//		// Busqueda en Tabla (EntidadBusqueda) (Opcion Simple)
//		if(tratamiento.equals(Tratamiento.BU)){
//			return value.getCodigoEntidadBusqueda();
//		}
//		
//		if(tratamiento.equals(Tratamiento.NA)){
//			if(Utils.isNotNull(value.getCampoValorOpcion())){
//				return value.getCampoValorOpcion().getCampoValorOpcion().getEtiqueta();
//			}else{
//				return value.getCampoValor().getCampoValor().getEtiqueta();
//			}
//		}
//				
//		return value.getValor();
//	}
}
