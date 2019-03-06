package fr.ec.producthunt.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.ec.producthunt.R;
import fr.ec.producthunt.data.model.Post;
import java.util.List;

public class PostAdapter extends BaseAdapter {

  private final List<Post> dataSource ;

  public PostAdapter(List<Post> dataSource) {
    this.dataSource = dataSource;
  }

  @Override public int getCount() {
    return dataSource.size();
  }

  @Override public Object getItem(int position) {
    return dataSource.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder viewHolder ;

    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

      viewHolder = new ViewHolder();
      viewHolder.title = convertView.findViewById(R.id.title);
      viewHolder.subTitle = convertView.findViewById(R.id.sub_title);
      viewHolder.upvotes = convertView.findViewById(R.id.upvote_count);
      viewHolder.image = convertView.findViewById(R.id.img_product);

      convertView.setTag(viewHolder);
    }else {

      viewHolder = (ViewHolder) convertView.getTag();
    }

    Post post = dataSource.get(position);
    viewHolder.title.setText(post.getTitle());
    viewHolder.subTitle.setText(post.getSubTitle());
    viewHolder.upvotes.setText(post.getUpvotes());
    Picasso.get().load(post.getImageUrl()).into(viewHolder.image);

    return convertView;
  }


  private static class ViewHolder {
    TextView upvotes;
    TextView title;
    TextView subTitle;
    ImageView image;
  }
}
