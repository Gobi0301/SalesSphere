<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<style>
.blockHead {
	position: relative;
}

.blockHead h4 {
	color: #333333;
}

.error {
	border: 1px solid red;
}

.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>
<script>
$(document).ready(function() {

	$('#inputFirstName').focus();

	$('#btnsubmit').click(function(e) {

			var roleName = $('#inputFirstName').val();
			if (roleName == '') {
				isValid = false;
				$("#inputFirstNameErr").show();
				$("#inputFirstNameErr").html("Please select salutation");
				$("#inputFirstName").css({
					"border" : "1px solid red",

				});

			}else if (!/^[a-zA-Z_\s]*$/g.test(roleName)) {
				$("#inputFirstNameErr").show();
				$("#inputFirstNameErr").html("Please Enter Character Only");
				var isValid = false;
			} else {
				$('#inputFirstNameErr').hide();
				$('#inputFirstName').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>

<div class="contact-form-wrapper">
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit Roles</h3>
				</div>
				<br/>

              <div class="item">
				<form:form method="POST" id="addForm" action="edit-role-type"
					modelAttribute="editroleobj" commandName="editroleobj">

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label> Role Name <span class="font10 text-danger">*</span></label>
								<form:input id="inputFirstName" type="text" path="roleName"
									class="form-control required" placeholder="RoleName"
									maxlength="150" />
								<form:errors path="roleName" class="input_error" />
								<div id="inputFirstNameErr" style="color: red;"></div>
								
							</div>
						</div>
					</div>

					<form:hidden path="roleId" />

					<div class="row">
						<div class="col-sm-1" style="float: right;">
							<a href=view-role?page=1><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
						</div>
						<div class="col-sm-1" style="float: right;">
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
