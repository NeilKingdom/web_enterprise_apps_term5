package cst8218.king0482.entity;

import cst8218.king0482.resources.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author tgk
 * @version 1.0
 * @since 2022-11-10
 * 
 * The facade class for Sprite entities. Returns the
 * entity manager for the Sprite Persistence Unit.
 */
@Stateless
public class SpriteFacade extends AbstractFacade<Sprite> {
    @PersistenceContext(unitName = "SpriteNeil-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpriteFacade() {
        super(Sprite.class);
    }
}
