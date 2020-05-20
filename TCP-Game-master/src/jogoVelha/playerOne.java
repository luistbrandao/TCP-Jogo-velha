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

public class playerOne {
	Scanner input = new Scanner(System.in);
	int choose;
	boolean available;
	
	public String name;
	
	public playerOne() {
		this.name = "Server";
	}
	
	public void play(buildJGVelha board) {
		do {
			System.out.println(this.name + " escolha sua opção: ");
			this.choose = input.nextInt();
			System.out.print("\n\n");
			this.available = board.checkPosition(this.choose); //if return = true, available position
			if(this.available == true) {
				board.atualizate(1, this.choose);
			}
		}while(this.available == false);
	}
}
