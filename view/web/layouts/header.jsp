<%@ page buffer="64kb" autoFlush="false"%>
<%@ page import="ar.gov.rosario.gait.base.view.util.*, ar.gov.rosario.gait.base.iface.model.*" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<% 
   UserSession userSession = (UserSession) session.getAttribute("userSession");
   boolean showAnonimo = userSession == null ? true : userSession.getIsAnonimo();
%>

<% if (showAnonimo) { %>
	<div id="top">
		<div id="iconostop">
      			<ul>
				<li><a href="<%= request.getContextPath()%>/Login.do" title="Login">Login</a></li>
      			</ul>
		</div>
	</div>
<% } else { %>
	<div id="top">
		<div id="iconostop">
      			<ul>
				<li><a href="<%= request.getContextPath()%>/seg/Login.do?method=logout" title="Salir">Salir</a></li>
        		<li><a href="<%= request.getContextPath()%>/seg/GaitMenu.do?method=build">&nbsp;&nbsp;Men&uacute;&nbsp;&nbsp;</a></li>
      			</ul>
		</div>
		<div class="usr-session">
		    <bean:write name="userSession" property="longUserName"/> (<bean:write name="userSession" property="userName"/>)&nbsp; 
		    <bean:write name="userSession" property="desDireccion"/> / <bean:write name="userSession" property="desArea"/>  	 
	    </div>
	</div>


<% } %>

<% if(ar.gov.rosario.gait.base.iface.model.GaitParam.isWebGait() &&
	"0".equals(ar.gov.rosario.gait.base.iface.model.GaitParam.getString("webGaitOn")) && 
	userSession.getEsAdmin()){%>
	<div class="messages"> 
		La instancia Web de la Aplicacion se encuentra deshabilitada. Intente en unos minutos.
	</div>
<%}%>
	
<% if(ar.gov.rosario.gait.base.iface.model.GaitParam.isIntranetGait() &&
		"0".equals(ar.gov.rosario.gait.base.iface.model.GaitParam.getString("intraGaitOn")) && 
		userSession.getEsAdmin()){%>
		<div class="messages">
			La instancia Intranet de la Aplicacion se encuentra deshabilitada. Intente en unos minutos.
		</div>
<%}%>

