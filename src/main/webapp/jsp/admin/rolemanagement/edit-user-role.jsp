<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>

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
				var name = $('#nameId').val();
				if (name == '') {
					isValid = false;
					$("#nameErr").show();
					$("#nameErr").html("Please enter user name");
					$("#nameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#nameErr').hide();
					$('#nameId').css({

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
					<h3>Edit UserRoles</h3>
				</div>
				<br />

				<div class="item">
					<form:form method="POST" id="addForm" action="edit-user-role"
						modelAttribute="useredit">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">

									<label>User Name <span class="font10 text-danger">
											*</span></label>
									<form:select path="name"  id="nameId" class="form-control required">
										<form:option value="Select"></form:option>

										<c:forEach items="${listuser}" var="username">
											<form:option value="${username.name}"></form:option>
										</c:forEach>

									</form:select>
									<form:errors path="name" class="error" />
									<div id="nameErr" style="color: red;"></div>

									<label>RoleName <span class="font10 text-danger">*</span></label>
									<form:checkboxes items="${listbo}" itemLabel="roleName"
										itemValue="roleId" path="roleIdList" />

								</div>

							</div>


						</div>

						<br />
						<div class="row">
						<div class="col-sm-1">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align">Update</button>
							</div>
							<div class="col-sm-1">
								<a href=create-user-roles><span
									class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
							</div>
							
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>