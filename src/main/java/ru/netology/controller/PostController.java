package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  // Получение всех постов (не включая удалённые)
  @GetMapping
  public List<Post> all() {
    return service.all();
  }

  // Получение поста по ID
  @GetMapping("/{id}")
  public Post getById(@PathVariable long id) {
    return service.getById(id);
  }

  // Создание нового поста
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post create(@RequestBody Post post) {
    return service.save(post);
  }

  // Обновление существующего поста
  @PutMapping("/{id}")
  public Post update(@PathVariable long id, @RequestBody Post post) {
    return service.update(id, post.getContent());
  }

  // Мягкое удаление поста
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeById(@PathVariable long id) {
    service.removeById(id);
  }
}



