package com.calumgilchrist.ld23.tinyworld.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.calumgilchrist.ld23.tinyworld.core.TinyWorld;

public class TinyWorldHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("tinyworld/");
    PlayN.run(new TinyWorld());
  }
}
