package cst8218.king0482.resources;

import cst8218.king0482.entity.Sprite;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * @author tgk
 * @version 1.0
 * @since 2022-11-10
 * @param <T>
 * 
 * The abstract facade class that is implemented in SpriteFacadeREST.
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    /**
     * @since 2022-11-10
     * @param entityClass The class of the entity
     * 
     * Default constructor.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * @since 2022-11-10
     * @return The entity manager for the entity being managed
     * 
     * To be implemented in SpriteFacadeREST.
     */
    protected abstract EntityManager getEntityManager();

    /**
     * @since 2022-11-10
     * @param entity The entity to persist
     * 
     * Invokes the entity manager and places the entity
     * in the "managed" state.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * @since 2022-11-10
     * @param entity The entity to merge
     * 
     * Invokes the entity manager and updates an
     * existing entity.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    /**
     * @since 2022-11-10
     * @param entity The entity to update
     *
     * Updates the calling Sprite entity with the attributes of 
     * the Sprite object passed by reference to the function. 
     * Values which are considered to be null are ignored. 0 is 
     * considered to be null for primitive types.
     */
    public void update(T entity) {
        if (!(entity instanceof Sprite)) {
            return;
        }
        
        Sprite tmpEntity = (Sprite)entity;
        Sprite obj = (Sprite)find(tmpEntity.getId());
        if (obj.isSameAs(tmpEntity)) {
            return;
        }
        
        if (tmpEntity.getPanelWidth() != 0) obj.setPanelWidth(tmpEntity.getPanelWidth());
        if (tmpEntity.getPanelHeight() != 0) obj.setPanelHeight(tmpEntity.getPanelHeight());
        if (tmpEntity.getX() != 0) obj.setX(tmpEntity.getX());
        if (tmpEntity.getY() != 0) obj.setY(tmpEntity.getY());          
        if (tmpEntity.getDx() != 0) obj.setDx(tmpEntity.getDx());
        if (tmpEntity.getDy() != 0) obj.setDy(tmpEntity.getDy());           
        if (tmpEntity.getColor() != null) obj.setColor(tmpEntity.getColor());
    }

    /**
     * @since 2022-11-10
     * @param entity The entity to remove
     * 
     * Invokes the entity manager and places an
     * entity in the "removed" state.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * @since 2022-11-10
     * @param id The ID of the entity being searched for
     * @return The matching entity if found, or null if not found
     * 
     * Invokes the entity manager and attempts to locate
     * an entity with matching ID.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * @since 2022-11-10
     * @return A list of all the entities currently stored in the DB
     * 
     * Invokes the entity manager and returns all existing
     * entities stored in the data source.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * @since 2022-11-10
     * @param range An array containing the first and last index of entities to return
     * @return A list of all the entities within the specified range
     * 
     * Invokes the entity manager and returns all entities 
     * that are within the range specified in the range[] array.
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * @since 2022-11-10
     * @return A count of the entities that exist in the DB
     * 
     * Invokes the entity manager and returns a count
     * of the entities that are currently stored in the Data
     * Source.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
