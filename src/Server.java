import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(1337);
		String svar = "";
		int klientValg;

		while (true) {
			boolean klientRunning = true;
			Socket connectionSocket = welcomeSocket.accept();
			String hostName = connectionSocket.getInetAddress().getHostName();
			System.out.println("Connection received from: " + hostName);

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());

			while (klientRunning) {
				klientValg = inFromClient.read();
				Date date = new Date();
				switch (klientValg) {
				case 1: // FULL
					System.out.println("Motatt melding fra " + hostName + ": FULL");
					SimpleDateFormat dfFull = new SimpleDateFormat("MMMMM yyyy HH:mm:ss");
					svar = dfFull.format(date);
					outToClient.writeBytes(svar + '\n');

					System.out.println("Sendt FULL: " + svar + " til " + hostName); 
					break;
				case 2:// DATE
					System.out.println("Motatt melding fra " + hostName + ": DATE");
					SimpleDateFormat dfDate = new SimpleDateFormat("MMMMM yyyy");
					svar = dfDate.format(date);
					outToClient.writeBytes(svar + '\n');
					
					System.out.println("Sendt DATE: " + svar + " til " + hostName);
					break;
				case 3:// TIME
					System.out.println("Motatt melding " + hostName + ": TIME");
					SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
					svar = dfTime.format(date);
					outToClient.writeBytes(svar + '\n');
					
					System.out.println("Sendt TIME: " + svar + " til " + hostName);
					break;
				case 4:
					System.out.println("Motatt melding fra " + hostName + ": CLOSE");
					klientRunning = false;
					break;
				default:
					System.out.println("Motatt melding: " + klientValg);
					svar = "Ugyldig valg: " + klientValg;
					outToClient.writeBytes(svar + '\n');// invalid handling
					
					System.out.println("Sendt feilmelding: " + svar + " til " + hostName);
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