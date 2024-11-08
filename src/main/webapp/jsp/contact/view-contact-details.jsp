<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script>
	$(document).ready(function() {
		$('#dateValId').datepicker({

			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>

<script>
	$(document).ready(function() {
		$('#nextdateValId').datepicker({

			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid = true;
			$('input[type="text"].required').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();

			});
		});
	});

	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid1 = true;
			$('input[id="password"].required').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid1 = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}
			});
			if (isValid1 == false)
				e.preventDefault();

		});
	});
</script>

<c:if test="${not empty successMessage}">
	<div class="alert alert-info" role="alert"
		style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<strong>success!</strong>
		<c:out value="${successMessage}"></c:out>
	</div>
</c:if>
<c:if test="${not empty errorMessage}">
	<div class="alert alert-info" role="alert"
		style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<strong>Info!</strong>
		<c:out value="${errorMessage}"></c:out>
	</div>
</c:if>

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<br> <br>
		<div class="item">
			<div class="text-center underline">
				<h3>Contact Details</h3>
			</div>
			<h3 class="title">Contact Information</h3>
			<div class="item" >

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								First
								Name:</label>
							<c:out value="${contactobj.firstname}"></c:out>

						</div>



						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Email:</label>
							<c:out value="${contactobj.email}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Phone:</label>
							<c:out value="${contactobj.phone}"></c:out>
						</div>


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Last
								Name:</label>
							<c:out value="${contactobj.lastname}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Account
								Name:</label>
							<c:out value="${contactobj.accountBO.accountName}">
							</c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">leads
								Sours:</label>
							<c:out value="${contactobj.opportunityBO.firstName}"></c:out>
						</div>
					</div>

				</div>

			</div>
			<h3 class="title">Address Information</h3>

			<div class="item" >

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">



						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">

								Street:</label>
							<c:out value="${contactobj.street}"></c:out>

						</div>



						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">State:</label>
							<c:out value="${contactobj.state}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">City:</label>
							<c:out value="${contactobj.city}"></c:out>
						</div>


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country
								:</label>
							<c:out value="${contactobj.country}"></c:out>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div style="text-align: right; margin-right: 31px">

				<a href="view-contact"><span
					class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
			</div>
		</div>
	</div>
</div>
</div>
<br>
<br>
</div>


