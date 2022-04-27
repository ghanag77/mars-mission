package mars.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mars.mission.models.Employee;
import mars.mission.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeServiceImpl employeeService;

    @PostMapping("/employee/register")
    @Operation(summary = "Register new employee")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok")})
    public ResponseEntity registerNewUser(@Valid @RequestBody Employee employee) {

        if(employee.getUsername() == null || employee.getUsername().isEmpty()
              || employee.getPassword() == null || employee.getPassword().isEmpty())   {
            return new ResponseEntity("username/password cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if(employeeService.isUserAlreadyExist(employee.username)) {
            return new ResponseEntity("username already taken", HttpStatus.BAD_REQUEST);
        }

        employeeService.saveEmployeeInfo(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }
}
