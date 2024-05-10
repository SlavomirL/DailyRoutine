package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import com.slavomirlobotka.dailyroutineforkids.repositories.ChildRepository;
import com.slavomirlobotka.dailyroutineforkids.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    return childService.convertToDto(user.getChildren());
  }

  @Override
  public User getCurrentParent() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return userRepository.findByEmail(email);
  }
}
