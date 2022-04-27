package mars.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mars.mission.CandidateQuery;
import mars.mission.CandidateResponse;
import mars.mission.models.Candidate;
import mars.mission.service.CandidateService;
import mars.mission.util.CandidateServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Mars Mission Candidate Service Application")
public class MarsMissionController {

    private final CandidateService candidateService;

    @GetMapping("/candidate")
    @Operation(summary = "Get information of all the candidates")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = CandidateResponse.class)))})
    public ResponseEntity<CandidateResponse> getAllCanditatesInfo() {

        CandidateResponse candidateResponse = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidateResponse);
    }

    @PostMapping("/candidate")
    @Operation(summary = "Save a candidate Information")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = Candidate.class)))})
    public ResponseEntity<Candidate> saveCandidateInfo(
            @Parameter(description = "Candidate information that needs to be saved",
                    required = true)
            @RequestBody Candidate candidate) {

        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(savedCandidate);
    }

    @PostMapping("/candidate/filter")
    @Operation(summary = "Filter candidate information by query")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = CandidateResponse.class)))})
    public ResponseEntity<CandidateResponse> queryCandidates(
            @Parameter(description = "Filters to query candidate information. Returns all the candidates if no query given",
                    required = true)
            @RequestBody CandidateQuery candidateQuery) {

        CandidateResponse candidateResponse = candidateService.getCanditateByQuery(candidateQuery);
        return ResponseEntity.ok(candidateResponse);
    }

    @PostMapping(value = "/candidate/save/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save a candidate Information from a csv file")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok")})
    public ResponseEntity saveCandidatesFromFile(
            @Parameter(description = "Upload a csv file with key as 'file'",
                    required = true)
            @RequestParam("file") MultipartFile file) {

        if(!file.getContentType().equals("text/csv"))
            return new ResponseEntity("Only csv file is allowed",HttpStatus.BAD_REQUEST);
        List<Candidate> candidates = CandidateServiceUtil.readCandidateInfoFromFile(file);
        candidateService.saveCandidatesFromFile(candidates);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
