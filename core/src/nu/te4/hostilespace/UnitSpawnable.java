package nu.te4.hostilespace;

import nu.te4.hostilespace.enemies.Unit;

/**
 * Can spawn a Unit.
 * @author Adrian Klasson
 */
public interface UnitSpawnable {
    
    /**
     * Spawns a Unit.
     * @param unit The Unit to spawn.
     */
    public void spawnUnit(Unit unit);
}
