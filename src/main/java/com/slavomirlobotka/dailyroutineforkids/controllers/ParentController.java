package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.services.ParentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PatchMapping("/parent/child/{id}")
  public ResponseEntity<?> updateChildData(
      @PathVariable Long id, @RequestBody(required = false) UpdateChildDTO updateChildDTO)
      throws Exception {
    DisplayChildDTO child = parentService.updateChild(id, updateChildDTO);

    return ResponseEntity.ok(child);
  }

  @DeleteMapping("/parent/child/{id}")
  public ResponseEntity<?> deleteChild(@PathVariable Long id) throws Exception {
    parentService.removeChild(id);

    return ResponseEntity.ok("Child with id " + id + " has been removed from the list.");
  }
}
