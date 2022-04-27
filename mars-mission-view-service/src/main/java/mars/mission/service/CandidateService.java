package mars.mission.service;

import mars.mission.CandidateQuery;
import mars.mission.models.Candidate;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAllCandidates();

    Candidate saveCandidate(Candidate candidate);

    void saveCandidatesFromFile(List<Candidate> candidates);

    List<Candidate> getCanditateByQuery(CandidateQuery candidateQuery);
}
