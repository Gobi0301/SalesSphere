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
<script type="text/javascript">
	function roleCheck() {
		var productTypes = document.getElementById("productTypesIds").value;
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
	<script>
		$(document)
				.ready(
						function() {
							$('#btnSubmit')
									.click(
											function(e) {
				
				//Product Type
				 //var isValid = true;
				var productType = $('#productTypesIds').val();
				if (productType == '') {
					isValid = false;
					$("#productTypesErr").show();
					$("#productTypesErr").html("Please Enter Product Type ");
					$("#productTypesIds").css({
						"border" : "1px solid red",
					});
				} else if (!/^[a-zA-Z\s]*$/g.test(productType)) {
					$("#productTypesErr").show();
					$("#productTypesErr").html("Please Enter Character Only");
					isValid = false;
				} else {
					$('#productTypesErr').hide();
					$('#productTypesIds').css({
						"border" : "",
						"background" : ""
					});
				} 

/* 
		//Product Type
				var product= $('#productTypesIds').val();

				if (product== '') {
					isValid = false;
					$("#productTypesErr").show();
					$("#productTypesErr").html("Please Enter Product Type");
					$("#productTypesIds").css({
						"border" : "1px solid red",
					});
				} else {
					$('#productTypesErr').hide();
					$('#productTypesIds').css({
						"border" : "",
						"background" : ""
					});
				} */

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script>


												
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

 

	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
				<div class="row ">

					<div class="text-center underline">
						<h3>Edit ProductType</h3>
					</div>
					<br>

					<form:form method="POST" action="update_producttypes"
						modelAttribute="productTypeBo">
						<form:hidden path="productTypesId" />
						<div class="col-sm-12">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Product Types<span class="font10 text-danger">*</span></label>
									<form:input type="text" path="productTypes" id="productTypesIds"
										class="form-control required" placeholder="Product Types"
										maxlength="150" onchange="roleCheck()"/>
									<form:errors path="productTypes" Class="error" />
									<div id="productTypesErr" style="color: red;"></div>
								</div>
							</div>
						</div>

						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnSubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href=view_producttypes?page=1><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</form:form>


				</div>
			</div>
		</div>
	</div>
</body>
</html> 