package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import java.util.NoSuchElementException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.findAll();
  }


  public Post getById(long id) {
    return repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Post not found with id=" + id));
  }


  public Post save(Post post) {
    if (post.getId() == 0) {
      // Новый пост
    return repository.save(post);
    } else {
      // Обновление существующего поста
      Post existing = repository.findById(post.getId())
              .orElseThrow(() -> new NoSuchElementException("Post not found with id=" + post.getId()));
      existing.setContent(post.getContent());
      return repository.save(existing);
    }
  }

  public Post update(long id, String content) {
    Post existing = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Post not found with id=" + id));
    existing.setContent(content);
    repository.save(existing); // ID уже не 0, новый не создаётся
    return existing;
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}



