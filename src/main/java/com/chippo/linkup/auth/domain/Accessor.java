package com.chippo.linkup.auth.domain;

import lombok.Getter;


// 접근 권한
@Getter
public class Accessor {
    private final Long memberId;
    private final Authority authority;

    private Accessor(final Long memberId, final Authority authority){
        this.memberId = memberId;
        this.authority = authority;
    }

    public static Accessor guest(){
        return new Accessor(0L, Authority.GUEST);
    }

    public static Accessor member(final Long memberId){
        return new Accessor(memberId, Authority.MEMBER);
    }
    public Accessor admin(final Long memberId){
        return new Accessor(memberId, Authority.GUEST);
    }

    public static Accessor master(final Long memberId){
        return new Accessor(memberId, Authority.MASTER);
    }

    public boolean isMember() {
        return Authority.MEMBER.equals(authority);
    }

    public boolean isAdmin() {
        return Authority.ADMIN.equals(authority) || Authority.MASTER.equals(authority);
    }

    public boolean isMaster() {
        return Authority.MASTER.equals(authority);
    }
}
