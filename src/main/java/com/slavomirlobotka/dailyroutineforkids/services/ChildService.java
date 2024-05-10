package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.models.Child;

import java.util.List;

public interface ChildService {
  List<DisplayChildDTO> convertAllToDto(List<Child> children);

  DisplayChildDTO convertSingleToDto(Child child);
}
