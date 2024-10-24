package Jugador;

public class AdminJugador {  // 
    private static AdminJugador instance; 
    private Jugador datosDeJugador;  

    // Constructor privado para evitar la instanciación externa.
    private AdminJugador() {
        datosDeJugador = new Jugador();  
    }

    // Método estático para obtener la instancia única de AdminJugador.
    public static AdminJugador getInstance() {
        if (instance == null) {  // Si no hay una instancia creada,
            instance = new AdminJugador();  // crea una nueva instancia
        }
        return instance;
    }

    // Método para obtener los datos del jugador.
    public Jugador getPlayerData() {
        return datosDeJugador; 
    }
}
