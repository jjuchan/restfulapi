package com.back.domain.post.post.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @Transactional(readOnly = true)
    @GetMapping
    public List<PostDto> getItems() {
        List<Post> items = postService.getList();

        return items
                .stream()
                .map(PostDto::new) // postDto 변환
                .toList();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable Long id) {
        Post item = postService.findById(id);

        return new PostDto(item);
    }

    @Transactional
    @GetMapping("/{id}/delete")
    public RsData<PostDto> delete(@PathVariable Long id) {
        Post item = postService.findById(id);

        postService.delete(item);

        return new RsData<PostDto>("200-1", "%d번 댓글이 삭제되었습니다.".formatted(id), new PostDto(item));
    }
}