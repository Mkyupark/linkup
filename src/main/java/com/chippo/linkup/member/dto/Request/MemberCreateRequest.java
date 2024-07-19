package com.chippo.linkup.member.dto.Request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    @NotNull(message = "아이디를 입력해주세요")
    private final String loginId;


    @NotNull(message = "별칭을 입력해주세요")
    private final String nickname;
}
