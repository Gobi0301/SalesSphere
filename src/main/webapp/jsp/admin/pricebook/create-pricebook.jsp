<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
 <!--  <script >
	$(document).ready(
			function() {debugger

				$('#serviceId').change(
						function(event) {
							var product = $("#serviceId").val();
							$.get('AjaxViewSupplier', {
								product : product
							}, function(response) {

								var select = $("#supplierIds");
								select.find('option').remove();
								$.each(response, function(supplierId,
										InventoryBO) {
									$('<option>').val(
											InventoryBO.supplierId).text(
													InventoryBO.supplierName)
											.appendTo(select);
								});
							});
						});
			});
</script>  
 -->
<script>
    $(document).ready(function() {
        $('#serviceId').change(function() {
            var product = $("#serviceId").val();
            $.get('AjaxViewSupplier', { product: product }, 
                    function(response) {
                var select = $("#supplierIds");
                select.empty(); // Clear existing options
                // Add default option
                select.append($('<option>').val('').text('Select Suppiler'));
                // Add options fetched from Ajax response
                $.each(response, function(supplierId, SupplierBO) {
                    $('<option>').val(SupplierBO.supplierId).text(SupplierBO.supplierName).appendTo(select);
                });
            });
        });
    });
</script> 


<!-- <script>
	function supplierValueId(supplierIds) {
		
		$.ajax({
			type : "GET",
			url : 'getSupplierDetails',
			data : 'supplierId=' + $("#supplierIds").val(),
			success : function(SupplierBO) {
				$("#fees").val(SupplierBO.financialAmount);

			},
		});
	};
