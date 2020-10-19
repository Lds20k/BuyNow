package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.controller.model.Uploader;
import br.com.zup.bootcamp.domain.entity.Image;
import br.com.zup.bootcamp.domain.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

// Intrinsic charge = 4
public class AddImagesRequest {

    @NotNull(message = "{mandatory}")
    @Size(min = 1, message = "{size.min}")
    private Collection<MultipartFile> images = new ArrayList<>();

    public AddImagesRequest(@Size(min = 1, message = "{size.min}") Collection<MultipartFile> images) {
        this.images = images;
    }

    public Collection<MultipartFile> getImages() {
        return images;
    }

    public void setImages(Collection<MultipartFile> images) {
        this.images = images;
    }

    /**
     * @param product Product que a imagem pertence
     * @param uploader Um objeto de Uploader
     * @return Collection de image
     */
    public Collection<Image> toModel(Product product, Uploader uploader) {
        Collection<String> links = uploader.send(this.getImages());
        Collection<Image> imagesEntities = new ArrayList<>();

        for (String link : links){
            imagesEntities.add(new Image(link, product));
        }
        return imagesEntities;
    }
}
