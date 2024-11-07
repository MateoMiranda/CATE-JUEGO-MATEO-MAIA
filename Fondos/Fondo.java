package Fondos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.Camara;

public class Fondo {
    private Texture fondoTex;
    private float y;

    public Fondo(String rutaFondo) {
        fondoTex = new Texture(rutaFondo);
        y = 0;
    }
    public Fondo(String rutaFondo, Camara camara) {
        fondoTex = new Texture(rutaFondo);
        y = 0;
    }

    public void mover(float velocidadY) {
        y -= velocidadY;
        if (y <= -fondoTex.getHeight()) {
            y = 0;
        }
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(fondoTex, 0, y);
        batch.draw(fondoTex, 0, y + fondoTex.getHeight());
    }
}