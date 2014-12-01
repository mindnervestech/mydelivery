package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class News extends Model {

	@Id
	private Integer newsId;
	private String newsHeader;
	private String newsDescription;
	private String newsImage;
	private String newsImageThumb;
	private Date newsDate;
	
	public static Finder<Integer,News> find = new Finder<>(Integer.class,News.class);
	
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getNewsHeader() {
		return newsHeader;
	}
	public void setNewsHeader(String newsHeader) {
		this.newsHeader = newsHeader;
	}
	public String getNewsDescription() {
		return newsDescription;
	}
	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
	}
	public String getNewsImage() {
		return newsImage;
	}
	public void setNewsImage(String newsImage) {
		this.newsImage = newsImage;
	}
	public String getNewsImageThumb() {
		return newsImageThumb;
	}
	public void setNewsImageThumb(String newsImageThumb) {
		this.newsImageThumb = newsImageThumb;
	}
	public Date getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}
	
	public static List<News> getNewsByToDate(Date toDate) {
		Expression expr = Expr.le("newsDate", toDate);
		return find.where(expr).findList();
	}
	
}
