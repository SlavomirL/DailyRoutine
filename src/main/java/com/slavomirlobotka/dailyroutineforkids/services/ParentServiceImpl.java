package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParentServiceImpl implements ParentService {

  private final UserRepository userRepository;
  private final ChildRepository childRepository;
  private final ChildService childService;

  @Override
  public void createChild(String childName, RegisterChildDTO registerChildDTO) {
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
  }

  @Override
  public List<DisplayChildDTO> getAllChildren() {
    User user = getCurrentParent();

    return childService.convertAllToDto(user.getChildren());
  }

  @Override
  public User getCurrentParent() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return userRepository.findByEmail(email);
  }

  @Override
  public DisplayChildDTO updateChild(Long id, UpdateChildDTO updateChildDTO) throws Exception {
    Optional<Child> childOpt = childRepository.findById(id);
    if (childOpt.isEmpty()) {
      throw new Exception("No child with ID " + id + " found");
    }

    Child child = childOpt.get();

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

    return childService.convertSingleToDto(child);
  }

  @Override
  public void removeChild(Long id) throws Exception {
    Optional<Child> childOpt = childRepository.findById(id);
    if (childOpt.isEmpty()) {
      throw new Exception("No child with ID " + id + " found");
    }
    Child child = childOpt.get();
    childRepository.delete(child);
  }
}
