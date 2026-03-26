package com.rymar.ht.service;

import com.rymar.ht.repository.ActivityRepo;
import com.rymar.ht.entity.Activity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
  private final ActivityRepo activityRepo;

  private static final LocalDateTime WEEK_AGO = LocalDateTime.now().minusDays(7);
  private static final LocalDateTime DAY_AGO = LocalDateTime.now().minusDays(1);

  public void addActivity(String name, Double count){
      var act = new Activity();
      act.setName(name);
      act.setCount_ex(count);
      activityRepo.save(act);
  }

  public double[] getWeekActivityCount_ByName(String activityName) {
    List<Activity> weakly_activity =
        activityRepo.findByWeekAndNameIgnoreCase(WEEK_AGO, activityName);
    return weakly_activity.stream().mapToDouble(Activity::getCount_ex).toArray();
  }

  public double[] getDailyActivityCount_ByName(String activityName) {
    List<Activity> daily_activity =
        activityRepo.findByWeekAndNameIgnoreCase(DAY_AGO, activityName);
      return daily_activity.stream().mapToDouble(Activity::getCount_ex).toArray();
  }
}
