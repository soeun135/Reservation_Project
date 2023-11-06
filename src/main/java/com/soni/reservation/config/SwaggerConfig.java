package com.soni.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.soni.reservation"))
//basePackage("zerobase.weather")하면 error controller 없앨 수 있음.
                .paths(PathSelectors.any())
//paths 기준 swagger에 표시가 될 API 정할 수 있음.
//none() : 아무 것도 안 뜸.
//ant(특정패턴) : ex) ant("/read/**") : read로 시작하는 애 모두 보여줌.
//백엔드 개발자용 admin API를 위 옵션 이용해서 숨김.
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("예약 서비스 구현 프로젝트")
                .description("점장이 등록한 매장에 대해 가입한 회원이 예약을 할 수 있는 API 입니다.")
                .version("20.0")
                .build();
    }
}