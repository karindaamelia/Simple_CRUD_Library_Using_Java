package pCRUD;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.time.Year;

public class Project_CRUD {
	
	public static void main (String[] args) throws IOException {
		
		Scanner userInput = new Scanner(System.in);
		String pilihanUser;
		boolean isLanjutkan = true;
		
		while(isLanjutkan) {
			System.out.println("\t===========================");
			System.out.println("\t|| DATABASE PERPUSTAKAAN ||");
			System.out.println("\t===========================\n");
			System.out.println("Menu :");
			System.out.println("1. List Buku");
			System.out.println("2. Cari Data Buku");
			System.out.println("3. Tambah Data Buku");
			System.out.println("4. Ubah Data Buku");
			System.out.println("5. Hapus Data Buku");
			
			System.out.print("\nMasukkan pilihan Anda : ");
			pilihanUser = userInput.next();
			
			switch(pilihanUser) {
			case "1":
				System.err.println("--->> List Buku\n");
				System.out.println("\n\t==========================");
				System.out.println("\t||       LIST BUKU      ||");
				System.out.println("\t==========================\n");
				tampilkanData();
				break;
			case "2":
				System.err.println("--->> Cari Data Buku\n");
				System.out.println("\n\t==========================");
				System.out.println("\t||    CARI DATA BUKU    ||");
				System.out.println("\t==========================\n");
				cariData();
				break;
			case "3":
				System.err.println("--->> Tambah Data Buku\n");
				System.out.println("\n\t==========================");
				System.out.println("\t||   TAMBAH DATA BUKU   ||");
				System.out.println("\t==========================\n");
				tambahData();
				System.out.println("\nDatabase : ");
				tampilkanData();
				break;
			case "4":
				System.err.println("--->> Ubah Data Buku\n");
				System.out.println("\n\t==========================");
				System.out.println("\t||    UBAH DATA BUKU    ||");
				System.out.println("\t==========================\n");
				break;
			case "5":
				System.err.println("--->> Hapus Data Buku\n");
				System.out.println("\n\t==========================");
				System.out.println("\t||    HAPUS DATA BUKU   ||");
				System.out.println("\t==========================\n");
				break;
			default :
				System.err.println("Pilihan Tidak Ditemukan\nSilahkan Masukkan Pilihan Kembali (1-5)\n");
		}
		
		isLanjutkan = getYesOrNo("\n\nApakah Anda ingin melanjutkan ");	
		
		if(isLanjutkan == true) {
			System.out.println("\n");
		} else {
			System.out.println("\n\n\t======= TERIMA KASIH ^_^ =======");
		}
		
		}
	}
	
	private static void tampilkanData() throws IOException{
		FileReader fileInput;
		BufferedReader bufferedInput;
		
		// Cek database ada atau tidak
		try {
			fileInput = new FileReader("database.txt");
			bufferedInput = new BufferedReader(fileInput);
		} catch(Exception e) {
			System.err.println("Database Tidak ditemukan");
			System.err.println("Silahkan Tambah Data Terlebih Dahulu");
			System.out.println("\nMasukkan data : ");
			tambahData();
			return;
		}
		
		System.out.println("=========================================================================================================");
		System.out.println("| No |	Tahun	|	Penulis		\t|	Penerbit	|	Judul Buku	\t|");
		System.out.println("=========================================================================================================");

		// Tokenizer dengan delimiter koma (,)
		String data = bufferedInput.readLine();
		int nomorData = 0;
		
		while(data != null) {
			nomorData++;
			StringTokenizer stringToken = new StringTokenizer(data, ",");
			
			stringToken.nextToken();
			System.out.printf("| %2d ", nomorData);
			System.out.printf("|	%s	",stringToken.nextToken());
			System.out.printf("|	%-20s	",stringToken.nextToken()); //-20 --> rata kanan
			System.out.printf("|	%-10s	",stringToken.nextToken());
			System.out.printf("|	%-20s	|",stringToken.nextToken());
			System.out.println();
			
			data = bufferedInput.readLine();
		}
		
		System.out.println("=========================================================================================================");
		System.err.println("\n--->>Akhir Dari Database");
	}
	
	private static void cariData() throws IOException {
		
		// Mengecek atau membaca database apakah ada atau tidak
		try {
			File file = new File("database.txt");
		} catch (Exception e){
			System.err.println("Database tidak ditemukan");
			System.err.println("Silahkan Tambah Data Terlebih Dahulu");
			System.out.println("\nMasukkan data : ");
			tambahData();
			return;
		}
		
		// Mengambil keyword dari user (mengambil satu baris sekaligus)
		Scanner userInput = new Scanner(System.in);
		
		String cariKata;
		
		System.out.print("Masukkan kata kunci untuk mencari buku : ");
		cariKata = userInput.nextLine();
		System.out.println(cariKata);
		
		// Mengambil semua baris
		String[] keyword = cariKata.split("\\s+");
		
		// Mencari atau mengecek keyword di database
		cekBukuDiDatabase(keyword, true);
	}
	
