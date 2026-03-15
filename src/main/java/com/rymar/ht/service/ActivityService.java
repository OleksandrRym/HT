package com.rymar.ht.service;

import com.rymar.ht.domain.Activity;
import com.rymar.ht.repository.ActivityRepo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityService {
  private final ActivityRepo activityRepo;

  public double[] getActivityByWeek() {
    LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
    List<Activity> list = activityRepo.findByWeekAndNameIgnoreCase(weekAgo, "run");
    System.out.println(list.toString());
      double[] list1 = list.stream().mapToDouble(el -> el.getCount_ex()).toArray();
      return list1;
  }
}
