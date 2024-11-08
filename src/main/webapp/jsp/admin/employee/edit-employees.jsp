<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
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
			$('#btnsubmit').click(function(e) {
				var isValid = true;
				var name = $('#nameId').val();
				if (name == '') {
					isValid = false;
					$("#nameErr").show();
					$("#nameErr").html("Please enter mobile number");
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
				//mobile number
				var isValid = true;
				var mobileNo = $('#mobileNoId').val();
				if (mobileNo == '') {
					isValid = false;
					$("#mobileNoErr").show();
					$("#mobileNoErr").html("Please enter mobile number");
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
					//email
					                               var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
													var emailAddress = $(
															"#emailAddressId").val();
													if (emailAddress == '') {
	
														isValid = false;
														$("#emailAddressErr").show();
														$("#emailAddressErr")
																.html(
																		"Please Enter Email")
														$("#emailAddressId")
																.css(
																		{
																			"border" : "1px solid red",
																		});
													} else if (!emailValidations
															.test(emailAddress)) {
														$("#emailAddressErr").show();
														$("#emailAddressErr")
																(
																		"Please Enter Valid Email ");
														isValid = false;
													} else {
														$("#emailAddressErr").hide();
														$("#emailAddressErr").css({
															"border" : "",
															"background" : ""
														});
													}
													//password
													var isValid = true;
													var password = $('#passwordId').val();
													if (password == '') {
														isValid = false;
														$("#passwordErr").show();
														$("#passwordErr").html("Please enter password");
														$("#passwordId").css({
															"border" : "1px solid red",

														});

													} else {
														$('#passwordErr').hide();
														$('#passwordId').css({

															"border" : "",
															"background" : ""
														});
													}		
													//confirm password
													var isValid = true;
													var confirmPassword = $('#confirmPasswordId').val();
													if (confirmPassword == '') {
														isValid = false;
														$("#confirmPasswordErr").show();
														$("#confirmPasswordErr").html("Please enter confirm password");
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
									
				if (isValid == false)
					e.preventDefault();
			});
		});

	</script>

	<script>
		$(function() {
			$("#startDateInput").datepicker({
				changeMonth : true,
				changeYear : true,
				numberOfMonths : 1,
				onSelect : function(selected) {
					var dt = new Date(selected);
					dt.setDate(dt.getDate());
					$("#endDateInput").datepicker("option", "minDate", dt);
				}
			});
			$("#endDateInput").datepicker({
				changeMonth : true,
				changeYear : true,
				numberOfMonths : 1,
				onSelect : function(selected) {
					var dt = new Date(selected);
					var dt2 = new Date(selected);
					dt.setDate(dt.getDate() - 1);
					dt2.setDate(dt.getDate() + 1);
				}
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
                                                var infoMessage = 'Please select atleast one checkbox.';

                                                var $infoAlert = $('<div class="alert alert-info" id="infoAlert">'
                                                        + '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'
                                                        + '<strong>Info!</strong> <span id="infoMessage"></span>'
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
								}
							}
						});
			}
		};
	</script>
	<script type="text/javascript">
		function emailAddressCheck() {
			var emailAdress = document.getElementById("emailAddress").value;
			document.getElementById("btnsubmit").disabled = false;
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
									document.getElementById("btnsubmit").disabled = true;
									$("#emailAddressIdErr").show();
									$("#emailAddress").css({
										"border" : "1px solid red",
									});
								} else {
									$("#emailAddressIdErr").hide();
								}
							}
						});
			}
		};
	</script>
<div id="infoAlertContainer"></div>
	<div class="warning">
		<div class="box-list">
			<div class="item">
				<div class="row ">
					<div class="text-center underline">
						<h3>Edit Employees</h3>
					</div>
					<br>
					<c:if test="${functionType eq 'edit'}">

						<form:form id="userForm" method="POST" modelAttribute="editUser"
							action="edit-employees">
							<form:hidden path="userId" />
							<div class="col-sm-12">
								<div class="col-sm-4">
									<div class="form-group">
										<label path="Name"> Name <span
											class="font10 text-danger">*</span></label>
										<form:input path="name" id="nameId" type="text"
											class="form-control required" maxlength="20" />
									<form:errors path="name" class="input_error" />
									<div id="nameErr" style="color: red;"></div>
									</div>
								</div>


								<div class="col-sm-4">
									<div class="form-group">
										<label path="EmailAddress">EmailAddress <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" path="emailAddress" id="emailAddressId"
											class="form-control required" />
										<form:errors path="emailAddress" class="error" />
									<div id="emailAddressErr" style="color: red;"></div>
									</div>
								</div>

								<div class="col-sm-4">
									<div class="form-group">
										<label path="Mobile No"> Mobile No <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" path="mobileNo" id="mobileNoId"
											class="form-control required" maxlength="10" onchange="mobileNoCheck()"/>
									<form:errors path="mobileNo" class="error" />
									<div id="mobileNoErr" style="color: red;"></div>
									</div>

								</div>
							</div>
				</div>
				<div class="col-sm-12">
					<div class="col-sm-4">
						<div class="form-group">
							<label path="password"> password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="password" id="passwordId"
								class="form-control required" maxlength="8" />
							<form:errors path="password" class="input_error" />
							<div id="passwordErr" style="color: red;"></div>
						</div>
					</div>

					
					<form:errors path="userType" class="input_error" />
					<div class="col-sm-4">
						<div class="form-group">
							<label path=" Confirm password"> Confirm password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="confirmPassword" id="confirmPasswordId"
								class="form-control required" maxlength="8"/>
							<form:errors path="confirmPassword" class="input_error" />
							<div id="confirmPasswordErr" style="color: red;"></div>
						</div>
					</div>
					<div class="col-sm-4">
								<div class="form-group">
									<label>Product Handled<span class="font10 text-danger">*</span></label>
									<form:select type="text" path="serviceName" id="proId"
										class="form-control required">
										<%-- <form:option value="${serviceId}">${serviceName}</form:option>
										<form:option value="Select ">-- Select --   </form:option> --%>
										<form:options items="${productListObj}"
											itemLabel="serviceName" itemValue="serviceId" />
									</form:select>
									<form:errors path="serviceName"   class="error"/>
										<div id="proErr" style= "color : red;" ></div>
								</div>
							</div>
				</div>
				 <div class="col-sm-12">
				<div class="form-group">
								<label>Skills<span class="font10 text-danger">*</span></label>
								
								<form:checkboxes path="skillsIds" items="${SkillsListBO}"
										itemLabel="descriptions" itemValue="skillsId" />

								
								
								<%-- <form:errors path="primarySkill" class="error" />
								<div id="descriptionsErr" style="color: red;"></div> --%>
							</div>
				 
				</div>
				
				

				<div style="text-align: right; margin-right: 31px">
					<div class="form-group">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20 ">Update</button>
						<a href="view-employees?page=1"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
				</div>
				</form:form>
				</c:if>
			</div>
		</div>
	</div>
</div>
</div>
<br>