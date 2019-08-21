package tj.example.githubusers.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import tj.example.githubusers.R;
import tj.example.githubusers.model.GithubUser;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.MyViewHolder> implements Filterable {

    private List<GithubUser> githubUserList;
    private List<GithubUser> userListOriginal;

    GithubUserAdapter(List<GithubUser> githubUserList) {
        this.githubUserList = githubUserList;
    }

    @NonNull
    @Override
    public GithubUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_github_user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubUserAdapter.MyViewHolder holder, int position) {
        final GithubUser githubUser = githubUserList.get(position);
        holder.mUserLoginIv.setText(githubUser.getLogin());
        holder.mUserLocationTv.setText(R.string.location_dushanbe);

        //Loading the image using Glide
        Context context = holder.mUserAvatarIv.getContext();
        Glide.with(context)
                .load(githubUser.getAvatarUrl())
                .into(holder.mUserAvatarIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent intent = GithubSingleUserActivity.newIntent(context, githubUser.getLogin());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return githubUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mUserLocationTv;
        CircleImageView mUserAvatarIv;
        TextView mUserLoginIv;

        MyViewHolder(@NonNull View view) {
            super(view);
            mUserAvatarIv = view.findViewById(R.id.userAvatarIv);
            mUserLoginIv = view.findViewById(R.id.userLoginTv);
            mUserLocationTv = view.findViewById(R.id.userLocationTv);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<GithubUser> filteredArrayList = new ArrayList<>();

                if (userListOriginal == null) {
                    userListOriginal = new ArrayList<>(githubUserList);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = userListOriginal.size();
                    results.values = userListOriginal;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (GithubUser user : userListOriginal) {
                        if (user.getLogin().toLowerCase(Locale.getDefault()).contains(constraint)) {
                            filteredArrayList.add(user);
                        }
                    }

                    results.count = filteredArrayList.size();
                    results.values = filteredArrayList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                githubUserList = (List<GithubUser>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
