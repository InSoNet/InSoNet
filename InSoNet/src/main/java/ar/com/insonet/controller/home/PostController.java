package ar.com.insonet.controller.home;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Post;
import ar.com.insonet.service.FacebookServiceImpl;

@Controller
public class PostController {

	
	//private Post post;
	@Autowired
	FacebookServiceImpl facebookService;
	/*@Autowired
    private Validator validator;
	
	@ModelAttribute("post")
    private Post getPost() {
        return post;
    }
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST)
	public String createPost(@ModelAttribute("post") Post post,  BindingResult result, HttpServletRequest request) throws Exception {
		
		validator.validate(post, result);
		if (result.hasErrors()) {
			return request.getRemoteAddr();
    	}
		
		return "redirect:" + request.getRemoteAddr();
	}*/
	
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST)
	public String publishing(@RequestParam("messageTxt") String messageTxt,
			@RequestParam(value = "publishingIn", required = false) String publishingIn,
			@RequestParam(value = "privacy", required = false) String privacy,
			@RequestPart("filePhoto") MultipartFile filePhoto, HttpServletRequest request) throws Exception {
		
		String fileName = null;
		
		if(filePhoto.getOriginalFilename() != ""){
			byte file[] = filePhoto.getBytes();
			
			Path path = Paths.get("c:\\Usuarios\\Developer\\resources\\tmp", filePhoto.getOriginalFilename());
			OutputStream out = Files.newOutputStream(path, CREATE);
			
			out.write(file, 0, file.length);
			out.close();
			
			fileName = filePhoto.getOriginalFilename();
		}
		
		if(messageTxt != "") {
		    facebookService.addPost(messageTxt, privacy, fileName);
		}
		
		URL url = new URL(request.getHeader("Referer"));
		String path = url.getPath();
		int index = path.indexOf("/InSoNet");
		if(index >= 0) {
		    path = path.substring(8);
		}
		
		return "redirect:" + path;
	}
}
