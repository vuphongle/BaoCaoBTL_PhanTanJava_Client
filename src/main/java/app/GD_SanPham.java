package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import dao.SanPhamService;
import entity.SanPham;
import utils.DateLabelFormatter;

public class GD_SanPham extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	Font font = new Font("Arial", Font.BOLD, 16); // SPung tittle
	Font font2 = new Font("Arial", Font.BOLD, 18); // thuộc tính
	Font font3 = new Font("Arial", Font.PLAIN, 18); // jtexfield
	private final String[] col = { "STT", "Mã sản phẩm", "Tên sản phẩm", "Ngày sản xuất", "Loại", "Số lượng tồn",
			"Đơn vị tính", "Đơn giá", "Hình ảnh" };
	private final JLabel lblTitle;
	private final JTextField txtTimKiem;
	private final JComboBox<String> cbLoaiTimKiem;
	private final JButton btnTimKiem;
	private final JTextField txtMa;
	private final JButton btnXoa;
	private final JButton btnSua;
	private final JButton btnLamMoi;
	private final DefaultTableModel model;
	private final JTable table;
	private final JScrollPane scroll;
	private final JButton btnXuatExcel;
	private final JButton btnThem;
	private final JComboBox<String> cbLoaiSanPham;
	private final SqlDateModel modelNgaylap;
	private final JDatePanelImpl datePanel;
	private final Properties p;
	private final JDatePickerImpl datePicker;
	private final JComboBox<String> cbDonViTinh;
	private final SanPhamService sp_Service;
	private XSSFWorkbook wordbook;
	private final JTextField txtDonGia;
	private final JTextField txtTen;
	private final JTextField txtSoLuong;
	private final JButton openButton;
	private final JLabel imageLabel;
	private File selectedFile;
	private String absolutePath;

	private final JButton btnUser;
	private final Dialog_User dialog_User= new Dialog_User();
	public GD_SanPham() throws RemoteException, MalformedURLException, NotBoundException{
		sp_Service = (SanPhamService) Naming.lookup(DataManager.getRmiURL() + "sanPhamServices");
		setBackground(new Color(242, 240, 255));
		setLayout(null);

		JPanel pnNorth = new JPanel();
		pnNorth.setBounds(0, 0, 1078, 60);
		pnNorth.setLayout(null);
		pnNorth.setBackground(new Color(181, 230, 251));
		lblTitle = new JLabel("SẢN PHẨM");

		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);

		// Đặt kích thước và tọa độ cho lblTitle
		int labelWidth = 500; // Thay đổi kích thước theo ý muốn
		int labelHeight = 40; // Thay đổi kích thước theo ý muốn
		int labelX = (pnNorth.getWidth() - labelWidth) / 2; // Căn giữa theo chiều ngang
		int labelY = (pnNorth.getHeight() - labelHeight) / 2; // Căn giữa theo chiều dọc
		lblTitle.setBounds(labelX, labelY, labelWidth, labelHeight);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		pnNorth.add(lblTitle);
		// ---nút user
		btnUser = new JButton();
		btnUser.setBackground(Color.decode("#B5E6FB"));
		btnUser.setBorderPainted(false);
		btnUser.setIcon(new ImageIcon("icon\\icon_profile.png"));
		btnUser.setBounds(1020, 7, 45, 45);
		ImageIcon iconProfile = new ImageIcon("icon\\icon_profile.png");
		iconProfile = new ImageIcon(iconProfile.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		btnUser.setIcon(iconProfile);
		pnNorth.add(btnUser);
		
		add(pnNorth);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(null);
		pnlCenter.setBounds(0, 0, 1070, 380);
		pnlCenter.setBackground(getBackground());
		add(pnlCenter);

		JPanel pnlThongTin = new JPanel();
		pnlThongTin.setLayout(null);
		pnlThongTin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
				"Thông tin sản phẩm", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Arial", Font.BOLD, 16),
				Color.blue));
		pnlThongTin.setBounds(7, 80, 690, 300);
		pnlThongTin.setBackground(Color.white);
		font = new Font("Arial", Font.BOLD, 14);

		JLabel lblMa = new JLabel("Mã sản phẩm");
		pnlThongTin.add(lblMa);
		lblMa.setBounds(20, 20, 150, 100);
		lblMa.setFont(font2);

		int x = 160, y = 55, w = 180, h = 28;
		txtMa = new JTextField();
		txtMa.setEditable(false);
		txtMa.setBounds(x, y, w, h);
		pnlThongTin.add(txtMa);
		txtMa.setHorizontalAlignment(JTextField.RIGHT);
		txtMa.setFont(font3);

		loadMa();

		JLabel lblTen = new JLabel("Tên sản phẩm");
		lblTen.setBounds(20, 70, 130, 100);
		lblTen.setFont(font2);
		pnlThongTin.add(lblTen);

		txtTen = new JTextField();
		pnlThongTin.add(txtTen);
		y += 50;
		txtTen.setBounds(x, y, w, h);
		txtTen.setFont(font3);

		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setBounds(20, 120, 150, 100);
		lblSoLuong.setFont(font2);
		pnlThongTin.add(lblSoLuong);

		txtSoLuong = new JTextField();
		pnlThongTin.add(txtSoLuong);
		y += 50;
		txtSoLuong.setBounds(x, y, w - 95, h);
		txtSoLuong.setFont(font3);

		cbDonViTinh = new JComboBox<String>();
		cbDonViTinh.setFont(font);
		cbDonViTinh.setBounds(x + 95, y, 85, h);
		pnlThongTin.add(cbDonViTinh);
