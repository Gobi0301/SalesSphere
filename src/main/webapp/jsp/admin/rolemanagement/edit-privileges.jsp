<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script>
	$(document).ready(function() {

		$('#privilegename').focus();

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

			} else if (!/^[a-zA-Z\_s]*$/g.test(privilegename)) {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please enter characters only");
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>



	<div class="contact-form-wrapper">
		<div class="box-list">
			<div class="item">
				<div class="row ">
					<div class="text-center underline">
						<h3>Edit Privileges</h3>
					</div>

					<br />
					<div class="item">
						<form:form method="POST" id="addForm" action="edit-privileges"
							modelAttribute="privilegebo">
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">

										<label>Privilege Name <span class="font10 text-danger">
												*</span></label>
										<form:input path="privilegename" class="form-control"
											id="privilegenameId" />
										<form:errors path="privilegename" cssClass="error" />
										<div id="privilegenameErr" style="color: red;"></div>


									</div>
								</div>

								<form:hidden path="privilegeId" />
							</div>

							<br />
							<div class="row">
								<div class="col-sm-1" style="float: right;">
									<a href="view-privileges"><span
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