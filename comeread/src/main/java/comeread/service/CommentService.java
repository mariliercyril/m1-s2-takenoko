package comeread.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import comeread.model.Article;
import comeread.model.ArticleLike;
import comeread.model.Comment;
import comeread.model.User;

@Service
@Qualifier("CommentService")
public class CommentService extends ComereadService{

	public Collection<Comment> getAllComments(){
		return db.getAllComments();
	}

	public Collection<Comment> getAllArticleComments(int aid){
		return db.getAllArticleComments(aid);
	}
	
	public void removeCommentByCid(int cid){
		db.removeCommentByCid(cid);
	}
	
	public void updateComment(Comment comment){
		db.updateComment(comment);
	}
	
	public void insertComment(Comment comment){
		db.insertComment(comment);
	}

	@Override
	public Collection<User> getAllUsers() {
		return null;
	}

	@Override
	public User getUserByUid(int uid) {
		return null;
	}

	@Override
	public void removeUserByUid(int uid) {
		
	}

	@Override
	public void updateUser(User user) {
		
	}

	@Override
	public void insertUser(User user) {
		
	}

	@Override
	public Collection<Article> getAllArticles() {
		return null;
	}

	@Override
	public Collection<Article> getAllUserArticles(int uid) {
		return null;
	}

	@Override
	public Article getArticleByAid(int aid) {
		return null;
	}

	@Override
	public void removeArticleByAid(int aid) {
		
	}

	@Override
	public void updateArticle(Article article) {
		
	}

	@Override
	public void insertArticle(Article article) {
		
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
