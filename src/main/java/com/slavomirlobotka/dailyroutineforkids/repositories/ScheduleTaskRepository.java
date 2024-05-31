package com.slavomirlobotka.dailyroutineforkids.repositories;

import com.slavomirlobotka.dailyroutineforkids.models.Schedule;
import com.slavomirlobotka.dailyroutineforkids.models.ScheduleTask;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long> {
  void deleteByTaskId(Long taskId);

  @Modifying
  @Transactional
  @Query(
      "DELETE FROM ScheduleTask st WHERE st.schedule.id IN (SELECT s.id FROM Schedule s WHERE s.child.id = :childId)")
  void deleteByChildId(@Param("childId") Long childId);

  @Modifying
  @Transactional
  @Query(
      "DELETE FROM ScheduleTask st WHERE st.schedule.id IN (SELECT s.id FROM Schedule s WHERE s.child.id IN (SELECT c.id FROM Child c WHERE c.user.id = :userId))")
  void deleteByUserId(@Param("userId") Long userId);

  List<ScheduleTask> findAllBySchedule(Schedule schedule);

  List<ScheduleTask> findAllByTaskId(Long taskId);
}
