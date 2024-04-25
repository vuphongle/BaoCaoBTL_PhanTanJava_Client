package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietDichVuServices;
import dao.ChiTietHoaDonServices;
import dao.PhongService;
import dao.SanPhamService;
import entity.ChiTietDichVu;
import entity.ChiTietHoaDon;
import entity.Enum_TrangThai;
import entity.HoaDonDatPhong;
import entity.Phong;
import entity.SanPham;
import utils.TempThemDV;

import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Dialog_ThemDichVu extends JDialog implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel panel;
    private final JPanel panel_Phai;
    private final JPanel panel_Trai;
    private final JPanel panel_1;
    private final JPanel pn_dssp;
    private final JPanel pn_dssp_Phai;
	private final JLabel lblSoLuong;
    private final JLabel lblTenNV;
    private final JLabel lblPhong;
	private final JComboBox<String> cbTimKiem;
	private final JTextField txtTenSP;
    private final JTextField txtSoLuong;
	private final JButton btnTimKiem;
    private final JButton btn_DongY;
    private final JButton btn_Huy;

	// bảng trái
	private final JTable tblThemDv_Trai;
    private final JTable tblThemDv_Phai;
	private final DefaultTableModel model_Trai;
    private final DefaultTableModel model_Phai;
	private final JLabel lblNV;
	private final String[] col_Trai = { "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá" };
	private final String[] col_Phai = { "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", };
	private final JScrollPane sp_phai;
	private final JScrollPane sp;
	private final JButton btn_LamMoi;
	private final JLabel lblTongTien;
	private final JTextField txtTongTien;
	private SanPhamService sp_Service;
