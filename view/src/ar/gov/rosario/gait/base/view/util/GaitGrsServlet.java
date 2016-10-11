package ar.gov.rosario.gait.base.view.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.grs.Grs;
import coop.tecso.grs.GrsServlet;
import coop.tecso.grs.page.GrsPageContext;
import coop.tecso.grs.page.Page;

public class GaitGrsServlet extends GrsServlet {

	private static final long serialVersionUID = 1L;
	private static final int BUFSIZE = 4096;
	private static String GrsContextPath = "/grs";
	
	@Override
	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
		GrsContextPath = cfg.getServletContext().getContextPath();
	}

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		request(arg0, arg1);
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		request(arg0, arg1);
	}

	@Override
	protected void doPut(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		request(arg0, arg1);
	}

	@Override
	protected void doDelete(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		request(arg0, arg1);
	}

	protected void request(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			UserContext uc = (UserContext) req.getSession().getAttribute("userSession");
			if (uc == null)
				return;

			// user information 
			uc.setIpRequest(req.getRemoteAddr());
			DemodaUtil.setCurrentUserContext(uc);

			GrsPageContext ctx = mkGrsPageContext(req, uc.getUserName(), "grs");
			String processdir = GaitParam.getFileSharePath() + File.separator + "reportes" + File.separator + ctx.process;  
			if ("salida".equals(ctx.function)) {
				responseFile(req, res, urlToPath(processdir, ctx.uri));
				
				return;
			} else if ("download".equals(ctx.function)) {
				String filepath = urlToPath(processdir, ctx.uri).replaceFirst("/download/", "/salida/");
				responseDownload(req, res, filepath);
				return;
			} else {
				responseGrs(ctx, req, res); // 'reporte' mismo dir que el ultimo dir del servlet pattern
				return;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	// convierte el path de una archivo en formato : {basepath}/func[/id]/file
	// en: {baseurl}/id/func/file
	// basepath es pa base de path: tipicamente es el directorio del reporte.
	// baseurl es la base del reporte no debe incluir el id.  pej: /siat/grs/ReporteDemo
	public static String pathToUrl(String basepath, String path, String baseurl) {
		String relpath = path.substring(basepath.length() + 1);
		String[] parts = relpath.split("/"); // {function, id|file, file|out of bound}
		
		String func, id, file;
		func = parts[0];
		if (parts.length > 2) { // el path tiene id (es un path nuevo)
			id = parts[1];
			file = parts[2];
		} else { // el path no tiene id, es un path viejo
			id = "-";
			file = parts[1];			
		}
		
		return baseurl + "/" + id + "/" + func + "/" + file;			
			
	}
	
	// converte una url de un archivo, viejo o nuevo a un filepath.
	//
	// caso url de reporte viejo:
	// convierte: /siat/grs/proceso/-/function/file
	// en: {processdir}/salida/file
	
	// caso url de reporte nuevo:
	// convierte: /siat/grs/proceso/id/function/file
	// en: {processdir}/salida/id/file
	public static String urlToPath(String processdir, String url) {
		String[] parts = url.split("/");
		String id = parts[4];
		
		if ("-".equals(id)) { // es url vieja
			String file = parts[6];
			return new File(processdir, "salida" + File.separator + file).getAbsolutePath();

		} else { // es url nueva
			StringBuilder sb = new StringBuilder();
			sb.append(File.separator).append("salida");
			sb.append(File.separator).append(id);
			for(int i = 6; i < parts.length; i++) {
				sb.append(File.separator).append(parts[i]);
			}
			return new File(processdir, sb.toString()).getAbsolutePath();
		}
	}

	
	
	// matchea algo como esto: /*/basedir/process/{id}/{function}
	// process default es "" 
	// idrun default es 0 
	// action default es "get" 
	private GrsPageContext mkGrsPageContext(HttpServletRequest req, String username, String basedir) {
		GrsPageContext ctx = new GrsPageContext();
		ctx.parameters = req.getParameterMap();
		ctx.method = req.getMethod();
		ctx.uri = req.getRequestURI();
		ctx.contextPath = req.getContextPath();
		ctx.userName = username;

		// parse uri
		String[] tk = ctx.uri.split("/");
		int i = 0;

		while (!basedir.equals(tk[i++]));

		if (i < tk.length) { 		
			ctx.process = tk[i];
			ctx.processpath = tk[i++];
		}

		ctx.id = 0;
		if (i < tk.length) 
			try { ctx.id = Long.parseLong(tk[i++]); } catch (Exception e) {}

		ctx.function = "get";
		if (i < tk.length) 
			ctx.function = tk[i++].trim();

		return ctx;
	}
	
	@SuppressWarnings("unchecked")
	protected void responseGrs(GrsPageContext ctx, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {	
			// init Grs stuff		
			res.setCharacterEncoding(Grs.HtmlEncoding);
			ctx.writer = res.getWriter();
			Page page = new Page(ctx);
			req.setAttribute("GrsPage", page);
			getServletContext().getRequestDispatcher(Grs.GrsPageTemplate).forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void responseDownload(HttpServletRequest req, HttpServletResponse res, String filepath) throws ServletException, IOException {
		System.out.println("PATH-N: "+filepath);
		File file = new File(filepath);
		int length   = 0;
		ServletOutputStream outStream = res.getOutputStream();
		ServletContext context  = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filepath);

		// sets response content type
		//	if (mimetype == null) {
		//		mimetype = "application/octet-stream";
		//	}

		mimetype = "application/octet-stream";
		res.setContentType(mimetype);
		res.setContentLength((int)file.length());
		String fileName = (new File(filepath)).getName();

		// sets HTTP header
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		byte[] byteBuffer = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(file));

		// reads the file's bytes and writes them to the response stream
		while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
			outStream.write(byteBuffer,0,length);
		}

		in.close();
		outStream.close();
	}
	
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    protected void responseFile(HttpServletRequest request, HttpServletResponse response, String fsFilename)
        throws ServletException, IOException
    {
    	System.out.println("PATH: "+fsFilename);
        File file = new File(fsFilename);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
        	contentType = "application/octet-stream";
        	response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        }
        response.setCharacterEncoding(Grs.OutHtmlEncoding);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            close(output);
            close(input);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }
}

