package Tuan4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Year;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class frmSach extends JFrame implements ActionListener, MouseListener, ListSelectionListener {
	JTextField txtMaSach, txtTuaSach, txtTacGia, txtNamXuatBan, txtNhaXuatBan, txtSoTrang, txtDonGia, txtISBN;
	JLabel lblMaSach, lblTuaSach, lblTacGia, lblNamXuatBan, lblNhaXuatBan, lblSoTrang, lblDonGia, lblISBN;
	JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu;
	JTable table;
	DefaultTableModel model;
	JComboBox<String> cbMaSach;
	QuanLySach quanLySach = new QuanLySach();
	
	String maSach, tuaSach, tacGia, nhaXuatBan, ISBN;
	int namXuatBan, soTrang;
	double donGia;
	
	public frmSach() {
		super("Quản lý sách");
		
		JPanel pTop = new JPanel();
		pTop.setBorder(BorderFactory.createTitledBorder("Records Editor"));
		pTop.setLayout(new BoxLayout(pTop, BoxLayout.X_AXIS));
		
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(lblMaSach = new JLabel("Mã sách:"));
		p1.add(Box.createVerticalStrut(10));
		p1.add(lblTuaSach = new JLabel("Tựa sách:"));
		p1.add(Box.createVerticalStrut(10));
		p1.add(lblTacGia = new JLabel("Tác giả:"));
		p1.add(Box.createVerticalStrut(10));
		p1.add(lblNamXuatBan = new JLabel("Năm xuất bản:"));
		pTop.add(Box.createHorizontalStrut(10));
		pTop.add(p1);
		
		pTop.add(Box.createHorizontalStrut(10));
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(txtMaSach = new JTextField(10));
		p2.add(Box.createVerticalStrut(5));
		p2.add(txtTuaSach = new JTextField(10));
		p2.add(Box.createVerticalStrut(5));
		p2.add(txtTacGia = new JTextField(10));
		p2.add(Box.createVerticalStrut(5));
		p2.add(txtNamXuatBan = new JTextField(10));
		pTop.add(p2);

		pTop.add(Box.createHorizontalStrut(10));
		
		JPanel p3 = new JPanel();
		p3.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p3.add(lblNhaXuatBan = new JLabel("Nhà xuất bản:"));
		p3.add(Box.createVerticalStrut(10));
		p3.add(lblSoTrang = new JLabel("Số trang:"));
		p3.add(Box.createVerticalStrut(10));
		p3.add(lblDonGia = new JLabel("Đơn giá:"));
		p3.add(Box.createVerticalStrut(10));
		p3.add(lblISBN = new JLabel("ISBN:"));
		pTop.add(p3);

		pTop.add(Box.createHorizontalStrut(10));
		
		JPanel p4 = new JPanel();
		p4.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		p4.add(txtNhaXuatBan = new JTextField(10));
		p4.add(Box.createVerticalStrut(5));
		p4.add(txtSoTrang = new JTextField(10));
		p4.add(Box.createVerticalStrut(5));
		p4.add(txtDonGia = new JTextField(10));
		p4.add(Box.createVerticalStrut(5));
		p4.add(txtISBN = new JTextField(10));
		pTop.add(p4);
		pTop.add(Box.createHorizontalStrut(10));
		
		add(pTop, BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		
		JPanel pBtn = new JPanel();
		pBtn.add(btnThem = new JButton("Thêm"));
		pBtn.add(btnXoaRong = new JButton("Xoá rỗng"));
		pBtn.add(btnXoa = new JButton("Xoá"));
		pBtn.add(btnSua = new JButton("Sửa"));
		pBtn.add(btnLuu = new JButton("Lưu"));
		pBtn.add(new JLabel("Tìm theo mã sách:"));
		pBtn.add(cbMaSach = new JComboBox<String>());
		
		cbMaSach.addActionListener(this);
		
		btnThem.addActionListener(this);
		btnXoaRong.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		
		JPanel pTable = new JPanel();
		pTable.setBorder(BorderFactory.createTitledBorder("Danh sách các cuốn sách"));
		String[] cols = {"MaSach", "TuaSach", "TacGia", "NamXuatBan", "NhaXuatBan", "SoTrang", "DonGia", "ISBN"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.addMouseListener(this);
		table.getSelectionModel().addListSelectionListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(850, 260));
		pTable.add(pane);
		
		pCenter.add(pBtn);
		pCenter.add(pTable);
		add(pCenter, BorderLayout.CENTER);
		
		loadDatabase();
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 500);
		setResizable(false);
	}
	
	private void loadDatabase() {
		ArrayList<Sach> ds = Database.getData();
		for (Sach s : ds) {
			String[] sachInArray = s.toString().split(";");
			quanLySach.themSach(s);
			model.addRow(sachInArray);
			cbMaSach.addItem(sachInArray[0]);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			Sach s = getSachFromTextField();
			if (s == null) {
				return;
			}
			if(quanLySach.themSach(s)) {
				String[] row = {maSach, tuaSach, tacGia, Integer.toString(namXuatBan), nhaXuatBan, Integer.toString(soTrang), Double.toString(donGia), ISBN};
				model.addRow(row);
				cbMaSach.addItem(s.getMaSach());
			} else {
				JOptionPane.showMessageDialog(this, "Mã không được trùng!");
			}
		}
		else if (o.equals(btnXoaRong)) {
			txtMaSach.setEditable(true);
			txtMaSach.setText("");
			txtTuaSach.setText("");
			txtTacGia.setText("");
			txtNamXuatBan.setText("");
			txtNhaXuatBan.setText("");
			txtSoTrang.setText("");
			txtDonGia.setText("");
			txtISBN.setText("");
		}
		else if (o.equals(btnXoa)) {
			int index = table.getSelectedRow();
			if (index != -1) {
				int w = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không? ", "Chú ý", JOptionPane.YES_NO_OPTION);
				if (w == JOptionPane.YES_OPTION) {
					if (quanLySach.xoaMotCuonSach(index)) {
						model.removeRow(index);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Chọn dòng cần xoá!");
			}
		} 
		else if (o.equals(btnSua)) {
			String maOldSach = txtMaSach.getText().trim();
			int rowIndex = table.getSelectedRow();
			Sach newSach = getSachFromTextField();
			if (quanLySach.capNhatSach(maOldSach, newSach)) {
				model.setValueAt(newSach.getTuaSach(), rowIndex, 1);
				model.setValueAt(newSach.getTacGia(), rowIndex, 2);
				model.setValueAt(newSach.getNamXuatBan(), rowIndex, 3);
				model.setValueAt(newSach.getNhaXuatBan(), rowIndex, 4);
				model.setValueAt(newSach.getSoTrang(), rowIndex, 5);
				model.setValueAt(newSach.getDonGia(), rowIndex, 6);
				model.setValueAt(newSach.getISBN(), rowIndex, 7);
				JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
			} else {
				JOptionPane.showMessageDialog(this, "Chọn dòng cần cập nhật!");
			}
		}
		else if (o.equals(btnLuu)) {
			Database.saveData(quanLySach.getDsSach());
			JOptionPane.showMessageDialog(this, "Lưu thành công!");
		}
		else if (o.equals(cbMaSach)) {
			String maSelected = (String)cbMaSach.getSelectedItem();
			Sach s = new Sach(maSelected);
			ArrayList<Sach> ds = quanLySach.getDsSach();
			if (ds.contains(s)) {
				int index = ds.indexOf(s);
				table.setRowSelectionInterval(index, index);
				table.scrollRectToVisible(table.getCellRect(index, 0, true));
			}
			
		}
	}
	
	private Sach getSachFromTextField() {
		
		maSach = txtMaSach.getText().trim();
		tuaSach = txtTuaSach.getText().trim();
		tacGia = txtTacGia.getText().trim();
		nhaXuatBan = txtNhaXuatBan.getText().trim();
		ISBN = txtISBN.getText().trim();
		
//		Mã sách: ^[A-Z]\d{3}$
//		Tựa sách: ^[A-Za-z\s-0-9()]+$
//		Tác giả: ^[A-Za-z\s']+$
//		ISBN: ^\d{1,}-\d{1,}-\d{1,}-\d{1,}$
		if (!maSach.matches("^[A-Z]\\d{3}$")) {
			JOptionPane.showMessageDialog(this, "Mã sách phải bắt đầu bằng 1 kí tự hoa, theo sau là 3 kí tự số");
			txtMaSach.selectAll();
			txtMaSach.requestFocus();
			return null;
		}
		
		if (!tuaSach.matches("^[A-Za-z\\s-0-9()]+$")) {
			JOptionPane.showMessageDialog(this, "Tựa sách không chứa các kí hiệu đặt biệt, ngoại trừ -");
			txtTuaSach.selectAll();
			txtTuaSach.requestFocus();
			return null;
		}
		
		if (!tacGia.matches("^[A-Za-z\\s']+$")) {
			JOptionPane.showMessageDialog(this, "Tác giả không chứa kí tự số, các kí tự đặc biêt, ngoại trừ '");
			txtTacGia.selectAll();
			txtTacGia.requestFocus();
			return null;
		}
		
		if (!ISBN.matches("^\\d{1,}-\\d{1,}-\\d{1,}-\\d{1,}$")) {
			JOptionPane.showMessageDialog(this, "ISBN phải có dạng A-B-C-D, ABCD là các số");
			txtISBN.selectAll();
			txtISBN.requestFocus();
			return null;
		}
		
		try {
			namXuatBan = Integer.parseInt(txtNamXuatBan.getText().trim());
			if (namXuatBan < 1900 || namXuatBan > Year.now().getValue()) {
				JOptionPane.showMessageDialog(this, "Năm phải lớn hơn 1900, và nhỏ hơn hoặc bằng năm hiện tại");
				txtNamXuatBan.selectAll();
				txtNamXuatBan.requestFocus();
				return null;
			}
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this, "Chỉ số và không để trống!");
			txtNamXuatBan.selectAll();
			txtNamXuatBan.requestFocus();
			return null;
		}
		
		if (txtSoTrang.getText().equals("")) {
			soTrang = 0;
		} else {
			try {
				soTrang = Integer.parseInt(txtSoTrang.getText().trim());
				if (soTrang < 0) {
					JOptionPane.showMessageDialog(this, "Số trang lớn hơn 0!");
					txtSoTrang.selectAll();
					txtSoTrang.requestFocus();
					return null;
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, "Chỉ số!");
				txtSoTrang.selectAll();
				txtSoTrang.requestFocus();
				return null;
			}
		}
		
		
		if (txtDonGia.getText().equals("")) {
			donGia = 0;
		} else {
			try {
				donGia = Double.parseDouble(txtDonGia.getText().trim());
				if (donGia < 0) {
					JOptionPane.showMessageDialog(this, "Đơn giá lớn hơn 0!");
					txtDonGia.selectAll();
					txtDonGia.requestFocus();
					return null;
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, "Chỉ số!");
				txtDonGia.selectAll();
				txtDonGia.requestFocus();
				return null;
			}
		}
		return new Sach(maSach, tuaSach, tacGia, namXuatBan, nhaXuatBan, soTrang, donGia, ISBN);
	}
	
	private void rowselected() {
		int rowIndex = table.getSelectedRow();
		if (rowIndex != -1) {
			String maSach = (String) table.getValueAt(rowIndex, 0);
			Sach s = new Sach(maSach);
			ArrayList<Sach> dsSach = quanLySach.getDsSach();
			if (dsSach.contains(s)) {
				s = dsSach.get(dsSach.indexOf(s));
				txtMaSach.setText(s.getMaSach());
				txtTuaSach.setText(s.getTuaSach());
				txtTacGia.setText(s.getTacGia());
				txtNhaXuatBan.setText(s.getNhaXuatBan());
				txtNamXuatBan.setText(Integer.toString(s.getNamXuatBan()));
				txtSoTrang.setText(Integer.toString(s.getSoTrang()));
				txtDonGia.setText(Double.toString(s.getDonGia()));
				txtISBN.setText(s.getISBN());
				txtMaSach.setEditable(false);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		rowselected();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		rowselected();
	}
}
