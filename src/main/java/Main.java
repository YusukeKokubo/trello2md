import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("hello");

        System.out.print(run("https://api.trello.com/1/boards/balwly1y/cards?fields=name,idlist,url&key=3101b4051791761b033e5ad3f99e9f9d&token=c74263db30626eec46f225e4e7a091b4070927273bc79a09faefec06f03d4cb0"));
    }

    private static String run (String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
