package comeread.model;

public class ArticleLike {
	
	private int alid;
	private int likerId;
	private int likedId;

	public ArticleLike(int alid, int likerId, int likedId) {
		this.alid = alid;
		this.likerId = likerId;
		this.likedId = likedId;
	}
	
	public ArticleLike(){}
	
	public int getAlid() {return alid;}
	public int getLikerId() {return likerId;}
	public int getLikedId() {return likedId;}
	
	public void setAlid(int alid) {this.alid = alid;}
	public void setLikerId(int likerId) {this.likerId = likerId;}
	public void setLikedId(int likedId) {this.likedId = likedId;}
}
