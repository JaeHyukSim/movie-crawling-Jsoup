package kr.co.shim.jsoup_programming_naver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Movies_JSOUP {
	private Jsoup jsoup;
	private Document doc;
	private final static String NAVER_URL = "https://movie.naver.com/movie/sdb/browsing/bmovie_year.nhn";
	private final static String EXCEPTION_MESSAGE = "fail to get document";
	private final static String QUERY_SELECTER_SEARCH_YEAR = "div#content div.article div#cbody table tbody tr td a";
	private final static String QUERY_SELECTOR_SEARCH_LIST = "#cbody #old_content ul.directory_list > li > a";

	public Movies_JSOUP() {
		try {
			doc = jsoup.connect(NAVER_URL).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
	}

	// VO형태로 모든 뮤비를 출력해주는 메서드
	public List<NaverMovieVO> getAllNaverMovie() {
		List<NaverMovieVO> res = new ArrayList<NaverMovieVO>();
		List<String> list;
		List<List<String>> movieList = new ArrayList<List<String>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		list = searchYearToGetMovies();

		for (int i = 0; i < list.size(); i++) {
			movieList.add(searchListToGetMovies(list.get(i)));
		}

		for (int i = 0; i < movieList.size(); i++) {
			for (int j = 0; j < movieList.get(i).size(); j++) {
				NaverMovieVO vo = new NaverMovieVO();
				String movieUrl = movieList.get(i).get(j);
				
				vo.setId(Integer.parseInt(getMovieId(movieUrl)));
				vo.setTitle(getMovieTitle(movieUrl));
				vo.setGrade(getMovieInfoByKind(movieUrl, "grade="));
				try {
					Date openningDate = dateFormat.parse(getMovieInfoByKind(movieUrl, "open="));
					vo.setOpening_date(openningDate);
					
				}catch(Exception e) {
				}
				vo.setGenre(getMovieInfoByKind(movieUrl, "genre="));
				vo.setCountry(getMovieInfoByKind(movieUrl, "nation="));
				int runningTime = 0;
				try {
					runningTime =Integer.parseInt(getMovieRunningTime(movieUrl));
					vo.setRunning_time(runningTime);
				}catch(Exception e) {
				}
				String hitString = getMovieInfoByKind(movieUrl, "view=");
				int hit = 0;
				if(hitString.indexOf("예매율") != -1)
					hit = Integer.parseInt(hitString.substring(hitString.indexOf("예매율") + 4, hitString.indexOf("예매율") + 5));
				vo.setHit(hit);
				int audienceCount = 0;
				try {
					audienceCount = Integer.parseInt(getMovieAudienceCount(movieUrl));
				}catch(Exception e) {
				}
				vo.setAudience_count(audienceCount);
				vo.setStory(getMovieStory(movieUrl));

				res.add(vo);
				System.out.println(vo.toString());
			}
		}
		return res;
	}

	// 모든 HTML을 보여주는 메서드
	public String getHTML() {
		String html = "";

		html = doc.outerHtml();

		return html;
	}

	// 모든 년도를 탐색하는 메서드 - 핵심 메서드
	public List<String> searchYearToGetMovies() {
		List<String> list = new ArrayList<String>();
		Elements table = doc.select(QUERY_SELECTER_SEARCH_YEAR);

		for (Element e : table) {
			String year = e.attr("href");
			String url = (NAVER_URL).substring(0, NAVER_URL.indexOf("bmovie_year"));
			list.add(url + year);
		}
		return list;
	}

	// 무비 상세정보를 탐색하는 메서드 - 핵심 메서드
	public List<String> searchListToGetMovies(String url) {
		List<String> list = new ArrayList<String>();
		String code = "";
		try {
			Document doc2 = jsoup.connect(url).get();
			Elements table = doc2.select(QUERY_SELECTOR_SEARCH_LIST);
			for (Element e : table) {
				code = e.attr("href");
				list.add(NAVER_URL + code);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
		return list;
	}

	// 무비 아이디를 가져오는 메서드
	public String getMovieId(String url) {
		String res = "";
		res = url.substring(url.indexOf("code=") + 5);
		return res;
	}

	// 무비 제목을 가져오는 메서드
	public String getMovieTitle(String url) {
		String res = "제목이 없습니다!";

		try {
			Document doc2 = jsoup.connect(url).get();
			Element title = doc2.selectFirst("#content .article .mv_info .h_movie > a");
			res = title.text();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
		return res;
	}

	// 무비 정보를 가져오는 메서드
	public String getMovieInfoByKind(String url, String kind) {
		String res = "";

		try {
			Document doc2 = jsoup.connect(url).get();
			Element parentTitle = doc2.selectFirst("#content .article .mv_info .info_spec");
			Elements title = parentTitle.select("a");
			res = getInfoTitle(kind, title);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
		return res;
	}

	// 무비의 info를 종류별로 가져오는 메서드
	public String getInfoTitle(String title, Elements wrap) {
		String res = "";
		for (Element info : wrap) {
			if (info.attr("href").contains(title)) {
				res += info.text();
			}
		}
		return res;
	}

	// 무비의 running_time을 가져온다
	public String getMovieRunningTime(String url) {
		String res = "";

		try {
			Document doc2 = jsoup.connect(url).get();
			Element parentTitle = doc2.selectFirst("#content .article .mv_info .info_spec");
			Elements title = parentTitle.select("a");
			for (Element info : title) {
				if (info.attr("href").contains("nation=")) {
					Element target = info.parent();
					target = target.nextElementSibling();
					if(target == null) {
						return "";
					}
					res = target.text();
					break;
				}
			}
			if(res.indexOf("분") != -1)
				res = res.substring(0, res.indexOf("분"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
		return res;
	}

	// 무비의 audience_count를 가져온다
	public String getMovieAudienceCount(String url) {
		String res = "";

		try {
			Document doc2 = jsoup.connect(url).get();
			Elements title = doc2.select("#content .article .mv_info .info_spec .count");
			res = title.text();
			if(res.indexOf("명") != -1)
				res = res.substring(0, res.indexOf("명"));
			res = res.replaceAll(",", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(EXCEPTION_MESSAGE);
		}
		return res;
	}
	// 무비의 story를 가져온다
		public String getMovieStory(String url) {
			String res = "";

			try {
				Document doc2 = jsoup.connect(url).get();
				Elements title = doc2.select(".obj_section .video .story_area .h_tx_story");
				res = title.text();
				title = doc2.select(".obj_section .video .story_area p.con_tx");
				System.out.println("story res : " + res);
				res += title.text();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(EXCEPTION_MESSAGE);
			}
			return res;
		}
}
