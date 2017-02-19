import java.io.*;
import java.net.*;

public class T {
	public static void main(String[] args) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(1337);
		int V = 0;
		String svar = "";
		int nesteLedig = 0;
		String[] logg = new String[25];
		String klientSvar;
		int klientValg;
		int endring;
		int subAdd;

		while (true) {
			boolean klientRunning = true;
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Connection received from: "
					+ connectionSocket.getInetAddress().getHostName());

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			ObjectOutputStream oos = new ObjectOutputStream(
					connectionSocket.getOutputStream());

			while (klientRunning) {
				klientValg = inFromClient.read();
				switch (klientValg) {
				case 1: // endre V
					int gammelVerdi = V;
					svar = "Endre med kor mykje?";
					outToClient.writeBytes(svar + '\n');

					endring = inFromClient.read();

					svar = "1. for å øke med " + endring
							+ " 2. for å trekke fra " + endring;
					outToClient.writeBytes(svar + '\n');

					subAdd = inFromClient.read();
					switch (subAdd) {
					case 1:
						V = V + endring;

						svar = "V økt med " + endring;
						outToClient.writeBytes(svar + '\n');
						break;
					case 2:
						V = V - endring;

						svar = "V minka med " + endring;
						outToClient.writeBytes(svar + '\n');
						break;

					default:
						svar = "Ugyldig endring";
						outToClient.writeBytes(svar + '\n');
						break;
					}
					System.out.println("V endra");

					// legge endring i logg
					logg[nesteLedig] = "Fra " + gammelVerdi + " til " + V + " ip: " + connectionSocket.getInetAddress(); 

					nesteLedig++;
					break;
				case 2:// returner V
					System.out.println("Returnerer verdi på V");
					svar = "V = " + String.valueOf(V);
					outToClient.writeBytes(svar + '\n');

					break;
				case 3:// returner logg om V
					oos.writeObject(logg);
					System.out.println("sender logg");

					break;
				case 4:
					klientRunning = false;
					break;
				default:
					System.out.println("ugyldig valg " + klientValg);
					svar = "Ugyldig valg " + klientValg;
					outToClient.writeBytes(svar + '\n');// invalid handling
					break;

				}
			}
			connectionSocket.close();
			oos.close();
			System.out.println("Connection closed");
			System.out.println("");
		}

	}
}