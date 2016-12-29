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
package ar.gov.rosario.gait.frm.buss.service;

public class HtmlReportHelper {

	private StringBuilder builder;
	
	public HtmlReportHelper() {
		builder = new StringBuilder();
	}
	
	public void addRawHTML(String html){
		builder.append(html);
	}
	
	public void startTable(){
		startTable("style='repeat-header: yes; width:100/%; border=1;'");
	}
	
	public void startTable(String extra){
		builder.append(String.format("<table %s >", extra));
	}
	
	public void startTable(String[] columns){
		startTable();
		addTableHeader(columns);
	}
	public void endTable(){
		builder.append("</table>");
		builder.append("<br></br>");
	}
	
	public void addTableHeader(String[] columns){
		addTableHeader("", columns);
	}
	
	public void addTableHeader(String extra, String[] columns){
		builder.append("<thead><tr>");
		for (String column : columns) {
			builder.append(String.format("<th %s >%s</th>", extra, column));
		}
		builder.append("</tr></thead>");
	}
	
	public void addTableRow(String[] values){
		addTableRow("", values);
	}
	
	public void addTableRow(String extra, String[] columns){
		builder.append("<tr>");
		for (String column : columns) {
			builder.append(String.format("<td %s >%s</td>", extra, column));
		}
		builder.append("</tr>");
	}
	
	public void startNewPage(){
		builder.append("<div style='page-break-before: always;'></div>");
	}
	
	public void addTableFooter(String extra, String[] columns){
		builder.append("<tfoot><tr>");
		for (String column : columns) {
			builder.append(String.format("<tr %s >%s</tr>", extra, column));
		}
		builder.append("</tr></tfoot>");
	}
	
	public String out(){
		return builder.toString();
	}
}