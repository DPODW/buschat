package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.repo.MemberRepository;
import com.dpod.buschat.businfo.entity.Members;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final MemberRepository memberRepository;

    public TestService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void testEntitySave(Members members){
        memberRepository.save(members);
    }


}
