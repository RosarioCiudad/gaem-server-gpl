	package ar.gov.rosario.gait.base.buss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.iface.model.GaitParam;

import com.sun.xml.wss.impl.callback.SignatureKeyCallback;

/**
 * 
 * Gait implementation of a CallbackHandler.
 * 
 * @author tecso.coop
 *
 */
public class SecurityHandler implements CallbackHandler {

	private Logger log = Logger.getLogger(SecurityHandler.class);

	private String keyStoreURL;
	private String keyStorePassword;
	private String keyStoreType;

	private String trustStoreURL;
	private String trustStorePassword;
	private String trustStoreType;

	private KeyStore keyStore;
	private KeyStore trustStore;

	private static final UnsupportedCallbackException unsupported =
			new UnsupportedCallbackException(null, "Unsupported Callback Type Encountered");


	public SecurityHandler() throws Exception {
		// Properties file
		Properties properties = new Properties();
		String clientPropsFile = GaitParam.getFileSharePath()
				+ File.separator + "conf" + File.separator
				+ "gait_security_env.properties";
		properties.load(new FileInputStream(clientPropsFile));

		this.keyStoreURL = properties.getProperty("keystore.url");
		this.keyStoreType = properties.getProperty("keystore.type");
		this.keyStorePassword = properties.getProperty("keystore.password");

		this.trustStoreURL = properties.getProperty("truststore.url");
		this.trustStoreType = properties.getProperty("truststore.type");
		this.trustStorePassword = properties.getProperty("truststore.password");

		initTrustStore();
		initKeyStore();
	}

	/**
	 * 
	 */
	public void handle(Callback[] callbacks) throws IOException,
	UnsupportedCallbackException {

		for (Callback callback : callbacks) {
			if (callback instanceof SignatureKeyCallback) {
				SignatureKeyCallback cb = (SignatureKeyCallback) callback;
				if (cb.getRequest() instanceof SignatureKeyCallback.DefaultPrivKeyCertRequest) {
					// Default private key certificate
					SignatureKeyCallback.DefaultPrivKeyCertRequest request = 
							(SignatureKeyCallback.DefaultPrivKeyCertRequest) cb.getRequest();
					getDefaultPrivKeyCert(request);
				} else if (cb.getRequest() instanceof SignatureKeyCallback.AliasPrivKeyCertRequest) {
					// Aliases private key certificate
					SignatureKeyCallback.AliasPrivKeyCertRequest request =
							(SignatureKeyCallback.AliasPrivKeyCertRequest) cb.getRequest();
					try {
						// Certificate from KeyStore
						X509Certificate certificate = getCertificate();
						request.setX509Certificate(certificate);
						// Private key from Certificate
						PrivateKey privateKey = getPrivateKey(certificate);
						request.setPrivateKey(privateKey);
					} catch (Exception e) {
						throw new IOException(e.getMessage());
					}
				} else {
					throw unsupported;
				}
			} else {
				// Unimplemented Callback
				log.info(String.format(
						"Unsupported Callback Type Encountered: %s", callback
						.getClass().getSimpleName()));
				throw unsupported;
			}
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void initTrustStore() throws IOException {
		try {
			if(trustStoreType.equals("PKCS12")){
				trustStore = KeyStore.getInstance(trustStoreType, "BC");
			}else{
				trustStore = KeyStore.getInstance(trustStoreType);
			}
			trustStore.load(new FileInputStream(trustStoreURL),
					trustStorePassword.toCharArray());
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void initKeyStore() throws IOException {
		try {
			if(keyStoreType.equals("PKCS12")){
				keyStore = KeyStore.getInstance(keyStoreType, "BC");
			}else{
				keyStore = KeyStore.getInstance(keyStoreType);
			}
			keyStore.load(new FileInputStream(keyStoreURL),
					keyStorePassword.toCharArray());
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private X509Certificate getCertificate() throws IOException {
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = (String) aliases.nextElement();
				if (!keyStore.isKeyEntry(alias))
					continue;
				Certificate cert = keyStore.getCertificate(alias);
				if (cert != null)
					return (X509Certificate) keyStore.getCertificate(alias);
			}
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param certificate
	 * @return
	 * @throws IOException
	 */
	private PrivateKey getPrivateKey(X509Certificate certificate)
			throws IOException {
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = (String) aliases.nextElement();
				if (!keyStore.isKeyEntry(alias))
					continue;
				Certificate cert = keyStore.getCertificate(alias);
				if (cert != null && cert.equals(certificate))
					return (PrivateKey) keyStore.getKey(alias,
							keyStorePassword.toCharArray());
			}
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	private void getDefaultPrivKeyCert(
			SignatureKeyCallback.DefaultPrivKeyCertRequest request)
					throws IOException {

		String uniqueAlias = null;
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String currentAlias = (String) aliases.nextElement();
				if (keyStore.isKeyEntry(currentAlias)) {
					Certificate thisCertificate = keyStore
							.getCertificate(currentAlias);
					if (thisCertificate != null) {
						if (thisCertificate instanceof X509Certificate) {
							if (uniqueAlias == null) {
								uniqueAlias = currentAlias;
							} else {
								// Not unique!
								uniqueAlias = null;
								break;
							}
						}
					}
				}
			}
			if (uniqueAlias != null) {
				request.setX509Certificate((X509Certificate) keyStore
						.getCertificate(uniqueAlias));
				request.setPrivateKey((PrivateKey) keyStore.getKey(uniqueAlias,
						keyStorePassword.toCharArray()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
}