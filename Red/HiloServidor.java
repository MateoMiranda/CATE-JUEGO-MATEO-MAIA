package Red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import com.mygdx.game.servershoot.Global;
import com.mygdx.game.servershoot.MyGdxGame;

public class HiloServidor extends Thread {
	private DatagramSocket conexion;  // Socket para comunicarse con los clientes
	boolean fin = false;  
	private DireccionRed[] clientes = new DireccionRed[2]; 
	private int cantClientes = 0; 
	private MyGdxGame juego; 
	
	// recibe el juego e inicializa el socket
	public HiloServidor(MyGdxGame juego) {
		this.juego = juego;  
		try {
			conexion = new DatagramSocket(9561);
		} catch (SocketException e) {
			e.printStackTrace();  // Muestra errores de conexión
		}
	}
	
	// Envía un mensaje a un cliente en una IP y puerto específicos
	public void enviarMensaje(String msg, InetAddress ip, int puerto) {
		System.out.println(msg);
		byte[] data = msg.getBytes();  // Convierte el mensaje en bytes
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);  // Crea el paquete de datos
		try {
			conexion.send(dp);  // Envía el paquete
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	@Override
	public void run() {
		do {
			byte[] data = new byte[1024]; // tamaño del buffer para recibir datos
			DatagramPacket dp = new DatagramPacket(data, data.length);  
			try {
				conexion.receive(dp);  
			} catch (IOException e) {
				e.printStackTrace();  
			}
			procesarMensaje(dp);  
		} while (fin == false);  
	}
	
	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();  // Convierte datos a String y elimina espacios
		if (msg.equals("Conexion")) { 
			System.out.println("LLego Conexion");
			if (cantClientes < 2) {  
				System.out.println("LLego cliente");
				clientes[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort());  // Guarda la dirección del cliente
				enviarMensaje("OK", clientes[cantClientes].getIp(), clientes[cantClientes].getPuerto());  // Confirma conexión
				cantClientes++;  
			}
			if (cantClientes == 2) {  
				System.out.println("2 clientes");
				enviarMensaje("1", clientes[0].getIp(), clientes[0].getPuerto());  // Informa al primer cliente que es el cliente 1
				enviarMensaje("2", clientes[1].getIp(), clientes[1].getPuerto());  // Informa al segundo cliente que es el cliente 2
				envBroadcast("Empieza");  
				Global.empieza = true; 
			}
		}
		
		//para diferenciar a los clientes
		if (cantClientes == 2) {  
			if (msg.startsWith("1")) {  
				enviarMensaje(msg, clientes[1].getIp(), clientes[1].getPuerto()); 
				ActualizarMov(0, msg); 
			} else if (msg.startsWith("2")) {  
				enviarMensaje(msg, clientes[0].getIp(), clientes[0].getPuerto());
				ActualizarMov(1, msg); 
			}
		}
	}
	
	// Envía un mensaje a ambos clientes
	public void envBroadcast(String msg) {
		enviarMensaje(msg, clientes[0].getIp(), clientes[0].getPuerto());  
		enviarMensaje(msg, clientes[1].getIp(), clientes[1].getPuerto());
	}
	
	// Actualiza el movimiento de un jugador basado en el mensaje recibido
	private void ActualizarMov(int jugador, String msg) {
		if (msg.contains("Arr")) { 
			juego.jugadores[jugador].arriba = ConvertirBoolean(msg); 
		}
		if (msg.contains("Abj")) {  
			juego.jugadores[jugador].abajo = ConvertirBoolean(msg);  
		}
		if (msg.contains("Der")) { 
			juego.jugadores[jugador].derecha = ConvertirBoolean(msg); 
		}
		if (msg.contains("Izq")) { 
			juego.jugadores[jugador].izquierda = ConvertirBoolean(msg); 
		}
		if (msg.contains("J")) { 
			juego.jugadores[jugador].j = ConvertirBoolean(msg); 
		}
		if (msg.contains("K")) { 
			juego.jugadores[jugador].k = ConvertirBoolean(msg); 
		}
	}
	
	// Convierte la parte final del mensaje a un valor booleano
	private Boolean ConvertirBoolean(String msg) {
		boolean valor = false;
		if (msg.endsWith("true")) { 
			valor = Boolean.parseBoolean(msg.substring(msg.lastIndexOf(" ") + 1)); 
		} else if (msg.endsWith("false")) {  
			valor = Boolean.parseBoolean(msg.substring(msg.lastIndexOf(" ") + 1));
		}
		return valor;
	}
}
