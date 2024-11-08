<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!-- <script>
    document.addEventListener('DOMContentLoaded', function() {
        var form = document.getElementById('addForm');
        var nameSelect = document.querySelector('select[name="name"]');
        var checkboxes = document.querySelectorAll('input[name="roleName"]');
        var nameError = document.getElementById('nameErr');
        var roleError = document.getElementById('roleErr');

        form.addEventListener('submit', function(event) {
            var isNameSelected = nameSelect.value !== 'Select';
            var isRoleChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

            // Clear previous error messages
            nameError.textContent = '';
            roleError.textContent = '';

            // Validation: Check if a user name is selected and at least one role is checked
            if (!isNameSelected) {
                event.preventDefault(); // Prevent form submission
                nameError.textContent = 'Please select a user name.';
            }

            if (!isRoleChecked) {
                event.preventDefault(); // Prevent form submission
                roleError.textContent = 'Please select at least one role.';
            }

            // Optionally, use AJAX to submit the form if validation passes
            if (isNameSelected && isRoleChecked) {
                var xhr = new XMLHttpRequest();
                xhr.open('POST', form.action, true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Handle successful response
                        console.log('Form submitted successfully');
                        // Optionally redirect or handle successful submission
                    } else {
                        // Handle error
                        console.log('Form submission failed');
                        // Optionally show an error message
                    }
                };

                var formData = new FormData(form);
                xhr.send(new URLSearchParams(formData).toString());
                event.preventDefault(); // Prevent the default form submission
            }
        });
    });
    </script> -->
    
    
    <script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			//Privilege Name
			var isValid = true;
			var userName = $('#nameId').val();
			if (userName == 'Select') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please Select User Name");
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

			var checkedCheckboxes = $(':checkbox:checked').length;

			if (checkedCheckboxes === 0) {
				isValid = false;
				$("#roleErr").show();
				$("#roleErr").html("Please select at least one Role checkbox");

			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>
<!-- 
<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {

			//var isValid = true;

			var name = $('#name').val();
			if (name == 'Select') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please Select UserName");
				$("#name").css({
					"border" : "1px solid red",

				});

			} else {
				$('#nameErr').hide();
				$('#name').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script> 
<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var rolename = $('#RoleandUserNameId').val();
			if (rolename == '') {
				isValid = false;
				$("#RoleandUserNameErr").show();
				$("#RoleandUserNameErr").html("Please enter User Role ");
				$("#RoleandUserNameId").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(rolename)) {
				isValid = false;
				$("#RoleandUserNameErr").show();
				$("#RoleandUserNameErr").html("Please enter characters only");
				$("#RoleandUserNameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#RoleNameErr').hide();
				$('#RoleandUserNameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

-->

	<c:if test="${not empty successmessage}">
		<div class="alert alert-success" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>success!</strong>
			<c:out value="${successmessage}"></c:out>
		</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-success">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Success:</strong>
			<c:out value="${errorMessage}"></c:out>
		</div>

	</c:if>
	<div class="contact-form-wrapper" style="margin-top: 50px;">
		<div class="box-list">
			<div class="item">
				<div class="text-center underline">
					<h3>Create UserRoles</h3>
				</div>
				<br />

				<div class="item">
					<form:form method="POST" id="addForm" action="create-user-roles"
						modelAttribute="userRoles">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>User Name <span class="font10 text-danger">*</span></label>
									<form:select path="name" id="nameId" class="form-control required">
										<form:option value="Select">Select</form:option>
										<form:options items="${listuser}" itemLabel="name"
											itemValue="id"></form:options>
									</form:select>
									<form:errors path="name" cssClass="error" />
									<div id="nameErr" style="color: red;"></div>

									<label style="margin-left: 5px;">RoleName <span
										class="font10 text-danger">*</span></label>
									<form:checkboxes items="${listbo}" itemLabel="roleName"
										itemValue="roleId" path="roleName" id="rolenameid" />
									<div id="roleErr" style="color: red;"></div>
								</div>
							</div>
						</div>

						<br />
						<div class="row">
							<div class="col-sm-1" style="float: right;">
								<a href="create-user-roles"><span
									class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
							</div>
							<div class="col-sm-1" style="float: right;">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align">Submit</button>
							</div>
						</div>
					</form:form>

					<h3 style="text-align: center;">View User Roles</h3>

					<c:if test="${not empty infomessage}">
						<div class="alert alert-info" role="alert"
							style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>info!</strong>
							<c:out value="${infomessage}"></c:out>
						</div>
					</c:if>
					<form:form id="myForm" method="post" class="login-form clearfix"
						action="search-user" commandName="searchObj">
						<div class="row"
							style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
							<div class=" col-md-3">
								<div class="form-group home-left">
									<label class="hidden-xs"></label>
									<form:input type="text" class="form-control" path="name"
										placeholder="User Role" id="RoleandUserNameId"
										escapeXml="false" style="height: 35px;font-weight: 700;"></form:input>
									<form:errors path="name" cssClass="error" />
									<div id="RoleandUserNameErr" style="color: red;"></div>
								</div>
							</div>

							<div class=" col-md-1" style="padding-bottom: 0px;">
								<div class="form-group home-right">
									<label class="hidden-xs"></label>
									<button class="btn btn-theme btn-success btn-block" id="search"
										style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
										<small><i class="fa fa-search" aria-hidden="true"
											style="font-size: 20px;"></i></small>
									</button>
								</div>
							</div>

						</div>
					</form:form>

					<c:if test="${ empty infomessage}">
						<c:if test="${not empty totalCountOfUsers}">

							<div class="col-sm-12" style="margin-top: 20px">
								<p>
									<a class="btn btn-theme btn-xs btn-default"
										style="color: #1b1818; font-weight: bold;"><c:out
											value="${totalCountOfUsers}"></c:out></a> <strong
										class="color-black">UserRole Found</strong>
								</p>
							</div>

						</c:if>

						<c:if test="${not empty countOfUsers}">

							<div class="col-sm-12" style="margin-top: 20px">
								<p>
									<a class="btn btn-theme btn-xs btn-default"
										style="color: #1b1818; font-weight: bold;"><c:out
											value="${countOfUsers}"></c:out></a> <strong class="color-black">UserRoleSearch
										Found</strong>
								</p>
							</div>

						</c:if>


						<c:if test="${!empty listUserBo.list}">
							<div class="row">
								<div class="pi-responsive-table-sm">
									<div class="pi-section-w pi-section-white piTooltips">

										<display:table id="data" name="${listUserBo.list}"
											requestURI="/create-user-roles" export="false"
											class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

											<display:column property="sNo" title="SNo" />

											<display:column property="name" title="User Name" />


											<display:column url="edit-user-role" media="html"
												paramId="id" paramProperty="id" title="Edit">
												<a href="edit-user-role?userId=${data.id}"><i
													style="text-align: center;" class="fa fa-pencil"></i></a>

											</display:column>

										</display:table>

									</div>
								</div>
							</div>
						</c:if>
						<nav style="text-align: center;">
						<ul class="pagination pagination-theme  no-margin center"
							style="margin-left: 575px;">
							<c:if test="${listUserBo.currentPage gt 1}">
								<li><a href="create-user-roles?page=1"><span><i
											class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
								<li><a
									href="create-user-roles?page=${listUserBo.currentPage - 1}&searchElement=${searchElement}"><span><i
											class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
							</c:if>
							<c:forEach items="${listUserBo.noOfPages}" var="i">
								<c:choose>
									<c:when test="${listUserBo.currentPage == i}">

										<li class="active"><a
											style="color: #fff; background-color: #34495e">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="create-user-roles?page=${i}&searchElement=${searchElement}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${listUserBo.currentPage lt listUserBo.totalPages}">
								<li><a
									href="create-user-roles?page=${listUserBo.currentPage + 1}&searchElement=${searchElement}"><span><i
											class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
								<li><a
									href="create-user-roles?page=${listUserBo.lastRecordValue}&searchElement=${searchElement}"><span><i
											class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
							</c:if>
						</ul>
						</nav>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>