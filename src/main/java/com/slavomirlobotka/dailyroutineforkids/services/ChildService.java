package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.UpdateChildDTO;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineBadRequest;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineIllegalArgument;
import com.slavomirlobotka.dailyroutineforkids.exceptions.DailyRoutineNotFound;
import com.slavomirlobotka.dailyroutineforkids.models.Child;
import java.util.List;

public interface ChildService {

  Long createChild(String childName, RegisterChildDTO registerChildDTO)
      throws DailyRoutineBadRequest, DailyRoutineIllegalArgument;

  List<DisplayChildDTO> getAllChildrenAsDTO() throws DailyRoutineNotFound;

  DisplayChildDTO updateChild(Long id, UpdateChildDTO updateChildDTO) throws DailyRoutineNotFound, DailyRoutineIllegalArgument;

  void removeChild(Long id) throws DailyRoutineNotFound;

  List<DisplayChildDTO> convertAllChildrenToDto(List<Child> children);

  DisplayChildDTO convertSingleChildToDto(Child child);

  void removeAllChildren() throws DailyRoutineNotFound;
}
