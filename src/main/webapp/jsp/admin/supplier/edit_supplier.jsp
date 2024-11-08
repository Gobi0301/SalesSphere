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
</style>
	<script type="text/javascript">
	function supplieremailcheck() {
		var emails = document.getElementById("emailAddressId").value;
		document.getElementById("btnsubmit").disabled = false;
		if (emails != '') {
			$.ajax({
				url : "check_supplieremail",
				type : "GET",
				data : 'emails=' + emails,
				success : function(result) {

					if (result == true) {
						$("#emailAddressIdErr").html("Email Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#emailAddressIdErr").show();
						$("#emailAddressId").css({
							"border" : "1px solid red",
						});

					} else {
						$("#emailAddressIdErr").hide();
						$("#emailAddressId").css({
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

		$('#emailAddressId').focus();

		$('#btnsubmit').click(function(e) {

		//Email Address
			//var isValid = true;
			var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			var emailAddress = $('#emailAddressId').val();
			if (emailAddress == '') {
				isValid = false;
				$("#emailAddressIdErr").show();
				$("#emailAddressIdErr").html("Please enter Email Address");
				$("#emailAddressId").css({
					"border" : "1px solid red",
				});
			}else if (!emailValidations
					.test(emailAddress)) {
				$("#emailAddressIdErr").show();
				$("#emailAddressIdErr").html("Please Enter Valid Email ");
				$("#emailAddressId").css({
					"border" : "1px solid red",
				});
				isValid = false;
			} else {
				$('#emailAddressIdErr').hide();
				$('#emailAddressId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Supplier Name
			//var isValid = true;
			var supplierName = $('#name').val();
			if (supplierName == '') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter Supplier Name");
				$("#name").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z\s]*$/g
					.test(supplierName)) {
				$("#nameErr").show();
				$("#nameErr").html("Please Enter Character Only");
				$("#name").css({
					"border" : "1px solid red",

				});
				isValid = false;
			} else {
				$('#nameErr').hide();
				$('#name').css({
					"border" : "",
					"background" : ""
				});
			}

			//Mobile Number
			var mobileNo = $('#mobileNoId').val();
				  if (!/^[0-9]{1,10}$/.test(mobileNo)) {
					isValid = false;
					$("#mobileNoErr").show();
					$("#mobileNoErr").html("Please enter  a number only");
					$("#mobileNoId").css({
						"border" : "1px solid red",

					});

				} else if (mobileNo.length < 10) {
                    isValid = false;
                    $("#mobileNoErr").show();
                    $("#mobileNoErr").html("Must enter 10 Digits Numbers");
                    $("#mobileNoId").css({
                        "border" : "1px solid red",
                    });
                } else {
					$('#mobileNoErr').hide();
					$('#mobileNoId').css({

						"border" : "",
						"background" : ""
					});
				}


			
			//Address
			//var isValid = true;
			var address = $('#addressId').val();
			if (address == '') {
				isValid = false;
				$("#addressErr").show();
				$("#addressErr").html("Please enter Address");
				$("#addressId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#addressErr').hide();
				$('#addressId').css({
					"border" : "",
					"background" : ""
				});
			}

			//City
			//var isValid = true;
			var city = $('#cityId').val();
			if (city == '') {
				isValid = false;
				$("#cityErr").show();
				$("#cityErr").html("Please enter City");
				$("#cityId").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z\s]*$/g.test(city)) {
				$("#cityErr").show();
				$("#cityErr").html("Please Enter Character Only");
				$("#cityId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			}  else {
				$('#cityErr').hide();
				$('#cityId').css({
					"border" : "",
					"background" : ""
				});
			}
			//State
			//var isValid = true;
			var state = $('#stateId').val();
			if (state == '') {
				isValid = false;
				$("#stateErr").show();
				$("#stateErr").html("Please enter State");
				$("#stateId").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z\s]*$/g.test(state)) {
				$("#stateErr").show();
				$("#stateErr").html("Please Enter Character Only");
				$("#stateId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#stateErr').hide();
				$('#stateId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Country
			//var isValid = true;
			var country = $('#countryId').val();
			if (country == '') {
				isValid = false;
				$("#countryErr").show();
				$("#countryErr").html("Please enter Country");
				$("#countryId").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z\s]*$/g.test(country)) {
				$("#countryErr").show();
				$("#countryErr").html("Please Enter Character Only");
				$("#countryId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#countryErr').hide();
				$('#countryId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Location
		//	var isValid = true;
			var location = $('#locationId').val();
			if (location == '') {
				isValid = false;
				$("#locationErr").show();
				$("#locationErr").html("Please enter Location");
				$("#locationId").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z\s]*$/g.test(location)) {
				$("#locationErr").show();
				$("#locationErr").html("Please Enter Character Only");
				$("#locationId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#locationErr').hide();
				$('#locationId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Website
			//var isValid = true;
			var siteRegex = /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/
			var website = $('#websiteId').val();
			if (website == '') {
				isValid = false;
				$("#websiteErr").show();
				$("#websiteErr").html("Please enter Website");
				$("#websiteId").css({
					"border" : "1px solid red",
				});
			}else if (!siteRegex
					.test(website)) {
				$("#websiteErr").show();
				$("#websiteErr").html("Please Enter Valid WebSite ");
				$("#websiteId").css({
					"border" : "1px solid red",
				});
				isValid = false;
			}
			 else {
				$('#websiteErr').hide();
				$('#websiteId').css({
					"border" : "",
					"background" : ""
				});
			}

			 //Product Name
			//var isValid = true;
			/* var serviceName = $('#serviceid').val();
			if (serviceName == '') {
				isValid = false;
				$("#serviceNameErr").show();
				$("#serviceNameErr").html("Please Select Product Name");
				$("#serviceid").css({
					"border" : "1px solid red",
				});
			} else {
				$('#serviceNameErr').hide();
				$('#serviceid').css({
					"border" : "",
					"background" : ""
				});
			} 

			//Technical Oriented
			//var isValid = true;
			var techOriented = $('#techOrientedId').val();
			if (techOriented == '') {
				isValid = false;
				$("#techOrientedErr").show();
				$("#techOrientedErr").html("Please enter Technical Oriented");
				$("#techOrientedId").css({
					"border" : "1px solid red",
				});
			} else if (/^[a-zA-Z@\s]*$@/g.test(techOriented)) {
				$("#techOrientedErr").show();
				$("#techOrientedErr").html("Please Enter Character Only");
				$("#techOrientedId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#techOrientedErr').hide();
				$('#techOrientedId').css({
					"border" : "",
					"background" : ""
				});
			}
        //Financialamount
   			var financialAmount = $('#financialAmountId').val();
			if (financialAmount == '0.0') {
				isValid = false;
				$("#financialAmountErr").show();
				$("#financialAmountErr").html("Please enter Financial Amount");
				$("#financialAmountId").css({
					"border" : "1px solid red",
				});
			} else if (!/^[0-9].{1,10}$/.test(financialAmount)) {
				$("#financialAmountErr").show();
				$("#financialAmountErr").html("Please Enter number Only");
				$("#financialAmountId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#financialAmountErr').hide();
				$('#financialAmountId').css({
					"border" : "",
					"background" : ""
				});
			}
      //Rating
      $(document).ready(function() {
    var rating = $('#ratingId').val();
    if (rating === 'N/A' || rating === '') {
        isValid = false;
        $("#ratingErr").show().html("Please select a rating.");
        $("#ratingId").css("border", "1px solid red");
    } else {
        $('#ratingErr').hide();
        $('#ratingId').css("border", "");
    }
});
       */
					
			if (isValid == false)
				e.preventDefault();
		});
	});
</script>
<div class="warning">

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
					<h3>Edit Supplier</h3>
				</div>

				<form:form method="POST" id="addForm" action="edit_supplier"
					modelAttribute="supplierBo">
					<form:hidden path="supplierId" name="id"
						value="${supplierBo.supplierId}" />
					<div class="item">
						<div class="row">
							<h3 class="title">Supplier Information</h3> 
							<div class="col-sm-12">
								<div class="col-sm-4">
									<div class="form-group">
										<label> Supplier Name <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="name" path="supplierName"
											class="form-control required" placeholder="supplierName"
											maxlength="150" />
										<form:errors path="supplierName" class="error" />
										<div id="nameErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>Email Address <span class="font10 text-danger">*</span></label>
										<form:input path="emailId" id="emailAddressId"
											class="form-control required" placeholder="EmailAddress"
											onchange="emailAddressCheck()" />
										<form:errors path="emailId" class="error" />
										<div id="emailAddressIdErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label> Mobile No <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="mobileNo" id="mobileNoId"
											class="form-control required" placeholder="Mobileno"
											maxlength="10" onchange="mobileNoCheck()" />
										<form:errors path="mobileNo" class="error" />
										<div id="mobileNoErr" style="color: red;"></div>
									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="col-sm-4">
									<div class="form-group">
										<label> Address <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="address" id="addressId"
											class="form-control required" placeholder="Address" />
										<form:errors path="address" class="error" />
										<div id="addressErr" style="color: red;"></div>

									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label> City <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="city" id="cityId"
											class="form-control required" placeholder="City" />
										<form:errors path="city" class="error" />
										<div id="cityErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label> State <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="state" id="stateId"
											class="form-control required" placeholder="State" />
										<form:errors path="state" class="error" />
										<div id="stateErr" style="color: red;"></div>
									</div>
								</div>

							</div>
							<div class="col-sm-12">

								<div class="col-sm-4">
									<div class="form-group">
										<label> Country <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="country" id="countryId"
											class="form-control required" placeholder="country" />
										<form:errors path="country" class="error" />
										<div id="countryErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label> Location <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="location" id="locationId"
											class="form-control required" placeholder="location" />
										<form:errors path="location" class="error" />
										<div id="locationErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label> Web Site <span class="font10 text-danger">*</span></label>
										<form:input type="text" path="webSite" id="websiteId"
											class="form-control required" placeholder="website" />
										<form:errors path="webSite" class="error" />
										<div id="websiteErr" style="color: red;"></div>
									</div>
								</div>

							</div>
							
						</div>
						<div style="text-align: right; margin-right: 31px">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
						<a href="view_supplier"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
					</div>

					<!-- <br>
					<br> -->

				<%-- 	<div class="box-list">
						<div class="item">
							<div class="row ">
								<h3 class="title">Product Information</h3> 
								<div class="col-sm-12">

										<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="productServiceBO.serviceName" id="serviceid"
												class="form-control required">
												<form:option value="${supplierBo.productServiceBO.serviceId}">${supplierBo.productServiceBO.serviceName}</form:option>
 												<form:option value="">-- Select --</form:option>
												<form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="productServiceBO.serviceName" class="error" />
											<div id="serviceNameErr" style="color: red;"></div>
							
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="productServiceBO.serviceName"
												class="form-control required" multiple="multiple">
												<c:if test="${not empty supplierBo.productServiceListBO}">

													<form:options items="${supplierBo.productServiceListBO}"
														itemLabel="serviceName" itemValue="serviceId"
														selected="selected" />
												</c:if>
												<form:option value=" ">-- Select --</form:option>
												<form:option value="${supplierBo.getproductServiceBO.serviceName}">$(supplierBo.getproductServiceBO.serviceName)</form:option> 
												<c:forEach items="${productList}" var="serviceName"
													itemValue="serviceId" />
												<form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> Tech Oriented <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" path="techOriented"
												id="techOrientedId" class="form-control required"
												placeholder="techOriented" />
											<form:errors path="techOriented" class="error" />
											<div id="techOrientedErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> FinancialAmount <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" path="financialAmount"
												id="financialAmountId" class="form-control required"
												placeholder="finanamount" maxlength="12" />
											<form:errors path="financialAmount" class="error" />
											<div id="financialAmountErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label> Rating <span class="font10 text-danger">*</span></label>
											<form:select type="text" path="rating" id="ratingId"
												class="form-control required" placeholder="rating">
												<form:option value="N/A">--Select--</form:option>
													<form:option value="1">*</form:option>
													<form:option value="2">**</form:option>
													<form:option value="3">***</form:option>
													<form:option value="4">****</form:option>
													<form:option value="5">*****</form:option>
													<form:option value="6">N/A</form:option>
												</form:select>
											<form:errors path="rating" class="error" />
											<div id="ratingErr" style="color: red;"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div> --%>
					<br>
					<br>
					<!-- <div style="text-align: right; margin-right: 31px">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
						<a href="view_supplier"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div> -->
				</form:form>
			</div>
		</div>
	</div>
</div>


