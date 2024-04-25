package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import dao.ChiTietHoaDonServices;
import dao.ClientConnectionService;
import dao.HoaDonDatPhongServices;
import dao.KhachHangServices;
import dao.LoaiPhongServices;
import dao.PhieuDatPhongService;
import dao.PhongService;
import dao.TempDatPhongServices;
import dao.TempPhongBiChuyenServices;
import dao.TempThanhToanServices;
import entity.ChiTietHoaDon;
import entity.Enum_TrangThai;
import entity.HoaDonDatPhong;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.TempDatPhong;
import entity.TempPhongBiChuyen;
import entity.TempThanhToan;

public class Dialog_PhongDangSD extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel lblPhong;
	private final JLabel lblGia;
	private final JLabel lblTrangThai;
	private JLabel lblThoiGianHat;
	private final JLabel lblSoNguoi;
	private final JLabel lblLoai;
	private final JLabel lblLoai_1;
	private final JLabel lblPhong_1;
	private final JLabel lblgia_1;
	private final JLabel lbltrangthai_1;
	private final JLabel lblSoNguoi_1;
	private final JLabel lblTenKH;
	private final JLabel lblTenKH_1;
	private final JButton btnThemDV;
	private final JButton btnChuyenPhong;
	private final JButton btnThanhToan;
	private final JButton btnThemPhong;

	private Dialog_ChuyenPhong dialog_ChuyenPhong;
	private Dialog_ThemDichVu dialog_ThemDichVu;
	private Dialog_ThanhToan dialog_ThanhToan;
	private PhongService p_Service;
	private final LoaiPhongServices lp_dao;
	private Phong p;
	private LoaiPhong lp;
	private  ChiTietHoaDonServices cthd_dao;
	private final Date gioHienTai;
	private final Date phutHienTai;
	private double soGioHat;
	private double soPhutHat;
	private final KhachHangServices kh_dao;
	private final PhieuDatPhongService pdp_Service;
	private PhieuDatPhong pdp_of_room;
	private final TempDatPhongServices tmp_dao;
	private final HoaDonDatPhongServices hddp_Service;
	private GD_TrangChu trangChu;
	private final TempThanhToanServices tempTT_dao;
	@SuppressWarnings("unused")
	private Dialog_DatPhongTrong_2 dialog_DatPhongTrong_2;
	private final String maP;
	private final TempPhongBiChuyenServices tempChuyen_dao;
	private final GD_DatPhong datPhong;
	private InetAddress ip;
	private ClientConnectionService clientConnectionService;

	public Dialog_PhongDangSD(String maPhong, GD_DatPhong datPhong) throws RemoteException, MalformedURLException, NotBoundException{
		// kích thước giao diện
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maP = maPhong;
		this.datPhong = datPhong;
		getContentPane().setBackground(Color.WHITE);
		setSize(335, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
		this.setIconImage(icon.getImage());

		tmp_dao = (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL()+"tempDatPhongServices");
		tempTT_dao = (TempThanhToanServices) Naming.lookup(DataManager.getRmiURL()+"tempThanhToanServices");
		tempChuyen_dao  = (TempPhongBiChuyenServices) Naming.lookup(DataManager.getRmiURL()+"tempPhongBiChuyenServices");

		cthd_dao = (ChiTietHoaDonServices)Naming.lookup(DataManager.getRmiURL()+"chiTietHoaDonServices");
		kh_dao = (KhachHangServices)Naming.lookup(DataManager.getRmiURL()+"khachHangServices");
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL()+"phongServices");
		hddp_Service = (HoaDonDatPhongServices) Naming.lookup(DataManager.getRmiURL()+"hoaDonDatPhongServices");
		pdp_Service = (PhieuDatPhongService) Naming.lookup(DataManager.getRmiURL()+"phieuDatPhongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL()+"loaiPhongServices");
		

		// các lbl góc
		// trái-----------------------------------------------------------------------
		lblPhong = new JLabel("Phòng:");
		lblPhong.setBounds(10, 10, 100, 30);
		lblPhong.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblPhong);

		lblLoai = new JLabel("Loại:");
		lblLoai.setBounds(10, 50, 100, 30);
		lblLoai.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblLoai);

		lblSoNguoi = new JLabel("Số người:");
		lblSoNguoi.setBounds(10, 90, 100, 30);
		lblSoNguoi.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblSoNguoi);

		lblThoiGianHat = new JLabel("Thời gian hát:");
		lblThoiGianHat.setBounds(10, 130, 130, 30);
		lblThoiGianHat.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblThoiGianHat);

		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setBounds(10, 170, 100, 30);
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblTrangThai);

		lblGia = new JLabel("Giá phòng:");
		lblGia.setBounds(10, 250, 100, 30);
		lblGia.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblGia);

		// các lbl góc
		// phải---------------------------------------------------------------------
		lblPhong_1 = new JLabel();
		lblPhong_1.setText(maPhong);
		lblPhong_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblPhong_1.setBounds(150, 10, 140, 30);
		getContentPane().add(lblPhong_1);
		try {
			p = p_Service.getPhongTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());

		lblLoai_1 = new JLabel();
		lblLoai_1.setText(lp.getTenLoaiPhong());
		lblLoai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblLoai_1.setBounds(130, 50, 120, 30);
		getContentPane().add(lblLoai_1);

		pdp_of_room = null;
		List<PhieuDatPhong> dsPDP = pdp_Service
				.getDanhSachPhieuDatPhongTheoMaPhong(lblPhong_1.getText().trim());
		for (PhieuDatPhong pdp : dsPDP) {
			pdp_of_room = pdp;
		}
		lblSoNguoi_1 = new JLabel();
		lblSoNguoi_1.setText(pdp_of_room.getSoNguoiHat() + "");
		lblSoNguoi_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblSoNguoi_1.setBounds(150, 90, 120, 30);
		getContentPane().add(lblSoNguoi_1);

		ChiTietHoaDon cthd_hienTaiCuaPhong = null;
		List<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong_1.getText().trim());
		for (ChiTietHoaDon cthd : dsCTHD) {
			cthd_hienTaiCuaPhong = cthd;
		}

		DateFormat dateFormatGio = new SimpleDateFormat("HH");
		gioHienTai = new Date();
		double gioHT = Double.parseDouble(dateFormatGio.format(gioHienTai));
		DateFormat dateFormatPhut = new SimpleDateFormat("mm");
		phutHienTai = new Date();
		double phutHT = Double.parseDouble(dateFormatPhut.format(phutHienTai));
		double gioNhanPhong = Double.parseDouble(dateFormatGio.format(cthd_hienTaiCuaPhong.getGioNhanPhong()));
		double phutNhanPhong = Double.parseDouble(dateFormatPhut.format(cthd_hienTaiCuaPhong.getGioNhanPhong()));

		if (gioHT >= gioNhanPhong && phutHT >= phutNhanPhong) {
			soGioHat = gioHT - gioNhanPhong;
			soPhutHat = phutHT - phutNhanPhong;
		} else if (gioHT <= gioNhanPhong && phutHT >= phutNhanPhong) {
			soGioHat = gioHT - gioNhanPhong + 24.0;
			soPhutHat = phutHT - phutNhanPhong;
		} else if (gioHT >= gioNhanPhong && phutHT <= phutNhanPhong) {
			soGioHat = gioHT - gioNhanPhong - 1;
			soPhutHat = phutHT - phutNhanPhong + 60.0;
		} else if (gioHT <= gioNhanPhong && phutHT <= phutNhanPhong) {
			soGioHat = gioHT - gioNhanPhong + 24.0 - 1.0;
			soPhutHat = phutHT - phutNhanPhong + 60.0;
		}

		DecimalFormat df = new DecimalFormat("#.#");
		lblThoiGianHat = new JLabel();
		lblThoiGianHat.setText(df.format(soGioHat) + " giờ " + df.format(soPhutHat) + " phút");
		lblThoiGianHat.setFont(new Font("Arial", Font.BOLD, 15));
		lblThoiGianHat.setBounds(150, 130, 120, 30);
		getContentPane().add(lblThoiGianHat);

		lbltrangthai_1 = new JLabel();
		lbltrangthai_1.setText("Đang dùng");
		lbltrangthai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lbltrangthai_1.setBounds(150, 170, 120, 30);
		getContentPane().add(lbltrangthai_1);

		try {
			p = p_Service.getPhongTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());

		lblgia_1 = new JLabel();
		lblgia_1.setText(lp.getDonGiaTheoGio() + "VNĐ");
		lblgia_1.setBackground(Color.WHITE);
		lblgia_1.setForeground(Color.RED);
		lblgia_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblgia_1.setBounds(150, 250, 140, 30);
		getContentPane().add(lblgia_1);

		lblTenKH = new JLabel("Khách hàng:");
		lblTenKH.setBounds(10, 210, 130, 30);
		lblTenKH.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblTenKH);

		KhachHang kh = kh_dao.getKhachHangTheoMaKH(pdp_of_room.getKhachHang().getMaKhachHang());
		lblTenKH_1 = new JLabel();
		lblTenKH_1.setText(kh.getHoTen());
		lblTenKH_1.setBackground(Color.WHITE);
		lblTenKH_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblTenKH_1.setBounds(150, 210, 150, 30);
		getContentPane().add(lblTenKH_1);

		// nút
		// button---------------------------------------------------------------------------
		btnThemDV = new JButton("Thêm Dịch Vụ");
		btnThemDV.setBounds(35, 290, 250, 35);
		btnThemDV.setForeground(Color.WHITE);
		btnThemDV.setFont(new Font("Arial", Font.BOLD, 17));
		btnThemDV.setBackground(new Color(33, 167, 38, 255));
		btnThemDV.setBorder(new RoundedBorder(60));
		//		btnThemDV.setBorderPainted(false);
		getContentPane().add(btnThemDV);

		btnChuyenPhong = new JButton("Chuyển Phòng");
		btnChuyenPhong.setBounds(35, 370, 250, 35);
		btnChuyenPhong.setForeground(Color.WHITE);
		btnChuyenPhong.setFont(new Font("Arial", Font.BOLD, 17));
		btnChuyenPhong.setBackground(new Color(26, 147, 216, 255));
		// btnChuyenPhong.setBorderPainted(false);
		btnChuyenPhong.setBorder(new RoundedBorder(60));
		getContentPane().add(btnChuyenPhong);

		btnThanhToan = new JButton("Thanh Toán");
		btnThanhToan.setBounds(35, 410, 250, 35);
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFont(new Font("Arial", Font.BOLD, 17));
		btnThanhToan.setBorder(new RoundedBorder(60));
		btnThanhToan.setBackground(new Color(252, 155, 78, 255));
		// btnThanhToan.setBorderPainted(false);
		getContentPane().add(btnThanhToan);

		btnThemPhong = new JButton("Đặt Thêm Phòng");
		btnThemPhong.setForeground(Color.WHITE);
		btnThemPhong.setFont(new Font("Arial", Font.BOLD, 16));
		btnThemPhong.setBorder(new RoundedBorder(60));
		btnThemPhong.setBackground(new Color(33, 167, 38));
		btnThemPhong.setBounds(35, 330, 250, 35);
		getContentPane().add(btnThemPhong);

		// thêm sự kiện button
		btnChuyenPhong.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnThemDV.addActionListener(this);
		btnThemPhong.addActionListener(this);


		Action chuyenPhongAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btnChuyenPhong.doClick();
			}
		};

		Action themDVAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btnThemDV.doClick();
			}
		};

		Action thanhToanAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btnThanhToan.doClick();
			}
		};
		// Lấy InputMap và ActionMap của JPanel
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), "themDv");
		actionMap.put("themDv", themDVAction);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "chuyenPhong");
		actionMap.put("chuyenPhong", chuyenPhongAction);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "thanhToan");
		actionMap.put("thanhToan", thanhToanAction);

	}


	// hàm cập nhật các Jlabel góc phải
	public void updateLabel(String newText) {
		lblPhong_1.setText(newText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnChuyenPhong)) {
			try {
				dialog_ChuyenPhong = new Dialog_ChuyenPhong(lblPhong_1.getText(), lblSoNguoi_1.getText());
			} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dialog_ChuyenPhong.setModal(true);
			dialog_ChuyenPhong.setVisible(true);
			dispose();
		}

		if (o.equals(btnThemDV)) {
			try {
				String mnv = "";
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
				dialog_ThemDichVu = new Dialog_ThemDichVu(lblTenKH_1.getText(), mnv,
						lblPhong_1.getText());
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dialog_ThemDichVu.setModal(true);
			dialog_ThemDichVu.setVisible(true);
			dispose();
		}
		if (o.equals(btnThanhToan)) {
			try {
				if(tempTT_dao.getAllTemp().size() == 0) {
					try {
						ChiTietHoaDon cthd_hienTaiCuaPhong = null;
						List<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong_1.getText().trim());
						for (ChiTietHoaDon cthd : dsCTHD) {
							cthd_hienTaiCuaPhong = cthd;
						}
						HoaDonDatPhong hd = null;
						hd = hddp_Service.getHoaDonTheoMaHoaDon(cthd_hienTaiCuaPhong.getHoaDon().getMaHoaDon());
						
						List<Phong> dsPhongTheoMaHoaDon = p_Service.getPhongTheoMaCTHD(hd.getMaHoaDon());
						if(dsPhongTheoMaHoaDon.size() == 1) {
							DataManager.setMaHD_trongDSThanhToan(hd.getMaHoaDon());
							dialog_ThanhToan = new Dialog_ThanhToan(lblPhong_1.getText());
							dialog_ThanhToan.setModal(true);
							dialog_ThanhToan.setVisible(true);
							dispose();
						}else {
						
							TempThanhToan tmp = new TempThanhToan(p.getMaPhong());
							tempTT_dao.addTemp(tmp);
							JOptionPane.showMessageDialog(this, "Phòng " + p.getMaPhong() + " được thêm vào danh sách thanh toán thành công.");
							
							datPhong.txtMaPhong.setText(p.getMaPhong());
							datPhong.txtMaPhong.setForeground(Color.white);
							
							int i = 0;
							for(TempPhongBiChuyen tm_Chuyen : tempChuyen_dao.getAllTemp()) {
								if(tm_Chuyen.getMaPhongMoi().equals(lblPhong_1.getText())){
									i++;
//								System.out.println(i);
								}
							}
							
							if(i > 0) {
								ChiTietHoaDon cthd_ht = null;
								List<ChiTietHoaDon> dsCTHD_ht = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong_1.getText());
								for (ChiTietHoaDon cthd : dsCTHD_ht) {
									cthd_ht = cthd;
								}
								HoaDonDatPhong hd_ht = hddp_Service.getHoaDonTheoMaHoaDon(cthd_ht.getHoaDon().getMaHoaDon());
								
								String maCt = "";
								ArrayList<TempPhongBiChuyen> ds_PhongBiChuyen = tempChuyen_dao.getAllTemp();
								for(TempPhongBiChuyen tm : ds_PhongBiChuyen) {
									List<ChiTietHoaDon> cthd_BiChuyen = cthd_dao.getChiTietHoaDonTheoMaPhong(tm.getMaPhongBiChuyen());
									for(ChiTietHoaDon ct : cthd_BiChuyen) {
										maCt = ct.getHoaDon().getMaHoaDon();
									}
									if(maCt.equals(hd_ht.getMaHoaDon()) && tm.getMaPhongMoi().equals(lblPhong_1.getText())) {
										TempThanhToan tmp2 = new TempThanhToan(tm.getMaPhongBiChuyen());
										tempTT_dao.addTemp(tmp2);
									}
//								System.out.println(tm.getMaPhong());
								}
							
							}
							dispose();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
				}else {
					try {
						int i = 0;
						for(TempPhongBiChuyen tm_Chuyen : tempChuyen_dao.getAllTemp()) {
							if(tm_Chuyen.getMaPhongMoi().equals(lblPhong_1.getText())){
								i++;
//							System.out.println(i);
							}
						}
						
						if(i > 0) {
							ChiTietHoaDon cthd_ht = null;
							List<ChiTietHoaDon> dsCTHD_ht = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong_1.getText());
							for (ChiTietHoaDon cthd : dsCTHD_ht) {
								cthd_ht = cthd;
							}
							HoaDonDatPhong hd_ht = hddp_Service.getHoaDonTheoMaHoaDon(cthd_ht.getHoaDon().getMaHoaDon());

							String maCt = "";
							ArrayList<TempPhongBiChuyen> ds_PhongBiChuyen = tempChuyen_dao.getAllTemp();
							for(TempPhongBiChuyen tm : ds_PhongBiChuyen) {
								List<ChiTietHoaDon> cthd_BiChuyen = cthd_dao.getChiTietHoaDonTheoMaPhong(tm.getMaPhongBiChuyen());
								for(ChiTietHoaDon ct : cthd_BiChuyen) {
									maCt = ct.getHoaDon().getMaHoaDon();
								}
								if(maCt.equals(hd_ht.getMaHoaDon()) && tm.getMaPhongMoi().equals(lblPhong_1.getText())) {
									TempThanhToan tmp2 = new TempThanhToan(tm.getMaPhongBiChuyen());
									tempTT_dao.addTemp(tmp2);
								}
							}
						}
						
						ChiTietHoaDon cthd_hienTaiCuaPhong = null;
						List<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong_1.getText().trim());
						for (ChiTietHoaDon cthd : dsCTHD) {
							cthd_hienTaiCuaPhong = cthd;
						}
						
						int flag = 0;
						ChiTietHoaDon cthd_hienTaiTemp = null;
						for(TempThanhToan tmp : tempTT_dao.getAllTemp()) {
							List<ChiTietHoaDon> dsCTHDTemp = cthd_dao.getChiTietHoaDonTheoMaPhong(tmp.getMaPhong().trim());
							for (ChiTietHoaDon cthd : dsCTHDTemp) {
								cthd_hienTaiTemp = cthd;
							}
							if (cthd_hienTaiCuaPhong.getHoaDon().getMaHoaDon().equals(cthd_hienTaiTemp.getHoaDon().getMaHoaDon())) {
								flag = 1;
								break;
							}
						}
						
						if(flag == 1) {
							TempThanhToan tmp = new TempThanhToan(p.getMaPhong());
							tempTT_dao.addTemp(tmp);
							JOptionPane.showMessageDialog(this, "Phòng " + p.getMaPhong() + " được thêm vào danh sách thanh toán thành công.");
							
							dispose();
						}else if(flag == 0){
							JOptionPane.showMessageDialog(null, "Phòng này không nằm trong cùng 1 hóa đơn đặt phòng của khách hàng với phòng trước đó!!");
						}
					
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btnThemPhong)) {
			try {
				tmp_dao.deleteALLTempDatPhong();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			TempDatPhong tmp = null;
			KhachHang kh = null;
			try {
				kh = kh_dao.getKhachHangTheoMaKH(pdp_of_room.getKhachHang().getMaKhachHang());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			List<PhieuDatPhong> DsPDP_Tmp = null;
			try {
				DsPDP_Tmp = pdp_Service.getPhieuDatPhongTheoMaKH(kh.getMaKhachHang());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// lấy ra danh sách phiếu đặt phòng mới nhất của khách hàng nếu bị trùng phòng
			ArrayList<PhieuDatPhong> DsPDP = new ArrayList<PhieuDatPhong>();
			PhieuDatPhong pdp_tmp = new PhieuDatPhong();
			for (PhieuDatPhong pdp1 : DsPDP_Tmp) {
				int chk = 0;
				if (DsPDP.size() != 0) {
					for (PhieuDatPhong pdp2 : DsPDP) {
						if (pdp2.getPhong().getMaPhong().equals(pdp1.getPhong().getMaPhong())) {
							chk = 1;
							pdp_tmp = pdp2;
						}
					}
				}
				if (chk == 1) {
					DsPDP.remove(pdp_tmp);
					chk = 0;
				}
				DsPDP.add(pdp1);
			}

			PhieuDatPhong pdp_PhongHT = null;
			for (PhieuDatPhong pdp : DsPDP) {
				if (pdp.getPhong().getMaPhong().equals(maP))
					pdp_PhongHT = pdp;
			}

			String maHD_PhongHT = null;
			try {
				maHD_PhongHT = hddp_Service.getHoaDonDatPhongTheoMaPDP(pdp_PhongHT.getMaPhieu()).getMaHoaDon();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (PhieuDatPhong pdp : DsPDP) {
				if(pdp.getPhong().getMaPhong().equals(maP)) {
					Phong p = null;
					try {
						p = p_Service.getPhongTheoMaPhong(pdp.getPhong().getMaPhong());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					HoaDonDatPhong hd = null;
					try {
						hd = hddp_Service.getHoaDonDatPhongTheoMaPDP(pdp.getMaPhieu());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung && hd.getMaHoaDon().equals(maHD_PhongHT)) {
						tmp = new TempDatPhong(pdp.getPhong().getMaPhong(), pdp.getSoNguoiHat());
						try {
							tmp_dao.addTemp(tmp);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
			DataManager.setSoDienThoaiKHDat(kh.getSoDienThoai());
			try {
				dialog_DatPhongTrong_2 = new Dialog_DatPhongTrong_2(lblPhong_1.getText(), p, lp,
						pdp_of_room.getSoNguoiHat(), trangChu);
			} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			JOptionPane.showMessageDialog(this, "Vui lòng chọn thêm phòng cần đặt");
		}
	}
}
