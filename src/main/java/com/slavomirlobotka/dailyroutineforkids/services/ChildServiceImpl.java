package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChildServiceImpl implements ChildService {

  @Override
  public List<DisplayChildDTO> convertToDto(List<Child> children) {
    List<DisplayChildDTO> result = new ArrayList<>();
    for (Child ch : children) {
      DisplayChildDTO chDto =
          DisplayChildDTO.builder()
              .id(ch.getId())
              .name(ch.getName())
              .age(ch.getAge())
              .gender(ch.getGender())
              .schedules(ch.getSchedules())
              .build();
      result.add(chDto);
    }

    return result;
  }
}
