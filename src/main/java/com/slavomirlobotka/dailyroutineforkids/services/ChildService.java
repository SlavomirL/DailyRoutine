package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import java.util.List;

public interface ChildService {

  Long createChild(String childName, RegisterChildDTO registerChildDTO);

  List<DisplayChildDTO> getAllChildren();

  User getCurrentParent();

  DisplayChildDTO updateChild(Long id, UpdateChildDTO updateChildDTO) throws DailyRoutineNotFound;

  void removeChild(Long id) throws DailyRoutineNotFound;

  List<DisplayChildDTO> convertAllToDto(List<Child> children);

  DisplayChildDTO convertSingleToDto(Child child);
}
