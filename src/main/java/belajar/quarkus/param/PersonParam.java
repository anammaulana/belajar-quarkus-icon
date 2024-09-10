package belajar.quarkus.param;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PersonParam {
    @JsonProperty("name")
    public String name;

    @JsonProperty("age")
    public Integer age;

}