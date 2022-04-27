package mars.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mars.mission.models.Candidate;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateQuery extends Candidate {

    @JsonProperty("minAge")
    private Integer minAge;

    @JsonProperty("maxAge")
    private Integer maxAge;

    @JsonProperty("minWeight")
    private Integer minWeight;

    @JsonProperty("maxWeight")
    private Integer maxWeight;

    @JsonProperty("minHeight")
    private Integer minHeight;

    @JsonProperty("maxHeight")
    private Integer maxHeight;

    @JsonProperty("Countries")
    private List<String> countries;
}
