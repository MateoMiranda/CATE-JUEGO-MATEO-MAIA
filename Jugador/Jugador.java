package Jugador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.shoot.Global;
import com.mygdx.shoot.MyGdxGame;
import Personajes.Nave;

public class Jugador {
public boolean invencible = false;
public String nombre;
public Nave nave;
public boolean arriba = false ;
public boolean abajo = false ;
public boolean izquierda = false ;
public boolean derecha = false ;
public boolean j = false ;
public boolean k = false ;
public int vida = 5;
private MyGdxGame juego;

public Jugador(MyGdxGame juego){
this.juego=juego;
System.out.println("Jugador creado");
}

public void actualizarEntrada() {
    arriba = Gdx.input.isKeyPressed(Input.Keys.W);
    abajo = Gdx.input.isKeyPressed(Input.Keys.S);
    izquierda = Gdx.input.isKeyPressed(Input.Keys.A);
    derecha = Gdx.input.isKeyPressed(Input.Keys.D);
    j = Gdx.input.isKeyPressed(Input.Keys.J);
    k = Gdx.input.isKeyPressed(Input.Keys.K);
    if(Global.empieza==true)
    {
   juego.hc.enviarMensaje(Global.Jugador + " " + "Arr" + " " + Boolean.toString(arriba));
   juego.hc.enviarMensaje(Global.Jugador + " " + "Abj" + " " + Boolean.toString(abajo));
   juego.hc.enviarMensaje(Global.Jugador + " " + "Izq" + " " + Boolean.toString(izquierda));
   juego.hc.enviarMensaje(Global.Jugador + " " + "Der" + " " + Boolean.toString(derecha));
   juego.hc.enviarMensaje(Global.Jugador + " " + "J" + " " + Boolean.toString(j));
   juego.hc.enviarMensaje(Global.Jugador + " " + "K" + " " + Boolean.toString(k));
    }
    }
}

