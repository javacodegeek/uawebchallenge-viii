import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.*;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Arrays;



public class TwitterClient {
    private static TwitterClient instance = new TwitterClient();

    private static  String CONSUMER_KEY = "MdFafcZCLZWeuGTtHORVDj9H4";
    private static  String CONSUMER_SECRET = "jEH55rNaNtLGrjfbuMcPcHDqp7QeLiw2QeBzIzaH8PUNJ0eQHh";
    private static  String ACCESS_TOKEN = "3627114675-XbEZYEuDToja7SOWze6TASf4kxVKRqJoBTKmFA4";
    private static  String CONSUMACCESS_TOKEN_SECRET = "G3olkQPzfXGods5pbgV8ZelEB5skLxVGw3gYwxkOnAnrj";

    private static  String twitter_api_url = "https://api.twitter.com/1.1";
    private static  String url_user_time_line = "/statuses/user_timeline.json";
    private static  String url_user_followings = "/friends/ids.json";
    private static  String url_users_lookup = "/users/lookup.json";

    private TwitterClient() {

    }

    protected static String makeHttpGetRequest(String apiUrl, String path, String getParams){
        try{
            OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
            oAuthConsumer.setTokenWithSecret(ACCESS_TOKEN, CONSUMACCESS_TOKEN_SECRET);

            HttpGet httpGet = new HttpGet(apiUrl + path + getParams);
            oAuthConsumer.sign(httpGet);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("status code:  " + statusCode);
            if (statusCode == 200) {
                BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                  result.append(line);
                }
                 return result.toString();
            }else {
                  return null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
          return null;
    }

    public static void saveImage(String url, String imageName) {
      try{
          OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
          oAuthConsumer.setTokenWithSecret(ACCESS_TOKEN, CONSUMACCESS_TOKEN_SECRET);

          HttpGet httpGet = new HttpGet(url);

          String[] extArr = url.split("\\.");

          String extFile = extArr[extArr.length-1];

          oAuthConsumer.sign(httpGet);

          HttpClient httpClient = new DefaultHttpClient();
          HttpResponse httpResponse = httpClient.execute(httpGet);

          int statusCode = httpResponse.getStatusLine().getStatusCode();
          System.out.println("status code:  " + statusCode);
          if (statusCode == 200) {
              InputStream is = httpResponse.getEntity().getContent();
              FileOutputStream fos = new FileOutputStream(new File("twitterimages/" + imageName + "." + extFile));
              int inByte;
              while ((inByte = is.read()) != -1) {
                  fos.write(inByte);
              }
              is.close();
              fos.close();
          }else {
          }
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
    }

    public static JSONObject getUserFollowings(String screenName, String cursor){
        String response = makeHttpGetRequest(twitter_api_url, url_user_followings, "?screen_name=" + screenName + "&cursor=" + cursor);
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONObject jsonObject = (JSONObject)obj;
            return jsonObject;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
          return null;
    }

    public static JSONArray getUserObjectList(String userIds){
        String response = makeHttpGetRequest(twitter_api_url, url_users_lookup, "?user_id=" + userIds);
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray jsonArray = (JSONArray)obj;
            return jsonArray;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
          return null;
    }


    public static JSONArray getUserTweets(String screenName, int count) {
      try{
        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        oAuthConsumer.setTokenWithSecret(ACCESS_TOKEN, CONSUMACCESS_TOKEN_SECRET);

        HttpGet httpGet = new HttpGet(twitter_api_url + url_user_time_line + "?screen_name=" + screenName + "&count=" + Integer.toString(count));;
        oAuthConsumer.sign(httpGet);

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            BufferedReader rd = new BufferedReader(
            new InputStreamReader(httpResponse.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
              result.append(line);
            }
            //System.out.println(result);
            JSONParser parser = new JSONParser();
            Object objArray = parser.parse(result.toString());

		        JSONArray jsonArray = (JSONArray)objArray;

             return jsonArray;
        }else {
              return null;
        }

      }catch (Exception e){
          System.out.println(e.getMessage());
      }

            return null;
    }










    public static TwitterClient getInstance() {
        return instance;
    }
}
