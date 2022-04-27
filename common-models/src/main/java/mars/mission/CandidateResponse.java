package mars.mission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import mars.mission.models.Candidate;

import java.util.List;

@Data
public class CandidateResponse {

    @JsonProperty("Result")
    private List<Candidate> candidateList;
}
