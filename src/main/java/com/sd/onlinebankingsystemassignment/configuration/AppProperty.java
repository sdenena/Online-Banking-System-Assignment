package com.sd.onlinebankingsystemassignment.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperty {
    private Allow allow;

    @Getter
    @Setter
    public static class Allow {
        private List<String> method;
        private List<String> origin;
        private Header header;

        @Getter
        @Setter
        public static class Header {
            private List<String> allowed;
            private List<String> exposed;
        }
    }
}
