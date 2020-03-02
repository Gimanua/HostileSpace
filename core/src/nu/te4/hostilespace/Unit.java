package nu.te4.hostilespace;

import nu.te4.hostilespace.shooting.Shootable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import nu.te4.hostilespace.movement.Moveable;

/**
 * A unit (ship).
 * @author Adrian Klasson
 */
public abstract class Unit implements Shootable, Moveable, Drawable {
    
    private final Sprite sprite;
    private Vector2 direction = new Vector2(0, 0);
    private float speed;
    private double health;
    private boolean dead;

    protected Unit(Sprite sprite, float x, float y, float speed, double health) {
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
        this.speed = speed;
        this.dead = false;
        this.health = health;
    }

    public abstract void update();
    
    @Override
    public final void dispose(){
        sprite.getTexture().dispose();
    }

    @Override
    public final void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public final void move(){
        if(isWithinWorldBounds(direction.x * speed, direction.y * speed)){
            sprite.translate(direction.x * speed, direction.y * speed);
        }
    }
    
    private boolean isWithinWorldBounds(float xTranslate, float yTranslate){
        float potentialX = sprite.getX() + xTranslate;
        float potentialY = sprite.getY() + yTranslate;
        
        WindowProperties windowProperties = Game.getWindowProperties();
        if(potentialX < 0 || potentialX + sprite.getWidth() >= windowProperties.getWidth()){
            return false;
        }
        else if(potentialY < 0 || potentialY + sprite.getHeight() >= windowProperties.getHeight()){
            return false;
        }
        return true;
    }
    
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getDirection() {
        return direction;
    }
    
    public void setDirection(Vector2 direction){
        this.direction = direction;
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

    public void damage(double damage){
        setHealth(this.health -= damage);
    }
    
    public void setHealth(double health) {
        this.health = health;
        if(this.health <= 0){
            setDead(true);
        }
    }

    public boolean isDead() {
        return dead;
    }

    private void setDead(boolean dead) {
        this.dead = dead;
    }
}
