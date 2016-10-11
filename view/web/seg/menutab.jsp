<%@ page buffer="64kb" autoFlush="false"%>
<%@ page import="ar.gov.rosario.gait.base.view.util.*" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<% 
   UserSession userSession = (UserSession) session.getAttribute("userSession");
   boolean showAnonimo = userSession == null ? true : userSession.getIsAnonimo();
   boolean showMenu = false; 
   if (userSession != null) {
   	showMenu = true;
   }
   
%>

<script type="text/javascript">
var TxInicial = 12;
var textoHtml;
var numCol = 1;
function zoom(Factor) {
    tx = document.getElementById("container");
    TxInicial = TxInicial + Factor;
    tx.style.fontSize = TxInicial;
    if (numCol > 1) {
        tb = document.getElementById("container");
        tb.style.fontSize = TxInicial;
    }
}
</script>

	<div id="userinfo">
	<% if(ar.gov.rosario.gait.base.iface.model.GaitParam.isWebGait() &&
			"0".equals(ar.gov.rosario.gait.base.iface.model.GaitParam.getString("webGaitOn")) && 
			userSession.getEsAdmin()){%>
			<div class="messages"> 
				"La instancia Web de la Aplicacion se encuentra deshabilitada"
			</div>
	<%}%>
	
	<% if(ar.gov.rosario.gait.base.iface.model.GaitParam.isIntranetGait() &&
			"0".equals(ar.gov.rosario.gait.base.iface.model.GaitParam.getString("intraGaitOn")) && 
			userSession.getEsAdmin()){%>
			<div class="messages">
				"La instancia Intra de la Aplicacion se encuentra deshabilitada"
			</div>
	<%}%>
	
<ul>
</ul>
