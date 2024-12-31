/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.hoan0105.entity;

import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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


/**
 *
 * @author J-PC
 */
//For FORM comment out DeclareROLES and ROLES ALLOWED
@DeclareRoles({"Admin","RestGroup","JsfGroup"})
@RolesAllowed({"Admin","JsfGroup"})
@Stateless
public class AppUserFacade extends AbstractFacade<AppUser> {

    @PersistenceContext(unitName = "SpriteJohnPU")
    private EntityManager em;

    public AppUserFacade() {
        super(AppUser.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
