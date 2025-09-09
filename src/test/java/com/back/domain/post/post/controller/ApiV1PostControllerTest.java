package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test") // 테스트 환경에서는 test 프로파일을 활성화합니다.
@SpringBootTest // 스프링부트 테스트 클래스임을 나타냅니다.
@AutoConfigureMockMvc // MockMvc를 자동으로 설정합니다.
@Transactional // 각 테스트 메서드가 종료되면 롤백됩니다.
public class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc; // MockMvc를 주입받습니다.

    @Autowired
    private PostService postService;

    //글쓰기 테스트
    @Test
    @DisplayName("글 쓰기")
    void t1() throws Exception {
        //요청을 보냅니다.
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목",
                                            "content": "내용"
                                        }
                                        """)
                )
                .andDo(print()); // 응답을 출력합니다.

        Post post = postService.findLatest().get();

        // 201 Created 상태코드 검증
        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 게시글이 생성되었습니다.".formatted(post.getId())))
                .andExpect(jsonPath("$.data.id").value(post.getId()))
                .andExpect(jsonPath("$.data.createDate").value(Matchers.startsWith(post.getCreateDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.modifyDate").value(Matchers.startsWith(post.getModifyDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.title").value("제목"))
                .andExpect(jsonPath("$.data.content").value("내용"));

    }

    //글 수정 테스트
    @Test
    @DisplayName("글 수정")
    void t2() throws Exception {
        long id = 1;

        //요청을 보냅니다.
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/posts/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목 update",
                                            "content": "내용 update"
                                        }
                                        """)
                )
                .andDo(print()); // 응답을 출력합니다.
        // 200 Ok 상태코드 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 게시글이 수정되었습니다.".formatted(id)));

//        Post post = postService.findById(id);
//
//        assertThat(post.getTitle().equals("제목 update"));
//        assertThat(post.getContent().equals("내용 update"));
    }

    //글 삭제 테스트
    @Test
    @DisplayName("글 삭제")
    void t3() throws Exception {
        long id = 1;

        //요청을 보냅니다.
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/posts/" + id)

                )
                .andDo(print()); // 응답을 출력합니다.
        // 200 Ok 상태코드 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("%d번 게시글이 삭제되었습니다.".formatted(id)))
                .andExpect(jsonPath("$.data.id").value(id));

    }
}