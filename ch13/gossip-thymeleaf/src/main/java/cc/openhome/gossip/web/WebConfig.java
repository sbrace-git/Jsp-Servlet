package cc.openhome.gossip.web;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@PropertySource("classpath:path.properties")
@ComponentScan(basePackages = {"cc.openhome.gossip.controller", "cc.openhome.gossip.aspect"})
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public PolicyFactory getPolicyFactory() {
        return new HtmlPolicyBuilder()
                .allowElements("a", "b", "i", "del", "pre", "code", "big", "small")
                .allowUrlProtocols("http", "https")
                .allowAttributes("href").onElements("a")
                .requireRelNofollowOnLinks()
                .toFactory();
    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
//        internalResourceViewResolver.setSuffix(".jsp");
//        internalResourceViewResolver.setExposeContextBeansAsAttributes(true);
//        return internalResourceViewResolver;
//    }

    @Bean
    public ServletContextTemplateResolver templateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver(servletContext);
        servletContextTemplateResolver.setCacheable(false);
        servletContextTemplateResolver.setPrefix("/WEB-INF/templates/");
        servletContextTemplateResolver.setSuffix(".html");
        servletContextTemplateResolver.setTemplateMode(TemplateMode.HTML);
        return servletContextTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine(ServletContextTemplateResolver servletContextTemplateResolver) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(servletContextTemplateResolver);
        return springTemplateEngine;
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        thymeleafViewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        thymeleafViewResolver.setCache(false);
        return thymeleafViewResolver;
    }
}

