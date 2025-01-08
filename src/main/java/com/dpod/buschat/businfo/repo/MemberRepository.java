package com.dpod.buschat.businfo.repo;


import com.dpod.buschat.businfo.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Members,Long> {
}
