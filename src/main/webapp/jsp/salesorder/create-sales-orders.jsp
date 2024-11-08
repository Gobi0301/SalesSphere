<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>




<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>

<script>
	$(document).ready(
			function() {

				$('#product_Id').change(
						function(event) {
							var product = $("#product_Id").val();
							$.get('AjaxViewPricebook', {
								product : product
							}, function(response) {

								var select = $("#pricebook_Id");
								select.find('options').remove();
								$.each(response, function(priceBookId,
										PriceBookBO) {
									$('<option>').val(PriceBookBO.priceBookId)
											.text(PriceBookBO.priceBookName)
											.appendTo(select);
								});
							});
						});
			});
</script>
<script>
	$(document).ready(
			function() {
				$('#product_Id').change(
						function(event) {
							var product = $("#product_Id").val();
							$.get('AjaxViewPricebook', {
								product : product
							}, function(response) {
								var select = $("#pricebook_Id");
								select.empty(); // Clear existing options
								// Add default option
								select.append($('<option>').val('').text(
										'Select Pricebook'));
								// Add options fetched from Ajax response
								$.each(response, function(priceBookId,
										PriceBookBO) {
									$('<option>').val(PriceBookBO.priceBookId)
											.text(PriceBookBO.priceBookName)
											.appendTo(select);
								});
							});
						});
			});
</script>

<!--  <script>
$(document).ready(function() {
    $('#product_Id').change(function(event) {
        var product = $("#product_Id").val();
        $.get('AjaxViewPricebook', { product: product }, function(response) {
            var select = $("#pricebook_Id");
            select.empty(); // Clear previous options
            $.each(response, function(index, PriceBookBO) {
            	
                $('<option>').val(PriceBookBO.priceBookId).text(PriceBookBO.priceBookName).appendTo(select); 
            });
        }).fail(function(xhr, status, error) {
            console.error("AJAX Error:", status, error);
        });
    });
});
</script> -->
<!--  <script>
$(document).ready(function() {
    $('#product_Id').change(function(event) {
        var product = $("#product_Id").val();
        $.get('AjaxViewPricebook', { product: product }, function(response) {
            var select = $("#pricebook_Id");
            select.empty(); // Use empty() instead of find('options').remove()
            $.each(response, function(index, PriceBookBO) {
                $('<option>').val(PriceBookBO.priceBookId).text(PriceBookBO.priceBookName).appendTo(select);
            });
        }).fail(function(xhr, status, error) {
            console.error("AJAX Error:", status, error);
        });
    });
});
</script> -->
<script>
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>

<script>
	function pricebookValueId(pricebook_Id) {

		var pricebookid = $("#pricebook_Id").val();
		alert(pricebookid);
		$.ajax({
			type : "GET",
			url : 'getPriceBookDetails',
			data : 'pricebook_Id=' + $("#pricebook_Id").val(),
			success : function(PriceBookBO) {
				$("#priceBookName").val(PriceBookBO.price);

			},
		});
	};
</script>

<!-- Product based gst and cgst -->
<!-- <script>
	$(document).ready(function() {
		$('#product_Id').change(function(event) {
			var productId = $(this).val(); // Assuming product_Id is an input field
			alert(productId);

			$.ajax({
				type : "GET",
				url : 'getProductGst',
				data : {
					product_Id : productId
				}, // Sending data as an object
				success : function(GstBO) {
					if (GstBO == null) {
						// Display error message
						alert('Error: GST details not found for this product.');
					} else {
						$("#gstId").val(GstBO.gstId);
						console.log(GstBO.gstId);
						$("#sgst").val(GstBO.sgst);
						$("#cgst").val(GstBO.cgst);
					}
				},
				error : function(xhr, status, error) {
					console.error(xhr.responseText); // Log any errors to the console
				}
			});
		});
	});
