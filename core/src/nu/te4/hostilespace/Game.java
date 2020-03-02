package nu.te4.hostilespace;

import nu.te4.hostilespace.shooting.Shot;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import nu.te4.hostilespace.enemies.FatsoCruiser;

public class Game extends ApplicationAdapter {

    private static WindowProperties windowProperties;
    private static final Random RANDOM = new Random();
    private static ReentrantLock lock = new ReentrantLock();
    Texture background;
    SpriteBatch batch;
    private static List<Unit> units;
    private static Unit player;
    static List<Shot> shots;

    @Override
    public void create() {
        batch = new SpriteBatch();
        windowProperties = new WindowProperties(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.jpeg");
        shots = new ArrayList();
        units = new ArrayList();
        player = new Player.PlayerBuilder()
                .sprite(new Sprite(new Texture("xwing.png")))
                .x(0)
                .y(0)
                .speed(4)
                .health(100)
                .build();
        units.add(player);

        Sprite cruiserSprite = new Sprite(new Texture("fatso_cruiser.png"));
        Thread spawnEnemyThread = new Thread(() -> {
            Unit cruiser = new FatsoCruiser.FatsoCruiserBuilder()
                    .sprite(new Sprite(cruiserSprite))
                    .x((Game.getWindowProperties().getWidth() - 100) * RANDOM.nextFloat() + 50)
                    .y(Game.getWindowProperties().getHeight() - 100)
                    .speed(4)
                    .health(100)
                    .build();
            Game.addUnit(cruiser);
        });
        
        ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(1);
        threadPool.scheduleAtFixedRate(spawnEnemyThread, 0, 2, TimeUnit.SECONDS);
    }

    private void update() {
        lock.lock();
        units.forEach(unit -> unit.update());
        units.forEach(unit -> unit.move());
        units.forEach(unit -> unit.shoot());

        shots.forEach(shot -> shot.move());
        shots.removeIf(shot -> {
            //Kolla efter kollision
            for (Unit unit : units) {
                if (shot.getSender() != unit && shot.hits(unit)) {
                    unit.damage(5);
                    return true;
                }
            }
            //Kolla ifall utanför världen
            return shot.isWithinWorldBounds();
        });
        units.forEach(unit -> {
            if(unit instanceof Explodable){
                Explodable explodableUnit = ((Explodable) unit);
                explodableUnit.explode(player);
                if(explodableUnit.isExploded()){
                    player.damage(20);
                }
            }
        });
        units.removeIf(unit -> {
            return unit instanceof Explodable && ((Explodable) unit).isExploded();
        });
        units.removeIf(unit -> unit.isDead());
        lock.unlock();
    }

    @Override
    public void render() {
        update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lock.lock();
        units.forEach(unit -> unit.draw(batch));
        shots.forEach(shot -> shot.draw(batch));
        lock.unlock();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static void addShot(Shot shot) {
        shots.add(shot);
    }
    
    public static void addUnit(Unit unit){
        lock.lock();
        units.add(unit);
        lock.unlock();
    }

    public static WindowProperties getWindowProperties() {
        return windowProperties;
    }
}
