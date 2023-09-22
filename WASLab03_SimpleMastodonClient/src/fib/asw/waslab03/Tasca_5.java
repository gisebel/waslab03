package fib.asw.waslab03;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;

public class Tasca_5 {

	public static void main(String[] args) {

		
		String fib_aswID = "109862447110628983";
		
		String URI1 = "https://mastodont.cat/api/v1/accounts/109862447110628983/statuses?limit=1";
		String TOKEN = Token.get();

		try {
			String output = Request.get(URI1)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			JSONObject result = new JSONObject(output.substring(1, output.length()-1));
			String idTut = result.getString("id");
			String content = result.getString("content");
			System.out.format("Tut Content:\n" + content);
			
			
			String URI2 = "https://mastodont.cat/api/v1/statuses/" + idTut + "/reblog";
			
			try {
				String output2 = Request.post(URI2)
						.addHeader("Authorization","Bearer "+TOKEN)
						.execute()
						.returnContent()
						.asString();
				
				// BOOST FET!
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
