package ar.com.insonet.controller.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.insonet.service.FacebookServiceImpl;

import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.ResponseList;

@Controller
public class PostController {
	@Autowired
	private FacebookServiceImpl fbService;
	
	
	/*private PostController(ApplicationContext applicationContext, FacebookServiceImpl facebookService) {
		this.fbService = facebookService;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String index() {
		
		return "/post";
	}*/
	@RequestMapping(value="/facebook/post/add", params="messageTxt", method=RequestMethod.GET)
	public @ResponseBody String publishing(String messageTxt) throws Exception {
		String result = "nok";
		if(messageTxt != "") {
		    result = fbService.addPost(messageTxt);
		}
		return result;
	}
	
	@RequestMapping(value="/facebook/{fb}/post/{id}", method=RequestMethod.GET)
	public Post show(int fb, String id, HttpServletRequest request) throws FacebookException {
		//ResponseList<Post> list = null;
		//Obtenemos el post del facebook indicado
		Post post = fbService.getPost(fb, id);
		
		return post;
	}
	//You can't update a post using the Graph API.
	/*@RequestMapping(value="/facebook/{fb}/post/{id}", method=RequestMethod.POST)
	public String update(@Valid Post post) {
		
		return "redirect:/post/{id}";
	}*/
	@RequestMapping(value="/facebook/{fb}/list", method=RequestMethod.GET)
	public ResponseList<Post> list(int fb) throws Exception {
		ResponseList<Post> posts = fbService.getPosts(fb);
		
		return posts;
	}
	/*@RequestMapping(value="/facebook/{fb}/delete/{id}", method=RequestMethod.GET)
	public boolean publishing(int fb, String id, HttpServletRequest request) {
		boolean result = fbService().delPost(fb, id, request);
		return result;
	}*/
	
}
