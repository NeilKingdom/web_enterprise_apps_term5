package cst8218.hoan0105.game;

import cst8218.hoan0105.entity.Sprite;
import cst8218.hoan0105.entity.SpriteFacade;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Only one instance in entire application - Moves the sprites
 * @author tgk
 */
//start the singleton on application startup --- concurrency to make sure asynchronise
@Singleton
@Startup
@RunAs("Admin")
@PermitAll
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SpriteGame {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;
    
    List<Sprite> sprites;  // the list of Sprites in the game
    
    @Inject
    private SpriteFacade spriteFacade;
    
    //lock on read
    @Lock(LockType.READ)
    public List<Sprite> getSpriteList() {
        return sprites;
    }
    //lock on write
    @Lock(LockType.WRITE)
    public void newSprite(MouseEvent event, Color color) {
        Sprite newSprite = new Sprite(HEIGHT, WIDTH, color);
        spriteFacade.create(newSprite);
        System.out.println("New sprite created");
    }
    @Lock(LockType.WRITE)
    public void moveSprite(){
        //move all the sprites and update them in the database
        sprites = spriteFacade.findAll();
        for (Sprite sprite : sprites) {
            sprite.move();
            spriteFacade.edit(sprite);
        }
    }
    @Lock(LockType.WRITE)
    public void editSprite(Long id,Sprite newsprite){
        spriteFacade.edit(id,newsprite);
    }
    public SpriteFacade getFacade(){
        return spriteFacade;
    }

    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            @SuppressWarnings("SleepWhileInLoop")
            public void run() {

                while (true) {
                    //move all the sprites and update them in the database
                    moveSprite();
                    //sleep while waiting to display the next frame of the animation
                    try {
                        Thread.sleep(100);  // wake up roughly 10 frames per second
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
