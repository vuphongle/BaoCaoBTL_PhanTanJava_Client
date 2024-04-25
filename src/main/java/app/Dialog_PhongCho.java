package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import dao.ClientConnectionService;
import dao.KhachHangServices;
import dao.LoaiPhongServices;
import dao.PhieuDatPhongService;
import dao.PhongService;
import dao.TempDatPhongServices;
import entity.Enum_TrangThai;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.TempDatPhong;

public class Dialog_PhongCho extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel lblPhong;
    private final JLabel lblGia;
    private final JLabel lblThoiGianHat;
    private final JLabel lblSoNguoi;
    private final JLabel lblLoai;
    private final JLabel lblLoai_1;
    private final JLabel lblPhong_1;
    private final JLabel lblgia_1;
	private final JButton btnNhanPhong;
    private final JButton btn_HuyPhong;

	private final PhongService p_Service;
	private final LoaiPhongServices lp_dao;
	private final PhieuDatPhongService pdp_Service;
	private final KhachHangServices kh_dao;
	private Phong p;
	private LoaiPhong lp;
	private PhieuDatPhong pdp;
	private KhachHang kh;
	private final GD_TrangChu trangChu;

	private final JLabel lblThoiGianHat_1;

	private final DateTimePicker dateTimePicker;
	private final TimePickerSettings timeSettings;
	private final DatePickerSettings dateSettings;
	private final DatePickerSettings dateSettings_1;

	private final TimePickerSettings timeSettings_1;
	private final DateTimePicker dateTimePicker_1;
	private final JLabel lbl_KhachHang;
	private final JLabel lbl_KhachHang_1;
	private final JLabel lbl_SoNguoi_1;
	private final TempDatPhongServices tmp_dao;

	private LocalDateTime ngayGioDatPhong;
	private LocalDateTime ngayGioNhanPhong;

	private int songuoihat;
	private double dongia;
	private String donGiaFormatted, hotenKH;

	private Dialog_DatPhongTrong_2 dialog_DatPhongTrong_2;
	private Dialog_HienThiPhong dialog_HienThiPhong;
	private final JButton btnDatPhong;
	private InetAddress ip;
	private ClientConnectionService clientConnectionService;

	public Dialog_PhongCho(String maPhong, GD_TrangChu trangChu) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		ip = InetAddress.getLocalHost();
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		p_Service= (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL()+"loaiPhongServices");
		pdp_Service = (PhieuDatPhongService) Naming.lookup(DataManager.getRmiURL()+"phieuDatPhongServices");
		kh_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL()+"khachHangServices");
		tmp_dao= (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL()+"tempDatPhongServices");
		this.trangChu = trangChu;
		// kích thước giao diện
		getContentPane().setBackground(Color.WHITE);
		setSize(400, 465);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
		this.setIconImage(icon.getImage());
		getContentPane().setLayout(null);

		laydulieu(maPhong);

		// các lbl góc
		// trái-----------------------------------------------------------------------
		lblPhong = new JLabel("Phòng:");
		lblPhong.setBounds(5, 10, 100, 30);
		lblPhong.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblPhong);

		lblLoai = new JLabel("Loại:");
		lblLoai.setBounds(5, 50, 100, 30);
		lblLoai.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblLoai);

		lblSoNguoi = new JLabel("Số người:");
		lblSoNguoi.setBounds(5, 90, 100, 30);
		lblSoNguoi.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblSoNguoi);

		lblThoiGianHat = new JLabel("Ngày đặt phòng:");
		lblThoiGianHat.setBounds(5, 130, 134, 30);
		lblThoiGianHat.setFont(new Font("Arial", Font.BOLD, 16));
		getContentPane().add(lblThoiGianHat);

		lblGia = new JLabel("Giá phòng:");
		lblGia.setBounds(5, 210, 100, 30);
		lblGia.setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().add(lblGia);

		// các lbl góc
		// phải---------------------------------------------------------------------
		lblPhong_1 = new JLabel();
		lblPhong_1.setText(maPhong);
		lblPhong_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblPhong_1.setBounds(150, 10, 150, 30);
		getContentPane().add(lblPhong_1);

		lblLoai_1 = new JLabel();
		lblLoai_1.setText(lp.getTenLoaiPhong());
		lblLoai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblLoai_1.setBounds(150, 50, 150, 30);
		getContentPane().add(lblLoai_1);

		lblgia_1 = new JLabel();
		lblgia_1.setText(donGiaFormatted);
		lblgia_1.setBackground(Color.WHITE);
		lblgia_1.setForeground(Color.RED);
		lblgia_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblgia_1.setBounds(150, 210, 140, 30);
		getContentPane().add(lblgia_1);

		// nút
		// button---------------------------------------------------------------------------
		btnNhanPhong = new JButton("Nhận Phòng");
		btnNhanPhong.setBounds(40, 335, 300, 40);
		btnNhanPhong.setForeground(Color.WHITE);
		btnNhanPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btnNhanPhong.setBackground(new Color(33, 167, 38, 255));
		btnNhanPhong.setBorder(new RoundedBorder(60));
