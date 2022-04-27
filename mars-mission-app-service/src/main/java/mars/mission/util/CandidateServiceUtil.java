package mars.mission.util;

import mars.mission.enums.FitnessStatus;
import mars.mission.models.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateServiceUtil {

    public static List<Candidate> readCandidateInfoFromFile(MultipartFile file) {
        List<Candidate> candidates = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int index=0;
            String[] tempArr;
            String line=" ";
            while((line = bufferedReader.readLine()) != null) {
                if(index == 0) {
                    index++;
                    continue;
                }
                tempArr = line.split(",");
                Candidate candidate = new Candidate();
                candidate.setName(tempArr[1]);
                candidate.setAge(tempArr[2].length() != 0 ? Integer.parseInt(tempArr[2]) : 0);
                candidate.setCountry(tempArr[3]);
                candidate.setWeight(tempArr[2].length() != 0 ? Integer.parseInt(tempArr[4]) : 0);
                candidate.setHeight(tempArr[2].length() != 0 ? Integer.parseInt(tempArr[5]) : 0);
                candidate.setExercise(tempArr[6]);
                FitnessStatus fitnessStatus = (tempArr[7].equals("FIT")) ? FitnessStatus.Fit : FitnessStatus.Unfit;
                candidate.setFitnessStatus(fitnessStatus);
                candidates.add(candidate);
            }
        } catch (IOException e) {
            return null;
        }
        return candidates;
    }
}
