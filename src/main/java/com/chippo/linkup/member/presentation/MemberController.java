package com.chippo.linkup.member.presentation;

import com.chippo.linkup.auth.Auth;
import com.chippo.linkup.auth.MemberOnly;
import com.chippo.linkup.auth.domain.Accessor;
import com.chippo.linkup.member.dto.Request.MemberCreateRequest;
import com.chippo.linkup.member.dto.Response.MemberCreateResonse;
import com.chippo.linkup.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Log4j2
public class MemberController {

    public final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberCreateResonse> createMember(@RequestBody @Valid final MemberCreateRequest memberCreateRequest){
        final MemberCreateResonse response = memberService.save(memberCreateRequest);

        return ResponseEntity.created(URI.create("/mypage" + response)).body(response);
    }

    @GetMapping
    @MemberOnly
    public ResponseEntity<MemberCreateResonse> getMyInfo(@Auth final Accessor accessor) {
        final MemberCreateResonse myPageResponse = memberService.getMyPageInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(myPageResponse);
    }
}
