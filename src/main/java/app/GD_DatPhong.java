package app;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import entity.Enum_TrangThai;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.TempDatPhong;
import entity.TempThanhToan;
import dao.ClientConnectionService;
import dao.KhachHangServices;
import dao.LoaiPhongServices;
import dao.PhieuDatPhongService;
import dao.PhongService;
import dao.TempDatPhongServices;
import dao.TempThanhToanServices;

public class GD_DatPhong extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private final GD_TrangChu trangChu;
	private static final long serialVersionUID = 1L;
	private final JButton btnUser;
	private final JComboBox<String> comboBox_TrangThai;
	private final JComboBox<String> comboBox_LoaiPhong;
	private final JTextField txtSoNguoi;
	public JTextField txtMaPhong;
	private final JButton btnTimKiemPDP;
	private final JButton btnLamMoi;
	private final JButton btnTimKiem;
	private final JScrollPane scrollPane_Phong;
	private final JPanel panel_3;
	private final JPanel outerPanel;
	private final JLabel lbl_iconPhongVIP;
	private final JLabel lbl_iconPhongsuaChua;
	private final JLabel lbl_iconPhongCho;
	private final JLabel lbl_iconPhongTrong;
	private String mnv = "";

	private final Dialog_User dialog_user = new Dialog_User();
	private Dialog_HienThiPhong dialog_htPhong;
	private Dialog_PhongDangSD dialog_PhongDangSD;
	private Dialog_PhongCho dialog_PhongCho;
	private Dialog_DatPhongCho dialog_DatPhongCho;
	private int sizeDSPhong;
	private PhongService p_Service;
	LoaiPhongServices lp_dao;
	private JButton btnPhong;
	ArrayList<JButton> btnPhongList = new ArrayList<>();
	private final JPanel panel_ChuaPhong;
	private final JPanel panel;
	private Dialog_HienThiPhongSuaChua dialog_htPhongSuaChua;
	private Dialog_TimPhieuDatPhong dialog_TimPhieuDatPhong;
	private final JButton btnBackToBook;
	private final TempDatPhongServices tmp_dao;
	private Dialog_DatPhongTrong_2 dialog_DatPhongTrong_2;
	private int sizeDSTmp;
	Font font2 = new Font("Arial", Font.BOLD, 18); // thuộc tính
	Font font3 = new Font("Arial", Font.PLAIN, 18); // jtexfield
	private final ImageIcon resizedIcon_phongtrong;
	private final ImageIcon resizedIcon_phongsd;
	private final ImageIcon resizedIcon_phongcho;
	private final ImageIcon resizedIcon_phongsua;
	private final ImageIcon resizedIcon_phongtrongvip;
	private final ImageIcon resizedIcon_phongsdvip;
	private final ImageIcon resizedIcon_phongchovip;
	private final ImageIcon resizedIcon_phongsuavip;
	private final JButton btnBackThanhToan;
	private final TempThanhToanServices tempTT_dao;
	private int sizeDSTemp_TT;
	private Dialog_ThanhToan dialog_ThanhToan;
	private final JButton btnBackHuyThanhToan;
	private final PhieuDatPhongService pdp_Service;
	private KhachHang kh = new KhachHang();
	private final KhachHangServices kh_dao;
	Timer timerChayThongBao;
	private final JButton btnBackPhongCho;
	private InetAddress ip;
	

	/**
	 * Create the panel.
	 * 
	 * @throws RemoteException
	 * @throws UnknownHostException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	private ClientConnectionService clientConnectionService;
	public GD_DatPhong(GD_TrangChu trangChu) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		ip = InetAddress.getLocalHost();
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL() + "loaiPhongServices");
		kh_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
		pdp_Service = (PhieuDatPhongService) Naming.lookup(DataManager.getRmiURL() + "phieuDatPhongServices");
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		tmp_dao = (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "tempDatPhongServices");
		tempTT_dao = (TempThanhToanServices) Naming.lookup(DataManager.getRmiURL() + "tempThanhToanServices");
		
		this.setSize(1080, 730);
		this.trangChu = trangChu;
		setLayout(null);
		// ---gốc chứa all panel
		// con--------------------------********************************************************************
		panel = new JPanel();
		panel.setBounds(0, 0, 1080, 730);
		panel.setBackground(Color.decode("#FAFAFF"));
		add(panel);
		panel.setLayout(null);
		// ---gốc
		// 1--------------------------***********************************************************************
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 1078, 60);
		panel_1.setBackground(new Color(187, 231, 252));
		panel_1.setLayout(null);
		JLabel lblTieuDe = new JLabel("ĐẶT PHÒNG");
		// căn giữa title
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setVerticalAlignment(SwingConstants.CENTER);

		// Đặt kích thước và tọa độ cho lblTitle
		int labelWidth = 500; // Thay đổi kích thước theo ý muốn
		int labelHeight = 40; // Thay đổi kích thước theo ý muốn
		int labelX = (panel_1.getWidth() - labelWidth) / 2; // Căn giữa theo chiều ngang
		int labelY = (panel_1.getHeight() - labelHeight) / 2; // Căn giữa theo chiều dọc
		lblTieuDe.setBounds(labelX, labelY, labelWidth, labelHeight);
		panel_1.add(lblTieuDe);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 25));
		panel.add(panel_1);
		// ---nút user
		btnUser = new JButton();
		btnUser.setBackground(Color.decode("#B5E6FB"));
		btnUser.setBorderPainted(false);
		btnUser.setIcon(new ImageIcon("icon\\icon_profile.png"));
		btnUser.setBounds(1020, 7, 45, 45);
		ImageIcon iconProfile = new ImageIcon("icon\\icon_profile.png");
		iconProfile = new ImageIcon(iconProfile.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		btnUser.setIcon(iconProfile);
		panel_1.add(btnUser);

		// ---gốc
		// 2----------------------------------****************************************************************
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.menu);
		panel_2.setBounds(0, 60, 1080, 103);
		panel.add(panel_2);
		panel_2.setLayout(null);
		// --- lbl và combox trạng thái
		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(font2);
		lblTrangThai.setBounds(10, 10, 100, 25);
		panel_2.add(lblTrangThai);

		comboBox_TrangThai = new JComboBox<String>();
		comboBox_TrangThai.setBackground(Color.WHITE);
		comboBox_TrangThai.setFont(font3);
		comboBox_TrangThai.setModel(
				new DefaultComboBoxModel<String>(new String[] { "", "Trống", "Chờ", "Đang sử dụng", "Đang sửa chữa" }));
		comboBox_TrangThai.setBounds(130, 10, 212, 30);
		panel_2.add(comboBox_TrangThai);

		JLabel lblLoiPhng = new JLabel("Loại phòng");
		lblLoiPhng.setFont(font2);
		lblLoiPhng.setBounds(10, 60, 150, 25);
		panel_2.add(lblLoiPhng);

		comboBox_LoaiPhong = new JComboBox<String>();
		comboBox_LoaiPhong.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Phòng VIP", "Phòng thường" }));
		comboBox_LoaiPhong.setFont(font3);
		comboBox_LoaiPhong.setBackground(Color.WHITE);
		comboBox_LoaiPhong.setBounds(130, 60, 212, 30);
		panel_2.add(comboBox_LoaiPhong);

		// ---lbl số người và mã phòng & txt
		JLabel lblSoNguoi = new JLabel("Số người");
		lblSoNguoi.setFont(font2);
		lblSoNguoi.setBounds(380, 10, 100, 25);
		panel_2.add(lblSoNguoi);

		JLabel lblMaPhong = new JLabel("Mã phòng");
		lblMaPhong.setFont(font2);
		lblMaPhong.setBounds(380, 60, 100, 30);
		panel_2.add(lblMaPhong);

		txtSoNguoi = new JTextField();
		txtSoNguoi.setFont(font3);
		txtSoNguoi.setBounds(509, 10, 102, 30);
		panel_2.add(txtSoNguoi);
		txtSoNguoi.setColumns(10);

		txtMaPhong = new JTextField();
		txtMaPhong.setFont(font3);
		txtMaPhong.setColumns(10);
		txtMaPhong.setBounds(509, 60, 102, 30);
		panel_2.add(txtMaPhong);

		// --- cuối góc phải là 3 nút jbutton
		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setIcon(new ImageIcon("icon\\Research_icon.png"));
		btnTimKiem.setBounds(672, 10, 180, 40);
		btnTimKiem.setBorder(new RoundedBorder(5));
		btnTimKiem.setBackground(new Color(13, 153, 255, 255));
		panel_2.add(btnTimKiem);

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setIcon(new ImageIcon("icon\\Refresh_icon.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBounds(879, 10, 180, 40);
		btnLamMoi.setBorder(new RoundedBorder(5));
		btnLamMoi.setBackground(new Color(112, 210, 103, 255));
		panel_2.add(btnLamMoi);

		btnTimKiemPDP = new JButton("Tìm phiếu đặt phòng");
		btnTimKiemPDP.setIcon(new ImageIcon("icon\\Research_icon.png"));
		btnTimKiemPDP.setForeground(Color.WHITE);
		btnTimKiemPDP.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiemPDP.setBounds(672, 55, 387, 40);
		btnTimKiemPDP.setBorder(new RoundedBorder(5));
		btnTimKiemPDP.setBackground(new Color(255, 180, 0, 255));

		panel_2.add(btnTimKiemPDP);

		ImageIcon phongtrong = new ImageIcon("icon\\phongtrong.png");
		Image originalImage_phongtrong = phongtrong.getImage();
		Image resizedImage_phongtrong = originalImage_phongtrong.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongtrong = new ImageIcon(resizedImage_phongtrong);
		//		panel_ChuaPhong.setLayout(null);
		ImageIcon phongsd = new ImageIcon("icon\\phongsd.png");
		Image originalImage_phongsd = phongsd.getImage();
		Image resizedImage_phongsd = originalImage_phongsd.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongsd = new ImageIcon(resizedImage_phongsd);

		ImageIcon phongcho = new ImageIcon("icon\\phongcho.png");
		Image originalImage_phongcho = phongcho.getImage();
		Image resizedImage_phongcho = originalImage_phongcho.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongcho = new ImageIcon(resizedImage_phongcho);

		ImageIcon phongsua = new ImageIcon("icon\\phongsua.png");
		Image originalImage_phongsua = phongsua.getImage();
		Image resizedImage_phongsua = originalImage_phongsua.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongsua = new ImageIcon(resizedImage_phongsua);

		// chỉnh sửa kích thước các icon VIP_______________________________
		ImageIcon phongtrongvip = new ImageIcon("icon\\trong_vip.png");
		Image originalImage_phongtrongvip = phongtrongvip.getImage();
		Image resizedImage_phongtrongvip = originalImage_phongtrongvip.getScaledInstance(60, 60,
				java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongtrongvip = new ImageIcon(resizedImage_phongtrongvip);

		ImageIcon phongsdvip = new ImageIcon("icon\\sd_vip.png");
		Image originalImage_phongsdvip = phongsdvip.getImage();
		Image resizedImage_phongsdvip = originalImage_phongsdvip.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongsdvip = new ImageIcon(resizedImage_phongsdvip);

		ImageIcon phongchovip = new ImageIcon("icon\\cho_vip.png");
		Image originalImage_phongchovip = phongchovip.getImage();
		Image resizedImage_phongchovip = originalImage_phongchovip.getScaledInstance(60, 60,
				java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongchovip = new ImageIcon(resizedImage_phongchovip);

		ImageIcon phongsuavip = new ImageIcon("icon\\sua_vip.png");
		Image originalImage_phongsuavip = phongsuavip.getImage();
		Image resizedImage_phongsuavip = originalImage_phongsuavip.getScaledInstance(60, 60,
				java.awt.Image.SCALE_SMOOTH);
		resizedIcon_phongsuavip = new ImageIcon(resizedImage_phongsuavip);

		// ---gốc
		// 3----------------------------------****************************************************************
		panel_3 = new JPanel();
		panel_3.setBackground(Color.PINK);
		panel_3.setBounds(0, 163, 1080, 500);
		panel.add(panel_3);
		panel_3.setLayout(null);

		scrollPane_Phong = new JScrollPane();
		scrollPane_Phong.setBounds(0, 0, 1080, 500);
		panel_3.add(scrollPane_Phong);

		// Tạo JPanel bên ngoài với kích thước cố định
		outerPanel = new JPanel();
		outerPanel.setPreferredSize(new Dimension(1040, calculateSize()));
		outerPanel.setLayout(null);

		panel_ChuaPhong = new JPanel();
		panel_ChuaPhong.setBounds(0, 0, 1059, calculateSize());
		panel_ChuaPhong.setBackground(new Color(244, 242, 255, 255));
		outerPanel.add(panel_ChuaPhong);
		panel_ChuaPhong.setLayout(null);
		
		List<Phong> dsPhong = getCurrentListRoom();
		loadData(dsPhong);
		setEnabledBtnDatPhong();
		// Tạo một Timer để gọi lại loadRoomList() mỗi 5000 milliseconds (5 giây)
		sizeDSTmp = tmp_dao.getAllTemp().size();
		sizeDSTemp_TT = tempTT_dao.getAllTemp().size();
		sizeDSPhong = p_Service.getallPhongs().size();
		
		
		sizeDSPhong = 0;
		List<Phong> phongs = p_Service.getallPhongs();
		for (Phong p : phongs) {
			if (p.getTrangThai() != Enum_TrangThai.Da_xoa) {
				sizeDSPhong++;
			}
		}
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Phong> dsPhong = null;
				dsPhong = getCurrentListRoom();
				try {
					if (sizeDSTmp != tmp_dao.getAllTemp().size()) {
						sizeDSTmp = tmp_dao.getAllTemp().size();
						setEnabledBtnDatPhong();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				try {
					if (sizeDSTemp_TT != tempTT_dao.getAllTemp().size()) {
						try {
							sizeDSTemp_TT = tempTT_dao.getAllTemp().size();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						setEnabledBtnDatPhong();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				try {
					int i = 0;
					p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
					List<Phong> phongs = p_Service.getallPhongs();
					for (Phong p : phongs) {
						if (p.getTrangThai() != Enum_TrangThai.Da_xoa) {
							i++;
						}
					}
					if (sizeDSPhong != i) {
						sizeDSPhong = i;
						outerPanel.setPreferredSize(new Dimension(1040, calculateSize()));
						panel_ChuaPhong.setBounds(0, 0, 1059, calculateSize());
						loadData(dsPhong);
					}
				} catch (RemoteException | MalformedURLException | NotBoundException e1) {
					e1.printStackTrace();
				}

				Map<String, Boolean> loadData = null;
				try {
					loadData = clientConnectionService.getLoadData();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<String, String> mapIP_MSNV = null;
				try {
					mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
					if(mnv.equals(entry.getKey())){
						loadData(dsPhong);
						entry.setValue(false);
						try {
							clientConnectionService.setLoadData(loadData);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
					}
				}
				
				try {
					btnBackToBook.setEnabled(tmp_dao.getAllTemp().size() != 1);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				try {
					btnBackThanhToan.setEnabled(tempTT_dao.getAllTemp().size() != 0);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				try {
					btnBackHuyThanhToan.setEnabled(tempTT_dao.getAllTemp().size() != 0);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				btnBackPhongCho.setEnabled(!DataManager.getMaPhongDatCho().equals(""));

			}
		});

		//		Bắt đầu Timer
		timer.start();

		//
		Timer timerThongBao = new Timer(60000, new ActionListener() {

			private Date gioHienTai;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<PhieuDatPhong> dsMaPhongDatTruoc = new ArrayList<PhieuDatPhong>();
				try {
					dsMaPhongDatTruoc = pdp_Service.getMaPhongDatTruoc();
				} catch (RemoteException e1) {
				}
			
				if (dsMaPhongDatTruoc != null && !dsMaPhongDatTruoc.isEmpty()) {
					for (PhieuDatPhong pdp : dsMaPhongDatTruoc) {
						Phong p = null;
						try {
							p = p_Service.getPhongTheoMaPhong(pdp.getPhong().getMaPhong());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
							DateFormat dateFormatGio = new SimpleDateFormat("HH");
							gioHienTai = new Date();
							double gioHT = Double.parseDouble(dateFormatGio.format(gioHienTai));
							DateFormat dateFormatPhut = new SimpleDateFormat("mm");
							double phutHT = Double.parseDouble(dateFormatPhut.format(gioHienTai));

							LocalDateTime thoiGianCanTraPhong = pdp.getNgayGioNhanPhong().minusMinutes(30);
							double gioTraPhong = thoiGianCanTraPhong.getHour();
							double phutTraPhong = thoiGianCanTraPhong.getMinute();
							if (gioHT == gioTraPhong && phutHT < phutTraPhong && (phutTraPhong - phutHT == 5)) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " còn khoảng 5 phút nữa hết thời gian!");
							}
							if (gioHT < gioTraPhong && phutHT > phutTraPhong && (phutTraPhong - phutHT + 60) == 5) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " còn khoảng 5 phút nữa hết thời gian!");
							}
							if (gioHT == gioTraPhong && phutHT == phutTraPhong) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " đã hết thời gian vui lòng thanh toán");
							}
						} else if (p.getTrangThai() == Enum_TrangThai.Cho) {
							DateFormat dateFormatGio = new SimpleDateFormat("HH");
							gioHienTai = new Date();
							int gioHT = Integer.parseInt(dateFormatGio.format(gioHienTai));
							DateFormat dateFormatPhut = new SimpleDateFormat("mm");
							int phutHT = Integer.parseInt(dateFormatPhut.format(gioHienTai));

							int gioNhanPhong = pdp.getNgayGioNhanPhong().getHour();
							int phutNhanPhong = pdp.getNgayGioNhanPhong().getMinute();
							String mkh = pdp.getKhachHang().getMaKhachHang();
							try {
								kh = kh_dao.getKhachHangTheoMaKH(mkh);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							if (gioHT == gioNhanPhong && phutHT < phutNhanPhong && (phutNhanPhong - phutHT == 20)) {
								JOptionPane.showMessageDialog(null,
										"Phòng " + pdp.getPhong().getMaPhong()
										+ " Còn 20p nữa đến thời gian nhận phòng vui lòng liên hệ KH:"
										+ kh.getSoDienThoai());
							}
							if (gioHT < gioNhanPhong && phutHT > phutNhanPhong && (phutNhanPhong - phutHT + 60) == 20) {
								JOptionPane.showMessageDialog(null,
										"Phòng " + pdp.getPhong().getMaPhong()
										+ " Còn 20p nữa đến thời gian nhận phòng vui lòng liên hệ KH:"
										+ kh.getSoDienThoai());
							}
							if (gioHT == gioNhanPhong && phutHT == phutNhanPhong) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " Đã đến thời gian nhận phòng vui lòng kiểm tra");
							}

							if (gioHT == gioNhanPhong && phutHT > phutNhanPhong && phutHT - phutNhanPhong == 20) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " Đã qua 20p nhận phòng vui lòng liên hệ KH:" + kh.getSoDienThoai());
							}

							if (gioHT > gioNhanPhong && phutHT < phutNhanPhong && (phutHT - phutNhanPhong + 60) == 20) {
								JOptionPane.showMessageDialog(null, "Phòng " + pdp.getPhong().getMaPhong()
										+ " Đã qua 20p nhận phòng vui lòng kiểm tra");
							}
						}
					}
				} else {
					System.out.println("");
				}
			}
		});

		timerChayThongBao = new Timer(1000, new ActionListener() {
			private Date timeHienTai;
			private boolean shouldStop = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!shouldStop) {
					timeHienTai = new Date();
					DateFormat dateFormatGiay = new SimpleDateFormat("ss");
					String giayHT = dateFormatGiay.format(timeHienTai);

					if (giayHT.equals("00") && !DataManager.isTimerChayTB()) {
						timerThongBao.start();
						shouldStop = true;
						timerChayThongBao.stop();
					}
				}
			}
		});

		timerChayThongBao.start();

		// set size icon cho gốc 4
		ImageIcon phongtrong4 = new ImageIcon("icon\\phongtrong.png");
		Image originalImage_phongtrong4 = phongtrong4.getImage();
		Image resizedImage_phongtrong4 = originalImage_phongtrong4.getScaledInstance(30, 30,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_phongtrong4 = new ImageIcon(resizedImage_phongtrong4);
		panel_ChuaPhong.setLayout(null);
		ImageIcon phongsd4 = new ImageIcon("icon\\phongsd.png");
		Image originalImage_phongsd4 = phongsd4.getImage();
		Image resizedImage_phongsd4 = originalImage_phongsd4.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_phongsd4 = new ImageIcon(resizedImage_phongsd4);

		ImageIcon phongcho4 = new ImageIcon("icon\\phongcho.png");
		Image originalImage_phongcho4 = phongcho4.getImage();
		Image resizedImage_phongcho4 = originalImage_phongcho4.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_phongcho4 = new ImageIcon(resizedImage_phongcho4);

		ImageIcon phongsua4 = new ImageIcon("icon\\phongsua.png");
		Image originalImage_phongsua4 = phongsua4.getImage();
		Image resizedImage_phongsua4 = originalImage_phongsua4.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_phongsua4 = new ImageIcon(resizedImage_phongsua4);

		ImageIcon phongvip = new ImageIcon("icon\\phongvip.png");
		Image originalImage_phongvip = phongvip.getImage();
		Image resizedImage_phongvip = originalImage_phongvip.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_phongvip = new ImageIcon(resizedImage_phongvip);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(0, 690, 1080, 40);
		panel.add(panel_4);
		panel_4.setLayout(null);
		// lbl & icon phòng trống
		lbl_iconPhongTrong = new JLabel("Phòng Trống");
		lbl_iconPhongTrong.setIcon(resizedIcon_phongtrong4);
		lbl_iconPhongTrong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_iconPhongTrong.setBounds(15, 0, 140, 35);
		panel_4.add(lbl_iconPhongTrong);
		// lbl & icon phòng chờ
		lbl_iconPhongCho = new JLabel("Phòng Chờ");
		lbl_iconPhongCho.setIcon(resizedIcon_phongcho4);
		lbl_iconPhongCho.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_iconPhongCho.setBounds(180, 0, 140, 35);
		panel_4.add(lbl_iconPhongCho);
		// lbl & icon phòng đang sử dụng
		JLabel lbl_iconPhongSD = new JLabel("Phòng đang sử dụng");
		lbl_iconPhongSD.setIcon(resizedIcon_phongsd4);
		lbl_iconPhongSD.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_iconPhongSD.setBounds(340, 0, 200, 35);
		panel_4.add(lbl_iconPhongSD);
		// lbl & icon phòng sửa chữa
		lbl_iconPhongsuaChua = new JLabel("Phòng sửa chửa");
		lbl_iconPhongsuaChua.setIcon(resizedIcon_phongsua4);
		lbl_iconPhongsuaChua.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_iconPhongsuaChua.setBounds(560, 0, 170, 35);
		panel_4.add(lbl_iconPhongsuaChua);
		// lbl & icon phòng VIP
		lbl_iconPhongVIP = new JLabel("Phòng VIP");
		lbl_iconPhongVIP.setIcon(resizedIcon_phongvip);
		lbl_iconPhongVIP.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_iconPhongVIP.setBounds(850, 0, 140, 35);
		panel_4.add(lbl_iconPhongVIP);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.activeCaption);
		panel_5.setBounds(0, 663, 1080, 27);
		panel.add(panel_5);
		panel_5.setLayout(null);

		btnBackHuyThanhToan = new JButton("Hủy danh sách thanh toán");
		btnBackHuyThanhToan.setBorderPainted(false);
		btnBackHuyThanhToan.setForeground(Color.blue);
		btnBackHuyThanhToan.setFont(new Font("Tahoma", Font.ITALIC, 15));
		btnBackHuyThanhToan.setBounds(360, 3, 250, 20);
		btnBackHuyThanhToan.setBorder(new RoundedBorder(5));
		panel_5.add(btnBackHuyThanhToan);

		btnBackThanhToan = new JButton("Quay về thanh toán");
		btnBackThanhToan.setBorderPainted(false);
		btnBackThanhToan.setForeground(Color.blue);
		btnBackThanhToan.setFont(new Font("Tahoma", Font.ITALIC, 15));
		btnBackThanhToan.setBounds(620, 3, 200, 20);
		btnBackThanhToan.setBorder(new RoundedBorder(5));
		panel_5.add(btnBackThanhToan);

		btnBackToBook = new JButton("Quay về đặt phòng");
		btnBackToBook.setBorderPainted(false);
		btnBackToBook.setForeground(Color.red);
		btnBackToBook.setFont(new Font("Tahoma", Font.ITALIC, 15));
		btnBackToBook.setBounds(830, 3, 200, 20);
		btnBackToBook.setBorder(new RoundedBorder(5));
		panel_5.add(btnBackToBook);

		btnBackPhongCho = new JButton("Quay về đặt phòng chờ");
		btnBackPhongCho.setBorderPainted(false);
		btnBackPhongCho.setForeground(Color.red);
		btnBackPhongCho.setFont(new Font("Tahoma", Font.ITALIC, 15));
		btnBackPhongCho.setBounds(140, 3, 200, 20);
		btnBackPhongCho.setBorder(new RoundedBorder(5));
		panel_5.add(btnBackPhongCho);

		// thêm sự kiện
		btnUser.addActionListener(this);
		btnTimKiemPDP.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);

		// add sự kiện cho nút
		btnBackToBook.addActionListener(this);
		btnBackThanhToan.addActionListener(this);
		btnBackHuyThanhToan.addActionListener(this);
		btnBackPhongCho.addActionListener(this);
	}

	public void loadData(List<Phong> listRoom) {
		// chỉnh sửa kích thước các icon thường______________________
		int i = 0;
		int x = 40;
		int y = -110;
		int w = 160;
		int h = 100;

		// Xóa sự kiện thêm trước đó
		for (JButton btn : btnPhongList) {
			btn.removeActionListener(this);
		}

		// Xóa phòng thêm trước đó
		panel_ChuaPhong.removeAll();
		panel_ChuaPhong.revalidate();
		panel_ChuaPhong.repaint();
		try {
			for (Phong p : listRoom) {
				if (p.getTrangThai() != Enum_TrangThai.Da_xoa) {

					if (i % 5 == 0) {
						y += 130;
						x = 40;
					}
					i++;

					btnPhong = new JButton("Phòng " + p.getMaPhong());
					btnPhongList.add(btnPhong);
					LoaiPhong lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
					btnPhong.setBounds(x, y, w, h);
					x += 210;
					btnPhong.setBackground(new Color(181, 230, 251, 255));
					if (lp.getTenLoaiPhong().equals("Phòng VIP")) {
						if (p.getTrangThai() == Enum_TrangThai.Trong)
							btnPhong.setIcon(resizedIcon_phongtrongvip);
						if (p.getTrangThai() == Enum_TrangThai.Cho)
							btnPhong.setIcon(resizedIcon_phongchovip);
						if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung)
							btnPhong.setIcon(resizedIcon_phongsdvip);
						if (p.getTrangThai() == Enum_TrangThai.Dang_sua_chua)
							btnPhong.setIcon(resizedIcon_phongsuavip);
					}
					if (lp.getTenLoaiPhong().equals("Phòng thường")) {
						if (p.getTrangThai() == Enum_TrangThai.Trong)
							btnPhong.setIcon(resizedIcon_phongtrong);
						if (p.getTrangThai() == Enum_TrangThai.Cho)
							btnPhong.setIcon(resizedIcon_phongcho);
						if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung)
							btnPhong.setIcon(resizedIcon_phongsd);
						if (p.getTrangThai() == Enum_TrangThai.Dang_sua_chua)
							btnPhong.setIcon(resizedIcon_phongsua);
					}

					btnPhong.setVerticalTextPosition(SwingConstants.BOTTOM);
					if (!p.getTrangThai().equals(Enum_TrangThai.Da_xoa)) {
						panel_ChuaPhong.add(btnPhong);
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// Thêm lại sự kiện cho các phòng
		for (JButton btn : btnPhongList) {
			btn.addActionListener(this);
		}

		// Hiển thị lại giao diện
		panel_ChuaPhong.revalidate();
		panel_ChuaPhong.repaint();
		scrollPane_Phong.setViewportView(outerPanel);

		setEnabledBtnDatPhong();
	}

	private void setEnabledBtnDatPhong() {
		List<JButton> btnPhongListCopy = new ArrayList<>(btnPhongList);
		for (JButton btn : btnPhongListCopy) {
			boolean kiemTra = true;
			String soPhong = btn.getText().replace("Phòng ", "");
			try {
				for (TempDatPhong tmp : tmp_dao.getAllTemp()) {
					Phong tmpP = p_Service.getPhongTheoMaPhong(tmp.getMaPhong());
					if (soPhong.equals(tmp.getMaPhong()) && tmpP.getTrangThai() == Enum_TrangThai.Trong)
						kiemTra = false;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				for (TempThanhToan tmp : tempTT_dao.getAllTemp()) {
					Phong tmpP = p_Service.getPhongTheoMaPhong(tmp.getMaPhong());
					if (soPhong.equals(tmp.getMaPhong()) && tmpP.getTrangThai() == Enum_TrangThai.Dang_su_dung)
						kiemTra = false;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			btn.setEnabled(kiemTra);
		}
	}

	private int calculateSize() throws RemoteException {
		int i = 0;
		
//		try {
//			i = p_Service.getallPhongs().size();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		List<Phong> list = p_Service.getallPhongs();
		for (Phong p : list) {
			if (p.getTrangThai() != Enum_TrangThai.Da_xoa) {
				i++;
			}
		}
		
		if (i <= 15) {
			return 498;
		} else if (i <= 20) {
			return 540;
		} else {
			return 540 + (((i - 1) / 5) - 3) * 130;
		}
	}
	
	private List<Phong> getCurrentListRoom() {
		try {
			PhongService phongService = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
			return phongService.getallPhongs();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	private void loadTimKiem(List<Phong> DSPhong) throws RemoteException {
		// chỉnh sửa kích thước các icon thường______________________
		int i = 0;
		int x = 40;
		int y = -110;
		int w = 160;
		int h = 100;

		// Xóa sự kiện thêm trước đó
		for (JButton btn : btnPhongList) {
			btn.removeActionListener(this);
		}

		// Xóa phòng thêm trước đó
		panel_ChuaPhong.removeAll();
		panel_ChuaPhong.revalidate();
		panel_ChuaPhong.repaint();

		for (Phong p : DSPhong) {
			if (i % 5 == 0) {
				y += 130;
				x = 40;
			}
			i++;

			btnPhong = new JButton("Phòng " + p.getMaPhong());
			btnPhongList.add(btnPhong);
			LoaiPhong lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
			btnPhong.setBounds(x, y, w, h);
			x += 210;
			btnPhong.setBackground(new Color(181, 230, 251, 255));
			if (lp.getTenLoaiPhong().equals("Phòng VIP")) {
				if (p.getTrangThai() == Enum_TrangThai.Trong)
					btnPhong.setIcon(resizedIcon_phongtrongvip);
				if (p.getTrangThai() == Enum_TrangThai.Cho)
					btnPhong.setIcon(resizedIcon_phongchovip);
				if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung)
					btnPhong.setIcon(resizedIcon_phongsdvip);
				if (p.getTrangThai() == Enum_TrangThai.Dang_sua_chua)
					btnPhong.setIcon(resizedIcon_phongsuavip);
			}
			if (lp.getTenLoaiPhong().equals("Phòng thường")) {
				if (p.getTrangThai() == Enum_TrangThai.Trong)
					btnPhong.setIcon(resizedIcon_phongtrong);
				if (p.getTrangThai() == Enum_TrangThai.Cho)
					btnPhong.setIcon(resizedIcon_phongcho);
				if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung)
					btnPhong.setIcon(resizedIcon_phongsd);
				if (p.getTrangThai() == Enum_TrangThai.Dang_sua_chua)
					btnPhong.setIcon(resizedIcon_phongsua);
			}

			btnPhong.setVerticalTextPosition(SwingConstants.BOTTOM);
			panel_ChuaPhong.add(btnPhong);
		}

		// Thêm lại sự kiện cho các phòng
		for (JButton btn : btnPhongList) {
			btn.addActionListener(this);
		}

		// Hiển thị lại giao diện
		panel_ChuaPhong.revalidate();
		panel_ChuaPhong.repaint();
		scrollPane_Phong.setViewportView(outerPanel);
	}

	private void Tim() throws RemoteException {
		thoat: if (btnTimKiem.getText().equals("Tìm kiếm")) {
			List<Phong> dsPhong = new ArrayList<Phong>();
			int soNguoi = 0;
			String loaiPhong = comboBox_LoaiPhong.getSelectedItem().toString();
			String trangThai = comboBox_TrangThai.getSelectedItem().toString();
			if (trangThai.equals("Đang sử dụng"))
				trangThai = "Dang_su_dung";
			else if (trangThai.equals("Đang sửa chữa"))
				trangThai = "Dang_sua_chua";
			else if (trangThai.equals("Trống"))
				trangThai = "Trong";
			else if (trangThai.equals("Chờ"))
				trangThai = "Cho";

			if (!txtSoNguoi.getText().trim().equals("")) {
				try {
					soNguoi = Integer.parseInt(txtSoNguoi.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Số người hát phải là số nguyên dương");
					break thoat;
				}
				if (soNguoi < 0) {
					JOptionPane.showMessageDialog(this, "Số người hát phải là số nguyên dương");
					break thoat;
				}
			}

			if (txtMaPhong.getText().trim().equals("") && txtSoNguoi.getText().trim().equals("")
					&& comboBox_LoaiPhong.getSelectedItem().equals("")
					&& comboBox_TrangThai.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn chưa nhập bất kì thông tin nào để tìm");
			} else {
				if (!txtMaPhong.getText().trim().equals("")) {
					try {
						if (p_Service.getPhongTheoMaPhong(txtMaPhong.getText()) != null) {
							try {
								dsPhong.add(p_Service.getPhongTheoMaPhong(txtMaPhong.getText()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (!comboBox_LoaiPhong.getSelectedItem().toString().equals("")
						&& comboBox_TrangThai.getSelectedItem().toString().equals("")) {
					try {
						dsPhong = p_Service.getPhongTKTheoTenLoaiPhong(loaiPhong, soNguoi);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (comboBox_LoaiPhong.getSelectedItem().toString().equals("")
						&& !comboBox_TrangThai.getSelectedItem().toString().equals("")) {
					try {
						dsPhong = p_Service.getPhongTKTheoTrangThai(trangThai, soNguoi);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (!comboBox_LoaiPhong.getSelectedItem().toString().equals("")
						&& !comboBox_TrangThai.getSelectedItem().toString().equals("")) {
					try {
						dsPhong = p_Service.getPhongTKTheoTenLoaiPhongVaTrangThai(loaiPhong, trangThai, soNguoi);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (comboBox_LoaiPhong.getSelectedItem().toString().equals("")
						&& comboBox_TrangThai.getSelectedItem().toString().equals("") && soNguoi != 0) {
					try {
						dsPhong = p_Service.getPhongTKTheoSoNguoiHat(soNguoi);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

				// Kiểm tra điều kiện tìm thấy hay không
				if (dsPhong != null && dsPhong.size() != 0) {
					loadTimKiem(dsPhong);
					btnTimKiem.setText("Hủy tìm");
				} else {
					JOptionPane.showMessageDialog(this, "Không tìm thấy phòng phù hợp");
				}
			}
		} else {
			List<Phong> dsPhong = getCurrentListRoom();
			loadData(dsPhong);
			btnTimKiem.setText("Tìm kiếm");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnUser)) {
			dialog_user.setVisible(true);
		}
		if (o.equals(btnTimKiem)) {
			try {
				Tim();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTimKiemPDP)) {
			try {
				dialog_TimPhieuDatPhong = new Dialog_TimPhieuDatPhong();
			} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
				e1.printStackTrace();
			}
			dialog_TimPhieuDatPhong.setModal(true);
			dialog_TimPhieuDatPhong.setVisible(true);

		}
		if (o.equals(btnBackToBook)) {
			try {
				if (tmp_dao.getAllTemp().size() == 1) {
					JOptionPane.showMessageDialog(this, "Chưa phòng nào được thêm vào danh sách đặt");
				} else {
					DataManager.setLoadDV(true);
					try {
						dialog_DatPhongTrong_2 = new Dialog_DatPhongTrong_2(TOOL_TIP_TEXT_KEY, null, null, 0, trangChu);
					} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
						e1.printStackTrace();
					}
					dialog_DatPhongTrong_2.setVisible(true);
				}
			} catch (HeadlessException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnBackThanhToan)) {
			try {
				if (tempTT_dao.getAllTemp().size() == 0) {
					JOptionPane.showMessageDialog(this, "Chưa phòng nào được thêm vào danh sách thanh toán");
				} else {
					try {
						dialog_ThanhToan = new Dialog_ThanhToan(txtMaPhong.getText());
					} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
						e1.printStackTrace();
					}
					dialog_ThanhToan.setVisible(true);
				}
			} catch (HeadlessException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnBackHuyThanhToan)) {
			try {
				if (tempTT_dao.getAllTemp().size() == 0) {
					JOptionPane.showMessageDialog(this, "Chưa phòng nào được thêm vào danh sách thanh toán");
				} else {
					tempTT_dao.deleteALLTempThanhToan();
				}
			} catch (HeadlessException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o instanceof JButton) {
			JButton clickedButton = (JButton) o;
			try {
				p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				e1.printStackTrace();
			}
			for (JButton btn : btnPhongList) {
				if (btn == clickedButton) {
					String maPhong = clickedButton.getText().replace("Phòng ", "");
					Phong p = null;
					try {
						p = p_Service.getPhongTheoMaPhong(maPhong);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					if (p.getTrangThai() == Enum_TrangThai.Trong) {
						try {
							dialog_htPhong = new Dialog_HienThiPhong(maPhong, trangChu);
						} catch (RemoteException | MalformedURLException | NotBoundException e1) {
							e1.printStackTrace();
						}
						dialog_htPhong.setVisible(true);
						return;
					}

					if (p.getTrangThai() == Enum_TrangThai.Cho) {
						try {
							dialog_PhongCho = new Dialog_PhongCho(maPhong, trangChu);
						} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
							e1.printStackTrace();
						}
						dialog_PhongCho.setVisible(true);
						break;
					}

					if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
						try {
							dialog_PhongDangSD = new Dialog_PhongDangSD(maPhong, this);
						} catch (RemoteException | MalformedURLException | NotBoundException e1) {
							e1.printStackTrace();
						}
						dialog_PhongDangSD.setModal(true);
						dialog_PhongDangSD.setVisible(true);
						return;
					}
					if (p.getTrangThai() == Enum_TrangThai.Dang_sua_chua) {

						try {
							dialog_htPhongSuaChua = new Dialog_HienThiPhongSuaChua(maPhong);
						} catch (RemoteException | MalformedURLException | NotBoundException e1) {
							e1.printStackTrace();
						}
						dialog_htPhongSuaChua.setModal(true);
						dialog_htPhongSuaChua.setVisible(true);
						return;
					}
					return;
				}
			}
		}
		
		
		if (o.equals(btnLamMoi)) {
			comboBox_LoaiPhong.setSelectedIndex(0);
			comboBox_TrangThai.setSelectedIndex(0);
			try {
				outerPanel.setPreferredSize(new Dimension(1040, calculateSize()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				panel_ChuaPhong.setBounds(0, 0, 1059, calculateSize());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<Phong> listRoom = getCurrentListRoom();
			loadData(listRoom);
			txtSoNguoi.setText("");
			txtMaPhong.setText("");
		}

	}
}
