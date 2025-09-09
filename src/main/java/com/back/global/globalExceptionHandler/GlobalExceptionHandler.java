package com.back.global.globalExceptionHandler;

import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


/**
 * 전역 예외 처리를 담당하는 클래스
 *
 * @RestControllerAdvice
 *  - 모든 @RestController 전역에서 발생하는 예외를 가로채 처리할 수 있도록 설정.
 *  - 공통적인 예외 처리 로직을 한 곳에서 관리 가능.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * NoSuchElementException 발생 시 실행되는 핸들러
     *
     * @ExceptionHandler(NoSuchElementException.class)
     *  - 특정 예외 타입(NoSuchElementException)을 감지해서 이 메서드로 처리.
     *
     * @ResponseStatus(HttpStatus.NOT_FOUND)
     *  - HTTP 응답 상태를 404(Not Found)로 반환.
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RsData<Void> handle() {
        return new RsData<>(
                "404-1",
                "해당 데이터가 존재하지 않습니다."
        );
    }
}