package tj.example.githubusers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GithubSingleUser {

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("blog")
    @Expose
    private String blog;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("public_repos")
    @Expose
    private String publicRepos;

    @SerializedName("public_gists")
    @Expose
    private String publicGists;

    @SerializedName("followers")
    @Expose
    private String followers;

    @SerializedName("following")
    @Expose
    private String following;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(String publicRepos) {
        this.publicRepos = publicRepos;
    }

    public String getFollowers() {
        return followers;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(String publicGists) {
        this.publicGists = publicGists;
    }

    @Override
    public String toString() {
        return "GithubSingleUser{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", publicRepos='" + publicRepos + '\'' +
                ", followers='" + followers + '\'' +
                ", following='" + following + '\'' +
                '}';
    }
}
