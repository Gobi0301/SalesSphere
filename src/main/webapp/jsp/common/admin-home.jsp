<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>
<html>
<div class="box-list">
	<div class="item">
		<div class="row">
			<div class="text-center underline">
				<h3>My Sales DashBoard</h3>
			</div>
		</div>
		</br>
		<div class="row">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px">

							<h3 style="text-align: center;">Employees</h3>
							<a href="view-employees"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.employeeCount }</h1>
							</a>
						</div>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px">

							<h3 style="text-align: center;">Products</h3>
							<a href="view-productservice"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.productCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px">

							<h3 style="text-align: center;">Employees</h3>
							<a href="view-employees"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.employeeCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRODUCT','ROLE_VIEW_PRODUCT')">	
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px">

							<h3 style="text-align: center;">Products</h3>
							<a href="view-productservice"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.productCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_CAMPAIGN','ROLE_VIEW_CAMPAIGN')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">Campaigns</h3>
							<a href="view-campaign"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align:center;">${adminDashboardCount.campaignCount }</h1>
							</a>
						</div>
					</div>
				</div>
				</sec:authorize>
			<sec:authorize
				access="hasAnyRole('ROLE_CREATE_LEAD','ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_LEAD','ROLE_MANAGE_LEAD')">

				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">Leads</h3>
							<a href="view-leads"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.leadsCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_CUSTOMER','ROLE_VIEW_CUSTOMER')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">Customers</h3>
							<a href="view-customers"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.customerCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			<%-- <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CONTACT_TEAM','ROLE_CONTACT_MANAGER')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">ContactCount</h3>
							<a href="view-contact"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.contactCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize> --%>
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_ACCOUNT','ROLE_VIEW_ACCOUNT')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">AccountCount</h3>
							<a href="view-accounts"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.accountCount}</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>

			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_OPPORTUNITY','ROLE_VIEW_OPPORTUNITY')">
				<div class="col-sm-3">
					<div class="form-group">
						<div
							style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
							<h3 style="text-align: center;">Opportunities</h3>
							<a href="view-opportunities"
								style="text-decoration: underline; text-transform: capitalize; color: #333;">
								<h1 style="text-align: center;">${adminDashboardCount.opportunityCount }</h1>
							</a>
						</div>
					</div>
				</div>
			</sec:authorize>
			
			<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_CAMPAIGN_MANAGER','ROLE_CAMPAIGN_TEAM''ROLE_MARKETING','ROLE_SALES_MANAGER','ROLE_SALES_TEAM','ROLE_LEAD_MANAGER')">
			<div class="col-sm-3">
				<div class="form-group">
					<div
						style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
						<h3 style="text-align: center;">Company</h3>
						<a href="view-company"
							style="text-decoration: underline; text-transform: capitalize; color: #333;">
							<h1 style="text-align: center;">${adminDashboardCount.companyCount }</h1>
						</a>
					</div>
				</div>
			</div>
	</sec:authorize>
	
	 <sec:authorize
		access="hasAnyRole('ROLE_COMPANY')">
			<div class="col-sm-3">
				<div class="form-group">
					<div
						style="width: 258px; height: 156px; border: 10px solid #e6e6e6; box-sizing: border-box; border-radius: 20px"">
						<h3 style="text-align: center;">Sales</h3>
						<a href="view-sales-order"
							style="text-decoration: underline; text-transform: capitalize; color: #333;">
							<h1 style="text-align: center;">${adminDashboardCount.salesCount }</h1>
						</a>
					</div>
				</div>
			</div>
	</sec:authorize> 
			
		</div>
	</div>
</div>
</html>


