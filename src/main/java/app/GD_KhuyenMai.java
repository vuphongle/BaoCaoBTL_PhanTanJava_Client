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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import dao.KhuyenMaiServices;
import entity.KhuyenMai;
import utils.DateLabelFormatter;

public class GD_KhuyenMai extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font font = new Font("Arial", Font.BOLD, 16); // khung tittle
	Font font2 = new Font("Arial", Font.BOLD, 18); // thuộc tính
	Font font3 = new Font("Arial", Font.PLAIN, 18); // jtexfield
	private final String[] col = { "STT", "Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc",
			"Phần trăm khuyến mãi" };
	private final JPanel pnNorth;
	private final JLabel lblTitle;
	private final JButton btnUser;
	private final JComboBox<String> cbLoaiTim;
	private final JTextField txtTuKhoaTim;
	private final JButton btnTimKiem;
	private final JButton btnXuatExcel;
	private final DefaultTableModel model;
	private final JTable table;
	private final JScrollPane scroll;
	private final JTextField txtMa;
	private final JTextField txtTenKhuyenMai;
	private final JTextField txtPhanTramKM;
	private final JButton btnThem;
	private final JButton btnXoa;
	private final JButton btnSua;
	private final JButton btnLamMoi;
	private final SqlDateModel modelBatDau;
	private final Properties p;
	private final JDatePanelImpl datePanel;
	private final JDatePickerImpl datePicker;
	private final SqlDateModel modelKetThuc;
	private final Properties pkt;
	private final JDatePanelImpl datePanelkt;
	private final JDatePickerImpl datePickerkt;
	private final JComboBox<String> cbLoaiTim1;
	private final KhuyenMaiServices km_dao;
	private XSSFWorkbook wordbook;
	private final Dialog_User dialog_User= new Dialog_User();

	public GD_KhuyenMai() throws RemoteException, MalformedURLException, NotBoundException{
		km_dao = (KhuyenMaiServices) Naming.lookup(DataManager.getRmiURL() + "khuyenMaiServices");
		setBackground(new Color(246, 245, 255));
		setLayout(null);

		pnNorth = new JPanel();
		pnNorth.setLayout(null);
		pnNorth.setBounds(0, 0, 1078, 60);
		pnNorth.setBackground(new Color(187, 231, 252));
		add(pnNorth);
		lblTitle = new JLabel("KHUYẾN MÃI");
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

		// căn giữa title
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);

		// Đặt kích thước và tọa độ cho lblTitle
		int labelWidth = 500; // Thay đổi kích thước theo ý muốn
		int labelHeight = 40; // Thay đổi kích thước theo ý muốn
		int labelX = (pnNorth.getWidth() - labelWidth) / 2; // Căn giữa theo chiều ngang
		int labelY = (pnNorth.getHeight() - labelHeight) / 2; // Căn giữa theo chiều dọc
		lblTitle.setBounds(labelX, labelY, labelWidth, labelHeight);
		pnNorth.add(lblTitle);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

		// Khung thông tin khuyến mãi
		JPanel pnSouth = new JPanel();
		pnSouth.setBackground(new Color(255, 255, 255));
		pnSouth.setBounds(7, 80, 690, 300);
		pnSouth.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
				"Thông tin khuyến mãi", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Arial", Font.BOLD, 16),
				Color.blue));
		TitledBorder titlethongtin = (TitledBorder) pnSouth.getBorder();
		titlethongtin.setTitleColor(Color.blue);
		titlethongtin.setTitleFont(font);
		pnSouth.setLayout(null);
		add(pnSouth);

		JLabel lblMa = new JLabel("Mã khuyến mãi");
		pnSouth.add(lblMa);
		lblMa.setBounds(20, 20, 150, 100);
		lblMa.setFont(font2);

		int x = 170, y = 55, w = 180, h = 28;
		txtMa = new JTextField();
		txtMa.setEditable(false);
		txtMa.setBounds(x, y, w, h);
		pnSouth.add(txtMa);
		txtMa.setHorizontalAlignment(JTextField.RIGHT);
		txtMa.setFont(font3);

		JLabel lblTenKhuyenMai = new JLabel("Tên khuyến mãi");
		lblTenKhuyenMai.setBounds(20, 70, 150, 100);
		lblTenKhuyenMai.setFont(font2);
		pnSouth.add(lblTenKhuyenMai);

		txtTenKhuyenMai = new JTextField();
		y += 50;
		txtTenKhuyenMai.setBounds(x, y, w, h);
		txtTenKhuyenMai.setFont(font3);
		pnSouth.add(txtTenKhuyenMai);

		JLabel lblPhanTramKM = new JLabel("Phần trăm giảm");
		lblPhanTramKM.setBounds(20, 120, 150, 100);
		lblPhanTramKM.setFont(font2);
		pnSouth.add(lblPhanTramKM);

		txtPhanTramKM = new JTextField();
		y += 50;
		txtPhanTramKM.setBounds(x, y, w, h);
		txtPhanTramKM.setFont(font3);
		pnSouth.add(txtPhanTramKM);

		JLabel lblNgayBatDau = new JLabel("Ngày bắt đầu");
		x = 380;
		y = 55;
		w = 100;
		h = 30;
		lblNgayBatDau.setBounds(x, y, w + 50, h);
		lblNgayBatDau.setFont(font2);
		pnSouth.add(lblNgayBatDau);

		modelBatDau = new SqlDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(modelBatDau, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		modelBatDau.setSelected(true);
		datePicker.setBackground(new Color(255, 255, 255));
		datePicker.setBounds(x + 140, y, w + 50, h);
		pnSouth.add(datePicker);

		JLabel lblNgayKetThuc = new JLabel("Ngày kết thúc");
		y += 50;
		lblNgayKetThuc.setBounds(x, y, w + 50, h);
		lblNgayKetThuc.setFont(font2);
		pnSouth.add(lblNgayKetThuc);

		modelKetThuc = new SqlDateModel();
		pkt = new Properties();
		pkt.put("text.today", "Today");
		pkt.put("text.month", "Month");
		pkt.put("text.year", "Year");
		datePanelkt = new JDatePanelImpl(modelKetThuc, pkt);
		datePickerkt = new JDatePickerImpl(datePanelkt, new DateLabelFormatter());
		modelKetThuc.setSelected(true);
		datePickerkt.setBackground(new Color(255, 255, 255));
		datePickerkt.setBounds(x + 140, y, w + 50, h);
		pnSouth.add(datePickerkt);

		// Các nút
		pnSouth.add(btnThem = new JButton("THÊM", new ImageIcon("icon\\Add_icon.png")));
		btnThem.setFont(font);
		btnThem.setBounds(60, 250, 130, 35);
		btnThem.setBackground(new Color(109, 197, 112));
		btnThem.setBorder(new RoundedBorder(5));
		pnSouth.add(btnXoa = new JButton("XÓA", new ImageIcon("icon\\Delete_icon.png")));
		btnXoa.setFont(font);
		btnXoa.setBounds(208, 250, 130, 35);
		btnXoa.setBackground(new Color(228, 50, 51));
		btnXoa.setBorder(new RoundedBorder(5));
		pnSouth.add(btnSua = new JButton("SỬA", new ImageIcon("icon\\Edit_icon.png")));
		btnSua.setFont(font);
		btnSua.setBounds(356, 250, 130, 35);
		btnSua.setBackground(new Color(74, 131, 215));
		btnSua.setBorder(new RoundedBorder(5));
		pnSouth.add(btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("icon\\Refresh_icon.png")));
		btnLamMoi.setFont(font);
		btnLamMoi.setBounds(504, 250, 130, 35);
		btnLamMoi.setBackground(new Color(104, 211, 211));
		btnLamMoi.setBorder(new RoundedBorder(5));

		// khung tìm kiếm
		JPanel pnEast = new JPanel();
		pnEast.setBackground(new Color(255, 255, 255));
		pnEast.setBounds(707, 80, 364, 300);
		pnEast.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
				"Tìm kiếm", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Arial", Font.BOLD, 16), Color.blue));
		TitledBorder titleTimKiem = (TitledBorder) pnEast.getBorder();
		titleTimKiem.setTitleColor(Color.blue);
		titleTimKiem.setTitleFont(font);
		pnEast.setLayout(null);
		add(pnEast);

		JLabel lblLoaiTim = new JLabel("Tìm kiếm theo");
		lblLoaiTim.setFont(font2);
		pnEast.add(lblLoaiTim);
		lblLoaiTim.setBounds(30, 55, 130, 30);

		cbLoaiTim = new JComboBox<String>();
		cbLoaiTim.setFont(font3);
		cbLoaiTim.setBounds(170, 55, 170, 30);
		cbLoaiTim.addItem("");
		cbLoaiTim.addItem("Mã khuyến mãi");
		cbLoaiTim.addItem("Tên khuyến mãi");
		pnEast.add(cbLoaiTim);

		cbLoaiTim1 = new JComboBox<String>();
		cbLoaiTim1.setFont(font3);
		cbLoaiTim1.setBounds(170, 105, 170, 30);
		cbLoaiTim1.addItem("");
		cbLoaiTim1.addItem("Còn hạn");
		cbLoaiTim1.addItem("Hết hạn");
		pnEast.add(cbLoaiTim1);

		JLabel lblTuKhoaTim = new JLabel("Nhập từ khóa tìm kiếm");
		lblTuKhoaTim.setFont(font2);
		pnEast.add(lblTuKhoaTim);
		lblTuKhoaTim.setBounds(30, 160, 300, 30);

		txtTuKhoaTim = new JTextField();
		txtTuKhoaTim.setFont(font3);
		txtTuKhoaTim.setBounds(30, 210, 310, 30);
		txtTuKhoaTim.setEditable(false);
		pnEast.add(txtTuKhoaTim);

		btnTimKiem = new JButton("Tìm kiếm", new ImageIcon("icon\\Research_icon.png"));
		btnTimKiem.setFont(font);
		btnTimKiem.setBounds(112, 250, 140, 35);
		btnTimKiem.setBackground(new Color(238, 233, 233));
		btnTimKiem.setBorder(new RoundedBorder(5));
		pnEast.add(btnTimKiem);

		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(null);
		pnCenter.setBackground(new Color(246, 245, 255));
		pnCenter.setBounds(0, 380, 1078, 500);

		JLabel lblBang = new JLabel("DANH SÁCH KHUYẾN MÃI");
		lblBang.setFont(new Font("Arial", Font.BOLD, 20));
		pnCenter.add(lblBang);
		lblBang.setForeground(Color.blue);
		lblBang.setBounds(20, 15, 500, 30);

		pnCenter.add(btnXuatExcel = new JButton("Xuất danh sách khuyến mãi", new ImageIcon("icon\\Excel_icon.png")));
		btnXuatExcel.setFont(font);
		btnXuatExcel.setBorder(new RoundedBorder(5));
		btnXuatExcel.setBounds(798, 14, 270, 30);
		add(pnCenter);

		model = new DefaultTableModel(col, 0);
		table = new JTable(model);
		table.setSelectionBackground(Color.pink);
		table.getTableHeader().setBackground(new Color(238, 233, 233));
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		scroll = new JScrollPane(table);
		scroll.setBounds(9, 50, 1060, 290);
		pnCenter.add(scroll);

		loadData();
		table.addMouseListener(this);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		cbLoaiTim.addActionListener(this);
		cbLoaiTim1.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		btnUser.addActionListener(this);
	}

	private void clearTable() {
		while (table.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	private void loadData() throws RemoteException {
		clearTable();
		int i = 0;
		for (KhuyenMai km : km_dao.getAllKhuyenMais()) {
			if (!km.getMaKhuyenMai().equals("NULL")) {
				i++;
				Object[] row = { i, km.getMaKhuyenMai(), km.getTenKhuyenMai(), km.getNgayBatDau(), km.getNgayKetThuc(),
						km.getPhanTramKhuyenMai() };
				model.addRow(row);
			}
		}
	}

	private void loadMa() {
		Date ngayBatDau = (Date) datePicker.getModel().getValue();
		// Định dạng năm với "yy" để lấy hai số cuối
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy");

		// Lấy năm dưới dạng chuỗi và chuyển đổi thành số nguyên
		String year = dateFormat.format(ngayBatDau);
		dateFormat = new SimpleDateFormat("MM");
		String month = dateFormat.format(ngayBatDau);
		dateFormat = new SimpleDateFormat("dd");
		String day = dateFormat.format(ngayBatDau);
		String ma = "KM" + year + month + day;
		txtMa.setText(ma);
	}

	private void them() throws HeadlessException, RemoteException {
		loadMa();
		if (txtMa.getText().equals("") || txtTenKhuyenMai.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!!");
		} else {
			String ma = txtMa.getText();
			String tenKM = txtTenKhuyenMai.getText();
			Date ngayBatDau = (Date) datePicker.getModel().getValue();
			Date ngayKetThuc = (Date) datePickerkt.getModel().getValue();
			float phanTramKM = Float.parseFloat(txtPhanTramKM.getText());
			KhuyenMai km = new KhuyenMai(ma, tenKM, ngayBatDau, ngayKetThuc, phanTramKM);
			if (km_dao.addKhuyenMai(km)) {
				JOptionPane.showMessageDialog(this, "Thêm thành công!!");
				clearTable();
				loadData();
				lamMoi();
			} else {
				JOptionPane.showMessageDialog(this, "Lỗi");
			}
		}
	}

	private void xoa() throws RemoteException {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa!!");
		} else if (table.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 khuyến mãi để xóa!!");
		} else {
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này không?", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
				km_dao.deleteKhuyenMai(model.getValueAt(row, 1).toString());
				JOptionPane.showMessageDialog(this, "Xóa thành công!!");
				loadData();
			}
		}
	}

	private void sua() throws HeadlessException, RemoteException {
		if (txtMa.getText().equals("") || txtTenKhuyenMai.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!!");
		} else {
			String ma = txtMa.getText();
			String tenKM = txtTenKhuyenMai.getText();
			Date ngayBatDau = (Date) datePicker.getModel().getValue();
			Date ngayKetThuc = (Date) datePickerkt.getModel().getValue();
			float phanTramKM = Float.parseFloat(txtPhanTramKM.getText());
			KhuyenMai km = new KhuyenMai(ma, tenKM, ngayBatDau, ngayKetThuc, phanTramKM);
			if (km_dao.updateKhuyenMai(km)) {
				JOptionPane.showMessageDialog(this, "Sửa thành công!!");
				clearTable();
				loadData();
				lamMoi();
			} else {
				JOptionPane.showMessageDialog(this, "Lỗi");
			}
		}
	}

	private void lamMoi() throws RemoteException {
		txtTenKhuyenMai.setText("");
		txtPhanTramKM.setText("");
		loadData();
		loadMa();
	}

	private void timKiem() throws RemoteException {
		int i = 1;
		if (btnTimKiem.getText().equals("Tìm kiếm")) {
			if (cbLoaiTim.getSelectedItem().equals("Mã khuyến mãi")) {
				KhuyenMai km = null;
				km = km_dao.getKhuyenMaiTheoMaKhuyenMai(txtTuKhoaTim.getText());
				if (km != null) {
					btnTimKiem.setText("Hủy tìm");
					clearTable();
					Object[] row = { i++, km.getMaKhuyenMai(), km.getTenKhuyenMai(), km.getNgayBatDau(),
							km.getNgayKetThuc(), km.getPhanTramKhuyenMai() };
					model.addRow(row);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			} else if (cbLoaiTim.getSelectedItem().equals("Tên khuyến mãi")) {
				ArrayList<KhuyenMai> dsKhuyenMai = km_dao.getKhuyenMaiTheoTenKhuyenMai(txtTuKhoaTim.getText());
				ArrayList<KhuyenMai> dsCanTim = new ArrayList<KhuyenMai>();
				if (dsKhuyenMai != null) {
					Date currentDate = getCurrentSqlDate();
					int kt = 1;
					for (KhuyenMai km : dsKhuyenMai) {
						if (!km.getTenKhuyenMai().equals("NULL")) {
							if (km.getNgayKetThuc().before(currentDate)
									&& cbLoaiTim1.getSelectedItem().equals("Hết hạn")) {
								kt = 0;
								dsCanTim.add(km);
							} else if (km.getNgayKetThuc().after(currentDate)
									&& cbLoaiTim1.getSelectedItem().equals("Còn hạn")) {
								kt = 0;
								dsCanTim.add(km);
							} else if (cbLoaiTim1.getSelectedItem().equals("")) {
								kt = 0;
								dsCanTim.add(km);
							}
						}
					}
					if (kt == 0)
						clearTable();
					for (KhuyenMai km : dsCanTim) {
						Object[] row = { i++, km.getMaKhuyenMai(), km.getTenKhuyenMai(), km.getNgayBatDau(),
								km.getNgayKetThuc(), km.getPhanTramKhuyenMai() };
						model.addRow(row);
					}
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
					} else {
						btnTimKiem.setText("Hủy tìm");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
				}
			} else if (cbLoaiTim.getSelectedItem().equals("") && cbLoaiTim1.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn loại cần tìm");
			} else if (cbLoaiTim.getSelectedItem().equals("")) {
				ArrayList<KhuyenMai> dsKhuyenMai = km_dao.getAllKhuyenMais();
				ArrayList<KhuyenMai> dsCanTim = new ArrayList<KhuyenMai>();
				if (dsKhuyenMai != null) {
					Date currentDate = getCurrentSqlDate();
					int kt = 1;
					for (KhuyenMai km : dsKhuyenMai) {
						if (!km.getTenKhuyenMai().equals("NULL")) {
							if (km.getNgayKetThuc().before(currentDate)
									&& cbLoaiTim1.getSelectedItem().equals("Hết hạn")) {
								kt = 0;
								dsCanTim.add(km);
							} else if (km.getNgayKetThuc().after(currentDate)
									&& cbLoaiTim1.getSelectedItem().equals("Còn hạn")) {
								kt = 0;
								dsCanTim.add(km);
							}
						}
					}
					if (kt == 0)
						clearTable();
					for (KhuyenMai km : dsCanTim) {
						Object[] row = { i++, km.getMaKhuyenMai(), km.getTenKhuyenMai(), km.getNgayBatDau(),
								km.getNgayKetThuc(), km.getPhanTramKhuyenMai() };
						model.addRow(row);
					}
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin!!");
					} else {
						btnTimKiem.setText("Hủy tìm");
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

	private static Date getCurrentSqlDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		return new java.sql.Date(currentDate.getTime());
	}

	public void xuatExcel() {
		try {
			wordbook = new XSSFWorkbook();
			XSSFSheet sheet = wordbook.createSheet("Danh sách nhân viên");

			XSSFRow row = null;
			Cell cell = null;
			row = sheet.createRow(2);// Tạo 2 dòng trống trong excel
			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("STT");
			cell = row.createCell(1, CellType.STRING);
			cell.setCellValue("Mã khuyến mãi");
			cell = row.createCell(2, CellType.STRING);
			cell.setCellValue("Tên khuyến mãi");
			cell = row.createCell(3, CellType.STRING);
			cell.setCellValue("Ngày bắt đầu");
			cell = row.createCell(4, CellType.STRING);
			cell.setCellValue("Ngày kết thúc");
			cell = row.createCell(5, CellType.STRING);
			cell.setCellValue("Phần trăm khuyến mãi");

			for (int i = 0; i < km_dao.getAllKhuyenMais().size(); i++) {
				row = sheet.createRow(3 + i); // Bỏ qua 2 dòng trống

				cell = row.createCell(0, CellType.NUMERIC);
				cell.setCellValue(i + 1);
				cell = row.createCell(1, CellType.STRING);
				cell.setCellValue(km_dao.getAllKhuyenMais().get(i).getMaKhuyenMai());
				cell = row.createCell(2, CellType.STRING);
				cell.setCellValue(km_dao.getAllKhuyenMais().get(i).getTenKhuyenMai());
				cell = row.createCell(5, CellType.STRING);
				cell.setCellValue(km_dao.getAllKhuyenMais().get(i).getPhanTramKhuyenMai());

				cell = row.createCell(3, CellType.STRING);
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String ngay = df.format(km_dao.getAllKhuyenMais().get(i).getNgayBatDau());
				cell.setCellValue(ngay);

				cell = row.createCell(4, CellType.STRING);
				ngay = df.format(km_dao.getAllKhuyenMais().get(i).getNgayBatDau());
				cell.setCellValue(ngay);
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
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			try {
				them();
			} catch (HeadlessException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnXoa)) {
			try {
				xoa();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnSua)) {
			try {
				sua();
			} catch (HeadlessException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnLamMoi)) {
			try {
				lamMoi();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTimKiem)) {
			try {
				timKiem();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(cbLoaiTim)) {
			if (cbLoaiTim.getSelectedItem().equals("Mã khuyến mãi")) {
				cbLoaiTim1.setSelectedItem("");
			}
            txtTuKhoaTim.setEditable(!cbLoaiTim.getSelectedItem().equals(""));
		}
		if (o.equals(cbLoaiTim1)) {
			if (!cbLoaiTim1.getSelectedItem().equals("") && cbLoaiTim.getSelectedItem().equals("Mã khuyến mãi")) {
				cbLoaiTim.setSelectedItem("");
			}
		}
		if (o.equals(btnXuatExcel)) {
			xuatExcel();
		}
		if(o.equals(btnUser)) {
			dialog_User.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		txtMa.setText(model.getValueAt(row, 1).toString());
		txtTenKhuyenMai.setText(model.getValueAt(row, 2).toString());

		try {
			modelBatDau.setValue((java.sql.Date) model.getValueAt(row, 3));
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			modelKetThuc.setValue((java.sql.Date) model.getValueAt(row, 4));
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		txtPhanTramKM.setText(model.getValueAt(row, 5).toString());
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
