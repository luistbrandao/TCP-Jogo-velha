package jogoVelha;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import tcpserver.TCPServer;
import java.util.Scanner;

public class playerTwo {
	Scanner input = new Scanner(System.in);
	int choose;
	boolean available;
	DataOutputStream out;
	DataInputStream in;
	Socket client;
	public String name;
	
	public playerTwo(Socket c) throws IOException{
		this.client = c;
		try {
            this.in = new DataInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.out = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.writeUTF("Digite o nome do player:");
        out.flush();
		System.out.println("Requesting player name ...");
		//this.name = input.nextLine();
        this.name = in.readUTF();
        System.out.println("Player name: "+this.name+"\n");
	}
	
	public void play(buildJGVelha board) throws IOException, InterruptedException{
		do {
			System.out.println(this.name + " ecolha sua posição: ");
			this.choose = input.nextInt();
			System.out.println("Choosen position: "+Integer.toString(this.choose) + " by " + this.name + "\n");
			this.available = board.checkPosition(this.choose); //if return = true, available position
			if(this.available == true) {
				board.atualizate(2, this.choose);
			}
		}while(this.available == false);
		
	}
	
}
