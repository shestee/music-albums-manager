package com.shestee.albums.service;

import com.shestee.albums.dao.RoleRepository;
import com.shestee.albums.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String theRoleName) {
        return roleRepository.findByName(theRoleName);
    }
}
