package com.chippo.linkup.member.dto.Response;

import com.chippo.linkup.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateResonse {

    private final String nickname;

    public static MemberCreateResonse from(final Member member) {
        return new MemberCreateResonse(member.getNickname());
    }
}