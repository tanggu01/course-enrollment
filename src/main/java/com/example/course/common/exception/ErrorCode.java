package com.example.course.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "1000", "서버 에러"),
    MEMBER_NOT_FOUND(404, "1001", "없는 유저입니다."),
    MEMBER_DUPLICATED(409, "1002", "중복된 유저입니다."),
    LOGIN_FAIL_EXCEPTION(401, "1003", "로그인에 실패했습니다."),
    ROLE_NOT_FOUND(401, "1004", "유효하지 않은 역할입니다."),

    PHASE_NOT_ENDED(400, "2001", "아직 수강신청 기간이 끝나지 않았습니다."),
    PHASE_NOT_EXIST(400, "2002", "등록된 수강신청 기간이 없습니다."),


    INVALID_PHASE_PERIOD(400, "2003", "잘못된 수강신청 기간입니다."),
    COURSE_NOT_OPEN(400, "2004", "강의가 아직 다 열리지 않았습니다."),
    NOT_ENOUGH_COURSE(400, "2005", "5개 이상의 강의가 필요합니다"),
    NOT_ENOUGH_PROFESSOR(400, "2006", "강의를 등록하지 않은 교수님이 있습니다."),

    COURSE_NOT_EXIST(400, "2007", "없는 강의입니다."),
    COURSE_OCCUPIED(400, "2008", "이미 다른 교수님에 의해 등록된 강의입니다."),

    COURSE_REGISTER_LIMIT_EXCEEDED(400, "2009", "강의는 동시에 2개까지만 강의할 수 있습니다."),


    ENROLL_LIMIT_EXCEPTION(404, "2010", "강의는 3개까지만 수강할 수 있습니다."),

    COURSE_FULL_EXCEPTION(404, "2011", "최대 수강 인원을 초과했습니다."),

    MODIFY_NOT_ALLOWED(401, "2012", "수정 권한이 없습니다."),
    UNABLE_TO_WITHDRAW_COURSE(404, "2013", "수강신청 마감 3일 전에는 수강신청을 철회할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}