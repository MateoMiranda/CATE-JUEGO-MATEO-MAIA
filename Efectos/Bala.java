package Efectos; 

import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.Sprite;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch; 
import com.badlogic.gdx.math.Rectangle;  
import com.mygdx.game.servershoot.Global; 

public class Bala {
    public int id = 0;
    private Texture spriteSheet; 
    private Sprite basicoSprite; 
    public float x;  
    public float y; 
    private float width = 10, height = 10;  
    private float velocidad = 5; 
    public Rectangle hitbox; 
    public boolean colision = false; 

    
    public Bala(float x, float y, int id) {
        this.id = Global.BalasDisparadas + 1;  // Asigna un nuevo ID
        Global.BalasDisparadas += 1;
        this.x = x;  // Inicializa posición x
        this.y = y;  // Inicializa posición y
        spriteSheet = new Texture("Bullet/Bullet1.png");  // Carga la imagen de la bala
        basicoSprite = new Sprite(spriteSheet);  // Crea un sprite a partir de la imagen
        basicoSprite.setSize(width, height);  // tamaño del sprite
        basicoSprite.setPosition(x, y);  // posición del sprite
        hitbox = new Rectangle(x - x / 2, y - y / 2, width - 15, height - 20);  // Crea la hitbox
    }

    public Bala(float f, float g) {
		// TODO Auto-generated constructor stub
	}

    public void update() {
        basicoSprite.setPosition(x, y);  // Actualiza la posición del sprite
        hitbox.setPosition(x, y);  // Actualiza la posición de la hitbox
    }

    public void dibujar(SpriteBatch batch) {
        basicoSprite.draw(batch);  // Dibuja el sprite
        y += velocidad;  // Mueve la bala hacia abajo
    }

    public boolean haColisionadoConObjetivo(Rectangle objetivo) {
        if (hitbox.overlaps(objetivo)) {  // Comprueba si hay colisión
            colision = true;  // Marca que ha chocado
            return true;
        }
        colision = false;  // Marca que NO ha chocado
        return false;
    }
}
