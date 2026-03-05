package org.example.gateway.security;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends AbstractGatewayFilterFactory {
    private final JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpCookie cookie = request.getCookies().getFirst("ATOKEN");

            if (cookie == null) {
                log.error("토큰 없음");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            String token = cookie.getValue();

            Long userIdx = jwtUtil.getUserIdx(token);
            String userName = jwtUtil.getUsername(token);

            ServerHttpRequest newRequest = exchange.getRequest().mutate()
                    .header("X-User-Idx", ""+userIdx)
                    .header("X-User-Name", ""+userName)
                    .build();

            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

            return chain.filter(newExchange);
        });
    }
}