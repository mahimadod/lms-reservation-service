package com.example.reservation.service;


import com.example.reservation.dto.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    AuthServiceImpl authService;
    @Override
    public Mono<Member> getMemberByIdMono(Long memberId) {
        log.info("***********1***********");
        WebClient memberClient = WebClient.create("lb://member-service");
        return authService.getAuthResponseMono().flatMap(authResponse ->
                        memberClient.get()
                                .uri("/api/member/{memberId}", memberId)
                                .header("token", authResponse.getToken())
                                .retrieve()
                                .bodyToMono(Member.class)
                ).flatMap(i->{
                    System.out.println(i);
            log.info("COMING OUT MEMBER SERVICE");
                 return Mono.just(i);
                });

    }
}
