package belajar.quarkus.resource;

import java.util.List;

import belajar.quarkus.entity.Person;
import belajar.quarkus.param.PersonParam;
import belajar.quarkus.result.MessageResult;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    public Uni<List<Person>> listAll() {
        return Person.listAll();
    }
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Uni<Response> saveRole(PersonParam param) {
    Person person = new Person();
    person.age = param.age;
    person.name = param.name;

    return Panache
            .withTransaction(() -> Person.persist(person)
                .onItem().transform(item -> {
                    MessageResult result = new MessageResult(true, "Data Berhasil di Tambahkan");
                    return Response.ok(result).build();
                })
                .onFailure().recoverWithItem(ex -> {
                    MessageResult result = new MessageResult(false, "Failed to save person: " + ex.getMessage());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
                }));
}
   
}
