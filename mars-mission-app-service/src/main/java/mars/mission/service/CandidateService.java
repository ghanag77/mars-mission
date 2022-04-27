package mars.mission.service;

import mars.mission.CandidateQuery;
import mars.mission.CandidateResponse;
import mars.mission.models.Candidate;

import java.util.List;

public interface CandidateService {

    CandidateResponse getAllCandidates();

    Candidate saveCandidate(Candidate candidate);

    void saveCandidatesFromFile(List<Candidate> candidates);

    CandidateResponse getCanditateByQuery(CandidateQuery candidateQuery);
}
