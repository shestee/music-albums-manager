package com.shestee.albums.service;

import com.shestee.albums.entity.Role;

public interface RoleService {
    Role findRoleByName(String theRoleName);
}
