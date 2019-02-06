package comeread.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import comeread.model.Article;
import comeread.model.ArticleLike;
import comeread.model.Comment;
import comeread.model.User;

@Service
@Qualifier("ArticleLikeService")
public class ArticleLikeService extends ComereadService{

	public Collection<ArticleLike> getAllArticleLikes() {
		return db.getAllArticleLikes();
	}

	public void removeArticleLikeByAlid(int alid) {
		db.removeArticleLikeByAlid(alid);
	}

	public void insertArticleLike(ArticleLike articleLike) {
		db.insertArticleLike(articleLike);
	}

	@Override
	public Collection<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUid(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUserByUid(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Article> getAllArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Article> getAllUserArticles(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getArticleByAid(int aid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeArticleByAid(int aid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Comment> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

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

}
