package fr.ec.producthunt.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.ec.producthunt.data.database.DataBaseContract;
import fr.ec.producthunt.data.database.ProductHuntDbHelper;
import fr.ec.producthunt.data.model.Post;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataProvider {

  public final ProductHuntDbHelper sqliteOpenHelper;

  public DataProvider(Context context) {
    sqliteOpenHelper = new ProductHuntDbHelper(context);
  }

  public List<Post> getPosts(int number) {
    List<Post> list = new ArrayList<>(number);

    for (int i = 0; i < number; i++) {
      Post post = new Post();
      post.setTitle("Gear 360 " + i);
      post.setSubTitle("Capture stunning 360 video for virtual reality, by Samsung");

      list.add(post);
    }

    return list;
  }

  public String getPostsFromWeb(String param) {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;

    // Contiendra la réponse JSON brute sous forme de String .
    String posts = null;

    String requestLink = "https://api.producthunt.com/v1/posts?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb";

    if (param.equals("y")) {
      requestLink += "&days_ago=1";
    } else if (param.equals("feedier")) {
      requestLink = "https://api.producthunt.com/v1/posts/all?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb&search[url]=https://feedier.com";
    }

    Log.d(TAG, "param: " + param);

    try {
      // Construire l' URL de l'API ProductHunt
      URL url = new URL(requestLink);

      // Creer de la requête http vers  l'API ProductHunt , et ouvrir la connexion
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      // Lire le  input stream et le convertir String
      InputStream inputStream = urlConnection.getInputStream();
      StringBuffer buffer = new StringBuffer();
      if (inputStream == null) {
        // Nothing to do.
        return null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line + "\n");
      }

      if (buffer.length() == 0) {
        // Si le stream est vide, on revoie null;
        return null;
      }
      posts = buffer.toString();
    } catch (IOException e) {
      Log.e(TAG, "Error ", e);
      return null;
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          Log.e(TAG, "Error closing stream", e);
        }
      }
    }

    return posts;
  }

  /**
   *
   * @param jsonObject
   * @return
   */
  public List<Post> getPostsFromJson(String jsonObject) {

    try {

      JSONObject obj = new JSONObject(jsonObject);

      //String posts = obj.getJSONObject("posts");

      JSONArray arr = obj.getJSONArray("posts");

      List<Post> list = new ArrayList<>(arr.length());

      for (int i = 0; i < arr.length(); i++) {
        Post post = new Post();
        post.setTitle(arr.getJSONObject(i).getString("name"));
        post.setUrl(arr.getJSONObject(i).getString("redirect_url"));
        post.setSubTitle(arr.getJSONObject(i).getString("tagline"));
        post.setId(arr.getJSONObject(i).getInt("id"));
        post.setUpvotes("Upvotes" + arr.getJSONObject(i).getString("votes_count"));
        post.setUrlImage(arr.getJSONObject(i).getJSONObject("thumbnail").getString("image_url"));

        list.add(post);
      }

      return list;

    } catch (JSONException e) {

      Log.e(TAG, "Error ", e);
      return null;

    }
  }


  /**
   *
   * @param posts
   */
  public void storeToDb(List<Post> posts) {

    SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();

    for (Post post : posts) {

      ContentValues values = new ContentValues();
      values.put(DataBaseContract.PostTable.TITLE_COLUMN, post.getTitle());
      values.put(DataBaseContract.PostTable.URL_COLUMN, post.getUrl());
      values.put(DataBaseContract.PostTable.ID_COLUMN, post.getId());

      database.insert(DataBaseContract.PostTable.TABLE_NAME, null, values);
    }

  }

  /**
   * Read values from DB
   */
  public void getPostFromDb() {

    SQLiteDatabase database = sqliteOpenHelper.getReadableDatabase();

    // TODO: create a new async task that reads from the DB in the main activity

  }

}

