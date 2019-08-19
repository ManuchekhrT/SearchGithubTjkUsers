package tj.example.githubusers.network;

import tj.example.githubusers.model.GithubSingleUser;
import tj.example.githubusers.model.response.GithubUserItems;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RestApiService {

    @GET("/search/users")
    Call<GithubUserItems> getGithubUsersList(@Query("q") String filter);

    @GET("/users/{username}")
    Call<GithubSingleUser> getSingleGithubUser(@Path(value = "username", encoded = true) String username);
}