</script> -->
<script>
	$(document).ready(function() {
		$('#product_Id').change(function(event) {
			var productId = $(this).val(); // Assuming product_Id is an input field
			alert(productId);

			$.ajax({
				type: "GET",
				url: 'getProductGst',
				data: {
					product_Id: productId
				}, // Sending data as an object
				success: function(GstBO) {
					if (GstBO != null && null!=GstBO && 0<GstBO.gstId){
						$("#gstId").val(GstBO.gstId);
						console.log(GstBO.gstId);
						$("#sgst").val(GstBO.sgst);
						$("#cgst").val(GstBO.cgst);
						$("#productIdErr").hide();
						$("#product_Id").css("border", "");
					} else {
						// Show the error message and apply styling
						$("#productIdErr").html("Product doesn't have GST details");
						$("#product_Id").css("border", "1px solid red");
						$("#productIdErr").show();
					}
				},
				error: function(xhr, status, error) {
					console.error(xhr.responseText); // Log any errors to the console
					// Display error message
					alert('Error fetching GST details for this product.');
				}
			});
		});
	});
</script>


<!-- 
 <script>

$(document).ready(
		function() {
			
			$('#product_Id').change(
					function(event) {
						var product = $("#product_Id").val();
					 
		$.ajax({
			type : "GET",
			url : 'AjaxViewPricebook',
			data : 'product_Id=' + $("#product_Id").val(),
			success : function(response) {
				$("#priceBookName").val(PriceBookBO.price);

			},
		});
	});
		});
</script> -->

<script>
	function productValueId(pricebook_Id) {

		var pricebookid = $("#pricebook_Id").val();
		alert(pricebookid);
		$.ajax({
			type : "GET",
			url : 'getProductDetails',
			data : 'pricebook_Id=' + $("#pricebook_Id").val(),
			success : function(PriceBookBO) {
				$("#priceBookName").val(PriceBookBO.price);

			},
		});
	};
</script>

<script>
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : 'getCustomerDetails',
			async : true,
			cache : false,
			data : 'customerId=' + $("#customer_Id").val(),
			success : function(ClientBO) {
				$("#cus_company_name").val(ClientBO.companyName);
				$("#cus_industry_type").val(ClientBO.industryType);
				$("#cus_mobile_no").val(ClientBO.mobileNo);
				$("#cus_status").val(ClientBO.status);
				$("#cus_email").val(ClientBO.emailAddress);
				$("#cus_website").val(ClientBO.website);
				$("#cus_address").val(ClientBO.address);
			},
		});

	});
</script>

<script>
	function pricebook(accountid) {

		var pricebookid = $("#accountid").val();
		alert();
		$.ajax({
			type : "GET",
			url : 'getAccountProfile',
			async : true,
			cache : false,
			data : {
				accountId : pricebookid
			},
			success : function(accountBO) {
				$("#acc_contact").val(accountBO.contactNo);
				$("#acc_email").val(accountBO.email);
				$("#acc_type").val(accountBO.type);
				$("#acc_industry").val(accountBO.industry);
				$("#acc_city").val(accountBO.city);
				$("#acc_state").val(accountBO.state);
				$("#acc_country").val(accountBO.country);
			}
		})
	};
</script>
<script>
	function quantityValueId(quantity_Id) {

		$.ajax({
			type : "GET",
			url : 'getTotalDetails',
			data : 'quantityId=' + $("#quantity_Id").val(),

			success : function(SalesOrderBO) {
				console.log(SalesOrderBO)
				$("#total_Id").val(SalesOrderBO.totalInvoice);

			},
		});
	};
</script>



<script>
	function grandtotalValue() {

		$.ajax({
			type : "GET",
			url : 'getGrandTotalDetails',
			data : 'quantityId=' + $("#quantity_Id").val(),
			success : function(SalesOrderBO) {
				$("#grandtotalId").val(SalesOrderBO.grandTotal);
				$("#totalInvoiceId").val(SalesOrderBO.totalInvoice);
			},
		});
	};
