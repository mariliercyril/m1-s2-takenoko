package comeread.model;


public class Article {

	private int aid;
	private int authorId;
	private String title;
	private String text;
	private String img;

	public Article(int aid, int uid, String title, String text, String img) {
		super();
		this.aid = aid;
		this.authorId = uid;
		this.title = title;
		this.text = text;
		this.img = img;
	}

	public Article() {}

	public int getAid() {return aid;}
	public int getAuthorId() {return authorId;}
	public String getTitle() {return title;}
	public String getText() {return text;}
	public String getImg() {return img;}
	
	public void setAid(int aid) {this.aid = aid;}
	public void setAuthorId(int uid) {this.authorId = uid;}
	public void setTitle(String title) {this.title = title;}
	public void setText(String text) {this.text = text;}
	public void setImg(String img) {this.img = img;}
}
