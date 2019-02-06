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

import comeread.model.Article;
import comeread.service.ComereadService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	@Qualifier("ArticleService")
	private ComereadService articleService;
	
	@RequestMapping()
	public Collection<Article> getAllArticles(){
		return articleService.getAllArticles();
	}
	
	@RequestMapping(value="/users/{uid}") //GET IS THE DEFAULT METHOD
	public Collection<Article> getAllUserArticles(@PathVariable("uid") int uid){
		return articleService.getAllUserArticles(uid);
	}
	
	@RequestMapping(value="/{aid}")
	public Article getArticleByAid(@PathVariable("aid") int aid){
		return articleService.getArticleByAid(aid);
	}
	
	@RequestMapping(value="/{aid}", method=RequestMethod.DELETE)
	public void removeArticleByAid(@PathVariable("aid") int aid){
		articleService.removeArticleByAid(aid);
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateArticle(@RequestBody Article article){
		articleService.updateArticle(article);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void insertArticle(@RequestBody Article article){
		articleService.insertArticle(article);
	}
}
