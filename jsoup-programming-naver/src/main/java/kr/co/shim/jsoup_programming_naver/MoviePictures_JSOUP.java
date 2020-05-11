package kr.co.shim.jsoup_programming_naver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MoviePictures_JSOUP {
	private Jsoup jsoup;
	private final static String URL = "https://movie.naver.com/movie/bi/mi/photo.nhn?code=#{code}&page=#{page}#movieEndTabMenu";

	// 한 url의 picture url로 이동하는 메서드 by movie_id
	public List<String> searchPicturesByUrl(int movieId) {
		String id = String.valueOf(movieId);
		int num = 1;
		String number = String.valueOf(num);
		String pictureUrl = URL.replace("#{code}", id)
							   .replace("#{page}", number);
		int count = 0;
		List<String> list = new ArrayList<String>();
		Document doc = null;
		try {
			doc = jsoup.connect(pictureUrl).get();
			count = getPicturesCount(doc);
			while (list.size() < count) {
				System.out.println("list size : " + list.size());
				System.out.println("count : " + count);
				getPicturesInList(doc, list);
				num++;
				number = String.valueOf(num);
				pictureUrl = URL.replace("#{code}", id)
								.replace("#{page}", number);
				doc = jsoup.connect(pictureUrl).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 총 사진 개수를 구하는 메서드
	public int getPicturesCount(Document doc) {
		Element countNode = doc.selectFirst(".obj_section2 .photo .title_area .count");
		String countStr = countNode.text();
		int count = 0;
		try {
			countStr = countStr.substring(countStr.indexOf("/") + 2, countStr.indexOf("건"));
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("count -> 0");
		}
		return count;
	}

	// 사진 리스트를 모두 리턴하는 메서드
	public void getPicturesInList(Document doc, List<String> list) {

		Elements pictures = doc.select(".photo .gallery_group li a img");
		System.out.println("pictures : "+ pictures.html());
		for (Element picture : pictures) {
			String pictureUrl = picture.attr("src");
			list.add(pictureUrl);
			System.out.println(pictureUrl);
		}
	}
}
