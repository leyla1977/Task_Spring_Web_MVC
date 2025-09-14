package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  public List<Post> findAll() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> findById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {   // только для новых постов
      long newId = idCounter.incrementAndGet();
      post.setId(newId);
    }
    posts.put(post.getId(), post); // сохраняем с существующим ID, если обновление
    return post;
  }



  public void removeById(long id) {
    posts.remove(id);
  }
}

