<%@ page buffer="64kb" autoFlush="false"%>
<%@ page import="java.io.*" %>
<%@ page import="coop.tecso.demoda.buss.dao.*"%>
<%@ page import="ar.gov.rosario.gait.dao.*" %>
<%@ page import="ar.gov.rosario.gait.bean.*" %>
<%@ page import="ar.gov.rosario.gait.def.buss.bean.*" %>
<%@ page import="ar.gov.rosario.gait.bean.*"%>
<%@ page import="java.util.*" %>

<html>
<body>
<pre>
<% System.gc(); %>

<% ar.gov.rosario.gait.base.buss.bean.ConsoleManager.getInstance().status(new PrintWriter(out)); %>

</pre>  
</body>
</html>
