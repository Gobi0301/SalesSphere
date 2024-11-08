<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="not-ie no-js" lang="en">
<head>
<meta charset="utf-8">
 <title>My Sales</title>
<!--Meta Property  -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="description"
	content="<%=request.getAttribute("meta")%>" />
<meta NAME="keywords"
	CONTENT="<%=request.getAttribute("meta")%>">
<link rel="canonical" href="http://myjobkart.com" />
<link rel="alternate" href="http://myjobkart.com" />
<!-- End Meta Property -->

<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<!-- <link
	href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic|Bitter:700'
	rel='stylesheet' type='text/css'> -->
<link rel="apple-touch-icon"
	href="resources/theme/images/apple-touch-icon.png">
<link rel="shortcut icon" href="resources/theme/images/favicon.ico"
	type="image/x-icon">
<link href="resources/plugins/bootstrap-3.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="resources/plugins/font-awesome-4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="resources/plugins/magnific-popup/magnific-popup.css"
	rel="stylesheet">
<link href="resources/theme/css/theme.css" rel="stylesheet">
<link href="resources/theme/css/theme-custom.css" rel="stylesheet">
<link rel="stylesheet" href="resources/theme/css/mobile-custom.css">
</head>
<body>
	<tiles:insertAttribute name="body"></tiles:insertAttribute>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.wpb_alert').on('click', function() {
				$(this).fadeOut();
			});
		});
	</script>

</body>
</html>