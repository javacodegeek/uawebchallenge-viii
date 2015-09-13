import name.javacodegeek.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TwitterAvatarCollage {
  	static String consumerKeyStr = "MdFafcZCLZWeuGTtHORVDj9H4";
  	static String consumerSecretStr = "jEH55rNaNtLGrjfbuMcPcHDqp7QeLiw2QeBzIzaH8PUNJ0eQHh";
  	static String accessTokenStr = "3627114675-XbEZYEuDToja7SOWze6TASf4kxVKRqJoBTKmFA4";
  	static String accessTokenSecretStr = "G3olkQPzfXGods5pbgV8ZelEB5skLxVGw3gYwxkOnAnrj";


  	public static void main(String[] args){
  	     System.out.println("Run....");
         TwitterClient twitter = TwitterClient.getInstance();

         JSONObject followingsObject = twitter.getUserFollowings("sportsru", "-1");
         Object objectFollns = followingsObject.get("ids");
         JSONArray followingsList = (JSONArray)objectFollns;
         long next_cursor = (long)(followingsObject.get("next_cursor"));

         String idsString = followingsList.toString();
         idsString = idsString.substring(1,idsString.length()-1);

         System.out.println(next_cursor);
         while(next_cursor != 0){
              JSONObject nextFollowingsObject = twitter.getUserFollowings("sportsru", Long.toString(next_cursor));
              Object nextObjectFollns = nextFollowingsObject.get("ids");
              JSONArray nextFollowingsList = (JSONArray)nextObjectFollns;
              next_cursor = (long)(nextFollowingsObject.get("next_cursor"));
              String nextIdsString = nextFollowingsList.toString();
              nextIdsString = nextIdsString.substring(1,nextIdsString.length()-1);

              idsString = idsString + nextIdsString;

              System.out.println(next_cursor);
         }

         String[] ids = idsString.split(",");
         System.out.println(ids.length);

    }
}
