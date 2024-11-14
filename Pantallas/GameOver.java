package Pantallas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.Global;
import com.mygdx.shoot.MyGdxGame;
import Fondos.Fondo;

public class GameOver extends PantallaBase {
	BitmapFont font;
	private boolean esperando = false;
	private boolean[] confirmacion = { false, false };

	public GameOver(MyGdxGame juego) {
		esperando = false;
		font = new BitmapFont();
		this.juego = juego;
		font.getData().setScale(1.5f);
		font.setColor(Color.WHITE);
	}

	@Override
	public void show() {
		fondo = new Fondo("GameOver.png");
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		salir();
		continuar();
		batch.begin();
		fondo.dibujar(batch);
		font.draw(batch, "Puntuacion:" + Global.puntuacion, 300, juego.camara.getAltura() - 50);
		font.draw(batch, "Enemigos Muertos:" + Global.enemigosMuertos, 300, juego.camara.getAltura() - 20);
		font.draw(batch, "Balas disparadas:" + Global.BalasDisparadas, 300, juego.camara.getAltura() - 100);
		font.draw(batch, "Presione j para reintentar:", 50, juego.camara.getAltura() - 80);
		if (esperando) {
			font.draw(batch, "Esperando a otro jugador...", 50, juego.camara.getAltura() - 100);
		}
		batch.end();
	}

	private void continuar() {
		for (int j = 0; j < juego.jugadores.length; j++) {
			if (juego.jugadores[j].j == true) {
				confirmacion[j] = true;
				esperando = true;
			}
		}
		if (confirmacion[0] == true && confirmacion[1] == true) {
			Global.puntuacion = 0;
			juego.pantallaActual = new Mapa1(juego);
			juego.setScreen(juego.pantallaActual);
			this.dispose();
		}
	}

	private void salir() {
		if (juego.jugadores[0].k == true) {
			juego.pantallaActual.dispose();
			juego.dispose();
		}
	}
}
