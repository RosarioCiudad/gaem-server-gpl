<%@ page buffer="64kb" autoFlush="true"%>
<%@ page import="ar.gov.rosario.gait.base.view.util.*, coop.tecso.grs.*, coop.tecso.grs.page.*, coop.tecso.adpcore.*, coop.tecso.adpcore.grs.*, java.util.*, java.io.*" %>

<script>
function grsfunc(fname) {
	var form = document.getElementById("grsform");
	form.action += '/' + fname;
	form.submit();
}

function grsback() {
	window.location.href = "<%= request.getContextPath()%>/seg/GaitMenu.do?method=build";
}

</script>

<% 
   // mmm Necesita pasarse a un Service/Controller
   Page grsPage = (Page) request.getAttribute("GrsPage"); 
   GrsPageContext ctx = grsPage.getContext(); 
   String action = String.format("%s/grs/%s/%s", ctx.contextPath, ctx.processpath, ctx.id);
   String baseurl = String.format("%s/grs/%s", ctx.contextPath, ctx.processpath);
%>

<form name="grsform" id="grsform" method="post" action="<%=action%>"> 

<div id="messages">
</div>

<% out.flush(); grsPage.request();

	// Recuperamos info de la corrida actual o la ultima que termino
	AdpRun run = null;
	List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();	
	run = AdpRun.getLastRun(ctx.process, ctx.userName);
	if (run != null)
		files = new Grs().adp().files(run.getId());
%>

<!-- botonera -->
<p align="center">
<% if (run == null || !(run.getIdEstadoCorrida() == AdpRunState.PROCESANDO.id() || 
						run.getIdEstadoCorrida() == AdpRunState.ESPERA_COMENZAR.id())) { %>
	
<button class="boton" onClick="this.form.reset()">Limpiar</button>
&nbsp;
<button class="boton" onclick="grsfunc('accept', '');">Ejecutar</button>
&nbsp;
	
<% } else if (run != null && (
	run.getIdEstadoCorrida() == AdpRunState.PROCESANDO.id() ||
	run.getIdEstadoCorrida() == AdpRunState.ESPERA_COMENZAR.id())) { %>

<button class="boton" disabled="disabled" onClick="this.form.reset()">Limpiar</button>
&nbsp;
<button class="boton" disabled="disabled" onclick="grsfunc('accept', '');">Ejecutar</button>
&nbsp;
	
<% } %>

<button class="boton" onclick="grsfunc('get', '');">Refrescar</button>
</p>

<div id="calendarDiv" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
</form>

<% try { %>

<% if (run != null && (
	run.getIdEstadoCorrida() == AdpRunState.FIN_OK.id() ||
	run.getIdEstadoCorrida() == AdpRunState.ESPERA_CONTINUAR.id())) { %>
<!-- Resultado Filtro -->
<div id="resultadoFiltro">
	<table class="tramonline" border="0" cellpadding="0" cellspacing="1" width="100%">
	<caption>Resultado de la petici&oacute;n del usuario <%=run.getUsuario()%> hecha el <%=run.getFechaInicio()%></caption>
	<tbody>
		<tr>
			<th width="1">&nbsp;</th> <!-- Ver/Seleccionar -->
			<th align="left">Reporte</th>
			<th align="left">Observaci&oacute;n</th>
		</tr>
		<% for(Map<String, Object> file : files) { 
			String filename = (String) file.get("filename");
			if (filename == null)
				filename = (String) file.get("FILENAME");
			String basename = run.getProcessDir(AdpRunDirEnum.BASE);
			System.out.println("FILE: " + filename);
		%>
		<tr>
			<% if (new File(filename).getName().endsWith("html")) {%>
			<td>
				<a style="cursor: pointer; cursor: hand;" href="<%=GaitGrsServlet.pathToUrl(basename, filename, baseurl)%>" target="_blank"><img title="Ver" alt="Ver" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/></a>
			</td>			
			<%
			} else {
			%>
			<td>
				<a style="cursor: pointer; cursor: hand;" href="<%=GaitGrsServlet.pathToUrl(basename, filename, baseurl).replaceFirst("/salida/", "/download/")%>">
				<img title="Ver" alt="Ver" border="0" src="<%=request.getContextPath()%>/images/iconos/selec0.gif"/>
				</a>
			</td>
			<% } %>
			
			<td><%= file.get("nombre") %></td> 
			<td><%= file.get("observacion") %></td> 
		</tr>
		<% } %>
	</tbody> 
	</table> 
</div> 
<!-- Fin Resultado Filtro --> 

<% } else if (run != null && (
	run.getIdEstadoCorrida() == AdpRunState.PROCESANDO.id() ||
	run.getIdEstadoCorrida() == AdpRunState.ESPERA_COMENZAR.id())) { %>
	<fieldset>
		<table class="tabladatos">
		<tr>
			<td class="normal">
				<%= AdpRunState.getById(run.getIdEstadoCorrida()).description() %> - <%= run.getMensajeEstado() %>
				&nbsp;
				<img border="0" src="<%=request.getContextPath()%>/images/iconos/barraProcesando.gif"/>
				</td>
			</tr>
		</table>
	</fieldset>
			
<% } else if (run != null) { %>

	<fieldset>
		<table class="tabladatos">
		<tr>
			<td class="normal">
				<%= AdpRunState.getById(run.getIdEstadoCorrida()).description() %> - <%= run.getMensajeEstado() %>
				</td>
			</tr>
		</table>
	</fieldset>

<% } %>

 
<table class="tablabotones"> 
	<tr>				
		<td align="left"> 
   			<input type="button" name="btnVolver" value="Volver" onclick="grsback();" class="boton"> 
		</td> 
	</tr> 
</table> 

<div id="tmsg" style="display:none">
<% if (grsPage.messages.size() > 0) { %>
<ul class="error" id="strutsErrors">
<% for(String msg : grsPage.messages) { %>
	  <li><%= msg %></li>
<% } %>
</ul>
<% } %>
</div>

<script>
	document.getElementById("messages").innerHTML = document.getElementById("tmsg").innerHTML;
</script>


<% } catch (Exception e) { e.printStackTrace(); } %>

<% ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil.closeSession(); %>