package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParentController {

  private final ParentService parentService;

  @PostMapping("/parent/child/{name}")
  public void addChild(
      @PathVariable String name, @RequestBody(required = false) RegisterChildDTO registerChildDto) {
    if (registerChildDto == null) {
      registerChildDto = new RegisterChildDTO();
    }

    parentService.createChild(name, registerChildDto);
  }
}
