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
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>


<div class="row scrollspy-sidenav pb-20 body-mt-15">
	<script>
		$(document).ready(function() {

			$('#name').focus();

			$('#btnsubmit').click(function(e) {
				var isValid = true;
				var name = $('#name').val();

				if (name == '') {
				    isValid = false;
				    $("#nameErr").show();
				    $("#nameErr").html("Please enter employee name");
				    $("#name").css({
				        "border": "1px solid red",
				    });
				} else if (!/^[a-zA-Z\s]*$/.test(name)) {
				    isValid = false;
				    $("#nameErr").show();
				    $("#nameErr").html("Please enter only alphabetic characters");
				    $("#name").css({
				        "border": "1px solid red",
				    });
				} else {
				    $('#nameErr').hide();
				    $('#name').css({
				        "border": "",
				        "background": ""
				    });
				}
				var emailAddress = $('#emailAddress').val();
				if (emailAddress == '') {
					isValid = false;
					$("#emailAddressIdErr").show();
					$("#emailAddressIdErr").html("Please enter emailaddress");
					$("#emailAddress").css({
						"border" : "1px solid red",

					});

				} else {
					$('#emailAddressIdErr').hide();
					$('#emailAddress').css({

						"border" : "",
						"background" : ""
					});
				}

				
			 	var password = $('#passwordId').val();
				if (password == '') {
					isValid = false;
					$("#passwordIdErr").show();
					$("#passwordIdErr").html("Please enter password");
					$("#passwordId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#passwordIdErr').hide();
					$('#passwordId').css({

						"border" : "",
						"background" : ""
					});
				}

				 
				var confirmPassword = $('#confirmPasswordId').val();
				if (confirmPassword == '') {
					isValid = false;
					$("#confirmPasswordErr").show();
					$("#confirmPasswordErr").html("Please enter confirm Password");
					$("#confirmPasswordId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#confirmPasswordErr').hide();
					$('#confirmPasswordId').css({

						"border" : "",
						"background" : ""
					});
				}
				var password = $("#passwordId").val();
	            var confirmPassword = $("#confirmPasswordId").val();
	            if (password != confirmPassword) {
	                alert("Passwords do not match.");
	                isValid = false;
	            }
			
				var mobileNo = $('#mobileNoId').val();
				if (mobileNo == '') {
				    isValid = false;
				    $("#mobileNoErr").show();
				    $("#mobileNoErr").html("Please enter mobile number");
				    $("#mobileNoId").css({
				        "border": "1px solid red",
				    });
				}else if (!/^[0-9]{1,10}$/.test(mobileNo)) {
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

				 
				var productId = $('#productId').val();
				if (productId == '') {
					isValid = false;
					$("#productErr").show();
					$("#productErr").html("Pleaseselect product");
					$("#productId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#productErr').hide();
					$('#productId').css({

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
    $(document)
            .ready(
                    function() {
                        var $infoAlertContainer = $('#infoAlertContainer');

                        $(document)
                                .on(
                                        'click',
                                        '#btnsubmit',
                                        function(e) {
                                            // Remove the previous alert if it exists
                                            $infoAlertContainer.empty();

                                            var checkedCheckboxes = $(':checkbox:checked').length;

                                            if (checkedCheckboxes === 0) {
                                                var infoMessage = 'Please select atleast one skill checkbox.';

                                                var $infoAlert = $('<div class="alert alert-info" id="infoAlert">'
                                                        + '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'
                                                        + '<strong>Info!</strong> <span id="="></span>'
                                                        + '</div>');

                                                $infoAlert.find('#infoMessage')
                                                        .text(infoMessage);
                                                $infoAlertContainer
                                                        .append($infoAlert); 

                                                $('html, body').animate({
                                                    scrollTop : 0
                                                }, 'slow');

                                                e.preventDefault();
                                            }
                                        });
                    });
    </script>
	<div id="infoAlertContainer"></div>

	<script type="text/javascript">
		function emailAddressCheck() {
			var emailAdress = document.getElementById("emailAddress").value;
			//sdocument.getElementById("btnsubmit").disabled = false;
			if (emailAdress != '') {
				$
						.ajax({
							
							url : "check_emailAddress",
							type : "GET",
							data : 'emailAddress=' + emailAdress,
							success : function(result) {

								if (result == true) {
									$("#emailAddressIdErr").html(
											"EmailAddress Already Exists");
									//document.getElementById("btnsubmit").disabled = true;
									$("#emailAddressIdErr").show();
									$("#emailAddress").css({
										"border" : "1px solid red",
									});
								} else {
									$("#emailAddressIdErr").hide();
									$("#emailAddress").css({
										"border" : "",
										"background" : ""
									});
								}
							}
						});
			}
		};
	</script>

	<script type="text/javascript">
		function mobileNoCheck() {
			var mobileNo = document.getElementById("mobileNoId").value;
			document.getElementById("btnsubmit").disabled = false;
			if (mobileNo != '') {
				$
						.ajax({
							url : "check_mobileNo",
							type : "GET",
							data : 'mobileNo=' + mobileNo,
							success : function(result) {

								if (result == true) {
									$("#mobileNoErr").html(
											"MobileNo Already Exists");
									document.getElementById("btnsubmit").disabled = true;
									$("#mobileNoErr").show();
									$("#mobileNoId").css({
										"border" : "1px solid red",
									});
								} else {
									$("#mobileNoErr").hide();
									$("#mobileNoId").css({
										"border" : "",
										"background" : ""
									});
								}
							}
						});
			}
		};
	</script>

	<script type="text/javascript">
    $(function() {
        $("#btnSubmit").click(function() {
            var password = $("#passwordId").val();
            var confirmPassword = $("#confirmPasswordId").val();
            if (password != confirmPassword) {
                alert("Passwords do not match.");
                return false;
            }
            return true;
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
		<c:if test="${not empty errorMobileMessage}">
			<div class="alert alert-info" role="alert"
				style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Info!</strong>
				<c:out value="${errorMobileMessage}"></c:out>
			</div>
		</c:if>
	</div>

	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
			<c:if test="${functionType eq 'add'}">
			<form:form method="POST" id="addForm" action="create-employees"
							modelAttribute="user">
				<div class="row ">
						<div class="text-center underline">
							<h3>Create Employee</h3>
						</div>
						<br>
							
								<div class="col-sm-4">
									<div class="form-group">
										<label path="Name"> Name <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="name" path="name"
											class="form-control required" placeholder="Name"
											maxlength="150" />
										<form:errors path="name" class="error" />
										<div id="nameErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="EmailAddress">Email Address <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" path="emailAddress" id="emailAddress"
											class="form-control required" placeholder="EmailAddress"
											onchange="emailAddressCheck()" />
										<form:errors path="emailAddress" class="error" />
										<div id="emailAddressIdErr" style="color: red;"></div>
									</div>
								</div>

								<div class="col-sm-4">
									<div class="form-group">
										<label path="Mobile No"> Mobile No <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" path="mobileNo" id="mobileNoId"
											class="form-control required" placeholder="Mobileno"
											maxlength="10" onchange="mobileNoCheck()" />
										<form:errors path="mobileNo" class="error" />
										<div id="mobileNoErr" style="color: red;"></div>
									</div>
								</div>
							</div>
				
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label path="password"> Password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="password" id="passwordId"
								class="form-control required" placeholder="password"
								maxlength="8" />
							<form:errors path="password" class="error" />
							<div id="passwordIdErr" style="color: red;"></div>
						</div>
					</div>


					<form:errors path="userType" class="input_error" />
					<div class="col-sm-4">
						<div class="form-group">
							<label path="Confirm password"> Confirm password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="confirmPassword"
								id="confirmPasswordId" class="form-control required"
								placeholder="confirm password" maxlength="8" />
							<form:errors path="confirmPassword" class="error" />
							<div id="confirmPasswordErr" style="color: red;"></div>

						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label>Product Handled<span class="font10 text-danger">*</span></label>
							<form:select type="text" path="serviceName" id="productId"
								class="form-control required">
								<form:option value="">-- Select --   </form:option>
								<form:options items="${productListObj}" itemLabel="serviceName"
									itemValue="serviceId" />
							</form:select>
							<form:errors path="serviceName" class="error" />
							<div id="productErr" style="color: red;"></div>
						</div>
					</div>
				</div>
				<div class="row">
<div class="col-sm-12">

					<div class="form-group">
						<%-- <label>Skills<span class="font10 text-danger">*</span></label>
								
								<form:checkboxes items="${slaList}" path="skillsBO.descriptions" itemLabel="descriptions" itemValue="skillsId"/>
								
								
								<form:errors path="skillsBO.descriptions" class="error" />
								<div id="descriptionsErr" style="color: red;"></div>
							</div> --%>
						<label style="margin-left: 5px;">Skills <span
							class="font10 text-danger">*</span></label>
						<form:checkboxes items="${slaList}" itemLabel="descriptions"
							itemValue="skillsId" path="skillsBO.descriptions" />

					</div>
					</div>
					</div>
					<br>
					<br>
					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</button>
							<a href="view-employees"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</div>
					</form:form>
					</c:if>
				</div>
			</div>
		</div>
	</div>
<br>
<br>
<br>
<br>
<br>
<br>

