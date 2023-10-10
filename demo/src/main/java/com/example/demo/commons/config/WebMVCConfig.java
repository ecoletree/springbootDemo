package com.example.demo.commons.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;

import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import kr.co.ecoletree.common.auth.ETLoginCheckInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer, JspConfigDescriptor {
	
	Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 인터 셉터 정의
	 *  /resource 및 /jslib 로 접근하는 경로는 인터 셉터 제외 한다
	 */
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(new ETLoginCheckInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/resources/**")
				.excludePathPatterns("/jslib/**");
	}
	
	/**
	 * Resource Handler 정의
	 *  /static 폴더에 있는 것들을 전부 Resource 처리
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
        
        registry.addResourceHandler("/jslib/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false) // 버전 관계 없이 처리 하도록
        ;
    }
    
    /**
     * 파일 업로드 Resolver 처리
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        return commonsMultipartResolver;
    }
    
    /**
     * Tile ViewResolver 관련 메소드
     * @return
     */
    @Bean
    public UrlBasedViewResolver viewResolver() {
    	UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
    	tilesViewResolver.setViewClass(TilesView.class);
    	tilesViewResolver.setOrder(1);
    	return tilesViewResolver;
    }

    /**
     * Tile Config 관련 메소드
     * @return
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer configurer = new TilesConfigurer();

        configurer.setDefinitions(new String[]{"/WEB-INF/tiles/tiles-defs.xml"});
        configurer.setCheckRefresh(true);
        return configurer;
    }
    
    private Collection<JspPropertyGroupDescriptor> jspPropertyGroups = new LinkedHashSet<JspPropertyGroupDescriptor>();
    
    private Collection<TaglibDescriptor> taglibs = new HashSet<TaglibDescriptor>();
 
    @Override
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
        JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
        jspPropertyGroup.addUrlPattern("*.jsp");
        jspPropertyGroup.setPageEncoding("UTF-8");
        jspPropertyGroup.setScriptingInvalid("true");
        jspPropertyGroup.addIncludePrelude("/WEB-INF/views/include/globalHeader.jsp");
        jspPropertyGroup.setTrimWhitespace("true");
        JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
        jspPropertyGroups.add(jspPropertyGroupDescriptor);
        return jspPropertyGroups;
    }
 
    @Override
    public Collection<TaglibDescriptor> getTaglibs() {
        return taglibs;
    }

}
