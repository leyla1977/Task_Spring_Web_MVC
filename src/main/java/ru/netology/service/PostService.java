package ru.netology.service;

import ru.netology.exception.PostNotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {

  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    // возвращаем только посты, которые не удалены
    return repository.findAll().stream()
            .filter(post -> !post.isRemoved())
            .collect(Collectors.toList());
  }


  // Получение поста по ID — выбрасывает исключение, если удалён
  public Post getById(long id) {
    Post post = repository.findByIdIncludeRemoved(id)
            .orElseThrow(() -> new PostNotFoundException(id));
    if (post.isRemoved()) {
      throw new PostNotFoundException(id);
    }
    return post;
  }

  // Создание нового поста или обновление существующего (только если не удалён)
  public Post save(Post post) {
    if (post.getId() == 0) {
      // Новый пост
      post.setRemoved(false); // явно ставим флаг
      return repository.save(post);
    } else {
      // Обновление существующего
      Post existing = repository.findByIdIncludeRemoved(post.getId())
              .orElseThrow(() -> new PostNotFoundException(post.getId()));
      if (existing.isRemoved()) {
        throw new PostNotFoundException(post.getId());
      }
      existing.setContent(post.getContent());
      return repository.save(existing);
    }
  }

  // Обновление контента поста (только если не удалён)
  public Post update(long id, String content) {
    Post existing = repository.findByIdIncludeRemoved(id)
            .orElseThrow(() -> new PostNotFoundException(id));
    if (existing.isRemoved()) {
      throw new PostNotFoundException(id);
    }
    existing.setContent(content);
    return repository.save(existing);
  }

  // Мягкое удаление поста
  public void removeById(long id) {
    Post post = repository.findByIdIncludeRemoved(id)
            .orElseThrow(() -> new PostNotFoundException(id));
    if (!post.isRemoved()) {
      repository.markAsRemoved(id);
    }
    // Если уже удалён, можно ничего не делать
  }
}



