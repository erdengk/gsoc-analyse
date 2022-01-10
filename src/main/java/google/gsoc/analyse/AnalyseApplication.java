package google.gsoc.analyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@SpringBootApplication
public class AnalyseApplication {

    public static void main( String[] args ) {
        SpringApplication.run( AnalyseApplication.class, args );
    }

}
