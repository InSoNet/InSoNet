package ar.com.insonet.security.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ar.com.insonet.security.mvc.config.WebMvcConfiguration;
import org.springframework.core.annotation.Order;

//@ComponentScan("ar.com.insonet.security.mvc")
@Order(2)
public class MvcWebApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SecurityConfig.class, RootConfiguration.class };
    }
	
	@Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfiguration.class };
    }

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/InSoNet" };
	}
	
	@Override
    protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[] { characterEncodingFilter};
    }
}
