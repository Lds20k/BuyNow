package br.com.zup.bootcamp.controller.model;

import br.com.zup.bootcamp.domain.entity.Category;

import javax.validation.constraints.NotBlank;

// Intrinsic charge = 1
public class CategoryCreationRequest {

    @NotBlank
    private String name;

    private String predecessorCategory;

    public CategoryCreationRequest(@NotBlank String name) {
        this.name = name;
    }

    public CategoryCreationRequest(@NotBlank String name, String predecessorCategory) {
        this.name = name;
        this.predecessorCategory = predecessorCategory;
    }

    /**
     * @return Category com os atributos convertidos e, caso houver, a categoria antecessora atribuída
     */
    public Category toModel() {
        Category predecessorCategory = null;
        if(this.predecessorCategory != null && !this.predecessorCategory.isBlank()){
            predecessorCategory = new Category();
            predecessorCategory.setId(this.predecessorCategory);
        }

        return new Category(this.name, predecessorCategory);
    }

    public String getPredecessorCategory() {
        return predecessorCategory;
    }

    public String getName() {
        return name;
    }
}
