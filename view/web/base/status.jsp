<%@ page buffer="64kb" autoFlush="false"%>
<%@ page import="java.io.*" %>
<%@ page import="coop.tecso.demoda.buss.dao.*"%>
<%@ page import="coop.tecso.demoda.iface.helper.*"%>
<%@ page import="ar.gov.rosario.gait.emi.buss.dao.*" %>
<%@ page import="ar.gov.rosario.gait.emi.buss.bean.*" %>
<%@ page import="java.util.*" %>

<html>
<body>
<pre>
<% System.gc(); %>

<% ar.gov.rosario.gait.base.buss.bean.ConsoleManager.getInstance().infoActas(new PrintWriter(out)); %>
<% ar.gov.rosario.gait.base.buss.bean.ConsoleManager.getInstance().status(new PrintWriter(out)); %>

</pre>  
</body>
</html>
