package com.rymar.ht.service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.knowm.xchart.*;
import org.springframework.stereotype.Component;

@Component
public class ChartGenerator {

  public InputStream generateChart( double[] data, String displayName) {
      double[] yLen = new double[data.length];
      for(int i = 0; i < data.length ; i++) {
          yLen[i] = i;
      }
    XYChart chart = new XYChartBuilder()
            .width(600)
            .height(400)
            .title(displayName)
            .build();
    chart.getStyler().setLegendVisible(false);
    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.addSeries(displayName, yLen, data);
    return toTelegramStyle(chart);
  }

  private static InputStream toTelegramStyle(XYChart chart) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);

      return new ByteArrayInputStream(baos.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Chart generation error", e);
    }
  }
}
