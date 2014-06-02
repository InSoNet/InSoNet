package ar.com.insonet.test;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.controller.home.HomeController;

import junit.framework.TestCase;

public class HomeControllerTests extends TestCase {

	@Test
    public void testLoginHandlerView() throws Exception{
        HomeController controller = new HomeController();
        String param2 = null;
		String param = null;
		ModelAndView modelAndView = controller.loginHandler(param, param2);
        assertEquals("login", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        //Map modelMap = (Map)modelAndView.getModel().get("message");
        String strValue = (String) modelAndView.getModel().get("message");
        assertNotNull(strValue);
        assertEquals(strValue, "Inicio de sesión");
    }
}