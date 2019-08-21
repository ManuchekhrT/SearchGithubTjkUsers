## SearchTjkGithubUsers

Android App to retrieve a list of Developers from Dushanbe, Tjk using Github API search and get a single user

## Getting Started

You can clone or download this project.

### Prerequisites
[GitHub API] (https://api.github.com/search/users?q=location:dushanbe)



### Dependencies
Obviously to use Retrofit you need this library in your dependencies. You also need a converter used for JSON format : Gson.
Parceler is used for serialization and deserialization of objects.

```
 compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
```



### GitHub Api Response in JSON
{
  "total_count": 68,
  "incomplete_results": false,
  "items": [
    {
      .........
    },
 ```
 
### What do you need ?

There is a minimum amount of files to write to request a Rest Api, you need at least one interface to write your query, 
a model to retrieve the api response and a restclient to make your calls.

### POJO
you need models to retrieve data from JSON returned by the server. You have to create POJOs, the fields name have to be the same as those in the JSON but you can have different names if you use @SerializedName annotation to specify the name of the field in the JSON. 
There is Serializable and Parcelable for serialization and deserialization of object. 

```
public class GithubUser implements Parcelable {

    @SerializedName("login")
    @Expose
    private String login;
    .......
    }
```


### Service

You need to create an interface called Service here to manage your url calls. 
In this interface you have to specify the type of the request like POST, GET, PUT, etc. 
For an asynchronized request you have to add a Callback to your methods and return void.  
To make the HTTp request we need to send some paramenters. In this case it is a query so we can also add ``q`` query parameters with @Query. 

public interface ApiService {

    @GET("/search/users")
    Call<GithubUserItems> getGithubUsersList(@Query("q") String filter);

    @GET("/users/{username}")
    Call<GithubSingleUser> getSingleGithubUser(@Path(value = "username", encoded = true) String username);
}

![Screenshot_2019-08-19-12-37-52-253_ng codeimpact andelachallengeproject](https://user-images.githubusercontent.com/47312133/63247642-f98dd300-c27e-11e9-84a2-fce31a93ac52.png)
![Screenshot_2019-08-19-12-38-17-424_ng codeimpact andelachallengeproject](https://user-images.githubusercontent.com/47312133/63247644-fabf0000-c27e-11e9-92ad-29194f8a5277.png)



