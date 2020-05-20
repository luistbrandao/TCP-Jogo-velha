/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import jogoVelha.buildJGVelha;
import jogoVelha.playerOne;
import jogoVelha.playerTwo;
import jogoVelha.startGame;

/*import jogoVelha.buildXadrez;
import jogoVelha.playerOne;
import jogoVelha.playerTwo;
import jogoVelha.startGame;
*/
public class TCPServer {
    static Socket client = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket slisten = new ServerSocket(6868);
            while (true){
                System.out.println("Establishing connection...");
                client = slisten.accept();
                Connection conexao = new Connection(client);
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static class Connection extends Thread {
        DataInputStream in;
        DataOutputStream out;
        Socket client;
        public Connection(Socket client){
            this.client = client;
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
            this.start();
        }
        @Override
        public void run(){
            String mensagem;
            try {
                System.out.println("Waiting connection");
                mensagem = in.readUTF();
                System.out.println("Message received from client: " + mensagem);
                //System.out.println("yes");
                out.writeUTF("Let's play!!");
                out.flush();

                String winner = " ";
        		int count = 1;
        		
        		//buildJGVelha b = new buildJGVelha(client);
        		buildJGVelha b = new buildJGVelha(client);
        		
        		//playerOne p1 = new playerOne();
        		playerOne p1 = new playerOne();
        		
        		//playerTwo p2 = new playerTwo(client);
        		playerTwo p2 = new playerTwo(client);
        		//System.out.println("\n=== Bem vindo(a) "+p1.name+" e "+p2.name+" ===\n");
        		
        		//startGame sG = new startGame(playerOne, playerTwo);
        		startGame sG = new startGame(p1.name, p2.name);
        		out.writeUTF("Joagdor(a) "+sG.playerInitialize+" inicia o jogo!\n");
        		System.out.println("Jogador(a) "+sG.playerInitialize+" inicia o jogo!\n");
        		
        		if(sG.playerInitialize.equals("Server")) { //informa ao cliente qual usuário começa
        			out.writeInt(1);
        		}
        		else {
        			out.writeInt(2);
        		}
        		
        		//synchronized(client){
        			while(winner == " " && count <= 9) {
        				if(sG.playerInitialize.equals(p1.name)){
        					p1.play(b);
        					sG.playerInitialize = p2.name;
        					count++;
        					b.printCurrentBoard();
        				}
        				else {
        					p2.play(b);
        					sG.playerInitialize = p1.name;
        					count++;
        					b.printCurrentBoard();
        					//b.printCurrentBoard2();
        				}
        				winner = b.endGame();
        				//client.notify();
        			}
        		//}
        		if(winner == "x") {
        			System.out.println("=== "+p1.name+" venceu o jogo ===\n");
        		}
        		else if (winner == "o"){
        			System.out.println("=== "+p2.name+" venceu o jogo ===\n");
        		}
        		else{
        			System.out.println("=== Empate!! ===");
        		}
                //================JV CODE END=====================
                //out.writeUTF(mensagem);
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }
    
}