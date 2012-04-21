package com.calumgilchrist.ld23.tinyworld.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.calumgilchrist.ld23.tinyworld.core.TinyWorld;

public class TinyWorldActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/calumgilchrist/ld23/tinyworld/resources");
    PlayN.run(new TinyWorld());
  }
}
