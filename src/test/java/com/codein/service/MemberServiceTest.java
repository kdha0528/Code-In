package com.codein.service;

import com.codein.crypto.PasswordEncoder;
import com.codein.domain.member.Member;
import com.codein.repository.member.MemberRepository;
import com.codein.requestdto.member.EditMemberDto;
import com.codein.requestdto.member.LoginDto;
import com.codein.requestdto.member.SignupDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given

        PasswordEncoder passwordEncoder = new PasswordEncoder();

        SignupDto signupDto = SignupDto.builder()
                .name("김동하")
                .nickname("데일이")
                .email("kdha4585@gmail.com")
                .password("12341234")
                .birth("2000-01-01")
                .sex("male")
                .phone("01012341234")
                .build();

        // when
        memberService.signup(signupDto.toSignupServiceDto());


        // then
        Assertions.assertEquals(1, memberRepository.count());
        Member member = memberRepository.findByEmail(signupDto.getEmail());
        Assertions.assertEquals(signupDto.getEmail(), member.getEmail());
        Assertions.assertEquals(signupDto.getName(), member.getName());
        Assertions.assertTrue(passwordEncoder.matches(signupDto.getPassword(), member.getPassword()));
    }

    @Test
    @DisplayName("로그인 성공")
    void test2() {
        // given
        SignupDto signupDto = SignupDto.builder()
                .name("김동하")
                .nickname("데일이")
                .email("kdha4585@gmail.com")
                .password("12341234")
                .birth("2000-01-01")
                .sex("male")
                .phone("01012341234")
                .build();
        memberService.signup(signupDto.toSignupServiceDto());

        LoginDto login = LoginDto.builder()
                .email("kdha4585@gmail.com")
                .password("12341234")
                .build();

        // when
        String accessToken = memberService.login(login.toMemberServiceDto());
        ResponseCookie responseCookie = memberService.buildResponseCookie(accessToken);

        // then
        Assertions.assertNotNull(responseCookie);
    }


    @Test
    @DisplayName("로그아웃 성공")
    void test3() {
        // given
        SignupDto signupDto = SignupDto.builder()
                .name("김동하")
                .nickname("데일이")
                .email("kdha4585@gmail.com")
                .password("12341234")
                .birth("2000-01-01")
                .sex("male")
                .phone("01012341234")
                .build();
        memberService.signup(signupDto.toSignupServiceDto());

        LoginDto login = LoginDto.builder()
                .email("kdha4585@gmail.com")
                .password("12341234")
                .build();
        String accessToken = memberService.login(login.toMemberServiceDto());

        // when
        memberService.logout(accessToken);

        // then
        Member nullMember = memberRepository.findByAccessToken(accessToken);
        Assertions.assertNull(nullMember);
    }

    @Test
    @DisplayName("회원정보 수정 성공")
    void test4() {
        // given
        SignupDto signupDto = SignupDto.builder()
                .name("김동하")
                .nickname("데일이")
                .email("kdha4585@gmail.com")
                .password("12341234")
                .birth("2000-01-01")
                .sex("male")
                .phone("01012341234")
                .build();
        memberService.signup(signupDto.toSignupServiceDto());

        LoginDto login = LoginDto.builder()
                .email("kdha4585@gmail.com")
                .password("12341234")
                .build();

        String accessToken = memberService.login(login.toMemberServiceDto());

        EditMemberDto editMemberDto = EditMemberDto.builder()
                .email("kdha0528@gmail.com")
                .nickname("데일이")
                .phone("01044444444")
                .name(null)
                .password("11112222")
                .build();
        // when
        memberService.editMember(accessToken, editMemberDto.toEditMemberServiceDto());

        // then
        Member editedMember = memberRepository.findByEmail(editMemberDto.getEmail());
        Assertions.assertEquals(signupDto.getName(), editedMember.getName());
        Assertions.assertEquals(editMemberDto.getEmail(), editedMember.getEmail());
        Assertions.assertEquals(editMemberDto.getPhone(), editedMember.getPhone());
        Assertions.assertTrue(passwordEncoder.matches(editMemberDto.getPassword(), editedMember.getPassword()));
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void test5() {
        // given
        SignupDto signupDto = SignupDto.builder()
                .name("김동하")
                .nickname("데일이")
                .email("kdha4585@gmail.com")
                .password("12341234")
                .birth("2000-01-01")
                .sex("male")
                .phone("01012341234")
                .build();
        memberService.signup(signupDto.toSignupServiceDto());

        LoginDto login = LoginDto.builder()
                .email("kdha4585@gmail.com")
                .password("12341234")
                .build();

        String accessToken = memberService.login(login.toMemberServiceDto());

        // when
        memberService.deleteMember(accessToken);

        // then
        Assertions.assertNull(memberRepository.findByEmail(signupDto.getEmail()));
    }
}