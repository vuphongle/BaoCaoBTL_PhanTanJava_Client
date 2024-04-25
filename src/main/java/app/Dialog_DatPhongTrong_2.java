package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.time.LocalDateTime;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.SystemColor;
import javax.swing.UIManager;

import entity.ChiTietDichVu;
import entity.ChiTietHoaDon;
import entity.Enum_TrangThai;
import entity.HoaDonDatPhong;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.SanPham;
import entity.TempDatPhong;
import utils.TempThemDV;

import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;

import dao.ChiTietDichVuServices;
import dao.ChiTietHoaDonServices;
import dao.ClientConnectionService;
import dao.HoaDonDatPhongServices;
import dao.KhachHangServices;
import dao.LoaiPhongServices;
import dao.NhanVienService;
import dao.PhieuDatPhongService;
import dao.PhongService;
import dao.SanPhamService;
import dao.TempDatPhongServices;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;

public class Dialog_DatPhongTrong_2 extends JDialog implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private final GD_TrangChu trangChu;
	private static final long serialVersionUID = 1L;
	private final JTextField txtSDT;
	private final JButton btn_KiemTraSDT;
    private final JButton btn_ThemDV;
    private final JButton btn_Sua;
    private final JButton btn_DatPhong;
	private final JPanel panel_1;
    private final JPanel panel_2;
	private final JCheckBox checkBox_KH;
	private final JLabel lbl_GioiTinh_1;
    private final JLabel lbl_GiaTien_1;
    private final JLabel lbl_TenKH_1;
	private final JTextField txtSoNguoi;
	private final JButton btn_DatThemPhong;
	private final NhanVienService nv_dao;
	private NhanVien nv;
	private final HoaDonDatPhongServices hddp_dao;
	private   ChiTietHoaDonServices cthd_dao;
	private  ChiTietDichVuServices ctdv_dao;

	private final JTable tblThemPhongMoi;
    private final JTable tblDV;
	private final DefaultTableModel model;
    private final DefaultTableModel modelDV;
	private final String[] col = { "STT", "Mã Phòng", "Loại Phòng", "Số người", "Đơn Giá" };
	private final String[] colDV = { "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng tiền" };

	private Dialog_ThemDichVu dialog_ThemDichVu;
	private final JLabel lbl_TenKH;

	private final TimePickerSettings timeSettings;
	private final DatePickerSettings dateSettings;
	private final JButton btn_XoaPhongDat;
    private final JButton btnXoaDV;

	private KhachHangServices khachHang_dao;
	private final JLabel lblTieuDe;
	private final TempDatPhongServices tmpDatPhong_dao;
	private final PhongService p_Service;
	private final LoaiPhongServices lp_dao;
	private final JLabel lbl_Loai;
	private final JLabel lblMaPhong;
	private final PhieuDatPhongService pdp_Service;
	private Date ngayHienTai;
	private Date date;
	private final KhachHangServices kh_dao;
	private final DecimalFormat df;
	private final SanPhamService sp_Service;
	private InetAddress ip;
	private ClientConnectionService clientConnectionService;

	public Dialog_DatPhongTrong_2(String maPhong, Phong p, LoaiPhong lp, int soNguoi, GD_TrangChu trangChu) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		ip = InetAddress.getLocalHost();
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		df = new DecimalFormat("#,###,### VNĐ");
		// màn
		// hình******************************************************************************
		this.trangChu = trangChu;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setSize(800, 580);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
		this.setIconImage(icon.getImage());
		nv_dao = (NhanVienService) Naming.lookup(DataManager.getRmiURL() + "nhanVienServices");
		hddp_dao = (HoaDonDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "hoaDonDatPhongServices");
		cthd_dao = (ChiTietHoaDonServices) Naming.lookup(DataManager.getRmiURL() + "chiTietHoaDonServices");
		ctdv_dao = (ChiTietDichVuServices) Naming.lookup(DataManager.getRmiURL() + "chiTietDichVuServices");
		tmpDatPhong_dao = (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "tempDatPhongServices");
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL() + "loaiPhongServices");
		pdp_Service = (PhieuDatPhongService) Naming.lookup(DataManager.getRmiURL() + "phieuDatPhongServices");
		kh_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
		sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");


		// panel chứa tiêu đề--------------------------------------
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 784, 35);
		panel.setBackground(new Color(181, 230, 251));
		getContentPane().add(panel);
		panel.setLayout(null);

		lblTieuDe = new JLabel("Đặt phòng");

		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLACK);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 18));
		lblTieuDe.setBounds(0, 0, 790, 35);
		panel.add(lblTieuDe);

		// pane; chứa các phần còn lại---------------------------------
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 35, 784, 557);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		lblMaPhong = new JLabel("Mã phòng:");
		lblMaPhong.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaPhong.setBounds(10, 5, 200, 25);
		panel_1.add(lblMaPhong);

		lbl_Loai = new JLabel("Loại:");
		lbl_Loai.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_Loai.setBounds(10, 40, 200, 25);
		panel_1.add(lbl_Loai);

		JLabel lbl_LoaiKhachHang = new JLabel("Khách hàng mặc định:");
		lbl_LoaiKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_LoaiKhachHang.setBounds(10, 70, 180, 25);
		panel_1.add(lbl_LoaiKhachHang);

		checkBox_KH = new JCheckBox();
		checkBox_KH.setBackground(Color.WHITE);
		checkBox_KH.setHorizontalAlignment(SwingConstants.LEFT);
		checkBox_KH.setFont(new Font("Arial", Font.BOLD, 15));
		checkBox_KH.setBounds(210, 70, 30, 25);
		panel_1.add(checkBox_KH);

		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.menu);
		panel_2.setBounds(10, 100, 760, 40);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JLabel lbl_sdtKH = new JLabel("SDT khách:");
		lbl_sdtKH.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_sdtKH.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_sdtKH.setBounds(5, 5, 130, 30);
		panel_2.add(lbl_sdtKH);

		txtSDT = new JTextField();
		txtSDT.setHorizontalAlignment(SwingConstants.LEFT);
		txtSDT.setFont(new Font("Arial", Font.BOLD, 16));
		txtSDT.setText("0788343289");
		txtSDT.setBounds(140, 5, 300, 30);
		panel_2.add(txtSDT);
		txtSDT.setColumns(10);

		lbl_TenKH = new JLabel("Tên khách hàng:");
		lbl_TenKH.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_TenKH.setBounds(10, 145, 135, 30);
		panel_1.add(lbl_TenKH);

		JLabel lbl_GioiTinh = new JLabel("Giới tính:");
		lbl_GioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_GioiTinh.setBounds(450, 145, 77, 30);
		panel_1.add(lbl_GioiTinh);

		lbl_GioiTinh_1 = new JLabel("Nam");
		lbl_GioiTinh_1.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_GioiTinh_1.setBounds(550, 145, 100, 30);
		panel_1.add(lbl_GioiTinh_1);

		JLabel lbl_GiaTien = new JLabel("Giá tiền:");
		lbl_GiaTien.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_GiaTien.setBounds(440, 40, 80, 25);
		panel_1.add(lbl_GiaTien);

		JLabel lbl_SoNguoi = new JLabel("Số người:");
		lbl_SoNguoi.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_SoNguoi.setBounds(440, 5, 80, 25);
		panel_1.add(lbl_SoNguoi);

		txtSoNguoi = new JTextField();
		txtSoNguoi.setText(soNguoi + "");
		txtSoNguoi.setFont(new Font("Arial", Font.BOLD, 16));
		txtSoNguoi.setBounds(550, 5, 100, 25);
		panel_1.add(txtSoNguoi);

		lbl_GiaTien_1 = new JLabel("");
		lbl_GiaTien_1.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_GiaTien_1.setBounds(550, 40, 200, 25);
		panel_1.add(lbl_GiaTien_1);

		lbl_TenKH_1 = new JLabel();
		lbl_TenKH_1.setFont(new Font("Arial", Font.ITALIC, 17));
		lbl_TenKH_1.setBounds(165, 145, 180, 30);
		panel_1.add(lbl_TenKH_1);

		// các nút
		// jbutton-------------------------------------------------------------------
		btn_DatPhong = new JButton("Đặt Phòng");
		btn_DatPhong.setBackground(Color.GREEN);
		btn_DatPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_DatPhong.setBackground(new Color(33, 167, 38, 255));
		btn_DatPhong.setBounds(10, 450, 160, 40);
		panel_1.add(btn_DatPhong);

		btn_Sua = new JButton("Sửa");
		btn_Sua.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Sua.setBackground(new Color(255, 83, 83, 255));
		btn_Sua.setBounds(600, 450, 170, 40);
		panel_1.add(btn_Sua);

		btn_ThemDV = new JButton("Thêm dịch vụ");
		btn_ThemDV.setBackground(Color.LIGHT_GRAY);
		btn_ThemDV.setFont(new Font("Arial", Font.BOLD, 15));
		btn_ThemDV.setBounds(225, 290, 135, 30);
		panel_1.add(btn_ThemDV);

		btn_KiemTraSDT = new JButton("Kiểm Tra");
		btn_KiemTraSDT.setBackground(UIManager.getColor("Button.background"));
		btn_KiemTraSDT.setFont(new Font("Arial", Font.BOLD, 17));
		btn_KiemTraSDT.setBounds(480, 5, 200, 32);
		panel_2.add(btn_KiemTraSDT);

		JLabel lbl_SoNguoi_1_1 = new JLabel();
		lbl_SoNguoi_1_1.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_SoNguoi_1_1.setBounds(140, 5, 250, 25);
		panel_1.add(lbl_SoNguoi_1_1);

		JLabel lbl_SoNguoi_1_2 = new JLabel("");
		lbl_SoNguoi_1_2.setFont(new Font("Arial", Font.BOLD, 16));
		lbl_SoNguoi_1_2.setBounds(140, 40, 200, 25);
		panel_1.add(lbl_SoNguoi_1_2);

		btn_DatThemPhong = new JButton("Đặt Thêm Phòng");
		btn_DatThemPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_DatThemPhong.setBackground(new Color(109, 197, 112));
		btn_DatThemPhong.setBounds(193, 450, 200, 40);
		panel_1.add(btn_DatThemPhong);

		// bảng thêm phòng mới
		model = new DefaultTableModel(col, 0);
		tblThemPhongMoi = new JTable(model);
		tblThemPhongMoi.setFont(new Font("Arial", Font.PLAIN, 12));
		tblThemPhongMoi.setBackground(Color.WHITE);
		JScrollPane sp = new JScrollPane(tblThemPhongMoi);
		sp.setBounds(10, 180, 765, 100);
		panel_1.add(sp);
		panel_1.setPreferredSize(new Dimension(800, 300));

		dateSettings = new DatePickerSettings();
		dateSettings.setLocale(new Locale("vi", "VN")); // Set the locale to English
		dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd"); // Set the date format

		timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);

		btn_XoaPhongDat = new JButton("Xóa Phòng Đặt");
		btn_XoaPhongDat.setFont(new Font("Arial", Font.BOLD, 18));
		btn_XoaPhongDat.setBackground(new Color(234, 234, 114, 255));
		btn_XoaPhongDat.setBounds(413, 450, 170, 40);
		panel_1.add(btn_XoaPhongDat);

		modelDV = new DefaultTableModel(colDV, 0);
		tblDV = new JTable(modelDV);
		tblDV.setFont(new Font("Arial", Font.PLAIN, 12));
		tblDV.setBackground(Color.WHITE);
		JScrollPane sp_ListDV = new JScrollPane(tblDV);
		sp_ListDV.setBounds(10, 330, 765, 100);
		panel_1.add(sp_ListDV);

		btnXoaDV = new JButton("Xóa dịch vụ");
		btnXoaDV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoaDV.setFont(new Font("Arial", Font.BOLD, 15));
		btnXoaDV.setBackground(Color.LIGHT_GRAY);
		btnXoaDV.setBounds(393, 290, 134, 30);
		panel_1.add(btnXoaDV);

		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (DataManager.isLoadDV()) {
					clearTableDV();
					try {
						if (tmpDatPhong_dao.getAllTemp().size() == 2) {
							loadDataDV(model.getValueAt(0, 1).toString());
							DataManager.setLoadDV(false);
						} else {
							DataManager.setLoadDV(false);
							clearTableDV();
							clearTable();
							loadDataPhong();
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		Action themDVAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btn_ThemDV.doClick();
			}
		};
		Action DatPhongAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btn_DatPhong.doClick();
			}
		};

		// Lấy InputMap và ActionMap của JPanel
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), "themDv");
		actionMap.put("themDv", themDVAction);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "datPhong");
		actionMap.put("datPhong", DatPhongAction);

		// thêm sự kiện button
		btn_DatPhong.addActionListener(this);
		btn_KiemTraSDT.addActionListener(this);
		btn_Sua.addActionListener(this);
		btn_ThemDV.addActionListener(this);
		btn_DatThemPhong.addActionListener(this);
		btn_XoaPhongDat.addActionListener(this);
		btnXoaDV.addActionListener(this);
		tblThemPhongMoi.addMouseListener(this);
		checkBox_KH.addActionListener(this);
		loadDataPhong();
		checkDefaultCustomer();
		timer.start();
	}

	private void checkDefaultCustomer() {
		if (DataManager.getSoDienThoaiKHDat().equals("0000000000")) {
			checkBox_KH.setSelected(true);
			lbl_TenKH_1.setText("Nguyễn Văn A");
			lbl_GioiTinh_1.setText("Nam");
			txtSDT.setEnabled(false);
			btn_KiemTraSDT.setEnabled(false);
		}
	}

	private void loadDataPhong() throws RemoteException {
		int i = 0;
		for (TempDatPhong tmp : tmpDatPhong_dao.getAllTemp()) {
			if (!tmp.getMaPhong().equals("000")) {
				i++;
				Phong p = null;
				try {
					p = p_Service.getPhongTheoMaPhong(tmp.getMaPhong());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LoaiPhong lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
				Object[] row = { i, tmp.getMaPhong(), lp.getTenLoaiPhong(), tmp.getSoNguoiHat(),
						lp.getDonGiaTheoGio() };
				model.addRow(row);
			}
		}
		if (!DataManager.getSoDienThoaiKHDat().equals("")) {
			txtSDT.setText(DataManager.getSoDienThoaiKHDat());
			try {
				khachHang_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sdt = txtSDT.getText();
			KhachHang khachHang = khachHang_dao.getKhachHangTheoSDT(sdt);
			String hoTen = khachHang.getHoTen();
			boolean gioiTinh = khachHang.isGioiTinh();
			String gioiTinhStr = gioiTinh ? "Nam" : "Nữ";
			lbl_GioiTinh_1.setText(gioiTinhStr);
			lbl_TenKH_1.setText(hoTen);
		}
	}

	private void loadDataDV(String maPhong) {
		if (DataManager.getCtdvTempList() != null) {
			int i = 0;
			for (TempThemDV tmp : DataManager.getCtdvTempList()) {
				if (tmp.getMaPhong().equals(maPhong)) {
					i++;
					Object[] row = { i, tmp.getMaSP(), tmp.getTenSP(), tmp.getSoLuong(), tmp.getDonGia(),
							df.format(tmp.getDonGia() * tmp.getSoLuong()) };
					modelDV.addRow(row);
				}
			}
		}
	}

	private void clearTable() {
		while (tblThemPhongMoi.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	private void clearTableDV() {
		while (tblDV.getRowCount() > 0) {
			modelDV.removeRow(0);
		}
	}

	private void xoa() throws RemoteException {
		if (tblThemPhongMoi.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa!!");
		} else if (tblThemPhongMoi.getSelectedRowCount() > 1) {
			int[] selectedRows = tblThemPhongMoi.getSelectedRows();
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa các phòng đã chọn không?", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					int row = selectedRows[i];
					String maPhong = model.getValueAt(row, 1).toString();
					tmpDatPhong_dao.deleteTempDP(maPhong);
					model.removeRow(row);
					if (DataManager.getCtdvTempList() != null) {
						Iterator<TempThemDV> iterator = DataManager.getCtdvTempList().iterator();
						while (iterator.hasNext()) {
							TempThemDV tmp = iterator.next();
							if (tmp.getMaPhong().equals(maPhong)) {
								iterator.remove();
							}
						}
					}
				}
				clearTable();
				loadDataPhong();
				JOptionPane.showMessageDialog(this, "Xóa thành công!!");
			}
		} else {
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phòng này không?", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = tblThemPhongMoi.getSelectedRow();
				String maPhong = model.getValueAt(row, 1).toString();
				tmpDatPhong_dao.deleteTempDP(maPhong);
				model.removeRow(row);
				if (DataManager.getCtdvTempList() != null) {
					Iterator<TempThemDV> iterator = DataManager.getCtdvTempList().iterator();
					while (iterator.hasNext()) {
						TempThemDV tmp = iterator.next();
						if (tmp.getMaPhong().equals(maPhong)) {
							iterator.remove();
						}
					}
				}
				clearTable();
				loadDataPhong();
				JOptionPane.showMessageDialog(this, "Xóa thành công!!");
			}
		}
		if (tmpDatPhong_dao.getAllTemp().size() == 1) {
			DataManager.setSoDienThoaiKHDat("");
			setVisible(false);
		}
	}

	private void sua() throws NumberFormatException, RemoteException {
		if (tblThemPhongMoi.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để sửa!!");
		} else if (tblThemPhongMoi.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 phòng để sửa!!");
		} else {
			tmpDatPhong_dao.updateTempDP(lblMaPhong.getText(), Integer.parseInt(txtSoNguoi.getText()));
			clearTable();
			loadDataPhong();
			JOptionPane.showMessageDialog(this, "Sửa thành công!");
		}
	}

	private void themDV() throws RemoteException {
		if (tblThemPhongMoi.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn phòng để thêm dịch vụ!");
		} else if (tblThemPhongMoi.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(null, "Chỉ thêm dịch vụ được 1 phòng!");
		} else {
			if (!lbl_TenKH_1.getText().equals("")) {
				String customer = lbl_TenKH_1.getText();
				String mnv = "";
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
				String employee = mnv;
				try {
					nv = nv_dao.findNhanVienToLogin(employee);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String maPhong = lblMaPhong.getText();
				String ma = maPhong.substring(maPhong.indexOf(":") + 1).trim();
				try {
					dialog_ThemDichVu = new Dialog_ThemDichVu(customer, nv.getHoTen(), ma);
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog_ThemDichVu.setModal(true);
				dialog_ThemDichVu.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chưa thêm khách hàng!");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btn_Sua)) {
			try {
				sua();
			} catch (NumberFormatException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btn_ThemDV)) {
			try {
				themDV();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btn_DatPhong)) {
			int check = 0;
			int checkPSD = 0;
			String maHoaDon = null;
			try {
				maHoaDon = TaoMaHDDP();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HoaDonDatPhong hddp = null;
			LocalDateTime ngayGioHT = LocalDateTime.now();
			if (!lbl_TenKH_1.getText().equals("")) {
				try {
					for (TempDatPhong tmpDatPhong : tmpDatPhong_dao.getAllTemp()) {
						// Sửa trống -> Đang sử dụng
						Phong p = p_Service.getPhongTheoMaPhong(tmpDatPhong.getMaPhong());
						if (p != null && p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
							checkPSD = 1;
							List<ChiTietHoaDon> dsChiTietHoaDon = cthd_dao
									.getChiTietHoaDonTheoMaPhong(tmpDatPhong.getMaPhong());
							for (ChiTietHoaDon cthd : dsChiTietHoaDon) {
								maHoaDon = cthd.getHoaDon().getMaHoaDon();
							}
						}

						if (!tmpDatPhong.getMaPhong().equals("000") && !(p.getTrangThai() == Enum_TrangThai.Dang_su_dung)) {

							// Thêm phiếu đặt phòng
							String mnv = "";
							Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
							for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
								if (entry.getKey().equals(ip.getHostAddress())) {
									mnv = entry.getValue();
								}
							}
							NhanVien nv = new NhanVien(mnv);
							KhachHang kh = new KhachHang();
							if (checkBox_KH.isSelected())
								kh = kh_dao.getKhachHangTheoSDT("0000000000");
							else
								kh = kh_dao.getKhachHangTheoSDT(txtSDT.getText());

							PhieuDatPhong pdb = new PhieuDatPhong(TaoMaPDP(), p, nv, kh, ngayGioHT, ngayGioHT,
									tmpDatPhong.getSoNguoiHat());
							if (p.getTrangThai() != Enum_TrangThai.Cho) {
								pdp_Service.addPhieuDatPhong(pdb);
								// Nếu là chờ thì sửa lại.
							}
							if (p.getTrangThai() == Enum_TrangThai.Cho) {
								pdp_Service.addPhieuDatPhong(pdb);
							}

							p.setTrangThai(Enum_TrangThai.Dang_su_dung);
							p_Service.updatePhong(p, p.getMaPhong());

							// Thêm vào HoaDonDatPhong
							KhuyenMai km = new KhuyenMai(null);
							java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
							if (checkPSD == 1) {
								hddp = hddp_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
								check = 1;
								checkPSD = 0;
							}
							if (check == 0) {
								hddp = new HoaDonDatPhong(maHoaDon, kh, nv, sqlDate, false, km, 0.0);
								hddp_dao.addHoaDonDatPhong(hddp);
								check = 1;
							}

							// Thêm chi tiết hóa đơn
							ChiTietHoaDon cthd;

							cthd = new ChiTietHoaDon(hddp, p, Timestamp.valueOf(ngayGioHT), Timestamp.valueOf(ngayGioHT),
									0);
							cthd_dao.addChiTietHD(cthd);

							// Thêm chi tiết dịch vụ, cập nhật lại số lượng sản phẩm trong csdl
							if (DataManager.getCtdvTempList() != null) {
								for (TempThemDV tmp : DataManager.getCtdvTempList()) {
									if(sp_Service.getLoaiSanPhamTheoMaSP(tmp.getMaSP()).equals("Thức ăn")) {
										ChiTietDichVu ctdv = new ChiTietDichVu(hddp, new Phong(tmp.getMaPhong()),
												new SanPham(tmp.getMaSP()), tmp.getSoLuong(), tmp.getDonGia() * 1.03);
										if (ctdv.getPhong().getMaPhong().equals(tmpDatPhong.getMaPhong())) {
											SanPham sp = sp_Service.getSanPhamTheoMaSP(tmp.getMaSP());
											sp.setSoLuongTon(sp.getSoLuongTon() - tmp.getSoLuong());
											ctdv_dao.addChiTietDV(ctdv);
											sp_Service.updateSanPham(sp);
										}
									}
									else if(sp_Service.getLoaiSanPhamTheoMaSP(tmp.getMaSP()).equals("Đồ uống")) {
										ChiTietDichVu ctdv = new ChiTietDichVu(hddp, new Phong(tmp.getMaPhong()),
												new SanPham(tmp.getMaSP()), tmp.getSoLuong(), tmp.getDonGia() * 1.02);
										if (ctdv.getPhong().getMaPhong().equals(tmpDatPhong.getMaPhong())) {
											SanPham sp = sp_Service.getSanPhamTheoMaSP(tmp.getMaSP());
											sp.setSoLuongTon(sp.getSoLuongTon() - tmp.getSoLuong());
											ctdv_dao.addChiTietDV(ctdv);
											sp_Service.updateSanPham(sp);
										}
									}
									else {
										ChiTietDichVu ctdv = new ChiTietDichVu(hddp, new Phong(tmp.getMaPhong()),
												new SanPham(tmp.getMaSP()), tmp.getSoLuong(), tmp.getDonGia() * 1.01);
										if (ctdv.getPhong().getMaPhong().equals(tmpDatPhong.getMaPhong())) {
											SanPham sp = sp_Service.getSanPhamTheoMaSP(tmp.getMaSP());
											sp.setSoLuongTon(sp.getSoLuongTon() - tmp.getSoLuong());
											ctdv_dao.addChiTietDV(ctdv);
											sp_Service.updateSanPham(sp);
										}
									}
								}
							}
						}
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					tmpDatPhong_dao.deleteALLTempDatPhong();
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
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
					System.out.println(entry.getKey() + " " + entry.getValue());
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
				
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
					System.out.println(entry.getKey() + " " + entry.getValue());
				}
				
				
				DataManager.setCtdvTempList(null);
				JOptionPane.showMessageDialog(this, "Đặt phòng thành công, thời gian bắt đầu được tính!");
				DataManager.setSoDienThoaiKHDat("");
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chưa thêm khách hàng!");
			}
		}
		if (o.equals(btn_DatThemPhong)) {
			if (checkBox_KH.isSelected()) {
				DataManager.setSoDienThoaiKHDat("0000000000");
			}
			setVisible(false);
		}
		if (o.equals(btn_XoaPhongDat)) {
			try {
				xoa();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// kiem tra khach hang
		if (o.equals(btn_KiemTraSDT)) {
			try {
				khachHang_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sdt = txtSDT.getText();
			KhachHang khachHang = null;
			try {
				khachHang = khachHang_dao.getKhachHangTheoSDT(sdt);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
//				e1.printStackTrace();
			}
			if (khachHang != null) {
				String hoTen = khachHang.getHoTen();
				boolean gioiTinh = khachHang.isGioiTinh();
				String gioiTinhStr = gioiTinh ? "Nam" : "Nữ";
				lbl_GioiTinh_1.setText(gioiTinhStr);
				lbl_TenKH_1.setText(hoTen);
				DataManager.setSoDienThoaiKHDat(txtSDT.getText());
			} else {
				int checkCustomer = JOptionPane.showConfirmDialog(this,
						"Khách hàng chưa có trên hệ thống! Bạn có muốn thêm khách hàng không?");
				DataManager.setSoDienThoaiKHDat(txtSDT.getText());
				DataManager.setLoadSDT(true);
				if (checkCustomer == JOptionPane.YES_OPTION) {
					setVisible(false);
					trangChu.showKhachHangCard();
				}
			}
		}
		if (o.equals(checkBox_KH)) {
			if (checkBox_KH.isSelected()) {
				lbl_TenKH_1.setText("Nguyễn Văn A");
				lbl_GioiTinh_1.setText("Nam");
				txtSDT.setEnabled(false);
				btn_KiemTraSDT.setEnabled(false);
			} else {
				txtSDT.setEnabled(true);
				btn_KiemTraSDT.setEnabled(true);
			}
		}
		if (o.equals(btnXoaDV)) {
			xoaDV();
		}

	}

	public void xoaDV() {
		if (tblDV.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa!");
		} else {
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa Sản phẩm này không?", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = tblDV.getSelectedRow();
				ArrayList<TempThemDV> ds = DataManager.getCtdvTempList();
				int chk = 0;
				flag: for (TempThemDV temp : ds) {
					if (temp.getMaPhong().equals(model.getValueAt(tblThemPhongMoi.getSelectedRow(), 1).toString())
							&& temp.getMaSP().equals(modelDV.getValueAt(row, 1).toString())) {
						ds.remove(temp);
						JOptionPane.showMessageDialog(this, "Xóa thành công!");
						chk = 1;
						break;
					}
				}
				if (chk == 1) {
					modelDV.removeRow(row);
				}
				clearTableDV();
				loadDataDV(model.getValueAt(tblThemPhongMoi.getSelectedRow(), 1).toString());
				DataManager.setCtdvTempList(ds);
			}
		}
	}

	private int ThuTuPhieuDatPhongTrongNgay() {
		int sl = 1;
		String maPDP = "";
		try {
			for (PhieuDatPhong pdp : pdp_Service.getAllsPhieuDatPhong()) {
				maPDP = pdp.getMaPhieu(); // Chạy hết vòng for sẽ lấy được mã Phiếu đặt phòng cuối danh sách
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int ngayTrenMaPDPCuoiDS = Integer.parseInt(maPDP.substring(3, 9));
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd"); // Format yyMMdd sẽ so sánh ngày được
		ngayHienTai = new Date();
		int ngayHT = Integer.parseInt(dateFormat.format(ngayHienTai));
		if (ngayHT != ngayTrenMaPDPCuoiDS) {
			sl = 1;
		} else if (ngayHT == ngayTrenMaPDPCuoiDS) {
			sl = Integer.parseInt(maPDP.substring(9, 13)) + 1;
		}
		return sl;
	}

	private String TaoMaPDP() {
		String prefix = "PDP";
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		date = new Date();
		String suffix = String.format("%04d", ThuTuPhieuDatPhongTrongNgay());
		return prefix + dateFormat.format(date) + suffix;
	}

	private int ThuTuHoaDonDatPhongTrongNgay() throws RemoteException {
		int sl = 1;
		String maHDDP = "";
		for (HoaDonDatPhong hddp : hddp_dao.getAllHoaDonDatPhong()) {
			maHDDP = hddp.getMaHoaDon(); // Chạy hết vòng for sẽ lấy được mã Phiếu đặt phòng cuối danh sách
		}
		int ngayTrenMaHDDPCuoiDS = Integer.parseInt(maHDDP.substring(2, 8));
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd"); // Format yyMMdd sẽ so sánh ngày được
		ngayHienTai = new Date();
		int ngayHT = Integer.parseInt(dateFormat.format(ngayHienTai));
		if (ngayHT != ngayTrenMaHDDPCuoiDS) {
			sl = 1;
		} else if (ngayHT == ngayTrenMaHDDPCuoiDS) {
			sl = Integer.parseInt(maHDDP.substring(8, 12)) + 1;
		}
		return sl;
	}

	private String TaoMaHDDP() throws RemoteException {
		String prefix = "HD";
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		date = new Date();
		String suffix = String.format("%04d", ThuTuHoaDonDatPhongTrongNgay());
		return prefix + dateFormat.format(date) + suffix;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tblThemPhongMoi.getSelectedRow();
		lblMaPhong.setText("Mã phòng: " + model.getValueAt(row, 1));
		lbl_Loai.setText("Loại: " + model.getValueAt(row, 2));
		txtSoNguoi.setText(model.getValueAt(row, 3).toString());
		lbl_GiaTien_1.setText(model.getValueAt(row, 4) + "VNĐ");
//		if(model.getValueAt(row, 1).equals(DataManager.getMaPhong())) {
//			clearTableDV();
//			loadDataDV(DataManager.getMaPhong());
//		}
		clearTableDV();
		loadDataDV(model.getValueAt(row, 1).toString());
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
}
