package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.ScheduleResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChildServiceImpl implements ChildService {

  private final ChildRepository childRepository;
  private final AuthenticationService authenticationService;

  @Override
  public Long createChild(String childName, RegisterChildDTO registerChildDTO)
      throws DailyRoutineBadRequest {
    User user = authenticationService.getCurrentParent();

    if (childRepository.existsByUserIdAndName(user.getId(), childName)) {
      throw new DailyRoutineBadRequest(
          "A child with name '"
              + childName
              + "' already registered for parent '"
              + user.getFirstName()
              + "'.");
    }
    Child child =
        Child.builder()
            .name(childName)
            .age(registerChildDTO.getAge())
            .gender(registerChildDTO.getGender())
            .user(user)
            .build();

    user.getChildren().add(child);
    childRepository.save(child);

    return child.getId();
  }

  @Transactional
  @Override
  public DisplayChildDTO updateChild(Long id, UpdateChildDTO updateChildDTO)
      throws DailyRoutineNotFound {
    Child child =
        childRepository
            .findById(id)
            .orElseThrow(() -> new DailyRoutineNotFound("No child with ID '" + id + "' found."));

    if (updateChildDTO.getName() != null) {
      child.setName(updateChildDTO.getName());
    }
    if (updateChildDTO.getAge() != null) {
      child.setAge(updateChildDTO.getAge());
    }
    if (updateChildDTO.getGender() != null) {
      child.setGender(updateChildDTO.getGender());
    }
    childRepository.save(child);

    return convertSingleChildToDto(child);
  }

  @Transactional
  @Override
  public void removeChild(Long id) throws DailyRoutineNotFound {
    Child child =
        childRepository
            .findById(id)
            .orElseThrow(() -> new DailyRoutineNotFound("No child with ID '" + id + "' found."));
    childRepository.delete(child);
  }

  @Transactional
  @Override
  public void removeAllChildren() throws DailyRoutineNotFound {
    User user = authenticationService.getCurrentParent();

    List<Child> children = childRepository.findByUser(user);
    if (children == null || children.isEmpty()) {
      throw new DailyRoutineNotFound(
          "This parent has no children currently assigned. Nothing to delete.");
    }

    childRepository.deleteAll(children);
  }

  @Override
  public List<DisplayChildDTO> getAllChildrenAsDTO() throws DailyRoutineNotFound {
    User user = authenticationService.getCurrentParent();

    List<Child> children = childRepository.findByUser(user);
    if (children == null || children.isEmpty()) {
      throw new DailyRoutineNotFound(
          "This parent has no children currently assigned. Nothing to display.");
    }

    return convertAllChildrenToDto(children);
  }

  @Override
  public List<DisplayChildDTO> convertAllChildrenToDto(List<Child> children) {
    List<DisplayChildDTO> result = new ArrayList<>();
    for (Child ch : children) {
      DisplayChildDTO chDto = convertSingleChildToDto(ch);
      result.add(chDto);
    }

    return result;
  }

  @Override
  public DisplayChildDTO convertSingleChildToDto(Child child) {
    return DisplayChildDTO.builder()
        .id(child.getId())
        .name(child.getName())
        .age(child.getAge())
        .gender(child.getGender())
        .schedules(
            child.getSchedules().stream()
                .map(
                    s ->
                        ScheduleResponseDTO.builder()
                            .scheduleId(s.getId())
                            .scheduleName(s.getScheduleName())
                            .weekDays(s.getWeekDays())
                            .tasks(s.getScheduleTasks())
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
