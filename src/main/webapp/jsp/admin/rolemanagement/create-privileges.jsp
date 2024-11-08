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

<script>
	$(document).ready(function() {

		$('#name').focus();

		$('#btnSubmit').click(function(e) {
			var isValid = true;
			var userName = $('#name').val();
			if (userName == '') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter privilege name");
				$("#name").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z_\s&]*$/g.test(userName)) {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter characters only");
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

<script type="text/javascript">
	function privilegeNameCheck() {
		var privilegename = document.getElementById("name").value;
		 document.getElementById("btnSubmit").disabled = false; 
		if (privilegename != '') {
			$.ajax({
				url : "check_privilege",
				type : "GET",
				data : 'privilegename=' + privilegename,
				success : function(result) {

					if (result == true) {
						$("#nameErr").html("Privileges Already Exists");
						document.getElementById("btnSubmit").disabled = true; 
						$("#nameErr").show();
						$("#name").css({
							"border" : "1px solid red",
						});
						 
					} else {
						$("#nameErr").hide();
						$("#name").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>

<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;
			var privilegename = $('#privilegenameId').val();
			if (privilegename == '') {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please enter privilege name ");
				$("#privilegenameId").css({
					"border" : "1px solid red",

				});

			}  else {
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="row" style="margin-top: 25px;">
		<div class="align-margin">
			<div class="box-list">
				<div class="item">
					<div class="text-center underline">
						<h3 style="text-decoration: underline; margin-top: 25px; border-bottom: 20px;">Create Privileges</h3>
					</div>
					<br />
					<c:if test="${not empty successMessage}">
							<div class="alert alert-success" role="alert"
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
							<div class="alert alert-success">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong>Success:</strong>
								<c:out value="${errorMessage}"></c:out>
							</div>
						</c:if>
						<c:if test="${not empty infoMessagemessage}">
							<div class="alert alert-info" role="alert"
								style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<strong>Info!</strong>
								<c:out value="${infoMessagemessage}"></c:out>
							</div>
						</c:if>
					<div>
						<form:form method="POST" id="addForm" action="create-privileges"
							modelAttribute="privileges">
							
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label>Privilege Name <span class="font10 text-danger">
												*</span></label>
										<form:input  type="text" path="privilegename" id="name"  placeholder="Privileges Name"
											class="form-control" onchange="privilegeNameCheck()"/>
										<form:errors path="privilegename" cssClass="error" />
											<div id="nameErr" style="color: red;"></div>

									</div>
								</div>
							</div>
							<div class="row">

								<!-- <div class="col-sm-1">
									<button type="submit" id="btnsubmit" onclick="privilegeNameCheck()
										class="btn btn-t-primary btn-theme lebal_align">Submit</button>
								</div> -->  
								<div class="col-sm-1">
							         <button type="submit" id="btnSubmit"
								class="btn btn-t-primary btn-theme lebal_align">Submit</button>
						</div>
								<div class="col-sm-1">
									<a href="view-privileges"><span
										class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
								</div>
							</div>
						</form:form>
						<br>
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>