//	private JButton btn_XoaDV;
	private final JButton btn_Them;
	private final DecimalFormat df;
	private final String ma;
	private double tongTien;
	private final PhongService p_Service;
	private  ChiTietDichVuServices ctdv_dao;
	@SuppressWarnings("unused")
	private String maHoaDon = "";
	private  ChiTietHoaDonServices cthd_dao;

	private final JLabel lblPhong1;

	public Dialog_ThemDichVu(String customer, String employee, String maPhong) throws RemoteException, MalformedURLException, NotBoundException {
		this.ma = maPhong;
		try {
			cthd_dao = (ChiTietHoaDonServices) Naming.lookup(DataManager.getRmiURL() + "chiTietHoaDonServices");
		}catch(RemoteException e) {
			e.printStackTrace();
		}
		df = new DecimalFormat("#,###,### VNĐ");
		getContentPane().setBackground(Color.WHITE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
		this.setIconImage(icon.getImage());
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		ctdv_dao = (ChiTietDichVuServices) Naming.lookup(DataManager.getRmiURL() + "chiTietDichVuServices");
		sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");


		// panel chứa tiêu đề--------------------------------------
		panel = new JPanel();
		panel.setBounds(0, 0, 784, 35);
		panel.setBackground(new Color(33, 167, 38, 255));
		getContentPane().add(panel);
		panel.setLayout(null);
		getContentPane().add(panel);
		JLabel lblTieuDe = new JLabel("Thêm Dịch Vụ");
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLACK);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 18));
		lblTieuDe.setBounds(0, 0, 790, 35);
		panel.add(lblTieuDe);

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.control);
		panel_1.setBounds(0, 35, 784, 426);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		panel_Trai = new JPanel();
		panel_Trai.setBackground(SystemColor.menu);
		panel_Trai.setBounds(0, 0, 370, 375);
		panel_1.add(panel_Trai);
		panel_Trai.setLayout(null);

		panel_Phai = new JPanel();
		panel_Phai.setBackground(SystemColor.menu);
		panel_Phai.setLayout(null);
		panel_Phai.setBounds(390, 0, 394, 375);
		panel_1.add(panel_Phai);

		// lbl và các thành phần góc phải
		JLabel lblKH = new JLabel("Khách hàng");
		lblKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKH.setBounds(5, 10, 89, 25);
		panel_Phai.add(lblKH);

		lblPhong = new JLabel("Phòng");
		lblPhong.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPhong.setBounds(230, 10, 50, 25);
		panel_Phai.add(lblPhong);

		lblNV = new JLabel("Nhân viên");
		lblNV.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNV.setBounds(5, 50, 89, 25);
		panel_Phai.add(lblNV);

		lblKH = new JLabel(customer);
		lblKH.setFont(new Font("Arial", Font.ITALIC, 14));
		lblKH.setBounds(105, 10, 115, 25);
		panel_Phai.add(lblKH);

		lblTenNV = new JLabel(employee);
		lblTenNV.setFont(new Font("Arial", Font.ITALIC, 14));
		lblTenNV.setBounds(105, 50, 115, 25);
		panel_Phai.add(lblTenNV);

		lblPhong1 = new JLabel(maPhong);
		lblPhong1.setFont(new Font("Arial", Font.BOLD, 18));
		lblPhong1.setBounds(285, 10, 100, 25);
		panel_Phai.add(lblPhong1);

		pn_dssp_Phai = new JPanel();
		pn_dssp_Phai.setLayout(null);
		pn_dssp_Phai.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), "Dịch vụ hiện có",
						TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Arial", Font.BOLD, 13), Color.black));
		pn_dssp_Phai.setBounds(0, 90, 393, 242);
		pn_dssp_Phai.setBackground(SystemColor.menu);
		// bảng chuyển phòng phải
		model_Phai = new DefaultTableModel(col_Phai, 0);
		tblThemDv_Phai = new JTable(model_Phai);
		tblThemDv_Phai.setFont(new Font("Arial", Font.PLAIN, 12));
		tblThemDv_Phai.setBackground(Color.WHITE);
		sp_phai = new JScrollPane(tblThemDv_Phai);
		sp_phai.setBounds(4, 15, 385, 222);
		pn_dssp_Phai.add(sp_phai);
		pn_dssp_Phai.setPreferredSize(new Dimension(390, 200));
		panel_Phai.add(pn_dssp_Phai);

		btn_LamMoi = new JButton("Làm Mới");
		btn_LamMoi.setIcon(new ImageIcon("icon\\Refresh_icon.png"));
		btn_LamMoi.setFont(new Font("Arial", Font.BOLD, 12));
		btn_LamMoi.setBackground(new Color(252, 155, 78));
		btn_LamMoi.setBounds(5, 335, 110, 30);
		panel_Phai.add(btn_LamMoi);

		lblTongTien = new JLabel("Tổng Tiền DV:");
		lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTongTien.setBounds(130, 335, 105, 30);
		panel_Phai.add(lblTongTien);

		txtTongTien = new JTextField();
		txtTongTien.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTongTien.setColumns(20);
		txtTongTien.setBounds(240, 335, 145, 30);
		panel_Phai.add(txtTongTien);

