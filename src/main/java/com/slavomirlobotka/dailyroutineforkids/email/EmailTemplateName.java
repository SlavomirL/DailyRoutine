package com.slavomirlobotka.dailyroutineforkids.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplateName {
  ACTIVATE_ACCOUNT("greetings-page"),
  VERIFY_BY_CODE("verification-page");

  private final String name;
}
