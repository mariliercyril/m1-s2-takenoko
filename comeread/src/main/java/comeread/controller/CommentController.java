package comeread.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import comeread.model.Comment;
import comeread.service.ComereadService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	@Qualifier("CommentService")
	private ComereadService commentService;
	
	@RequestMapping()
	public Collection<Comment> getAllComments(){
		return commentService.getAllComments();
	}
	
	@RequestMapping(value="/remove/{cid}", method=RequestMethod.DELETE)
	public void removeCommentByCid(@PathVariable int cid){
		commentService.removeCommentByCid(cid);
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateComment(@RequestBody Comment comment){
		commentService.updateComment(comment);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void insertComment(@RequestBody Comment comment){
		commentService.insertComment(comment);
	}
}
