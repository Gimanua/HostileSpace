package nu.te4.hostilespace;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Can be drawn to the screen.
 * @author Adrian Klasson
 */
public interface Drawable {
    
    /**
     * Draws itself to the screen.
     * @param spriteBatch The SpriteBatch doing the drawing.
     */
    public void draw(SpriteBatch spriteBatch);
    
    /**
     * Disposes itself.
     */
    public void dispose();
}
