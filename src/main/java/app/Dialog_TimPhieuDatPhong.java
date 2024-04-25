package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.ClientConnectionService;
import dao.HoaDonDatPhongServices;
import dao.KhachHangServices;
import dao.LoaiPhongServices;
import dao.NhanVienService;
import dao.PhieuDatPhongService;
import dao.PhongService;
import dao.TempDatPhongServices;
import entity.Enum_TrangThai;
import entity.HoaDonDatPhong;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.TempDatPhong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Window;

public class Dialog_TimPhieuDatPhong extends JDialog implements ActionListener, MouseListener {
	private final JPanel panel;
	private final JLabel lblTieuDe;
    private final JLabel lblTrangThai;
    private final JLabel lblMaPDP;
    private final JLabel lblSDTKhach;
	private final PhongService p_Service;
	private final JComboBox<String> comboBox_TrangThai;
    private final JComboBox<String> comboBox_TrangThai_1;
	private final JButton btnTimKiem;
    private final JButton btnLamMoi;
    private final JButton btn_XuatPhong;
    private final JButton btn_XemPhong;
    private final JButton btn_HuyPhong;
    private final JButton btn_NhanPhong;

	private JTable tblPhieuDatPhong;
	private DefaultTableModel model;
	private final String[] col = { "Mã PDP", "Phòng", "Tên NV", "Tên KH", "   Ngày Giờ Đặt   ", "   Ngày Giờ Nhận   ",
			"Số Người", "Hình Thức", "Trạng Thái" };
	private final JButton btn_QuayLai;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField txtLoaiTimKiem;
	private final PhieuDatPhongService pdp_Service;
    private final KhachHangServices kh_dao;
	private KhachHang kh = new KhachHang();
	private NhanVien nv = new NhanVien();
	private final NhanVienService nv_dao;
	private PhieuDatPhong pdp = new PhieuDatPhong();
	private HoaDonDatPhong hd = new HoaDonDatPhong();
	private final HoaDonDatPhongServices hd_dao;
	private Phong p = new Phong();
	private Dialog_PhongCho dialog_PhongCho;
	private XSSFWorkbook wordbook;
	private final TempDatPhongServices tmp_dao;
	private Dialog_DatPhongTrong_2 dialog_DatPhongTrong_2;
	private GD_TrangChu trangChu;
	private final LoaiPhongServices lp_dao;
	private LoaiPhong lp;
	private Dialog_PhongDangSD dialog_PhongDangSD;
	private Dialog_TimPDP_DaThanhToan dialog_TimPDP_DaThanhToan;
	private InetAddress ip;
	private ClientConnectionService clientConnectionService;
	public Dialog_TimPhieuDatPhong() throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		ip = InetAddress.getLocalHost();
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		
		tmp_dao = (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "tempDatPhongServices"); 
		p_Service =(PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		pdp_Service = (PhieuDatPhongService) Naming.lookup(DataManager.getRmiURL() + "phieuDatPhongServices");
		kh_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
		nv_dao = (NhanVienService) Naming.lookup(DataManager.getRmiURL() + "nhanVienServices");
		hd_dao = (HoaDonDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "hoaDonDatPhongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL() + "loaiPhongServices");
		// kích thước
		getContentPane().setBackground(Color.WHITE);
		setSize(900, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
		this.setIconImage(icon.getImage());

		// panel chứa tiêu
		// đề-------------------------------------------------------------------------
		panel = new JPanel();
		panel.setBounds(0, 0, 884, 35);
		panel.setBackground(new Color(181, 230, 251, 255));
		getContentPane().add(panel);
		panel.setLayout(null);
		getContentPane().add(panel);

		lblTieuDe = new JLabel("Tìm Phiếu Đặt Phòng");
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLACK);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 18));
		lblTieuDe.setBounds(0, 0, 884, 35);
		panel.add(lblTieuDe);

