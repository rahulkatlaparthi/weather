package com.example.demo.controller;

import java.util.Objects;

import com.example.demo.model.TempeartureRange;

class WrapperTemperature {
    private TempeartureRange e;

    public WrapperTemperature(TempeartureRange e) {
        this.e = e;
    }

    public TempeartureRange unwrap() {
        return this.e;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        WrapperTemperature that = (WrapperTemperature) o;
//        return Objects.equals(e.getLatitude(), that.e.getLatitude());
//    }
    
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      WrapperTemperature that = (WrapperTemperature) o;
      return Objects.equals(e.getLatitude(), that.e.getLatitude());
  }


    @Override
    public int hashCode() {
        return Objects.hash(e.getLatitude());
    }
}
