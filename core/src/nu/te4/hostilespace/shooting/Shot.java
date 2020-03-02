/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.hostilespace.shooting;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.lang.annotation.Annotation;
import nu.te4.hostilespace.Drawable;
import nu.te4.hostilespace.Game;
import nu.te4.hostilespace.Unit;
import nu.te4.hostilespace.movement.Moveable;

/**
 *
 * @author Adrian Klasson
 */
public final class Shot implements Drawable, Moveable {

    private final Sprite sprite;
    private final Vector2 direction;
    private final float speed;
    private final Unit sender;
    
    private Shot(ShotBuilder builder) {
        this.sprite = builder.sprite;
        this.sprite.setPosition(builder.x, builder.y);
        this.sprite.setSize(builder.width, builder.height);
        this.direction = builder.direction;
        this.speed = builder.speed;
        this.sender = builder.sender;
    }
    
    public boolean hits(Unit unit){
        return sprite.getBoundingRectangle().overlaps(unit.getSprite().getBoundingRectangle());
    }
    
    public boolean isWithinWorldBounds(){
        return this.sprite.getY() >= Game.getWindowProperties().getHeight();
    }
    
    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    @Override
    public void move() {
        sprite.translate(direction.x * speed, direction.y * speed);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }

    public Unit getSender() {
        return sender;
    }

    public static final class ShotBuilder {
        private Sprite sprite;
        private float x;
        private float y;
        private float width;
        private float height;
        private Vector2 direction;
        private float speed;
        private Unit sender;
        
        public ShotBuilder sprite(Sprite sprite){
            this.sprite = sprite;
            return this;
        }
        
        public ShotBuilder x(float x){
            this.x = x;
            return this;
        }
        
        public ShotBuilder y(float y){
            this.y = y;
            return this;
        }
        
        public ShotBuilder width(float width){
            this.width = width;
            return this;
        }
        
        public ShotBuilder height(float height){
            this.height = height;
            return this;
        }
        
        public ShotBuilder direction(Vector2 direction){
            this.direction = direction;
            return this;
        }
        
        public ShotBuilder speed(float speed){
            this.speed = speed;
            return this;
        }
        
        public ShotBuilder sender(Unit sender){
            this.sender = sender;
            return this;
        }
        
        public Shot build(){
            return new Shot(this);
        }
    }
}
