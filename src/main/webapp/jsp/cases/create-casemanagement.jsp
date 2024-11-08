
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%-- <%@ page trimDirectiveWhitespaces="true"%> --%>
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script>
	function customerProfile(customerId) {
		//alert("Ajax Goes To Call");
		$
				.ajax({
					type : "GET",
					url : 'getCustomerProfile',
					async : true,
					cache : false,

					data : 'customerId=' + $("#customerId").val(),

					success : function(clientList) {
						var len = clientList.length;
						var html = '<option value="0">Select</option>';
						var htmls = '<option value="0">Select</option>';

						for (var i = 0; i < len; i++) {
							html += '<option value="' +clientList[i].productServieBO.serviceId+ '">'
									+ clientList[i].productServieBO.serviceName
									+ '</option>';

							var id = clientList[i].productServieBO.serviceId;
							var name = clientList[i].productServieBO.serviceName;

							console.log(id);
							console.log(name);
						}
						html += '</option>';
						$('#serviceid').html(html);

					}
				});
	};
</script>

<!-- <script>
	function pricebookValueId(pricebook_Id) {

		$.ajax({
			type : "GET",
			url : 'getPriceBookDetails',
			data : 'pricebook_Id=' + $("#pricebook_Id").val(),
			success : function(PriceBookBO) {
				$("#priceBookName").val(PriceBookBO.price);

			},
		});
	};
</script>  -->
<!-- <script>
	function productProfile(serviceid) {
		alert("Ajax Goes To Call");
		$.ajax({
			type : "GET",
			url : 'productProfile',
			async : true,
			cache : false,
			data : 'serviceid=' + $("#serviceid").val(),
			success : function(data) {
				alert("Ajax  To Call");
				$("#namess").val(data);

			}
		});
	};
</script> -->


<script>
	function productProfile(serviceid) {
		var ids = $("#serviceid").val();
		
		$.ajax({

			type : "GET",
			url : 'getProduct',
			data : 'serviceids=' + $("#serviceid").val(),
			success : function(data) {

				console.log(data);
				$("#namess").val(data);

			}
		});
	};
</script>


