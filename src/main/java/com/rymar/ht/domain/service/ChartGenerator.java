package com.rymar.ht.domain.service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.knowm.xchart.*;
import org.springframework.stereotype.Component;

@Component
public class ChartGenerator {
  private static final int WIDTH_SIZE = 600;
  private static final int HEIGHT_SIZE = 400;

  public InputStream generateChart(double[] data, String displayName) {
    double[] yLen = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      yLen[i] = i;
    }
    XYChart chart =
        new XYChartBuilder().width(WIDTH_SIZE).height(HEIGHT_SIZE).title(displayName).build();
    chart.getStyler().setLegendVisible(false);
    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.addSeries(displayName, yLen, data);
    return toStream(chart);
  }

  private static InputStream toStream(XYChart chart) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
      return new ByteArrayInputStream(baos.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Chart generation error", e);
    }
  }
}
