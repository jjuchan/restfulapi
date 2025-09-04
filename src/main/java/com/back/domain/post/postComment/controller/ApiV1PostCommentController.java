package com.back.domain.post.postComment.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
@RestController
public class ApiV1PostCommentController {
    private final PostService postService;

    @Transactional(readOnly = true)
    @GetMapping
    public List<PostCommentDto> getItems(
            @PathVariable long postId
    ) {
        Post post = postService.findById(postId);

        return post
                .getComments()
                .stream()
                .map(PostCommentDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public PostCommentDto getItem(
            @PathVariable long postId,
            @PathVariable long id
    ) {
        Post post = postService.findById(postId);

        PostComment postComment = post.findCommentById(id).get();

        return new PostCommentDto(postComment);
    }

    @Transactional
    @GetMapping("/{id}/delete")
    public String delete(
            @PathVariable long postId,
            @PathVariable long id
    ) {
        Post post = postService.findById(postId);

        PostComment postComment = post.findCommentById(id).get();

        postService.deleteComment(post, postComment);

        return "%d 댓글이 삭제 되었습니다.".formatted(id);
    }
}