package Red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import com.mygdx.shoot.Global;
import com.mygdx.shoot.MyGdxGame;
import Enemigos.Basico;

public class HiloCliente extends Thread {
	private DatagramSocket conexion; //comunicarse con el sever
	private InetAddress ipServer;
	private int puerto = 9561; // posible error
	boolean fin = false;
	private MyGdxGame juego;

	public HiloCliente(MyGdxGame juego) {
		this.juego = juego;
		try {
			ipServer = InetAddress.getByName("255.255.255.255");
			conexion = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensaje(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
		try {
			conexion.send(dp);
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
		} while (!fin);
	}

	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		if (msg.equals("OK")) {
			ipServer = dp.getAddress(); //le dice al cliente la ip del server

		} else if (msg.equals("Empieza")) {
			Global.empieza = true;
		} else

		if (Global.empieza == false)
			switch (Integer.parseInt(msg)) {
			case 1:
				Global.Jugador = 1;
				break;
			case 2:
				Global.Jugador = 2;
			}

		else if (Global.empieza) {
			System.out.println(juego.pantallaActual.tiempo);
			ActualizarMov(1, msg);
		}

		if (msg.contains("idE")) {
			boolean encontrado = false;
			int idBuscado = sacarNumeros(msg);
			Iterator<Basico> iterator = juego.pantallaActual.listaEnemigos.iterator();

			while (iterator.hasNext() && !encontrado) {
				Basico enemigo = iterator.next();
				if (enemigo.id == idBuscado) {
					enemigo.morir();
					encontrado = true;
					Global.enemigosMuertos++;
					iterator.remove(); // Elimina el enemigo de la lista
				}
			}
		}

		if (msg.contains("Golpe")) {

			if (Global.Jugador == sacarNumeros(msg)) {
				juego.jugadores[0].vida -= 1;
			} else {
				juego.jugadores[1].vida -= 1;
			}
		}
	}

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

	private Boolean ConvertirBoolean(String msg) {
		boolean valor = false;
		if (msg.endsWith("true")) {
			valor = Boolean.parseBoolean(msg.substring(msg.lastIndexOf(" ") + 1)); 
																				
		} else if (msg.endsWith("false")) {
			valor = Boolean.parseBoolean(msg.substring(msg.lastIndexOf(" ") + 1)); 
																				
		}
		return valor;
	}

	private int sacarNumeros(String msg) {
		String numeros = msg.replaceAll("\\D+", ""); // Elimina todo lo que no sea dígito
		int numero = 0;
		if (!numeros.isEmpty()) {
			numero = Integer.parseInt(numeros); // Convierte el String resultante a int
		}
		return numero;
	}

}