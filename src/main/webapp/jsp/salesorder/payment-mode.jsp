<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

  <script>
	$(document).ready(function() {

		$('#name').focus();

		$('#btnsubmit').click(function(e) {
			var isValid = true;
			var sgst = $('#sgst').val();
			if (sgst == '') {
				isValid = false;
				$("#sgstErr").show();
				$("#sgstErr").html("Please enter sgst value");
				$("#sgst").css({
					"border" : "1px solid red",

				});

			} else {
				$('#sgstErr').hide();
				$('#sgst').css({

					"border" : "",
					"background" : ""
				});
			}
			
			

			var isValid = true;
			var cgst = $('#cgst').val();
			if (cgst == '') {
				isValid = false;
				$("#cgstErr").show();
				$("#cgstErr").html("Please enter cgst value");
				$("#cgst").css({
					"border" : "1px solid red",

				});

			} else {
				$('#cgstErr').hide();
				$('#cgst').css({

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
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			minDate: today, 
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
	});
</script>
<script>
$(document).ready(function() {

	$('#paymentmode').focus();

	$('#btnsubmit').click(function(e) {


		//Date
		var isValid = true;
		var name = $('#datepicker').val();
		if (name == '') {
			isValid = false;
			$("#datepickerErr").show();
			$("#datepickerErr").html("Please enter the Date");
			$("#datepicker").css({
				"border" : "1px solid red",
			});
		} else {
			$('#datepickerErr').hide();
			$('#datepickerErr').css({
				"border" : "",
				"background" : ""
			});
		}


		//Check No cgst
		var isValid = true;
		var name = $('#cgst').val();
		if (name == '0') {
			isValid = false;
			$("#cgstErr").show();
			$("#cgstErr").html("Please enter the valid Check Number");
			$("#cgst").css({
				"border" : "1px solid red",
			});
		} else {
			$('#cgstErr').hide();
			$('#cgstErr').css({
				"border" : "",
				"background" : ""
			});
		}

		//Payment Mode (Static DropDown)
		var type = $('#paymentmode').val();
		if (type == '') {
			isValid = false;
			$("#paymentmodeErr").show();
			$("#paymentmodeErr").html("Choose Payment Option");
			$("#paymentmode").css({
				"border" : "1px solid red",

			});

		} else {
			$('#paymentmodeErr').hide();
			$('#paymentmode').css({

				"border" : "",
				"background" : ""
			});
		}

		//transaction
		var checkNo = $('#transactionId').val();
		if (checkNo == '') {
			isValid = false;
			$("#transactionErr").show();
			$("#transactionErr").html("Please Enter the Transaction");
			$("#transactionId").css({
				"border" : "1px solid red",

			});

		} else {
			$('#transactionErr').hide();
			$('#transactionId').css({

				"border" : "",
				"background" : ""
			});
		}
		if (isValid == false)
			e.preventDefault();
	});
});

</script>
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
					<h3>Payment Mode</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="paymentmode"
					modelAttribute="paymentbo">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Payment Mode<span class="font10 text-danger">*</span></label>
									<form:select type="text" path="paymentmode" id="paymentmode"
											class="form-control required">
									<form:option value="">-- Select --   </form:option>
											<form:option value="cash">Cash   </form:option>
											<form:option value="check">Check </form:option>
											<form:option value="decitcard">Debit Card  </form:option>
											<form:option value="creditcard">Credit Card  </form:option>
											</form:select>
								 <form:errors path="paymentmode" cssClass="error" /> 
								<div id="paymentmodeErr" style="color: red;"></div> 
							</div>
						</div>
					<form:hidden path="salesOrderNo" />
							<div class="col-sm-4">
							<div class="form-group">
								<label class="element-block fw-normal font-lato"> Date<span
									class="font10 text-danger">*</span></font>
								</label>
								<form:input type="text" id="datepicker" path="date" readonly="true"
									placeholder="Date" class="form-control element-block" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
									<form:errors path="date" class="error" />
													<div id="datepickerErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Transaction Id/ Check No / Cash<span class="font10 text-danger">*</span></label>
								<form:input id="transactionId" type="text" path="checkNo"
									class="form-control required" placeholder="Transaction Id"
									maxlength="150" />
								 <form:errors path="checkNo" cssClass="error" /> 
								<div id="transactionErr" style="color: red;"></div>
							</div>
						</div>

					</div>
       
					<br />
					<div class="row">
						<div class="col-sm-1" style="float: right;">
							<a href=createinvoice?salesno=${salesno}><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
						</div>
						<div class="col-sm-1" style="float: right;">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align">Submit</button>
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
