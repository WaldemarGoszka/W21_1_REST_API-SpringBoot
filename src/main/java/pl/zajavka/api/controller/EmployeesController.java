package pl.zajavka.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    // serwisy
    @PostMapping
    public ResponseEntity<?> addEmployee(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "salary") String salary
    ) {
        // metoda tworząca i zapisująca do bazy obiekt pracownika
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> showEmployeeDetails(@PathVariable Integer employeeId) {
        // metoda zwracająca pracownika o zdefiniowanym id
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer employeeId,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String salary
    ) {
        // metoda aktualizująca obiekt pracownika
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        // metoda usuwająca pracownika
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<?> partiallyUpdateEmployee (@PathVariable Integer employeeId) {
        //metoda dodająca jakiś zasób do pracownika
    }
}

