/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.Scanner;

public class TCPClient {

    /**
     * @param args the command line arguments
     * @throws InterruptedException 
     */
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, IOException {
    	Scanner input = new Scanner(System.in);
    	//InputStream inp = null;
		//BufferedReader d = new BufferedReader(new InputStreamReader(inp));
        DataInputStream in = null;
        DataOutputStream out;
        Socket s = null;
        String nomePlayer;
        String perg;
        String x;
        //boolean endGame = true;
        int choose;
        //String boardPart;
        try {
        	int i = 0;
            int port = 6868;
            String test = "";
            //InetAddress server = InetAddress.getLocalHost(); EM ALGUNS PC'S PROTEGIDOS TEM Q COLOCAR O NUMERO DO HOST MANUALMENTE ABAIXO
            s = new Socket("127.0.0.1", port);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
            
            out.writeUTF("Connection established!");
            String resposta = in.readUTF();
            System.out.println("Message received from server: " + resposta);
            //==================JG DA VELHA CODE START==============
            do {
            	System.out.print(in.readUTF()); //printar o tabuleiro
            	i++;
            }while (i <= 30); //O tabuleiro do xadrez eh uma matriz 5x5 + 3 '\n's
            i = 0;
            
            nomePlayer = input.nextLine();
            out.writeUTF(nomePlayer);
            out.flush();
            System.out.println("\nInitializing game Jogo da Velha ...");
            System.out.println(in.readUTF()); //jogador ... inicia o jogo
            
            if(in.read() == 1) {//VEZ DO SERVER
            	System.out.println("Waiting for Server ...");
            	try {
                    synchronized(s){
                        s.wait();
                    }
                } catch (InterruptedException e) {
                    // ...
                }
            }
            else{
            	System.out.println("Choose your option: "); //Escolha sua opção
            	choose = input.nextInt();
            	out.writeInt(choose);
            	System.out.println("\nWaiting server choose\n\n");
            }
            in.readUTF();
            in.readByte();
            do {
            	x = in.readLine();
            	System.out.println(x); //printar o tabuleiro
            	i++;
            }while (i <= 4); //O tabuleiro do xadrez eh uma matriz 5x5 + 3 '\n's
            i = 0;
            
            
            //==================JG DA VELHA CODE END================
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {if (s != null )
                try {
                    s.close();
                } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }
    
}