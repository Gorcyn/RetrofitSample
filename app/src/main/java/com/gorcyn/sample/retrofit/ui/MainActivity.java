package com.gorcyn.sample.retrofit.ui;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.gorcyn.sample.retrofit.R;
import com.gorcyn.sample.retrofit.model.Repos;
import com.gorcyn.sample.retrofit.model.User;
import com.gorcyn.sample.retrofit.service.GithubServiceProvider;

import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    private EditText userEditText;
    private ListView reposListView;

    private ArrayAdapter<Repos> reposListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userEditText = (EditText) findViewById(R.id.user);
        reposListView = (ListView) findViewById(android.R.id.list);

        reposListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new LinkedList<Repos>());
        reposListView.setAdapter(reposListAdapter);
    }

    public void showUserBlog(View view) {
        final String userName = userEditText.getText().toString();

        GithubServiceProvider.getInstance().getUser(userName, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.success)
                    .setMessage(user.getBlog())
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            }

            @Override
            public void failure(RetrofitError error) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.failure)
                    .setMessage(error.getMessage())
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            }
        });
    }

    public void listUserRepos(View view) {
        reposListAdapter.clear();
        reposListAdapter.notifyDataSetChanged();

        final String userName = userEditText.getText().toString();

        GithubServiceProvider.getInstance().getUserRepos(userName, new Callback<List<Repos>>() {
            @Override
            public void success(List<Repos> reposes, Response response) {
                reposListAdapter.clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    reposListAdapter.addAll(reposes);
                } else {
                    for (Repos repos : reposes) {
                        reposListAdapter.add(repos);
                    }
                }
                reposListAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.failure)
                    .setMessage(error.getMessage())
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
                Log.e("Repositories", error.getMessage(), error);
            }
        });
    }
}
