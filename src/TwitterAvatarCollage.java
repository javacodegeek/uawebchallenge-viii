import name.javacodegeek.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TwitterAvatarCollage {
  	static String consumerKeyStr = "MdFafcZCLZWeuGTtHORVDj9H4";
  	static String consumerSecretStr = "jEH55rNaNtLGrjfbuMcPcHDqp7QeLiw2QeBzIzaH8PUNJ0eQHh";
  	static String accessTokenStr = "3627114675-XbEZYEuDToja7SOWze6TASf4kxVKRqJoBTKmFA4";
  	static String accessTokenSecretStr = "G3olkQPzfXGods5pbgV8ZelEB5skLxVGw3gYwxkOnAnrj";


  	public static void main(String[] args){
  	     System.out.println("Run programm....");

         String screenName = args[0];

         String sizePX = args[1];

         TwitterClient twitter = TwitterClient.getInstance();


         JSONObject followingsObject = twitter.getUserFollowings(screenName, "-1");
         Object objectFollns = followingsObject.get("ids");
         JSONArray followingsList = (JSONArray)objectFollns;
         long next_cursor = (long)(followingsObject.get("next_cursor"));

         String idsString = followingsList.toString();
         idsString = idsString.substring(1,idsString.length()-1);

         while(next_cursor != 0){
              JSONObject nextFollowingsObject = twitter.getUserFollowings(screenName, Long.toString(next_cursor));
              Object nextObjectFollns = nextFollowingsObject.get("ids");
              JSONArray nextFollowingsList = (JSONArray)nextObjectFollns;
              next_cursor = (long)(nextFollowingsObject.get("next_cursor"));
              String nextIdsString = nextFollowingsList.toString();
              nextIdsString = nextIdsString.substring(1,nextIdsString.length()-1);

              idsString = idsString + nextIdsString;

              //
              //System.out.println(next_cursor);
         }

         String[] ids = idsString.split(",");


         StringBuilder nextIdsStr = new StringBuilder();

         int k = 0;
         int i = 0;
         while(k<ids.length){
              for(i=k;i<(k+100)&&i<(ids.length);i++){
                  nextIdsStr.append(ids[i]);
                  if(i<(k+100-1)) {nextIdsStr.append(",");}
                  //System.out.println(k);
              }

              Object userObjects = twitter.getUserObjectList(nextIdsStr.toString());
              JSONArray userObjectsList = (JSONArray)userObjects;

              for(int j = 0; j < userObjectsList.size(); j++){
                   JSONObject user = (JSONObject)(userObjectsList.get(j));
                   String url = user.get("profile_image_url").toString();
                   String name = user.get("id") + "_small";
                   twitter.saveImage(url, name, screenName);
              }

              nextIdsStr = new StringBuilder();
              k = i;
         }



        String link = twitter.generateCollage(sizePX, screenName, "newimage_"+screenName+"_"+sizePX);

        System.out.println(link);





    }
}