</script>

<script>
	var agreementLength = 0;
	function addAgreementDetails() {
		var isValid = true;
		var name = $('#accountid').val();
		if (name == 'Select') {
			isValid = false;
			$("#accountIdErr").show();
			$("#accountIdErr").html("Please Select Account Name");
			$("#accountid").css({
				"border" : "1px solid red",

			});

		} else {
			$('#accountIdErr').hide();
			$('#accountid').css({

				"border" : "",
				"background" : ""
			});
		}

		//Product Name
		var serviceName = $('#product_Id').val();
		if (serviceName == 'Select') {
			isValid = false;
			$("#productIdErr").show();
			$("#productIdErr").html("Please Select Product Name");
			$("#product_Id").css({
				"border" : "1px solid red",

			});

		} else {
			$('#productIdErr').hide();
			$('#product_Id').css({

				"border" : "",
				"background" : ""
			});
		}

		//Pricebook Name
		var serviceName = $('#pricebook_Id').val();
		if (serviceName == 'Select') {
			isValid = false;
			$("#pricebookIdErr").show();
			$("#pricebookIdErr").html("Please Select Pricebook Name");
			$("#pricebook_Id").css({
				"border" : "1px solid red",

			});

		} else {
			$('#pricebookIdErr').hide();
			$('#pricebook_Id').css({

				"border" : "",
				"background" : ""
			});
		}

		//Price
		var serviceName = $('#priceBookName').val();
		if (serviceName == '0.0') {
			isValid = false;
			$("#priceErr").show();
			$("#priceErr").html("Please Enter Price");
			$("#priceBookName").css({
				"border" : "1px solid red",

			});

		} else {
			$('#priceErr').hide();
			$('#priceBookName').css({

				"border" : "",
				"background" : ""
			});
		}

		//Quantity
		var quantity = $('#quantity_Id').val();
		if (quantity == '') {
			isValid = false;
			$("#quantityErr").show();
			$("#quantityErr").html("Please Enter Quantity");
			$("#quantity_Id").css({
				"border" : "1px solid red",

			});

		} else if (!/^[0-9]{1,10}$/.test(quantity)) {
			isValid = false;
			$("#quantityErr").show();
			$("#quantityErr").html("Please Enter Character Only");
			$("#quantity_Id").css({
				"border" : "1px solid red",
			});
		} else {
			$('#quantityErr').hide();
			$('#quantity_Id').css({

				"border" : "",
				"background" : ""
			});
		}

		//Total
		var serviceName = $('#total_Id').val();
		if (serviceName == '0.0') {
			isValid = false;
			$("#totalPriceErr").show();
			$("#totalPriceErr").html("Please Select Total");
			$("#total_Id").css({
				"border" : "1px solid red",

			});

		} else {
			$('#totalPriceErr').hide();
			$('#total_Id').css({

				"border" : "",
				"background" : ""
			});
		}

		if (isValid == false) {
			e.preventDefault();
		} else {
			var pricebookName = $("#priceBookName").val();
			var pricebook_Id = $("#pricebook_Id").val();
			var quantity_Id = $("#quantity_Id").val();
			var total_Id = $("#total_Id").val();
			var product_Id = $("#product_Id").val();
			var gstId = $("#gstId").val();
			$
					.ajax({
						type : "GET",
						url : 'addAgreement',
						data : 'pricebook=' + pricebook_Id + '&price='
								+ pricebookName + '&quantityId=' + quantity_Id
								+ '&totalId=' + total_Id + '&productId='
								+ product_Id + '&gstId=' + gstId,
						success : function(agreementList) {
							var len = agreementList.length;
							agreementLength = agreementList.length;
							html = '<br/><div class="pi-responsive-table-sm">'
									+ '<table style="border: 1px solid; width:100%;" >'
									+ '<thead style="background-color: #2a3f54" >'
									+ '<tr>'
									+ '<th style="text-align: center; color: #fff;">S.NO</th>'
									+ '<th style="text-align: center; color: #fff;">PriceBook</th>'
									+ '<th style="text-align: center; color: #fff;">Amount</th>'
									+ '<th style="text-align: center; color: #fff;">Quantity</th>'
									+ '<th style="text-align: center; color: #fff;">Total</th>'
									+ '</tr></thead><tbody>';

							for (var i = 0; i < len; i++) {
								html = html
										+ '<tr>'
										+ '<td class="td-border list-capitalize; style="text-align: center;">'
										+ (i + 1)
										+ '</td>'
										+ '<td class="td-border list-capitalize">'
										+ agreementList[i].priceBookBo.priceBookName
										+ '</td>'
										+ '<td class="td-border list-capitalize">'
										+ agreementList[i].price
										+ '</td>'
										+ '<td class="td-border list-capitalize">'
										+ agreementList[i].quantity
										+ '</td>'
										+ '<td class="td-border list-capitalize">'
										+ agreementList[i].quantityPrice
										+ '</tr>';
							}
							html = html + '</tbody></table></div></div>';
							$("#addAgreement").html(html);
							$("#pricebook_Id").val("");
							$("#priceBookName").val("");
							$("#quantity_Id").val("");
							$("#total_Id").val("");

							grandtotalValue();

						}

					});

		}
	};

	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			if (agreementLength == 0) {

				e.preventDefault();
			}
		});
	});
