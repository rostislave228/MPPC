package Rostislav.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Rostislav.Models.Book.BookBuilder;
import Rostislav.Models.EBook.EBookBuilder;
import Rostislav.Models.Newspaper.NewspaperBuilder;
import Rostislav.Models.PrintedProduct.PrintedProductBuilder;

@Configuration
public class ModelConfig {

    @Bean
    public PrintedProductBuilder printedProductBuilder() {
        return new PrintedProductBuilder();
    }

    @Bean
    public BookBuilder bookBuilder() {
        return new BookBuilder();
    }
    
    @Bean
    public NewspaperBuilder newspaperBuilder() {
        return new NewspaperBuilder();
    }

    @Bean
    public EBookBuilder eBookBuilder() {
        return new EBookBuilder();
    }

}