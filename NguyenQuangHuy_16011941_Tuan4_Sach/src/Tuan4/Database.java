package Tuan4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Database {
	public static final String FILENAME = "data.txt";
	
	public static ArrayList<Sach> getData() {
		BufferedReader br = null;
		ArrayList<Sach> ds = new ArrayList<Sach>();
		try {
			if(new File(FILENAME).exists()) {
				br = new BufferedReader(new FileReader(FILENAME));
				br.readLine();
				while(br.ready()) {
					String line = br.readLine();
					if (line != null && !line.trim().equals("")) {
						String[] partofSach = line.split(";");
						Sach s = new Sach(partofSach[0], partofSach[1], partofSach[2], Integer.parseInt(partofSach[3]), partofSach[4], Integer.parseInt(partofSach[5]), Double.parseDouble(partofSach[6]), partofSach[7]);
						ds.add(s);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ds;
	}
	
	public static void saveData(ArrayList<Sach> ds) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(FILENAME));
			bw.write("MaSach;TuaSach;TacGia;NamXuatBan;NhaXuatBan;SoTrang;DonGia;ISBN;\n");
			for(Sach s : ds) {
				bw.write(s.toString() + "\n");
			}
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
