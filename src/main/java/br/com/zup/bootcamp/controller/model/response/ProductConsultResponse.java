package br.com.zup.bootcamp.controller.model.response;

import br.com.zup.bootcamp.domain.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Intrinsic charge = 9
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

        for(Image image : productEntity.getImages())
            response.images.add(image.getLink());

        response.name = productEntity.getName();
        response.price = productEntity.getPrice();

        for(Characteristic characteristic : productEntity.getCharacteristics()) {
            Map<String, String> aux = new HashMap<>();
            aux.put(characteristic.getTitle(), characteristic.getValue());
            response.characteristics.add(aux);
        }

        response.description = productEntity.getDescription();

        Integer sum = 0;
        for(Opinion opinion : productEntity.getOpinions()){
            sum += opinion.getRating();

            Map<String, String> aux = new HashMap<>();
            aux.put(opinion.getTitle(), opinion.getDescription());
            response.opinions.add(aux);
        }

        Integer totalOpinion = productEntity.getOpinions().size();
        response.rating = sum.floatValue() / totalOpinion;
        response.totalRatingVotes = totalOpinion;

        for(Question question : productEntity.getQuestions()){
            response.questions.add(question.getTitle());
        }

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
