package com.excilys.cdb.mapper;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UserDTO;
import com.excilys.cdb.dto.UserRoleDTO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.model.UserRole;

@Component
public class UserDTOMapper implements IUserDTOMapper {
    @Override
    public User dtoToUser(UserDTO dto) {
        User user = new User.Builder(dto.getId()).withLogin(dto.getLogin()).withPassword(dto.getPassword()).build();
        user.setRole(dtoToUserRole(ofNullable(dto.getRole()).orElse(new UserRoleDTO())));
        return user;
    }

    @Override
    public UserRole dtoToUserRole(UserRoleDTO dto) {
        return new UserRole(dto.getId(), dto.getLabel());
    }
    
    @Override
    public UserDTO userToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        ofNullable(user.getRole()).ifPresent(role -> {
            dto.setRole(userRoleToDto(role));
        });
        return dto;
    }
    
    @Override
    public UserRoleDTO userRoleToDto(UserRole role) {
        return new UserRoleDTO(role.getId(), role.getLabel());
    }
}