//		btnThemDV.setBorderPainted(false);
		getContentPane().add(btnNhanPhong);

		btn_HuyPhong = new JButton("Hủy Phòng");
		btn_HuyPhong.setBounds(40, 380, 300, 40);
		btn_HuyPhong.setForeground(Color.WHITE);
		btn_HuyPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_HuyPhong.setBackground(new Color(26, 147, 216, 255));
		// btnChuyenPhong.setBorderPainted(false);
		btn_HuyPhong.setBorder(new RoundedBorder(60));
		getContentPane().add(btn_HuyPhong);

		btnDatPhong = new JButton("Đặt Phòng");
		btnDatPhong.setForeground(Color.WHITE);
		btnDatPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btnDatPhong.setBorder(new RoundedBorder(60));
		btnDatPhong.setBackground(new Color(33, 167, 38));
		btnDatPhong.setBounds(40, 290, 300, 40);
		getContentPane().add(btnDatPhong);

		lbl_SoNguoi_1 = new JLabel();
		lbl_SoNguoi_1.setText(String.valueOf(songuoihat));
		lbl_SoNguoi_1.setFont(new Font("Arial", Font.BOLD, 15));
		lbl_SoNguoi_1.setBounds(150, 90, 120, 30);
		getContentPane().add(lbl_SoNguoi_1);

		lblThoiGianHat_1 = new JLabel("Ngày nhận phòng:");
		lblThoiGianHat_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblThoiGianHat_1.setBounds(5, 170, 143, 30);
		getContentPane().add(lblThoiGianHat_1);

		// hiển thị thời gian đặt phòng chờ

		dateSettings = new DatePickerSettings();
		dateSettings.setLocale(new Locale("vi", "VN")); // Set the locale to English
		dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd"); // Set the date format

		timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);

		dateTimePicker = new DateTimePicker(dateSettings, timeSettings);
		dateTimePicker.setDateTimePermissive(ngayGioDatPhong);
		dateTimePicker.getTimePicker().getComponentSpinnerPanel().setBounds(80, 0, 0, 25);
		dateTimePicker.getTimePicker().getComponentToggleTimeMenuButton().setBounds(65, 0, 30, 30);
		dateTimePicker.getTimePicker().getComponentTimeTextField().setBounds(0, 0, 64, 30);
		dateTimePicker.getDatePicker().getComponentToggleCalendarButton().setBounds(103, 0, 30, 30);
		dateTimePicker.getDatePicker().getComponentDateTextField().setBounds(0, 0, 104, 30);
		dateTimePicker.setSize(230, 30);
		dateTimePicker.setLocation(150, 130);
		dateTimePicker.getDatePicker().getComponentDateTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker.getTimePicker().getComponentTimeTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker.getTimePicker().getComponentToggleTimeMenuButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker.getDatePicker().getComponentToggleCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker.timePicker.setBounds(148, 0, 86, 30);
		dateTimePicker.datePicker.setBounds(0, 0, 143, 30);
		dateTimePicker.getTimePicker().setBounds(135, 0, 99, 30);
		dateTimePicker.getTimePicker().setLayout(null);
		dateTimePicker.getDatePicker().setBounds(0, 0, 134, 30);
		dateTimePicker.getDatePicker().setLayout(null);
		getContentPane().add(dateTimePicker);
		dateTimePicker.setLayout(null);

		// hiển thị thời gian nhận phòng chờ
		dateSettings_1 = new DatePickerSettings();
		dateSettings_1.setLocale(new Locale("vi", "VN")); // Set the locale to English
		dateSettings_1.setFormatForDatesCommonEra("yyyy-MM-dd"); // Set the date format
		timeSettings_1 = new TimePickerSettings();
		timeSettings_1.setDisplaySpinnerButtons(true);

		dateTimePicker_1 = new DateTimePicker(dateSettings_1, timeSettings_1);
		dateTimePicker_1.setDateTimePermissive(ngayGioNhanPhong);
		dateTimePicker_1.getTimePicker().getComponentSpinnerPanel().setBounds(80, 0, 0, 25);
		dateTimePicker_1.getTimePicker().getComponentToggleTimeMenuButton().setBounds(65, 0, 30, 30);
		dateTimePicker_1.getTimePicker().getComponentTimeTextField().setBounds(0, 0, 64, 30);
		dateTimePicker_1.getDatePicker().getComponentToggleCalendarButton().setBounds(103, 0, 30, 30);
		dateTimePicker_1.getDatePicker().getComponentDateTextField().setBounds(0, 0, 104, 30);
		dateTimePicker_1.setSize(230, 30);
		dateTimePicker_1.setLocation(150, 170);
		dateTimePicker_1.getDatePicker().getComponentDateTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker_1.getTimePicker().getComponentTimeTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker_1.getTimePicker().getComponentToggleTimeMenuButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker_1.getDatePicker().getComponentToggleCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker_1.timePicker.setBounds(148, 0, 86, 30);
		dateTimePicker_1.datePicker.setBounds(0, 0, 143, 30);
		dateTimePicker_1.getTimePicker().setBounds(135, 0, 99, 30);
		dateTimePicker_1.getTimePicker().setLayout(null);
		dateTimePicker_1.getDatePicker().setBounds(0, 0, 134, 30);
		dateTimePicker_1.getDatePicker().setLayout(null);

		getContentPane().add(dateTimePicker_1);
		dateTimePicker_1.setLayout(null);

		lbl_KhachHang = new JLabel("Khách hàng:");
		lbl_KhachHang.setFont(new Font("Arial", Font.BOLD, 18));
		lbl_KhachHang.setBounds(5, 250, 115, 30);
		getContentPane().add(lbl_KhachHang);

		lbl_KhachHang_1 = new JLabel();
		lbl_KhachHang_1.setText(hotenKH);
		lbl_KhachHang_1.setFont(new Font("Arial", Font.BOLD, 15));
		lbl_KhachHang_1.setBounds(150, 250, 150, 30);
		getContentPane().add(lbl_KhachHang_1);

		// thêm sự kiện button
		btn_HuyPhong.addActionListener(this);
		btnNhanPhong.addActionListener(this);
		btnDatPhong.addActionListener(this);
		
		// Tạo một Action cho btnDatPhong

		Action huyPhongAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btn_HuyPhong.doClick();
			}
		};

		// Tạo một Action cho btn_DatPhongCho
		Action nhanPhongAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btnNhanPhong.doClick();
			}
		};

		// Lấy InputMap và ActionMap của JPanel
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();

		// Thêm phím nóng cho btnDatPhong
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK), "huyPhong");
		actionMap.put("huyPhong", huyPhongAction);

		// Thêm phím nóng cho btn_DatPhongCho
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_DOWN_MASK), "nhanPhong");
		actionMap.put("nhanPhong", nhanPhongAction);
		
		setEnabledBtnDatPhong(maPhong);
	}

	public void laydulieu(String maPhong) throws RemoteException {
		try {
			pdp = pdp_Service.getPDPDatTruocTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			p = p_Service.getPhongTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
		kh = kh_dao.getKhachHangTheoMaKH(pdp.getKhachHang().getMaKhachHang());

		if (pdp != null) {
			// Thực hiện các thao tác trên pdp
			songuoihat = pdp.getSoNguoiHat();
			dongia = lp.getDonGiaTheoGio();
			// Định dạng đơn giá theo định dạng tiền tệ Việt Nam
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			donGiaFormatted = currencyFormat.format(dongia);

			ngayGioDatPhong = pdp.getNgayGioDatPhong();
			ngayGioNhanPhong = pdp.getNgayGioNhanPhong();

			hotenKH = kh.getHoTen();

		} else {
			// Xử lý trường hợp không tìm thấy phiếu đặt phòng
			JOptionPane.showMessageDialog(this, "không tìm thấy mã");
		}
	}

	private void setEnabledBtnDatPhong(String maPhong) {
		try {
			pdp = pdp_Service.getPDPDatTruocTheoMaPhong(maPhong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Trước 90p để hát tối thiểu 60p -> Vì trước 90p khó demo đổi sang 30p
		LocalDateTime check = pdp.getNgayGioNhanPhong().minusMinutes(30);
        btnDatPhong.setEnabled(!check.isBefore(LocalDateTime.now()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDatPhong)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
			try {
				pdp = pdp_Service.getPDPDatTruocTheoMaPhong(lblPhong_1.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String ngayGioNhan = pdp.getNgayGioNhanPhong().minusMinutes(30).format(formatter);
			if (JOptionPane.showConfirmDialog(null,
					"Nếu đặt phòng trực tiếp, chỉ được sử dụng trước " + ngayGioNhan
							+ " Bạn có muốn tiếp tục đặt không?",
					"Thông báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					dialog_HienThiPhong = new Dialog_HienThiPhong(lblPhong_1.getText(), trangChu);
				} catch (RemoteException | MalformedURLException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				dialog_HienThiPhong.setVisible(true);
			}
		}
		if (o.equals(btnNhanPhong)) {
			// giờ phút hiện tại
			int gio_ht = LocalDateTime.now().getHour();
			int phut_ht = LocalDateTime.now().getMinute();
			int tongsophut_ht = gio_ht * 60 + phut_ht;
			// giờ phút nhận phòng
			try {
				pdp = pdp_Service.getPDPDatTruocTheoMaPhong(lblPhong_1.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int gio_np = pdp.getNgayGioNhanPhong().getHour();
			int phut_np = pdp.getNgayGioNhanPhong().getMinute();
			int tongsophut_np = gio_np * 60 + phut_np;
			int ngayht = LocalDateTime.now().getDayOfMonth();
			int ngaynp = pdp.getNgayGioNhanPhong().getDayOfMonth();

			if (ngayht < ngaynp) {
				// Khách hàng đến sớm hơn giờ nhận phòng 30 phút
				JOptionPane.showMessageDialog(this, "Hãy đến đúng giờ nhận hoặc trước 30 phút!");
			} else if (ngayht == ngaynp) {
				if (tongsophut_np - tongsophut_ht > 30) {
					JOptionPane.showMessageDialog(this, "Hãy đến đúng giờ nhận hoặc trước 30 phút!");
				} else if (tongsophut_np - tongsophut_ht <= 30 && tongsophut_np - tongsophut_ht > -30) {
					// Khách hàng đến đúng giờ
					TempDatPhong tmp = new TempDatPhong(p.getMaPhong(), Integer.parseInt(lbl_SoNguoi_1.getText()));
					try {
						dialog_DatPhongTrong_2 = new Dialog_DatPhongTrong_2(lblPhong_1.getText(), p, lp,
								Integer.parseInt(lbl_SoNguoi_1.getText()), trangChu);
					} catch (NumberFormatException | RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						boolean result = tmp_dao.addTemp(tmp);
						if(!result) {
							JOptionPane.showMessageDialog(this, "Phòng đã được thêm vào danh sách đặt!");
							dispose();
							dialog_DatPhongTrong_2.setVisible(true);
							return;
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
//						e1.printStackTrace();
					}
					dispose();
					JOptionPane.showMessageDialog(this,
							"Phòng " + p.getMaPhong() + " được thêm vào danh sách đặt phòng thành công.");
					DataManager.setSoDienThoaiKHDat("");
					dialog_DatPhongTrong_2.setVisible(true);
				} else if (tongsophut_np - tongsophut_ht < -30) {
					// Khách hàng đến trễ hơn giờ nhận phòng 30 phút
					// Thực hiện công việc B
					JOptionPane.showMessageDialog(this, "Phòng hủy do đến trễ quá 30 phút vui lòng đặt phòng khác để sử dụng phòng karaoke!");
					try {
						pdp_Service.xoaPhieuDatPhongTheoMa(lblPhong_1.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
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
					String mnv = "";
					for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
						if (entry.getKey().equals(ip.getHostAddress())) {
							mnv = entry.getValue();
						}
					}
 					
					for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
							entry.setValue(true);
					}
					try {
						clientConnectionService.setLoadData(loadData);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Enum_TrangThai trangThai = Enum_TrangThai.Trong;
					Phong phong = new Phong(lblPhong_1.getText(), trangThai);
					try {
						p_Service.updatePhong(phong, lblPhong_1.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
					Window[] windows = Window.getWindows();
					for (Window window : windows) {
						if (window instanceof JDialog) {
							window.dispose();
						}
					}

				}
			} else {

				// Khách hàng đến trễ hơn giờ nhận phòng 30 phút
				// Thực hiện công việc B
				JOptionPane.showMessageDialog(this, "Phòng hủy do đến trễ quá 30 phút!");
				try {
					pdp_Service.xoaPhieuDatPhongTheoMa(lblPhong_1.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
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
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				try {
					clientConnectionService.setLoadData(loadData);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Enum_TrangThai trangThai = Enum_TrangThai.Trong;
				Phong phong = new Phong(lblPhong_1.getText(), trangThai);
				try {
					p_Service.updatePhong(phong, lblPhong_1.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				Window[] windows = Window.getWindows();
				for (Window window : windows) {
					if (window instanceof JDialog) {
						window.dispose();
					}
				}

			}
		}

		else if (o.equals(btn_HuyPhong)) {
			int tb = JOptionPane.showConfirmDialog(null, "Bạn có muốn Hủy Phòng chờ?", "Hủy phòng chờ",
					JOptionPane.YES_NO_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Phòng hủy thành công!");
				try {
					pdp_Service.xoaPhieuDatPhongTheoMa(lblPhong_1.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
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
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				try {
					clientConnectionService.setLoadData(loadData);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Enum_TrangThai trangThai = Enum_TrangThai.Trong;
				Phong phong = new Phong(lblPhong_1.getText(), trangThai);
				try {
					p_Service.updatePhong(phong, lblPhong_1.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);

				Window[] windows = Window.getWindows();
				for (Window window : windows) {
					if (window instanceof JDialog) {
						window.dispose();
					}
				}
			}

		}

	}
}
