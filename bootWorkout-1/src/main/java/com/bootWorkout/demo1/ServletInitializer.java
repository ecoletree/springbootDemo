package com.bootWorkout.demo1;

import java.util.Collections;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {
	
	Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * pom.xml에 javax-servlet dependency 추가
	 * Springboot 환경에서 web.xml 의 jsp-config 사용하기 
	 * (w.SpringBootServletInitializer)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(BootWorkout1Application.class);
	}
	
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
		log.info("ConfigurableServletWebServerFactory 시작");
		return new TomcatServletWebServerFactory() {
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
	}

}
