package mars.mission.service.impl;

import lombok.RequiredArgsConstructor;
import mars.mission.CandidateQuery;
import mars.mission.CandidateResponse;
import mars.mission.models.Candidate;
import mars.mission.service.CandidateService;
import mars.mission.util.HttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final HttpService httpService;

    @Value("${external.view-service.url}")
    private String viewBaseUrl;

    @Value("${external.view-service.candidates}")
    private String candidateServicePath;

    @Value("${external.view-service.filter-candidate}")
    public String filterCandidatePath;

    @Value("${external.view-service.save-data-from-file}")
    public String saveCandidateCsv;

    @Override
    public CandidateResponse getAllCandidates() {
        String url = viewBaseUrl + candidateServicePath;
        Map<String, String> headers = new HashMap<>();
        return httpService.getEntity(url, headers);
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {

        String url = viewBaseUrl + candidateServicePath;
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return null;
    }

    @Override
    public void saveCandidatesFromFile(List<Candidate> candidates) {
        String url = viewBaseUrl + candidateServicePath + saveCandidateCsv;
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpService.postEntity(url, headers, candidates);
    }

    @Override
    public CandidateResponse getCanditateByQuery(CandidateQuery candidateQuery) {
        String url = viewBaseUrl + candidateServicePath + filterCandidatePath;
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return httpService.postEntity(url, headers, candidateQuery);
    }

}