<script>
	$(document).ready(function() {

		$('#casesolution').focus();

		$('#btnsubmit').click(function(e) {

			//Case Solution
			var isValid = true;
			var name = $('#casesolution').val();
			if (name == '') {
				isValid = false;
				$("#casesolutionErr").show();
				$("#casesolutionErr").html("Please enter case solution");
				$("#casesolution").css({
					"border" : "1px solid red",
				});
			} else {
				$('#casesolutionErr').hide();
				$('#casesolution').css({
					"border" : "",
					"background" : ""
				});
			}

			//Reported By
			//var isValid = true;
			var clientBO = $('#customerId').val();
			if (clientBO == 'select') {
				isValid = false;
				$("#firstNameErr").show();
				$("#firstNameErr").html("Please Select ReportedBy");
				$("#customerId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#firstNameErr').hide();
				$('#customerId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Probability
			//var isValid = true;
			var name = $('#probability').val();
			if (name == '0.0') {
				isValid = false;
				$("#probabilityErr").show();
				$("#probabilityErr").html("Please enter the probability");
				$("#probability").css({
					"border" : "1px solid red",
				});
			} else {
				$('#probabilityErr').hide();
				$('#probability').css({
					"border" : "",
					"background" : ""
				});
			}

			//productName
			//var isValid = true;
			var serviceName = $('#serviceid').val();
			if (serviceName == 'select') {
				isValid = false;
				$("#serviceNameErr").show();
				$("#serviceNameErr").html("Please select product name");
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
			//Warranty Date
			//var isValid = true;
			var name = $('#datepicker').val();
			if (name == '') {
				isValid = false;
				$("#warrantyDateErr").show();
				$("#warrantyDateErr").html("Please enter the Warranty Date");
				$("#datepicker").css({
					"border" : "1px solid red",
				});
			} else {
				$('#warrantyDateErr').hide();
				$('#datepicker').css({
					"border" : "",
					"background" : ""
				});
			}

			//claiming Date
			//var isValid = true;
			var name = $('#datepicker2').val();
			if (name == '') {
				isValid = false;
				$("#claimingDateErr").show();
				$("#claimingDateErr").html("Please enter the Claiming Date");
				$("#datepicker2").css({
					"border" : "1px solid red",
				});
			} else {
				$('#claimingDateErr').hide();
				$('#datepicker2').css({
					"border" : "",
					"background" : ""
				});
			}

			//Expire  Date
			//var isValid = true;
			var name = $('#datepicker3').val();
			if (name == '') {
				isValid = false;
				$("#expireDateErr").show();
				$("#expireDateErr").html("Please enter the Expire Date");
				$("#datepicker3").css({
					"border" : "1px solid red",
				});
			} else {
				$('#expireDateErr').hide();
				$('#datepicker3').css({
					"border" : "",
					"background" : ""
				});
			}

			//Case Reason
			//var isValid = true;
			var name = $('#caseId').val();
			if (name == '') {
				isValid = false;
				$("#caseReasonErr").show();
				$("#caseReasonErr").html("Please enter the Case Reason");
				$("#caseId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#caseReasonErr').hide();
				$('#caseId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Priority
			//var isValid = true;
			var name = $('#priority').val();
			if (name == '') {
				isValid = false;
				$("#priorityErr").show();
				$("#priorityErr").html("Please enter the Priority");
				$("#priority").css({
					"border" : "1px solid red",
				});
			} else {
				$('#priorityErr').hide();
				$('#priority').css({
					"border" : "",
					"background" : ""
				});
			}

			//Description
			//var isValid = true;
			var name = $('#description').val();
			if (name == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please enter the description");
				$("#description").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionErr').hide();
				$('#description').css({
					"border" : "",
					"background" : ""
				});
			}

			//Status
			var status = $('#status').val();
			if (status == 'Select') {
				isValid = false;
				$("#statusErr").show();
				$("#statusErr").html("Please select Status");
				$("#status").css({
					"border" : "1px solid red",

				});

			} else {
				$('#statusErr').hide();
				$('#status').css({

					"border" : "",
					"background" : ""
				});
			}

			//Category
			var category = $('#category').val();
			if (category == 'Select') {
				isValid = false;
				$("#categoryErr").show();
				$("#categoryErr").html("Please select Category");
				$("#category").css({
					"border" : "1px solid red",

				});

			} else {
				$('#categoryErr').hide();
				$('#category').css({

					"border" : "",
					"background" : ""
				});
			}

			//CaseOrigin
			var caseOrigin = $('#caseOrigin').val();
			if (caseOrigin == 'Select') {
				isValid = false;
				$("#caseOriginErr").show();
				$("#caseOriginErr").html("Please select CaseOrigin");
				$("#caseOrigin").css({
					"border" : "1px solid red",

				});

			} else {
				$('#caseOriginErr').hide();
				$('#caseOrigin').css({

					"border" : "",
					"background" : ""
				});
			}
			//Type
			var type = $('#type').val();
			if (type == '') {
				isValid = false;
				$("#typeErr").show();
				$("#typeErr").html("Please enter the Type");
				$("#type").css({
					"border" : "1px solid red",

				});

			} else {
				$('#typeErr').hide();
				$('#type').css({

					"border" : "",
					"background" : ""
				});
			}
			//commend
			var commend = $('#commend').val();
			if (commend == '') {
				isValid = false;
				$("#typeCommendErr").show();
				$("#typeCommendErr").html(" Please enter Comments");
				$("#commend").css({
					"border" : "1px solid red",

				});

			} else {
				$('#typeCommendErr').hide();
				$('#commend').css({

					"border" : "",
					"background" : ""
				});
			}
			
			var mobileNo = $('#mobileNoId').val();
			if (mobileNo == '') {
				isValid = false;
				$("#mobileNoErr").show();
				$("#mobileNoErr").html(" Please enter PhoneNumber");
				$("#mobileNoId").css({
					"border" : "1px solid red",

				});

			}
			else if (!/^[0-9]{1,10}$/.test(mobileNo)) {
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
			
			 //Email
			var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
											var companyEmail = $(
													"#CompanyEmailinput").val();
											if (companyEmail == '') {

												isValid = false;
												$("#companyEmailErr").show();
												$("#companyEmailErr").html(
														"Please Enter Email")
												$("#CompanyEmailinput").css({
													"border" : "1px solid red",
												});
											} else if (!emailValidations
													.test(companyEmail)) {
												$("#companyEmailErr").show();
												$("#companyEmailErr")
														.html("Please Enter Valid Email ");
												$("#CompanyEmailinput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#companyEmailErr").hide();
												$("#CompanyEmailinput").css({
													"border" : "",
													"background" : ""
												});
											}

			
			
			
			if (isValid == false)
				e.preventDefault();
		});
	});
</script>

<!-- <script>
    $(function() {
        $("#datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
        });
    });
</script> -->
<script>
    $(function() {
    	var today = new Date(); 
        $("#datepicker,#datepicker3").datepicker({
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
        var startDate = $("#datepicker2").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: today,
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#datepicker3").datepicker("option", "minDate", dt); // Set minDate for datepicker3
                //$("#datepicker3").datepicker("option", "maxDate", selected-1);
                $("#datepicker3").prop("disabled", false); // Enable datepicker3
            }
        })

        $("#datepicker3").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            beforeShowDay: function(date) {
                // Disable dates before selected startDate
                if (startDate.datepicker("getDate") === null) {
                    return [false, ""];
                }
                return [date.getTime() >= startDate.datepicker("getDate").getTime(), ""];
            }
        });

        // Initially disable endDateInput
        $("#datepicker3").prop("disabled", true);
    });
</script>



<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">

				<div class="text-center underline">
					<h3>Create CaseManagement</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="create-casemanagement"
					modelAttribute="caseBO" enctype="multipart/form-data">
					<div class="box-list">
						<div class="item">
							<div class="row">
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>Reported By<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="clientBO.firstName"
												id="customerId" onchange="customerProfile(customerId)"
												class="form-control required">
												<form:option value="select">-- Select --   </form:option>
												<form:options items="${listClients}" itemLabel="firstName"
													itemValue="id" />
											</form:select>
											<form:errors path="clientBO.firstName" class="error" />
											<div id="firstNameErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="productServiceBO.serviceName"
												id="serviceid" onchange="productProfile(serviceid)"
												class="form-control required">

												<form:option value="select">-- Select --</form:option>
												<form:options items="${productlist}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="productServiceBO.serviceName"
												class="error" />
											<div id="serviceNameErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Case Solution <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="casesolution"
												class="form-control required" placeholder="Case Solution" />
											<form:errors path="casesolution" class="error" />
											<div id="casesolutionErr" style="color: red;"></div>

										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>Warranty Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="warrantyDate" id="datepicker" readonly="true"
												class="form-control required" placeholder="Warranty Date" />
											<form:errors path="warrantyDate" class="error" />
											<div id="warrantyDateErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> Claiming Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="claimingDate" id="datepicker2" readonly="true"
												class="form-control required" placeholder="Claiming Date" />
												
											<form:errors path="claimingDate" class="error" />
											<div id="claimingDateErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> Expire Date<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="expireDate" id="datepicker3" readonly="true"
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
											<form:input type="text" path="caseReason" id="caseId"
												class="form-control required" placeholder="Case Reason"
												text-align="left" />
											<form:errors path="caseReason" class="error" />
											<div id="caseReasonErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Priority <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="priority"
												class="form-control required" placeholder="priority" />
											<form:errors path="priority" class="error" />
											<div id="priorityErr" style="color: red;"></div>

										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label>Status<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="status" id="status"
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
											<form:select type="text" path="category"
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
											<label>Case Origin<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="caseOrigin"
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
											<form:input type="text" path="type"
												class="form-control required" placeholder="type" />
											<form:errors path="type" class="error" />
											<div id="typeErr" style="color: red;"></div>

										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Comments<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="commend"
												class="form-control required" placeholder="comments" />
											<form:errors path="commend" class="error" />
											<div id="typeCommendErr" style="color: red;"></div>

										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label>Phone Number<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="phoneNo" maxlength="10" id="mobileNoId"
												class="form-control required" placeholder="phoneNumber" />
											<form:errors path="phoneNo" class="error" />
											<div id="mobileNoErr" style="color: red;"></div>

										</div>
									</div>
								</div>
                                        <div class="col-sm-12">
                                        <div class="col-sm-4">
										<div class="form-group">
											<label>Description <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="description" maxlength="2000"
												class="form-control required" placeholder="description" />
											<form:errors path="description" class="error" />
											<div id="descriptionErr" style="color: red;"></div>

										</div>
									</div>
									</div>

								

								<div style="text-align: right; margin-right: 31px">
									<button type="submit" id="btnsubmit"
										class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
									<a href="view-casemanagement"><span
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
