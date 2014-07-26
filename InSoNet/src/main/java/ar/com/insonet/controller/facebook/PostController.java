package ar.com.insonet.controller.facebook;

import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import ar.com.insonet.service.FacebookServiceImpl;

import facebook4j.FacebookException;
import facebook4j.Media;
import facebook4j.Post;
import facebook4j.ResponseList;
import static java.nio.file.StandardOpenOption.*;

@Controller
@SessionAttributes("privacy")
@MultipartConfig
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
	
	@RequestMapping(value="/facebook/post/add", method=RequestMethod.POST)
	public String publishing(@RequestParam("messageTxt") String messageTxt,
			@RequestParam(value = "publishingIn", required = false) String publishingIn,
			@RequestPart("filePhoto") MultipartFile filePhoto) throws Exception {
		
		String fileName = null;
		
		if(filePhoto.getOriginalFilename() != ""){
			byte file[] = filePhoto.getBytes();
			
			Path path = FileSystems.getDefault().getPath("c:\\Projects\\Java\\InSoNetDAO\\InSoNet\\InSoNet\\src\\main\\webapp\\resources\\tmp", filePhoto.getOriginalFilename());
			OutputStream out = Files.newOutputStream(path, CREATE); 
			
			out.write(file, 0, file.length);
			out.close();
			fileName = filePhoto.getOriginalFilename();
		}
		
		if(messageTxt != "") {
		    fbService.addPost(messageTxt, fileName);
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping(value="/facebook/post/file", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public String postFile(@RequestPart("filePhoto") MultipartFile filePhoto) throws Exception {
		String result = null;
		
		if(filePhoto.getOriginalFilename() != ""){
			byte file[] = filePhoto.getBytes();
			
			Path path = FileSystems.getDefault().getPath("c:\\Projects\\Java\\InSoNetDAO\\InSoNet\\InSoNet\\src\\main\\webapp\\resources\\tmp", filePhoto.getOriginalFilename());
			OutputStream out = Files.newOutputStream(path, CREATE); 
			
			out.write(file, 0, file.length);
			out.close();
			
			//if(filePhoto.getName() != "") {
			    result = "{\"result\": \"ok\", \"files\": \"" + filePhoto.getOriginalFilename() + "\"}";
			//}
		} else {
			result = "{\"result\": \"ok\", \"files\": \"\"}";
		}
		
		return result;
	}
	
	@RequestMapping(value="/facebook/post/submit", method=RequestMethod.POST,  produces="application/json")
	@ResponseBody
	public String postSubmit(@RequestParam("messageTxt") String messageTxt,
			@RequestParam(value = "publishingIn", required = false) String publishingIn,
			@RequestParam(value = "fileName", required = false) String fileName) throws Exception {
		
		String result = "nok";
		Media mediaFile = null;
		
		if(fileName == "") {
			fileName = null;
			//Path path = FileSystems.getDefault().getPath("c:\\Projects\\Java\\InSoNetDAO\\InSoNet\\InSoNet\\src\\main\\webapp\\resources\\tmp", fileName);
			//InputStream file = Files.newInputStream(path); 
			//mediaFile = new Media(fileName, file);
			//file.close();
		}
		
        if(messageTxt != "") {
        	//result = fbService.addPost(messageTxt, mediaFile);
        	result = fbService.addPost(messageTxt, fileName);
		    result = "{\"result\": \""+result+"\"}";
		}
		
		return result;
	}
	
	@RequestMapping(value="/facebook/{fb}/post/{id}", method=RequestMethod.GET)
	public Post show(@PathVariable int fb, @PathVariable String id, HttpServletRequest request) throws FacebookException {
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
	public ResponseList<Post> list(@PathVariable int fb) throws Exception {
		ResponseList<Post> posts = fbService.getPosts(fb);
		
		return posts;
	}
	/*@RequestMapping(value="/facebook/{fb}/delete/{id}", method=RequestMethod.GET)
	public boolean publishing(int fb, String id, HttpServletRequest request) {
		boolean result = fbService().delPost(fb, id, request);
		return result;
	}*/
	
}
