package Personajes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.shoot.Global;

import Efectos.Bala;
import Jugador.Jugador;
import Pantallas.GameOver;
import Pantallas.MenuPrincipal;
import Pantallas.PantallaInicial;

public class Nave {
	public Rectangle hitbox;
    private Animation<TextureRegion> defaultAnimac;
    private Animation<TextureRegion> arriAnimac;
    private Animation<TextureRegion> arrDerAnimac;
    private Animation<TextureRegion> derAnimac;
    private Animation<TextureRegion> abajoArrAnimac;
    private Animation<TextureRegion> abajoAnimac;
    private Animation<TextureRegion> abajoIzqAnimac;
    private Animation<TextureRegion> izqAnimac;
    private Animation<TextureRegion> arrAnimac;
    private Animation<TextureRegion> animActu;
    private Sprite naveSprite;
    private float elapsedTime;
    public float x, y;
    private float width = 40, height = 40;
    private float velocidad = 2.5f;
    private float tiempoCooldown = 0.3f; 
    float tiempoUltimoDisparo = 0;
    
    public Nave(Texture spriteSheet, float x, float y) {
        this.x = x;
        this.y = y;
        TextureRegion[][] frames = TextureRegion.split(spriteSheet, 64, 64);
        defaultAnimac = new Animation<>(0.25f, frames[0][0]);
        arriAnimac = new Animation<>(0.25f, frames[1][0],frames[0][0]);
        arrDerAnimac = new Animation<>(0.25f, frames[2][0],frames[0][0]);
        derAnimac = new Animation<>(0.25f, frames[3][0],frames[0][0]);
        abajoArrAnimac = new Animation<>(0.25f, frames[4][0],frames[0][0]);
        abajoAnimac = new Animation<>(0.25f, frames[5][0],frames[0][0]);
        abajoIzqAnimac = new Animation<>(0.25f, frames[6][0],frames[0][0]);
        izqAnimac = new Animation<>(0.25f, frames[7][0],frames[0][0]);
        arrAnimac = new Animation<>(0.25f, frames[8][0],frames[0][0]);
        animActu = defaultAnimac;
        naveSprite = new Sprite(frames[0][0]);
        naveSprite.setSize(width, height);
        naveSprite.setPosition(x, y);
        hitbox = new Rectangle(x, y, width/2, height/2);
    }
    
	public void regEntradaMov(Jugador Jugador, ArrayList<Bala> balas,float[] limites) {
	
        if (Jugador.arriba && Jugador.derecha) {
            animActu = arrDerAnimac;
        } else if (Jugador.arriba && Jugador.izquierda) {
            animActu = arrAnimac;
        } else if (Jugador.abajo && Jugador.derecha) {
            animActu = abajoArrAnimac;
        } else if (Jugador.abajo && Jugador.izquierda) {
            animActu = abajoIzqAnimac;
        } else if (Jugador.arriba) {
            animActu = arriAnimac;
        } else if (Jugador.abajo) {
            animActu = abajoAnimac;
        } else if (Jugador.izquierda) {
            animActu = izqAnimac;
        } else if (Jugador.derecha) {
            animActu = derAnimac;
        } else {
            animActu = defaultAnimac;
        }
        if (Jugador.arriba&&y<= limites[0]) {
            y += velocidad;
        }
        else if (Jugador.abajo && y>= limites[1]) {
            y -= velocidad;
        }
         if (Jugador.izquierda && x>=limites[2]) {
            x -= velocidad;
        }
        else if (Jugador.derecha && x<=limites[3]) {
            x += velocidad;
        }
        if(Jugador.j)
        {
         disparar(balas);
        }
        Mover();
        }
	
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        naveSprite.setRegion(animActu.getKeyFrame(elapsedTime, true));
    }

    public void dibujar(SpriteBatch batch) {
        naveSprite.draw(batch);
    }
    
    public void Mover(){
        naveSprite.setPosition(x,y);
        hitbox.setPosition(x, y);
    	}
    
    public void generarBala(ArrayList<Bala> balas){
    	System.out.println("Bala");
    	
    	Bala bala= new Bala(x+14,y+30);
    	 balas.add(bala);
    	}
  
    public void disparar(ArrayList<Bala> balas){ 

    	float tiempoActual = TimeUtils.nanoTime() / 1000000000.0f;
    	
    if (tiempoActual - tiempoUltimoDisparo > tiempoCooldown) {
    generarBala(balas);
     
    tiempoUltimoDisparo = tiempoActual;
    		    }
    	}

	public float getPosY() {
		return y;
	}

	public float getPosX() {
		return x;
	}
   }

