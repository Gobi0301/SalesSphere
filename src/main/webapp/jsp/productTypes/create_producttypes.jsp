<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<!-- <link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div class="row scrollspy-sidenav pb-20 body-mt-15">
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
	</div>

	<script>
		$(document).ready(function() {

			$('#id').focus();

			$('#btnSubmit').click(function(e) {
				
				//Product Type
				var isValid = true;
				var productTypes = $('#id').val();
				if (productTypes == '') {
					isValid = false;
					$("#productTypesErr").show();
					$("#productTypesErr").html("Please Enter Product Type ");
					$("#id").css({
						"border" : "1px solid red",
					});
				} else if (!/^[a-zA-Z\s]*$/g.test(supplierName)) {
					$("#productTypesErr").show();
					$("#productTypesErr").html("Please Enter Character Only");
					isValid = false;
				} else {
					$('#productTypesErr').hide();
					$('#id').css({
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
	function roleCheck() {
		var productTypes = document.getElementById("id").value;
		document.getElementById("btnSubmit").disabled = false;
		if (productTypes != '') {
			$.ajax({
				url : "check_productTypes",
				type : "GET",
				data : 'productTypes=' + productTypes,
				success : function(result) {

					if (result == true) {
						$("#productTypesErr").html("ProductType Already Exists");
						document.getElementById("btnSubmit").disabled = true;
						$("#productTypesErr").show();
					} else {
						$("#productTypesErr").hide();
					}
				}
			});
		}
	};
</script>

	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
				<div class="row ">

					<div class="text-center underline">
						<h3>Create ProductType</h3>
					</div>
					<br>

					<form:form method="POST" id="addForm" action="create_producttypes"
						modelAttribute="productTypes">
						<div class="col-sm-12">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Product Types<span class="font10 text-danger">*</span></label>
									<form:input id="id" type="text" path="productTypes"
										class="form-control required" placeholder="Product Types"
										maxlength="150" onchange="roleCheck()" />
									<form:errors path="productTypes" Class="error" />
									<div id="productTypesErr" style="color: red;"></div>
								</div>
							</div>
						</div>

						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnSubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href="view_producttypes"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</form:form>


				</div>
			</div>
		</div>
	</div>
</body>
</html>