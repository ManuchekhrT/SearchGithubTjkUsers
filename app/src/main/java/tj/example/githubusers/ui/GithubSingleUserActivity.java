package tj.example.githubusers.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;
import tj.example.githubusers.R;
import tj.example.githubusers.model.GithubSingleUser;
import tj.example.githubusers.network.ApiBuilder;
import tj.example.githubusers.util.CommonUtils;
import tj.example.githubusers.util.ConstantUtils;
import tj.example.githubusers.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubSingleUserActivity extends AppCompatActivity {

    private ImageView mSingleUserAvatarIv;
    private TextView mSingleUserNameTv;
    private TextView mSingleUserBlogTv;
    private TextView mSingleUserBioTv;
    private TextView mSingleUserPublicReposTv;
    private TextView mSingleUserPublicGistsTv;
    private TextView mSingleUserFollowersTv;
    private TextView mSingleUserFollowingTv;
    private TextView mSingleUserHtmlUrlTv;
    private ProgressBar mLoadingPb;
    private RelativeLayout mRelativeLayout;

    public static Intent newIntent(Context context, String login) {
        Intent intent = new Intent(context, GithubSingleUserActivity.class);
        intent.putExtra(ConstantUtils.ARG_USER_LOGIN, login);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_single_user);

        Toolbar toolbar = findViewById(R.id.singleUserMainTb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingTbLayout);

        mSingleUserAvatarIv = collapsingToolbarLayout.findViewById(R.id.singleUserAvatarIv);
        mSingleUserNameTv = findViewById(R.id.singleUserNameTv);
        mSingleUserHtmlUrlTv = findViewById(R.id.singleUserHtmlUrlTv);
        mSingleUserBlogTv = findViewById(R.id.singleUserBlogTv);
        mSingleUserBioTv = findViewById(R.id.singleUserBioTv);
        mSingleUserPublicReposTv = findViewById(R.id.singleUserPublicReposTv);
        mSingleUserPublicGistsTv = findViewById(R.id.singleUserPublicGistsTv);
        mSingleUserFollowersTv = findViewById(R.id.singleUserFollowersTv);
        mSingleUserFollowingTv = findViewById(R.id.singleUserFollowingTv);
        mLoadingPb = findViewById(R.id.singleUserLoadingPb);
        mRelativeLayout = findViewById(R.id.singleUserRL);


        String userLogin = getIntent().getStringExtra(ConstantUtils.ARG_USER_LOGIN);
        if (userLogin != null && !userLogin.isEmpty()) {
            collapsingToolbarLayout.setTitle(userLogin);
            toolbar.setTitle(userLogin);
            fetchSingleGithubUserData(userLogin);
        }

    }

    private void fetchSingleGithubUserData(String userLogin) {
        if (!CommonUtils.isNetworkAvailable(this)) {
            CommonUtils.makeToast(this, "No internet connection");
            return;
        }
        mLoadingPb.setVisibility(View.VISIBLE);
        mRelativeLayout.setVisibility(View.GONE);
        ApiService apiService = new ApiBuilder().getService();
        Call<GithubSingleUser> call = apiService.getSingleGithubUser(userLogin);
        call.enqueue(new Callback<GithubSingleUser>() {
            @Override
            public void onResponse(Call<GithubSingleUser> call, Response<GithubSingleUser> response) {
                mLoadingPb.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    GithubSingleUser singleUser = response.body();
                    setSingleUser(singleUser);
                } else {
                    CommonUtils.makeToast(getApplicationContext(), "Request error");
                }
            }

            @Override
            public void onFailure(Call<GithubSingleUser> call, Throwable t) {
                mLoadingPb.setVisibility(View.GONE);
                if (t instanceof Exception)
                    CommonUtils.makeToast(getApplicationContext(), t.getMessage());
            }
        });
    }

    private void setSingleUser(GithubSingleUser singleUser) {
        Glide.with(getApplicationContext())
                .load(singleUser.getAvatarUrl())
                .into(mSingleUserAvatarIv);

        mSingleUserNameTv.setText(singleUser.getName());
        mSingleUserBlogTv.setText(singleUser.getBlog());
        mSingleUserBioTv.setText(singleUser.getBio());
        mSingleUserPublicReposTv.setText(singleUser.getPublicRepos());
        mSingleUserPublicGistsTv.setText(singleUser.getPublicGists());
        mSingleUserFollowersTv.setText(singleUser.getFollowers());
        mSingleUserFollowingTv.setText(singleUser.getFollowing());
        mSingleUserHtmlUrlTv.setText(singleUser.getHtmlUrl());

        BetterLinkMovementMethod movementMethod = BetterLinkMovementMethod.linkify(Linkify.WEB_URLS, mSingleUserHtmlUrlTv);
        movementMethod.setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
            @Override
            public boolean onClick(TextView textView, String url) {
                getCustomTabIntentInstance().launchUrl(getApplicationContext(), Uri.parse(url));
                return true;
            }
        });
    }

    private CustomTabsIntent getCustomTabIntentInstance() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        return builder.build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
