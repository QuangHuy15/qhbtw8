package vniot.star.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name="videos")
@NamedQuery(name="Video.findAll", query="SELECT v FROM Video v")
public class Video implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VideoId")
	private String videoid;

	@Column(name = "Status")
	private int status;

	@Column(name = "Description", columnDefinition = "NVARCHAR(255)")
	private String description;

	@Column(name = "Videos", columnDefinition = "NVARCHAR(255)")
	private String videos;

	@Column(name = "Title", columnDefinition = "NVARCHAR(255)")
	private String title;

	@Column(name = "Views")
	private int views;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;

	public Video() {
	}


	public String getVideoid() {
		return videoid;
	}


	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}



	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getVideos() {
		return videos;
	}


	public void setVideos(String videos) {
		this.videos = videos;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
}
