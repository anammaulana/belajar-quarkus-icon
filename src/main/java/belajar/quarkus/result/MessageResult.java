package belajar.quarkus.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageResult {
    @JsonProperty("status")
    public Boolean status;
    @JsonProperty("message")
    public String message;
    public MessageResult(){}

    public MessageResult(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}

