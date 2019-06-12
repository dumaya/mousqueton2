package dumaya.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dumaya.dev.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String>{}
