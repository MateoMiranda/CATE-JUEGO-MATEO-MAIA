package Pantallas;

import com.badlogic.gdx.Gdx;
import Efectos.Bala;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.Camara;
import com.mygdx.shoot.Global;
import com.mygdx.shoot.MyGdxGame;
import Enemigos.Basico;
import Enemigos.MovTipo;
import Fondos.Fondo;
import Personajes.Nave;
import java.util.ArrayList;
import java.util.Iterator;

public class Mapa1 extends PantallaBase {
	Fondo fondo;
	SpriteBatch batch;
	MyGdxGame juego;
	Camara camaraHud;
	BitmapFont font;
	int reloj = 0;
	int invencibilidad = 0;
	Iterator<Basico> iterator; //para ordenar a los enemigos

	public Mapa1(MyGdxGame juego) {
		Global.BalasDisparadas = 0;
		this.juego = juego;
	}

	@Override
	public void show() {
		Global.enemigosTotal = 0;
		balas = new ArrayList<Bala>(); // Lista incrementable de balas disparadas por el jugador
		listaEnemigos = new ArrayList<Basico>();
		iterator = listaEnemigos.iterator();
		//colocar enemigos
		float x = 20;
		float y = 600;

		for (int i = 0; i < 5; i++) {
			listaEnemigos.add(new Basico(x, y, Global.enemigosTotal++, MovTipo.RECTO, "Kenney/Ships/ship_0006.png", 1));
			x += 50;
		}
		
		y = 50;
		for (int i = 0; i < 5; i++) {
			listaEnemigos.add(
					new Basico(x, y, Global.enemigosTotal++, MovTipo.IZQUIERDO, "Kenney/Ships/ship_0007.png", 1));
			x += 20;
			y += 30;
		}

		switch (Global.Jugador) {

		case 1:
			juego.jugadores[0].nave = new Nave(new Texture("nave.png"), 0, 0);
			juego.jugadores[1].nave = new Nave(new Texture("nave2.png"), 120, 0);
			break;
		case 2:
			juego.jugadores[0].nave = new Nave(new Texture("nave2.png"), 120, 0);
			juego.jugadores[1].nave = new Nave(new Texture("nave.png"), 0, 0);
		}

		for (int i = 0; i < 5; i++) {
			listaEnemigos.add(new Basico(-30, y + 50, Global.enemigosTotal++, MovTipo.INTERCEPTOR,
					"Kenney/Ships/ship_0005.png", 1));
			y += 100;
		}
		for (int i = 0; i < 5; i++) {
			listaEnemigos.add(new Basico(400, y + 400, Global.enemigosTotal++, MovTipo.INTERCEPTOR,
					"Kenney/Ships/ship_0005.png", 1));
			y += 100;
		}
		
		Gdx.input.setInputProcessor(new InputAdapter());
		batch = new SpriteBatch();
		juego.jugadores[0].vida = 2;
		juego.jugadores[1].vida = 2;
		fondo = new Fondo("Mapa1.png");
		camaraHud = new Camara();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}

	public void render(float delta) {
		if (reloj >= 60) {
			reloj = 0;
			tiempo++;
		} else {
			reloj++;
		}

		camaraHud.update(delta);

		for (Bala bala : balas) {
			for (int i = 0; i < listaEnemigos.size(); i++) {
				if (bala != null && listaEnemigos.get(i) != null) {
					if (bala.haColisionadoConObjetivo(listaEnemigos.get(i).hitbox)) {
					}

				}
			}
		}

		for (int i = 0; i < juego.jugadores.length; i++) {
			if (juego.jugadores[i].vida > 0) {
				juego.jugadores[i].nave.regEntradaMov(juego.jugadores[i], balas, juego.limites);
				juego.jugadores[i].nave.update(Gdx.graphics.getDeltaTime());
			} else {
				juego.jugadores[i].nave.y = -40;
			}
		}
		fondo.mover(150 * Gdx.graphics.getDeltaTime());
		batch.begin();
		fondo.dibujar(batch);
		for (int i = 0; i < juego.jugadores.length; i++) {
			if (juego.jugadores[i].vida > 0) {
				juego.jugadores[i].nave.dibujar(batch);
			}
		}
		float alto = jugadorAlto();

		actualizarYDibujarEnemigos(juego.pantallaActual.listaEnemigos, alto, batch, juego.camara.getAncho());

		dibujarBalas();
		batch.end();
		batch.setProjectionMatrix(camaraHud.getCamara().combined);
		batch.begin();
		font.draw(batch, "Tiempo:" + tiempo, 50, juego.camara.getAltura() - 50);
		font.draw(batch, "Puntuacion:" + Global.puntuacion, 300, juego.camara.getAltura() - 40);
		font.draw(batch, "Vida:" + juego.jugadores[0].vida, 50, juego.camara.getAltura() - 70);
		font.draw(batch, "PJ:" + Global.Jugador, 50, juego.camara.getAltura() - 110);
		batch.end();

		if (juego.jugadores[0].vida <= 0 && juego.jugadores[1].vida <= 0) {
			this.dispose();
			juego.pantallaActual = new GameOver(juego);
			juego.setScreen(juego.pantallaActual);
			juego.jugadores[0].nave = null;
			juego.jugadores[1].nave = null;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}


	private void dibujarBalas() {
		for (int i = 0; i < balas.size(); i++) {
			Bala bala = balas.get(i);
			if (bala.y > juego.camara.getAltura() || bala.colision) {
				balas.remove(i);
			} else {
				bala.update();
				bala.dibujar(batch);
			}
		}
	}

	private float jugadorAlto() {
		float jugadorAlto = 0;
		for (int i = 0; i < juego.jugadores.length; i++) {
			if (jugadorAlto < juego.jugadores[i].nave.getPosY()) {
				jugadorAlto = juego.jugadores[i].nave.getPosY();
			}
		}
		return jugadorAlto;
	}

	private void actualizarYDibujarEnemigos(ArrayList<Basico> listaEnemigos, float alto, SpriteBatch batch,
			float anchoCamara) {
		ArrayList<Basico> copiaListaEnemigos = new ArrayList<>(listaEnemigos);
		for (Basico enemigo : copiaListaEnemigos) {
			enemigo.update(alto, juego.jugadores[0].nave.getPosX());
			enemigo.dibujar(batch);

			if (enemigo.getX() > anchoCamara + 50 || enemigo.getX() < -100 || enemigo.getY() < -100) {
				listaEnemigos.remove(enemigo);
			}
		}
	}
}
