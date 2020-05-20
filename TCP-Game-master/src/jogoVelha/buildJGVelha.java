package jogoVelha;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import tcpserver.TCPServer;

public class buildJGVelha {
	String[][] tabuleiro = new String[5][5];
	int row, col;
	boolean[][] occupy = new boolean[5][5];
	private int i = 1;
	public boolean winRow, winCol, winDiagPrinc, winDiagSec;
	DataOutputStream out;
	DataInputStream in;
	Socket client;
	
	public buildJGVelha(Socket c) throws IOException {
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
		for(row = 0; row <= 4; row++) {
			for(col = 0; col <=4; col++) {
				if((row == 1 || row == 3) && (col == 0 || col == 2 || col == 4)) {
					tabuleiro[row][col] = "-";
					out.writeUTF("-");
					out.flush();
					System.out.print("-");
				}
				else if((row == 0 || row == 2 || row == 4) && (col == 1 || col == 3)){
					tabuleiro[row][col] = "|";
					out.writeUTF("|");
					out.flush();
					System.out.print("|");
				}
				else if ((row == 1 || row == 3) && (col == 1 || col == 3)) {
					tabuleiro[row][col] = "+";
					out.writeUTF("+");
					out.flush();
					System.out.print("+");
				}
				else {
					//tabuleiro[row][col] = Character.forDigit(this.i, 10);
					tabuleiro[row][col] = Integer.toString(this.i);
					out.writeUTF(tabuleiro[row][col]);
					out.flush();
					System.out.print(this.i);
					this.i++;
					this.occupy[row][col] = false;
				}
			}
			out.writeUTF("\n");
			out.flush();
			System.out.print("\n");
		}
	}
	
	public boolean checkPosition(int position){
		if(position == 1 && this.tabuleiro[0][0].equals("1") && this.occupy[0][0] == false){
			return true;
		}
		else if(position == 2 && this.tabuleiro[0][2].equals("2") && this.occupy[0][2] == false) {
			return true;
		}
		else if(position == 3 && this.tabuleiro[0][4].equals("3") && this.occupy[0][4] == false) {
			return true;
		}
		else if(position == 4 && this.tabuleiro[2][0].equals("4") && this.occupy[2][0] == false) {
			return true;
		}
		else if(position == 5 && this.tabuleiro[2][2].equals("5") && this.occupy[2][2] == false) {
			return true;
		}
		else if(position == 6 && this.tabuleiro[2][4].equals("6") && this.occupy[2][4] == false) {
			return true;
		}
		else if(position == 7 && this.tabuleiro[4][0].equals("7") && this.occupy[4][0] == false) {
			return true;
		}
		else if(position == 8 && this.tabuleiro[4][2].equals("8") && this.occupy[4][2] == false) {
			return true;
		}
		else if(position == 9 && this.tabuleiro[4][4].equals("9") && this.occupy[4][4] == false) {
			return true;
		}
		return false;
	}
	
	public void atualizate(int player, int position) {
		String simbol;
		if(player == 1) {
			simbol = "x";
		}
		else {
			simbol = "o";
		}
		atualizateBoard(position, simbol);
	}
	
	public void atualizateBoard(int position, String simbol) {
		if(position == 1) {
			tabuleiro[0][0] = simbol;
			occupy[0][0] = true;
		}
		else if(position == 2) {
			tabuleiro[0][2] = simbol;
			occupy[0][2] = true;
		}
		else if(position == 3) {
			tabuleiro[0][4] = simbol;
			occupy[0][4] = true;
		}
		else if(position == 4) {
			tabuleiro[2][0] = simbol;
			occupy[2][0] = true;
		}
		else if(position == 5) {
			tabuleiro[2][2] = simbol;
			occupy[2][2] = true;
		}
		else if(position == 6) {
			tabuleiro[2][4] = simbol;
			occupy[2][4] = true;
		}
		else if(position == 7) {
			tabuleiro[4][0] = simbol;
			occupy[4][0] = true;
		}
		else if(position == 8) {
			tabuleiro[4][2] = simbol;
			occupy[4][2] = true;
		}
		else{
			tabuleiro[4][4] = simbol;
			occupy[4][4] = true;
		}
	} 

	public String endGame() {
		int row, col;
		
		for(row = 0; row <= 4; row = row + 2) {
			for(col = 0; col <= 4; col = col + 2) {
				if(occupy[row][col] == true) {
					this.winRow = checkRow(tabuleiro[row][col], row);
					this.winCol = checkCol(tabuleiro[row][col], col);
					this.winDiagPrinc = checkDiagPrinc(tabuleiro[row][col]);
					this.winDiagSec = checkDiagSec(tabuleiro[row][col]);
					if(this.winRow == true || this.winCol == true || this.winDiagPrinc == true || this.winDiagSec == true) {
						return tabuleiro[row][col];
					}
				}
			}
		}
		return " ";
	}
	
	public boolean checkRow(String simbol, int row) {
		int col;
		
		for(col = 0; col <= 4; col = col + 2) {
			if(!(tabuleiro[row][col].equals(simbol))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkCol(String simbol, int col) {
		int row;
		
		for(row = 0; row <= 4; row = row + 2) {
			if(!(tabuleiro[row][col].equals(simbol))) {
				return false;
			}
		}
		return true;
	}

	public boolean checkDiagPrinc(String simbol) {
		int row, col;

		for(row = 0; row <= 4; row = row + 2) {
			for(col = 0; col <= 4; col = col + 2) {
				if(row == col && !(tabuleiro[row][col].equals(simbol))){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean checkDiagSec(String simbol) {
		int row, col;
		
		for(row = 0; row <= 4; row = row + 2) {
			for(col = 0; col <= 4; col = col + 2) {
				if(row + col == 4 && !(tabuleiro[row][col].equals(simbol))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void printCurrentBoard(){
		int row, col;
		for(row = 0; row <= 4; row++) {
			for(col = 0; col <= 4; col++) {
				System.out.print(tabuleiro[row][col]);
			}
			System.out.print("\n");
		}
	}
	
	public void printCurrentBoard2() throws IOException {
		int row, col;
		for(row = 0; row <= 4; row++) {
			for(col = 0; col <= 4; col++) {
				out.writeBytes(tabuleiro[row][col]);
				out.flush();
				System.out.print(tabuleiro[row][col]);
			}
			out.writeBytes("\n");
			out.flush();
			System.out.print("\n");
		}
	}
	
}
