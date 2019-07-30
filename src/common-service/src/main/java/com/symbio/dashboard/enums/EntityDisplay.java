package com.symbio.dashboard.enums;

public enum EntityDisplay {
  HIDE(0),
  SHOW(1),
  DELETE(4);

  private EntityDisplay(int display){
    this.display = display;
  }
  private int display;

  public int getValue(){
    return this.display;
  }

}
