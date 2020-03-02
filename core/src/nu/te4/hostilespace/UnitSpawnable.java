package nu.te4.hostilespace;

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
