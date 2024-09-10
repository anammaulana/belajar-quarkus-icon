package belajar.quarkus.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageResult {
    @JsonProperty("status")
    public Boolean status;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public Object data;
    public MessageResult(){}

    public MessageResult(Boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

