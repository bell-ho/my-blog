package com.shop.myshop.utils;

public enum RequestResultEnum {
    /**
     * 작업 성공 (HTTP Status: 200)
     */
    SUCCESS(200, 0, "처리에 성공하였습니다."),

    // 400 : BAD_REQUEST
    /**
     * 필수 Header 누락 (HTTP Status: 400)
     */
    WRONG_HEADER(400, 11, "필요한 Header값이 누락되었습니다."),
    /**
     * 필수 Parameter 누락 (HTTP Status: 400)
     */
    WRONG_PARAMETER(400, 12, "요청 파라미터를 확인하세요."),
    /**
     * 필수 Cookie 없음 (HTTP Status: 400)
     */
    NO_COOKIE(400, 13, "필요한 쿠키가 없습니다."),
    /**
     * 잘못된 요청: 이미 존재하는 정보 (HTTP Status: 400)
     */
    DUPLICATED(400, 14, "중복된 작업입니다."),

    /**
     * 인증 실패: 유효하지 않은 Token (HTTP Status: 401)
     */
    TOKEN_INVALID(401, 21, "잘못된 토큰입니다."),
    /**
     * 인증 실패: 만료된 Token (HTTP Status: 401)
     */
    TOKEN_EXPIRED(401, 22, "만료된 토큰입니다."),
    /**
     * 인증 실패: 로그인 정보 없음 (HTTP Status: 401)
     */
    LOGIN_REQUIRED(401, 23, "로그인이 필요한 작업입니다."),

    // 402 : PAYMENT_REQUIRED

    // 405 : METHOD_NOT_ALLOWED
    /**
     * 잘못된 요청: 권한이 없음 (HTTP Status: 403)
     */
    FORBIDDEN(403, 24, "현재 작업에 필요한 권한이 없습니다."),

    // 404 : NOT_FOUND
    /**
     * 잘못된 요청: 존재하지 않는 정보 (HTTP Status: 404)
     */
    NOT_FOUND(404, 15, "존재하지 않는 정보입니다."),

    /**
     * 지원되지 않는 미디어 (HTTP Status: 415)
     */
    UNSUPPORTED_MEDIA_TYPE(415, 16, "지원되지 않는 형식입니다."),
    INTERNAL_SERVER_ERROR(500, 500, "Internal server error"),

    /**
     * 일반 오류 (HTTP Status: 500)
     */
    FAIL(500, 1, "처리에 실패하였습니다.");

    /**
     * HTTP status code
     */
    private int status;
    /**
     * client에게 전달될 상세 코드
     */
    private int code;
    /**
     * client에게 표시될 메시지
     */
    private String message;

    /**
     * client에게 전달될 code를 포함하는 생성자
     *
     * @param code client에게 전달될 상세 코드
     */
    RequestResultEnum(int code) {
        this.code = code;
    }

    /**
     * client에게 전달될 code와 message를 포함하는 생성자
     */
    RequestResultEnum(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * HTTP status code를 반환한다.
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * client에게 전달될 상세 코드를 반환한다.
     *
     * @return client에게 전달될 상세 코드
     */
    public int getCode() {
        return this.code;
    }

    /**
     * client에게 표시될 상세 메시지를 반환한다.
     *
     * @return client에게 표시될 상세 메시지
     */
    public String getMessage() {
        return this.message;
    }

}
