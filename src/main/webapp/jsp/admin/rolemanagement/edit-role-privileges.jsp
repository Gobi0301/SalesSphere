<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script>
		$(document)
				.ready(
						function() {
							$('#btnsubmit')
									.click(
											function(e) {

												//role

												var role = $(
														'#roleId').val();
												if (role == 'Select') {
													isValid = false;
													$("#roleErr").show();
													$("#roleErr")
															.html(
																	"Please Select Role");
													$("#roleId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#roleErr').hide();
													$('#roleId').css({
														"border" : "",
														"background" : ""
													});
												}

												//privilege

												var privilege = $(
														'#privId').val();
												if (privilege == '') {
													isValid = false;
													$("#privErr").show();
													$("#privErr")
															.html(
																	"Please Select Privilege");
													$("#privId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#roleErr').hide();
													$('#privId').css({
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

	<div class="contact-form-wrapper" style="margin-top: 50px;">
		<div class="box-list">
			<div class="item">

				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')">
					<div class="row">

						<div class="form-group">
							<form:form method="post" modelAttribute="rolesbo">

								<div>
									<form:hidden path="roleId" />
								</div>
								<h3 style="text-align: center;">Edit-Role-Privileges</h3>

								<td></br> </br> <label>Role <span class="font10 text-danger">*</span></label>

									<form:select path="roleName" id="roleId" class="form-control required">
										<form:option value="Select">--Select Role--</form:option>
										<form:errors path="roleName" class="error" />
												<div id="roleErr" style="color: red;"></div>
												
										<c:forEach items="${rolebolists}" var="rolename">
											<form:option value="${rolename.roleName}"></form:option>
										</c:forEach>
									</form:select></td>
											
									

								<br />
								
								<label>Privilege <span class="font10 text-danger">*</span></label>
								<form:checkboxes path="privilegeIds" id="privId" items="${listprivilege}"
									itemLabel="privilegename" itemValue="privilegeId" />
									<form:errors path="privilegeIds" class="error" />
												<div id="privErr" style="color: red;"></div>

								<div style="text-align: right; margin-right: 31px">
									<button type="submit" id="btnsubmit"
										class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
									<a href="role-privileges"><span
										class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
								</div>


							</form:form>

						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</div>
</body>