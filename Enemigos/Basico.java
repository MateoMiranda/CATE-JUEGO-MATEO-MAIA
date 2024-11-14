package Enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.shoot.Global;

public class Basico {
	public int id;
	private Texture spriteSheet;
	private Sprite basicoSprite;
	private float x, y;
	private float width = 40, height = 40;
	private float velocidad = 3;
	private int salud;
	public Rectangle hitbox;
	private MovTipo movimiento;
	private boolean activado = false;
	private boolean direccion = false;

	public Basico(float x, float y, int id, MovTipo movimiento, String textura, int nsalud) {
		this.id = id;
		this.x = x;
		this.y = y;
		salud = nsalud;
		spriteSheet = new Texture(textura);
		basicoSprite = new Sprite(spriteSheet);
		basicoSprite.setSize(width, height);
		basicoSprite.setPosition(x, y);
		hitbox = new Rectangle(x - x / 2, y - y / 2, width - 10, height - 10);
		this.movimiento = movimiento;
	}

	public void update(float jugadorY, float jugadorX) {
		moverse(jugadorY, jugadorX);
		basicoSprite.setPosition(x, y);
		hitbox.setPosition(x, y);
	}

	public void dibujar(SpriteBatch batch) {
		basicoSprite.draw(batch);
	}

	public void recibirDanio() {
		if (salud > 0) {
			salud--;
		}
	}

	public int getSalud() {
		return salud;
	}

	public void morir() {
		salud = 0;
		Global.puntuacion += 50;
	}

	public void moverse(float jugadorY, float jugadorX) {
		switch (movimiento) {
		case RECTO:
			y -= velocidad;
			break;
		case DERECHO:
			x += velocidad;
			break;
		case IZQUIERDO:
			x -= velocidad;
			break;
		case INTERCEPTOR:

			if (jugadorY <= y && !activado) {
				y -= velocidad;
			}

			else {

				if (jugadorX < x && activado == false) {
					direccion = true;
					activado = true;
				}

				if (jugadorX > x && activado == false) {
					direccion = false;
					activado = true;
				}

				if (activado) {

					if (direccion) {
						x -= velocidad;
					}

					else {
						x += velocidad;
					}
				}
			}
			System.out.println(activado);
		}

	}

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
}