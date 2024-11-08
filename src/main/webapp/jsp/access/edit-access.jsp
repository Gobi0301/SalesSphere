<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>


	<script>
		$(document).ready(function() {
			$('#firstNameInput').focus();
			$('#btnsubmit').click(function(e) {
				var isValid = true;
//access
												var access = $(
												'#firstNameInput').val();
										if (access == '') {
											isValid = false;
											$("#firstNameErr").show();
											$("#firstNameErr")
													.html(
															"Please Enter Access");
											$("#firstNameInput")
													.css(
															{
																"border" : "1px solid red",
															});

										}else if (!/^[a-zA-Z._/&]*$/.test(access)) {
											
											$("#firstNameErr").show();
											$("#firstNameErr").html("Please enter characters only");
											$("#firstNameInput").css({
												"border" : "1px solid red",

											});
											isValid = false;
										} else {
											$('#firstNameErr').hide();
											$('#firstNameInput').css({
												"border" : "",
												"background" : ""
											});
										}

												if (isValid == false)
													e.preventDefault();

											});
						});
	</script>

<body>

	<div class="contact-form-wrapper">
		<div class="box-list">
			<div class="item">
				<div class="row ">
					<div class="text-center underline">
						<h3>Edit Access</h3>
					</div>
					<br />
					<div class="item">
	<form:form method="POST" id="add-form" action="edit-access"
		modelAttribute="editAccessBo">
<div class="row">

		<div class="col-sm-4">
			<div class="form-group">
				<label>Access Name<span class="font10 text-danger">*</span></label>
				<form:input id="firstNameInput" type="text" path="accessName"
					class="form-control required" placeholder="Access Name"
					maxlength="150" />
				<form:errors path="accessName" cssClass="error" />
				<div id="firstNameErr" style="color: red;"></div>
			</div>
		</div>
		<form:hidden path="accessId" />
		</div>
		<br />
		<div class="row" >
		     <div class="col-sm-1" style="float: right;" >
				<a href="view-access?page=1"><span
					class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
					
			</div>
			<div class="col-sm-1" style="float: right;" >
				<button type="submit" id="btnsubmit"
					class="btn btn-t-primary btn-theme lebal_align">Update</button>
			</div>
			</div>

	</form:form>
	</div>
	</div>
	</div>
	</div>
	</div>



</body>
</html>