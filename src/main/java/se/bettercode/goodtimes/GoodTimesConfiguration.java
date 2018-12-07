package se.bettercode.goodtimes;

import io.dropwizard.Configuration;
import lombok.Data;

@Data
public class GoodTimesConfiguration extends Configuration {

  private String message = "Good times!";
}
