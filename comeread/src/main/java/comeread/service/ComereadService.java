package comeread.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import comeread.database.DB;
import comeread.model.Article;
import comeread.model.ArticleLike;
import comeread.model.Comment;
import comeread.model.User;

public abstract class ComereadService {

	@Autowired
	@Qualifier("DataBase")
	protected DB db;
	
	public abstract Collection<User> getAllUsers();
	public abstract User getUserByUid(int uid);
	public abstract void removeUserByUid(int uid);
	public abstract void updateUser(User user);
	public abstract void insertUser(User user);
	
	public abstract Collection<Article> getAllArticles();
	public abstract Collection<Article> getAllUserArticles(int uid);
	public abstract Article getArticleByAid(int aid);
	public abstract void removeArticleByAid(int aid);
	public abstract void updateArticle(Article article);
	public abstract void insertArticle(Article article);

	public abstract Collection<Comment> getAllComments();
	public abstract Collection<Comment> getAllArticleComments(int aid);
	public abstract void removeCommentByCid(int cid);
	public abstract void updateComment(Comment comment);
	public abstract void insertComment(Comment comment);
	
	public abstract Collection<ArticleLike> getAllArticleLikes();
	public abstract void removeArticleLikeByAlid(int alid);
	public abstract void insertArticleLike(ArticleLike articleLike);
}