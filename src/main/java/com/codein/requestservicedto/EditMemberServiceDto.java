package com.codein.requestservicedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditMemberServiceDto {

    private String email;
    private String nickname;
    private String password;
    private String name;
    private String phone;

    @Builder
    public EditMemberServiceDto(String email, String nickname, String password, String name, String phone) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
