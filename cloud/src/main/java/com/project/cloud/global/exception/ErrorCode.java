package com.project.cloud.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 에러코드 규약
     * HTTP Status Code는 에러에 가장 유사한 코드를 부여한다.
     * 사용자정의 에러코드는 중복되지 않게 배정한다.
     * 사용자정의 에러코드는 각 카테고리 이름과 숫자를 조합하여 명확성을 더한다.
     * =============================================================
     * 400 : 잘못된 요청
     * 401 : 인증되지 않은 요청
     * 403 : 권한의 문제가 있을때
     * 404 : 요청한 리소스가 존재하지 않음
     * 409 : 현재 데이터와 값이 충돌날 때(ex. 아이디 중복)
     * 412 : 파라미터 값이 뭔가 누락됐거나 잘못 왔을 때
     * 422 : 파라미터 문법 오류
     * 424 : 뭔가 단계가 꼬였을때, 1번안하고 2번하고 그런경우
     * =============================================================
     */


    // Common
    SERVER_UNTRACKED_ERROR("COMMON500", "미등록 서버 에러입니다. 서버 팀에 연락주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("COMMON400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("COMMON401", "인증되지 않은 요청입니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("COMMON403", "권한이 부족합니다.", HttpStatus.FORBIDDEN),
    OBJECT_NOT_FOUND("COMMON404", "조회된 객체가 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PARAMETER("COMMON422", "잘못된 파라미터입니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    PARAMETER_VALIDATION_ERROR("COMMON422", "파라미터 검증 에러입니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    PARAMETER_GRAMMAR_ERROR("COMMON422", "파라미터 문법 에러입니다.", HttpStatus.UNPROCESSABLE_ENTITY),

    // Token
    TOKEN_INVALID("TOKEN401", "유효하지 않은 Token 입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID_ROLE("TOKEN401", "JWT 토큰에 Role 정보가 없습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_EXPIRED("TOKEN401", "Access Token 이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID("TOKEN401", "유효하지 않은 Access Token 입니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_NOT_FOUND("TOKEN404", "해당 사용자에 대한 Refresh Token 을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_MISMATCH("TOKEN401", "Refresh Token 이 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("TOKEN401", "Refresh Token 이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID("TOKEN401", "유효하지 않은 Refresh Token 입니다.", HttpStatus.UNAUTHORIZED),

    // User (회원)
    USER_ALREADY_EXIST("USER400", "이미 회원가입된 유저입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("USER404", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    USER_REQUIRED("USER402", "User는 반드시 필요합니다.", HttpStatus.BAD_REQUEST),
    USER_EMAIL_NOT_EXIST("USER404", "가입된 이메일이 존재하지 않습니다. 다시 입력해주세요.", HttpStatus.NOT_FOUND),
    USER_NOT_VALID("USER404", "유효한 사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_WRONG_PASSWORD("USER401", "비밀번호가 일치하지 않습니다. 다시 입력해주세요.", HttpStatus.UNAUTHORIZED),
    USER_SAME_PASSWORD("USER400", "동일한 비밀번호로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCH("PASSWORD401", "입력한 두 개의 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NO_PERMISSION("USER403", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    USER_FORBIDDEN("USER403", "유저의 권한이 부족합니다.", HttpStatus.FORBIDDEN),

    // Routine (운동 루틴)
    ROUTINE_NAME_EMPTY("ROUTINE400", "루틴 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_DUPLICATE_NAME("ROUTINE401", "이미 같은 이름의 루틴이 존재합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_NOT_FOUND("ROUTINE404", "해당 루틴을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROUTINE_FORBIDDEN("ROUTINE403", "해당 루틴에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ROUTINE_REQUIRED("ROUTINE405","Routine은 반드시 필요합니다." ,HttpStatus.BAD_REQUEST ),

    // RoutineItem (루틴 항목)
    ROUTINE_ITEM_NOT_FOUND("ROUTINEITEM404", "해당 루틴 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROUTINE_ITEM_REQUIRED("ROUTINEITEM402", "추가할 루틴 항목이 없습니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_DUPLICATE("ROUTINEITEM400", "동일한 운동 항목이 이미 루틴에 추가되어 있습니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_INVALID_ORDER("ROUTINEITEM400", "루틴 항목 순서가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_FORBIDDEN("ROUTINEITEM403", "해당 루틴 항목에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ROUTINE_ITEM_ROUTINE_REQUIRED("ROUTINEITEM400", "루틴 항목 생성 시 Routine 정보가 필요합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_EXERCISE_REQUIRED("ROUTINEITEM401", "루틴 항목 생성 시 Exercise 정보가 필요합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_SETS_INVALID("ROUTINEITEM402", "세트 수는 1 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_REPS_INVALID("ROUTINEITEM403", "반복(reps) 수는 1 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_WEIGHT_INVALID("ROUTINEITEM404", "무게(weight)는 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    ROUTINE_ITEM_ORDERNO_INVALID("ROUTINEITEM405", "정렬 순서(orderNo)는 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),

    // BodyPart (신체 부위)
    BODYPART_NAME_EMPTY("BODYPART400", "신체 부위 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    BODYPART_REQUIRED("BODYPART402", "BODYPART는 반드시 필요합니다.", HttpStatus.BAD_REQUEST),
    BODYPART_NOT_FOUND("BODYPART404", "해당 BODYPART를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

    // Exercise (운동)
    EXERCISE_NAME_EMPTY("EXERCISE400", "운동 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    EXERCISE_NOT_FOUND("EXERCISE404", "해당 운동을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Record (기록)
    RECORD_DATE_REQUIRED("RECORD401", "기록 생성 시 날짜 정보가 필요합니다.",  HttpStatus.BAD_REQUEST),
    RECORD_TOTALTIME_REQUIRED("RECORD402", "기록 생성 시 총 운동 시간이 필요합니다.", HttpStatus.BAD_REQUEST),
    RECORD_TOTALTIME_INVALID("RECORD403", "총 운동 시간은 1 이상이어야 합니다.", HttpStatus.BAD_REQUEST),

    // UserBodyPart (사용자 별 신체 부위 경험치)
    USER_BODYPART_EXP_INVALID("USERBODYPART403", "신체 부위 별 경험치는 1 이상이어야 합니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
