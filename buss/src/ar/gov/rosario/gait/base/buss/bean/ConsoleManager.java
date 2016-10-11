package ar.gov.rosario.gait.base.buss.bean;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import coop.tecso.demoda.buss.dao.JDBCConnManager;


/**
 * Manejador de la consola gait
 * 
 * @author tecso
 *
 */
public class ConsoleManager {
	
	private static Logger log = Logger.getLogger(BaseManager.class);
	
	public static final ConsoleManager INSTANCE = new ConsoleManager();
	
	/**
	 * Constructor privado
	 */
	private ConsoleManager() {
		
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static ConsoleManager getInstance() {
		return INSTANCE;
	}
	
	
	public boolean infoActas(PrintWriter cons){
		boolean stat = true;
		List<Long> listError = FrmDAOFactory.getFormularioDAO().getListIdProcesadoError();
		List<Long> listOk = FrmDAOFactory.getFormularioDAO().getListIdProcesado();
		Formulario formularioOk = FrmDAOFactory.getFormularioDAO().getUltimoProcesadoOk();
		Formulario formularioError = FrmDAOFactory.getFormularioDAO().getUltimoProcesadoOk();
		List<Object[]> listErrores = FrmDAOFactory.getFormularioDAO().getListErrorCantidad();
		
		cons.println("Informacion General de las Actas: ");
		if(formularioOk != null){
			cons.println("");
			cons.println("Ultima acta procesada Ok:   "+"Numero: "+formularioOk.getNumero()+" | Fecha Inicio: "+formularioOk.getFechaInicio()+" | Fecha Cierre: "+
					formularioOk.getFechaCierre()+" | Usuario: "+formularioOk.getNumeroInspector());
		}
		if(formularioError != null){
				cons.println("Ultima acta procesada Error:   "+"Numero: "+formularioError.getNumero()+" | Fecha Inicio: "+formularioError.getFechaInicio()+" | Fecha Cierre: "+
						formularioError.getFechaCierre()+" | Usuario: "+formularioOk.getNumeroInspector());
				cons.println("");
		}
		cons.println("");
		cons.println("Total Actas OK: "+listOk.size());
		cons.println("Total Actas Error: "+listError.size());
		
		if(listErrores != null && !listErrores.isEmpty()){
			String formato = "| %-125s | %-9d |\n";
			cons.println("");
			cons.println("Resumen de los errores: ");
			cons.format("+-----------------------------------------------------------------------------------------------------------------------------+---------+%n");
			for(Object[] par : listErrores){
				if(((String)par[0]).endsWith("\n"))
					cons.format(formato, ((String)par[0]).substring(0,((String)par[0]).length()-1), ((BigDecimal)par[1]).intValue());
				else
					cons.format(formato, par[0], ((BigDecimal)par[1]).intValue());
				cons.println("");
			}
			cons.format("+-----------------------------------------------------------------------------------------------------------------------------+---------+%n");
		}
		cons.println("");
		cons.println("");
		
		return stat;
	}

	public boolean status(PrintWriter cons) {
		boolean stat = true;
		
		cons.println("Gait status: " + new Date()); cons.println();
		cons.println("version: " + GaitParam.version() +
				(GaitParam.isWebGait()?" (webGait)":"") + 
				(GaitParam.isIntranetGait()?" (intraGait)":"")); 
		cons.println();		

		if (stat) { cons.println(); stat = statusMemory(cons); }
		if (stat) { cons.println(); statusDBCon(cons); }
		if (stat) {	cons.println(); stat = statusFileShare(cons); }
		
		return stat;
	}

	public boolean statusFileShare(PrintWriter cons) {
		cons.println("File Share Status");
		cons.println("not implemented!");
		return true;
	}

	public boolean statusMemory(PrintWriter cons) {
		cons.println("Memory Status");
		cons.println("Used Memory: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) /1024/1024) + "M");
		return true;
	}

	public boolean statusDBCon(PrintWriter cons) {
		int result = 1;
		cons.println("Database Connections");

		result *= statusDatasource(cons, "java:comp/env/ds/gait");
		result *= statusDatasource(cons, "java:comp/env/ds/swe");
		
		return result == 1;
	}

	private int statusDatasource(PrintWriter cons, String ds) {
		Connection cn = null;
		try {
			cons.print("Conectando...: " + ds);
			System.out.println("Conectando...: " + ds);
			cn = JDBCConnManager.getConnection(ds);
			System.out.println("Conectando...: " + ds + "OK");
			try { cn.close(); } catch (Exception ex) {}
			cons.println(" ok" );
		} catch (Exception e) {
			try { cn.close(); } catch (Exception ex) {}
			cons.println(" !ERROR!: " + ds + " " + e);
			e.printStackTrace(cons);
			cons.println();
			return 0;
		}
		return 1;
	} 	
}
