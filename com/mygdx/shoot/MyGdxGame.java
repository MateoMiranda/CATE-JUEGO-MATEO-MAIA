package com.mygdx.shoot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Red.HiloCliente;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Jugador.Jugador;
import Pantallas.PantallaBase;
import Pantallas.PantallaInicial;

public class MyGdxGame extends Game {
	private int maxjugadores = 2;
	public Jugador[] jugadores = new Jugador[maxjugadores];
	public PantallaBase pantallaActual;
	public Camara camara;
	public HiloCliente hc;
	public Viewport viewport;
	SpriteBatch batch;
	Input input = Gdx.input;
	public boolean empieza = false;
	public float[] limites = new float[4];

	@Override
	public void create() {
		hc = new HiloCliente(this);
		for (int i = 0; i < maxjugadores; i++) {
			jugadores[i] = new Jugador(this);
		}
		pantallaActual = new PantallaInicial(this);
		setScreen(pantallaActual);
		Gdx.graphics.setVSync(true);
		Gdx.input.setInputProcessor(new InputAdapter());
		
		batch = new SpriteBatch();
		camara = new Camara();
		viewport = new FitViewport(camara.getAncho(), camara.getAltura(), camara.getCamara());
		viewport.apply();
		
		limites[0] = camara.getAltura() - 25; // arriba
		limites[1] = -20; // abajo
		limites[2] = -15; // izquierda
		limites[3] = camara.getAncho() - 25; //derecha
		hc.start();
	}

	@Override
	public void render() {
		jugadores[0].actualizarEntrada();

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camara.update(Gdx.graphics.getDeltaTime());
		batch.setProjectionMatrix(camara.getCamara().combined);
		pantallaActual.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		Gdx.gl.glClearColor(05, 3, 20, 1);
		render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void pause() {
		resume();

	}

}