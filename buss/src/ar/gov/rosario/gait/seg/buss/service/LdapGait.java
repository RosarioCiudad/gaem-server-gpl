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
package ar.gov.rosario.gait.seg.buss.service;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapGait {
	static public final String LdapServerResource = "java:comp/env/config/ldap/server";
	static public final String LdapUserBase = "URL-INFO-BASE";
	
	/**
	 * Valida si username y password son correctos para esta aplicación cargada en en el ldap.
	 * El servidor al que se conecta queda definido en el Resource "java:comp/env/config/ldap/server"
	 * El el bind se hace con: uid={username},{LdapUserBase},
	 * 
	 * El mecanismo de autenticación, crea un contexto en LdapUserBase, haciendo el bind contra
	 * uid={username},{LdapUserBase}. Luego se piden los Atributos del Nombre uid={username}, 
	 * dentro de la base. 
	 * 
	 * @param username uid del grupo
	 * @param password credenciales del uid.
	 * @param data (puede ser null). Map donde colocara los atributos del DN "LdapUserBase,uid={username}"
	 * @return true si se pudo verificar el password. false si fallo la validación.
	 * @throws Exception
	 */
	public boolean login(String username, String password, Map<String, Object> data) throws Exception {
		String ldap = (String) new InitialContext().lookup(LdapServerResource);
		return new LdapGait().auth(ldap, LdapUserBase, username, password, data);
	}

	/**
	 * @see LdapGrsMr.login()
	 */
	private boolean auth(String url, String base, String uid, String password, Map<String, Object> data) throws Exception {
		DirContext ctx;

		try {
			Hashtable<String, Object> env = new Hashtable<String, Object>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, url + "/" + base);
			env.put(Context.SECURITY_PRINCIPAL, "uid=" + uid + ","+ base);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put("java.naming.ldap.attributes.binary", "userPKCS12");

			// env.put("com.sun.jndi.ldap.trace.ber", System.out);
			// anonymous
			ctx = new InitialDirContext(env);

			Attributes attrs;
			attrs = ctx.getAttributes("uid=" + uid);

			if (data != null) {
				NamingEnumeration<? extends Attribute> e = attrs.getAll();
				while (e.hasMoreElements()) {
					Attribute attr = (Attribute) e.nextElement();
					data.put(attr.getID(), attr.get());
				}
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return false;			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		if (ctx != null) 
			ctx.close();
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String host = args[0];
		String base = args[1];
		String uid = args[2];
		String password = args[3];

		LdapGait ldapmr = new LdapGait();
		Hashtable<String, Object> attrs = new Hashtable<String, Object>();
		
		boolean auth = ldapmr.auth(host, base, uid, password, attrs);
		if (!auth) {
			System.out.printf("Invalid login.\n");
			return;
		}
		for(Map.Entry<String, Object> e : attrs.entrySet()) {
			System.out.printf("Attr: %s, value: %s\n", e.getKey(), e.getValue());
		}
	}
}