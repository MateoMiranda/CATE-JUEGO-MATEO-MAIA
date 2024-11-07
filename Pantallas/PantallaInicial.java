package Pantallas;
import Fondos.Fondo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.MyGdxGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
public class PantallaInicial extends PantallaBase {
MyGdxGame juego ;
Fondo fondo=new Fondo("MenuInicial.png");
SpriteBatch batch;
Texture whiteTexture = new Texture(1, 1, Pixmap.Format.RGBA8888);
private boolean isTransitioning = true;
private float transitionTimer = 0.0f;
private float transitionDuration = 2.0f; // Duración de la transición en segundos

	public PantallaInicial(MyGdxGame myGdxGame) {
	juego=myGdxGame;
	
	}

	@Override
	public void show() {
		   batch = new SpriteBatch();
		super.show();
	}

	@Override
	public void render(float delta) {
	    
		  if (isTransitioning) {
			   // Aumentar gradualmente la transparencia para que la pantalla se vuelva blanca
		        transitionTimer += 0.5f * delta; // Ajusta la velocidad de transición según tus necesidades

		        // Dibuja un rectángulo blanco con el valor de transparencia en el lote de sprites (batch)
		        batch.setColor(1,1,1,transitionTimer);
		        batch.begin();
		        fondo.dibujar(batch);
		        batch.draw(whiteTexture, 0, 0, juego.camara.getAncho(),juego.camara.getAltura());
		        batch.end();

	            // Comprueba si la transición ha terminado
	            if (transitionTimer >= transitionDuration) {
	                isTransitioning = false;
	                transitionTimer = 0.0f;
	                // Cambia a la siguiente pantalla después de la transición
	                juego.pantallaActual=new MenuPrincipal(juego);
	                juego.setScreen(juego.pantallaActual);
	            }
		else {
          
        }
		super.render(delta);
		
	}
	

}
}
