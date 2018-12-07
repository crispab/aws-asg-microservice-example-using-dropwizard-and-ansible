package se.bettercode.goodtimes;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoodTimesApp extends Application<GoodTimesConfiguration> {

  @Override
  public void initialize(Bootstrap<GoodTimesConfiguration> bootstrap) {
    log.info("Initializing");
    // nothing to do yet
  }

  @Override
  public void run(GoodTimesConfiguration configuration, Environment environment) {
    log.info("Running");
    // nothing to do yet
  }

  public static void main(String[] args) throws Exception {
    new GoodTimesApp().run(args);
  }

}
