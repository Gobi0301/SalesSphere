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
			if (name == '') {
				isValid = false;
				$("#priceBookOwnerErr").show();
				$("#priceBookOwnerErr").html("Please enter priceBook owner");
				$("#priceBookOwner").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(name)) {
				isValid = false;
				$("#priceBookOwnerErr").show();
				$("#priceBookOwnerErr").html("Please enter Character Only");
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
			if (accountSource == '00') {
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

			/*  //Specify Price
			var financialAmount = $('#fees').val();
			if (financialAmount == '0.0') {
				isValid = false;
				$("#financialAmountErr").show();
				$("#financialAmountErr").html("Please enter Specify Price");
				$("#fees").css({
					"border" : "1px solid red",

				});

			}else if (!/^[0-9]{1,10}$/.test(financialAmount)) {
				isValid = false;
				$("#financialAmountErr").show();
				 $("#financialAmountErr").html("Please enter number Only"); 
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
			 */
			//Final Amount	
			var price = $('#price').val();
			if (price == '0.0') {
				isValid = false;
				$("#priceErr").show();
				$("#priceErr").html("Please enter Final Price");
				$("#price").css({
					"border" : "1px solid red",

				});

			} else if (!/^[0-9]{1,10}$/.test(price)) {
				isValid = false;
				$("#priceErr").show();
				$("#priceErr").html("Please enter number Only");
				$("#price").css({
					"border" : "1px solid red",

				});

			} else {
				$('#priceErr').hide();
				$('#price').css({

					"border" : "",
					"background" : ""
				});
			}

			var description = $('#description').val();
			if (description == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please enter description");
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
	<br> <br> <br>
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit PriceBook</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="edit-pricebook"
					modelAttribute="priceBookVO">
					<form:hidden path="companyId" value="${companyId}" />
					<div class="box-list">
						<div class="item">
							<div class="row ">
								<div class="row ">
									<!--  <h3 style="background:#483D8B;">Account Information </h3> -->
									<h3 class="title">PriceBook Information</h3>
								</div>
								<form:hidden path="priceBookId" />
								<div class="col-sm-b   12">
									<div class="col-sm-4">
										<div class="form-group">
											<label>PriceBook Owner <span
												class="font10 text-danger">*</span></label>
											<form:select type="text" name="priceBookOwner" path="user.id"
												class="form-control required">
												<form:option value="0">Select User</form:option>
												<form:options items="${userBOList}" itemLabel="name"
													itemValue="id"></form:options>
											</form:select>

											<form:errors path="user.id" class="error" />
											<div id="user.idErr" style="color: red;"></div>
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
												<form:option value="0">Select Active</form:option>
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
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" name="priceBookOwner"
												path="productservicevo.serviceName" id="serviceNameId"
												class="form-control required" readonly="true">
												 <form:option
													value="${priceBookVO.productservicevo.serviceId}">${priceBookVO.productservicevo.serviceName}</form:option>
												<%-- <form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId"></form:options> --%>
											</form:select>

											<form:errors path="productservicevo.serviceName"
												class="error" />
											<div id="serviceNameErr" style="color: red;"></div>
										</div>






										<%-- 
										<div class="form-group">
											<label path="productservicebo.serviceName">Product Name <span
												class="font10 text-danger">*</span></label>
											<form:select type="text" path="productservicevo.serviceName"
												class="form-control required" id="serviceId">
												<form:option value="0">-- Select ProductName --</form:option>
												<form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
										</div> --%>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Supplier Name <span class="font10 text-danger">*</span></label>
											<form:select path="suppliervo.supplierName"
												class="form-control required" id="supplierIds"
												onclick="supplierValueId(supplierIds)" readonly="true">
												 <form:option value="${priceBookVO.suppliervo.supplierId}">${priceBookVO.suppliervo.supplierName}</form:option> 
												<%-- <form:options items="${supplierLists}"
													itemLabel="supplierName" itemValue="supplierId" /> --%>
											</form:select>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label> Specify price<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="suppliervo.financialAmount"
												id="fees" class="form-control required" maxlength="150"
												readonly="true" />
											<form:errors path="suppliervo.financialAmount" class="error" />
											<div id="financialAmountErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<div class="col-sm-b   12">
									<div class="col-sm-4">
										<div class="form-group">
											<label> Final Price<span class="font10 text-danger">*</span></label>
											<form:input type="text" path="price" id="price"
												class="form-control required" maxlength="150" />
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
											<%-- <form:input type="text" path="description" id="description"
											class="form-control" placeholder="Description"
											maxlength="10" /> --%>
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


	<br>