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
<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>
<div class="row scrollspy-sidenav pb-20 body-mt-15">
	<script>
		$(document).ready(function() {
			$('#btnsubmit').click(function(e) {
				var isValid = true;

				var firstname = $('#firstnameId').val();
				if (firstname == '') {
					isValid = false;
					$("#firstnameErr").show();
					$("#firstnameErr").html("Please enter firstname");
					$("#firstnameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#firstnameErr').hide();
					$('#firstnameId').css({

						"border" : "",
						"background" : ""
					});
				}

				var lastname = $('#lastnameId').val();
				if (lastname == '') {
					isValid = false;
					$("#lastnameErr").show();
					$("#lastnameErr").html("Please enter lastname");
					$("#lastnameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#lastnameErr').hide();
					$('#lastnameId').css({

						"border" : "",
						"background" : ""
					});
				}

				var emailId = $('#emailId').val();
				if (emailId == '') {
					isValid = false;
					$("#emailErr").show();
					$("#emailErr").html("Please enter email");
					$("#emailId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#emailErr').hide();
					$('#emailId').css({

						"border" : "",
						"background" : ""
					});
				}

				var city = $('#cityId').val();
				if (city == '') {
					isValid = false;
					$("#cityErr").show();
					$("#cityErr").html("Please enter city");
					$("#cityId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#cityErr').hide();
					$('#cityId').css({

						"border" : "",
						"background" : ""
					});
				}
				var country = $('#countryId').val();
				if (country == '') {
					isValid = false;
					$("#countryErr").show();
					$("#countryErr").html("Please enter country");
					$("#countryId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#countryErr').hide();
					$('#countryId').css({

						"border" : "",
						"background" : ""
					});
				}

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
						<h3>Edit Contact</h3>

					</div>
					<br>

					<form:form method="POST" id="addForm" action="edit-contact"
						modelAttribute="contacto">

						<h3 class="title">contact Information</h3>

						<div class="box-list">
							<div class="item">
								<div class="row ">
									<form:hidden path="contactId" name="id"
										value="${contacto.contactId}" />
									<div class="col-sm-12">

										<div class="col-sm-4">
											<div class="form-group">
												<label>Salutation<span class="font10 text-danger">*</span></label>
												<form:select type="text" path="salutation"
													class="form-control required">
													<form:option value="Select">-- Select --   </form:option>
													<form:option value="Mrs"> Mrs  </form:option>
													<form:option value="Ms"> Ms    </form:option>
													<form:option value="Mr"> Mr  </form:option>

												</form:select>
											</div>
										</div>




										<div class="col-sm-4">
											<div class="form-group">
												<label>First Name <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="firstname" id="firstnameId"
													class="form-control required" placeholder="firstname" />
												<form:errors path="firstname" class="error" />
												<div id="firstnameErr" style="color: red;"></div>

											</div>
										</div>


										<div class="col-sm-4">
											<div class="form-group">
												<label>Last Name <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="lastname" id="lastnameId"
													class="form-control required" placeholder="lastname" />
												<form:errors path="lastname" class="error" />
												<div id="lastnameErr" style="color: red;"></div>

											</div>
										</div>




									</div>

									<div class="col-sm-12">

										<div class="col-sm-4">
											<div class="form-group">
												<label>Assigned To<span class="font10 text-danger">*</span></label>
												<form:select type="text" path="assignedTo.id"
													class="form-control required">
													<%-- <form:option value="">-- Select --   </form:option> --%>
													<form:options items="${userBOList}" itemLabel="name"
														itemValue="id" />

												</form:select>
											</div>
										</div>


										<div class="col-sm-4">
											<div class="form-group">
												<label>Lead Source<span class="font10 text-danger">*</span></label>
												<form:select type="text" path="opportunityBO.firstName"
													class="form-control required">
													<%-- <form:option value="Select">-- Select --   </form:option> --%>
													<form:options items="${opportunitylist}" itemLabel="firstName"
														itemValue="opportunityId" />

												</form:select>
											</div>
										</div>


										<div class="col-sm-4">
											<div class="form-group">
												<label>Email<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="email" id="emailId"
													class="form-control required" placeholder="email" />
												<form:errors path="email" class="error" />
												<div id="emailErr" style="color: red;"></div>

											</div>
										</div>




									</div>
									<div class="col-sm-12">



										<div class="col-sm-4">
											<div class="form-group">
												<label>Account Name<span class="font10 text-danger">*</span></label>
												<form:select type="text" path="accountBO.accountId"
													class="form-control required">
													<form:option value="">-- Select --   </form:option>
													<form:options items="${accountlist}" />


												</form:select>
											</div>
										</div>



										<div class="col-sm-4">
											<div class="form-group">
												<label>Phone Number<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="phone" id="phoneId"
													class="form-control required" placeholder="phone"
													maxlength="10" />
												<form:errors path="phone" class="error" />
												<div id="phoneErr" style="color: red;"></div>

											</div>
										</div>

									</div>

								</div>
							</div>
						</div>

						<h3 class="title">Address Information</h3>


						<div class="box-list">
							<div class="item">
								<div class="row ">

									<div class="col-sm-12">

										<div class="col-sm-4">
											<div class="form-group">
												<label>Street<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="street" id="streetId"
													class="form-control required" placeholder="street" />
												<form:errors path="street" class="error" />
												<div id="streetErr" style="color: red;"></div>

											</div>
										</div>


										<div class="col-sm-4">
											<div class="form-group">
												<label>City<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="city" id="cityId"
													class="form-control required" placeholder="city" />
												<form:errors path="city" class="error" />
												<div id="cityErr" style="color: red;"></div>

											</div>
										</div>


										<div class="col-sm-4">
											<div class="form-group">
												<label>State<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="state" id="stateId"
													class="form-control required" placeholder="state" />
												<form:errors path="state" class="error" />
												<div id="stateErr" style="color: red;"></div>

											</div>
										</div>

									</div>
									<div class="col-sm-12">

										<div class="col-sm-4">
											<div class="form-group">
												<label>Country<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="country" id="countryId"
													class="form-control required" placeholder="country" />
												<form:errors path="country" class="error" />
												<div id="countryErr" style="color: red;"></div>

											</div>
										</div>


									</div>

								</div>
							</div>
						</div>
				</div>

				<div style="text-align: right; margin-right: 31px">
					<button type="submit" id="btnsubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
					<a href="view-contact"><span
						class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
				</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</div>
<br>