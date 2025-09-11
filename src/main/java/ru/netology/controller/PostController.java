package ru.netology.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  private final Gson gson = new Gson();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try {
      final var post = service.getById(id);
      response.getWriter().print(gson.toJson(post));
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void save(HttpServletRequest req, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    response.setCharacterEncoding("UTF-8");
    final var gson = new Gson();

    try (Reader body = new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8)) {
      final var post = gson.fromJson(body, Post.class);
      final var data = service.save(post);
      response.getWriter().print(gson.toJson(data));
    }
  }


  public void removeById(long id, HttpServletResponse response) throws IOException {
    try {
      service.removeById(id);
      response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
