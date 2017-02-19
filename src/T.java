import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class T {
	public static void main(String[] args) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(1337);
		String svar = "";
		int klientValg;

		while (true) {
			boolean klientRunning = true;
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Connection received from: " + connectionSocket.getInetAddress().getHostName());

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());

			while (klientRunning) {
				klientValg = inFromClient.read();
				Date date = new Date();
				switch (klientValg) {
				case 1: // FULL
					SimpleDateFormat dfFull = new SimpleDateFormat("MMMMM yyyy HH:mm:ss");
					svar = dfFull.format(date);
					outToClient.writeBytes(svar + '\n');

					System.out.println("Sendt FULL dato"); 
					break;
				case 2:// DATE
					SimpleDateFormat dfDate = new SimpleDateFormat("MMMMM yyyy");
					svar = dfDate.format(date);
					outToClient.writeBytes(svar + '\n');
					
					System.out.println("Sendt DATE dato");
					break;
				case 3:// TIME
					SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
					svar = dfTime.format(date);
					outToClient.writeBytes(svar + '\n');
					
					System.out.println("Sendt TIME dato");
					break;
				case 4:
					klientRunning = false;
					break;
				default:
					System.out.println("ugyldig valg: " + klientValg);
					svar = "Ugyldig valg: " + klientValg;
					outToClient.writeBytes(svar + '\n');// invalid handling
					break;

				}
			}
			connectionSocket.close();
			oos.close();
			System.out.println("Connection closed");
			System.out.println("");
			welcomeSocket.close();
		}
		
	}
}