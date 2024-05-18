package com.slavomirlobotka.dailyroutineforkids.repositories;

import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildRepository extends JpaRepository<Child, Long> {

  List<Child> findByUser(User user);

  @Query(
      "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Child c WHERE c.user.id = :userId AND c.name = :name")
  boolean existsByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);
}
