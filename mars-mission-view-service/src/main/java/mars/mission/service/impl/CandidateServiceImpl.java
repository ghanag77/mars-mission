package mars.mission.service.impl;

import mars.mission.CandidateQuery;
import mars.mission.models.Candidate;
import mars.mission.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Candidate> getAllCandidates() {

        List<Candidate> candidates = mongoTemplate.findAll(Candidate.class);
        return candidates;

    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {

        Candidate savedCandidate = mongoTemplate.save(candidate);
        return savedCandidate;
    }

    @Override
    public void saveCandidatesFromFile(List<Candidate> candidates) {
        mongoTemplate.insertAll(candidates);
    }

    @Override
    public List<Candidate> getCanditateByQuery(CandidateQuery candidateQuery) {

        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if(candidateQuery.getName() != null && candidateQuery.getName().length() > 0)
            criteria.add(Criteria.where("Name").is(candidateQuery.getName()));

        if(candidateQuery.getMaxAge() != null)
            criteria.add(Criteria.where("Age").lte(candidateQuery.getMaxAge()));

        if(candidateQuery.getAge() != null && candidateQuery.getAge() != 0)
            criteria.add(Criteria.where("Age").is(candidateQuery.getAge()));

        if(candidateQuery.getMinAge() != null)
            criteria.add(Criteria.where("Age").gte(candidateQuery.getMinAge()));

        if(candidateQuery.getMaxWeight() != null)
            criteria.add(Criteria.where("Weight").lte(candidateQuery.getMaxWeight()));

        if(candidateQuery.getWeight() != null && candidateQuery.getWeight() != 0)
            criteria.add(Criteria.where("Weight").is(candidateQuery.getWeight()));

        if(candidateQuery.getMinWeight() != null)
            criteria.add(Criteria.where("Weight").gte(candidateQuery.getMinWeight()));

        if(candidateQuery.getMaxHeight() != null)
            criteria.add(Criteria.where("Height").lte(candidateQuery.getMaxWeight()));

        if(candidateQuery.getHeight() != null && candidateQuery.getHeight() != 0)
            criteria.add(Criteria.where("Height").is(candidateQuery.getHeight()));

        if(candidateQuery.getMinHeight() != null)
            criteria.add(Criteria.where("Height").gte(candidateQuery.getMinHeight()));

        if(candidateQuery.getCountry() != null && candidateQuery.getCountry().length() > 0)
            criteria.add(Criteria.where("Country").is(candidateQuery.getCountry()));

        if(candidateQuery.getFitnessStatus() != null)
            criteria.add(Criteria.where("FitnessStatus").is(candidateQuery.getFitnessStatus()));

        if(candidateQuery.getExercise() != null && candidateQuery.getExercise().length() != 0)
            criteria.add(Criteria.where("Exercise").is(candidateQuery.getExercise()));

        if(candidateQuery.getCountries() != null && candidateQuery.getCountries().size() > 0) {
            List<String> countries = candidateQuery.getCountries();
            criteria.add(Criteria.where("Country").in(countries));
        }

        List<Candidate> candidates = new ArrayList<>();

        if(criteria.size() > 0) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

            candidates = mongoTemplate.find(query, Candidate.class);
        } else {
            candidates = mongoTemplate.findAll(Candidate.class);
        }

        return candidates;
    }
}