	private static boolean cekBukuDiDatabase(String[] keyword, boolean isDisplay) throws IOException {
		FileReader fileInput = new FileReader("database.txt");
		BufferedReader bufferedInput = new BufferedReader(fileInput);
		
		// Membaca perbaris
		String data = bufferedInput.readLine();
		boolean adaTidaknyaBuku = false;
		int nomorData = 0;
		
		if(isDisplay == true) {
			System.out.println("\n=========================================================================================================");
			System.out.println("| No |	Tahun	|	Penulis		\t|	Penerbit	|	Judul Buku	\t|");
			System.out.println("=========================================================================================================");

		}
		
		while(data != null) {
			
			// Cek keyword di dalam baris
			adaTidaknyaBuku = true;
			
			for(String key : keyword) {
				adaTidaknyaBuku = adaTidaknyaBuku && data.toLowerCase().contains(key.toLowerCase());
			}
			
			// Jika keywordnya cocok maka tampilkan data
			if(adaTidaknyaBuku) {
				if(isDisplay == true) {
					nomorData++;
					StringTokenizer stringToken = new StringTokenizer(data, ",");
					
					stringToken.nextToken();
					System.out.printf("| %2d ", nomorData);
					System.out.printf("|	%s	",stringToken.nextToken());
					System.out.printf("|	%-20s	",stringToken.nextToken()); //-20 --> rata kanan
					System.out.printf("|	%-10s	",stringToken.nextToken());
					System.out.printf("|	%-20s	|",stringToken.nextToken());
					System.out.println();
				} else {
					break;
				}
			}
			
			data = bufferedInput.readLine();
		}
		
		if(isDisplay == true) {
			System.out.println("=========================================================================================================");
		}
		
		return adaTidaknyaBuku;
	}
	
	private static void tambahData() throws IOException {
		
		FileWriter fileOutput = new FileWriter("database.txt", true);
		BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);
		
		// Mengambil data atau input dari user
		Scanner userInput = new Scanner(System.in);
		String penulis, judul, penerbit, tahun;
		
		System.out.print("Masukkan nama penulis : ");
		penulis = userInput.nextLine();
		System.out.print("Masukkan judul buku : ");
		judul = userInput.nextLine();
		System.out.print("Masukkan nama penerbit : ");
		penerbit = userInput.nextLine();
		System.out.print("Masukkan tahun terbit (yyyy) : ");
		tahun = ambilTahun();
		
		// Cek buku di database
		String[] keyword = {tahun + "," + penulis + "," + penerbit + "," + judul};
		System.out.println(Arrays.toString(keyword));
		
		boolean adaTidaknyaBuku = cekBukuDiDatabase(keyword, false);
		
		// Menulis data buku di database
		if(!adaTidaknyaBuku) {
			
			// Karinda Amelia_2025_1,2025,Karinda Amelia,Gramedia,Belajar Java
			System.out.println(ambilEntryPertahun(penulis, tahun));
			long nomorEntry = ambilEntryPertahun(penulis, tahun) + 1;
			String primaryKey = penulis + "_" + tahun + "_" + nomorEntry;
			
			System.out.println("\nData yang akan Anda masukkan adalah : ");
			System.out.println("PrimaryKey    :  " + primaryKey);
			System.out.println("Tahun terbit  :  " + tahun);
			System.out.println("Penulis       :  " + penulis);
			System.out.println("Judul         :  " + judul);
			System.out.println("Penerbit      :  " + penerbit);
			
			boolean isTambah = getYesOrNo("\nApakah Anda ingin menambah data tersebut?");
			
			// Menambah data
			if(isTambah) {
				bufferedOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
				bufferedOutput.newLine();
				bufferedOutput.flush();
			}
			
		} else {
			System.out.println("\n--->>Buku yang Anda masukkan sudah tersedia di database dengan data berikut");
			cekBukuDiDatabase(keyword, true);
		}
		
		bufferedOutput.close();
	}
	
	private static String ambilTahun() throws IOException {
		Scanner userInput = new Scanner(System.in);
		boolean tahunValid = false;
		String tahunInput = userInput.nextLine();
		
		while(!tahunValid) {
			try {
				Year.parse(tahunInput);
				tahunValid = true;
			} catch (Exception e){
				System.err.println("--->> Format tahun yang Anda masukkan salah");
				System.out.print("\nMasukkan tahun Kembali (yyyy) : ");
				tahunValid = false;
				tahunInput = userInput.nextLine();
			}
		}
		
		return tahunInput;
	}
	
	private static long ambilEntryPertahun(String penulis, String tahun) throws IOException {
		FileReader fileInput = new FileReader("database.txt");
		BufferedReader bufferedInput = new BufferedReader(fileInput);
		
		long entry = 0;
		String data = bufferedInput.readLine();
		String primaryKey;
		Scanner dataScanner;
		
		while(data != null) {
			dataScanner = new Scanner(data);
			dataScanner.useDelimiter(",");
			primaryKey = dataScanner.next();
			dataScanner = new Scanner(primaryKey);
			dataScanner.useDelimiter("_");
			
			penulis = penulis.replaceAll("\\s+", "");
			
			if(penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())) {
				entry = dataScanner.nextInt();
			}
			data = bufferedInput.readLine();
		}
		
		return entry;
	}
	
	private static boolean getYesOrNo(String message) {
		Scanner userInput = new Scanner(System.in);
		String pilihanUser;
		
		System.out.print(message + "(Y/N) : ");
		pilihanUser = userInput.next();
		
		while(!pilihanUser.equalsIgnoreCase("Y") && !pilihanUser.equalsIgnoreCase("N")) {
			System.err.println("\nPilihan Anda bukan Y atau N");
			System.out.println(message + "(Y/N)");
			pilihanUser = userInput.next();
		}
		
		return pilihanUser.equalsIgnoreCase("Y");
	}
	
	private static void clearScreen() {
		try {
			// For Windows
			if(System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd","/","cls").inheritIO().start().waitFor();
			} 
			// For Linux or Mac
			else {
				System.out.print("\033\143");
			}
		} catch (Exception e) {
			System.err.println("Tidak bisa clear screen");
		}
	}
}

















