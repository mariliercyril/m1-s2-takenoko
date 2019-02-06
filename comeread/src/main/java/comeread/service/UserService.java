package comeread.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import comeread.model.Article;
import comeread.model.ArticleLike;
import comeread.model.Comment;
import comeread.model.User;

@Service
@Qualifier("UserService")
public class UserService extends ComereadService{
	
	public Collection<User> getAllUsers(){
		return db.getAllUsers();
	}
	
	public User getUserByUid(int uid){
		return db.getUserByUid(uid);
	}
	
	public void removeUserByUid(int uid){
		db.removeUserByUid(uid);
	}
	
	public void updateUser(User user){
		db.updateUser(user);
	}
	
	public void insertUser(User user){
		db.insertUser(user);
	}

	@Override
	public Collection<Article> getAllArticles() {return null;}
	@Override
	public Collection<Article> getAllUserArticles(int uid) {return null;}
	@Override
	public Article getArticleByAid(int aid) {return null;}
	@Override
	public void removeArticleByAid(int aid) {}
	@Override
	public void updateArticle(Article article) {}
	@Override
	public void insertArticle(Article article) {}

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
