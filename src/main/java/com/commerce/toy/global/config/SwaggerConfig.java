package com.commerce.toy.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
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
	private static final String API_NAME = "The Commerce API";
	private static final String API_VERSION = "0.0.1";
	private static final String API_DESCRIPTION = "더 커머스 과제 API 명세서";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.directModelSubstitute(Pageable.class, SwaggerPageable.class)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.commerce.toy"))
			.paths(PathSelectors.any())
			.build();
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(API_NAME)
			.version(API_VERSION)
			.description(API_DESCRIPTION)
			.build();
	}
	@ApiModel(value = "페이징 처리 DTO", description = "페이징 처리를 위한 DTO 입니다.")
	@Getter
	private static class SwaggerPageable {

		@ApiParam(value = "페이지당 content 개수 (0..N)", example = "0")
		@ApiModelProperty(value = "페이지당 content 개수 (0..N)")
		@Nullable
		private Integer pageSize;

		@ApiParam(value = "조회할 페이지 번호 (0..N)", example = "0")
		@ApiModelProperty(value = "조회할 페이지 번호 (0..N)")
		@Nullable
		private Integer page;

		@ApiParam(value = "정렬 기준 (가입일/이름)", example = "createdAt,desc / name,asc")
		@ApiModelProperty(value = "정렬 기준 (가입일/이름)")
		@Nullable
		private String sort;

	}
}
