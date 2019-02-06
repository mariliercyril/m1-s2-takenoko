package comeread.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import comeread.model.Article;
import comeread.model.ArticleLike;
import comeread.model.Comment;
import comeread.model.User;

@Service
@Qualifier("ArticleService")
public class ArticleService extends ComereadService{
	
	public Collection<Article> getAllArticles(){
		return db.getAllArticles();
	}
	
	public Collection<Article> getAllUserArticles(int uid){
		return db.getAllUserArticles(uid);
	}
	
	public Article getArticleByAid(int aid){
		return db.getArticleByAid(aid);
	}
	
	public void removeArticleByAid(int aid){
		db.removeArticleByAid(aid);
	}
	
	public void updateArticle(Article article){
		db.updateArticle(article);
	}
	
	public void insertArticle(Article article){
		db.insertArticle(article);
	}

	@Override
	public Collection<User> getAllUsers() {
		return null;}
	@Override
	public User getUserByUid(int uid) {
		return null;}
	@Override
	public void removeUserByUid(int uid) {}
	@Override
	public void updateUser(User user) {}
	@Override
	public void insertUser(User user) {}

	@Override
	public Collection<Comment> getAllArticleComments(int aid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCommentByCid(int cid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Comment> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ArticleLike> getAllArticleLikes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeArticleLikeByAlid(int alid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertArticleLike(ArticleLike articleLike) {
		// TODO Auto-generated method stub
		
	}
}
