<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="not-ie no-js" lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="REFRESH"
	content="<%= session.getMaxInactiveInterval()%>;" />
<title>My Sales</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="description"
	content="<%=request.getAttribute("meta")%>" />
<meta NAME="keywords"
	CONTENT="<%=request.getAttribute("meta")%>">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<link rel="shortcut icon" href="resources/theme/images/myjobkart.ico" >
<!-- <link
	href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic|Bitter:700'
	rel='stylesheet' type='text/css'> -->
<link href="resources/plugins/bootstrap-3.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="resources/plugins/font-awesome-4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="resources/plugins/magnific-popup/magnific-popup.css"
	rel="stylesheet">
<link href="resources/theme/css/theme.css" rel="stylesheet">
<link href="resources/theme/css/theme-custom.css" rel="stylesheet">
<link rel="stylesheet" href="resources/theme/css/contact-buttons.css">
<link rel="stylesheet" href="resources/theme/css/mobile-custom.css">
<script src="resources/theme/js/jquery.contact-buttons.js"></script>
<script src="resources/theme/js/demo.js"></script>
<!-- Modernizr Plugin -->
<script src="resources/theme/js/modernizr.custom.79639.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="resources/theme/js/jquery-1.11.2.min.js"></script>
<!-- Retina Plugin -->
<script src="resources/theme/js/retina.min.js"></script>
<!-- ScrollReveal Plugin -->
<script src="resources/theme/js/scrollReveal.min.js"></script>
<!-- Flex Menu Plugin -->
<script src="resources/theme/js/jquery.flexmenu.js"></script>
<!-- Slider Plugin -->
<script src="resources/theme/js/jquery.ba-cond.min.js"></script>
<script src="resources/theme/js/jquery.slitslider.js"></script>
<!-- Carousel Plugin -->
<script src="resources/theme/js/owl.carousel.min.js"></script>
<!-- Parallax Plugin -->
<script src="resources/theme/js/parallax.js"></script>
<!-- Counterup Plugin -->
<script src="resources/theme/js/jquery.counterup.min.js"></script>
<script src="resources/theme/js/waypoints.min.js"></script>
<!-- No UI Slider Plugin -->
<script src="resources/theme/js/jquery.nouislider.all.min.js"></script>
<!-- Bootstrap Wysiwyg Plugin -->
<script src="resources/theme/js/bootstrap-wysiwyg.js"></script>
<script src="resources/theme/js/jquery.hotkeys.js"></script>
<!-- Flickr Plugin -->
<script src="resources/theme/js/jflickrfeed.min.js"></script>
<!-- Fancybox Plugin -->
<script src="resources/theme/js/fancybox.pack.js"></script>
<!-- Magic Form Processing -->
<script src="resources/theme/js/magic.js"></script>
<!-- jQuery Settings -->
<script src="resources/theme/js/settings.js"></script>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="contentArea">
	<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</div> 
	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	<script src="resources/plugins/jquery.js"></script>
	<script src="resources/plugins/jquery.easing-1.3.pack.js"></script>
	<script src="resources/plugins/bootstrap-3.3.2/js/bootstrap.min.js"></script>
	<script
		src="resources/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
	<script src="resources/theme/js/theme.js"></script>
	<!-- <script src="http://maps.googleapis.com/maps/api/js?sensor=false"
		type="text/javascript"></script> -->
	<script src="resources/plugins/gmap3.min.js"></script>
	<script src="resources/theme/js/map-detail.js"></script>
	<script type="text/javascript"
		src="resources/theme/script/jquery.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$('.wpb_alert').on('click', function() {
			$(this).fadeOut();
		});
	});
	</script>
	<script src="resources/theme/js/jquery.contact-buttons.js"></script>
	<script src="resources/theme/js/demo.js"></script>
</body>
</html>