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
		/*
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
		*/
		MovieMedia_JSOUP mmj = new MovieMedia_JSOUP();
		List<Pair> list = mmj.searchMediasByUrl(14448);
		System.out.println("size : " + list.size());
		for(int i = 0; i < list.size(); i++) {
			System.out.println("first : " + list.get(i).first + ", second : " + list.get(i).third);
		}
	}

}
