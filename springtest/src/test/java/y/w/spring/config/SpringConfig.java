package y.w.spring.config;

import y.w.spring.AOP.TrackingDogAspect;
import y.w.spring.beans.Animal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig
{
    @Bean
    public Animal petDog()
    {
        return new Animal("Lion King", 5);
    }

    @Bean
    public TrackingDogAspect trackingDog()
    {
        return new TrackingDogAspect();
    }
}
