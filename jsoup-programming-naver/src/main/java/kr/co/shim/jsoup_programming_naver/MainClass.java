package kr.co.shim.jsoup_programming_naver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainClass {

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		//영화 데이터를 구한다
		
		Movies_JSOUP mj = new Movies_JSOUP();
		
		List<NaverMovieVO> list = mj.getAllNaverMovie();
		
		MoviePictures_JSOUP mpj = new MoviePictures_JSOUP();
		for(int i = 0; i < list.size(); i++) {
			int id = list.get(i).getId();
			List<String> pictureList = mpj.searchPicturesByUrl(id);
			System.out.println("pictureList=============");
			for(int j = 0; j < pictureList.size(); j++) {
				System.out.println(pictureList.get(j));
			}
			System.out.println("=======================");
		}
		System.out.println("list size : " + list.size());
		
		//출력문
		for(NaverMovieVO vo : list) {
			System.out.println(vo.toString());
		}
		/*
		NaverMovieVO vo = new NaverMovieVO();
		String movieUrl = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=191633";
		
		vo.setId(Integer.parseInt(mj.getMovieId(movieUrl)));
		vo.setTitle(mj.getMovieTitle(movieUrl));
		vo.setGrade(mj.getMovieInfoByKind(movieUrl, "grade="));
		try {
			Date openningDate = dateFormat.parse(mj.getMovieInfoByKind(movieUrl, "open="));
			vo.setOpening_date(openningDate);
			
		}catch(Exception e) {
		}
		vo.setGenre(mj.getMovieInfoByKind(movieUrl, "genre="));
		vo.setCountry(mj.getMovieInfoByKind(movieUrl, "nation="));
		int runningTime = 0;
		try {
			runningTime =Integer.parseInt(mj.getMovieRunningTime(movieUrl));
			vo.setRunning_time(runningTime);
		}catch(Exception e) {
		}
		String hitString = mj.getMovieInfoByKind(movieUrl, "view=");
		int hit = 0;
		if(hitString.indexOf("예매율") != -1)
			hit = Integer.parseInt(hitString.substring(hitString.indexOf("예매율") + 4, hitString.indexOf("예매율") + 5));
		vo.setHit(hit);
		int audienceCount = 0;
		try {
			audienceCount = Integer.parseInt(mj.getMovieAudienceCount(movieUrl));
		}catch(Exception e) {
		}
		vo.setAudience_count(audienceCount);
		vo.setStory(mj.getMovieStory(movieUrl));
		System.out.println(vo.toString());
		*/
	}

}
