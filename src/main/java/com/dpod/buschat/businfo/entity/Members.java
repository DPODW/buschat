package com.dpod.buschat.businfo.entity;


import jakarta.persistence.*;

@Entity
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "name")
    private String name;


    @Column(name = "password")
    private String password;

    public Members createMemberInfo(String name,String password){
        this.name = name;
        this.password = password;
        return this;
    }
}
