package nu.te4.hostilespace.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import nu.te4.hostilespace.Unit;
import java.util.Random;
import nu.te4.hostilespace.Explodable;
import nu.te4.hostilespace.Game;
import nu.te4.hostilespace.shooting.Shot;

/**
 * A very tanky enemy.
 * @author Adrian Klasson
 */
public final class FatsoCruiser extends Unit implements Explodable {

    private boolean exploded;
    private long lastShootTime;
    private static final Random RANDOM = new Random();

    private FatsoCruiser(FatsoCruiserBuilder builder) {
        super(builder.sprite, builder.x, builder.y, builder.speed, builder.health);
    }
    
    @Override
    public void shoot() {
        if(!OnCooldown()){
            Shot.ShotBuilder shotBuilder = new Shot.ShotBuilder()
                    .sprite(new Sprite(new Texture("shot.png")))
                    .x(getSprite().getX() + 3)
                    .y(getSprite().getY() + getSprite().getHeight() - 45)
                    .width(Game.getWindowProperties().getWidth() * 0.02f)
                    .height(Game.getWindowProperties().getHeight() * 0.08f)
                    .direction(new Vector2(0, -1))
                    .speed(10)
                    .sender(this);
            Game.addShot(shotBuilder.build());
            
            Shot.ShotBuilder shotBuilder2 = new Shot.ShotBuilder()
                    .sprite(new Sprite(new Texture("shot.png")))
                    .x(getSprite().getX() + getSprite().getWidth() - 15)
                    .y(getSprite().getY() + getSprite().getHeight() - 45)
                    .width(Game.getWindowProperties().getWidth() * 0.02f)
                    .height(Game.getWindowProperties().getHeight() * 0.08f)
                    .direction(new Vector2(0, -1))
                    .speed(10)
                    .sender(this);
            Game.addShot(shotBuilder2.build());
        }
    }
    
    private boolean OnCooldown(){
        long now = System.currentTimeMillis();
        if(now - lastShootTime >= 1000){
            lastShootTime = now;
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        
        float xDirection = RANDOM.nextBoolean() ? RANDOM.nextFloat() : -RANDOM.nextFloat();
        float yDirection = RANDOM.nextBoolean() ? RANDOM.nextFloat() : -RANDOM.nextFloat();
        
        setDirection(new Vector2(xDirection, yDirection).nor());
    }
    
    @Override
    public void explode(Unit player) {
        if(this.getSprite().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())){
            this.exploded = true;
        }
    }

    @Override
    public boolean isExploded() {
        return exploded;
    }

    public static final class FatsoCruiserBuilder{
        private Sprite sprite;
        private float x;
        private float y;
        private float speed;
        private double health;
        
        public FatsoCruiserBuilder sprite(Sprite sprite){
            this.sprite = sprite;
            return this;
        }
        
        public FatsoCruiserBuilder x(float x){
            this.x = x;
            return this;
        }
        
        public FatsoCruiserBuilder y(float y){
            this.y = y;
            return this;
        }
        
        public FatsoCruiserBuilder speed(float speed){
            this.speed = speed;
            return this;
        }
        
        public FatsoCruiserBuilder health(double health){
            this.health = health;
            return this;
        }
        
        public FatsoCruiser build(){
            return new FatsoCruiser(this);
        }
    }
}
