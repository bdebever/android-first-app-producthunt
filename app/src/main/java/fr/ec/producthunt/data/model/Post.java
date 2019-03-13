package fr.ec.producthunt.data.model;

public class Post {
  String title;
  String subTitle;
  String upvotes;
  String imageUrl;
  String url;
  Integer id;

  public String getTitle() {
    return title;
  }
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public String getUpvotes() { return upvotes; }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public void setUpvotes(String upvotes) {
    this.upvotes = upvotes;
  }

  public void setUrlImage(String image_url) {
    this.imageUrl = image_url;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
