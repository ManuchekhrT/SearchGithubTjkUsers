package tj.example.githubusers.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import tj.example.githubusers.R;
import tj.example.githubusers.network.ApiBuilder;
import tj.example.githubusers.util.CommonUtils;
import tj.example.githubusers.model.response.GithubUserItems;
import tj.example.githubusers.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserActivity extends AppCompatActivity {

    private RecyclerView mGithubUsersRv;
    private ProgressBar mProgressBar;
    private GithubUserAdapter adapter;

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

        //search users by login name
        EditText mSearchEdt = findViewById(R.id.searchEdt);
        mSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(adapter != null){
                    adapter.getFilter().filter(s.toString());
                }
            }
        });

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
        ApiService apiService = new ApiBuilder().getService();
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
        adapter = new GithubUserAdapter(userItems.getItems());
        mGithubUsersRv.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.githubTb);
        setSupportActionBar(mToolbar);
    }
}
