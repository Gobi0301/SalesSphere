package com.scube.crm.service;

import com.scube.crm.bo.AdminUserBO;

public interface UserRoleService {

	AdminUserBO createUserRoles(AdminUserBO userRolesBo) throws Exception;

	void deleteUserRole(AdminUserBO userbo);

	AdminUserBO updateUserRoles(AdminUserBO bo) throws Exception;

	
}