</script> -->

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
<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			var isValid = true;
			var name = $('#priceBookOwner').val();
			if (name == 'Select') {
				isValid = false;
				$("#priceBookOwnerErr").show();
				$("#priceBookOwnerErr").html("Please Select priceBook owner");
				$("#priceBookOwner").css({
					"border" : "1px solid red",

				});

			} else {
				$('#priceBookOwnerErr').hide();
				$('#priceBookOwner').css({

					"border" : "",
					"background" : ""
				});
			}
			
			var comapany = $('#priceBookName').val();
			if (comapany == '') {
				isValid = false;
				$("#priceBookNameErr").show();
				$("#priceBookNameErr").html("Please enter priceBook name");
				$("#priceBookName").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(comapany)) {
				isValid = false;
				$("#priceBookNameErr").show();
				$("#priceBookNameErr").html("Please enter Character Only");
				$("#priceBookName").css({
					"border" : "1px solid red",

				});

			} else {
				$('#priceBookNameErr').hide();
				$('#priceBookName').css({

					"border" : "",
					"background" : ""
				});
			}
			
			var accountSource = $('#active').val();
			if (accountSource == 'Select') {
				isValid = false;
				$("#activeErr").show();
				$("#activeErr").html("Please select active");
				$("#active").css({
					"border" : "1px solid red",

				});

			} else {
				$('#activeErr').hide();
				$('#active').css({

					"border" : "",
					"background" : ""
				});
			}
        //Product name
			var serviceName = $('#serviceId').val();
			if (serviceName == 'Select') {
				isValid = false;
				$("#serviceNameErr").show();
				$("#serviceNameErr").html("Please Select Product Name");
				$("#serviceId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#serviceNameErr').hide();
				$('#serviceId').css({

					"border" : "",
					"background" : ""
				});
			}
			//Suplier Nmae
				var supplierName = $('#supplierIds').val();
			if (supplierName == 'select') {
				isValid = false;
				$("#supplierNameErr").show();
				$("#supplierNameErr").html("Please Select Supplier Name");
				$("#supplierIds").css({
					"border" : "1px solid red",

				});

			} else {
				$('#supplierNameErr').hide();
				$('#supplierIds').css({

					"border" : "",
					"background" : ""
				});
			}
     //Specify Price
			var description = $('#fees').val();
			if (description == '') {
				isValid = false;
				$("#financialAmountErr").show();
				$("#financialAmountErr").html("Please Enter Specify Price");
				$("#fees").css({
					"border" : "1px solid red",

				});

			}else if (!/^[0-9]{1,10}$/.test(description)) {
				isValid = false;
				$("#financialAmountErr").show();
				$("#financialAmountErr").html("Please Enter number Only");
				$("#fees").css({
					"border" : "1px solid red",

				});

			} else {
				$('#financialAmountErr').hide();
				$('#fees').css({
					"border" : "",
					"background" : ""
				});
			}
			//Final Amount	
			var price = $('#price').val();
			if (price == '') {
				isValid = false;
				$("#priceErr").show();
				$("#priceErr").html("Please Enter Final Price");
				$("#price").css({
					"border" : "1px solid red",

				});

			}else if (!/^[0-9]{1,10}$/.test(price)) {
				isValid = false;
				$("#priceErr").show();
				$("#priceErr").html("Please Enter number Only");
				$("#price").css({
					"border" : "1px solid red",

				});

			}else {
				$('#priceErr').hide();
				$('#price').css({

					"border" : "",
					"background" : ""
				});
			}
    //Description
			var description = $('#description').val();
			if (description == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please Enter Description");
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

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>


<div class="contact-form-wrapper">
	<br> 
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create Price Book</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="create-pricebook"
					modelAttribute="priceBookBO">
					<div class="box-list">
						<div class="item">
							<div class="row ">
								<div class="row ">
									<!--  <h3 style="background:#483D8B;">Account Information </h3> -->
									<h3 class="title">PriceBook Information</h3>
								</div>
								<div class="col-sm-b   12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>PriceBook Owner <span
												class="font10 text-danger">*</span></label>
											<form:select type="text" path="priceBookOwner"
												class="form-control required">
												<form:option value="Select">-- Select Owner --</form:option>
												<form:options items="${userBOList}" itemLabel="name"
													itemValue="id" />
											</form:select>
											<form:errors path="priceBookOwner" class="error" />
											<div id="priceBookOwnerErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>PriceBook Name <span
			
												class="font10 text-danger">*</span></label>
											<form:input type="text" id="priceBookName"
												path="priceBookName" class="form-control required"
												placeholder="PriceBook Name" maxlength="150" />
											<form:errors path="priceBookName" class="error" />
											<div id="priceBookNameErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label> Active<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="active" id="active"
												class="form-control required">
												<form:option value="Select">Select Active</form:option>
												<form:option value="1">Active</form:option>
												<form:option value="0">De-Active</form:option>
											</form:select>
											<form:errors path="active" class="error" />
											<div id="activeErr" style="color: red;"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br>
					<div class="box-list">
						<div class="item">
							<div class="row ">
								<div class="row ">
									<h3 class="title">Product Information</h3>
								</div>
								 <div class="col-sm-b   12"> 
									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name <span
												class="font10 text-danger">*</span></label>
											<form:select type="text" path="productservicebo.serviceName"
												class="form-control required" id="serviceId">
												<form:option value="Select">-- Select ProductName --</form:option>
												<form:options items="${productList}"  itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="productservicebo.serviceName" class="error" />
											<div id="serviceNameErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label >Supplier Name <span
												class="font10 text-danger">*</span></label>
											<form:select  path="supplierBO.supplierName"
												class="form-control required" id="supplierIds">
												 <form:option value="select">-- Select SupplierName --</form:option>
												<form:options items="${supplierLists}"  itemLabel="supplierName"
													itemValue="supplierId" />
											</form:select>
											<form:errors path="supplierBO.supplierName" class="error" />
											<div id="supplierNameErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label> Specify Price<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="supplierBO.financialAmount"
												id="fees" class="form-control required" 
												maxlength="150" placeholder="Specify Price"/>
											<form:errors path="supplierBO.financialAmount" class="error" />
											<div id="financialAmountErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<div class="col-sm-b   12">
								<div class="col-sm-4">
										<div class="form-group">
											<label> Final Price<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="price"
												id="price" class="form-control required" 
												maxlength="10" placeholder="Final Price"/>
											<form:errors path="price" class="error" />
											<div id="priceErr" style="color: red;"></div>
										</div>
									</div>
									</div>

							</div>
						</div>
					</div>
					<br>
					<div class="box-list">
						<div class="item">
							<div class="row ">
								<div class="row ">
									<h3 class="title">Description Information</h3>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-12">
										<div class="form-group">
											<label> Description <span class="font10 text-danger">*</span></label>
											<form:textarea path="description" id="description"
												class="form-control" placeholder="Description" rows="3"
												maxlength="2000" />
											<form:errors path="description" class="error" />
											<div id="descriptionErr" style="color: red;"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br>
					<div style="text-align: right; margin-right: 30px">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20"
							style="text-align: right;">Submit</button>
						<a href="view-pricebook"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>

				</form:form>


			</div>
		</div>
	</div>
</div>

<br>



