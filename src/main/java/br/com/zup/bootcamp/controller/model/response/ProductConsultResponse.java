package br.com.zup.bootcamp.controller.model.response;

import br.com.zup.bootcamp.domain.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Intrinsic charge = 1
public class ProductConsultResponse {

    private Collection<String> images = new ArrayList<>();

    private String name;

    private BigDecimal price;

    private Collection<Map<String, String>> characteristics = new ArrayList<>();

    private String description;

    private Float rating;

    private Integer totalRatingVotes;

    private Collection<Map<String, String>> opinions = new ArrayList<>();

    private Collection<String> questions = new ArrayList<>();

    @Deprecated
    public ProductConsultResponse() {}

    /**
     * @param productEntity Product que sera convertido em ProductConsultResponse
     * @return ProductConsultResponse com as informações do produto
     */
    public static ProductConsultResponse toResponse(Product productEntity) {
        ProductConsultResponse response = new ProductConsultResponse();

        response.images = productEntity.imagesToCollection();
        response.name = productEntity.getName();
        response.price = productEntity.getPrice();
        response.characteristics = productEntity.characteristicsToCollection();
        response.description = productEntity.getDescription();
        response.opinions = productEntity.opinionsToCollection();
        response.rating = productEntity.calculateRatingAverage();
        response.totalRatingVotes = productEntity.getOpinions().size();
        response.questions = productEntity.questionsToCollection();

        return response;
    }

    public Collection<String> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Collection<Map<String, String>> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Float getRating() {
        return rating;
    }

    public Integer getTotalRatingVotes() {
        return totalRatingVotes;
    }

    public Collection<Map<String, String>> getOpinions() {
        return opinions;
    }

    public Collection<String> getQuestions() {
        return questions;
    }
}
