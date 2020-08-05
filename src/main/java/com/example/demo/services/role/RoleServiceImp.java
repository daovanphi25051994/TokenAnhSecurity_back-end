package com.example.demo.services.role;

import com.example.demo.models.AppRole;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AppRole getById(Long id) {
        return roleRepository.findById(id).get();
    }
}
