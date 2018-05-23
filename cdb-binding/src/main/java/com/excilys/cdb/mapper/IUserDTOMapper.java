package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.UserDTO;
import com.excilys.cdb.dto.UserRoleDTO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.model.UserRole;

public interface IUserDTOMapper {

    User dtoToUser(UserDTO dto);

    UserRole dtoToUserRole(UserRoleDTO dto);

    UserDTO userToDto(User user);

    UserRoleDTO userRoleToDto(UserRole role);

}