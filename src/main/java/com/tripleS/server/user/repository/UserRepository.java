package com.tripleS.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

}
