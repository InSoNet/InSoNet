package ar.com.insonet.security.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
//import org.springframework.security.web.context.*;

@Order(2)
public class SecurityInitializer extends
		AbstractSecurityWebApplicationInitializer {
	//Descomentar si no usamos Spring MVC, de lo contrario no es necesario.
	/*public SecurityInitializer() {
        super(SecurityConfig.class);
    }*/
}
