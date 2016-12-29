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
package ar.gov.rosario.gait.sidom.data;


import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
public class DocumentoResultHandler extends DefaultHandler {

	private Stack<String> elementStack = new Stack<>();
	private DocumentoResult documentoResult;
	private Documento documento;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		//Push it in element stack
		this.elementStack.push(qName);
		//
		if (qName.equalsIgnoreCase("DocumentoBuscador")) {
			documentoResult = new DocumentoResult();
			documentoResult.setListDocumento(new ArrayList<Documento>());
		} 
		if (qName.equalsIgnoreCase("PiezaBuscador")) {
			documento = new Documento();
		} 
	}

	@Override
	public void endElement(String uri, String localName, 
			String qName) throws SAXException {
		//
		if (qName.equalsIgnoreCase("PiezaBuscador")) {
			documentoResult.getListDocumento().add(documento);
		} 
	}

	@Override
	public void characters(char ch[], int start, 
			int length) throws SAXException {
		String data = new String(ch, start, length);
		String element = currentElement();
		
		if(StringUtil.isNullOrEmpty(data)) return;
		//
		if(element.equalsIgnoreCase("Pid")){
			documento.setPid(data);
		} 
		if(element.equalsIgnoreCase("Url")){
			documento.setUrl(data);
		} 
		
	}

	public DocumentoResult getDocumentoResult() {
		return documentoResult;
	}

	/**
	 * Utility method for getting the current element in processing
	 * */
	private String currentElement() {
		return this.elementStack.peek();
	}
}