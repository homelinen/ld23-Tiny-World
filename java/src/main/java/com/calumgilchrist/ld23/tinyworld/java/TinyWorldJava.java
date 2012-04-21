package com.calumgilchrist.ld23.tinyworld.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.calumgilchrist.ld23.tinyworld.core.TinyWorld;

public class TinyWorldJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("com/calumgilchrist/ld23/tinyworld/resources");
    PlayN.run(new TinyWorld());
  }
}
