package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
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
  public ResponseEntity<?> addChild(
      @PathVariable String name, @RequestBody(required = false) RegisterChildDTO registerChildDto) {
    if (registerChildDto == null) {
      registerChildDto = new RegisterChildDTO();
    }

    Long id = parentService.createChild(name, registerChildDto);
    return ResponseEntity.ok("A child " + name + " added to the list with id " + id + ".");
  }

  @GetMapping("/parent/child/all")
  public ResponseEntity<?> displayChildren() throws Exception {
    List<DisplayChildDTO> children = parentService.getAllChildren();
    if (children != null) {

      return ResponseEntity.ok(children);
    }

    throw new DailyRoutineNotFound("No children found for this parent");
  }

  @PatchMapping("/parent/child/{id}")
  public ResponseEntity<?> updateChildData(
      @PathVariable Long id, @RequestBody(required = false) UpdateChildDTO updateChildDTO)
      throws DailyRoutineNotFound {
    DisplayChildDTO child = parentService.updateChild(id, updateChildDTO);

    return ResponseEntity.ok(child);
  }

  @DeleteMapping("/parent/child/{id}")
  public ResponseEntity<?> deleteChild(@PathVariable Long id) throws DailyRoutineNotFound {
    parentService.removeChild(id);

    return ResponseEntity.ok("Child with id " + id + " has been removed from the list.");
  }
}
