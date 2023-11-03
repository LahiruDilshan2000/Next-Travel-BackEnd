package lk.ijse.apigateway.filter;

import lk.ijse.apigateway.util.JwtUtil;
import lk.ijse.apigateway.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Optional;

import static java.util.stream.DoubleStream.builder;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;

/**
 * @author Lahiru Dilshan
 * @created Sat 4:49 PM on 10/21/2023
 * @project api-gateway
 **/
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidate routeValidate;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (routeValidate.isSecured.test(exchange.getRequest())){

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing authorization header !");
                }
                String header = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = null;
                if (header != null && header.startsWith("Bearer ")){
                    token = header.substring(7);
                }
                try {

                    jwtUtil.validateToken(token);

                }catch (Exception e){

                    throw new RuntimeException("Un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{ }
}
