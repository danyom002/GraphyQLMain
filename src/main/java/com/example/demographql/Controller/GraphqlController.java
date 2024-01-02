package com.example.demographql.Controller;

import com.example.demographql.AddEmployeeInput;
import com.example.demographql.Repository.Employee;
import com.example.demographql.Repository.DepartmentRepository;
import com.example.demographql.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Controller
@Slf4j
public class GraphqlController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public GraphqlController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }
    Function<AddEmployeeInput, Employee> mapping = aei ->{
        var employee = new Employee();
        employee.setName(aei.getName());
        employee.setSalary(aei.getSalary());
        employee.setDepartmentId(aei.getDepartmentId());
        log.info("Received a request to add an employee");
        return employee;
    };
    //@SchemaMapping(typeName = "Schema", field = "addEmployee")
    @MutationMapping
    public Mono<Employee> addEmployee(@Argument AddEmployeeInput addEmployeeInput){
        return this.employeeRepository.save(mapping.apply(addEmployeeInput));
    }
    @QueryMapping
    public Flux<Employee> employeeByName(@Argument String employeeName){
        return this.employeeRepository.getEmployeeByName(employeeName);
    }


//    public Mono<Employee> addEmploy(@Argument AddEmployeeInput addEmployeeInput){
//        var employee = new Employee();
//        Function<AddEmployeeInput,Employee> mapping = aei ->{
//            employee.setName(aei.getName());
//            employee.setSalary(aei.getSalary());
//            employee.setDepartmentId(aei.getDepartmentId());
//            return employee;
//        };
//        return employeeRepository.save(mapping.apply(addEmployeeInput));
//    }

}
