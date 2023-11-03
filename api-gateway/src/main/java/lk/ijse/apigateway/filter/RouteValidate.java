package lk.ijse.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Lahiru Dilshan
 * @created Sat 5:01 PM on 10/21/2023
 * @project api-gateway
 **/
@Component
public class RouteValidate {

    public static final List<String> openApiEndPoints = List.of(
            "/auth/register",
            "/auth/token",
            "/vehicle/get",
            "/vehicle/getAll",
            "/hotel/get",
            "/hotel/getAll",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
