package com.mb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.demo.model.PostType;

public interface PostTypeDao extends JpaRepository<PostType, Long> {


}
