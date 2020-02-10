package nu.te4.hostilespace.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nu.te4.hostilespace.Drawable;
import nu.te4.hostilespace.Moveable;
import nu.te4.hostilespace.Shootable;

/**
 * An enemy unit.
 * @author Adrian Klasson
 */
public class Unit implements Shootable, Moveable, Drawable {
    
    protected final Sprite sprite;
    protected float speed;
    protected double health;

    public Unit(Sprite sprite, float speed, double health) {
        this.sprite = sprite;
        this.speed = speed;
        this.health = health;
    }

    @Override
    public final void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
    
    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Sprite getSprite() {
        return sprite;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
