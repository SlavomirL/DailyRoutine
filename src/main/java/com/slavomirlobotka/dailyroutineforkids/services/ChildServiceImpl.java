package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChildServiceImpl implements ChildService {

  private final UserRepository userRepository;
  private final ChildRepository childRepository;


  @Override
  public Long createChild(String childName, RegisterChildDTO registerChildDTO) {
    User user = getCurrentParent();

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

  @Override
  public List<DisplayChildDTO> getAllChildren() {
    User user = getCurrentParent();

    return convertAllToDto(user.getChildren());
  }

  @Override
  public User getCurrentParent() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return userRepository.findByEmail(email);
  }

  @Override
  public DisplayChildDTO updateChild(Long id, UpdateChildDTO updateChildDTO)
      throws DailyRoutineNotFound {
    Child child = childRepository.findById(id).orElseThrow(() -> new DailyRoutineNotFound("No child with ID " + id + " found"));

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

    return convertSingleToDto(child);
  }

  @Override
  public void removeChild(Long id) throws DailyRoutineNotFound {
    Child child = childRepository.findById(id).orElseThrow(() -> new DailyRoutineNotFound("No child with ID " + id + " found"));
    childRepository.delete(child);
  }

  @Override
  public List<DisplayChildDTO> convertAllToDto(List<Child> children) {
    List<DisplayChildDTO> result = new ArrayList<>();
    for (Child ch : children) {
      DisplayChildDTO chDto = convertSingleToDto(ch);
      result.add(chDto);
    }

    return result;
  }

  @Override
  public DisplayChildDTO convertSingleToDto(Child child) {

    return DisplayChildDTO.builder()
            .id(child.getId())
            .name(child.getName())
            .age(child.getAge())
            .gender(child.getGender())
            .schedules(child.getSchedules())
            .build();
  }
}
