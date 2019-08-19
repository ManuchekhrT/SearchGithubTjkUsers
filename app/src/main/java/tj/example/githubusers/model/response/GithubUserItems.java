package tj.example.githubusers.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import tj.example.githubusers.model.GithubUser;

public class GithubUserItems {

    @SerializedName("total_count")
    @Expose
    private String totalCount;

    @SerializedName("incomplete_results")
    @Expose
    private boolean incompleteResults;

    @SerializedName("items")
    @Expose
    private List<GithubUser> items;


    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<GithubUser> getItems() {
        return items;
    }

    public void setItems(List<GithubUser> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GithubUserItems{" +
                "totalCount='" + totalCount + '\'' +
                ", incompleteResults=" + incompleteResults +
                ", items=" + items +
                '}';
    }
}
