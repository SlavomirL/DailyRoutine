package com.slavomirlobotka.dailyroutineforkids.services;

import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import com.slavomirlobotka.dailyroutineforkids.models.roles.Role;
import com.slavomirlobotka.dailyroutineforkids.repositories.ParentRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final ParentRepository parentRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Parent parent = parentRepository.findByEmail(email);
    if (parent == null) {
      throw new UsernameNotFoundException("email not found");
    }
    return new User(
        parent.getEmail(), parent.getPassword(), mapRolesToAuthorities(List.of(parent.getRole())));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    return roles.stream()
        .map(x -> new SimpleGrantedAuthority(x.getName()))
        .collect(Collectors.toList());
  }
}
