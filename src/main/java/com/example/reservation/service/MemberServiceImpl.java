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

    private final WebClient.Builder webClientBuilder;

    @Autowired
    AuthServiceImpl authService;

    public MemberServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Member> getMemberByIdMono(Long memberId) {
        log.info("***********1***********");
        return authService.getAuthResponseMono().flatMap(authResponse ->
                        webClientBuilder.baseUrl("lb://spring-cloud-gateway-service/").build().get()
                                .uri("member-service/api/member/{memberId}", memberId)
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
