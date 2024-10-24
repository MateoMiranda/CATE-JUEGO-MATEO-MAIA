package Personajes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import Efectos.Bala;
import Jugador.Jugador;

public class Nave {
    // Sonido que se reproduce al disparar
    Sound disparoSound;
    
    // Hitbox para detectar colisiones
    public Rectangle hitbox;
    
    // Textura que contiene el spriteSheet de la nave
    private Texture spriteSheet;
    
    // Animaciones para diferentes movimientos de la nave
    private Animation<TextureRegion> defaultAnimac;
    private Animation<TextureRegion> arriAnimac;
    private Animation<TextureRegion> arrDerAnimac;
    private Animation<TextureRegion> derAnimac;
    private Animation<TextureRegion> abajoArrAnimac;
    private Animation<TextureRegion> abajoAnimac;
    private Animation<TextureRegion> abajoIzqAnimac;
    private Animation<TextureRegion> izqAnimac;
    private Animation<TextureRegion> arrAnimac;
    private Animation<TextureRegion> animacAct;
    
    // Sprite de la nave
    private Sprite naveSprite;
    
    // Tiempo transcurrido para controlar las animaciones
    private float elapsedTime;
    
    // Posición de la nave en el eje X e Y
    public float x, y;
    
    // tamaño de la nave
    private float width = 40, height = 40;
    
    // Velocidad de movimiento de la nave
    private float velocidad = 2.5f;
    
    // Tiempo de cooldown entre disparos en segundos
    private float tiempoCooldown = 0.3f;
    
    // Tiempo del último disparo realizado
    float tiempoUltimoDisparo = 0;

    
    public Nave(Texture spriteSheet, float x, float y) {
        // Carga el sonido de disparo desde los archivos internos
        disparoSound = Gdx.audio.newSound(Gdx.files.internal("sonidos/explosion.mp3"));
    
        this.x = x;
        this.y = y;
        this.spriteSheet = spriteSheet;
        
        // Divide el SpriteSheet en partes de 64x64 píxeles
        TextureRegion[][] frames = TextureRegion.split(spriteSheet, 64, 64);
        
        // Crea animaciones para diferentes direcciones de movimiento
        defaultAnimac = new Animation<>(0.25f, frames[0][0]);
        arriAnimac = new Animation<>(0.25f, frames[1][0], frames[0][0]);
        arrDerAnimac = new Animation<>(0.25f, frames[2][0], frames[0][0]);
        derAnimac = new Animation<>(0.25f, frames[3][0], frames[0][0]);
        abajoArrAnimac = new Animation<>(0.25f, frames[4][0], frames[0][0]);
        abajoAnimac = new Animation<>(0.25f, frames[5][0], frames[0][0]);
        abajoIzqAnimac = new Animation<>(0.25f, frames[6][0], frames[0][0]);
        izqAnimac = new Animation<>(0.25f, frames[7][0], frames[0][0]);
        arrAnimac = new Animation<>(0.25f, frames[8][0], frames[0][0]);
        
        // Inicializa la animación actual como la animación por defecto
        animacAct = defaultAnimac;
        
        // Crea el sprite de la nave usando el primer frame del SpriteSheet
        naveSprite = new Sprite(frames[0][0]);
        
        // Tamaño del sprite
        naveSprite.setSize(width, height);
        
        // Posición inicial del sprite
        naveSprite.setPosition(x, y);
        
        // Inicializa la hitbox de la nave (mitad del tamaño del sprite)
        hitbox = new Rectangle(x, y, width / 2, height / 2);
    }

    public void regEntradaMov(Jugador Jugador, ArrayList<Bala> balas, float[] limites) {
        // Determina la animación actual basada en las teclas de movimiento presionadas
        if (Jugador.arriba && Jugador.derecha) {
            animacAct = arrDerAnimac; // Arriba y derecha
            
        } else if (Jugador.arriba && Jugador.izquierda) {
            animacAct = arrAnimac; // Arriba y izquierda
            
        } else if (Jugador.abajo && Jugador.derecha) {
            animacAct = abajoArrAnimac; // Abajo y derecha
            
        } else if (Jugador.abajo && Jugador.izquierda) {
            animacAct = abajoIzqAnimac; // Abajo y izquierda
            
        } else if (Jugador.arriba) {
            animacAct = arriAnimac; // Solo arriba
            
        } else if (Jugador.abajo) {
            animacAct = abajoAnimac; // Solo abajo
            
        } else if (Jugador.izquierda) {
            animacAct = izqAnimac; // Solo izquierda
            
        } else if (Jugador.derecha) {
            animacAct = derAnimac; // Solo derecha
            
        } else {
            animacAct = defaultAnimac; // Animación por defecto si no hay movimiento
        }

        // Actualiza la posición Y de la nave si se presiona arriba y no supera el límite superior
        if (Jugador.arriba && y <= limites[0]) {
            y += velocidad;
        }
        // Actualiza la posición Y de la nave si se presiona abajo y no supera el límite inferior
        else if (Jugador.abajo && y >= limites[1]) {
            y -= velocidad;
        }

        // Actualiza la posición X de la nave si se presiona izquierda y no supera el límite izquierdo
        if (Jugador.izquierda && x >= limites[2]) {
            x -= velocidad;
        }
        // Actualiza la posición X de la nave si se presiona derecha y no supera el límite derecho
        else if (Jugador.derecha && x <= limites[3]) {
            x += velocidad;
        }

        // Si se presiona la tecla de disparo (asumido como 'j'), dispara una bala
        if (Jugador.j) {
            disparar(balas);
        }

        // Actualiza la posición del sprite y la hitbox
        Mover();
    }

     // Actualiza la animación de la nave basada en el tiempo transcurrido
    public void update(float deltaTime) {
        // Incrementa el tiempo transcurrido
        elapsedTime += deltaTime;
        
        // Actualiza la región del sprite según la animación actual
        naveSprite.setRegion(animacAct.getKeyFrame(elapsedTime, true));
    }

     // Dibuja el sprite de la nave en el batch proporcionado
    public void dibujar(SpriteBatch batch) {
        naveSprite.draw(batch);
    }

     // Actualiza la posición del sprite y la hitbox de la nave
    public void Mover() {
        naveSprite.setPosition(x, y);
        hitbox.setPosition(x, y);
    }

     //Genera una bala y la añade a la lista de balas disparadas
    public void generarBala(ArrayList<Bala> balas) {
        System.out.println("Bala");
        
        // Reproduce el sonido de disparo
        long disparoId = disparoSound.play(1.0f);
        
        // Configura el sonido para que no se repita
        disparoSound.setLooping(disparoId, false);
        
        // Establece el volumen del sonido
        disparoSound.setVolume(disparoId, 1.0f);
        
        // Crea una nueva bala en la posición actual de la nave con un desplazamiento
        Bala bala = new Bala(x + 14, y + 30);
        
        // Añade la bala a la lista de balas disparadas
        balas.add(bala);
    }

    
     // cooldown de la balas
    public void disparar(ArrayList<Bala> balas) { 
        float tiempoActual = TimeUtils.nanoTime() / 1000000000.0f; // Obtiene el tiempo actual en segundos
        
        // Verifica si ha pasado suficiente tiempo desde el último disparo
        if (tiempoActual - tiempoUltimoDisparo > tiempoCooldown) {
            generarBala(balas); // Genera una nueva bala
            
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
