package com.example.demo;

import java.util.Collections;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import kr.co.ecoletree.common.provider.JwtTokenProvider;

/*
 * bean 으로 등록될 component를 스캔해준다 
 * <context:component-scan base-package="kr.co.ecoletree" />
 * basePackages = {"base 패키지","추가할 패키지"}
 * basePackages 는 String[] 형태로 추가
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo","kr.co.ecoletree"})
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				
				super.postProcessContext(context);
                JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
                jspPropertyGroup.addUrlPattern("*.jsp");
                jspPropertyGroup.setPageEncoding("UTF-8");
                jspPropertyGroup.setScriptingInvalid("true");
                jspPropertyGroup.addIncludePrelude("/WEB-INF/views/include/globalHeader.jsp");
                jspPropertyGroup.setTrimWhitespace("true");
                JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
                context.setJspConfigDescriptor(new JspConfigDescriptorImpl(Collections.singletonList(jspPropertyGroupDescriptor), Collections.emptyList()));
			}
		};
		
		// AJP 설정
		//tomcat.addAdditionalTomcatConnectors(createAjpConnector());
		
		return tomcat;
	}

}
