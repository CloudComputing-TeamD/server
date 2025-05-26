package com.project.cloud.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

@EnableAspectJAutoProxy
@WebMvcTest
@WithMockUser
@Import({TestBeanConfig.class, MockAllServiceBeanFactoryPostProcessor.class, })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMvcTest {

}
