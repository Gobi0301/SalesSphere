<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page trimDirectiveWhitespaces="true"%>
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {

			//case
			var isValid = true;
			var caseReason = $('#casesId').val();
			if (caseReason == '') {
				isValid = false;
				$("#caseReasonErr").show();
				$("#caseReasonErr").html("Please enter the Case Reason");
				$("#casesId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#caseReasonErr').hide();
				$('#casesId').css({
					"border" : "",
					"background" : ""
				});
			}
			//email
			var isValid = true;
			var email = $('#CompanyEmailinput').val();
			if (email == '') {
				isValid = false;
				$("#companyEmailErr").show();
				$("#companyEmailErr").html("Please enter Email Address");
				$("#CompanyEmailinput").css({
					"border" : "1px solid red",
				});
			} else {
				$('#companyEmailErr').hide();
				$('#CompanyEmailinput').css({
					"border" : "",
					"background" : ""
				});
			}
			//casesolution

			var casesoln = $('#casesolutionId').val();
			if (casesoln == '') {
				isValid = false;
				$("#casesolutionErr").show();
				$("#casesolutionErr").html("Please enter case solution");
				$("#casesolutionId").css({
					"border" : "1px solid red",
				});

			} else {
				$('#casesolutionErr').hide();
				$('#casesolutionId').css({
					"border" : "",
					"background" : ""
				});
			}

			//productname

			var product = $('#productId').val();
			if (product == 'select') {
				isValid = false;
				$("#productErr").show();
				$("#productErr").html("Please Select Product Name");
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

			//Warranty Date
			var name = $('#warrantyDateId').val();
			if (name == '') {
				isValid = false;
				$("#warrantyDateErr").show();
				$("#warrantyDateErr").html("Please enter the Warranty Date");
				$("#warrantyDateId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#warrantyDateErr').hide();
				$('#warrantyDateId').css({
					"border" : "",
					"background" : ""
				});
			}

			//claiming Date
			var name = $('#claimingDateId').val();
			if (name == '') {
				isValid = false;
				$("#claimingDateErr").show();
				$("#claimingDateErr").html("Please enter the Claiming Date");
				$("#claimingDateId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#claimingDateErr').hide();
				$('#claimingDateId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Expire  Date
			var name = $('#expireDateId').val();
			if (name == '') {
				isValid = false;
				$("#expireDateErr").show();
				$("#expireDateErr").html("Please enter the Expire Date");
				$("#expireDateId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#expireDateErr').hide();
				$('#expireDateId').css({
					"border" : "",
					"background" : ""
				});
			}

			//report

			var report = $('#reportId').val();
			if (report == 'select') {
				isValid = false;
				$("#reportErr").show();
				$("#reportErr").html("Please Select Report");
				$("#reportId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#reportErr').hide();
				$('#reportId').css({
					"border" : "",
					"background" : ""
				});
			}

			//priority

			var priority = $('#priorityId').val();
			if (priority == '') {
				isValid = false;
				$("#priorityErr").show();
				$("#priorityErr").html("Please Enter Priority");
				$("#priorityId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#priorityErr').hide();
				$('#priorityId').css({
					"border" : "",
					"background" : ""
				});
			}

			//status

			var status = $('#statusId').val();
			if (status == 'Select') {
				isValid = false;
				$("#statusErr").show();
				$("#statusErr").html("Please Select Status");
				$("#statusId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#statusErr').hide();
				$('#statusId').css({
					"border" : "",
					"background" : ""
				});
			}

			//description

			var description = $('#descriptionId').val();
			if (description == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please Enter Description");
				$("#descriptionId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionErr').hide();
				$('#descriptionId').css({
					"border" : "",
					"background" : ""
				});
			}

			//description

			var category = $('#categoryId').val();
			if (category == 'Select') {
				isValid = false;
				$("#categoryErr").show();
				$("#categoryErr").html("Please Select Category");
				$("#categoryId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#categoryErr').hide();
				$('#categoryId').css({
					"border" : "",
					"background" : ""
				});
			}

			//caseOriginId

			var caseOrigin = $('#caseOriginId').val();
			if (caseOrigin == 'Select') {
				isValid = false;
				$("#caseOriginErr").show();
				$("#caseOriginErr").html("Please Select CaseOrigin");
				$("#caseOriginId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#caseOriginErr').hide();
				$('#caseOriginId').css({
					"border" : "",
					"background" : ""
				});
			}

			//type

			var type = $('#typeId').val();
			if (type == '') {
				isValid = false;
				$("#typeErr").show();
				$("#typeErr").html("Please Enter Type");
				$("#typeId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#typeErr').hide();
				$('#typeId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Commend

			var commend = $('#commendId').val();
			if (commend == '') {
				isValid = false;
				$("#typeCommendErr").show();
				$("#typeCommendErr").html("Please Enter Comments");
				$("#commendId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#typeCommendErr').hide();
				$('#commendId').css({
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
    	var today = new Date();
        $("#warrantyDateId, #expireDateId").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: today, 
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $(this).siblings(".date-input").datepicker("option", "minDate", dt);
            }
        });
    });
</script>
<script>
    $(function() {
    	var today = new Date();
        var startDate = $("#claimingDateId").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: today, 
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#expireDateId").datepicker("option", "minDate", dt); // Set minDate for datepicker3
                //$("#datepicker3").datepicker("option", "maxDate", selected-1);
                $("#expireDateId").prop("disabled", false); // Enable datepicker3
            }
        })

        $("#expireDateId").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: today, 
            beforeShowDay: function(date) {
                // Disable dates before selected startDate
                if (startDate.datepicker("getDate") === null) {
                    return [false, ""];
                }
                return [date.getTime() >= startDate.datepicker("getDate").getTime(), ""];
            }
        });

        // Initially disable endDateInput
        $("#expireDateId").prop("disabled", true);
    });
</script>

<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">

				<div class="text-center underline">
					<h3>edit CaseManagement</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="edit-casemanagement"
					modelAttribute="caseBOs" enctype="multipart/form-data">
					<form:hidden path="caseId" name="id" value="${caseBOs.caseId}" />
					<div class="box-list">
						<div class="item">
							<div class="row">
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>Reported By<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="clientBO.firstName"
												id="reportId" class="form-control required" readonly="true">
												<form:option value="${caseBOs.clientBO.clientId}">${caseBOs.clientBO.firstName}</form:option>
												<%-- <form:option value="select">-- Select --   </form:option>
												<form:options items="${listClients}" itemLabel="firstName"
													itemValue="clientId" /> --%>
											</form:select>
											<form:errors path="clientBO.firstName" class="error" />
											<div id="reportErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="ProductServiceBO.serviceName"
												id="productId" class="form-control required" readonly="true">
												<form:option value="${caseBOs.productServiceBO.serviceId}">${caseBOs.productServiceBO.serviceName}</form:option>
												<%-- <form:option value="select">-- Select --   </form:option>
												<form:options items="${productlist}" itemLabel="serviceName"
													itemValue="serviceId" /> --%>
											</form:select>
											<form:errors path="ProductServiceBO.serviceName"
												class="error" />
											<div id="productErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Case Solution <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="casesolution"
												id="casesolutionId" class="form-control required"
												placeholder="Case Solution" />
											<form:errors path="casesolution" class="error" />
											<div id="casesolutionErr" style="color: red;"></div>

										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label> Warranty Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="warrantyDate" readonly="true"
												id="warrantyDateId" class="form-control required"
												placeholder="Warranty Date" />
											<form:errors path="warrantyDate" class="error" />
											<div id="warrantyDateErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> claiming Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="claimingDate" readonly="true"
												id="claimingDateId" class="form-control required"
												placeholder="Claiming Date" />
											<form:errors path="claimingDate" class="error" />
											<div id="claimingDateErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> Expire Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="expireDate" id="expireDateId"  readonly="true"
												class="form-control required" placeholder="Expire Date" />
											<form:errors path="expireDate" class="error" />
											<div id="expireDateErr" style="color: red;"></div>

										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="leftAlign">Case Reason <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" path="caseReason" id="casesId"
												class="form-control required" placeholder="Case Reason"
												text-align="left" />
											<form:errors path="caseReason" class="error" />
											<div id="caseReasonErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Priority <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="priority" id="priorityId"
												class="form-control required" placeholder="priority" />
											<form:errors path="priority" class="error" />
											<div id="priorityErr" style="color: red;"></div>

										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label>Status<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="status" id="statusId"
												class="form-control required">
												<form:option value="Select">--Select--</form:option>
												<form:option value="complete">complete</form:option>
												<form:option value="Incomplete">Incomplete</form:option>
											</form:select>
											<form:errors path="status" class="error" />
											<div id="statusErr" style="color: red;"></div>
										</div>
									</div>
								</div>

								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>Email<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="email" id="CompanyEmailinput"
												class="form-control required" placeholder="email" />
											<form:errors path="email" class="error" />
											<div id="companyEmailErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Category<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="category" id="categoryId"
												class="form-control required">
												<form:option value="Select">--Select--</form:option>
												<form:option value="Procurement product">Procurement product</form:option>
												<form:option value="Manufacturing Product">Manufacturing Product</form:option>

											</form:select>
											<form:errors path="category" class="error" />
											<div id="categoryErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>case Origin<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="caseOrigin" id="caseOriginId"
												class="form-control required">
												<form:option value="Select">--Select--</form:option>
												<form:option value="Email">Email</form:option>
												<form:option value="Website">WebSite</form:option>
												<form:option value="Whatsp">WhatsUp</form:option>
											</form:select>
											<form:errors path="caseOrigin" class="error" />
											<div id="caseOriginErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>Type<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="type" id="typeId"
												class="form-control required" placeholder="type" />
											<form:errors path="type" class="error" />
											<div id="typeErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Comments<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="commend" id="commendId"
												class="form-control required" placeholder="commend" />
											<form:errors path="commend" class="error" />
											<div id="typeCommendErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Phone Number<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="phoneNo" maxlength="10"
												id="mobileNoId" class="form-control required"
												placeholder="phoneNumber" />
											<form:errors path="phoneNo" class="error" />
											<div id="mobileNoErr" style="color: red;"></div>

										</div>
									</div>
								</div>
								<div class="col-sm-12">
										<div class="col-sm-4">
										<div class="form-group">
											<label>Description <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="description" id="descriptionId"
												class="form-control required" placeholder="description" />
											<form:errors path="description" class="error" />
											<div id="descriptionErr" style="color: red;"></div>

										</div>
									</div>
								</div>
								<div style="text-align: right; margin-right: 31px">
									<button type="submit" id="btnsubmit"
										class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
									<a href="view-casemanagement?page=1"><span
										class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
								</div>

							</div>

						</div>
					</div>

				</form:form>

			</div>

		</div>

	</div>

</div>
