import java.net.*;
import java.io.*;

public class K {

	public static void main(String[] args) throws Exception {
		String valg;
		String serverSvar;
		int tabellStorelse;
		boolean running = true;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));

		Socket clientSocket = new Socket("127.0.0.1", 1337);

		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		ObjectInputStream ois = new ObjectInputStream(
				clientSocket.getInputStream());
		while (running) {
			System.out.println("");
			System.out
					.println("Velg ein handling: 1. endre verdi 2. spørr om verdi 3. endringslogg 4. avbryt ");
			valg = inFromUser.readLine();
			int valgInt = Integer.parseInt(valg);
			outToServer.write(valgInt);

			switch (valgInt) {
			case 1: // endre verdi V
				serverSvar = inFromServer.readLine();
				System.out.println(serverSvar); //

				valg = inFromUser.readLine(); // endring
				int valgInt2 = Integer.parseInt(valg);
				outToServer.write(valgInt2);

				serverSvar = inFromServer.readLine();
				System.out.println(serverSvar);

				valg = inFromUser.readLine(); // - eller +
				valgInt2 = Integer.parseInt(valg);
				outToServer.write(valgInt2);

				serverSvar = inFromServer.readLine();
				System.out.println(serverSvar);
				break;
			case 2: // få verdi V
				serverSvar = inFromServer.readLine();

				System.out.println(serverSvar);
				break;

			case 3: // liste endringslogg for V
				String[] array = (String[]) ois.readObject();

				for (int i = 0; i < array.length; i++) {
					System.out.println(array[i]);
				}
				break;

			case 4:
				running = false;
				break;
			default:
				serverSvar = inFromServer.readLine();

				System.out.println(serverSvar);
				break;
			}
		}
		clientSocket.close();
		System.out.println("Connection closed");
	}
}