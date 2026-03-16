package com.rymar.ht.domain.service;

import com.rymar.ht.adapter.out.repository.ActivityRepo;
import com.rymar.ht.domain.entity.Activity;
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
