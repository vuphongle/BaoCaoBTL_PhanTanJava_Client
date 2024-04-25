package app;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.Color;

import entity.LoaiPhong;
import entity.Phong;
import dao.LoaiPhongServices;
import dao.PhongService;

public class Dialog_HienThiPhongSuaChua extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel lblPhong;
    private final JLabel lblLoai;
    private final JLabel lblSucChua;
    private final JLabel lblTrangThai;
    private final JLabel lblGia;
    private final JLabel lblLoai_1;
    private final JLabel lblSucChua_1;
    private final JLabel lbltrangthai_1;
    private final JLabel lblgia_1;
    private final JLabel lblPhong_1;
	private final PhongService p_Service;
	private final LoaiPhongServices lp_dao;

	private Phong p = new Phong();
	private final LoaiPhong lp;

	public Dialog_HienThiPhongSuaChua(String maPhong) throws RemoteException, MalformedURLException, NotBoundException{
		// kích thước
		// dialog--------------*****************************************************************
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL() + "loaiPhongServices");
		
		getContentPane().setBackground(Color.WHITE);
		setSize(300, 260);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
	    this.setIconImage(icon.getImage());

		// các
		// lbl-----------------------------------------------------------------------
		lblPhong = new JLabel("Phòng:");
		lblPhong.setFont(new Font("Arial", Font.BOLD, 18));
		lblPhong.setBounds(20, 10, 100, 30);
		getContentPane().add(lblPhong);

		lblLoai = new JLabel("Loại:");
		lblLoai.setFont(new Font("Arial", Font.BOLD, 18));
		lblLoai.setBounds(20, 50, 100, 30);
		getContentPane().add(lblLoai);

		lblSucChua = new JLabel("Sức chứa:");
		lblSucChua.setFont(new Font("Arial", Font.BOLD, 18));
		lblSucChua.setBounds(20, 90, 100, 30);
		getContentPane().add(lblSucChua);

		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 18));
		lblTrangThai.setBounds(20, 130, 100, 30);
		getContentPane().add(lblTrangThai);

		lblGia = new JLabel("Giá:");
		lblGia.setFont(new Font("Arial", Font.BOLD, 18));
		lblGia.setBounds(20, 170, 100, 30);
		getContentPane().add(lblGia);

		lblPhong_1 = new JLabel(maPhong);
		lblPhong_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblPhong_1.setBounds(130, 10, 120, 30);
		getContentPane().add(lblPhong_1);

		try {
			p = p_Service.getPhongTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());

		lblLoai_1 = new JLabel(lp.getTenLoaiPhong());
		lblLoai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblLoai_1.setBounds(130, 50, 120, 30);
		getContentPane().add(lblLoai_1);

		lblSucChua_1 = new JLabel(lp.getSucChua() + "");
		lblSucChua_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblSucChua_1.setBounds(130, 90, 120, 30);
		getContentPane().add(lblSucChua_1);

		lbltrangthai_1 = new JLabel(p.getTrangThai() + "");
		lbltrangthai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lbltrangthai_1.setBounds(130, 130, 130, 30);
		getContentPane().add(lbltrangthai_1);

		lblgia_1 = new JLabel(lp.getDonGiaTheoGio() + "VNĐ");
		lblgia_1.setBackground(Color.WHITE);
		lblgia_1.setForeground(Color.RED);
		lblgia_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblgia_1.setBounds(130, 170, 120, 30);
		getContentPane().add(lblgia_1);
	}
}
