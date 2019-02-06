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

import comeread.model.ArticleLike;
import comeread.service.ComereadService;

@RestController
@RequestMapping("/article-likes")
public class ArticleLikeController {
	
	@Autowired
	@Qualifier("ArticleLikeService")
	private ComereadService articleLikeService;
	
	@RequestMapping()
	public Collection<ArticleLike> getAllArticleLikes(){
		return articleLikeService.getAllArticleLikes();
	}

	@RequestMapping(value="/remove/{alid}", method=RequestMethod.DELETE)
	public void removeArticleLikeByAlid(@PathVariable int alid){
		articleLikeService.removeArticleLikeByAlid(alid);
	}

	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void insertArticleLike(@RequestBody ArticleLike articleLike){
		articleLikeService.insertArticleLike(articleLike);
	}
}
