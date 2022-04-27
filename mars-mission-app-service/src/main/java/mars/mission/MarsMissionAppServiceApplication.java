package mars.mission;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import mars.mission.util.HttpService;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition
public class MarsMissionAppServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsMissionAppServiceApplication.class,args);
    }

    @Bean
    public HttpService httpService(){
        return new HttpService();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Mars-Mission-Candidate-Service")
                .packagesToScan("mars.mission.controller")
                .pathsToMatch("/**")
                .build();
    }
}
