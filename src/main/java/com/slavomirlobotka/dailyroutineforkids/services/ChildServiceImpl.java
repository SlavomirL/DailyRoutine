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
