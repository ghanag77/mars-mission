package mars.mission.repository;

import mars.mission.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Employee findByUsername(String username);

    Boolean existsByUsername(String username);
}
