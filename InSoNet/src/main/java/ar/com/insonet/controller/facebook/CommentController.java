package ar.com.insonet.controller.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.insonet.service.FacebookServiceImpl;

@Controller
public class CommentController {
	
	@Autowired
	FacebookServiceImpl facebookService;
	
	@RequestMapping(value="/facebook/{id}/comment/add", method=RequestMethod.GET)
	@ResponseBody
	public String add(@RequestParam(value = "m", required = true) String messageTxt,
			@RequestParam(value = "p", required = true) String postId,
			@PathVariable String id) throws Exception {
		String result = "nok";
		
		if(messageTxt != "" && postId != "") {
		    result = facebookService.addComment(id, messageTxt, postId);
		}
		
		return result;
	}
}
