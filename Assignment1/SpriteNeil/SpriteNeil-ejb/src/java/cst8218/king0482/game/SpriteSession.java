package cst8218.king0482.game;

import cst8218.king0482.entity.Sprite;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.ejb.Stateful;

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

    @Override
    public List<Sprite> getSpriteList() {
        return spriteGame.getSpriteList();
    }

    @Override
    public void newSprite(MouseEvent event) {
        spriteGame.newSprite(event, color);
    }
    @Override
    public int getHeight() {
        return SpriteGame.HEIGHT;
    }
    @Override
    public int getWidth() {
        return SpriteGame.WIDTH;
    }
}
