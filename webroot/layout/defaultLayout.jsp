<%@ page contentType="text/html; charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>

<head>
	<link href="./css/xgafStyle.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="./js/inputDate.js"></script>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript" src="./js/xgaf.js"></script>
	<TITLE><tiles:getAsString name="title"/></TITLE>
</head>

<body>
<table width="100%">
	<tr>
		<td><h3><tiles:getAsString name="header"/></h3><hr></td>
		<TD valign="top" align="left" width="20%"><tiles:insert attribute="menu"/></TD>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<tiles:insert attribute="body"/>
		</td>
	</tr>
	<tr><td align="center" colspan="2"><hr><h5><tiles:getAsString name="footer"/></h5></td></tr>
</table>

</body>
</html>