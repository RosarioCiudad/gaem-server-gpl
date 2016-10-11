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