		// panel 1 chứa thông tin kh, nhân viên và bảng
		// table-------------------------------------------
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 34, 884, 377);
		panel_1.setBackground(SystemColor.menu);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		lblMaPDP = new JLabel("Tìm phiếu theo:");
		lblMaPDP.setFont(new Font("Arial", Font.BOLD, 14));
		lblMaPDP.setBounds(5, 5, 110, 30);
		panel_1.add(lblMaPDP);

		lblSDTKhach = new JLabel("Nhập thông tin:");
		lblSDTKhach.setFont(new Font("Arial", Font.BOLD, 14));
		lblSDTKhach.setBounds(5, 45, 110, 30);
		panel_1.add(lblSDTKhach);

		txtLoaiTimKiem = new JTextField();
		txtLoaiTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
		txtLoaiTimKiem.setColumns(10);
		txtLoaiTimKiem.setBounds(120, 45, 250, 30);
		panel_1.add(txtLoaiTimKiem);

		// --- lbl và combox trạng thái
		lblTrangThai = new JLabel("Trạng Thái:");
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 15));
		lblTrangThai.setBounds(400, 5, 85, 30);
		panel_1.add(lblTrangThai);
		comboBox_TrangThai = new JComboBox<String>();
		comboBox_TrangThai.setBackground(Color.WHITE);
		comboBox_TrangThai.setFont(new Font("Arial", Font.BOLD, 15));
		comboBox_TrangThai.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Tất Cả", "Chưa Thanh Toán", "Đã Thanh Toán" }));
		comboBox_TrangThai.setSelectedIndex(0);
		comboBox_TrangThai.setBounds(490, 5, 200, 30);
		panel_1.add(comboBox_TrangThai);
		// cbo tìm theo loại
		comboBox_TrangThai_1 = new JComboBox<String>();
		comboBox_TrangThai_1.setFont(new Font("Arial", Font.BOLD, 15));
		comboBox_TrangThai_1.setBackground(Color.WHITE);
		comboBox_TrangThai_1.setModel(new DefaultComboBoxModel<String>(
				new String[] { "", "Mã phiếu đặt", "Số điện thoại KH", "Họ tên KH", "Ngày nhận phòng" }));
		comboBox_TrangThai_1.setBounds(120, 5, 250, 30);
		panel_1.add(comboBox_TrangThai_1);

		// --- cuối góc phải là 3 nút jbutton
		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFont(new Font("Arial", Font.BOLD, 15));
		btnTimKiem.setIcon(new ImageIcon("icon\\Research_icon.png"));
		btnTimKiem.setBounds(400, 45, 290, 30);
		btnTimKiem.setBackground(new Color(13, 153, 255, 255));
		btnTimKiem.setBorder(new RoundedBorder(20));
		panel_1.add(btnTimKiem);

		btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setIcon(new ImageIcon("icon\\Refresh_icon.png"));
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
		btnLamMoi.setBounds(720, 5, 125, 30);
		btnLamMoi.setBackground(new Color(112, 210, 103, 255));
		btnLamMoi.setBorder(new RoundedBorder(10));
		panel_1.add(btnLamMoi);

		// bảng Phiếu ĐẶt Phòng
		model = new DefaultTableModel(col, 0);
		tblPhieuDatPhong = new JTable(model);
		tblPhieuDatPhong.setFont(new Font("Arial", Font.PLAIN, 12));
		tblPhieuDatPhong.setBackground(Color.WHITE);
		tblPhieuDatPhong.getColumnModel().getColumn(4).setMinWidth(100);
		tblPhieuDatPhong.getColumnModel().getColumn(5).setMinWidth(110);
		tblPhieuDatPhong.getColumnModel().getColumn(6).setMaxWidth(60);
		tblPhieuDatPhong.getColumnModel().getColumn(7).setMinWidth(30);
		tblPhieuDatPhong.getColumnModel().getColumn(1).setMaxWidth(50);
		tblPhieuDatPhong.getColumnModel().getColumn(2).setMinWidth(100);
		tblPhieuDatPhong.getColumnModel().getColumn(3).setMinWidth(90);
		tblPhieuDatPhong.getColumnModel().getColumn(0).setMinWidth(100);

		JScrollPane sp = new JScrollPane(tblPhieuDatPhong);
		sp.setBounds(0, 90, 884, 210);
		panel_1.add(sp);
		panel_1.setPreferredSize(new Dimension(800, 300));

		// các nút
		// jbutton-------------------------------------------------------------------
		btn_NhanPhong = new JButton("Nhận Phòng");
		btn_NhanPhong.setBackground(Color.GREEN);
		btn_NhanPhong.setForeground(Color.WHITE);
		btn_NhanPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_NhanPhong.setBackground(new Color(33, 167, 38, 255));
		btn_NhanPhong.setBorder(new RoundedBorder(5));
		btn_NhanPhong.setBounds(10, 330, 150, 40);
		panel_1.add(btn_NhanPhong);

		btn_HuyPhong = new JButton("Hủy Phòng");
		btn_HuyPhong.setForeground(Color.WHITE);
		btn_HuyPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_HuyPhong.setBackground(new Color(236, 52, 52, 255));
		btn_HuyPhong.setBounds(170, 330, 150, 40);
		btn_HuyPhong.setBorder(new RoundedBorder(5));
		panel_1.add(btn_HuyPhong);

		btn_XemPhong = new JButton("Xem Phòng");
		btn_XemPhong.setForeground(Color.WHITE);
		btn_XemPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_XemPhong.setBackground(new Color(13, 153, 255, 255));
		btn_XemPhong.setBorder(new RoundedBorder(5));
		btn_XemPhong.setBounds(330, 330, 150, 40);
		panel_1.add(btn_XemPhong);

		btn_XuatPhong = new JButton("Xuất Excel");
		btn_XuatPhong.setForeground(Color.WHITE);
		btn_XuatPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btn_XuatPhong.setBackground(new Color(13, 153, 255, 255));
		btn_XuatPhong.setBorder(new RoundedBorder(5));
		btn_XuatPhong.setBounds(660, 330, 200, 40);
		panel_1.add(btn_XuatPhong);

		btn_QuayLai = new JButton("Quay Lại");
		btn_QuayLai.setForeground(Color.WHITE);
		btn_QuayLai.setFont(new Font("Arial", Font.BOLD, 15));
		btn_QuayLai.setBackground(new Color(236, 52, 52, 255));
		btn_QuayLai.setBorder(new RoundedBorder(5));
		btn_QuayLai.setBounds(720, 45, 125, 30);
		panel_1.add(btn_QuayLai);

		// add sự kiện
		btn_HuyPhong.addActionListener(this);
		btn_NhanPhong.addActionListener(this);
		btn_QuayLai.addActionListener(this);
		btn_XemPhong.addActionListener(this);
		btn_XuatPhong.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		tblPhieuDatPhong.addMouseListener(this);
		loadData();
		MyTable(model, tblPhieuDatPhong);
	}

	// hàm căn giữa nội dung trong bảng
	public void MyTable(DefaultTableModel model, JTable table) {
		this.model = model;
		this.tblPhieuDatPhong = table;
	}

	class CenterRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CenterRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}
	}

	public void Canh_Deu_Bang() {
		CenterRenderer centerRenderer = new CenterRenderer();
		for (int i = 0; i < tblPhieuDatPhong.getColumnCount(); i++) {
			tblPhieuDatPhong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	// hàm load sữ liệu
	public void loadData() throws RemoteException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
		String hinhthuc;
		String trangthai;
		List<PhieuDatPhong> allPhieuDatPhong = null;
		try {
			allPhieuDatPhong = pdp_Service.getAllsPhieuDatPhong();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		Collections.sort(allPhieuDatPhong, Comparator.comparing(PhieuDatPhong::getNgayGioDatPhong));
		for (PhieuDatPhong x : allPhieuDatPhong) {
			String ngayGioDat = x.getNgayGioDatPhong().format(formatter);
			String ngayGioNhan = x.getNgayGioNhanPhong().format(formatter);
			if (formatter1.format(x.getNgayGioDatPhong()).equals(formatter1.format(x.getNgayGioNhanPhong()))) {
				hinhthuc = "Đặt trực tiếp";
			} else {
				hinhthuc = "Đặt trước";
			}
			String mp = x.getMaPhieu();
			String mkh = x.getKhachHang().getMaKhachHang();
			try {
				pdp = pdp_Service.getPhieuDatPhongTheoMaPDP(mp);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			p = pdp.getPhong();
			kh = kh_dao.getKhachHangTheoMaKH(mkh);

			String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(x.getMaPhieu());
			try {
				nv = nv_dao.findByID(x.getNhanVien().getMaNhanVien());
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
			if (hd != null && !hd.isTrangThai()) {
				trangthai = "Chưa TT";
			} else if (hd != null && hd.isTrangThai()) {
				trangthai = "Đã TT";
			} else {
				trangthai = "Chưa TT";
			}

			Object[] row = { x.getMaPhieu(), p.getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat, ngayGioNhan,
					x.getSoNguoiHat(), hinhthuc, trangthai };
			model.addRow(row);
		}
		Canh_Deu_Bang();
	}

//clear bảng
	public void clearTable() {
		while (tblPhieuDatPhong.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	// hàm tìm
	public void tim() throws RemoteException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
		String hinhthuc = "";
		String trangthai = "";
		String ngayGioDat = pdp.getNgayGioDatPhong().format(formatter);
		String ngayGioNhan = pdp.getNgayGioNhanPhong().format(formatter);
		String thongtinTimKiem = txtLoaiTimKiem.getText();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		if (formatter1.format(pdp.getNgayGioDatPhong()).equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
			hinhthuc = "Đặt trực tiếp";
		} else {
			hinhthuc = "Đặt trước";
		}

		// {"Mã phiếu đặt", "Số điện thoại KH","Họ tên KH"}
		thoatTim: if (btnTimKiem.getText().equals("Tìm kiếm")) {
			if (comboBox_TrangThai_1.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn loại tìm kiếm!");
				break thoatTim;
			}

			if (thongtinTimKiem.equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
				break thoatTim;
			}
			// tìm theo mã phiếu
			if (comboBox_TrangThai_1.getSelectedItem().equals("Mã phiếu đặt")) {
				
				Pattern pattern = Pattern.compile("^\\d{10}$");
				Matcher matcher = pattern.matcher(thongtinTimKiem);
				if (!matcher.matches()) {
				    JOptionPane.showMessageDialog(null, "Mã PDP gồm 10 số ứng với mã phiếu đặt phòng của khách hàng!");
				    return;
				}
				
			    try {
					pdp = pdp_Service.getPhieuDatPhongTheoMaPDP("PDP"+thongtinTimKiem);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			    kh = kh_dao.getKhachHangTheoMaKH(pdp.getKhachHang().getMaKhachHang());
			    String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
			    try {
					nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			    hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
			    if (hd != null && !hd.isTrangThai()) {
			        trangthai = "Chưa TT";
			    } else if (hd != null && hd.isTrangThai()) {
			        trangthai = "Đã TT";
			    } else {
			        return; // Skip this record if it doesn't match the selected payment status
			    }
			    if (pdp != null) {
			        btnTimKiem.setText("Hủy tìm");
			        clearTable();
			        Object[] row = { pdp.getMaPhieu(), pdp.getPhong().getMaPhong(), nv.getHoTen(), kh.getHoTen(),
			                ngayGioDat, ngayGioNhan, pdp.getSoNguoiHat(), hinhthuc, trangthai };
			        model.addRow(row);
			        Canh_Deu_Bang();
			    } else {
			        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
			    }
			}

			//tìm theo sdt khách
			else if (comboBox_TrangThai_1.getSelectedItem().equals("Số điện thoại KH")) {
				//kiem tra sdt nhà mạng vietnam
				Pattern pattern = Pattern.compile("(03[2-9]|05[689]|07[06-9]|08[1-689]|09[0-46-9])+([0-9]{7})\\b");
				Matcher matcher = pattern.matcher(thongtinTimKiem);
				if (!matcher.matches()) {
				    JOptionPane.showMessageDialog(null, "Số điện thoại phải là một chuỗi số hợp lệ theo các nhà mạng Việt Nam!");
				    return;
				}

			    int check = 1;
			    // Tìm kiếm theo số điện thoại
			    kh = kh_dao.getKhachHangTheoSDT(thongtinTimKiem);
			    if (kh != null) {
			        List<PhieuDatPhong> pdps = null;
					try {
						pdps = pdp_Service.getPhieuDatPhongTheoMaKH(kh.getMaKhachHang());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
			        for (PhieuDatPhong pdp : pdps) {
			            if (formatter1.format(pdp.getNgayGioDatPhong())
			                    .equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
			                hinhthuc = "Đặt trực tiếp";
			            } else {
			                hinhthuc = "Đặt trước";
			            }
			            String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
			            try {
							nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
						} catch (RemoteException e) {
							e.printStackTrace();
						}

			            hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
			            if (hd != null && !hd.isTrangThai()) {
			                trangthai = "Chưa TT";
			            } else if (hd != null && hd.isTrangThai()) {
			                trangthai = "Đã TT";
			            } else {
			                continue; // Skip this record if it doesn't match the selected payment status
			            }

			            if (pdp != null) {
			                if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
			                        && check == 1) {
			                    btnTimKiem.setText("Hủy tìm");
			                    clearTable();
			                    XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
			                    check = 0;
			                }
			                Canh_Deu_Bang();
			            }
			        }
			        if (check == 1) {
			            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
			    }
			}
 
			// Tìm kiếm theo tên khách hàng
			else if (comboBox_TrangThai_1.getSelectedItem().equals("Họ tên KH")) {
				//kiem tra ho ten hop le
				Pattern pattern = Pattern.compile("^[\\p{L} .'-]+$");
				Matcher matcher = pattern.matcher(thongtinTimKiem);
				if (!matcher.matches()) {
				    JOptionPane.showMessageDialog(null, "Tên phải là một chuỗi chữ cái hợp lệ VD: Trần Trọng Tín hoặc trần trọng tín!");
				    return;
				}

				ArrayList<KhachHang> DSkh = kh_dao.getKhachHangTheoTenKH(thongtinTimKiem);
				if (DSkh.size() != 0) {
					int check = 1;
					for (KhachHang kh : DSkh) {
						List<PhieuDatPhong> pdps = null;
						try {
							pdps = pdp_Service.getPhieuDatPhongTheoMaKH(kh.getMaKhachHang());
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						for (PhieuDatPhong pdp : pdps) {
							if (kh != null) {
								String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
								try {
									nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
								} catch (RemoteException e) {
									e.printStackTrace();
								}

								hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
								if (hd != null && !hd.isTrangThai()) {
									trangthai = "Chưa TT";
								} else if (hd != null && hd.isTrangThai()) {
									trangthai = "Đã TT";
								}

								if (pdp != null) {
									if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
											&& check == 1) {
										btnTimKiem.setText("Hủy tìm");
										clearTable();
										XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
										check = 0;
									}
									Canh_Deu_Bang();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
							}
						}
					}
					if (check == 1) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
				}
			} 
			
			else if (comboBox_TrangThai_1.getSelectedItem().equals("Ngày nhận phòng")) {
			    String ngaynhanStr = txtLoaiTimKiem.getText();
			    List<PhieuDatPhong> dsPDPtheoNgay = new ArrayList<>();

			    // Kiểm tra xem người dùng đã nhập vào một tháng trong một năm cụ thể hay không
			    if (ngaynhanStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			    	 DateTimeFormatter formatter_ngaynhan = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				        LocalDate ngaynhan = LocalDate.parse(ngaynhanStr, formatter_ngaynhan);
				        try {
							dsPDPtheoNgay = pdp_Service.getPDPTheoNgayNhan(ngaynhan);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					    if (!dsPDPtheoNgay.isEmpty()) {
					        int check = 1;
					        for (PhieuDatPhong pdp : dsPDPtheoNgay) {
					            // so sánh mã hóa đơn tồn tại
					            if (formatter1.format(pdp.getNgayGioDatPhong())
					                    .equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
					                hinhthuc = "Đặt trực tiếp";
					            } else {
					                hinhthuc = "Đặt trước";
					            }
					            if (dsPDPtheoNgay != null) {
					                String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
					                try {
										nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
									} catch (RemoteException e) {
										e.printStackTrace();
									}
					                hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
					                if (hd != null && !hd.isTrangThai()) {
					                    trangthai = "Chưa TT";
					                } else if (hd != null && hd.isTrangThai()) {
					                    trangthai = "Đã TT";
					                } else {
					                    trangthai = "Chưa TT";
					                }
					                if (pdp != null) {
					                    if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
					                            && check == 1) {
					                        btnTimKiem.setText("Hủy tìm");
					                        clearTable();
					                        XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
					                        check = 0;
					                    }
					                    Canh_Deu_Bang();
					                }
					            } else {
					                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
					            }
					        }
					        if (check == 1) {
					            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
					        }
					    } else {
					        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
					    }
			    }

			    else if (ngaynhanStr.matches("^\\d{4}-\\d{2}$")) {
			        YearMonth thangNhan = YearMonth.parse(ngaynhanStr);
			        try {
						dsPDPtheoNgay = pdp_Service.getPDPTheoThangNhan(thangNhan);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				    if (!dsPDPtheoNgay.isEmpty()) {
				        int check = 1;
				        for (PhieuDatPhong pdp : dsPDPtheoNgay) {
				            // so sánh mã hóa đơn tồn tại
				            if (formatter1.format(pdp.getNgayGioDatPhong())
				                    .equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
				                hinhthuc = "Đặt trực tiếp";
				            } else {
				                hinhthuc = "Đặt trước";
				            }
				            if (dsPDPtheoNgay != null) {
				                String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
				                try {
									nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
								} catch (RemoteException e) {
									e.printStackTrace();
								}
				                hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
				                if (hd != null && !hd.isTrangThai()) {
				                    trangthai = "Chưa TT";
				                } else if (hd != null && hd.isTrangThai()) {
				                    trangthai = "Đã TT";
				                } else {
				                    trangthai = "Chưa TT";
				                }
				                if (pdp != null) {
				                    if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
				                            && check == 1) {
				                        btnTimKiem.setText("Hủy tìm");
				                        clearTable();
				                        XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
				                        check = 0;
				                    }
				                    Canh_Deu_Bang();
				                }
				            } else {
				                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				            }
				        }
				        if (check == 1) {
				            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
				        }
				    } else {
				        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
				    }
			    }
			    // Kiểm tra xem người dùng đã nhập vào một năm cụ thể hay không
			    else   if (ngaynhanStr.matches("^\\d{4}$")) {
			        int namNhan = Integer.parseInt(ngaynhanStr);
			        try {
						dsPDPtheoNgay = pdp_Service.getPDPTheoNamNhan(namNhan);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				    if (!dsPDPtheoNgay.isEmpty()) {
				        int check = 1;
				        for (PhieuDatPhong pdp : dsPDPtheoNgay) {
				            // so sánh mã hóa đơn tồn tại
				            if (formatter1.format(pdp.getNgayGioDatPhong())
				                    .equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
				                hinhthuc = "Đặt trực tiếp";
				            } else {
				                hinhthuc = "Đặt trước";
				            }
				            if (dsPDPtheoNgay != null) {
				                String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
				                try {
									nv = nv_dao.findByID(pdp.getNhanVien().getMaNhanVien());
								} catch (RemoteException e) {
									e.printStackTrace();
								}
				                hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
				                if (hd != null && !hd.isTrangThai()) {
				                    trangthai = "Chưa TT";
				                } else if (hd != null && hd.isTrangThai()) {
				                    trangthai = "Đã TT";
				                } else {
				                    trangthai = "Chưa TT";
				                }
				                if (pdp != null) {
				                    if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
				                            && check == 1) {
				                        btnTimKiem.setText("Hủy tìm");
				                        clearTable();
				                       
				                        XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
				                        check = 0;
				                    }
				                    Canh_Deu_Bang();
				                }
				            } else {
				                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				            }
				        }
				        if (check == 1) {
				            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
				        }
				    } else {
				        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
				    }
			    }
			    // Ngược lại, giả định người dùng đã nhập vào một ngày cụ thể
			    else {
			    	JOptionPane.showMessageDialog(this, "Vui lòng nhập Năm-Tháng-Ngày cần tìm. Bạn có thể nhập Năm, Năm-Tháng cần tìm!");
			    }
   
			}

			
			
//			else if (comboBox_TrangThai_1.getSelectedItem().equals("Ngày nhận phòng")) {
//			    DateTimeFormatter formatter_ngaynhan = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			    String ngaynhanStr = txtLoaiTimKiem.getText();
//			    LocalDate ngaynhan = LocalDate.parse(ngaynhanStr, formatter_ngaynhan);
//			    ArrayList<PhieuDatPhong> dsPDPtheoNgay = pdp_dao.getPDPTheoNgayNhan(ngaynhan);
//			    if (!dsPDPtheoNgay.isEmpty()) {
//			        int check = 1;
//			        for (PhieuDatPhong pdp : dsPDPtheoNgay) {
//			            // so sánh mã hóa đơn tồn tại
//			            if (formatter1.format(pdp.getNgayGioDatPhong())
//			                    .equals(formatter1.format(pdp.getNgayGioNhanPhong()))) {
//			                hinhthuc = "Đặt trực tiếp";
//			            } else {
//			                hinhthuc = "Đặt trước";
//			            }
//			            if (dsPDPtheoNgay != null) {
//			                String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
//			                nv = nv_dao.getNhanVienTheoMa(pdp.getNhanVien().getMaNhanVien());
//			                hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
//			                if (hd != null && hd.isTrangThai() == false) {
//			                    trangthai = "Chưa TT";
//			                } else if (hd != null && hd.isTrangThai() == true) {
//			                    trangthai = "Đã TT";
//			                } else {
//			                    trangthai = "Chưa TT";
//			                }
//			                if (pdp != null) {
//			                    if (XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai)
//			                            && check == 1) {
//			                        btnTimKiem.setText("Hủy tìm");
//			                        clearTable();
//			                        XuatDSTheoTrangThai(pdp, kh, ngayGioDat, ngayGioNhan, hinhthuc, trangthai);
//			                        check = 0;
//			                    }
//			                    Canh_Deu_Bang();
//			                }
//			            } else {
//			                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
//			            }
//			        }
//			        if (check == 1) {
//			            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
//			        }
//			    } else {
//			        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin");
//			    }
//			}


		} else {
			clearTable();
			loadData();
			btnTimKiem.setText("Tìm kiếm");
			Canh_Deu_Bang();
		}

	}

	private boolean XuatDSTheoTrangThai(PhieuDatPhong pdp, KhachHang kh, String ngayGioDat, String ngayGioNhan,
			String hinhThuc, String trangthai) {
		if (comboBox_TrangThai.getSelectedItem().equals("Tất Cả")) {
			Object[] row = { pdp.getMaPhieu(), pdp.getPhong().getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat,
					ngayGioNhan, pdp.getSoNguoiHat(), hinhThuc, trangthai };
			model.addRow(row);
			return true;
		} else if (comboBox_TrangThai.getSelectedItem().equals("Chưa Thanh Toán") && trangthai.equals("Chưa TT")) {
			Object[] row = { pdp.getMaPhieu(), pdp.getPhong().getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat,
					ngayGioNhan, pdp.getSoNguoiHat(), hinhThuc, trangthai };
			model.addRow(row);
			return true;
		} else if (comboBox_TrangThai.getSelectedItem().equals("Đã Thanh Toán") && trangthai.equals("Đã TT")) {
			Object[] row = { pdp.getMaPhieu(), pdp.getPhong().getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat,
					ngayGioNhan, pdp.getSoNguoiHat(), hinhThuc, trangthai };
			model.addRow(row);
			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btn_QuayLai)) {
			setVisible(false);
		}
		if (o.equals(btnTimKiem)) {
			try {
				tim();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btn_XemPhong)) {
			try {
				xemPhong();
			} catch (RemoteException | UnknownHostException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btn_XuatPhong)) {
			xuatExcel();
		}
		if (o.equals(btn_NhanPhong)) {
			try {
				nhanPhong();
			} catch (NumberFormatException | RemoteException | UnknownHostException e1) {
				e1.printStackTrace();
			}
			clearTable();
			try {
				loadData();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnLamMoi)) {
			comboBox_TrangThai_1.setSelectedIndex(0);
			txtLoaiTimKiem.setText("");
			comboBox_TrangThai.setSelectedIndex(0);
			clearTable();
			try {
				loadData();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btn_HuyPhong)) {
			try {
				try {
					HuyPhieu();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
//		if (o.equals(comboBox_TrangThai)) {
//			if (comboBox_TrangThai.getSelectedItem().equals("Tất Cả")) {
//				clearTable();
//				loadData();
//			}
//			if (comboBox_TrangThai.getSelectedItem().equals("Chưa Thanh Toán")) {
//				clearTable();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
//				String hinhthuc = "";
//				String trangthai = "";
//				// Lấy tất cả phiếu đặt phòng
//				ArrayList<PhieuDatPhong> allPhieuDatPhong = pdp_dao.getAllsPhieuDatPhong_ChuaThanhToan();
//
//				// Sắp xếp danh sách theo ngày giờ đặt phòng
//				Collections.sort(allPhieuDatPhong, Comparator.comparing(PhieuDatPhong::getNgayGioDatPhong));
//
//				for (PhieuDatPhong x : allPhieuDatPhong) {
//					String ngayGioDat = x.getNgayGioDatPhong().format(formatter);
//					String ngayGioNhan = x.getNgayGioNhanPhong().format(formatter);
//					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//					if (formatter1.format(x.getNgayGioNhanPhong()).equals(formatter1.format(x.getNgayGioDatPhong()))) {
//						hinhthuc = "Đặt trực tiếp";
//					} else {
//						hinhthuc = "Đặt trước";
//					}
//					String mp = x.getMaPhieu();
//					String mkh = x.getKhachHang().getMaKhachHang();
//					pdp = pdp_dao.getPhieuDatPhongTheoMaPDP(mp);
//					p = pdp.getPhong();
//					kh = kh_dao.getKhachHangTheoMaKH(mkh);
//					nv = nv_dao.getNhanVienTheoMa(pdp.getNhanVien().getMaNhanVien());
//					String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
//					hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
//					trangthai = "Chưa TT";
//					Object[] row = { x.getMaPhieu(), p.getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat,
//							ngayGioNhan, x.getSoNguoiHat(), hinhthuc, trangthai };
//					model.addRow(row);
//				}
//				Canh_Deu_Bang();
//
//			}
//			if (comboBox_TrangThai.getSelectedItem().equals("Đã Thanh Toán")) {
//				clearTable();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
//				String hinhthuc = "";
//				String trangthai = "";
//				// Lấy tất cả phiếu đặt phòng
//				ArrayList<PhieuDatPhong> allPhieuDatPhong = pdp_dao.getAllsPhieuDatPhong_DaThanhToan();
//
//				// Sắp xếp danh sách theo ngày giờ đặt phòng
//				Collections.sort(allPhieuDatPhong, Comparator.comparing(PhieuDatPhong::getNgayGioDatPhong));
//
//				for (PhieuDatPhong x : allPhieuDatPhong) {
//					String ngayGioDat = x.getNgayGioDatPhong().format(formatter);
//					String ngayGioNhan = x.getNgayGioNhanPhong().format(formatter);
//					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//					if (formatter1.format(x.getNgayGioNhanPhong()).equals(formatter1.format(x.getNgayGioDatPhong()))) {
//						hinhthuc = "Đặt trực tiếp";
//					} else {
//						hinhthuc = "Đặt trước";
//					}
//					String mp = x.getMaPhieu();
//					String mkh = x.getKhachHang().getMaKhachHang();
//					pdp = pdp_dao.getPhieuDatPhongTheoMaPDP(mp);
//					p = pdp.getPhong();
//					kh = kh_dao.getKhachHangTheoMaKH(mkh);
//					nv = nv_dao.getNhanVienTheoMa(pdp.getNhanVien().getMaNhanVien());
//					String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(pdp.getMaPhieu());
//					hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
//					trangthai = "Đã TT";
//					Object[] row = { x.getMaPhieu(), p.getMaPhong(), nv.getHoTen(), kh.getHoTen(), ngayGioDat,
//							ngayGioNhan, x.getSoNguoiHat(), hinhthuc, trangthai };
//					model.addRow(row);
//				}
//				Canh_Deu_Bang();
//
//			}
//		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void HuyPhieu() throws SQLException, RemoteException {
		int row = tblPhieuDatPhong.getSelectedRow();
		String maphong = (String) tblPhieuDatPhong.getValueAt(row, 1);
		String hinhthuc = (String) tblPhieuDatPhong.getValueAt(row, 7);
		String mp = (String) tblPhieuDatPhong.getValueAt(row, 0);
		String trangthai = "";
        String maHoaDon = hd_dao.getMaHDTheoMaPhieuDP(mp);
        hd = hd_dao.getHoaDonDatPhongTheoMaHD(maHoaDon);
        if (hd != null && !hd.isTrangThai()) {
            trangthai = "Chưa TT";
        } else if (hd != null && hd.isTrangThai()) {
            trangthai = "Đã TT";
        } else {
            trangthai = "Chưa TT";
        }
		if (row != 1) {
			if (hinhthuc.equals("Đặt trước") && trangthai.equals("Chưa TT")) {
				int tb = JOptionPane.showConfirmDialog(null, "Bạn có hủy phòng?", "Hủy phòng chờ",
						JOptionPane.YES_NO_OPTION);
				if (tb == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(this, "Phòng hủy thành công!");
					pdp_Service.xoaPhieuDatPhongTheoMa(maphong);
					Map<String, Boolean> loadData = clientConnectionService.getLoadData();
					Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
					String mnv = "";
					for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
						if (entry.getKey().equals(ip.getHostAddress())) {
							mnv = entry.getValue();
						}
					}
 					
					for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
							entry.setValue(true);
					}
					clientConnectionService.setLoadData(loadData);
					Enum_TrangThai trangThai = Enum_TrangThai.Trong;
					Phong phong = new Phong(maphong, trangThai);
					try {
						p_Service.updatePhong(phong, maphong);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					model.removeRow(row);
				}
			} else if (hinhthuc.equals("Đặt trước") && trangthai.equals("Đã TT")) {
				JOptionPane.showMessageDialog(null, "Phòng đặt trước đã thanh toán nên thể không thể hủy!");
			}

			else {
				JOptionPane.showMessageDialog(null, "Phòng đặt trực tiếp không thể hủy!");
				// (hinhthuc.equals("Đặt trực tiếp") && trangthai.equals("Đã TT") ||
				// hinhthuc.equals("Đặt trực tiếp") && trangthai.equals("Chưa TT")) {
			}
		} else {
			JOptionPane.showMessageDialog(null, "chưa chọn phòng hủy!");
		}

	}

	public void xemPhong() throws RemoteException, UnknownHostException {
		int row = tblPhieuDatPhong.getSelectedRow();
		String maphong = (String) tblPhieuDatPhong.getValueAt(row, 1);
		String hinhthuc = (String) tblPhieuDatPhong.getValueAt(row, 7);
		String trangthai = (String) tblPhieuDatPhong.getValueAt(row, 8);
		String maPDP = (String) tblPhieuDatPhong.getValueAt(row, 0);
		try {
			p = p_Service.getPhongTheoMaPhong(maphong);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (row != -1) {
			if (hinhthuc.equals("Đặt trước") && p.getTrangThai() == Enum_TrangThai.Cho) {
				try {
					dialog_PhongCho = new Dialog_PhongCho(maphong, trangChu);
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					e.printStackTrace();
				}
				Map<String, Boolean> loadData = clientConnectionService.getLoadData();
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				clientConnectionService.setLoadData(loadData);
				dialog_PhongCho.setModal(true);
				dialog_PhongCho.setVisible(true);
			} else if (hinhthuc.equals("Đặt trước") && p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
				try {
					dialog_PhongDangSD = new Dialog_PhongDangSD(maphong, null);
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					e.printStackTrace();
				}
				Map<String, Boolean> loadData = clientConnectionService.getLoadData();
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				clientConnectionService.setLoadData(loadData);
				dialog_PhongDangSD.setModal(true);
				dialog_PhongDangSD.setVisible(true);
			} else if (hinhthuc.equals("Đặt trực tiếp") && trangthai.equals("Chưa TT")
					&& p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
				try {
					dialog_PhongDangSD = new Dialog_PhongDangSD(maphong, null);
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					e.printStackTrace();
				}
				Map<String, Boolean> loadData = clientConnectionService.getLoadData();
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				clientConnectionService.setLoadData(loadData);
				dialog_PhongDangSD.setModal(true);
				dialog_PhongDangSD.setVisible(true);

			} else {
				try {
					dialog_TimPDP_DaThanhToan = new Dialog_TimPDP_DaThanhToan(maphong, maPDP);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Map<String, Boolean> loadData = clientConnectionService.getLoadData();
				Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
				String mnv = "";
				for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
					if (entry.getKey().equals(ip.getHostAddress())) {
						mnv = entry.getValue();
					}
				}
					
				for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
						entry.setValue(true);
				}
				clientConnectionService.setLoadData(loadData);
				dialog_TimPDP_DaThanhToan.setModal(true);
				dialog_TimPDP_DaThanhToan.setVisible(true);
			}

		} else
			JOptionPane.showMessageDialog(null, "chưa chọn phòng chờ hiển thị!");
	}

	public void nhanPhong() throws NumberFormatException, RemoteException, UnknownHostException {
		int row = tblPhieuDatPhong.getSelectedRow();
		String maphong = tblPhieuDatPhong.getValueAt(row, 1).toString();
		String songuoi = tblPhieuDatPhong.getValueAt(row, 6).toString();
		try {
			pdp = pdp_Service.getPDPDatTruocTheoMaPhong(maphong);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			p = p_Service.getPhongTheoMaPhong(maphong);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
		kh = kh_dao.getKhachHangTheoMaKH(pdp.getKhachHang().getMaKhachHang());
		String hinhthuc = (String) tblPhieuDatPhong.getValueAt(row, 7);
//		String trangthai = (String) tblPhieuDatPhong.getValueAt(row, 8);

		if (row != 1) {
			if (hinhthuc.equals("Đặt trước") && p.getTrangThai() == Enum_TrangThai.Cho) {

				// giờ phút hiện tại
				int gio_ht = LocalDateTime.now().getHour();
				int phut_ht = LocalDateTime.now().getMinute();
				int tongsophut_ht = gio_ht * 60 + phut_ht;
				// giờ phút nhận phòng
				try {
					pdp = pdp_Service.getPDPDatTruocTheoMaPhong(maphong);
				} catch (RemoteException e) {
					e.printStackTrace();
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
						TempDatPhong tmp = new TempDatPhong(p.getMaPhong(), Integer.parseInt(songuoi));
						tmp_dao.addTemp(tmp);
						try {
							try {
								dialog_DatPhongTrong_2 = new Dialog_DatPhongTrong_2(maphong, p, lp, Integer.parseInt(songuoi),
										trangChu);
							} catch (UnknownHostException | MalformedURLException | NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						dispose();
						JOptionPane.showMessageDialog(this,
								"Phòng " + p.getMaPhong() + " được thêm vào danh sách đặt phòng thành công.");
						DataManager.setSoDienThoaiKHDat("");
						dialog_DatPhongTrong_2.setModal(true);
						dialog_DatPhongTrong_2.setVisible(true);
					} else if (tongsophut_np - tongsophut_ht < -30) {
						// Khách hàng đến trễ hơn giờ nhận phòng 30 phút
						// Thực hiện công việc B
						JOptionPane.showMessageDialog(this, "Phòng hủy do đến trễ quá 30 phút!");
						pdp_Service.xoaPhieuDatPhongTheoMa(maphong);
						Map<String, Boolean> loadData = clientConnectionService.getLoadData();
						Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
						String mnv = "";
						for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
							if (entry.getKey().equals(ip.getHostAddress())) {
								mnv = entry.getValue();
							}
						}
	 					
						for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
								entry.setValue(true);
						}
						clientConnectionService.setLoadData(loadData);
						Enum_TrangThai trangThai = Enum_TrangThai.Trong;
						Phong phong = new Phong(maphong, trangThai);
						p_Service.updatePhong(phong, maphong);
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
					pdp_Service.xoaPhieuDatPhongTheoMa(maphong);
					Map<String, Boolean> loadData = clientConnectionService.getLoadData();
					Map<String, String> mapIP_MSNV = clientConnectionService.getMapIP_MSNV();
					String mnv = "";
					for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
						if (entry.getKey().equals(ip.getHostAddress())) {
							mnv = entry.getValue();
						}
					}
 					
					for (Map.Entry<String, Boolean> entry : loadData.entrySet()) {
							entry.setValue(true);
					}
					clientConnectionService.setLoadData(loadData);
					Enum_TrangThai trangThai = Enum_TrangThai.Trong;
					Phong phong = new Phong(maphong, trangThai);
					p_Service.updatePhong(phong, maphong);
				}

				// DataManager.setSoDienThoaiKHDat(kh.getSoDienThoai());
			} else {
				JOptionPane.showMessageDialog(null, "Phòng bạn chọn là phòng đang sử dụng nên không thể nhận !");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Chưa chọn phòng chờ để nhận!");
		}
	}

	public void xuatExcel() {
		try {
			wordbook = new XSSFWorkbook();
			XSSFSheet sheet = wordbook.createSheet("Danh sách phiếu đặt phòng");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");

			XSSFCellStyle style = wordbook.createCellStyle();
			// Đặt chữ in đậm
			XSSFFont font = wordbook.createFont();
			font.setBold(true);
			style.setFont(font);
			// Căn giữa
			style.setAlignment(HorizontalAlignment.CENTER);
			XSSFRow row = null;
			Cell cell = null;
			String hinhthuc;
			String trangthai;
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			row = sheet.createRow(2);// Tạo 2 dòng trống trong excel
			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("STT");
			cell = row.createCell(1, CellType.STRING);
			cell.setCellValue("Mã PDP");
			cell = row.createCell(2, CellType.STRING);
			cell.setCellValue("Phòng");
			cell = row.createCell(3, CellType.STRING);
			cell.setCellValue("Tên NV");
			cell = row.createCell(4, CellType.STRING);
			cell.setCellValue("Tên KH");
			cell = row.createCell(5, CellType.STRING);
			cell.setCellValue("Ngày Giờ Đặt");
			cell = row.createCell(6, CellType.STRING);
			cell.setCellValue("Ngày Giờ Nhận");
			cell = row.createCell(7, CellType.STRING);
			cell.setCellValue("Số Người");
			cell = row.createCell(8, CellType.STRING);
			cell.setCellValue("Hình Thức");
			cell = row.createCell(9, CellType.STRING);
			cell.setCellValue("Trạng Thái");

			for (int i = 0; i < pdp_Service.getAllsPhieuDatPhong().size(); i++) {
				String ngayGioDat = pdp_Service.getAllsPhieuDatPhong().get(i).getNgayGioDatPhong().format(formatter);
				String ngayGioNhan = pdp_Service.getAllsPhieuDatPhong().get(i).getNgayGioNhanPhong().format(formatter);

				if (formatter1.format(pdp_Service.getAllsPhieuDatPhong().get(i).getNgayGioNhanPhong())
						.equals(formatter1.format(pdp_Service.getAllsPhieuDatPhong().get(i).getNgayGioDatPhong()))) {
					hinhthuc = "Đặt trực tiếp";
				} else {
					hinhthuc = "Đặt trước";
				}

				String mkh = pdp_Service.getAllsPhieuDatPhong().get(i).getKhachHang().getMaKhachHang();
				p = pdp.getPhong();
				kh = kh_dao.getKhachHangTheoMaKH(mkh);
				nv = nv_dao.findByID(pdp_Service.getAllsPhieuDatPhong().get(i).getNhanVien().getMaNhanVien());
				row = sheet.createRow(3 + i); // Bỏ qua 2 dòng trống
				cell = row.createCell(0, CellType.NUMERIC);
				cell.setCellValue(i + 1);

				cell = row.createCell(1, CellType.STRING);
				cell.setCellValue(pdp_Service.getAllsPhieuDatPhong().get(i).getMaPhieu());

				cell = row.createCell(2, CellType.STRING);
				String mp = pdp_Service.getAllsPhieuDatPhong().get(i).getMaPhieu();
				pdp = pdp_Service.getPhieuDatPhongTheoMaPDP(mp);
				p = pdp.getPhong();
				cell.setCellValue(p.getMaPhong());

				cell = row.createCell(3, CellType.STRING);

				cell.setCellValue(nv.getHoTen());

				cell = row.createCell(4, CellType.STRING);
				cell.setCellValue(kh.getHoTen());

				cell = row.createCell(5, CellType.STRING);
				cell.setCellValue(ngayGioDat);

				cell = row.createCell(6, CellType.STRING);
				cell.setCellValue(ngayGioNhan);

				cell = row.createCell(7, CellType.STRING);
				cell.setCellValue(pdp_Service.getAllsPhieuDatPhong().get(i).getSoNguoiHat());

				cell = row.createCell(8, CellType.STRING);
				cell.setCellValue(hinhthuc);

				if (hd != null && hd.getTienKhachDua() > 0) {
					trangthai = "Đã TT";
				} else if (hd != null && hd.getTienKhachDua() == 0) {
					trangthai = "Chưa TT";
				} else {
					trangthai = "Chưa TT";
				}
				cell = row.createCell(9, CellType.STRING);
				cell.setCellValue(trangthai);
			}

			File file = new File("LuuFile_Excel\\DanhSach.xlsx");
			try {
				FileOutputStream file_out = new FileOutputStream(file);
				wordbook.write(file_out);
				file_out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(this, "In file danh sách thành công!!");
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Không in được");
		}
	}
}