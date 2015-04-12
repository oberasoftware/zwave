package com.oberasoftware.home.zwave.local;

import com.oberasoftware.home.zwave.ZWaveConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author renarj
 */
@Configuration
@Import(ZWaveConfiguration.class)
@PropertySource("classpath:application.properties")
public class LocalSpringContainer {
    private static final AnnotationConfigApplicationContext CONTEXT = new AnnotationConfigApplicationContext(LocalSpringContainer.class);

    public static <T> T getBean(Class<T> type) {
        return CONTEXT.getBean(type);
    }

    static void destroy() {
        CONTEXT.destroy();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
