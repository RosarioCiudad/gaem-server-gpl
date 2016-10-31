<%@ page errorPage="/error.jsp" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!DOCTYPE html 
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html locale="false">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style type="text/css">
	@import url("<%= request.getContextPath()%>/styles/tramites.css");
	@import url("<%= request.getContextPath()%>/styles/gait.css");
	@import url("<%= request.getContextPath()%>/styles/style.css");
	</style>
	<link rel="shortcut icon" href="<%= request.getContextPath()%>/images/favicon.ico" />
	<title><bean:message bundle="base" key="base.gait.title"/></title>

    <script src="<%= request.getContextPath()%>/base/submitForm.js"></script>
    <script src="<%= request.getContextPath()%>/base/calendar.js"></script>
    
    <script src="<%= request.getContextPath()%>/base/OpenLayers-2.11/OpenLayers.js"></script>
    <script src="<%= request.getContextPath()%>/base/proj4js-compressed.js"></script>
    <script src="<%= request.getContextPath()%>/base/mapa.js"></script>
</head>

<body onload="init()" onunload="unload()">
	<div id="container">
		<tiles:insert flush="false" attribute="header"/>
		
		<div id="contenido">	
			<tiles:insert attribute="body"/>
		</div>

		<h1>&nbsp;</h1>

		<tiles:insert attribute="footer"/>
	</div>
</body>

</html:html>
