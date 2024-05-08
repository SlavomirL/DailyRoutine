package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.dtos.RegisterChildDTO;

public interface ParentService {

  void createChild(String childName, RegisterChildDTO registerChildDTO);
}
