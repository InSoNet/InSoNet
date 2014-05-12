package ar.com.insonet.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ar.com.insonet.security.config.SecurityConfig;

@Configuration
@ComponentScan
//@Import(SecurityConfig.class)
public class RootConfiguration {

}
