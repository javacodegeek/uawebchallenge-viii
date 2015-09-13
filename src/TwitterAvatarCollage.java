import name.javacodegeek.*;

public class TwitterAvatarCollage {
  	static String consumerKeyStr = "MdFafcZCLZWeuGTtHORVDj9H4";
  	static String consumerSecretStr = "jEH55rNaNtLGrjfbuMcPcHDqp7QeLiw2QeBzIzaH8PUNJ0eQHh";
  	static String accessTokenStr = "3627114675-XbEZYEuDToja7SOWze6TASf4kxVKRqJoBTKmFA4";
  	static String accessTokenSecretStr = "G3olkQPzfXGods5pbgV8ZelEB5skLxVGw3gYwxkOnAnrj";


  	public static void main(String[] args){
  	     System.out.println("Run....");
         TwitterClient twitter = TwitterClient.getInstance();
         twitter.getUserFollowings("sportsru", "-1");
  	}
}
