package com.scube.crm.dao;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.User;

public interface UserRoleDAO {

	User createUserRole(User uservo) throws MySalesException;

	User deleteUserRole(long id,long roleId);

	User updateUserRole(User uservo);

	/*List<UserRolesBO> retrieveUserRoles(List<UserRolesBO> listbo);
*/

}
