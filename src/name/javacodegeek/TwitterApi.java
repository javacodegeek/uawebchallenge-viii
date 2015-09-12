package javacodegeek.name;

public class TwitterApi {
    private static TwitterApi instance = new TwitterApi();

    private TwitterApi() {

    }

    public static TwitterApi getInstance() {
        return instance;
    }
}
