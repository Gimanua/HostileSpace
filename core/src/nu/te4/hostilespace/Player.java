package nu.te4.hostilespace;

import nu.te4.hostilespace.shooting.Shot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Adrian Klasson
 */
public final class Player extends Unit {

    private long lastShootTime;
    
    private Player(PlayerBuilder builder) {
        super(builder.sprite, builder.speed, builder.speed, builder.speed, builder.health);
    }

    @Override
    public void update(){
        float xDirection = 0;
        float yDirection = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            yDirection += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            yDirection -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            xDirection += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            xDirection -= 1;
        }
        
        setDirection(new Vector2(xDirection, yDirection).nor());
    }
    
    @Override
    public void shoot() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !OnCooldown()){
            Shot.ShotBuilder shotBuilder = new Shot.ShotBuilder()
                    .sprite(new Sprite(new Texture("shot.png")))
                    .x(getSprite().getX() + 3)
                    .y(getSprite().getY() + getSprite().getHeight() - 45)
                    .width(Game.getWindowProperties().getWidth() * 0.02f)
                    .height(Game.getWindowProperties().getHeight() * 0.08f)
                    .direction(new Vector2(0, 1))
                    .speed(10)
                    .sender(this);
            Game.addShot(shotBuilder.build());
            
            Shot.ShotBuilder shotBuilder2 = new Shot.ShotBuilder()
                    .sprite(new Sprite(new Texture("shot.png")))
                    .x(getSprite().getX() + getSprite().getWidth() - 15)
                    .y(getSprite().getY() + getSprite().getHeight() - 45)
                    .width(Game.getWindowProperties().getWidth() * 0.02f)
                    .height(Game.getWindowProperties().getHeight() * 0.08f)
                    .direction(new Vector2(0, 1))
                    .speed(10)
                    .sender(this);
            Game.addShot(shotBuilder2.build());
        }
    }
    
    private boolean OnCooldown(){
        long now = System.currentTimeMillis();
        if(now - lastShootTime >= 80){
            lastShootTime = now;
            return false;
        }
        return true;
    }
    
    public static final class PlayerBuilder {
        private Sprite sprite;
        private float x;
        private float y;
        private float speed;
        private double health;
        
        public PlayerBuilder sprite(Sprite sprite){
            this.sprite = sprite;
            return this;
        }
        
        public PlayerBuilder x(float x){
            this.x = x;
            return this;
        }
        
        public PlayerBuilder y(float y){
            this.y = y;
            return this;
        }
        
        public PlayerBuilder speed(float speed){
            this.speed = speed;
            return this;
        }
        
        public PlayerBuilder health(double health){
            this.health = health;
            return this;
        }
        
        public Player build(){
            return new Player(this);
        }
    }
}
