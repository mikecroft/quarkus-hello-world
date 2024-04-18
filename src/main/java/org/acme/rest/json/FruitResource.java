package org.acme.rest.json;

import io.quarkus.panache.common.Sort;
import jakarta.json.Json;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Path("/fruits")
public class FruitResource {
//
//    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
//
//    public FruitResource() {
//        fruits.add(new Fruit("Apple", "Winter fruit"));
//        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
//    }
//
//    @GET
//    public Set<Fruit> list() {
//        return fruits;
//    }
//
//    @POST
//    public Set<Fruit> add(Fruit fruit) {
//        fruits.add(fruit);
//        return fruits;
//    }
//
//    @DELETE
//    public Set<Fruit> delete(Fruit fruit) {
//        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
//        return fruits;
//    }
//}
@GET
public List<Fruit> get() {
    return Fruit.listAll(Sort.by("name"));
}

    @GET
    @Path("{id}")
    public Fruit getSingle(@PathParam("id") Long id) {
        Fruit entity = Fruit.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public List<Fruit> create(Fruit fruit) {
        if (fruit.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        fruit.persist();
        return Fruit.listAll(Sort.by("name"));
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Fruit update(@PathParam("id") Long id, Fruit fruit) {
        if (fruit.name == null) {
            throw new WebApplicationException("Fruit Name was not set on request.", 422);
        }

        Fruit entity = Fruit.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }

        entity.name = fruit.name;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Fruit entity = Fruit.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }

    @DELETE
    @Transactional
    public List<Fruit> delete(Fruit fruit) {
        Fruit.delete("name", fruit.name);

        return Fruit.listAll(Sort.by("name"));
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }

    }
}