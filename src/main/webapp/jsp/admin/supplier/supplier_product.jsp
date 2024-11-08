<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script>

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
    $(document).ready(function () {
        $('#serviceid').focus();

        $('#btnSubmit').click(function (e) {
            var isValid = true;

            // Product Name Validation
            var serviceName = $('#serviceid').val();
            if (serviceName === '') {
                isValid = false;
                $("#serviceNameErr").show().html("Please Select Product Name");
            } else {
                $("#serviceNameErr").hide().css("border", "");
            }

            // Technical Oriented Validation
            var techOriented = $('#techOrientedId').val();
            if (techOriented === '') {
                isValid = false;
                $("#techOrientedErr").show().html("Please Enter Technical Oriented");
            } else {
                $("#techOrientedErr").hide().css("border", "");
            }

            // Buying Price Validation
            var buyingPrice = $('#BuyingPriceId').val();
            if (buyingPrice === '') {
                isValid = false;
                $("#BuyingPriceErr").show().html("Please Enter Buying Price");
            } else if (!/^[0-9]+$/.test(buyingPrice)) {
                isValid = false;
                $("#BuyingPriceErr").show().html("Please Enter Number Only");
            } else {
                $("#BuyingPriceErr").hide().css("border", "");
            }

            // Prevent form submission if validation fails
            if (!isValid) {
                e.preventDefault();
            }
        });
    });
</script>
<div class="warning">


		<c:if test="${not empty successMessage}">
						<div class="alert alert-success">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Success:</strong>
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

<form:form method="POST" id="addForm" action="supplier_product" modelAttribute="SupplierProductBO">
    <form:hidden path="supplierId" name="supplierId" />
    <div class="box-list">
        <div class="item">
            <div class="row">
                <h3 class="title">Product Information</h3>
                <div class="col-sm-12">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label>Product Name<span class="font10 text-danger">*</span></label>
                            <form:select path="productServiceBO.serviceName" id="serviceid" class="form-control required">
                                <form:option value="">-- Select --</form:option>
                                <form:options items="${productList}" itemLabel="serviceName" itemValue="serviceId" />
                            </form:select>
                            <form:errors path="productServiceBO.serviceName" class="error" />
                            <div id="serviceNameErr" style="color: red;"></div>
                        </div>
                    </div>

                    <div class="col-sm-4">
                        <div class="form-group">
                            <label>Technical Oriented<span class="font10 text-danger">*</span></label>
                            <form:input type="text" path="techOriented" id="techOrientedId" class="form-control required" placeholder="Technical Oriented" />
                            <form:errors path="techOriented" class="error" />
                            <div id="techOrientedErr" style="color: red;"></div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label>Buying Price<span class="font10 text-danger">*</span></label>
                            <form:input type="text" path="BuyingPrice" id="BuyingPriceId" class="form-control required" placeholder="Buying Price"  />
                            <form:errors path="BuyingPrice" class="error" />
                            <div id="BuyingPriceErr" style="color: red;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align: right; margin-right: 31px">
                <button type="submit" id="btnSubmit" class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
                <a href="view_supplier"><span class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
            </div>
        </div>
    </div>
</form:form>
