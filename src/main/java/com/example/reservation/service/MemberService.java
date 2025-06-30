package com.example.reservation.service;

import com.example.reservation.dto.AuthResponse;
import com.example.reservation.dto.Member;
import reactor.core.publisher.Mono;

public interface MemberService {
    Mono<Member> getMemberByIdMono(Long memberId);
}
