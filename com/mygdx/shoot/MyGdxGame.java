package com.mygdx.shoot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Personajes.Nave;
import Red.HiloCliente;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Fondos.Fondo;
import Jugador.Jugador;
import Pantallas.MenuPrincipal;
import Pantallas.PantallaBase;
import Pantallas.PantallaInicial;
public class MyGdxGame extends Game {
	   private boolean arrastre;
	private boolean reenderizador;
	private int cantJugadores=2;
	public Jugador[] jugadores= new Jugador[cantJugadores];
public PantallaBase pantallaActual;
	  public Camara camara;
	public HiloCliente hc;
	public Viewport viewport;
	SpriteBatch batch;
	Input input = Gdx.input;
	public boolean empieza=false;
	 public float[] limites= new float[4];
	 int contador =0;
		//0 limiteSuperior=camara.get;
		//1limInf=-20;
		//2limIzq=-20;
		//3limDer;
	  @Override
	    public void create() {
		  reenderizador=false;
		  Gdx.graphics.setContinuousRendering(true);
		  hc=new HiloCliente(this);
		  for (int i = 0; i < cantJugadores; i++) {
			jugadores[i]=new Jugador(this);
		}
		  pantallaActual=new PantallaInicial(this);
		  setScreen(pantallaActual); 
		  Gdx.graphics.setVSync(true);
	        Gdx.input.setInputProcessor(new InputAdapter());
	        camara = new Camara();
	        viewport = new FitViewport(camara.getAncho(), camara.getAltura(), camara.getCamara());
	        viewport.apply();
	        batch = new SpriteBatch();
limites[0] =camara.getAltura()-25;
limites[1]=-20;
limites[2]=-15;
limites[3]=camara.getAncho()-25;
	 hc.start();
	 }
	    @Override
	    public void render() {
	    	if(reenderizador||Gdx.graphics.isContinuousRendering())
	    	{
				jugadores[0].actualizarEntrada();
				
	        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); 
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        camara.update(Gdx.graphics.getDeltaTime());
	        batch.setProjectionMatrix(camara.getCamara().combined);
	   pantallaActual.render(Gdx.graphics.getDeltaTime());

	if (Gdx.input.isTouched()) {
           // Se está tocando la pantalla
           arrastre = true; // o cualquier lógica que desees para el arrastre
           reenderizador = true;
       } else {
           arrastre= false;
       }

       reenderizador = false;
	    }
	    	contador++;
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
	   public void pause()
	   {
		   resume();
		   
	   }
	  
	}