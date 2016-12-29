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
package ar.gov.rosario.gait.base.view.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;

public class GaitDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(GaitDownloadServlet.class);


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preInvoke(req);
		request(req, res);
	}

	protected void request(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			String uri = request.getRequestURI();
			String filePath = GaitParam.getString(GaitParam.FILE_SHARE_PATH);
			int begin = uri.lastIndexOf("/download/") + "/download/".length();

			Path path = Paths.get(filePath + File.separator + uri.substring(begin));
			File file = path.toFile();
			
			ServletOutputStream outStream = response.getOutputStream();
			ServletContext context  = getServletConfig().getServletContext();

			// sets response content type
			String mimetype = context.getMimeType(file.getName());
			if (mimetype == null) mimetype = "application/octet-stream";
			
			response.setContentType(mimetype);
			response.setContentLength(Long.valueOf(file.length()).intValue());
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", 
					String.format("attachment; filename='%s'", file.getName()));

			Files.copy(path, outStream);

			outStream.flush();
			outStream.close();	

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void preInvoke(HttpServletRequest request) {
		String username = request.getParameter("username");
		String usertoken = request.getParameter("usertoken");
		UserContext userContext = SegServiceLocator.getSeguridadService().initUserApm(username, usertoken);
		DemodaUtil.setCurrentUserContext(userContext);
	}
}
