
package coop.tecso.demoda.iface.model;





/**
 *  Representa una columna de una tabla de un reporte.
 *   
 * @author Tecso
 *
 */
public class ReportColumnVO {
	
    // nombre que lo identifica
	private String nombreColumna = "";  
	
	private String[] metodosEjecutar = null;// new String[] {""};

	private ReportPieVO reportPie = new ReportPieVO();
	
	private Integer width = new Integer(0);
	
	private Class claseEnumeracion = null ;
	
	public ReportColumnVO(){
	}
	
	public ReportColumnVO(String nombreColumna, String[] metodosEjecutar) {
		this.nombreColumna = nombreColumna;
		this.metodosEjecutar = metodosEjecutar;
	}
	
	public ReportColumnVO(String nombreColumna, String metodoEjecutar) {
		this.nombreColumna = nombreColumna;
		this.metodosEjecutar = new String[] {metodoEjecutar};
	}
	
	public ReportColumnVO(String nombreColumna, String metodoEjecutar, Integer width) {
		this.nombreColumna = nombreColumna;
		this.metodosEjecutar = new String[] {metodoEjecutar};
		this.width = width;
	}

	
	public void addMultiCelda(String metodoEjecutar) {
		String[] v = this.getMetodosEjecutar();
		if (v == null){
			this.metodosEjecutar = new String[] {metodoEjecutar};
		}else{
			String[] nueva = new String[v.length + 1];
			for (int i = 0; i < v.length; i++) {
				nueva[i] = v[i];
			}
			nueva[v.length] = metodoEjecutar;
		}
	}

	// Getters Y Setters
	public String[] getMetodosEjecutar() {
		return metodosEjecutar;
	}
	public void setMetodosEjecutar(String[] metodosEjecutar) {
		this.metodosEjecutar = metodosEjecutar;
	}
	public String getNombreColumna() {
		return nombreColumna;
	}
	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}
	public ReportPieVO getReportPie() {
		return reportPie;
	}
	public void setReportPie(ReportPieVO reportPie) {
		this.reportPie = reportPie;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Class getClaseEnumeracion() {
		return claseEnumeracion;
	}
	public void setClaseEnumeracion(Class claseEnumeracion) {
		this.claseEnumeracion = claseEnumeracion;
	}
	
	
	
}
