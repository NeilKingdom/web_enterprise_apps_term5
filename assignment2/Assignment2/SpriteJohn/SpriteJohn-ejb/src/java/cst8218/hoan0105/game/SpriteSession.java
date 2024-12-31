package cst8218.hoan0105.game;

import cst8218.hoan0105.entity.Sprite;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author tgk
 */
@Stateful
public class SpriteSession implements SpriteSessionRemote {

    public static final Random random = new Random();
    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    
    @Inject
    private SpriteGame spriteGame;

    public List<Sprite> getSpriteList() {
        return spriteGame.getSpriteList();
    }

    @Override
    public void newSprite(MouseEvent event) {
        spriteGame.newSprite(event, color);
    }
    @Override
    public int getHeight() {
        return spriteGame.HEIGHT;
    }
    @Override
    public int getWidth() {
        return spriteGame.WIDTH;
    }

    @Override
    public void setSprite(Long id,Sprite newsprite) {
        spriteGame.editSprite(id,newsprite);
    }

}
