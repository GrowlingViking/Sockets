import java.net.*;
import java.io.*;

public class K {

	public static void main(String[] args) throws Exception {
		String valg;
		String serverSvar;
		boolean running = true;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket("10.0.0.64", 1337);

		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		while (running) {
			System.out.println("");
			System.out.println("Velg ein melding å senda: 1. FULL  2. DATE  3. TIME  4. CLOSE ");
			valg = inFromUser.readLine();
			int valgInt = Integer.parseInt(valg);
			outToServer.write(valgInt);

			if (valgInt == 4) {
				running = false;
			} else {
				serverSvar = inFromServer.readLine();
				System.out.println(serverSvar);
			}
			
		}
		clientSocket.close();
		System.out.println("Connection closed");
	}
}