//		for (SanPham item : sp_dao.getallSanPhams()) {
//			cbLoaiSanPham.addItem(item.getDonViTinh());
//		}
		cbDonViTinh.addItem("Phần");
		cbDonViTinh.addItem("Lon");
		cbDonViTinh.addItem("Ly");
		cbDonViTinh.addItem("Chai");

		JLabel lblDonGia = new JLabel("Đơn giá");
		lblDonGia.setBounds(20, 170, 100, 100);
		lblDonGia.setFont(font2);
		pnlThongTin.add(lblDonGia);

		txtDonGia = new JTextField();
		pnlThongTin.add(txtDonGia);
		y += 50;
		txtDonGia.setBounds(x, y, w, h);
		txtDonGia.setFont(font3);

		JLabel lblNgaySX = new JLabel("Ngày sản xuất");
		x = 380;
		y = 55;
		w = 100;
		h = 30;
		lblNgaySX.setBounds(x, y, w + 30, h);
		lblNgaySX.setFont(font2);
		pnlThongTin.add(lblNgaySX);

		modelNgaylap = new SqlDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(modelNgaylap, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		modelNgaylap.setSelected(true);
		datePicker.setBackground(new Color(255, 255, 255));
		datePicker.setBounds(x + 140, y, w + 50, h);
		pnlThongTin.add(datePicker);

		JLabel lblChucVu = new JLabel("Loại SP");
		y += 50;
		lblChucVu.setBounds(x, y, w, h);
		lblChucVu.setFont(font2);
		pnlThongTin.add(lblChucVu);

		cbLoaiSanPham = new JComboBox<String>();
		cbLoaiSanPham.setFont(font3);
		cbLoaiSanPham.setBounds(x + 140, y, w + 50, h);
		cbLoaiSanPham.addItem("Nước ngọt");
		cbLoaiSanPham.addItem("Bia");
		cbLoaiSanPham.addItem("Thức ăn");
		cbLoaiSanPham.addItem("Đồ uống");
		pnlThongTin.add(cbLoaiSanPham);
//		for (SanPham item : sp_dao.getallSanPhams()) {
//			cbLoaiSanPham.addItem(item.getloaiSanPham());
//		}
		pnlThongTin.add(cbLoaiSanPham);

		JLabel lblAnh = new JLabel("Ảnh");
		pnlThongTin.add(lblAnh);
		lblAnh.setFont(font2);
		y += 50;
		lblAnh.setBounds(x, y, w + 20, h);

		pnlThongTin.add(openButton = new JButton("Chọn tệp"));
		openButton.setBounds(x + 45, y + 5, 87, 20);
		pnlThongTin.add(imageLabel = new JLabel());
		imageLabel.setBounds(x + 170, y - 5, 95, 90);

		// Các nút
		pnlThongTin.add(btnThem = new JButton("THÊM", new ImageIcon("icon\\Add_icon.png")));
		btnThem.setFont(font);
		btnThem.setBounds(60, 250, 130, 35);
		btnThem.setBackground(new Color(109, 197, 112));
		btnThem.setBorder(new RoundedBorder(5));
		pnlThongTin.add(btnXoa = new JButton("XÓA", new ImageIcon("icon\\Delete_icon.png")));
		btnXoa.setFont(font);
		btnXoa.setBounds(208, 250, 130, 35);
		btnXoa.setBackground(new Color(228, 50, 51));
		btnXoa.setBorder(new RoundedBorder(5));
		pnlThongTin.add(btnSua = new JButton("SỬA", new ImageIcon("icon\\Edit_icon.png")));
		btnSua.setFont(font);
		btnSua.setBounds(356, 250, 130, 35);
		btnSua.setBackground(new Color(74, 131, 215));
		btnSua.setBorder(new RoundedBorder(5));
		pnlThongTin.add(btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("icon\\Refresh_icon.png")));
		btnLamMoi.setFont(font);
		btnLamMoi.setBounds(504, 250, 130, 35);
		btnLamMoi.setBackground(new Color(104, 211, 211));
		btnLamMoi.setBorder(new RoundedBorder(5));
		pnlCenter.add(pnlThongTin);

		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setLayout(null);
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
				"Tìm kiếm", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Arial", Font.BOLD, 16), Color.blue));
		pnlTimKiem.setBounds(707, 80, 364, 300);
		pnlTimKiem.setBackground(Color.white);

		JLabel lblLoaiTim = new JLabel("Tìm kiếm theo");
		lblLoaiTim.setFont(font2);
		pnlTimKiem.add(lblLoaiTim);
		lblLoaiTim.setBounds(30, 55, 130, 30);

		cbLoaiTimKiem = new JComboBox<String>();
		cbLoaiTimKiem.setFont(font3);
		cbLoaiTimKiem.setBounds(170, 55, 170, 30);
		cbLoaiTimKiem.addItem("Mã sản phẩm");
		cbLoaiTimKiem.addItem("Tên sản phẩm");
		cbLoaiTimKiem.addItem("Loại sản phẩm");
		pnlTimKiem.add(cbLoaiTimKiem);

		JLabel lblTuSPoaTim = new JLabel("Nhập từ Khóa tìm kiếm");
		lblTuSPoaTim.setFont(font2);
		pnlTimKiem.add(lblTuSPoaTim);
		lblTuSPoaTim.setBounds(30, 105, 300, 30);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(font3);
		txtTimKiem.setBounds(30, 160, 310, 30);
		pnlTimKiem.add(txtTimKiem);

		btnTimKiem = new JButton("Tìm kiếm", new ImageIcon("icon\\Research_icon.png"));
		btnTimKiem.setFont(font);
		btnTimKiem.setBounds(112, 250, 140, 35);
		btnTimKiem.setBackground(new Color(238, 233, 233));
		btnTimKiem.setBorder(new RoundedBorder(5));
		pnlTimKiem.add(btnTimKiem);
		pnlCenter.add(pnlTimKiem);

		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(null);
		pnCenter.setBackground(new Color(246, 245, 255));
		pnCenter.setBounds(0, 380, 1078, 500);

		JLabel lblBang = new JLabel("DANH SÁCH SẢN PHẨM");
		lblBang.setFont(new Font("Arial", Font.BOLD, 20));
		pnCenter.add(lblBang);
		lblBang.setForeground(Color.blue);
		lblBang.setBounds(20, 15, 500, 30);

		pnCenter.add(btnXuatExcel = new JButton("Xuất danh sách sản phẩm", new ImageIcon("icon\\Excel_icon.png")));
		btnXuatExcel.setFont(font);
		btnXuatExcel.setBounds(798, 14, 270, 30);
		btnXuatExcel.setBorder(new RoundedBorder(5));
		add(pnCenter);

		model = new DefaultTableModel(col, 0);
		table = new JTable(model);
		table.setSelectionBackground(Color.pink);
		table.getTableHeader().setBackground(new Color(238, 233, 233));
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(70);
		table.getColumnModel().getColumn(7).setMaxWidth(70);
		table.getColumnModel().getColumn(8).setMinWidth(280);
		scroll = new JScrollPane(table);
		scroll.setBounds(9, 50, 1060, 290);
		pnCenter.add(scroll);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		table.addMouseListener(this);
		btnUser.addActionListener(this);
		openButton.addActionListener(this);

		loadData();
	}

	// ---- Mã SP sinh tự động tăng dần bắt đầu từ 001
	private int ThuTuSanPhamTrongNgay() {
		int sl = 1;
		String maSP = "";
		try {
			for (SanPham sp : sp_Service.getAllSanPhams()) {
				maSP = sp.getMaSanPham(); // Chạy hết vòng for sẽ lấy được mã SP cuối danh sách
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		int STTTrenMaSPCuoiDS = Integer.parseInt(maSP.substring(3, 5));
		sl = STTTrenMaSPCuoiDS + 1;
		return sl;
	}

	private String generateRandomCode() {
		String prefix = "SP";
//		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
//		date = new Date();
		String suffix = String.format("%03d", ThuTuSanPhamTrongNgay());
		return prefix + suffix;
	}

	private void loadMa() {
		String code;
		code = generateRandomCode();
		txtMa.setText(code);
	}
	// ---------------------------------------------------------

	private void chenAnh() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
			imageLabel.setIcon(icon);
		}
	}

	public void loadData() {
		int i = 0;
		try {
			for (SanPham sp : sp_Service.getAllSanPhams()) {
				i++;
				Object[] row = { i, sp.getMaSanPham(), sp.getTenSanPham(), sp.getNgaySanXuat(), sp.getloaiSanPham(),
						sp.getDonGia(), sp.getDonViTinh(), sp.getSoLuongTon(), sp.getHinhAnh() };
				model.addRow(row);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void clearTable() {
		while (table.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void xoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		txtDonGia.setText("");
		txtSoLuong.setText("");
		imageLabel.setIcon(null);
	}

	public void them() {
		ImageIcon icon = (ImageIcon) imageLabel.getIcon();
		if (icon != null) {
			File imageFile = new File(icon.getDescription());
			absolutePath = imageFile.getAbsolutePath();
		}
		if (txtMa.getText().equals("") || txtTen.getText().equals("") || txtDonGia.getText().equals("")
				|| txtSoLuong.getText().equals("") || icon == null) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!!");
		} else {
			String ma = txtMa.getText().trim();
			String ten = txtTen.getText().trim();
			Date ngaySX = (Date) datePicker.getModel().getValue();
			String loaiSP = (String) cbLoaiSanPham.getSelectedItem();
			double donGia = Double.parseDouble(txtDonGia.getText().trim());
			String donViTinh = (String) cbDonViTinh.getSelectedItem();
			int soLuongTon = Integer.parseInt(txtSoLuong.getText().trim());
			SanPham sp = new SanPham(ma, ten, ngaySX, loaiSP, donGia, donViTinh, soLuongTon, absolutePath);
			try {
				if (sp_Service.addSanPham(sp)) {
					model.addRow(new Object[] { model.getRowCount() + 1, ma, ten, ngaySX, loaiSP, donGia, donViTinh,
							soLuongTon, absolutePath });
					JOptionPane.showMessageDialog(this, "Thêm thành công!!");
					xoaTrang();
					loadMa();
				} else {
					JOptionPane.showMessageDialog(null, "Trùng mã");
					xoaTrang();
					loadMa();
				}
			} catch (HeadlessException | RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void xoa() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa!!");
		} else if (table.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 Sản phẩm để xóa!!");
		} else {
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa Sản phẩm này không?", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
				try {
					sp_Service.deleteSanPham(model.getValueAt(row, 1).toString());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				model.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa thành công!!");
			}
		}
	}

	public void sua() {
		ImageIcon icon = (ImageIcon) imageLabel.getIcon();
		File imageFile = new File(icon.getDescription());
		absolutePath = imageFile.getAbsolutePath();
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để sửa!!");
		} else if (table.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 Sản phẩm để sửa!!");
		} else {
			String ma = txtMa.getText().trim();
			String ten = txtTen.getText().trim();
			Date ngaySX = (Date) datePicker.getModel().getValue();
			String loaiSP = (String) cbLoaiSanPham.getSelectedItem();
			double donGia = Double.parseDouble(txtDonGia.getText().trim());
			String donViTinh = (String) cbDonViTinh.getSelectedItem();
			int soLuongTon = Integer.parseInt(txtSoLuong.getText().trim());
			SanPham sp = new SanPham(ma, ten, ngaySX, loaiSP, donGia, donViTinh, soLuongTon, absolutePath);
			try {
				if (sp_Service.updateSanPham(sp)) {
					clearTable();
					loadData();
					JOptionPane.showMessageDialog(null, "Sửa thành công!!");
				}
			} catch (HeadlessException | RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void tim() {
		int i = 0;
		if (btnTimKiem.getText().equals("Tìm kiếm")) {
			if (cbLoaiTimKiem.getSelectedItem().equals("Mã sản phẩm")) {
				SanPham sp = null;
				try {
					sp = sp_Service.getSanPhamTheoMaSP(txtTimKiem.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (sp != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					Object[] row = { ++i, sp.getMaSanPham(), sp.getTenSanPham(), sp.getNgaySanXuat(),
							sp.getloaiSanPham(), sp.getDonGia(), sp.getDonViTinh(), sp.getSoLuongTon(),
							sp.getHinhAnh() };
					model.addRow(row);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			} else if (cbLoaiTimKiem.getSelectedItem().equals("Tên sản phẩm")) {
				List<SanPham> dsSanPham = null;
				try {
					dsSanPham = sp_Service.getSanPhamTheoTenSanPham(txtTimKiem.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (dsSanPham != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					for (SanPham sp : dsSanPham) {
						Object[] row = { ++i, sp.getMaSanPham(), sp.getTenSanPham(), sp.getNgaySanXuat(),
								sp.getloaiSanPham(), sp.getDonGia(), sp.getDonViTinh(), sp.getSoLuongTon(),
								sp.getHinhAnh() };
						model.addRow(row);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			} else if (cbLoaiTimKiem.getSelectedItem().equals("Loại sản phẩm")) {
				List<SanPham> dsSanPham = null;
				try {
					dsSanPham = sp_Service.getSanPhamTheoLoaiSanPham(txtTimKiem.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (dsSanPham != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					for (SanPham sp : dsSanPham) {
						Object[] row = { ++i, sp.getMaSanPham(), sp.getTenSanPham(), sp.getNgaySanXuat(),
								sp.getloaiSanPham(), sp.getDonGia(), sp.getDonViTinh(), sp.getSoLuongTon(),
								sp.getHinhAnh() };
						model.addRow(row);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			}
		} else {
			clearTable();
			loadData();
			btnTimKiem.setText("Tìm kiếm");
		}
	}

	public void xuatExcel() {
		try {
			wordbook = new XSSFWorkbook();
			XSSFSheet sheet = wordbook.createSheet("Danh sách SPách hàng");

			XSSFRow row = null;
			Cell cell = null;
			row = sheet.createRow(2);// Tạo 2 dòng trống trong excel
			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("STT");
			cell = row.createCell(1, CellType.STRING);
			cell.setCellValue("Mã Sản phẩm");
			cell = row.createCell(2, CellType.STRING);
			cell.setCellValue("Tên Sản phẩm");
			cell = row.createCell(3, CellType.STRING);
			cell.setCellValue("Ngày sản xuất");
			cell = row.createCell(4, CellType.STRING);
			cell.setCellValue("Loại sản phẩm");
			cell = row.createCell(5, CellType.STRING);
			cell.setCellValue("Đơn giá");
			cell = row.createCell(6, CellType.STRING);
			cell.setCellValue("Đơn vị tính");
			cell = row.createCell(7, CellType.STRING);
			cell.setCellValue("Số lượng tồn");
			cell = row.createCell(8, CellType.STRING);
			cell.setCellValue("Hình ảnh");

			for (int i = 0; i < sp_Service.getAllSanPhams().size(); i++) {
				row = sheet.createRow(3 + i); // Bỏ qua 2 dòng trống

				cell = row.createCell(0, CellType.NUMERIC);
				cell.setCellValue(i + 1);
				cell = row.createCell(1, CellType.STRING);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getMaSanPham());
				cell = row.createCell(2, CellType.STRING);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getTenSanPham());

				cell = row.createCell(3, CellType.STRING);
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String ngay = df.format(sp_Service.getAllSanPhams().get(i).getNgaySanXuat());
				cell.setCellValue(ngay);

				cell = row.createCell(4, CellType.STRING);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getloaiSanPham());
				cell = row.createCell(5, CellType.NUMERIC);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getDonGia());
				cell = row.createCell(6, CellType.STRING);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getDonViTinh());
				cell = row.createCell(7, CellType.NUMERIC);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getSoLuongTon());
				cell = row.createCell(8, CellType.STRING);
				cell.setCellValue(sp_Service.getAllSanPhams().get(i).getHinhAnh());

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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(btnThem)) {
			them();
		} else if (obj.equals(btnXoa)) {
			xoa();
		} else if (obj.equals(btnSua)) {
			sua();
		} else if (obj.equals(btnLamMoi)) {
			xoaTrang();
			loadMa();
			loadData();
		} else if (obj.equals(btnTimKiem)) {
			tim();
		} else if (obj.equals(btnXuatExcel)) {
			xuatExcel();
		} else if (obj.equals(openButton)) {
			chenAnh();
		}
		else if(obj.equals(btnUser)) {
			dialog_User.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		txtMa.setText(model.getValueAt(row, 1).toString());
		txtTen.setText(model.getValueAt(row, 2).toString());
		try {
			modelNgaylap.setValue((java.sql.Date) model.getValueAt(row, 3));
		} catch (Exception e2) {
		}
		cbLoaiSanPham.setSelectedItem(model.getValueAt(row, 4));
		txtDonGia.setText(model.getValueAt(row, 5).toString());
		cbDonViTinh.setSelectedItem(model.getValueAt(row, 6));
		txtSoLuong.setText(model.getValueAt(row, 7).toString());
		imageLabel.setIcon(new ImageIcon(model.getValueAt(row, 8).toString()));

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
}
