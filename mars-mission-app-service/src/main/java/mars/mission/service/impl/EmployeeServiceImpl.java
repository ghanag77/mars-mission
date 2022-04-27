package mars.mission.service.impl;

import mars.mission.models.Employee;
import mars.mission.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EmployeeServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("employee"));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public boolean isUserAlreadyExist(String username) {
        return employeeRepository.existsByUsername(username);
    }

    public void saveEmployeeInfo(Employee employee) {

        Employee newEmployee = new Employee(employee.getUsername(), encoder.encode(employee.getPassword()));

        employeeRepository.save(newEmployee);
    }
}
