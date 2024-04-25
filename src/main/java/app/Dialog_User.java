package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.UIManager;

import dao.DangNhapServices;
import dao.NhanVienService;
import entity.NhanVien;

public class Dialog_User extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField txt_HoTen;
	private final JButton btnThot;
	private final JButton btnDoiMK;
	private final JLabel lbl_HoTen;
    private final JLabel lbl_TrangThai;
	private final JLabel lbl_ChucVu;
	private final JTextField txtQunL;
	private final JLabel hinhNV;
	private final JLabel lbl_nen;
	private final NhanVienService nv_dao;
	private Dialog_DoiMatKhau Dialog_Doi_mk;
	private String ma;
	private final JLabel lbl_TrangThai_1;
	@SuppressWarnings("unused")
	private  DangNhapServices dangNhap_dao;
	private final String trangthaidangnhap;
    private String hinhanh_url;
    private InetAddress ip;
//	private GD_TrangDangNhap gd_dangNhap = new GD_TrangDangNhap(); 
	public Dialog_User() throws RemoteException, MalformedURLException, NotBoundException {
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("User");
		setSize(400, 300);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
	    this.setIconImage(icon.getImage());
		//setResizable(false);
		nv_dao = (NhanVienService) Naming.lookup(DataManager.getRmiURL() + "nhanVienServices");
		dangNhap_dao= (DangNhapServices) Naming.lookup(DataManager.getRmiURL() + "dangNhapServices");
		this.addWindowListener(new WindowAdapter() {
		    public void windowOpened(WindowEvent e) {
				NhanVien nv = null;
				try {
					String mnv = "";
					Map<String, String> mapIP_MSNV = DataManager.getMapIP_MSNV();
					for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
						if (entry.getKey().equals(ip.getHostAddress())) {
							mnv = entry.getValue();
						}
					}
					nv = nv_dao.findByID(mnv);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txt_HoTen.setText(nv.getHoTen());
				txtQunL.setText(nv.getChucVu());
				ma=nv.getMaNhanVien();
				hinhanh_url=nv.getAnhDaiDien();
			      ImageIcon phongtrong = new ImageIcon(hinhanh_url);
			      Image originalImage_phongtrong = phongtrong.getImage();
			      Image resizedImage_phongtrong = originalImage_phongtrong.getScaledInstance(130, 110, java.awt.Image.SCALE_SMOOTH);
			      ImageIcon resizedIcon_phongtrong = new ImageIcon(resizedImage_phongtrong);
			      hinhNV.setIcon(resizedIcon_phongtrong);
				
		    }
		});		
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		hinhNV = new JLabel("");
		hinhNV.setBounds(0, 10, 120, 120);
		
		getContentPane().add(hinhNV);
		
		btnDoiMK = new JButton("Đổi Mật Khẩu");
		btnDoiMK.setBounds(10, 180, 130, 40);
		 btnDoiMK.setBackground(SystemColor.info);
		 btnDoiMK.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnDoiMK);
		
		 btnThot = new JButton("Đăng Xuất");
		 btnThot.setBounds(210, 180, 130, 40);
		 btnThot.setBackground(UIManager.getColor("Button.background"));
		 btnThot.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnThot);
		
		JLabel lblthongtinNV = new JLabel("Thông Tin Nhân Viên");
		lblthongtinNV.setBounds(150, 5, 200, 30);
		lblthongtinNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblthongtinNV.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblthongtinNV);
		
		txt_HoTen = new JTextField();
		txt_HoTen.setBackground(SystemColor.inactiveCaptionBorder);
		txt_HoTen.setBounds(210, 50, 160, 30);
		txt_HoTen.setFont(new Font("Arial", Font.PLAIN, 14));


		txt_HoTen.setEditable(false);
		getContentPane().add(txt_HoTen);
		txt_HoTen.setColumns(10);
		
		lbl_HoTen = new JLabel("Họ tên:");
		lbl_HoTen.setBounds(130, 50, 62, 30);
		lbl_HoTen.setFont(new Font("Arial", Font.BOLD, 15));
		getContentPane().add(lbl_HoTen);
		
		lbl_ChucVu = new JLabel("Chức vụ:");
		lbl_ChucVu.setBounds(130, 90, 70, 30);
		lbl_ChucVu.setFont(new Font("Arial", Font.BOLD, 15));
		getContentPane().add(lbl_ChucVu);
		
		lbl_TrangThai = new JLabel("Trạng Thái:");
		lbl_TrangThai.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_TrangThai.setBounds(130, 135, 90, 25);
		getContentPane().add(lbl_TrangThai);
		
		

		trangthaidangnhap="Đang hoạt động";
		lbl_TrangThai_1 = new JLabel(trangthaidangnhap);
		lbl_TrangThai_1.setFont(new Font("Arial", Font.ITALIC, 14));
		lbl_TrangThai_1.setBounds(230, 135, 120, 25);
		getContentPane().add(lbl_TrangThai_1);
		
		txtQunL = new JTextField();
		txtQunL.setBackground(SystemColor.inactiveCaptionBorder);
		txtQunL.setBounds(210, 90, 160, 30);
//		txtQunL.setText("Nhân viên quản lý");
		txtQunL.setEditable(false);
		txtQunL.setFont(new Font("Arial", Font.PLAIN, 14));
		txtQunL.setColumns(10);
		getContentPane().add(txtQunL);
		
		lbl_nen = new JLabel("");
		lbl_nen.setIcon(new ImageIcon("image\\nenUser.jpg"));
		lbl_nen.setBounds(0, 0, 384, 261);
		getContentPane().add(lbl_nen);
		

				
		btnThot.addActionListener(this);
		btnDoiMK.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThot)) {
			Window[] windows = Window.getWindows();
			for (Window window : windows) {
				window.dispose();
			}
			GD_TrangDangNhap dangNhap = null;
			try {
				dangNhap = new GD_TrangDangNhap();
			} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dangNhap.setVisible(true);
		}else if(o.equals(btnDoiMK)) {		
			try {
				Dialog_Doi_mk= new Dialog_DoiMatKhau(ma);
			} catch (MalformedURLException | RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Dialog_Doi_mk.setModal(true);
			Dialog_Doi_mk.setVisible(true);
			
		}
	}
}