</script>




<div class="row scrollspy-sidenav pb-20 body-mt-15">

	<!-- <script>
		$(document).ready(function() {

			$('#btnsubmit').click(function(e) {
				//Account Name
				var isValid = true;
				var name = $('#accountid').val();
				if (name == 'Select') {
					isValid = false;
					$("#accountIdErr").show();
					$("#accountIdErr").html("Please Select Account Name");
					$("#accountid").css({
						"border" : "1px solid red",

					});

				} else {
					$('#accountIdErr').hide();
					$('#accountid').css({

						"border" : "",
						"background" : ""
					});
				}

				//Product Name
				var serviceName = $('#product_Id').val();
				if (serviceName == 'Select') {
					isValid = false;
					$("#productIdErr").show();
					$("#productIdErr").html("Please Select Product Name");
					$("#product_Id").css({
						"border" : "1px solid red",

					});

				} else {
					$('#productIdErr').hide();
					$('#product_Id').css({

						"border" : "",
						"background" : ""
					});
				}

				//Pricebook Name
				var serviceName = $('#pricebook_Id').val();
				if (serviceName == 'Select') {
					isValid = false;
					$("#pricebookIdErr").show();
					$("#pricebookIdErr").html("Please Select Pricebook Name");
					$("#pricebook_Id").css({
						"border" : "1px solid red",

					});

				} else {
					$('#pricebookIdErr').hide();
					$('#pricebook_Id').css({

						"border" : "",
						"background" : ""
					});
				}

				//Price
				var serviceName = $('#priceBookName').val();
				if (serviceName == '0.0') {
					isValid = false;
					$("#priceErr").show();
					$("#priceErr").html("Please Enter Price");
					$("#priceBookName").css({
						"border" : "1px solid red",

					});

				} else {
					$('#priceErr').hide();
					$('#priceBookName').css({

						"border" : "",
						"background" : ""
					});
				}

				//Quantity
				var serviceName = $('#quantity_Id').val();
				if (serviceName == '0') {
					isValid = false;
					$("#quantityErr").show();
					$("#quantityErr").html("Please Enter Quantity");
					$("#quantity_Id").css({
						"border" : "1px solid red",

					});

				} else if (!/^[0-9]{1,10}$/.test(serviceName)) {
					$("#quantityErr").show();
					$("#quantityErr").html("Please Enter Character Only");
					isValid = false;
				} else {
					$('#quantityErr').hide();
					$('#quantity_Id').css({

						"border" : "",
						"background" : ""
					});
				}

				//Total
				var serviceName = $('#total_Id').val();
				if (serviceName == '0.0') {
					isValid = false;
					$("#totalPriceErr").show();
					$("#totalPriceErr").html("Please Select Total");
					$("#total_Id").css({
						"border" : "1px solid red",

					});

				} else {
					$('#totalPriceErr').hide();
					$('#total_Id').css({

						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();

			});
		});
	</script> -->

	<!-- 
<script>
	$(document).ready(function() {

		$('#accountid').focus();

		$('#btnsubmit').click(function(e) {


			//Account Name
			var isValid = true;
			var name = $('#accountid').val();
			if (name == 'Select') {
				isValid = false;
				$("#accountIdErr").show();
				$("#accountIdErr").html("Please Select Account Name");
				$("#accountid").css({
					"border" : "1px solid red",

				});

			} else {
				$('#accountIdErr').hide();
				$('#accountid').css({

					"border" : "",
					"background" : ""
				});
			}
			
   //Product Name
			var serviceName = $('#product_Id').val();
			if (serviceName == 'Select') {
				isValid = false;
				$("#productIdErr").show();
				$("#productIdErr").html("Please Select Product Name");
				$("#product_Id").css({
					"border" : "1px solid red",

				});

			} else {
				$('#productIdErr').hide();
				$('#product_Id').css({

					"border" : "",
					"background" : ""
				});
			}

			//Pricebook Name
			var serviceName = $('#pricebook_Id').val();
			if (serviceName == 'Select') {
				isValid = false;
				$("#pricebookIdErr").show();
				$("#pricebookIdErr").html("Please Select Pricebook Name");
				$("#pricebook_Id").css({
					"border" : "1px solid red",

				});

			} else {
				$('#pricebookIdErr').hide();
				$('#pricebook_Id').css({

					"border" : "",
					"background" : ""
				});
			}

			//Price
			var serviceName = $('#priceBookName').val();
			if (serviceName == '0.0') {
				isValid = false;
				$("#priceErr").show();
				$("#priceErr").html("Please Enter Price");
				$("#priceBookName").css({
					"border" : "1px solid red",

				});

			} else {
				$('#priceErr').hide();
				$('#priceBookName').css({

					"border" : "",
					"background" : ""
				});
			}

			//Quantity
			var serviceName = $('#quantity_Id').val();
			if (serviceName == '0') {
				isValid = false;
				$("#quantityErr").show();
				$("#quantityErr").html("Please Enter Quantity");
				$("#quantity_Id").css({
					"border" : "1px solid red",

				});

			}else if (!/^[0-9]{1,10}$/.test(serviceName)) {
				$("#quantityErr").show();
				$("#quantityErr").html
						(
								"Please Enter Character Only");
				isValid = false;
			} 
			 else {
				$('#quantityErr').hide();
				$('#quantity_Id').css({

					"border" : "",
					"background" : ""
				});
			}

			//Total
			var serviceName = $('#total_Id').val();
			if (serviceName == '0.0') {
				isValid = false;
				$("#totalPriceErr").show();
				$("#totalPriceErr").html("Please Select Total");
				$("#total_Id").css({
					"border" : "1px solid red",

				});

			} else {
				$('#totalPriceErr').hide();
				$('#total_Id').css({

					"border" : "",
					"background" : ""
				});
			}

		
			if (isValid == false)
				e.preventDefault();
		});
	});
</script> -->



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
						<h3>Create Sales Order</h3>

					</div>
					<br>
					<%-- <c:if test="${!empty listClients}"> --%>
					<%-- 	<div class="row ">
						<form:form id="myForm" method="post" class="login-form clearfix"
							action="search-customer" commandName="salesOrderBO">
							<div class="row"
								style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
								<input type="hidden" value="true" name="search" />
								<div class="col-sm-4">
									<div class="form-group">
										<label>Customer Name<span class="font10 text-danger">*</span></label>
										<form:select type="text" path="clientBO.firstName"
											class="form-control required">
											<form:option value="${firstname}">${firstname}</form:option>
											<form:options items="${listClients}" itemLabel="firstName"
												itemValue="id" />
										</form:select>
									</div>
								</div>



								<div class=" col-md-1" style="padding-bottom: 0px;">
									<div class="form-group home-right">
										<label class="hidden-xs"></label>
										<button class="btn btn-theme btn-success btn-block"
											style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
											<small><i class="fa fa-search" aria-hidden="true"
												style="font-size: 20px;"></i></small>
										</button>
									</div>
								</div>

							</div>
						</form:form>
					</div> --%>
					<%-- 	</c:if> --%>



					<form:form method="POST" id="addForm" action="create-sales-order"
						modelAttribute="salesOrderBO">
						<h3 class="title">Sales Information</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">
									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label> Sales Order No<span
													class="font10 text-danger">*</span></label>
												<form:input id="productNameInputt" type="text"
													path="salesOrderNo" class="form-control required"
													placeholder="Sales Order No" maxlength="150"
													value="${salesno}" readonly="true" />
												<%-- <form:errors path="salesOrderNo cssClass="error" /> --%>
												<div id="productNameErrr" style="color: red;"></div>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
						<br>
						<h3 class="title">Account Information</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">
									<div class="col-sm-12">
										<div class="col-sm-3">
											<div class="form-group">
												<label>Account Name <span class="font10 text-danger">*</span></label>
												<form:select type="text" name="accountId" id="accountid"
													path="accountBO.accountId" Onchange="pricebook(accountid)"
													class="form-control required">
													<form:option value="Select">Select</form:option>
													<form:options items="${accountList}"></form:options>
												</form:select>
												<form:errors path="accountBO.accountId" class="error" />
												<div id="accountIdErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label> Contact Number <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.contactNo"
													id="acc_contact" class="form-control required"
													placeholder="Phone No" readonly="true" />
												<form:errors path="accountBO.contactNo" cssClass="error" />
												<div id="contactNoErr" style="color: red;"></div>
											</div>

										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label> Email <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.email"
													id="acc_email" class="form-control required"
													placeholder="Email" readonly="true" />
												<form:errors path="accountBO.email" class="input_error" />
												<div id="emailErr" style="color: red;"></div>
											</div>

										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label> Type <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.type"
													class="form-control required" id="acc_type"
													placeholder="Type" readonly="true" />
												<form:errors path="accountBO.type" class="input_error" />
											</div>
										</div>



									</div>
									<div class="col-sm-12">
										<div class="col-sm-3">
											<div class="form-group">
												<label> Industry <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.industry"
													class="form-control required" placeholder="Industry"
													id="acc_industry" readonly="true" />
												<form:errors path="accountBO.industry" class="input_error" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label> City <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.city"
													class="form-control required" placeholder="City"
													id="acc_city" readonly="true" />
												<form:errors path="accountBO.city" class="input_error" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label> State <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.state"
													class="form-control required" placeholder="State"
													id="acc_state" readonly="true" />
												<form:errors path="accountBO.state" class="input_error" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label> Country <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="accountBO.country"
													class="form-control required" placeholder="Country"
													id="acc_country" readonly="true" />
												<form:errors path="accountBO.country" class="input_error" />
											</div>
										</div>
									</div>

								</div>
							</div>


						</div>
						<br>
						<h3 class="title">Product Information</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">
									<!-- <div class="col-sm-12"> -->
									<div class="col-sm-2">
										<div class="form-group">
											<label>Product<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="product.serviceName"
												onchange="productgstId(product_Id)" id="product_Id"
												class="form-control required">
												<form:option value="Select">-- Select --</form:option>
												<form:options items="${productBOList}"
													itemLabel="serviceName" itemValue="serviceId" />
											</form:select>
											<form:errors path="product.serviceName" class="error" />
											<div id="productIdErr" style="color: red;"></div>
										</div>
									</div>

									<input type="hidden" name="gstId" id="gstId"
										class="form-control required" />

									<div class="col-sm-1">
										<div class="form-group">
											<label>SGST<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="gstBO.sgst" id="sgst"
												readonly="true" class="form-control required"
												placeholder="SGst" />
											<form:errors path="gstBO.sgst" class="input_error" />
										</div>
									</div>

									<div class="col-sm-1">
										<div class="form-group">
											<label>CGST<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="gstBO.cgst" id="cgst"
												readonly="true" class="form-control required"
												placeholder="CGst" />
											<form:errors path="gstBO.cgst" class="input_error" />
										</div>
									</div>


									<div class="col-sm-2">
										<div class="form-group">
											<label>PriceBook Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="pricebookbo.priceBookName"
												id="pricebook_Id" onchange="pricebookValueId(pricebook_Id)"
												class="form-control required">
												<form:option value="Select">-- Select --</form:option>
												<form:options items="${pricebooksList}"
													itemLabel="priceBookName" itemValue="priceBookId" />
											</form:select>
											<div id="pricebookIdErr" style="color: red;"></div>
										</div>
									</div>



									<div class="col-sm-2">
										<div class="form-group">
											<label>Price <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="pricebookbo.price"
												readonly="true" id="priceBookName"
												class="form-control required" placeholder="Price" />
											<form:errors path="pricebookbo.price" class="input_error" />
											<div id="priceErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-2">
										<div class="form-group">
											<label>Quantity <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="quantity" id="quantity_Id"
												onchange="quantityValueId(quantity_Id)"
												class="form-control required" placeholder="Quantity"
												maxlength="10" />
											<form:errors path="quantity" class="input_error" />
											<div id="quantityErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-2">
										<div class="form-group">
											<label>Total <span class="font10 text-danger">*</span></label>
											<form:input type="text" path="totalPrice" id="total_Id"
												readonly="true" class="form-control required"
												placeholder="Total" />
											<form:errors path="totalPrice" class="input_error" />
											<div id="totalPriceErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<!--  </div>  -->

							</div>
						</div>


						<div class="row">
							<div class="col-sm-1" style="float: right;">

								<input type="button"
									style="background: #20354a; color: #ffffff; border: 1px solid transparent;"
									onclick="addAgreementDetails();" value="Add" />

							</div>
						</div>

						<div id="addAgreement"></div>

						<br>
						<div class="row">
							<div class="col-sm-2" style="float: right;">
								<form:input type="text" path="grandTotal" id="grandtotalId"
									readonly="true" class="form-control required"
									placeholder="Grand Total" />
								<form:errors path="grandTotal" class="input_error" />
							</div>


							<div class="col-sm-2" style="float: right;">
								<div class="form-group" style="float: right;">
									<label>Grand Total <span class="font10 text-danger">*</span></label>

								</div>
							</div>
						</div>
						</br>
						<div class="row">
							<div class="" style="float: right;"></div>
							<div class="col-sm-2" style="float: right;">
								<div class="form-group">
									<form:input type="text" path="totalInvoice" readonly="true"
										class="form-control required" placeholder="TotalInvoice"
										id="totalInvoiceId" />
									<form:errors path="totalInvoice" class="input_error" />
								</div>
							</div>

							<div class="col-sm-2" style="float: right;">
								<div class="form-group" style="float: right;">
									<label>Total Invoice<span class="font10 text-danger">*</span></label>
								</div>
							</div>
						</div>

						<br />
						<div class="row">

							<div class="col-sm-1" style="float: right;">
								<a href="admin-home"><span
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
</div>
<br>

