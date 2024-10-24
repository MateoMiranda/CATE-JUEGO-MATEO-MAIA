package Jugador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import Personajes.Nave;

public class Jugador {
    public boolean invencible = false;  // invencibilidad del jugador
    public String nombre;  // Nombre del jugador
    public Nave nave;  // Objeto Nave asociado al jugador
    public boolean arriba = false;  // Movimiento hacia arriba
    public boolean abajo = false;  // abajo
    public boolean izquierda = false;  // izquierda
    public boolean derecha = false;  // derecha
	public boolean j = false;  // disparo
    public boolean k = false;  // salir
    public int vida = 1;  // Vida del jugador

    public Jugador() {

	}

    // Método para actualizar el estado de las teclas
    public void actualizarEntrada() {
        arriba = Gdx.input.isKeyPressed(Input.Keys.W);  // Actualiza el estado de la tecla W (arriba)
        abajo = Gdx.input.isKeyPressed(Input.Keys.S);  // Actualiza el estado de la tecla S (abajo)
        izquierda = Gdx.input.isKeyPressed(Input.Keys.A);  // Actualiza el estado de la tecla A (izquierda)
        derecha = Gdx.input.isKeyPressed(Input.Keys.D);  // Actualiza el estado de la tecla D (derecha)
        j = Gdx.input.isKeyPressed(Input.Keys.J);  // Actualiza el estado de la tecla J (disparo)
        k = Gdx.input.isKeyPressed(Input.Keys.K);  // Actualiza el estado de la tecla K (salir)
    }
}
