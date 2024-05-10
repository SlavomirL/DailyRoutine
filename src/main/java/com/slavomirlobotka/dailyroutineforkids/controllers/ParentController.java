package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/parent/child/all")
  public ResponseEntity<?> displayChildren() throws Exception {
    List<DisplayChildDTO> children = parentService.getAllChildren();
    if (children != null) {

      return ResponseEntity.ok(children);
    }

    throw new Exception("No children found");
  }
}
