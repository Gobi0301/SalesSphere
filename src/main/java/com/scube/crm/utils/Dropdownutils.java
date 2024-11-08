package com.scube.crm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.scube.crm.service.MySalesResourceBundle;

public class Dropdownutils {
	
	public static List<String> dropdownList = new ArrayList<>();
	
	public static List<String> getCompanyType(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.companyType");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return dropdownList;
	}
	public static List<String> dropdowncityList = new ArrayList<>();
	public static List<String> getCity(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.city");
			dropdowncityList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return dropdowncityList;
	}
	
	
	public static List<String> dropdownstateList = new ArrayList<>();
	public static List<String> getStates(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.states");
			dropdownstateList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return dropdownstateList;
	}
	
	public static List<String> dropdownlangList = new ArrayList<>();
	public static List<String> getLang(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.lang");
			dropdownlangList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return dropdownlangList;
	}

	public static List<String> getMinimumExperiences(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.minimumexperiences");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return dropdownList;
	}
	
	public static List<String> getMaximumExperiences(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.maximumexperiences");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	public static List<String> getCurrencytype(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.currencytype");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getMinimumSalary(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.minimumsalary");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getMaximumSalary(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.maximumsalary");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getJobType(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.jobType");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getLocation(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.location");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getFunctionarea(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.functionarea");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	public static List<String> getUgqualifications(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.ugqualifications");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getJobseekerUgQualifications(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("jobseeker.ugqualifications");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}
	
	
	public static List<String> getPgqualifications(){
		try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.pgqualifications");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return dropdownList;
	}

	/**
	 * @return
	 */
	public static List<String> getSalary() {
		
try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.salary");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	return dropdownList;
	
	}

	/**
	 * @return
	 */
	public static List<String> getPreferedIndustry() {
		
try{
			
			String companyType=MySalesResourceBundle.getDropdown("employer.companyType");
			dropdownList=Arrays.asList(companyType.split(","));
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	return dropdownList;
	
	}
}
