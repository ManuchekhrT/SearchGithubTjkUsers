package tj.example.githubusers.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApiBuilder {

    private static final String BASE_URL = "https://api.github.com";

    private Retrofit retrofit;

    public RestApiBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RestApiService getService() {
        return retrofit.create(RestApiService.class);
    }
}