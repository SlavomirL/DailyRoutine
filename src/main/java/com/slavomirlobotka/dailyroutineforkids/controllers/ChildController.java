package com.slavomirlobotka.dailyroutineforkids.controllers;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.services.ChildService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChildController {

  private final ChildService childService;

  @PostMapping("/child/{name}")
  public ResponseEntity<?> addChild(
      @PathVariable String name, @RequestBody(required = false) RegisterChildDTO registerChildDto)
      throws DailyRoutineBadRequest {
    if (registerChildDto == null) {
      registerChildDto = new RegisterChildDTO();
    }

    Long id = childService.createChild(name, registerChildDto);

    return ResponseEntity.ok("A child '" + name + "' added to the list with id " + id + ".");
  }

  @GetMapping("/child/all")
  public ResponseEntity<?> displayChildren() throws DailyRoutineNotFound {
    List<DisplayChildDTO> children = childService.getAllChildrenAsDTO();

    return ResponseEntity.ok(children);
  }

  @PatchMapping("/child/{id}")
  public ResponseEntity<?> updateChildData(
      @PathVariable Long id, @RequestBody(required = false) UpdateChildDTO updateChildDTO)
      throws DailyRoutineNotFound {
    DisplayChildDTO child = childService.updateChild(id, updateChildDTO);

    return ResponseEntity.ok(child);
  }

  @DeleteMapping("/child/{id}")
  public ResponseEntity<?> deleteChild(@PathVariable Long id) throws DailyRoutineNotFound {
    childService.removeChild(id);

    return ResponseEntity.ok("Child with id '" + id + "' has been removed from the list.");
  }

  @DeleteMapping("/child/all")
  public ResponseEntity<?> deleteAllChildren() throws DailyRoutineNotFound {
    childService.removeAllChildren();

    return ResponseEntity.ok("All children have been removed from the list.");
  }
}
