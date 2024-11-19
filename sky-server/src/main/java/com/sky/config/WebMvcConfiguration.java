
package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;*/




@Configuration
@Slf4j
@OpenAPIDefinition(
        info = @Info(
                title = "苍穹外卖项目接口文档",
                version = "1.0",
                description = "苍穹外卖项目接口文档1.0",
                contact = @Contact(name = "Mark")
        )
)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }



/*    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("苍穹外卖项目接口文档")
                .version("2.0")
                .description("苍穹外卖项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }*//*

*/
/*     @Bean
     public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("苍穹外卖项目接口文档") // 组名，用于在界面中显示
            .packagesToScan("com.sky.controller") // 指定扫描的包
            .pathsToMatch("/**") // 匹配所有路径，相当于 PathSelectors.any()
            .build();
       }/*



   */
/*
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/

      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
         log.info("开始配置静态资源路径...");
         registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/", "classpath:/public/");

        // Swagger UI 资源
         registry.addResourceHandler("doc.html")
             .addResourceLocations("classpath:/META-INF/resources/");
         registry.addResourceHandler("/webjars/**")
             .addResourceLocations("classpath:/META-INF/resources/webjars/");
     }


}

