 <%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="" style="padding-top: 7%;">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<a class="collapsed" data-toggle="expand" data-parent="#accordion"
					href="#collapseTwo" aria-controls="collapseTwo">
					
					<sec:authorize access="isAuthenticated()">
						<ul class="nav navbar-nav" style="float: right;">
							<sec:authorize access="hasRole('ROLE_ADMIN')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">My Products<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-gst" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create GST</a> <a href="view-gst"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View GST</a> <a href="create-productservice"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											Create Product</a> <a href="view-productservice"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Product</a>
											<a href="create-pricebook"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											Create PriceBook</a> <a href="view-pricebook"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View PriceBook</a>
									</div></li>

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Access Management<i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 7px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="role-user-type" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Manage Role</a>
										<!-- <a href="view-role"
										style="height: 15px;"><i class="fa fa-play"
										aria-hidden="true" style="font-size: 8px;"></i>
										List Roles</a>  -->
										<a href="create-privileges" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Manage Privileges</a>
										<!-- <a href="view-privileges"
										style="height: 15px;"><i class="fa fa-play"
										aria-hidden="true" style="font-size: 8px;"></i>
										List Privileges -->
										</a> <a href="role-privileges" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Manage Role Privileges</a> <a href="create-access"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											Manage Access</a>
										<!-- <a href="view-access"
										style="height: 15px;"><i class="fa fa-play"
										aria-hidden="true" style="font-size: 8px;"></i>
										List Access</a> -->
										<a href="create-privilege-access" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Privileges Access</a>
										<!-- <a href="view-privilege-access"
										style="height: 15px;"><i class="fa fa-play"
										aria-hidden="true" style="font-size: 8px;"></i>
										List Privileges-Access</a> -->


									</div></li>

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Employees<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-employees" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Employees</a> <a href="view-employees"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Employees</a><a href="create-user-roles"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i> 
											Manage User & Role</a>
									</div></li>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_CAMPAIGN_TEAM','ROLE_CAMPAIGN_MANAGER','ROLE_SALES_MANAGER')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Campaigns <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">

										<a href="create-campaign" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Campaign</a> <a href="view-campaign"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Campaign</a>

									</div></li>

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Leads <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-leads" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Leads</a> <a href="view-leads?number=1"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Leads</a><a href="upload-leads"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											Upload Leads</a>
									</div></li>



							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_SALES_TEAM','ROLE_SALES_MANAGER')">

								<!-- <li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Customers <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-customers" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Customers</a> 
											
											
											<a href="view-customers?number=1"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Customers</a>
									</div></li>
 -->




								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Sales Order <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-sales-order" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Sales </a> <a href="view-sales-order"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Sales </a>
									</div></li>

							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_CONTACT_TEAM','ROLE_CONTACT_MANAGER')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Contacts <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-contact" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Contact</a> <a href="view-contact?number=1"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Contact</a>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_SALES_TEAM','ROLE_SALES_MANAGER')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Reports<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="view-leads-reports?number=1" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Leads Report </a> <a href="view-opportunitie?number=1"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											Opportunity Report </a>
										<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
											<a href="report-salesorder" style="height: 15px;"><i class="fa fa-play"
												aria-hidden="true" style="font-size: 8px;"></i>
												Sales Report </a>
										</sec:authorize>
									</div></li>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 13px; font-weight: 700;"><span
										class="dropbtn">Accounts<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<a href="create-account" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Account </a><a href="view-accounts"
											style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i>
											View Accounts </a>
									</div></li>
							</sec:authorize>
      
      <sec:authorize access="hasRole('ROLE_ADMIN')">
							<li class="dropdown" id="fdJobId"><a href="#"
								class="pd-tp-27" style="font-size: 13px; font-weight: 700; padding-left: 311px; "><span
									class="dropbtn">Opportunity<i class="fa fa-caret-down"
										aria-hidden="true" style="font-size: 15px; margin-left: 5px;"></i></span></a>

								<div class="dropdown-content dropdown-menu" role="menu"
									style="list-style-type: none;">
									<a href="create-opportunity" style="height: 15px;"><i
										class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
										 Create Opportunity </a> <a href="view-opportunities"
										style="height: 15px;"><i class="fa fa-play"
										view-opportunities aria-hidden="true" style="font-size: 8px;"></i>
										 View Opportunities </a>

								</div></li>
								</sec:authorize>

							<sec:authorize access="isAuthenticated()">
								<li class="link-btn hdr-icon-pd"><a href="logout"
									class="pd-tp-20"><span
										class="btn btn-theme  btn-pill btn-xs btn-line">Logout</span></a></li>
							</sec:authorize>
						</ul>
					</sec:authorize>
				</a>
				
				<a class="collapsed" data-toggle="expand" data-parent="#accordion"
					href="#collapseTwo" aria-controls="collapseTwo">
					<h4 class=" no-margin with-ic">
						<i class=" menu-fa-icon"></i> Home
					</h4>
				</a>
			</div>
			
			
			
		</div></div></div> --%>
		<%--<div id="collapseTwo"
			class="panel-collapse collapse in no-mr-tp no-pd">
			<div class="panel-body  panel-body-lg">
				<ul class="list-unstyled" style="text-align: left">
					<li><a href="create-jobseeker"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Create JobSeeker</a></li>
					<li><a href="admin-jobseekers"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View JobSeekers</a></li>
							
					<li><a href="jobseekers-update-profile-alert"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">JobSeekers Update Profile Alert</a></li>		
				</ul>
				<div class="white-space-20"></div>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<a class="collapsed" data-toggle="expand" data-parent="#accordion"
					href="#collapseThree" aria-controls="collapseThree">
					<h4 class=" no-margin with-ic">
						<i class="fa fa-users menu-fa-icon"></i> Employer
					</h4>
				</a>
			</div>
		</div>
		<div id="collapseThree"
			class="panel-collapse collapse in no-mr-tp no-pd">
			<div class="panel-body  panel-body-lg">
				<ul class="list-unstyled" style="text-align: left">
					<li><a href="create-employer"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Create Employer</a></li>
					<li><a href="employer-details"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View Employers</a></li>


					<li><a href="admin-employer-history"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Employer History</a></li>
					<li><a href="employer_Invitation"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Employer Invitation</a></li>

					<li><a href="walkin-job-view"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Walkin Jobs</a></li>

					<li><a href="employer-report-company"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Company Report</a></li>



				</ul>
				<div class="white-space-20"></div>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<a class="collapsed" data-toggle="expand" data-parent="#accordion"
					href="#collapseFour" aria-controls="collapseFour">
					<h4 class=" no-margin with-ic">
						<i class="fa fa-qrcode menu-fa-icon"></i> Product Management
					</h4>
				</a>
			</div>
		</div>
		<div id="collapseFour"
			class="panel-collapse collapse in no-mr-tp no-pd">
			<div class="panel-body  panel-body-lg">
				<ul class="list-unstyled" style="text-align: left">

					<li><a href="create-product"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Create Product</a></li>
					<li><a href="view-product"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View Product</a></li>

					<li><a href="view-contact"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View Contact</a></li>
					<li><a href="view-feedback"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View FeedBack</a></li>
				</ul>
				<div class="white-space-20"></div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<a class="collapsed" data-toggle="expand" data-parent="#accordion"
					href="#collapseFour" aria-controls="collapseFour">
					<h4 class=" no-margin with-ic">
						<i class="fa fa-cog menu-fa-icon"></i> Settings
					</h4>
				</a>
			</div>
		</div>
		<div id="collapseFour"
			class="panel-collapse collapse in no-mr-tp no-pd">
			<div class="panel-body  panel-body-lg">
				<ul class="list-unstyled" style="text-align: left">
					<li><a href="user-access-log"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Access Log</a></li>

					<!-- <li><a href="meta-information"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Add MetaInformation</a></li>

					<li><a href="meta-information-view"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View MetaInformation</a></li> -->

					<li><a href="upload-files"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Upload Company</a></li>

					<li><a href="admin-employer-upload"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Upload Employer</a></li>


					<li><a href="admin-job-opening-upload"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Upload JobPost</a></li>

					<li><a href="admin-jobseeker-upload"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Upload Job seeker</a></li>
					<li><a href="admin-company-view"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View Company Entity</a></li>

					<li><a href="admin-industry-view"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View Industry Entity</a></li>

					<li><a href="view-login-status"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">View User Online</a></li>

					<li><a href="refresh-context-values"><img
							src="resources/theme/images/menu_arrow.png"
							class="side-menu-icon">Refresh Industry And Company</a></li>

				</ul>
			</div>
		</div>
	</div>
</div> --%>