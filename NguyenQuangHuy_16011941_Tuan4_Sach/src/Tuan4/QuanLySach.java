package Tuan4;

import java.util.ArrayList;

public class QuanLySach {
	public ArrayList<Sach> dsSach;
	
	public QuanLySach() {
		dsSach = new ArrayList<Sach>();
	}
	
	public boolean themSach(Sach sach) {
		if(dsSach.contains(sach)) return false;
		dsSach.add(sach);
		return true;
	}
	
	public boolean xoaMotCuonSach(int index) {
		if(index >= 0 && index < dsSach.size()) {
			dsSach.remove(index);
			return true;
		}
		return false;
	}
	
	public ArrayList<Sach> getDsSach() {
		return this.dsSach;
	}
	
	public Sach getSach(int index) {
		if(index >= 0 && index < dsSach.size()) {
			return this.dsSach.get(index);
		}
		return null;
	}
	
	public Sach timKiem(String maSach) {
		Sach s = new Sach(maSach);
		if(this.dsSach.contains(s)) {
			return this.dsSach.get(dsSach.indexOf(s));
		}
		return null;
	}
	
	public boolean capNhatSach(String maSachOld, Sach newSach) {
		Sach oldSach = new Sach(maSachOld);
		if(this.dsSach.contains(oldSach)) {
			oldSach = this.dsSach.get(this.dsSach.indexOf(oldSach));
			
			oldSach.setTuaSach(newSach.getTuaSach());
			oldSach.setTacGia(newSach.getTacGia());
			oldSach.setNamXuatBan(newSach.getNamXuatBan());
			oldSach.setNhaXuatBan(newSach.getNhaXuatBan());
			oldSach.setSoTrang(newSach.getSoTrang());
			oldSach.setDonGia(newSach.getDonGia());
			oldSach.setISBN(newSach.getISBN());
			
			return true;
		}
		return false;
	}
}
