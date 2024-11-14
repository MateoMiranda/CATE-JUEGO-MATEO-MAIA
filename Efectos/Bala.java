package Efectos;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.shoot.Global;

public class Bala {
	public int id;
	private Texture spriteSheet;
	private Sprite basicoSprite;
	private float x;
	public float y;
	private float width = 10, height = 10;
	private float velocidad = 5;
	public Rectangle hitbox;
	public boolean colision = false;

	public Bala(float x, float y) {
		this.id = Global.BalasDisparadas + 1;
		Global.BalasDisparadas += 1;
		this.x = x;
		this.y = y;
		spriteSheet = new Texture("Bullet/Bullet1.png");
		basicoSprite = new Sprite(spriteSheet);
		basicoSprite.setSize(width, height);
		basicoSprite.setPosition(x, y);
		hitbox = new Rectangle(x - x / 2, y - y / 2, width - 15, height - 20);
	}

	public void update() {
		basicoSprite.setPosition(x, y);
		hitbox.setPosition(x, y);
	}

	public void dibujar(SpriteBatch batch) {
		basicoSprite.draw(batch);
		y += velocidad;
	}

	public boolean haColisionadoConObjetivo(Rectangle objetivo) {
		if (hitbox.overlaps(objetivo)) {
			System.out.println("golpe");
			// Ha colisionado con la otra hitbox
			colision = true;
			return true;
		}
		// La bala no ha colisionado con ningún enemigo
		colision = false;
		return false;
	}
}
