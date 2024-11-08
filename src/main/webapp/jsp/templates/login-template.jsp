<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="not-ie no-js" lang="en">
<head>
<meta charset="utf-8">
<title>My Sales</title>
<!-- Meta informations tag start here -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="description" content="<%=request.getAttribute("meta")%>" />
<meta NAME="keywords" CONTENT="<%=request.getAttribute("meta")%>">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta http-equiv="REFRESH"
	content="<%=session.getMaxInactiveInterval()%>;" />
<!-- Meta informations tag start here -->

<!-- Css Class And images and font class link loaded -->
<link rel="shortcut icon" href="resources/theme/images/myjobkart.ico">
<link rel="apple-touch-icon" href="images/apple-touch-icon.png">
<link rel="apple-touch-icon" href="images/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" href="images/apple-touch-icon-114x114.png">
<link rel="apple-touch-icon" href="images/apple-touch-icon-144x144.png">
<link rel="apple-touch-icon"
	href="resources/theme/images/apple-touch-icon.png">



<!-- <link
	href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic|Bitter:700'
	rel='stylesheet' type='text/css'> -->


<link href="resources/plugins/bootstrap-3.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="resources/theme/css/popup.css">
<link
	href="resources/plugins/font-awesome-4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="resources/plugins/magnific-popup/magnific-popup.css"
	rel="stylesheet">
<link href="resources/theme/css/theme.css" rel="stylesheet">
<link href="resources/theme/css/theme-custom.css" rel="stylesheet">
<link rel="stylesheet" href="resources/theme/css/contact-buttons.css">
<link rel="stylesheet" href="resources/theme/css/mobile-custom.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet" type="text/css"
	href="resources/theme/css/jquery-ui-1.10.4.custom.css" />
<link rel="stylesheet" type="text/css"
	href="resources/theme/css/tables.css" />

</head>

<body>

	
	
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
			<div class="container">
			<tiles:insertAttribute name="body"></tiles:insertAttribute>
			</div>
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		
	

	

	<!-- Country State City Drop Down Plugins -->
	<!-- <script src="resources/theme/js/cityLocation.js"></script> -->
	<!-- State City Drop Down Plugins -->
	<script src="resources/theme/js/state.js"></script>
	<!-- Js file link implemented -->
	<script src="resources/plugins/jquery.easing-1.3.pack.js"></script>
	<script src="resources/plugins/bootstrap-3.3.2/js/bootstrap.min.js"></script>
	<script
		src="resources/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
	<script src="resources/theme/js/theme.js"></script>
	<!-- <script src="http://maps.googleapis.com/maps/api/js?sensor=false"
		type="text/javascript"></script> -->
	<script src="resources/plugins/gmap3.min.js"></script>
	<script src="resources/theme/js/map-detail.js"></script>
	<script src="resources/theme/js/jquery.contact-buttons.js"></script>
	<script src="resources/theme/js/demo.js"></script>
	<script src="resources/theme/js/jquery.js"></script>
	<!-- <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="vendor/modernizr.js"></script>
	<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->
	<script type="text/javascript"
		src="resources/theme/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript"
		src="resources/theme/script/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="resources/theme/script/jquery.min.js"></script>
	<script type="text/javascript"
		src="resources/theme/script/jquery-ui.js"></script>
	<script src="resources/theme/js/popup.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.wpb_alert').on('click', function() {
								$(this).fadeOut();
							});

							$("#emlId")
									.click(
											function() {
												var mailId = $("#emIds").val();
												var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

												if (mailId.match(mailformat)) {
													$("#emailAddressError")("");
													$
															.ajax({
																type : "POST",
																url : "jobseeker-email-update",
																data : "emailId="
																		+ mailId,
																success : function(
																		data) {

																	window.location
																			.replace("jobseeker-home");

																},
															});
												} else {
													isValid = false;
													$("#emailAddressError")
															(
																	"You have entered an invalid email address!");
												}
											});
						});
	</script>
	<c:if test="${(userType eq 'Jobseeker') && empty emailId}">
		<div id="boxes">
			<div style="top: 199.5px; left: 1px; display: none;" id="dialog"
				class="window">
				<div id="lorem">
					<div class="form-group" style="margin: 10px;">
						<div class="row clearfix">
							<div class="col-sm-10" style="padding: 0px;">
								<input type="text11" name="emailAddress" id="emIds"
									placeholder="Email Id" class="form-control"
									style="height: 45px; font-weight: 700;">
							</div>
							<div class="col-sm-2" style="padding: 0px;">
								<span class="btn btn-theme btn-success btn-block" id="emlId"
									style="padding: 11px 10px; background-color: #7cb228; border-color: #7cb228; font-family: 'Segoe UI', 'wf_segoe-ui_normal', 'Arial', sans-serif;">
									<i class="fa fa-arrow-right" aria-hidden="true"
									style="font-size: 20px;"></i>
								</span>
							</div>
						</div>
						<div id="emailAddressError" style="color: #000000;"></div>
					</div>
				</div>
				<!-- <div id="popupfoot">
				<a href="#" class="close agree">I agree</a> | <a class="agree"
					style="color: red;" href="http://www.cartoonnetwork.com/">I do
					not agree</a>
			</div> -->
			</div>
			<div
				style="width: 1478px; font-size: 32pt; color: white; height: 602px; display: none; opacity: 0.8;"
				id="mask"></div>
		</div>
	</c:if>
</body>
</html>