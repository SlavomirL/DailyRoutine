package com.slavomirlobotka.dailyroutineforkids.repositories;

import com.slavomirlobotka.dailyroutineforkids.models.Child;
import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  List<Schedule> findByChild(Child child);

  @Query(
      "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Schedule s WHERE s.child.id = :childId AND s.scheduleName = :scheduleName")
  boolean existsByChildIdAndScheduleName(
      @Param("childId") Long childId, @Param("scheduleName") String scheduleName);
}
