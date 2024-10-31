package com.mygdx.game.servershoot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Jugador.Jugador;
import Pantallas.PantallaBase;
import Pantallas.PantallaInicial;
import Personajes.Nave;
import Red.HiloServidor;


public class MyGdxGame extends Game {
    private boolean arrastre; // Indica si se está arrastrando en pantalla
    private boolean reenderizador; // Controla el renderizado continuo
    private int cantJugadores = 2; 
    public Jugador[] jugadores = new Jugador[cantJugadores];
    public PantallaBase pantallaActual; // Pantalla actual en el juego
    public Camara camara;
    public Viewport viewport; // Viewport para ajustar la escala de pantalla
    SpriteBatch batch; // Lote de sprites para renderizado
    Input input = Gdx.input; // Entrada del usuario en pantalla

    public float[] limites = new float[4]; // Array para límites de la cámara

    public HiloServidor hs; // Hilo para el servidor

    @Override
    public void create() {
        reenderizador = false; // Renderizado continuo apagado inicialmente
        Gdx.graphics.setContinuousRendering(true); // Activa renderizado continuo
        hs = new HiloServidor(this);
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador(); // Inicializa los jugadores
        }

        pantallaActual = new PantallaInicial(this); // Pantalla inicial del juego
        setScreen(pantallaActual); // Establece la pantalla inicial
        Gdx.graphics.setVSync(true); // Sincronización vertical activada
        Gdx.input.setInputProcessor(new InputAdapter()); // Configura el procesador de entrada
        camara = new Camara(); // Crea la cámara
        viewport = new FitViewport(camara.getAncho(), camara.getAltura(), camara.getCamara()); // Configura el viewport
        viewport.apply(); // Aplica el viewport
        batch = new SpriteBatch(); // Crea un SpriteBatch para renderizar
        limites[0] = camara.getAltura() - 25; // Establece límite superior
        limites[1] = -20; // Establece límite inferior
        limites[2] = -15; // Establece límite izquierdo
        limites[3] = camara.getAncho() - 25; // Establece límite derecho

        hs.start(); // Inicia el Hilo del Servidor
    }

    @Override
    public void render() {
        if (reenderizador || Gdx.graphics.isContinuousRendering()) {
            Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // Color de fondo gris oscuro
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpia la pantalla
            camara.update(Gdx.graphics.getDeltaTime()); // Actualiza la cámara
            camara.getCamara().position.y = viewport.getWorldHeight() / 2; // Centra la cámara en el eje Y
            batch.setProjectionMatrix(camara.getCamara().combined); // Configura la matriz de proyección para el batch
            pantallaActual.render(Gdx.graphics.getDeltaTime()); // Renderiza la pantalla actual

          // Detecta si el usuario esta haciendo click
            if (Gdx.input.isTouched()) {
                arrastre = true; // Activa la bandera de arrastre
                reenderizador = true; // Activa el renderizado continuo
            } else {
                arrastre = false; // Desactiva la bandera de arrastre si no se toca
            }
            reenderizador = false; // Desactiva el renderizado continuo
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Actualiza el viewport al cambiar el tamaño de la ventana
        Gdx.gl.glClearColor(0.05f, 0.3f, 0.2f, 1); // Color de fondo 
    }

    @Override
    public void dispose() {
        batch.dispose(); // Libera los recursos del batch al terminar
    }
}
