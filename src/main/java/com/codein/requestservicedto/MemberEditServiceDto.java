package com.codein.requestservicedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberEditServiceDto {

    private String email;
    private String password;
    private String name;
    private String phone;

    @Builder
    public MemberEditServiceDto(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
