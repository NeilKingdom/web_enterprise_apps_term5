package cst8218.king0482.resources;

import cst8218.king0482.entity.Sprite;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author tgk
 * @version 1.0
 * @since 2022-11-10
 * 
 * Adds support for a RESTful API. This includes
 * things like creating a new entity, updating existing
 * entities, returning the count of entities, etc.
 */
@Stateless
@Path("cst8218.king0482.game.sprite")
public class SpriteFacadeREST extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "SpriteNeil-ejbPU")
    private EntityManager em;

    /**
     * @since 2022-11-10
     * 
     * Default constructor.
     */
    public SpriteFacadeREST() {
        super(Sprite.class);
    }

    /**
     * @since 2022-11-10
     * @param entity The Sprite entity
     * 
     * Creates a new Sprite entity or overwrites
     * an existing one.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Sprite entity) {
        super.create(entity);
    }
    
    /**
     * @since 2022-11-10
     * @param id The ID specified in the URL
     * @param entity The Sprite entity
     * @return Returns an HTTP response. This can be either a 404 or Status.OK
     * 
     * Updates an existing entity.
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Sprite entity) {
        // Check if any sprites with the specified ID exist
        if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Update if ID in JSON content matches ID in PathParam
        else if (id == (long)entity.getId()) {
            super.update(entity);
            return Response.status(Response.Status.OK).entity(entity).build();
        }
        // ID in JSON content does not match ID in PathParam
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @since 2022-11-10
     * @param id The ID specified in the URL
     * @param entity The Sprite entity
     * @return Returns an HTTP response. This can be either a 404 or Status.OK
     * 
     * Overwrites with an existing entity.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Sprite entity) {
        // Check if any sprites with the specified ID exist
        if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Merge if ID in JSON content matches ID in PathParam
        else if (id == (long)entity.getId()) {
            super.edit(entity);
            return Response.status(Response.Status.OK).entity(entity).build();
        }
        // ID in JSON content does not match ID in PathParam
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @since 2022-11-10
     * @param id The ID specified in the URL
     * 
     * Removes an existing entity.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * @since 2022-11-10
     * @param id The ID specified in the URL
     * @return Returns the Sprite entity if found, otherwise returns null
     * 
     * Searches for an entity with the ID specified.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Sprite find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * @since 2022-11-10
     * @return Returns all existing Sprite entities
     * 
     * Returns all existing Sprite entities.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findAll() {
        return super.findAll();
    }

    /**
     * @since 2022-11-10
     * @param from The starting search index
     * @param to The ending search index
     * @return Returns a list of entities in a given range
     * 
     * Returns a list of entities within a certain range. The
     * range is determined by the from and to arguments in the 
     * URL.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    /**
     * @since 2022-11-10
     * @return Returns a count of existing entities
     * 
     * Returns a count of existing entities.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * @since 2022-11-10
     * @return Returns the entity manager
     * 
     * Returns the entity manager for the Sprite entity class.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
