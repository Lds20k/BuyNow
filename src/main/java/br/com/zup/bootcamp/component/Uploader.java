package br.com.zup.bootcamp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.stream.Collectors;

// Intrinsic charge = 1
@Component
public class Uploader {

    // Configurável no arquivo application.yaml
    @Value("${dev_mode}")
    public boolean fake;

    /**
     * @param files Arquivos que serão enviador para o servidor de arquivos
     * @return String com o local dos arquivos
     */
    public Collection<String> send(Collection<MultipartFile> files){
        if(fake)
            return files
                    .stream()
                    .map(file -> "http://bucket.fake.io/" + file.getOriginalFilename())
                    .collect(Collectors.toSet());

        // Alguma coisa que fara o upload verdadeiro e retornara os links

        return files
                .stream()
                .map(file -> "http://bucket.io/" + file.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
