package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.domain.entity.Category;

import javax.validation.constraints.NotBlank;

// Intrinsic charge = 1
public class CategoryCreationRequest {

    @NotBlank(message = "{notblank}")
    private String name;

    private String predecessor;

    public CategoryCreationRequest(@NotBlank String name) {
        this.name = name;
    }

    public CategoryCreationRequest(@NotBlank String name, String predecessor) {
        this.name = name;
        this.predecessor = predecessor;
    }

    /**
     * @return Category com os atributos convertidos e, caso houver, a categoria antecessora atribuída
     */
    public Category toModel() {
        Category predecessorCategory = null;
        if(this.predecessor != null && !this.predecessor.isBlank()){
            predecessorCategory = new Category();
            predecessorCategory.setId(this.predecessor);
        }

        return new Category(this.name, predecessorCategory);
    }

    public String getPredecessor() {
        return predecessor;
    }

    public String getName() {
        return name;
    }
}
