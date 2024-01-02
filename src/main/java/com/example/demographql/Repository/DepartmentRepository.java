package com.example.demographql.Repository;

import com.example.demographql.DTO.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DepartmentRepository extends ReactiveCrudRepository<Department,Integer> {
         Flux<Department> getDepartmentById(Integer id);
}
