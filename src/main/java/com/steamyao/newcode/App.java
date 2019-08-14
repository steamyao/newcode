package com.steamyao.newcode;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.steamyao"})
@MapperScan("com.steamyao.newcode.dao")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);

    }


    // Tomcat Cookie 处理配置 Bean
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

}
