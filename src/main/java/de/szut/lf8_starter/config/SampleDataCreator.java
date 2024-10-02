package de.szut.lf8_starter.config;


import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.project.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SampleDataCreator implements ApplicationRunner {

    private ProjectRepository repository;

    public SampleDataCreator(ProjectRepository repository) {
        this.repository = repository;
    }

    public void run(ApplicationArguments args) {
//        repository.save(new ProjectEntity("Hallo Welt!"));
//        repository.save(new ProjectEntity("Schöner Tag heute"));
//        repository.save(new ProjectEntity("FooBar"));

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
