package com.example.reservation.service;


import com.example.reservation.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    AuthServiceImpl authService;
    @Override
    public Mono<Member> getMemberByIdMono(Long memberId) {

        WebClient memberClient = WebClient.create("http://localhost:8085/member-service");
        return authService.getAuthResponseMono().flatMap(authResponse ->
                        memberClient.get()
                                .uri("/api/member/{memberId}", memberId)
                                .header("token", authResponse.getToken())
                                .retrieve()
                                .bodyToMono(Member.class)
                ).flatMap(i->{
                    System.out.println(i);
                 return Mono.just(i);
                });

    }
}
