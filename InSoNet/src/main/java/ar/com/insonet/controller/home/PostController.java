package ar.com.insonet.controller.home;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.service.FacebookServiceImpl;

@Controller
public class PostController {

	/*@Autowired
	FacebookServiceImpl facebookService;
	
	@ModelAttribute("post")
    private Post getPost() {
        return post;
    }
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST)
	public String createPost(@ModelAttribute("post")Post post) throws Exception {
		
		return "redirect:/index";
	}*/
}
