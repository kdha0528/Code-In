package com.codein.controller;

import com.codein.requestdto.post.WritePostDto;
import com.codein.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/writepost")
    public String writePost(@RequestBody @Valid WritePostDto writePostDto, @CookieValue(value = "accesstoken") Cookie cookie) {
        postService.writePost(writePostDto.toWritePostServiceDto(), cookie.getValue());
        return "redirect:/home";
    }
}
