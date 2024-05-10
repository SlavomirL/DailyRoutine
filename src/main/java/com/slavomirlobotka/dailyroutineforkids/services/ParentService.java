package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import java.util.List;

public interface ParentService {

  void createChild(String childName, RegisterChildDTO registerChildDTO);

  List<DisplayChildDTO> getAllChildren();

  User getCurrentParent();
}
