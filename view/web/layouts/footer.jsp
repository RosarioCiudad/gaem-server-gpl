<%@ page buffer="64kb" autoFlush="false"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="ar.gov.rosario.gait.base.view.util.*" %>

<% 
   UserSession userSession = (UserSession) session.getAttribute("userSession");
   boolean showAnonimo = userSession == null ? true : userSession.getIsAnonimo();
%>


<div id="pie">          
<p>
Desarrollado con software libre por la <a href="http://www.rosario.gob.ar"><strong>Municipalidad de Rosario</strong></a> y <a href="http://www.tecso.coop" target="_blank"><strong>Coop. Tecso Ltda</strong></a> | Rosario | Santa Fe | Argentina | 2016
<p/>

<p>
<a href="http://www.rosario.gov.ar/sitio/paginainicial/terminos.html" target="_blank"><strong>T&eacute;rminos y condiciones.</strong></a>&nbsp;
<!--  a href="#" target="_blank">Contacto</a>  -->
<p/>
</div>
