package mars.mission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mars.mission.CandidateQuery;
import mars.mission.CandidateResponse;
import mars.mission.models.Candidate;
import mars.mission.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MarsMissionController {

    private final CandidateService candidateService;

    @GetMapping("/candidate")
    public ResponseEntity<CandidateResponse> getAllCandidatesInfo() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setCandidateList(candidates);
        return ResponseEntity.ok(candidateResponse);
    }

    @PostMapping("/candidate")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate) {

        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(candidate);

    }

    @PostMapping("/candidate/filter")
    public ResponseEntity<CandidateResponse> queryCandidates(@RequestBody CandidateQuery candidateQuery) {

        List<Candidate> candidates = candidateService.getCanditateByQuery(candidateQuery);
        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setCandidateList(candidates);
        return ResponseEntity.ok(candidateResponse);
    }

    @PostMapping("/candidate/save/file")
    public ResponseEntity saveCandidatesFromFile(@RequestBody List<Candidate> candidates) {

        candidateService.saveCandidatesFromFile(candidates);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
