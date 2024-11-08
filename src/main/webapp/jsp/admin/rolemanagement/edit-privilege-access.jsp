<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script>
		$(document).ready(function() {
			$('#btnsubmit').click(function(e) {
				var isValid = true;
				var privilegename = $('#privilegenameId').val();
				if (privilegename == '') {
					isValid = false;
					$("#privilegenameErr").show();
					$("#privilegenameErr").html("Please enter privilege name");
					$("#privilegenameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#privilegenameErr').hide();
					$('#privilegenameId').css({

						"border" : "",
						"background" : ""
					});
				}
				if (isValid == false)
					e.preventDefault();
			});
		});

	</script>

	<div class="contact-form-wrapper" style="margin-top: 50px;">
		<div class="box-list">
			<div class="item">
				<div class="text-center underline">
					<h3>Edit - Privilege - Access</h3>
				</div>
				<br />
				<div class="item">
					<form:form method="POST" id="addForm"
						action="edit-privilege-access"
						modelAttribute="editPrivilegeAccessObj">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">

									<label>Privilege Name <span class="font10 text-danger">
											*</span></label>
									<form:select path="privilegename" id="privilegenameId" class="form-control required">
										<form:option value="Select"></form:option>

										<form:options items="${listprivilege }"
											itemLabel="privilegename" itemValue="privilegename"></form:options>
									</form:select>
									<form:errors path="privilegename" class="error" />
									<div id="privilegenameErr" style="color: red;"></div>

								
										
									<label style="margin-left: 5px;">Access Name <span class="font10 text-danger">*</span></label>
									<form:checkboxes path="accessIds" items="${accessBO}"
										itemLabel="accessName" itemValue="accessId" />

									<form:hidden path="privilegeId" /> 

								</div>

							</div>


						</div>

						<br />
						<div class="row">
							<div class="col-sm-1" style="float: right;">
								<a href="view-privilege-access"><span
									class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
							</div>
							<div class="col-sm-1" style="float: right;">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align">Submit</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
