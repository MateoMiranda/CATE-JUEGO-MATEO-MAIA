package Pantallas;

import Fondos.Fondo;
import Jugador.Jugador;
import Personajes.Nave;

import javax.management.remote.SubjectDelegationPermission;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.shoot.Global;
import com.mygdx.shoot.MyGdxGame;

public class MenuPrincipal extends PantallaBase {

MyGdxGame juego;
Fondo fondo;
SpriteBatch batch;
BitmapFont font;
boolean esperando=false;
public MenuPrincipal(MyGdxGame myGdxGame)
{
	 juego=myGdxGame;
}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter());
		batch = new SpriteBatch();
		fondo=new Fondo("MenuPrincipal.jpg");
		 font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		if(juego.jugadores[0].k||Global.empieza==true)
		{
			if(Global.empieza)
			{
				esperando=false;
	 juego.pantallaActual=new Mapa1(juego);
	 juego.setScreen(juego.pantallaActual);
	 System.out.println("Empieza");
	 this.dispose();
			}
			else if(!esperando){
				juego.hc.enviarMensaje("Conexion");
	esperando=true;
			}
			}
		batch.begin();
		fondo.dibujar(batch);
		font.draw(batch, "Presione la tecla K" , 50, juego.camara.getAltura() - 50);
		if(esperando) {
			font.draw(batch, "Esperando a otro jugador..." , 50, juego.camara.getAltura() - 100);
			}
		batch.end();
		super.render(delta);
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
}
