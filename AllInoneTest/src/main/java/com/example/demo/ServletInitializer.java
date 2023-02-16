package com.example.demo;

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

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		log.info("=======================================================");
		log.info("서블릿 시작한다!!!!");
		log.info("=======================================================");
		return application.sources(DemoApplication.class);
	}
	
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
		log.info("ConfigurableServletWebServerFactory 시작");
		return new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				
				log.info("postProcessContext 시작");
				
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
