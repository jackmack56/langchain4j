package dev.langchain4j.model.openai.internal.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;

@JsonDeserialize(builder = SecuritySuggest.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SecuritySuggest {
    @JsonProperty
    private final String action;


    public SecuritySuggest(Builder builder) {
        this.action = builder.action;

    }

    public String action() {
        return action;
    }


    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof Delta
                && equalTo((SecuritySuggest) another);
    }


    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + Objects.hashCode(action);
        return h;
    }


    @Override
    public String toString() {
        return "SecuritySuggest{"
                + "action=" + action + "}";
    }


    private boolean equalTo(SecuritySuggest another) {
        return Objects.equals(action, another.action);
    }


    public static Builder builder() {
        return new Builder();
    }


    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class Builder {
        private String action;

        public Builder action(String action) {
            this.action = action;
            return this;
        }
        public SecuritySuggest build() {
            return new SecuritySuggest(this);
        }
    }
}
