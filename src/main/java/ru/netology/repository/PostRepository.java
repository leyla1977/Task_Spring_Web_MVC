package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  public List<Post> findAll() {
    return posts.values().stream()
            .filter(post -> !post.isRemoved())
            .toList();
  }

  public Optional<Post> findById(long id) {
    Post post = posts.get(id);
    if (post == null || post.isRemoved()) return Optional.empty();
    return Optional.of(post);
  }

  public Optional<Post> findByIdIncludeRemoved(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void markAsRemoved(long id) {
    Post post = posts.get(id);
    if (post == null) throw new RuntimeException("Post not found with id=" + id);
    post.setRemoved(true);
  }
}



