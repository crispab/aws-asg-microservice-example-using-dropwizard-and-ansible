package se.bettercode.goodtimes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoodResponse {

  @JsonProperty
  private String id;

  @JsonProperty
  private String message;

  @JsonProperty
  private String hostname;

}
