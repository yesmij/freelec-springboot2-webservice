package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("posts", postsService.findAllDesc());    // Service에서 가져온 값을 index.mustache에 posts로 전달
        // PostsListResponseDto postsListResponseDto = (PostsListResponseDto) postsService.findAllDesc();     // todo why? 이게 안되는지?
        List<PostsListResponseDto> postsListResponseDtoList = postsService.findAllDesc();
        model.addAttribute("posts", postsListResponseDtoList);

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        // model.addAttribute("post", postsService.findById(id));
        PostsResponseDto postsResponseDto = postsService.findById(id);            // Response 객체는 항상 별도로!!! (CRUD)
        model.addAttribute("post", postsResponseDto );
        return "posts-update";
    }

    @DeleteMapping("/posts/delete/{id}")
    public String postDeleter(@PathVariable Long id) {
        postsService.deleteById(id);
        return "/";
    }
}
