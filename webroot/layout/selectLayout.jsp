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
<tiles:insert attribute="body"/>
</body>
</html>