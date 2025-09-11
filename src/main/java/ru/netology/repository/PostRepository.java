package ru.netology.repository;

import ru.netology.model.Post;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>(); // <-- поле класса
  private final AtomicLong idCounter = new AtomicLong(0);          // <-- поле класса

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0 || !posts.containsKey(post.getId())) {
      // Создание нового поста (или id не найден — тоже создаём новый)
      long newId = idCounter.incrementAndGet();
      post.setId(newId);
    }
    // Сохраняем пост (обновление существующего или новый)
    posts.put(post.getId(), post);
    return post;
  }


  public void removeById(long id) {
    posts.remove(id);
  }
}

