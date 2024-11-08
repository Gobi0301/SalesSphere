package com.scube.crm.utils;

public enum UserRoles {   
  
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_COMPANY("ROLE_COMPANY"),
    ROLE_REALESTATE("ROLE_realEstate "),
    ROLE_CAMPAIGN_MANAGER("ROLE_CAMPAIGN_MANAGER"), 
    ROLE_CAMPAIGN_TEAM("ROLE_CAMPAIGN_TEAM"),
    ROLE_SALES_MANAGER("ROLE_SALES_MANAGER"),
    ROLE_SALES_TEAM("ROLE_SALES_TEAM"),
	ROLE_CONTACT_MANAGER("ROLE_CONTACT_MANAGER"),
    ROLE_LEAD_MANAGER("ROLE_LEAD_MANAGER"),
	ROLE_CONTACT_TEAM("ROLE_CONTACT_TEAM"),
    ROLE_ACCOUNT_MANAGER("ROLE_ACCOUNT_MANAGER"),
	ROLE_ACCOUNT_TEAM("ROLE_ACCOUNT_TEAM"),
	ROLE_OPPORTUNITY_MANAGER("ROLE_OPPORTUNITY_MANAGER"),
	ROLE_OPPORTUNITY_TEAM("ROLE_OPPORTUNITY_TEAM");

	
    private String role;
    
    UserRoles(String role) {
    
        this.role = role;
    }
    
    
    public String getRole() {
    
        return role;
    }
}
