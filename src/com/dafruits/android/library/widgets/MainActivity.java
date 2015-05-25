package com.dafruits.android.library.widgets;

import android.app.Activity;
import android.os.Bundle;

import com.dafruits.android.library.R;

/**
 * 
 * @description TODO
 * @autor Ryze 2015-5-21 上午9:30:00
 * 
 */
public class MainActivity extends Activity {

  
  private PullToRefreshView pullToRefreshView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_layout);
  }

}
