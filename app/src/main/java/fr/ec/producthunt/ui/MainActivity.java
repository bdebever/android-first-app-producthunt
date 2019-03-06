package fr.ec.producthunt.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.ec.producthunt.R;
import fr.ec.producthunt.data.DataProvider;
import fr.ec.producthunt.data.model.Post;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  private DataProvider dataProvider;

  private String dataPH;
  private ListView listView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    dataProvider = new DataProvider();

    listView = findViewById(R.id.list_item);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view,
                              int position, long id) {

        Post post = (Post)parent.getItemAtPosition(position);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //intent.putExtra("url", post.getTitle());
        startActivity(intent);

        Toast.makeText(MainActivity.this, post.getTitle(), Toast.LENGTH_SHORT).show();

      }

    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.refresh:
        PostsAsyncTask postsAsyncTask = new PostsAsyncTask();
        postsAsyncTask.execute("all");
        return true;
      case R.id.yesterday:
        PostsAsyncTask postsAsyncTaskY = new PostsAsyncTask();
        postsAsyncTaskY.execute("y");
        return true;
      case R.id.feedier:
        PostsAsyncTask postsAsyncTaskF = new PostsAsyncTask();
        postsAsyncTaskF.execute("feedier");
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  private class PostsAsyncTask extends AsyncTask<String, Void, List> {


    //Do on Main Thread
    @Override protected void onPreExecute() {
      super.onPreExecute();
      Log.d(TAG, "onPreExecute: ");
    }

    //Do on Background Thread
    @Override protected List doInBackground(String... params) {

      String posts = dataProvider.getPostsFromWeb(params[0]);
      Log.d(TAG, "doInBackground: Posts :"+posts);

      return dataProvider.getPostsFromJson(posts);
    }

    //Do on Main Thread
    @Override protected void onPostExecute(List result) {
      super.onPostExecute(result);
      Log.d(TAG, "onPostExecute() called with: " + "result = [" + result + "]");

      // Do the computation in background
      listView.setAdapter(new PostAdapter(result));

    }

  }
}
