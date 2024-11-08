<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>

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

<script type="text/javascript">
	//<![CDATA[
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'bold', 'italic', 'underline', 'ol', 'ul',
					'strikeThrough', 'html', 'image' ]
		}).panelInstance('keySkillsInput');
		new nicEditor({
			buttonList : [ 'bold', 'italic', 'underline', 'ol', 'ul',
					'strikeThrough', 'html', 'image' ]
		}).panelInstance('descriptionInput');
		new nicEditor({
			buttonList : [ 'bold', 'italic', 'underline', 'ol', 'ul',
					'strikeThrough', 'html', 'image' ]
		}).panelInstance('descriptionInput');
	});

	//]]>
</script>



<script>
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>

<div class="row scrollspy-sidenav pb-20 body-mt-15">
	<!-- 	<script>
		$(document)
				.ready(
						function() {
							$('#btnSubmit')
									.click(
											function(e) {

												//firstname

												var isValid = true;
												var firstName = $(
														"#firstNameInput")
														.val();
												if (firstName == '') {
													$("#firstNameErr").show();
													$("#firstNameErr")
															.html(
																	"Please Enter First Name")
													$("#firstNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(firstName)) {
													$("#firstNameErr").show();
													$("#firstNameErr")
															(
																	"Please Enter Character Only");
													isValid = false;
												} else {
													$("#firstNameErr").hide();
													$("#firstNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//lastname

												var lastname = $(
														"#lastNameInput").val();
												if (lastname == '') {

													$("#lastNameErr").show();
													$("#lastNameErr")
															.html(
																	"Please Enter Last Name")
													$("#lastNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(lastname)) {
													$("#lastNameErr").show();
													$("#lastNameErr")
															(
																	"Please Enter Character Only");
													isValid = false;
												} else {
													$("#lastNameErr").hide();
													$("#lastNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//email
												var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
												var companyEmail = $(
														"#emailIdInput").val();
												if (companyEmail == '') {

													isValid = false;
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter The EmilId")

													$("#emailIdInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
												} else if (!emailValidations
														.test(companyEmail)) {
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter Valid Email ");
													isValid = false;
												} else {
													$("#emailIdInputErr")
															.hide();
													$("#emailIdInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//contact number

												var contactNo = $('#contactId')
														.val();
												if (!/^[0-9]{1,11}$/
														.test(contactNo)) {
													isValid = false;
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter Contact Number");
													$("#contactId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (contactNo.length != 11) {
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter 11 Digit Contact Number ");
													  isValid = false;
												} else {
													$('#contacterror').hide();
													$('#contactId').css({

														"border" : "",
														"background" : ""
													});
												}

												// mobile Number Validation

												var mobileNo = document
														.getElementById("mobileId").value;
												if (!/^[0-9]{1,11}$/
														.test(mobileNo)) {
													isValid = false;
													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter Mobile Number");
													$("#mobileId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (mobileNo.length != 10) {

													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter 10 Digit Mobile Number");
													isValid = false;
												} else {
													$('#mobileerror').hide();
													$('#mobileId').css({

														"border" : "",
														"background" : ""
													});
												}


												//Assign Employe
												var assign = $('#userNameId')
														.val();
												if (assign == '0') {
													isValid = false;
													$("#userNameErr").show();
													$("#userNameErr")
															.html(
																	"Please Select Employee");
													$("#userNameId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#userNameErr').hide();
													$('#userNameId').css({
														"border" : "",
														"background" : ""
													});

												}
												//product

												var productName = $(
														'#productId').val();
												if (productName == '0') {
													isValid = false;
													$("#productErr").show();
													$("#productErr")
															.html(
																	"Please Select Product");
													$("#productId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#productErr').hide();
													$('#productId').css({
														"border" : "",
														"background" : ""
													});
												}

												//status

												var status = $('#statusId')
														.val();
												if (status == 'Select') {
													isValid = false;
													$("#statusErr").show();
													$("#statusErr")
															.html(
																	"Please Select Status");
													$("#statusId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#statusErr').hide();
													$('#statusId').css({
														"border" : "",
														"background" : ""
													});
												}


												/* //leadsource
												var leadsource = $(
														'#campaignName').val();
												if (leadsource == '0') {
													isValid = false;
													$("#campaignNameErr")
															.show();
													$("#campaignNameErr")
															.html(
																	"Please Select Lead Source");
													$("#campaignName")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#campaignNameErr')
															.hide();
													$('#campaignName').css({
														"border" : "",
														"background" : ""
													});
												}
 */
												//numberofemployee

												var employee = $(
														'#noOfEmployeesId')
														.val();
												if (employee == '0') {
													isValid = false;
													$("#noOfEmployeesErr")
															.show();
													$("#noOfEmployeesErr")
															.html(
																	"Please Enter No.Of.Employees");
													$("#noOfEmployeesId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#campaignNameErr')
															.hide();
													$('#noOfEmployeesId').css({
														"border" : "",
														"background" : ""
													});
												}

												//salutation

												var salutation = $(
														'#salutationId').val();
												if (salutation == 'Select') {
													isValid = false;
													$("#salutationErr").show();
													$("#salutationErr")
															.html(
																	"Please Select Salutation");
													$("#salutationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#salutationErr').hide();
													$('#salutationId').css({
														"border" : "",
														"background" : ""
													});

												}

												//designation

												var designation = $(
														'#designationId').val();
												if (designation == '') {
													isValid = false;
													$("#designationErr").show();
													$("#designationErr")
															.html(
																	"Please Select Salutation");
													$("#designationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#designationErr').hide();
													$('#designationId').css({
														"border" : "",
														"background" : ""
													});
												}

												//street
												var street = $('#streetId')
														.val();
												if (street == '') {
													isValid = false;
													$("#streetErr").show();
													$("#streetErr")
															.html(
																	"Please Enter Street");
													$("#streetId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#streetErr').hide();
													$('#streetId').css({
														"border" : "",
														"background" : ""
													});
												}

												//city
												var city = $('#cityId').val();
												if (city == '') {
													isValid = false;
													$("#cityErr").show();
													$("#cityErr")
															.html(
																	"Please Enter City");
													$("#cityId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#cityErr').hide();
													$('#cityId').css({
														"border" : "",
														"background" : ""
													});
												}

												//district
												var district = $('#districtId')
														.val();
												if (district == '') {
													isValid = false;
													$("#districtErr").show();
													$("#districtErr")
															.html(
																	"Please Enter District");
													$("#districtId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#districtErr').hide();
													$('#districtId').css({
														"border" : "",
														"background" : ""
													});
												}

												//state
												var state = $('#stateId').val();
												if (state == '') {
													isValid = false;
													$("#stateErr").show();
													$("#stateErr")
															.html(
																	"Please Enter State");
													$("#stateId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#stateErr').hide();
													$('#stateId').css({
														"border" : "",
														"background" : ""
													});
												}

												//country
												var country = $('#countryId')
														.val();
												if (country == '') {
													isValid = false;
													$("#countryErr").show();
													$("#countryErr")
															.html(
																	"Please Enter Country");
													$("#countryId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#countryErr').hide();
													$('#countryId').css({
														"border" : "",
														"background" : ""
													});
												}

												//postalcode
												var postalcode = $(
														'#postalCodeId').val();
												if (postalcode == '0') {
													isValid = false;
													$("#postalCodeErr").show();
													$("#postalCodeErr")
															.html(
																	"Please Enter Postal Code");
													$("#postalCodeId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#postalCodeErr').hide();
													$('#postalCodeId').css({
														"border" : "",
														"background" : ""
													});
												}

												//Annualrevenue

												var Annualrevenue = $(
														'#annualRevenueId')
														.val();
												if (Annualrevenue == '') {
													isValid = false;
													$("#annualRevenueErr")
															.show();
													$("#annualRevenueErr")
															.html(
																	"Please Enter Annual Revenue");
													$("#annualRevenueId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#annualRevenueErr')
															.hide();
													$('#annualRevenueId').css({
														"border" : "",
														"background" : ""
													});
												}


												//fax
									/* 			var fax = $('#faxId').val();
												if (fax == '') {
													isValid = false;
													$("#faxErr").show();
													$("#faxErr").html(
															"Please Enter Fax");
													$("#faxId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#faxErr').hide();
													$('#faxId').css({
														"border" : "",
														"background" : ""
													});
												} 
 */
												

												//description
												var description = $(
														'#descriptionId').val();
												if (description == '') {
													isValid = false;
													$("#descriptionErr").show();
													$("#descriptionErr")
															.html(
																	"Please Select Description");
													$("#descriptionId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#descriptionErr').hide();
													$('#descriptionId').css({
														"border" : "",
														"background" : ""
													});
												}

												if (isValid == false)
													e.preventDefault();

											});
						});
	</script> -->

<!-- //while Admin login script validation........ -->
	<script>
		$(document)
				.ready(
						function() {
							$('#btnSubmit')
									.click(
											function(e) {

												//firstname

												var isValid = true;
												var firstName = $(
														"#firstNameInput")
														.val();
												if (firstName == '') {
													$("#firstNameErr").show();
													$("#firstNameErr")
															.html(
																	"Please Enter First Name")
													$("#firstNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(firstName)) {
													$("#firstNameErr").show();
													$("#firstNameErr")
															.html(
																	"Please Enter Character Only ");
													$("#firstNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#firstNameErr").hide();
													$("#firstNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//lastname

												var lastName = $(
														"#lastNameInput").val();
												if (lastName == '') {

													$("#lastNameErr").show();
													$("#lastNameErr")
															.html(
																	"Please Enter Last Name")
													$("#lastNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(lastName)) {
													$("#lastNameErr").show();
													$("#lastNameErr")
															.html(
																	"Please Enter Character Only ");
													$("#lastNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#lastNameErr").hide();
													$("#lastNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//email
												var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
												var emailAddress = $(
														"#emailIdInput").val();
												if (emailAddress == '') {

													isValid = false;
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter The EmilId")

													$("#emailIdInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
												} else if (!emailValidations
														.test(emailAddress)) {
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter Valid Email ");
													$("#emailIdInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#emailIdInputErr")
															.hide();
													$("#emailIdInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//contact number

												var contactNo = $('#contactId')
														.val();
												if (!/^[0-9]{1,11}$/
														.test(contactNo)) {
													isValid = false;
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter Number Only");
													$("#contactId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (contactNo.length != 11) {
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter 11 Digit Contact Number ");
													$("#contactId")
															.css(
																	{
																		"border" : "1px solid red",

																	});
													isValid = false;
												} else {
													$('#contacterror').hide();
													$('#contactId').css({

														"border" : "",
														"background" : ""
													});
												}

												// mobile Number Validation

												var mobileNo = document
														.getElementById("mobileId").value;
												if (!/^[0-9]{1,11}$/
														.test(mobileNo)) {
													isValid = false;
													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter Number Only");
													$("#mobileId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (mobileNo.length != 10) {

													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter 10 Digit Mobile Number");
													isValid = false;
												} else {
													$('#mobileerror').hide();
													$('#mobileId').css({

														"border" : "",
														"background" : ""
													});
												}

												//Assign Employe
												var assign = $('#userNameId')
														.val();
												if (assign == '0') {
													isValid = false;
													$("#userNameErr").show();
													$("#userNameErr")
															.html(
																	"Please Select Employee");
													$("#userNameId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#userNameErr').hide();
													$('#userNameId').css({
														"border" : "",
														"background" : ""
													});

												}

												//product

												var productName = $(
														'#productId').val();
												if (productName == '0') {
													isValid = false;
													$("#productErr").show();
													$("#productErr")
															.html(
																	"Please Select Product");
													$("#productId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#productErr').hide();
													$('#productId').css({
														"border" : "",
														"background" : ""
													});
												}

												//status

												var status = $('#statusId')
														.val();
												if (status == 'Select') {
													isValid = false;
													$("#statusErr").show();
													$("#statusErr")
															.html(
																	"Please Select Status");
													$("#statusId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#statusErr').hide();
													$('#statusId').css({
														"border" : "",
														"background" : ""
													});
												}

												//numberofemployee

												var noOfEmployees = $(
														'#noOfEmployeesId')
														.val();
												if (noOfEmployees == '0') {
													isValid = false;
													$("#noOfEmployeesErr")
															.show();
													$("#noOfEmployeesErr")
															.html(
																	"Please Enter No.Of.Employees");
													$("#noOfEmployeesId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[0-9]{1,11}$/
														.test(noOfEmployees)) {
													isValid = false;
													$("#noOfEmployeesErr")
															.show();
													$("#noOfEmployeesErr")
															.html(
																	"Please Enter Number Only");
													$("#noOfEmployeesId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else {
													$('#noOfEmployeesErr')
															.hide();
													$('#noOfEmployeesId').css({
														"border" : "",
														"background" : ""
													});
												}

												//salutation

												var salutation = $(
														'#salutationId').val();
												if (salutation == 'Select') {
													isValid = false;
													$("#salutationErr").show();
													$("#salutationErr")
															.html(
																	"Please Select Salutation");
													$("#salutationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#salutationErr').hide();
													$('#salutationId').css({
														"border" : "",
														"background" : ""
													});

												}

												//designation

												var designation = $(
														'#designationId').val();
												if (designation == '') {
													isValid = false;
													$("#designationErr").show();
													$("#designationErr")
															.html(
																	"Please Enter designation");
													$("#designationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#designationErr').hide();
													$('#designationId').css({
														"border" : "",
														"background" : ""
													});
												}
												//street
												var street = $('#streetId')
														.val();
												if (street == '') {
													isValid = false;
													$("#streetErr").show();
													$("#streetErr")
															.html(
																	"Please Enter Street");
													$("#streetId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#streetErr').hide();
													$('#streetId').css({
														"border" : "",
														"background" : ""
													});
												}

												//city
												var city = $('#cityId').val();
												if (city == '') {
													isValid = false;
													$("#cityErr").show();
													$("#cityErr")
															.html(
																	"Please Enter City");
													$("#cityId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(city)) {
													$("#cityErr").show();
													$("#cityErr")
															.html(
																	"Please Enter Character Only ");
													$("#cityId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#cityErr').hide();
													$('#cityId').css({
														"border" : "",
														"background" : ""
													});
												}

												//district
												var district = $('#districtId')
														.val();
												if (district == '') {
													isValid = false;
													$("#districtErr").show();
													$("#districtErr")
															.html(
																	"Please Enter District");
													$("#districtId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(district)) {
													$("#districtErr").show();
													$("#districtErr")
															.html(
																	"Please Enter Character Only ");
													$("#districtId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#districtErr').hide();
													$('#districtId').css({
														"border" : "",
														"background" : ""
													});
												}

												//state
												var state = $('#stateId').val();
												if (state == '') {
													isValid = false;
													$("#stateErr").show();
													$("#stateErr")
															.html(
																	"Please Enter State");
													$("#stateId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(state)) {
													$("#stateErr").show();
													$("#stateErr")
															.html(
																	"Please Enter Character Only ");
													$("#stateId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#stateErr').hide();
													$('#stateId').css({
														"border" : "",
														"background" : ""
													});
												}

												//country
												var country = $('#countryId')
														.val();
												if (country == '') {
													isValid = false;
													$("#countryErr").show();
													$("#countryErr")
															.html(
																	"Please Enter Country");
													$("#countryId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(country)) {
													$("#countryErr").show();
													$("#countryErr")
															.html(
																	"Please Enter Character Only ");
													$("#countryId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#countryErr').hide();
													$('#countryId').css({
														"border" : "",
														"background" : ""
													});
												}

												//postalcode
												var postalcode = $(
														'#postalCodeId').val();
												if (postalcode == '') {
													isValid = false;
													$("#postalCodeErr").show();
													$("#postalCodeErr")
															.html(
																	"Please Enter Postal Code");
													$("#postalCodeId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[0-9]{1,11}$/
														.test(postalcode)) {
													isValid = false;
													$("#postalCodeErr").show();
													$("#postalCodeErr")
															.html(
																	"Please Enter Number Only");
													$("#postalCodeId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else {
													$('#postalCodeErr').hide();
													$('#postalCodeId').css({
														"border" : "",
														"background" : ""
													});
												}
												//Annualrevenue

												var Annualrevenue = $(
														'#annualRevenueId')
														.val();
												if (Annualrevenue == '') {
													isValid = false;
													$("#annualRevenueErr")
															.show();
													$("#annualRevenueErr")
															.html(
																	"Please Enter Annual Revenue");
													$("#annualRevenueId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[0-9]{1,11}$/
														.test(Annualrevenue)) {
													isValid = false;
													$("#annualRevenueErr")
															.show();
													$("#annualRevenueErr")
															.html(
																	"Please Enter Number Only");
													$("#annualRevenueId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else {
													$('#annualRevenueErr')
															.hide();
													$('#annualRevenueId').css({
														"border" : "",
														"background" : ""
													});
												}

												//description
												var description = $(
														'#descriptionId').val();
												if (description == '') {
													isValid = false;
													$("#descriptionErr").show();
													$("#descriptionErr")
															.html(
																	"Please Select Description");
													$("#descriptionId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#descriptionErr').hide();
													$('#descriptionId').css({
														"border" : "",
														"background" : ""
													});
												}

												if (isValid == false)
													e.preventDefault();

											});
						});
	</script>


<!-- //while company login script validation........ -->
<script>
		$(document)
				.ready(
						function() {
							$('#btnsubmit')
									.click(
											function(e) {

												//firstname

												var isValid = true;
												var firstName = $(
														"#firstNameInput")
														.val();
												if (firstName == '') {
													$("#firstNameErr").show();
													$("#firstNameErr")
															.html(
																	"Please Enter First Name")
													$("#firstNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(firstName)) {
													$("#firstNameErr").show();
													$("#firstNameErr")
															.html(
																	"Please Enter Character Only ");
													$("#firstNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#firstNameErr").hide();
													$("#firstNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//lastname

												var lastName = $(
														"#lastNameInput").val();
												if (lastName == '') {

													$("#lastNameErr").show();
													$("#lastNameErr")
															.html(
																	"Please Enter Last Name")
													$("#lastNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else if (!/^[a-zA-Z\s]*$/g
														.test(lastName)) {
													$("#lastNameErr").show();
													$("#lastNameErr")
															.html(
																	"Please Enter Character Only ");
													$("#lastNameInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#lastNameErr").hide();
													$("#lastNameInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//email
												var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
												var emailAddress = $(
														"#emailIdInput").val();
												if (emailAddress == '') {

													isValid = false;
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter The EmilId")

													$("#emailIdInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
												} else if (!emailValidations
														.test(emailAddress)) {
													$("#emailIdInputErr")
															.show();
													$("#emailIdInputErr")
															.html(
																	"Please Enter Valid Email ");
													$("#emailIdInput")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$("#emailIdInputErr")
															.hide();
													$("#emailIdInput").css({
														"border" : "",
														"background" : ""
													});
												}

												//contact number

												var contactNo = $('#contactId')
														.val();
												if (!/^[0-9]{1,11}$/
														.test(contactNo)) {
													isValid = false;
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter Number Only");
													$("#contactId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (contactNo.length != 11) {
													$("#contacterror").show();
													$("#contacterror")
															.html(
																	"Please Enter 11 Digit Contact Number ");
													$("#contactId")
															.css(
																	{
																		"border" : "1px solid red",

																	});
													isValid = false;
												} else {
													$('#contacterror').hide();
													$('#contactId').css({

														"border" : "",
														"background" : ""
													});
												}

												// mobile Number Validation

												var mobileNo = document
														.getElementById("mobileId").value;
												if (!/^[0-9]{1,11}$/
														.test(mobileNo)) {
													isValid = false;
													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter Number Only");
													$("#mobileId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else if (mobileNo.length != 10) {

													$("#mobileerror").show();
													$("#mobileerror")
															.html(
																	"Please Enter 10 Digit Mobile Number");
													isValid = false;
												} else {
													$('#mobileerror').hide();
													$('#mobileId').css({

														"border" : "",
														"background" : ""
													});
												}

												//Assign Employe
												var assign = $('#userNameId')
														.val();
												if (assign == '0') {
													isValid = false;
													$("#userNameErr").show();
													$("#userNameErr")
															.html(
																	"Please Select Employee");
													$("#userNameId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#userNameErr').hide();
													$('#userNameId').css({
														"border" : "",
														"background" : ""
													});

												}

												//status

												var status = $('#statusId')
														.val();
												if (status == 'Select') {
													isValid = false;
													$("#statusErr").show();
													$("#statusErr")
															.html(
																	"Please Select Status");
													$("#statusId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#statusErr').hide();
													$('#statusId').css({
														"border" : "",
														"background" : ""
													});
												}

												//salutation

												var salutation = $(
														'#salutationId').val();
												if (salutation == 'Select') {
													isValid = false;
													$("#salutationErr").show();
													$("#salutationErr")
															.html(
																	"Please Select Salutation");
													$("#salutationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#salutationErr').hide();
													$('#salutationId').css({
														"border" : "",
														"background" : ""
													});

												}

												//designation

												var designation = $(
														'#designationId').val();
												if (designation == '') {
													isValid = false;
													$("#designationErr").show();
													$("#designationErr")
															.html(
																	"Please Enter designation");
													$("#designationId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#designationErr').hide();
													$('#designationId').css({
														"border" : "",
														"background" : ""
													});
												}
												//street
												var street = $('#streetId')
														.val();
												if (street == '') {
													isValid = false;
													$("#streetErr").show();
													$("#streetErr")
															.html(
																	"Please Enter Street");
													$("#streetId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#streetErr').hide();
													$('#streetId').css({
														"border" : "",
														"background" : ""
													});
												}

												//city
												var city = $('#cityId').val();
												if (city == '') {
													isValid = false;
													$("#cityErr").show();
													$("#cityErr")
															.html(
																	"Please Enter City");
													$("#cityId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(city)) {
													$("#cityErr").show();
													$("#cityErr")
															.html(
																	"Please Enter Character Only ");
													$("#cityId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#cityErr').hide();
													$('#cityId').css({
														"border" : "",
														"background" : ""
													});
												}

												//district
												var district = $('#districtId')
														.val();
												if (district == '') {
													isValid = false;
													$("#districtErr").show();
													$("#districtErr")
															.html(
																	"Please Enter District");
													$("#districtId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(district)) {
													$("#districtErr").show();
													$("#districtErr")
															.html(
																	"Please Enter Character Only ");
													$("#districtId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#districtErr').hide();
													$('#districtId').css({
														"border" : "",
														"background" : ""
													});
												}

												//state
												var state = $('#stateId').val();
												if (state == '') {
													isValid = false;
													$("#stateErr").show();
													$("#stateErr")
															.html(
																	"Please Enter State");
													$("#stateId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(state)) {
													$("#stateErr").show();
													$("#stateErr")
															.html(
																	"Please Enter Character Only ");
													$("#stateId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#stateErr').hide();
													$('#stateId').css({
														"border" : "",
														"background" : ""
													});
												}

												//country
												var country = $('#countryId')
														.val();
												if (country == '') {
													isValid = false;
													$("#countryErr").show();
													$("#countryErr")
															.html(
																	"Please Enter Country");
													$("#countryId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[a-zA-Z\s]*$/g
														.test(country)) {
													$("#countryErr").show();
													$("#countryErr")
															.html(
																	"Please Enter Character Only ");
													$("#countryId")
															.css(
																	{
																		"border" : "1px solid red",
																	});
													isValid = false;
												} else {
													$('#countryErr').hide();
													$('#countryId').css({
														"border" : "",
														"background" : ""
													});
												}

												//postalcode
												var postalcode = $(
														'#postalCodeId').val();
												if (postalcode == '') {
													isValid = false;
													$("#postalCodeErr").show();
													$("#postalCodeErr")
															.html(
																	"Please Enter Postal Code");
													$("#postalCodeId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[0-9]{1,11}$/
														.test(postalcode)) {
													isValid = false;
													$("#postalCodeErr").show();
													$("#postalCodeErr")
															.html(
																	"Please Enter Number Only");
													$("#postalCodeId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else {
													$('#postalCodeErr').hide();
													$('#postalCodeId').css({
														"border" : "",
														"background" : ""
													});
												}
												//Annualrevenue

												var Annualrevenue = $(
														'#annualRevenueId')
														.val();
												if (Annualrevenue == '') {
													isValid = false;
													$("#annualRevenueErr")
															.show();
													$("#annualRevenueErr")
															.html(
																	"Please Enter Annual Revenue");
													$("#annualRevenueId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else if (!/^[0-9]{1,11}$/
														.test(Annualrevenue)) {
													isValid = false;
													$("#annualRevenueErr")
															.show();
													$("#annualRevenueErr")
															.html(
																	"Please Enter Number Only");
													$("#annualRevenueId")
															.css(
																	{
																		"border" : "1px solid red",

																	});

												} else {
													$('#annualRevenueErr')
															.hide();
													$('#annualRevenueId').css({
														"border" : "",
														"background" : ""
													});
												}

												//description
												var description = $(
														'#descriptionId').val();
												if (description == '') {
													isValid = false;
													$("#descriptionErr").show();
													$("#descriptionErr")
															.html(
																	"Please Select Description");
													$("#descriptionId")
															.css(
																	{
																		"border" : "1px solid red",
																	});

												} else {
													$('#descriptionErr').hide();
													$('#descriptionId').css({
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
						<h3>Edit Leads</h3>
					</div>
					<br>

					<form:form method="POST" id="addForm" action="edit-leads"
						modelAttribute="leadsBO">
						<form:hidden path="leadsId" />
						<div class="box-list">
							<div class="item">
								<div class="row ">
									<!-- <div class="row"
										style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #2a3f54">
										<h3 class="title" style="color:#fff">Leads Information</h3>
										style="background:gray;"
									</div> -->
									<h3 class="title">Leads Information</h3>
									<div class="row">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_realEstate','ROLE_ADMIN')">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Assign Employee<span
														class="font10 text-danger">*</span></label>
													<form:select type="text" path="adminLoginBO.id"
														id="userNameId" class="form-control required">
														<%-- <form:option value="${usrId}">${userName}</form:option> --%>
														<form:option value="0">-- Select --   </form:option>
														<form:options items="${userBOList}" itemLabel="name"
															itemValue="id" />
													</form:select>
													<form:errors path="userName" class="error" />
													<div id="userNameErr" style="color: red;"></div>

												</div>
											</div>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Product Interested<span
														class="font10 text-danger">*</span></label>
													<form:select type="text" path="productServiceBO.serviceId"
														id="productId" class="form-control required">
														<form:option value="0">-- Select --   </form:option>
														<form:options items="${productListObj}"
															itemLabel="serviceName" itemValue="serviceId" />
													</form:select>
													<form:errors path="serviceName" class="error" />
													<div id="productErr" style="color: red;"></div>
												</div>
											</div>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_realEstate','ROLE_ADMIN')">

											<div class="col-sm-4">
												<div class="form-group">
													<label>Project</label>
													<form:select type="text" path="projectBO.projectId"
														id="projectId" class="form-control required">
														<form:option value="0">-- Select --   </form:option>
														<form:options items="${projectBOList}"
															itemLabel="projectName" itemValue="projectId" />
													</form:select>
													<form:errors path="projectBO.projectName" class="error" />
													<div id="projectErr" style="color: red;"></div>
												</div>
											</div>
										</sec:authorize>

									</div>

									<div class="row">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_realEstate','ROLE_ADMIN')">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Lead Status</label>
													<form:select type="text" path="status" id="statusId"
														class="form-control required">
														<form:option value="Select">--Select--</form:option>
														<form:option value="new">New</form:option>
														<form:option value="working">Working</form:option>
														<form:option value="nurturing">Nurturing</form:option>
														<form:option value="unqualified">UnQualified</form:option>
														<form:option value="qualified">Qualified</form:option>
													</form:select>
													<form:errors path="status" class="error" />
													<div id="statusErr" style="color: red;"></div>

												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Lead Source</label>
													<form:select type="text" path="campaignBO.campaignId"
														id="campaignName" class="form-control ">
														<form:option value="0">-- Select --   </form:option>
														<form:options items="${listcampaign}"
															itemLabel="campaignName" itemValue="campaignId" />
													</form:select>
													<form:errors path="campaignBO.campaignName" class="error" />
													<div id="campaignNameErr" style="color: red;"></div>
												</div>
											</div>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_realEstate','ROLE_ADMIN')">
											<div class="col-sm-4">
												<div class="form-group">
													<label>NumberOfEmployees<span
														class="font10 text-danger">*</span></label>
													<form:input type="text" path="noOfEmployees"
														id="noOfEmployeesId" class="form-control required"
														placeholder="Number of Employees" />
													<form:errors path="noOfEmployees" class="error" />
													<div id="noOfEmployeesErr" style="color: red;"></div>

												</div>
											</div>
										</sec:authorize>
									</div>
								</div>
							</div>
						</div>
						<br>

						<div class="box-list">
							<div class="item">
								<div class="row ">
									<!-- <div class="row"
										style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #2a3f54">
				<h3 class="title" style="color:#fff">Personal Information</h3>
										style="background:gray;"
									</div> -->
									<h3 class="title">Personal Information</h3>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Salutation</label>
												<form:select type="text" path="salutation" id="salutationId"
													class="form-control required">
													<form:option value="Select">--Select--</form:option>
													<form:option value="Mr">Mr</form:option>
													<form:option value="Mrs/Ms">Mrs/Ms</form:option>
													<form:option value="Sir">Sir</form:option>
													<form:option value="Madam">Madam</form:option>
												</form:select>
												<form:errors path="salutation" cssClass="error" />
												<div id="salutationErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label> First Name <span class="font10 text-danger">*</span></label>
												<form:input id="firstNameInput" type="text" path="firstName"
													class="form-control required" placeholder="First Name"
													maxlength="150" />
												<form:errors path="firstName" class="error" />
												<div id="firstNameErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Last Name </label>
												<form:input type="text" path="lastName"
													class="form-control " placeholder="Last Name"
													id="lastNameInput" />
												<form:errors path="lastName" class="error" />
												<div id="lastNameErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Designation<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="designation"
													id="designationId" class="form-control required"
													placeholder="Designation" />
												<form:errors path="designation" class="error" />
												<div id="designationErr" style="color: red;"></div>
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
									<!-- <div class="row"
										style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #2a3f54">
										<h3 class="title" style="color:#fff">Communication Information</h3>
										style="background:gray;"
									</div> -->
									<h3 class="title">Communication Information</h3>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Email Address </label>
												<form:input type="mail" path="emailAddress"
													id="emailIdInput" class="form-control"
													placeholder="Email Address" />
												<form:errors path="emailAddress" cssClass="error" />
												<div id="emailIdInputErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Contact Number<span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="contactNo"
													class="form-control" placeholder="Contact No"
													maxlength="11" id="contactId" />
												<form:errors path="contactNo" cssClass="error" />
												<div id="contacterror" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label> Mobile Number <span
													class="font10 text-danger">*</span>
												</label>
												<form:input id="mobileId" type="text" path="mobileNo"
													maxlength="10" class="form-control"
													placeholder="Mobile Number" />
												<form:errors path="mobileNo" class="input_error" />
												<div id="mobileerror" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Street<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="street" id="streetId"
													class="form-control required" placeholder="Street" />
												<form:errors path="street" class="error" />
												<div id="streetErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>City<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="city" id="cityId"
													class="form-control required" placeholder="City" />
												<form:errors path="city" class="error" />
												<div id="cityErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>District<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="district" id="districtId"
													class="form-control required" placeholder="District" />
												<form:errors path="district" class="error" />
												<div id="districtErr" style="color: red;"></div>

											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>State<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="state" id="stateId"
													class="form-control required" placeholder="State" />
												<form:errors path="state" class="error" />
												<div id="stateErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Country<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="country" id="countryId"
													class="form-control required" placeholder="Country" />
												<form:errors path="country" class="error" />
												<div id="countryErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Postal Code<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="postalCode" maxlength="6"
													id="postalCodeId" class="form-control required"
													placeholder="Postal Code" />
												<form:errors path="postalCode" class="error" />
												<div id="postalCodeErr" style="color: red;"></div>

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
									<!-- <div class="row"
										style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #2a3f54">
										<h3 class="title" style="color:#fff">Additional Information</h3>
										style="background:gray;"
									</div> -->
									<h3 class="title">Additional Information</h3>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Annual Revenue <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="annualRevenue"
													id="annualRevenueId" class="form-control required"
													placeholder="Annual Revenue" />
												<form:errors path="annualRevenue" class="error" />
												<div id="annualRevenueErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Company Name</label>
												<form:input path="companyName" class="form-control "
													id="companyId" placeholder="Company Name" />
												<form:errors path="companyName" class="error" />
												<div id="companyErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Industry</label>
												<form:input path="industryType" class="form-control "
													id="industryId" placeholder="Industry" />
												<form:errors path="industryType" class="error" />
												<div id="industryErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Website</label>
												<form:input path="website" class="form-control "
													id="companyWebsiteInput" placeholder="Website" />
												<form:errors path="website" cssClass="error" />
												<div id="websiteErr" style="color: red;"></div>
												<%-- <form:errors path="website" class="input_error" /> --%>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Fax<span class="font10 text-danger"></span></label>
												<form:input type="text" path="fax" id="faxId"
													class="form-control required" placeholder="Fax" />
												<form:errors path="fax" class="error" />
												<div id="faxErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Rating</label>
												<form:select type="text" path="rating" id="ratingId"
													class="form-control required">
													<form:option value="Select">--Select--</form:option>
													<form:option value="1">*</form:option>
													<form:option value="2">**</form:option>
													<form:option value="3">***</form:option>
													<form:option value="4">****</form:option>
													<form:option value="5">*****</form:option>
												</form:select>
												<form:errors path="rating" class="error" />
												<div id="ratingErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="col-sm-12">
										<div class="form-group">
											<label>Description<span class="font10 text-danger"></span></label>
											<label class="hidden-xs"></label>
											<form:textarea path="description" id="descriptionId"
												class="form-control required" placeholder="Description"
												cols="130" rows="06" maxlength="2000" />
											<form:errors path="description" class="error" />
											<div id="descriptionErr" style="color: red;"></div>

										</div>
									</div>
								</div>
							</div>
						</div>

						<br>
						<sec:authorize
											access="hasAnyRole('ROLE_ADMIN')">
						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnSubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
							<a href="view-leads?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
						</sec:authorize>
						
						<sec:authorize
											access="hasAnyRole('ROLE_COMPANY')">
											<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
							<a href="view-leads?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
						</sec:authorize>
					</form:form>
					<br> <br>
				</div>
			</div>
		</div>
	</div>
</div>
<br>
