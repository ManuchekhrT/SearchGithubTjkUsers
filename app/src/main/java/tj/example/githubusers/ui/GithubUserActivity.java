package tj.example.githubusers.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import tj.example.githubusers.network.RestApiBuilder;
import tj.example.githubusers.util.CommonUtils;
import tj.example.andelachallengeproject.R;
import tj.example.githubusers.model.response.GithubUserItems;
import tj.example.githubusers.network.RestApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserActivity extends AppCompatActivity {

    private RecyclerView mGithubUsersRv;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_users);
        initToolbar();

        FloatingActionButton filterFab = findViewById(R.id.filterFab);
        filterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        mProgressBar = findViewById(R.id.loadingPb);
        mGithubUsersRv = findViewById(R.id.githubUsersRv);
        mGithubUsersRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mGithubUsersRv.setLayoutManager(layoutManager);

        //checking for network connectivity
        if (!CommonUtils.isNetworkAvailable(this)) {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "No Network connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fetchGithubUsersData();
                        }
                    });
            snackbar.show();
        } else {
            fetchGithubUsersData();
        }
    }

    private void fetchGithubUsersData() {
        String searchParams = "location:dushanbe";
        RestApiService apiService = new RestApiBuilder().getService();
        mProgressBar.setVisibility(View.VISIBLE);
        Call<GithubUserItems> call = apiService.getGithubUsersList(searchParams);
        call.enqueue(new Callback<GithubUserItems>() {
            @Override
            public void onResponse(Call<GithubUserItems> call, Response<GithubUserItems> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    GithubUserItems items = response.body();
                    prepareItems(items);
                } else
                    CommonUtils.makeToast(getApplicationContext(), "Request not Sucessful");
            }

            @Override
            public void onFailure(Call<GithubUserItems> call, Throwable t) {
                CommonUtils.makeToast(getApplicationContext(), "Request failed. Check your internet connection");
            }
        });

    }

    private void prepareItems(GithubUserItems userItems) {
        GithubUserAdapter adapter = new GithubUserAdapter(userItems.getItems());
        mGithubUsersRv.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.githubTb);
        setSupportActionBar(mToolbar);
    }
}
