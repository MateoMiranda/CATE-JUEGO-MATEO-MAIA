package Pantallas;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.MyGdxGame;

import Efectos.Bala;
import Enemigos.Basico;
import Fondos.Fondo;

public  class PantallaBase implements Screen {
    public ArrayList<Bala> balas;
	 public ArrayList<Basico> listaEnemigos;
public String mensaje ="Base";
public boolean empieza;
public boolean termina;
Fondo fondo; 
SpriteBatch batch;
MyGdxGame juego;
public int tiempo;
PantallaBase()
{
}
@Override
	public void show() {
	
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void render(float delta, SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
	public void setEmpieza(boolean empieza) {
		this.empieza = empieza;
	}
	
}

