package com.iospring.pets.petsfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    INVALID_DELETE(UNAUTHORIZED, "게시글을 지울 수 없습니다"),
    INVALID_UPDATE(UNAUTHORIZED , "게시글을 수정할 수 없습니다"),
    INVALID_PAGE(BAD_REQUEST, "페이지를 초과했습니다."),
    INVALID_CONDITION(BAD_REQUEST, "해당 조건으로 검색할 수 없습니다."),
    USER_NOT_FOUND(BAD_REQUEST, "해당 유저 정보를 찾을 수 없습니다"),
    BOARD_NOT_FOUND(BAD_REQUEST, "해당 게시판 정보를 찾을 수 없습니다"),
    /* 400 BAD_REQUEST : 잘못된 요청 */
    FAIL_DELETE_IN_S3(FORBIDDEN, "S3 저장소의 데이터를 지우는데 실패하여습니다"),
    FAIL_UPLOAD_IN_S3(FORBIDDEN, "S3 저장소의 데이터를 생성하는데 실패하여습니다"),

    INVALID_REQUEST(BAD_REQUEST ,"입력한 정보로부터 에러가 발생했습니다."),
    INVALID_REQUEST_SORT(BAD_REQUEST, "해당 방법으로 조회 할 수 없습니다"),
    INVALID_REQUEST_IMAGE(BAD_REQUEST ,"파일 형식이 옳바르지 않습니다."),
    //조건을  장바구니에서 ORDER로 바꿔야함
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),

    INVALID_REQUEST_FOR_DATABASE(BAD_REQUEST,"제약조건에 위반되었습니다"),


    MISMATCH_PASSWORD(BAD_REQUEST, "입력한 비밀번호가 일치하지 않습니다."),
    MISMATCH_REGEXP_PWD(BAD_REQUEST, "비밀번호는 영어 숫자 특수문자, 8자 이상이여합니다"),

    MISMATCH_FILE_MIMETYPE(BAD_REQUEST,"이미지 파일만 처리할 수 있습니다."),

    NOT_AUTHORIZATION(UNAUTHORIZED,"권한이 없습니다."),
    FAIL_JOIN(UNAUTHORIZED,"회원가입에 실패하셨습니다."),



    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),


    DUPLICATE_PHONENUMBER(CONFLICT, "이미 존재하는 전화번호입니다."),
    NOT_ENOUGH_MONEY(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    MISMATCH_ID_PWD(BAD_REQUEST,"아이디와 패스워드가 일치하지 않습니다"),

    // basket

    INVALID_NUMBER(BAD_REQUEST , "0 이상의 수를 입력해주세요"),
    FAIL_UPLOAD_IMAGE(METHOD_NOT_ALLOWED, "이미지 업로드에 실패하였습니다")
    ;



    private final HttpStatus httpStatus;
    private final String detail;
}