//------------------------------------------------------------------------------------------------------------------
		// --- các thành phần panel trái
		cbTimKiem = new JComboBox<>();
		cbTimKiem.setFont(new Font("Tahoma", Font.BOLD, 14));
		cbTimKiem.setBounds(5, 5, 130, 30);
		cbTimKiem.addItem("Mã sản phẩm");
		cbTimKiem.addItem("Tên sản phẩm");
		panel_Trai.add(cbTimKiem);

		txtTenSP = new JTextField();
		txtTenSP.setBounds(145, 5, 110, 30);
		panel_Trai.add(txtTenSP);
		txtTenSP.setColumns(20);

		ImageIcon iconTimKiem = new ImageIcon("icon\\Research_icon.png");
		Image originalImage_iconTimKiem = iconTimKiem.getImage();
		Image resizedImage_iconTimKiem = originalImage_iconTimKiem.getScaledInstance(16, 16,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon_iconTimKiem = new ImageIcon(resizedImage_iconTimKiem);

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnTimKiem.setIcon(resizedIcon_iconTimKiem);
		btnTimKiem.setBounds(260, 5, 110, 30);
		btnTimKiem.setBackground(new Color(13, 153, 255, 255));
		panel_Trai.add(btnTimKiem);

		pn_dssp = new JPanel();
		pn_dssp.setLayout(null);
		pn_dssp.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), "Danh sách sản phẩm",
						TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Arial", Font.BOLD, 13), Color.black));
		pn_dssp.setBounds(0, 40, 370, 297);
		pn_dssp.setBackground(SystemColor.menu);
		// bảng chuyển phòng
		model_Trai = new DefaultTableModel(col_Trai, 0);
		tblThemDv_Trai = new JTable(model_Trai);
		tblThemDv_Trai.setFont(new Font("Arial", Font.PLAIN, 12));
		tblThemDv_Trai.setBackground(Color.WHITE);
		tblThemDv_Trai.getColumnModel().getColumn(0).setMaxWidth(60);
		tblThemDv_Trai.getColumnModel().getColumn(1).setMaxWidth(160);
		tblThemDv_Trai.getColumnModel().getColumn(2).setMaxWidth(70);
		tblThemDv_Trai.getColumnModel().getColumn(3).setMaxWidth(70);
		sp = new JScrollPane(tblThemDv_Trai);
		sp.setBounds(5, 15, 360, 277);
		pn_dssp.add(sp);
		pn_dssp.setPreferredSize(new Dimension(370, 280));
		panel_Trai.add(pn_dssp);

		lblSoLuong = new JLabel("Nhập số lượng:");
		lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSoLuong.setBounds(180, 340, 115, 25);
		panel_Trai.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setColumns(20);
		txtSoLuong.setBounds(300, 340, 65, 25);
		panel_Trai.add(txtSoLuong);

		btn_Them = new JButton("Thêm");
		btn_Them.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Them.setBackground(UIManager.getColor("Button.light"));
		btn_Them.setBounds(10, 340, 100, 30);
		panel_Trai.add(btn_Them);

		btn_DongY = new JButton("Đồng Ý");
		btn_DongY.setFont(new Font("Arial", Font.BOLD, 18));
		btn_DongY.setBackground(new Color(33, 167, 38, 255));
		btn_DongY.setBounds(100, 380, 250, 40);
		panel_1.add(btn_DongY);

		btn_Huy = new JButton("Hủy");
		btn_Huy.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Huy.setBackground(new Color(255, 83, 83));
		btn_Huy.setBounds(420, 380, 250, 40);
		panel_1.add(btn_Huy);

		loadDataPDSD();

		// thêm sự kiện
		btn_DongY.addActionListener(this);
		btn_Huy.addActionListener(this);
		btn_LamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);

		Object[] selectedRowData = new Object[model_Trai.getColumnCount() + 1];

		// Cập nhật dữ liệu khi chọn một hàng mới từ bảng
		tblThemDv_Trai.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = tblThemDv_Trai.getSelectedRow(); // Lấy hàng được chọn

				// Lấy dữ liệu từ hàng được chọn
				for (int i = 0; i < model_Trai.getColumnCount(); i++) {
					selectedRowData[i] = model_Trai.getValueAt(selectedRow, i);
				}
			}
		});

		// Xóa tất cả ActionListener hiện có từ nút 'btnThem'
		for (ActionListener act : btn_Them.getActionListeners()) {
			btn_Them.removeActionListener(act);
		}

		// Thêm ActionListener vào nút 'btnThem'
		btn_Them.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lấy số lượng từ 'txtSoLuong'
				int selectedRow = tblThemDv_Trai.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!");
				} else {
					int soLuong = 0;
					try {
						soLuong = Integer.parseInt(txtSoLuong.getText());
						if (soLuong > 0) {
							int soLuongTon = Integer
									.parseInt(model_Trai.getValueAt(tblThemDv_Trai.getSelectedRow(), 2).toString());
							// Thay đổi số lượng trong dữ liệu
							if (soLuong < soLuongTon) {
								String maSP = model_Trai.getValueAt(selectedRow, 0).toString(); // Assuming maSP is in
																								// the
								// first column
								boolean found = false;
								for (int i = 0; i < model_Phai.getRowCount(); i++) {
									if (model_Phai.getValueAt(i, 0).equals(maSP)) { // Assuming maSP is in the first
																					// column
										int existingSoLuong = Integer.parseInt(
												model_Phai.getValueAt(i, selectedRowData.length - 3).toString());
										model_Phai.setValueAt(existingSoLuong + soLuong, i, selectedRowData.length - 3);
										found = true;
										break;
									}
								}
								if (!found) {
//					                selectedRowData[selectedRowData.length - 3] = soLuong;
									int currentRow = tblThemDv_Trai.getSelectedRow();

									Object[] row = { model_Trai.getValueAt(currentRow, 0),
											model_Trai.getValueAt(currentRow, 1), soLuong,
											model_Trai.getValueAt(currentRow, 3),

									};
									model_Phai.addRow(row);
								}
								soLuongTon -= soLuong;
								model_Trai.setValueAt(soLuongTon, selectedRow, 2);
								capNhatTongTien();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Nhập không hợp lệ");
					}
				}
			}
		});

		Phong p = p_Service.getPhongTheoMaPhong(ma);
		if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
			loadData_DangSD();
		} else {
			loadData_DatPhong();
		}

	}

	public void loadDataPDSD() {
		Phong p = null;
		try {
			p = p_Service.getPhongTheoMaPhong(ma);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
				ChiTietHoaDon cthd_hienTaiCuaPhong1 = null;
				List<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaPhong(ma);
				for (ChiTietHoaDon cthd : dsCTHD) {
					cthd_hienTaiCuaPhong1 = cthd;
				}

				ChiTietDichVu ctdv_hienTaiCuaPhong2 = null;
				List<ChiTietDichVu> dsCTDV = ctdv_dao.getChiTietDichVuTheoMaPhong(ma);
				for (ChiTietDichVu ctdv : dsCTDV) {
					ctdv_hienTaiCuaPhong2 = ctdv;
				}

				if (ctdv_hienTaiCuaPhong2 != null) {
					if (cthd_hienTaiCuaPhong1.getHoaDon().getMaHoaDon()
							.equals(ctdv_hienTaiCuaPhong2.getHoaDon().getMaHoaDon())) {
						List<ChiTietDichVu> dsctdv = ctdv_dao
								.getChiTietDichVuTheoMaHD(ctdv_hienTaiCuaPhong2.getHoaDon().getMaHoaDon());
						maHoaDon = ctdv_hienTaiCuaPhong2.getHoaDon().getMaHoaDon();
						for (ChiTietDichVu ctdv : dsctdv) {
							if (ctdv.getPhong().getMaPhong().equals(ma)) {
								SanPham sp = sp_Service.getSanPhamTheoMaSP(ctdv.getSanPham().getMaSanPham());
								Object[] row = { ctdv.getSanPham().getMaSanPham(), sp.getTenSanPham(),
										ctdv.getSoLuong(), ctdv.getGia() };
								model_Phai.addRow(row);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn lại phòng cần thêm dịch vụ");
		}
	}

	public void loadData_DangSD() {
		try {
			try {
				sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (SanPham x : sp_Service.getAllSanPhams()) {
				Object[] row = { x.getMaSanPham(), x.getTenSanPham(), x.getSoLuongTon(), x.getDonGia() };
				model_Trai.addRow(row);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadData_DatPhong() {
		try {
			try {
				sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (SanPham x : sp_Service.getAllSanPhams()) {
				if (DataManager.getCtdvTempList() != null) {
					int soLuong = x.getSoLuongTon();
					for (TempThemDV tmp : DataManager.getCtdvTempList()) {
						if (tmp.getMaSP().equals(x.getMaSanPham())) {
							soLuong -= tmp.getSoLuong();
						}
					}
					Object[] row = { x.getMaSanPham(), x.getTenSanPham(), soLuong, x.getDonGia() };
					model_Trai.addRow(row);
				} else {
					Object[] row = { x.getMaSanPham(), x.getTenSanPham(), x.getSoLuongTon(), x.getDonGia() };
					model_Trai.addRow(row);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadDataTheoMa(ArrayList<SanPham> ds) {
		for (SanPham x : ds) {
			Object[] row = { x.getMaSanPham(), x.getTenSanPham(), x.getSoLuongTon(), x.getDonGia() };
			model_Trai.addRow(row);

		}
	}

	public void loadDataTheoTenSP(List<SanPham> ds) {
		for (SanPham x : ds) {
			Object[] row = { x.getMaSanPham(), x.getTenSanPham(), x.getSoLuongTon(), x.getDonGia() };
			model_Trai.addRow(row);

		}
	}

	// tổng tiền dịch vụ
	public void capNhatTongTien() {
		double tongTien = 0;

		// Giả sử cột đơn giá nằm ở vị trí 3 và cột số lượng nằm ở vị trí 2
		for (int i = 0; i < model_Phai.getRowCount(); i++) {
			double donGia = Double.parseDouble(model_Phai.getValueAt(i, 3).toString());
			int soLuong = Integer.parseInt(model_Phai.getValueAt(i, 2).toString());
			tongTien += donGia * soLuong;
		}

		this.tongTien = tongTien;

		// Đặt tổng tiền vào txtTongTien
		txtTongTien.setText(df.format(tongTien));
		txtTongTien.setEnabled(false);
	}

	public void clearTable() {
		while (tblThemDv_Trai.getRowCount() > 0) {
			model_Trai.removeRow(0);
		}
	}

	public void tim() {
		try {
			try {
				sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (btnTimKiem.getText().equals("Tìm kiếm")) {
			if (cbTimKiem.getSelectedItem().equals("Mã sản phẩm")) {
				SanPham sp = null;
				try {
					sp = sp_Service.getSanPhamTheoMaSP(txtTenSP.getText());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (sp != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					Object[] row = { sp.getMaSanPham(), sp.getTenSanPham(), sp.getSoLuongTon(), sp.getDonGia() };
					model_Trai.addRow(row);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}


			} else if (cbTimKiem.getSelectedItem().equals("Tên sản phẩm")) {
				List<SanPham> ds = null;
				try {
					ds = sp_Service.getSanPhamTheoTenSanPham(txtTenSP.getText());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (ds != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					loadDataTheoTenSP(ds);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			}
		}else {
			clearTable();
			loadData_DatPhong();
			btnTimKiem.setText("Tìm kiếm");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btn_Huy)) {
			setVisible(false);
		}
//		        Window[] windows = Window.getWindows();
//		        for (Window window : windows) {
//		            if (window instanceof JDialog) {
//		                window.dispose();
//		            }
//		        }
//		    }
		if (o.equals(btn_DongY)) {
			int flag = 0;
			Phong p = null;
			try {
				p = p_Service.getPhongTheoMaPhong(ma);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (p.getTrangThai() == Enum_TrangThai.Trong || p.getTrangThai() == Enum_TrangThai.Cho) {
				if (DataManager.getCtdvTempList() != null) {
					ArrayList<TempThemDV> ctdvTempList = DataManager.getCtdvTempList();
					for (int i = 0; i < model_Phai.getRowCount(); i++) {

						for (TempThemDV temp : ctdvTempList) {
							if (temp.getMaPhong().equals(ma)
									&& temp.getMaSP().equals(model_Phai.getValueAt(i, 0).toString())) {
								temp.setSoLuong(
										temp.getSoLuong() + Integer.parseInt(model_Phai.getValueAt(i, 2).toString()));
								flag = 1;
							}
						}
						if (flag != 1) {
							TempThemDV ctdv = new TempThemDV(ma, model_Phai.getValueAt(i, 0).toString(), // Replace with
																											// the
									// actual column
									// indices
									model_Phai.getValueAt(i, 1).toString(), // and types if they are not strings
									Integer.parseInt(model_Phai.getValueAt(i, 2).toString()),
									Double.parseDouble(model_Phai.getValueAt(i, 3).toString()));
							ctdvTempList.add(ctdv);
							flag = 0;
						}
					}

					DataManager.setCtdvTempList(ctdvTempList);
					double tongTienOld = DataManager.getTongTienDV();
					DataManager.setTongTienDV(tongTienOld + tongTien);
				} else if (DataManager.getCtdvTempList() == null) {
					ArrayList<TempThemDV> ctdvTempList = new ArrayList<TempThemDV>();
					for (int i = 0; i < model_Phai.getRowCount(); i++) {
						// Assuming ChiTietDichVu has a constructor that accepts the column values as
						// parameters
						TempThemDV ctdv = new TempThemDV(ma, model_Phai.getValueAt(i, 0).toString(), // Replace with the
								// actual column
								// indices
								model_Phai.getValueAt(i, 1).toString(), // and types if they are not strings
								Integer.parseInt(model_Phai.getValueAt(i, 2).toString()),
								Double.parseDouble(model_Phai.getValueAt(i, 3).toString()));

						ctdvTempList.add(ctdv);
					}
					DataManager.setCtdvTempList(ctdvTempList);
					DataManager.setTongTienDV(tongTien);
				}
			} else if (p.getTrangThai() == Enum_TrangThai.Dang_su_dung) {
				ArrayList<TempThemDV> ctdvTempList = new ArrayList<TempThemDV>();
				for (int i = 0; i < model_Phai.getRowCount(); i++) {
					// Assuming ChiTietDichVu has a constructor that accepts the column values as
					// parameters
					TempThemDV ctdv = new TempThemDV(ma, model_Phai.getValueAt(i, 0).toString(), // Replace with the
							// actual column
							// indices
							model_Phai.getValueAt(i, 1).toString(), // and types if they are not strings
							Integer.parseInt(model_Phai.getValueAt(i, 2).toString()),
							Double.parseDouble(model_Phai.getValueAt(i, 3).toString()));

					ctdvTempList.add(ctdv);
					for (int row = 0; row < tblThemDv_Trai.getRowCount(); row++) {
						try {
							for (SanPham sp : sp_Service.getAllSanPhams()) {
								if (sp.getMaSanPham().equals(model_Trai.getValueAt(row, 0).toString())) {
									sp_Service.updateSLTon(Integer.parseInt(model_Trai.getValueAt(row, 2).toString()),
											sp.getMaSanPham());
									break;
								}
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				ChiTietHoaDon cthd_hienTaiCuaPhong = null;
				List<ChiTietHoaDon> dsCTHD = new ArrayList<ChiTietHoaDon>();
				try {
					dsCTHD = cthd_dao.getChiTietHoaDonTheoMaPhong(lblPhong1.getText().trim());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (ChiTietHoaDon cthd : dsCTHD) {
					cthd_hienTaiCuaPhong = cthd;
				}

				DataManager.setCtdvTempList(ctdvTempList);
				for (TempThemDV tmp : DataManager.getCtdvTempList()) {
					try {
						if (sp_Service.getLoaiSanPhamTheoMaSP(tmp.getMaSP()).equals("Thức ăn")) {
							ChiTietDichVu ctdv = new ChiTietDichVu(
									new HoaDonDatPhong(cthd_hienTaiCuaPhong.getHoaDon().getMaHoaDon()),
									new Phong(tmp.getMaPhong()), new SanPham(tmp.getMaSP()), tmp.getSoLuong(),
									tmp.getDonGia() * 1.03);
							try {
								ctdv_dao.addChiTietDV(ctdv);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else
							try {
								if (sp_Service.getLoaiSanPhamTheoMaSP(tmp.getMaSP()).equals("Đồ uống")) {
									ChiTietDichVu ctdv = new ChiTietDichVu(
											new HoaDonDatPhong(cthd_hienTaiCuaPhong.getHoaDon().getMaHoaDon()),
											new Phong(tmp.getMaPhong()), new SanPham(tmp.getMaSP()), tmp.getSoLuong(),
											tmp.getDonGia() * 1.02);
									try {
										ctdv_dao.addChiTietDV(ctdv);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									ChiTietDichVu ctdv = new ChiTietDichVu(
											new HoaDonDatPhong(cthd_hienTaiCuaPhong.getHoaDon().getMaHoaDon()),
											new Phong(tmp.getMaPhong()), new SanPham(tmp.getMaSP()), tmp.getSoLuong(),
											tmp.getDonGia() * 1.01);
									try {
										ctdv_dao.addChiTietDV(ctdv);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
			DataManager.setLoadDV(true);
			setVisible(false);
		}
		if (o.equals(btnTimKiem)) {
			tim();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
