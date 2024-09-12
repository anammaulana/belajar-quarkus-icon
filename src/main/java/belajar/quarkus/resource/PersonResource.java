package belajar.quarkus.resource;


import belajar.quarkus.entity.Person;
import belajar.quarkus.param.PersonParam;
import belajar.quarkus.result.MessageResult;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
@Produces(MediaType.APPLICATION_JSON)
public Uni<Response> listAll() {
    return Person.listAll()
        .onItem().ifNotNull().transform(personList -> {
            // Buat MessageResult dengan data List<Person>
            MessageResult result = new MessageResult(true, "Data berhasil diambil", personList);
            return Response.ok(result).build();
        })
        .onItem().ifNull().continueWith(() -> {
            // Jika data tidak ditemukan, kembalikan pesan error
            MessageResult result = new MessageResult(false, "Person tidak ditemukan", null);
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        })
        .onFailure().recoverWithItem(ex -> {
            // Jika terjadi kesalahan, kembalikan pesan error
            MessageResult result = new MessageResult(false, "Gagal mengambil data: " + ex.getMessage(), null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
        });
}
  @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getPersonById(@PathParam("id") Long id) {
        return Person.findById(id)
                .onItem().ifNotNull().transform(person -> {
                    // Buat MessageResult dengan data Person
                    MessageResult result = new MessageResult(true, "Data berhasil diambil", person);
                    return Response.ok(result).build();
                })
                .onItem().ifNull().continueWith(() -> {
                    // Jika data tidak ditemukan, kembalikan pesan error
                    MessageResult result = new MessageResult(false, "Person tidak ditemukan", null);
                    return Response.status(Response.Status.NOT_FOUND).entity(result).build();
                })
                .onFailure().recoverWithItem(ex -> {
                    // Jika terjadi kesalahan, kembalikan pesan error
                    MessageResult result = new  MessageResult(false, "Gagal mengambil data: " + ex.getMessage(), null);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
                });
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
                            MessageResult result = new MessageResult(true, "Data Berhasil di Tambahkan", person);
                            return Response.ok(result).build();
                        })
                        .onFailure().recoverWithItem(ex -> {
                            MessageResult result = new MessageResult(false, "Failed to save person: " + ex.getMessage(),
                                    null);
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
                        }));
    }

     @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deletePersonById(@PathParam("id") Long id) {
        return Person.findById(id)
                .onItem().ifNotNull().transformToUni(person -> {
                    // Hapus Person jika ditemukan
                    return Panache.withTransaction(person::delete)
                            .replaceWith(Response.ok(new MessageResult(true, "Person berhasil dihapus", person)).build());
                })
                .onItem().ifNull().continueWith(() -> {
                    // Jika Person tidak ditemukan
                    MessageResult result = new MessageResult(false, "Person tidak ditemukan", null);
                    return Response.status(Response.Status.NOT_FOUND).entity(result).build();
                })
                .onFailure().recoverWithItem(ex -> {
                    // Jika terjadi kesalahan dalam proses penghapusan
                    MessageResult result = new MessageResult(false, "Gagal menghapus Person: " + ex.getMessage(), null);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
                });
    }
}