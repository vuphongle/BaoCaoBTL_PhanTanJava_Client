package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.ChiTietDichVuServices;
import dao.SanPhamService;
import entity.ChiTietDichVu;
import entity.HoaDonDatPhong;
import entity.Phong;
import entity.SanPham;



public class Dialog_TraSanPham extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel lblSLDaDat;
	private final Font font = new Font("Arial", Font.BOLD, 14);
	private final JTextField txtSLDat;
	private final JLabel lblSLTra;
	private final JTextField txtSLTra;
	private final JButton btnDongY;
	private final JButton btnHuy;
	private  ChiTietDichVuServices ctdv_dao;
	private  SanPhamService sp_Service;
	private final String tenSp;
	private final String maHD;
	private final Dialog_ThanhToan thanhToan;
	private final String maPhong;

	public Dialog_TraSanPham(int soLuong, String tenSp, String maHD, String maPhong, Dialog_ThanhToan thanhToan) throws RemoteException, MalformedURLException, NotBoundException {
		getContentPane().setBackground(Color.WHITE);
		setSize(300, 180);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.thanhToan = thanhToan;
		this.maHD = maHD;
		this.tenSp = tenSp;
		this.maPhong = maPhong;
		ctdv_dao = (ChiTietDichVuServices) Naming.lookup(DataManager.getRmiURL() + "chiTietDichVuServices");
		sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");
		
		getContentPane().add(lblSLDaDat = new JLabel("Số lượng đã đặt"));
		lblSLDaDat.setBounds(10, 10, 200, 30);
		lblSLDaDat.setFont(font);
		
		getContentPane().add(txtSLDat = new JTextField(soLuong+""));
		txtSLDat.setBounds(210,10,60,30);
		txtSLDat.setEditable(false);
		txtSLDat.setFont(font);
		
		getContentPane().add(lblSLTra = new JLabel("Nhập số lượng muốn trả lại:"));
		lblSLTra.setBounds(10, 50, 200, 30);
		lblSLTra.setFont(font);
		
		getContentPane().add(txtSLTra = new JTextField());
		txtSLTra.setBounds(210,50,60,30);
		txtSLTra.setFont(font);
		
		getContentPane().add(btnDongY = new JButton("Đồng ý"));
		btnDongY.setBounds(40, 100, 100, 35);
		btnDongY.setForeground(Color.WHITE);
		btnDongY.setFont(new Font("Arial", Font.BOLD, 16));
		btnDongY.setBorder(new RoundedBorder(10));
		btnDongY.setBackground(new Color(46, 204, 113));
		
		getContentPane().add(btnHuy = new JButton("Hủy"));
		btnHuy.setBounds(150, 100, 100, 35);
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Arial", Font.BOLD, 16));
		btnHuy.setBorder(new RoundedBorder(10));
		btnHuy.setBackground(Color.red);
		
		btnDongY.addActionListener(this);
		btnHuy.addActionListener(this);
		
	}
	
	public void dongY() throws NumberFormatException, HeadlessException, RemoteException, MalformedURLException, NotBoundException {
		HoaDonDatPhong hd = new HoaDonDatPhong(maHD);
		Phong ph = new Phong(maPhong);
		SanPham s = null;
		try {
			s = sp_Service.getSanPhamTheoTenSanPham2(tenSp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SanPham sp = new SanPham(s.getMaSanPham());
		if(Integer.parseInt(txtSLTra.getText()) < Integer.parseInt(txtSLDat.getText())
				 && Integer.parseInt(txtSLTra.getText()) >= 0) {
			int soLuong = Integer.parseInt(txtSLDat.getText()) - Integer.parseInt(txtSLTra.getText());
			double donGia = 0;
			if(s.getloaiSanPham().equals("Thức ăn")) {
				donGia = s.getDonGia() * 1.03;
			}
			else if(s.getloaiSanPham().equals("Đồ uống")) {
				donGia = s.getDonGia() * 1.02;
			}else {
				donGia = s.getDonGia() * 1.01;
			}
			ChiTietDichVu ctdv = new ChiTietDichVu(hd,ph,sp,soLuong,donGia);
			try {
				for(SanPham sanPham : sp_Service.getAllSanPhams()) {
					if(sanPham.getMaSanPham().equals(s.getMaSanPham())) {
						sp_Service.updateSLTon(sanPham.getSoLuongTon() + Integer.parseInt(txtSLTra.getText()), sanPham.getMaSanPham());
						break;
					}
				}
			} catch (NumberFormatException | RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ctdv_dao.UpdateChiTietDV(ctdv)) {
				thanhToan.clearTable();
				thanhToan.loadData();
				thanhToan.clear_Tien();
				thanhToan.load_Tien();
				JOptionPane.showMessageDialog(this, "Trả thành công!");
				setVisible(false);
			}
		}else if(Integer.parseInt(txtSLTra.getText()) == Integer.parseInt(txtSLDat.getText())){
			if(ctdv_dao.deleteChiTietDV2(maHD, s.getMaSanPham(), maPhong)) {
				thanhToan.clearTable();
				thanhToan.loadData();
				thanhToan.clear_Tien();
				thanhToan.load_Tien();
				JOptionPane.showMessageDialog(this, "Trả thành công!!!");
				setVisible(false);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Nhập không hợp lệ!!!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnDongY)){
			try {
				dongY();
			} catch (NumberFormatException | HeadlessException | RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(o.equals(btnHuy)) {
			setVisible(false);
		}
	}
}
