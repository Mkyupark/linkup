package com.chippo.linkup.member.service;

import com.chippo.linkup.global.exception.AuthException;
import com.chippo.linkup.global.exception.ExceptionCode;
import com.chippo.linkup.member.domain.Member;
import com.chippo.linkup.member.domain.repository.MemberRepository;
import com.chippo.linkup.member.dto.Request.MemberCreateRequest;
import com.chippo.linkup.member.dto.Response.MemberCreateResonse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberCreateResonse save(final MemberCreateRequest memberCreateRequest) {

        checkDuplicatedNickname(memberCreateRequest.getNickname());
        final Member member = Member.of(memberCreateRequest.getLoginId(), memberCreateRequest.getNickname());
        final Member saveMember = memberRepository.save(member);
        MemberCreateResonse response = new MemberCreateResonse(saveMember.getNickname());
        return response;
    }

    @Transactional(readOnly = true)
    public MemberCreateResonse getMyPageInfo(final Long memberId) {
        final Member member = memberRepository.findById(memberId).orElseThrow(() -> new AuthException(ExceptionCode.NOT_FOUND_MEMBER_ID));
        return MemberCreateResonse.from(member);
    }

    private void checkDuplicatedNickname(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new AuthException(ExceptionCode.DUPLICATED_MEMBER_NICKNAME);
        }
    }
}
