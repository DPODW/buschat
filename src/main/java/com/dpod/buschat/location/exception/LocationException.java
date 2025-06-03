package com.dpod.buschat.location.exception;

import lombok.Getter;

@Getter
public class LocationException extends RuntimeException {
  LocationErrorCode locationErrorCode;

  public LocationException(LocationErrorCode locationErrorCode) {
    super(locationErrorCode.getMessage());
    this.locationErrorCode = locationErrorCode;
  }
}
