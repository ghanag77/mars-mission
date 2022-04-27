package mars.mission.models;

import mars.mission.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Age")
    private Integer Age;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Weight")
    private Integer Weight;

    @JsonProperty("Height")
    private Integer Height;

    @JsonProperty("Fitness")
    private FitnessStatus FitnessStatus;

    @JsonProperty("Exercise")
    private String Exercise;
}
