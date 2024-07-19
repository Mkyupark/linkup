package com.chippo.linkup.member.dto.Response;

import com.chippo.linkup.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WriterResponse {

    private final String nickname;

    public static WriterResponse of(final Member member) {
        return new WriterResponse(member.getNickname());
    }
}