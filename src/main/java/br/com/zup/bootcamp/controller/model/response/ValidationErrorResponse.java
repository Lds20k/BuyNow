package br.com.zup.bootcamp.controller.model.response;

import br.com.zup.bootcamp.controller.model.Violation;

import java.util.ArrayList;
import java.util.List;

// Intrinsic charge = 1
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }

    public void add(Violation violation){
        this.violations.add(violation);
    }
}
