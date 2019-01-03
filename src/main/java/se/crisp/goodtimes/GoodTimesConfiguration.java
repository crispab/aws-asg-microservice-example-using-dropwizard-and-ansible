package se.crisp.goodtimes;

import io.dropwizard.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodTimesConfiguration extends Configuration {

  private String message = "Good times!";
}
