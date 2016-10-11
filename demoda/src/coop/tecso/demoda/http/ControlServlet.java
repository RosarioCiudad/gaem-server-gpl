package coop.tecso.demoda.http;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import coop.tecso.demoda.sys.Jsoner;
import coop.tecso.demoda.sys.Work;
import coop.tecso.demoda.sys.WorkAttr;
import coop.tecso.demoda.util.To;
import coop.tecso.demoda.util.VoJsoner;

public class ControlServlet extends HttpServlet {
	Logger log = Logger.getLogger(ControlServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	private static Jsoner jsoner = new VoJsoner();
	
	@Override
	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		request(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		request(req, res);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		request(req, res);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		request(req, res);
	}

	protected void preInvoke(HttpServletRequest req, Class<?> klass, String methodname) {
	}

	protected void request(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		try {
			/* set response header */
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");

			// process request
			String uri = req.getRequestURI();
			String method = req.getMethod();
			
			
			System.out.println("URL: "+uri);
			Object[] routeParams = Route.get(method, uri);

			if (routeParams == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			// create work
			Map<String, Object> attrs = new HashMap<String, Object>();
			addRequestAttrs(req, attrs);
			Work.set(new Work(attrs));
				
			// find service, method and invoke
			Object reply;
			try {
				reply = invoke(routeParams, req);
			} catch (ReplyException e) {
				reply = e.reply();
			} catch (Exception e) {
				reply = new Reply<String>(500, "No se pudo concretar la operacion.", "");
				log.error("", e);
				e.printStackTrace();
			}
						
			String out = null;
			if (reply instanceof Reply)
				out = jsoner.toJson(reply);
			else
				out = new To().String(reply);
			
			if (out != null)
				res.getOutputStream().write(out.getBytes(res.getCharacterEncoding()));
			
			//res.getWriter().write(out);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	public void addRequestAttrs(HttpServletRequest req, Map<String, Object> attrs) {
		attrs.put(WorkAttr.RequestURI, req.getRequestURI());
		attrs.put(WorkAttr.RequestContentType, req.getContentType());
		attrs.put(WorkAttr.RequestRemoteInfo, req.getRemoteAddr());
		attrs.put(WorkAttr.RequestParameterMap, req.getParameterMap());
	}
	
	//params: Class<?>, 'method name', extraParams 
	public  Object invoke(Object params[], HttpServletRequest req) throws Exception {
		Class<?> klass = (Class<?>) params[0];
		String methodname = (String) params[1];
		@SuppressWarnings({ "rawtypes", "unchecked" })
		RestRequest<?> rreq = new RestRequest(req.getParameterMap(), null);

		Method method = klass.getMethod(methodname, RestRequest.class);
		if (!method.getReturnType().equals(Reply.class)
				&& !method.getReturnType().equals(String.class)) {
			throw new IllegalArgumentException(String.format("Method %s must return a Reply or String class", method));
		}

		preInvoke(req, klass, methodname);
		
		Object resource = klass.newInstance();
		Object o = method.invoke(resource, rreq);

		return o;
	}
}
