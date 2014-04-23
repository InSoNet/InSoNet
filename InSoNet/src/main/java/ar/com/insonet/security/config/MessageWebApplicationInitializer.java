package ar.com.insonet.security.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ar.com.insonet.security.mvc.config.WebMvcConfiguration;

@ComponentScan("ar.com.insonet.security.mvc")
public class MessageWebApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfiguration.class };
    }
	
	@Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfiguration.class };
    }

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/InSoNet/**" };
	}
	
	@Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new HiddenHttpMethodFilter() };
    }
}
