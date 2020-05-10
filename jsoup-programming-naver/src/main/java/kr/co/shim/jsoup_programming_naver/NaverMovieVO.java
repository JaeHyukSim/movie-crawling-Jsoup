package kr.co.shim.jsoup_programming_naver;

import java.util.Date;

public class NaverMovieVO {
	private int id;
	private String title;
	private String grade;
	private Date opening_date;
	private String genre;
	private String country;
	private Date running_time;
	private int hit;
	private int audience_count;
	private String story;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getOpening_date() {
		return opening_date;
	}

	public void setOpening_date(Date opening_date) {
		this.opening_date = opening_date;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getRunning_time() {
		return running_time;
	}

	public void setRunning_time(Date running_time) {
		this.running_time = running_time;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getAudience_count() {
		return audience_count;
	}

	public void setAudience_count(int audience_count) {
		this.audience_count = audience_count;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	@Override
	public String toString() {
		return "NaverMovieVO [id=" + id + ", title=" + title + ", grade=" + grade + ", opening_date=" + opening_date
				+ ", genre=" + genre + ", country=" + country + ", running_time=" + running_time + ", hit=" + hit
				+ ", audience_count=" + audience_count + ", story=" + story + "]";
	}

}
