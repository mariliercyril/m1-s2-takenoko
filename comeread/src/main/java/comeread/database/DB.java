package comeread.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import comeread.model.Article;
import comeread.model.Comment;
import comeread.model.ArticleLike;
import comeread.model.User;

@Repository
@Qualifier("DataBase")
public class DB {

	private static Map<Integer, User> users;
	private static Map<Integer, Article> articles;
	private static Map<Integer, Comment> comments;
	private static Map<Integer, ArticleLike> articleLikes;
	
	static{
		users = new HashMap<Integer, User>(){
			private static final long serialVersionUID = 1L;{
			put(0, new User(0, "Zero", "Zero@email.com", "#zero000", ""));
			put(1, new User(1, "One", "One@email.com", "#one111", ""));
			put(2, new User(2, "Two", "Two@email.com", "#two222", ""));
			put(3, new User(3, "Three", "Three@email.com", "#three333", ""));
			put(4, new User(4, "Four", "Four@email.com", "#four444", ""));
		}};

		articles = new HashMap<Integer, Article>(){
			private static final long serialVersionUID = 1L;{
				put(0, new Article(0, 0, "Story of Zero", "Zero is the first element in the set of integers.", "zero.jpg"));
				put(1, new Article(1, 0, "Story of One", "One is the second element in the set of integers.", "one.jpg"));
				put(2, new Article(2, 0, "Story of Two", "Two is the third element in the set of integers.", "two.jpg"));
				put(3, new Article(3, 3, "Story of Three", "Three is the forth element in the set of integers.", "three.jpg"));
				put(4, new Article(4, 4, "Story of Four", "Four is the fifth element in the set of integers.", "four.jpg"));
		}};

		comments = new HashMap<Integer, Comment>(){
			private static final long serialVersionUID = 1L;{
				put(0, new Comment(0, 0, 1, "0 commented on 1"));
				put(1, new Comment(1, 2, 0, "2 commented on 0"));
				put(2, new Comment(2, 1, 3, "1 commented on 3"));
				put(3, new Comment(3, 3, 2, "3 commented on 2"));
		}};

		articleLikes = new HashMap<Integer, ArticleLike>(){
			private static final long serialVersionUID = 1L;{
				put(0, new ArticleLike(0, 0, 1));
				put(1, new ArticleLike(1, 2, 0));
				put(2, new ArticleLike(2, 1, 3));
				put(3, new ArticleLike(3, 3, 2));
		}};
	}
	
	public Collection<User> getAllUsers(){
		return users.values();
	}
	
	public User getUserByUid(int uid){
		return users.get(uid);
	}
	
	public void removeUserByUid(int uid){
		users.remove(uid);
	}
	
	public void updateUser(User user){
		User tmpUser = users.get(user.getUid());
		tmpUser.setUsername(user.getUsername());
		tmpUser.setEmail(user.getEmail());
		tmpUser.setPassword(user.getPassword());
		tmpUser.setType(user.getType());
		tmpUser.setQuote(user.getQuote());
		users.replace(tmpUser.getUid(), tmpUser);
	}
	
	public void insertUser(User user){
		users.put(user.getUid(), user);
	}
	
	public Collection<Article> getAllArticles(){
		return articles.values();
	}

	public Collection<Article> getAllUserArticles(int uid) {
		List<Article> userArticles = new ArrayList<>();
		for(HashMap.Entry<Integer, Article> entry : articles.entrySet()){
			if(entry.getValue().getAuthorId() == uid){
				userArticles.add(entry.getValue());
			}
		}
		return userArticles;
	}
	
	public Article getArticleByAid(int aid){
		return articles.get(aid);
	}
	
	public void removeArticleByAid(int aid){
		articles.remove(aid);
	}
	
	public void updateArticle(Article article){
		Article tmpArticle = articles.get(article.getAid());
		tmpArticle.setAuthorId(article.getAuthorId());
		tmpArticle.setTitle(article.getTitle());
		tmpArticle.setText(article.getText());
		tmpArticle.setImg(article.getImg());
		articles.replace(tmpArticle.getAid(), tmpArticle);
	}
	
	public void insertArticle(Article article){
		articles.put(article.getAid(), article);
	}
	
	public Collection<Comment> getAllComments(){
		return comments.values();
	}

	public Collection<Comment> getAllArticleComments(int aid) {
		List<Comment> articleComments = new ArrayList<>();
		for(HashMap.Entry<Integer, Comment> entry : comments.entrySet()){
			if(entry.getValue().getArticleId() == aid){
				articleComments.add(entry.getValue());
			}
		}
		return articleComments;
	}
	
	public void removeCommentByCid(int cid){
		comments.remove(cid);
	}
	
	public void updateComment(Comment comment){
		Comment tmpComment = comments.get(comment.getCid());
		tmpComment.setCid(comment.getCid());
		tmpComment.setAuthorId(comment.getAuthorId());
		tmpComment.setArticleId(comment.getArticleId());
		tmpComment.setText(comment.getText());
		comments.replace(tmpComment.getCid(), tmpComment);
	}
	
	public void insertComment(Comment comment){
		comments.put(comment.getCid(), comment);
	}
	
	public Collection<ArticleLike> getAllArticleLikes(){
		return articleLikes.values();
	}
	
	public void removeArticleLikeByAlid(int alid){
		articleLikes.remove(alid);
	}
	
	public void insertArticleLike(ArticleLike articleLike){
		articleLikes.put(articleLike.getAlid(), articleLike);
	}
}