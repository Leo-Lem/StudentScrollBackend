package studentscroll.api.posts.web.dto;

import java.time.LocalDate;

import org.hibernate.TypeMismatchException;

import lombok.*;
import studentscroll.api.posts.data.ContentPost;
import studentscroll.api.posts.data.EventPost;
import studentscroll.api.posts.data.Post;
import studentscroll.api.shared.Location;

@Data
public class CreatePostRequest {

  @NonNull
  private final Long posterId;

  @NonNull
  private final String title;

  @NonNull
  private final String[] tags;

  private final String description;

  private final LocalDate date;

  private final Location location;

  private final String content;

  public Class<? extends Post> getType() throws TypeMismatchException {
    if (description != null && date != null && location == null)
      return EventPost.class;
    else if (content != null)
      return ContentPost.class;
    else
      throw new TypeMismatchException("Unknown post type.");
  }

}
