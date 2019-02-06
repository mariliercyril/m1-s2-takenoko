package comeread.model;

public class Comment {

	private int cid;
	private int authorId;
	private int articleId;
	private String text;
	
	public Comment(int cid, int authorId, int articleId, String text) {
		this.cid = cid;
		this.authorId = authorId;
		this.articleId = articleId;
		this.text = text;
	}
	
	public Comment() {}
	
	public int getCid() {return cid;}
	public int getAuthorId() {return authorId;}
	public int getArticleId() {return articleId;}
	public String getText() {return text;}
	
	public void setCid(int cid) {this.cid = cid;}
	public void setAuthorId(int authorId) {this.authorId = authorId;}
	public void setArticleId(int articleId) {this.articleId = articleId;}
	public void setText(String text) {this.text = text;}
}
