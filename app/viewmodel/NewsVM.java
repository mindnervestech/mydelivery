package viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import models.News;

public class NewsVM {

	public int id;
	public String header;
	public String description;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String image;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String thumbnail;
	public String date;
	
	public NewsVM() {}
	
	public NewsVM(News news) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.id = news.getNewsId();
		this.header = news.getNewsHeader();
		if(news.getNewsDescription().length() > 100) {
			this.description = news.getNewsDescription().substring(0, 99)+"...";
		} else {
			this.description = news.getNewsDescription();
		}
		this.thumbnail = news.getNewsImageThumb();
		this.date = dateFormat.format(news.getNewsDate());
	}
}
