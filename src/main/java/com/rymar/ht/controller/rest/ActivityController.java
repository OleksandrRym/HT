package com.rymar.ht.controller.rest;

import com.rymar.ht.service.ActivityService;
import com.rymar.ht.service.ChartGenerator;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("img")
@RequiredArgsConstructor
public class ActivityController {
  private final ActivityService activityService;
  private final ChartGenerator chartGenerator;

  @GetMapping("/{activity}")
  public ResponseEntity<InputStreamResource> getImage(@PathVariable("activity") String activity) {
    var l = activityService.getWeekActivityCount_ByName(activity);
    InputStream chartStream = chartGenerator.generateChart(l, activity);
    return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_PNG)
        .body(new InputStreamResource(chartStream));
  }
}
