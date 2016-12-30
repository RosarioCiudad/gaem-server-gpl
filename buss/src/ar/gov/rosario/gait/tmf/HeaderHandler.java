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
package ar.gov.rosario.gait.tmf;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


/**
 *
 * @author www.javadb.com
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessage soapMessage = smc.getMessage();

		if (outboundProperty) {
			try {
				SOAPPart soapPart = soapMessage.getSOAPPart();
				SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

				SOAPHeader soapHeader = soapEnvelope.addHeader();
				SOAPHeaderElement headerElement = soapHeader.addHeaderElement(soapEnvelope.createName(
						"Signature", "SOAP-SEC", "http://schemas.xmlsoap.org/soap/security/2000-12"));

				SOAPBody soapBody = soapEnvelope.getBody();
				soapBody.addAttribute(soapEnvelope.createName("id", "SOAP-SEC",
						"http://schemas.xmlsoap.org/soap/security/2000-12"), "Body");

				Source source = soapPart.getContent();
				Node root = null;
				if (source instanceof DOMSource) {
					root = ((DOMSource) source).getNode();
				} else if (source instanceof SAXSource) {
					InputSource inSource = ((SAXSource) source).getInputSource();
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					dbf.setNamespaceAware(true);
					DocumentBuilder db = null;

					db = dbf.newDocumentBuilder();

					Document doc = db.parse(inSource);
					root = (Node) doc.getDocumentElement();
				}

				dumpDocument(root);

				KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
				keyStore.load(new FileInputStream("path/to/cert"), "".toCharArray());
				

				String alias = keyStore.aliases().nextElement();
				PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, "".toCharArray());
				X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
				PublicKey publicKey = certificate.getPublicKey();

				XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance();
				Reference ref = sigFactory.newReference("#Body", sigFactory.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1",
						null));
				SignedInfo signedInfo = sigFactory.newSignedInfo(sigFactory.newCanonicalizationMethod(
						"http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", (C14NMethodParameterSpec) null), sigFactory
						.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#dsa-sha1", null), Collections.singletonList(ref));
//				KeyInfoFactory kif = sigFactory.getKeyInfoFactory();
//				KeyValue kv = kif.newKeyValue(publicKey);
//				KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(kv));
				//--
		        KeyInfoFactory keyInfoFactory = sigFactory.getKeyInfoFactory();
		        List<Serializable> x509Content = new ArrayList<Serializable>();
		        x509Content.add(certificate.getSubjectX500Principal().getName());
		        x509Content.add(certificate);
		        X509Data data = keyInfoFactory.newX509Data(x509Content);
		        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(data));
				//--

				System.out.println("Signing the message...");
				Element envelope = getFirstChildElement(root);
				Element header = getFirstChildElement(envelope);
				DOMSignContext sigContext = new DOMSignContext(privateKey, header);
				sigContext.putNamespacePrefix(XMLSignature.XMLNS, "ds");
				sigContext.setIdAttributeNS(getNextSiblingElement(header),
						"http://schemas.xmlsoap.org/soap/security/2000-12", "id");
				
				XMLSignature sig = sigFactory.newXMLSignature(signedInfo, keyInfo);
				sig.sign(sigContext);

				dumpDocument(root);

				System.out.println("Validate the signature...");
				Element sigElement = getFirstChildElement(header);
				DOMValidateContext valContext = new DOMValidateContext(publicKey, sigElement);
				valContext.setIdAttributeNS(getNextSiblingElement(header),
						"http://schemas.xmlsoap.org/soap/security/2000-12", "id");
				boolean valid = sig.validate(valContext);

				System.out.println("Signature valid? " + valid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private static void dumpDocument(Node root) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(root), new StreamResult(System.out));
	}

	private static Element getFirstChildElement(Node node) {
		Node child = node.getFirstChild();
		while ((child != null) && (child.getNodeType() != Node.ELEMENT_NODE)) {
			child = child.getNextSibling();
		}
		return (Element) child;
	}

	public static Element getNextSiblingElement(Node node) {
		Node sibling = node.getNextSibling();
		while ((sibling != null) && (sibling.getNodeType() != Node.ELEMENT_NODE)) {
			sibling = sibling.getNextSibling();
		}
		return (Element) sibling;
	}

	public Set getHeaders() {
		//throw new UnsupportedOperationException("Not supported yet.");
		return null;
	}

	public boolean handleFault(SOAPMessageContext context) {
		//throw new UnsupportedOperationException("Not supported yet.");
		return true;
	}

	public void close(MessageContext context) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}
}