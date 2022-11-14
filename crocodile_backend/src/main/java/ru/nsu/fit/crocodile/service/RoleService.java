package ru.nsu.fit.crocodile.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.model.Role;
import ru.nsu.fit.crocodile.repository.RoleRepository;

import javax.management.InstanceAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> mapStringToRoles(String... list) {//todo проверить подозрительное
        List<Role> roles = new LinkedList<>();
        for (String name : list) {
            Optional<Role> opt = roleRepository.findByName(name);
            if (!opt.isPresent()) {
                throw new IllegalArgumentException("no such role");
            }
            roles.add(opt.get());
        }
        return roles;
    }

    public Long saveRole(String name) throws InstanceAlreadyExistsException {
        Optional<Role> opt = roleRepository.findByName(name);
        if (opt.isPresent()) {
            throw new InstanceAlreadyExistsException();
        }
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return role.getId();
    }

}
