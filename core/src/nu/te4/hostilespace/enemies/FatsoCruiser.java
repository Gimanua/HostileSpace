package nu.te4.hostilespace.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.te4.hostilespace.Explodable;

/**
 * A very tanky enemy.
 * @author Adrian Klasson
 */
public final class FatsoCruiser extends Unit implements Explodable {

    Callable<List<Unit>> getNearbyUnits;

    public FatsoCruiser(Callable<List<Unit>> getNearbyUnits, Sprite sprite, float speed, double health) {
        super(sprite, speed, health);
        this.getNearbyUnits = getNearbyUnits;
    }
    
    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move() {
        sprite.translateX(speed);
    }

    @Override
    public void explode() {
        try {
            List<Unit> nearbyUnits = getNearbyUnits.call();
        } catch (Exception ex) {
            Logger.getLogger(FatsoCruiser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
