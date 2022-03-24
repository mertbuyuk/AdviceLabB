package com.mb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.demo.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {

	Role findByName(String roleName);
}
