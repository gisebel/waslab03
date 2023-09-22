package fib.asw.waslab03;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hc.client5.http.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tasca_6 {

	private static final String LOCALE = "ca";
	
	public static void main(String[] args) {
		
		String TOKEN = Token.get();
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM 'de' yyyy 'a les' HH:mm:ss", new Locale(LOCALE));
		String now = sdf.format(new Date());
		
		System.out.println();
		System.out.println("Els 10 tags més populars a Mastodon [" + now + "]");
		System.out.println();
		
		//GET 10 TUTS
		String URI1 = "https://mastodont.cat/api/v1/trends/tags?limit=10";
		try {
			String output1 = Request.get(URI1)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();	
			JSONArray trendingTags = new JSONArray(output1);
			
			for (int i = 0; i < trendingTags.length(); ++i) {
				JSONObject tag = trendingTags.getJSONObject(i);
				
				String tagID = tag.get("name").toString();
				
				System.out.println("*************************************************");
				System.out.println("* Tag: " + tagID);
				System.out.println("*************************************************");
			
				// GET 5 TUTS FOR TAG
				String URI2 = "https://mastodont.cat/api/v1/timelines/tag/" + tagID + "?limit=5";
				try {
					String output2 = Request.get(URI2)
							.addHeader("Authorization","Bearer "+TOKEN)
							.execute()
							.returnContent()
							.asString();
					JSONArray trendingTuts = new JSONArray(output2);
					
					for (int j = 0; j < trendingTuts.length(); ++j) {
						JSONObject tut = trendingTuts.getJSONObject(j);
						printTut(tut);
						
					}
					System.out.println();
					
			
					
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}		
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printTut(JSONObject tut) {
		JSONObject account = new JSONObject(tut.get("account").toString());
		String nom = account.get("display_name").toString();
		String acct = "@" + account.get("acct").toString();
		String content = removeHTML(tut.get("content").toString());
		
		
		System.out.println("- " + nom + " (" + acct + "): " + content);
		System.out.println("-------------------------------------------------");
	
	}
	
	public static String removeHTML(String input) {
		return input.replaceAll("\\<[^>]*>","");
	}

}
