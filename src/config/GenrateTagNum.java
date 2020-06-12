package config;

import java.util.UUID;

public final class GenrateTagNum {

  public String getTagNumber() {
	  String uuid = UUID.randomUUID().toString();
    return uuid;
  }
}