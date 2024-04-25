package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import dao.ChiTietDichVuServices;
import dao.ChiTietHoaDonServices;
import dao.HoaDonDatPhongServices;
import dao.KhachHangServices;
import dao.KhuyenMaiServices;
import dao.NhanVienService;
import dao.PhongService;
import dao.ThongKeServices;
import entity.HoaDonDatPhong;
import utils.CurveLineChart;
import utils.DoanhThuLoaiPhong;
import utils.ModelChart;
import utils.ModelPieChart;
import utils.ModelThongKe;
import utils.ModelThongKeDTNhieuNam;
import utils.ModelThongKeKH;
import utils.PieChart;

public class GD_ThongKe extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] col = { "STT", "Mã hóa đơn", "Ngày lập hóa đơn", "Tên khách hàng", "Số điện thoại",
			"Khuyến mãi", "Tổng tiền", "Tên nhân viên" };
	private final String[] colKH = { "STT", "Mã khách hàng", "Họ tên", "Số điện thoại", "Giới tính", "Số giờ hát" };
	private final DefaultTableModel model;
	private final DefaultTableModel modelKH;
	private final JTable table;
	private final JTable tblKH;
	private final JScrollPane scroll;
	private final JScrollPane scrollKH;
	JLabel lblTitle, lblThongKe, lblLoaihinhTK, lblDate, lblTongDoanhThu, lblChartTitle, lblTongHoaDon,
			lblDoanhThuPhongThuong, lblDoanhThuPhongVIP, lblTongSoGioHat, lblDoanhThuDichVu, lblYearStart, lblYearEnd;
	JComboBox<String> cbThongKe, cbDate, cbYearStart, cbYearEnd, cbYear, cbMonth, cbMonthKH, cbYearKH;
	JButton btnThongKe, btnLamMoi, btnProfile;
	private final HoaDonDatPhongServices hoadon_dao;
	private final KhachHangServices khachhang_dao;
	private final PhongService p_Service;
	private final KhuyenMaiServices khuyenmai_dao;
	private final ChiTietDichVuServices chitietdichvu_dao;
	private final ChiTietHoaDonServices chitiethoadon_dao;
	private final JPanel pnTable;
	private final JPanel pnContent;
	private final JPanel pnPieChart;
	private final JPanel pnCurveLineChart;
	private final JPanel pnTableKH;
	private final DecimalFormat df;
	private final PieChart pieChart;
	private final CurveLineChart lineChart;
	private final ThongKeServices thongke_dao;
	private final NhanVienService nhanvien_dao;
	private final Dialog_User dialog_user;

	private final LocalDateTime now;
	private final DateTimePicker dateTimePicker;
	private final TimePickerSettings timeSettings;
	private final DatePickerSettings dateSettings;
	private CategoryDataset dataset;
	private JFreeChart barChart;
	private final ChartPanel pnBarChart;

	public GD_ThongKe() throws RemoteException, MalformedURLException, NotBoundException {
		dialog_user = new Dialog_User();
		df = new DecimalFormat("#,###,### VNĐ");
		setLayout(null);
		setBackground(Color.decode("#FAFAFF"));
		hoadon_dao = (HoaDonDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "hoaDonDatPhongServices");
		khachhang_dao = (KhachHangServices) Naming.lookup(DataManager.getRmiURL() + "khachHangServices");
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		chitietdichvu_dao = (ChiTietDichVuServices) Naming.lookup(DataManager.getRmiURL() + "chiTietDichVuServices");
		khuyenmai_dao = (KhuyenMaiServices) Naming.lookup(DataManager.getRmiURL() + "khuyenMaiServices");
		chitiethoadon_dao = (ChiTietHoaDonServices) Naming.lookup(DataManager.getRmiURL() + "chiTietHoaDonServices");
		thongke_dao = (ThongKeServices) Naming.lookup(DataManager.getRmiURL() + "thongKeServices");
		nhanvien_dao = (NhanVienService) Naming.lookup(DataManager.getRmiURL() + "nhanVienServices");
		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(Color.decode("#B5E6FB"));
		pnNorth.setBounds(0, 0, 1080, 60);
		pnNorth.setLayout(null);
		lblTitle = new JLabel("Thống kê");
		lblTitle.setBounds(501, 15, 200, 30);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		pnNorth.add(lblTitle);
		btnProfile = new JButton();
		btnProfile.setBackground(Color.decode("#B5E6FB"));
		btnProfile.setBorderPainted(false);
		btnProfile.setIcon(new ImageIcon("icon\\icon_profile.png"));
		btnProfile.setBounds(1020, 7, 45, 45);
		ImageIcon iconProfile = new ImageIcon("icon\\icon_profile.png");
		iconProfile = new ImageIcon(iconProfile.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		btnProfile.setIcon(iconProfile);
		pnNorth.add(btnProfile);
		JPanel pnMenu = new JPanel();
		pnMenu.setLayout(null);
		pnMenu.setBounds(0, 60, 1080, 122);
		pnMenu.setBackground(Color.white);
		pnMenu.add(lblThongKe = new JLabel("Thống kê"));
		lblThongKe.setFont(new Font("Arial", Font.BOLD, 18));
		lblThongKe.setBounds(15, 25, 187, 24);
		pnMenu.add(cbThongKe = new JComboBox<String>());
		cbThongKe.addItem("Doanh thu");
		cbThongKe.addItem("Khách hàng");
		cbThongKe.setFont(new Font("Arial", Font.PLAIN, 18));
		cbThongKe.setBounds(222, 25, 187, 24);
		pnMenu.add(lblLoaihinhTK = new JLabel("Loại hình thống kê"));
		lblLoaihinhTK.setFont(new Font("Arial", Font.BOLD, 18));
		lblLoaihinhTK.setBounds(15, 85, 187, 24);
		pnMenu.add(cbDate = new JComboBox<String>());
		addItemIntoCbDate();
		cbDate.setFont(new Font("Arial", Font.PLAIN, 18));
		cbDate.setBounds(222, 85, 187, 24);

		pnMenu.add(lblYearStart = new JLabel("Năm bắt đầu"));
		lblYearStart.setFont(new Font("Arial", Font.BOLD, 18));
		lblYearStart.setBounds(500, 25, 120, 24);
		pnMenu.add(cbYearStart = new JComboBox<String>());
		cbYearStart.setFont(new Font("Arial", Font.PLAIN, 18));
		cbYearStart.setEnabled(false);
		cbYearStart.setBounds(630, 25, 80, 24);
		cbYearStart.setVisible(true);

		cbMonthKH = new JComboBox<String>();
		cbMonthKH.setFont(new Font("Arial", Font.PLAIN, 18));
		cbMonthKH.setVisible(false);
		cbMonthKH.setEnabled(false);
		cbMonthKH.setBounds(630, 25, 80, 24);
		pnMenu.add(cbMonthKH);

		pnMenu.add(lblYearEnd = new JLabel("Năm kết thúc"));
		lblYearEnd.setFont(new Font("Arial", Font.BOLD, 18));
		lblYearEnd.setBounds(500, 85, 120, 24);
		pnMenu.add(cbYearEnd = new JComboBox<String>());
		cbYearEnd.setFont(new Font("Arial", Font.PLAIN, 18));
		cbYearEnd.setBounds(630, 85, 80, 24);
		cbYearEnd.setEnabled(false);

		cbYearKH = new JComboBox<String>();
		cbYearKH.setFont(new Font("Arial", Font.PLAIN, 18));
		cbYearKH.setVisible(false);
		cbYearKH.setEnabled(false);
		cbYearKH.setBounds(630, 85, 80, 24);
		pnMenu.add(cbYearKH);

		pnMenu.add(btnThongKe = new JButton("Thống kê"));
		btnThongKe.setBounds(800, 15, 184, 42);
		btnThongKe.setBackground(Color.decode("#0D99FF"));
		btnThongKe.setForeground(Color.white);
		btnThongKe.setFont(new Font("Arial", Font.BOLD, 18));
		btnThongKe.setIcon(new ImageIcon("icon\\ThongKe_icon.png"));
		btnThongKe.setBorder(new RoundedBorder(5));
		btnThongKe.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnThongKe.setIconTextGap(18);
		pnMenu.add(btnLamMoi = new JButton("Làm mới"));
		btnLamMoi.setBounds(800, 70, 184, 42);
		btnLamMoi.setBackground(Color.decode("#32BF26"));
		btnLamMoi.setForeground(Color.white);
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 18));
		btnLamMoi.setIcon(new ImageIcon("icon\\Refresh_icon.png"));
		btnLamMoi.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnLamMoi.setBorder(new RoundedBorder(5));
		btnLamMoi.setIconTextGap(18);
		add(pnMenu);
		pnContent = new JPanel();
		pnContent.setLayout(null);
		pnContent.setBounds(0, 192, 400, 535);
		pnContent.setBackground(Color.white);
		pnContent.add(lblDate = new JLabel("Chọn ngày"));
		lblDate.setFont(new Font("Arial", Font.BOLD, 18));
		lblDate.setBounds(15, 20, 120, 34);

		now = LocalDateTime.now();

		dateSettings = new DatePickerSettings();
		dateSettings.setLocale(new Locale("vi", "VN")); // Set the locale to English
		dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd"); // Set the date format

		timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);

		dateTimePicker = new DateTimePicker(dateSettings, timeSettings);
		dateTimePicker.getTimePicker().setVisible(false);
		dateTimePicker.getDatePicker().getComponentDateTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker.getTimePicker().getComponentTimeTextField().setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateTimePicker.getTimePicker().getComponentSpinnerPanel().setBounds(80, 0, 0, 25);
		dateTimePicker.getTimePicker().getComponentToggleTimeMenuButton().setBounds(75, 0, 26, 25);
		dateTimePicker.getTimePicker().getComponentTimeTextField().setBounds(0, 0, 70, 25);
		dateTimePicker.getTimePicker().getComponentToggleTimeMenuButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker.getDatePicker().getComponentToggleCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateTimePicker.timePicker.setBounds(141, 0, 80, 25);
		dateTimePicker.datePicker.setBounds(0, 0, 136, 25);
		dateTimePicker.getTimePicker().setBounds(150, 0, 110, 25);
		dateTimePicker.getTimePicker().setLayout(null);
		dateTimePicker.getTimePicker().setBackground(Color.white);
		dateTimePicker.getDatePicker().setBounds(0, 0, 136, 25);
		dateTimePicker.setDateTimePermissive(now);
		dateTimePicker.setBackground(Color.white);
		dateTimePicker.setBackground(Color.white);

		// Add the DateTimePicker to your user interface, e.g. to a JPanel
		// panel.add(dateTimePicker);
		dateTimePicker.setBounds(208, 20, 164, 34);
		pnContent.add(dateTimePicker);

		cbMonth = new JComboBox<String>();
		cbMonth.setFont(new Font("Arial", Font.PLAIN, 18));
		cbMonth.setVisible(false);
		cbMonth.setBounds(208, 20, 80, 34);
		pnContent.add(cbMonth);

		cbYear = new JComboBox<String>();
		cbYear.setFont(new Font("Arial", Font.PLAIN, 18));
		cbYear.setVisible(false);
		cbYear.setBounds(308, 20, 84, 34);
		pnContent.add(cbYear);

		JPanel pnLine = new JPanel();
		pnLine.setBounds(0, 71, 400, 2);
		pnLine.setBackground(Color.decode("#CCCCCC"));
		pnContent.add(pnLine);
		JLabel lblTotalRevenue = new JLabel("Tổng doanh thu:");
		JLabel lblTotalOrder = new JLabel("Tổng hóa đơn:");
		JLabel lblNormalRomRevenue = new JLabel("Doanh thu phòng thường:");
		JLabel lblVIPRomRevenue = new JLabel("Doanh thu phòng VIP:");
		JLabel lblTotalServiceRevenue = new JLabel("Doanh thu dịch vụ:");
		JLabel lblTotalHour = new JLabel("Tổng số giờ hát:");
		pnContent.add(lblTotalRevenue);
		lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotalRevenue.setBounds(15, 113, 210, 34);
		pnContent.add(lblTongDoanhThu = new JLabel("0 VNĐ"));
		lblTongDoanhThu.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTongDoanhThu.setBounds(220, 113, 210, 34);
		pnContent.add(lblTotalOrder);
		lblTotalOrder.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotalOrder.setBounds(15, 189, 210, 34);
		pnContent.add(lblTongHoaDon = new JLabel("0"));
		lblTongHoaDon.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTongHoaDon.setBounds(260, 189, 210, 34);
		pnContent.add(lblNormalRomRevenue);
		lblNormalRomRevenue.setFont(new Font("Arial", Font.BOLD, 18));
		lblNormalRomRevenue.setBounds(15, 265, 240, 34);
		pnContent.add(lblDoanhThuPhongThuong = new JLabel("0 VNĐ"));
		lblDoanhThuPhongThuong.setFont(new Font("Arial", Font.PLAIN, 18));
		lblDoanhThuPhongThuong.setBounds(260, 265, 210, 34);
		pnContent.add(lblVIPRomRevenue);
		lblVIPRomRevenue.setFont(new Font("Arial", Font.BOLD, 18));
		lblVIPRomRevenue.setBounds(15, 341, 240, 34);
		pnContent.add(lblDoanhThuPhongVIP = new JLabel("0 VNĐ"));
		lblDoanhThuPhongVIP.setFont(new Font("Arial", Font.PLAIN, 18));
		lblDoanhThuPhongVIP.setBounds(220, 341, 210, 34);
		pnContent.add(lblTotalServiceRevenue);
		lblTotalServiceRevenue.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotalServiceRevenue.setBounds(15, 417, 240, 34);
		pnContent.add(lblDoanhThuDichVu = new JLabel("0 VNĐ"));
		lblDoanhThuDichVu.setFont(new Font("Arial", Font.PLAIN, 18));
		lblDoanhThuDichVu.setBounds(220, 417, 210, 34);
		pnContent.add(lblTotalHour);
		lblTotalHour.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotalHour.setBounds(15, 493, 240, 34);
		pnContent.add(lblTongSoGioHat = new JLabel("0"));
		lblTongSoGioHat.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTongSoGioHat.setBounds(260, 493, 210, 34);
		pnTable = new JPanel();
		pnTable.setLayout(null);
		pnTable.setBounds(400, 192, 670, 535);
		model = new DefaultTableModel(col, 0);
		table = new JTable(model);
		table.setSelectionBackground(Color.pink);
		table.getTableHeader().setBackground(new Color(238, 233, 233));
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(70);
		table.getColumnModel().getColumn(6).setMaxWidth(80);
		table.getColumnModel().getColumn(7).setMaxWidth(100);
		scroll = new JScrollPane(table);
		scroll.setBounds(0, 0, 670, 535);
		pnTable.add(scroll);
		add(pnTable);

		pnPieChart = new JPanel();
		pnPieChart.setLayout(null);
		pnPieChart.setBounds(400, 192, 670, 535);
		pnPieChart.add(lblChartTitle = new JLabel("BIỂU ĐỒ THỐNG KÊ DOANH THU THEO THÁNG"));
		lblChartTitle.setForeground(Color.blue);
		lblChartTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lblChartTitle.setBounds(20, 0, 600, 34);
		pnPieChart.add(pieChart = new PieChart());
		pieChart.setBounds(100, 20, 500, 500);
		pieChart.setChartType(PieChart.PeiChartType.DEFAULT);
		pnPieChart.setVisible(false);
		add(pnPieChart);
		add(pnContent);
		pnCurveLineChart = new JPanel();
		pnCurveLineChart.setLayout(null);
		pnCurveLineChart.setBounds(0, 182, 1080, 548);
		pnCurveLineChart.add(lineChart = new CurveLineChart());
		lineChart.setBounds(400, 0, 670, 535);
		lineChart.addLegend("Tổng doanh thu", Color.decode("#7b4397"), Color.decode("#dc2430"));
		lineChart.addLegend("Doanh thu phòng", Color.decode("#e65c00"), Color.decode("#F9D423"));
		lineChart.addLegend("Doanh thu dịch vụ", Color.decode("#0099F7"), Color.decode("#F11712"));
		pnCurveLineChart.setVisible(false);
		add(pnCurveLineChart);

//		Table Thống kê khách hàng
		pnTableKH = new JPanel();
		pnTableKH.setLayout(null);
		pnTableKH.setBounds(0, 182, 480, 548);
		modelKH = new DefaultTableModel(colKH, 0);
		tblKH = new JTable(modelKH);
		tblKH.setSelectionBackground(Color.pink);
		tblKH.getTableHeader().setBackground(new Color(238, 233, 233));
		tblKH.getColumnModel().getColumn(0).setMaxWidth(40);
		tblKH.getColumnModel().getColumn(1).setMaxWidth(80);
		tblKH.getColumnModel().getColumn(2).setMaxWidth(120);
		tblKH.getColumnModel().getColumn(3).setMaxWidth(80);
		tblKH.getColumnModel().getColumn(4).setMaxWidth(70);
		tblKH.getColumnModel().getColumn(5).setMaxWidth(90);
		scrollKH = new JScrollPane(tblKH);
		scrollKH.setBounds(0, 0, 480, 548);
		pnTableKH.add(scrollKH);
		pnTableKH.setVisible(false);
		add(pnTableKH);
//		Bar chart
		dataset = createDataset();
		barChart = createChart(dataset);
		pnBarChart = new ChartPanel(barChart);
		pnBarChart.setBounds(480, 182, 600, 548);
		pnBarChart.setVisible(false);
		add(pnBarChart);

		add(pnNorth);

		dateTimePicker.addDateTimeChangeListener(event -> {
			if (DataManager.getRole().equals("NV")) {
				JOptionPane.showMessageDialog(null, "Nhân viên không có quyền thống kê doanh thu");
			} else {
				resetField();
				clearDataDoanhThuTheoNgay();
				try {
					loadDataDoanhThuTheoNgay();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // adjust this
																										// pattern to
																										// match your
																										// SQL Server
																										// date/time
																										// format
					String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);

					DoanhThuLoaiPhong dtlp = p_Service.tinhTongDoanhThuLoaiPhongTheoNgay(formattedDateTime);
					if (dtlp != null) {
						lblDoanhThuPhongThuong.setText(df.format(dtlp.getDoanhThuPhongThuong()));
						lblDoanhThuPhongVIP.setText(df.format(dtlp.getDoanhThuPhongVIP()));
					}
					double soGioHat = chitiethoadon_dao.tinhSoGioHatTheoNgay(formattedDateTime);
					double soGioHatSauKhiLamTron = Math.round(soGioHat * 100.0) / 100.0;
					lblTongSoGioHat.setText(soGioHatSauKhiLamTron + "");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (model.getRowCount() <= 0) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
					String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);
					JOptionPane.showMessageDialog(this, "Không có dữ liệu thống kê của ngày: " + formattedDateTime);
				}
			}
		});
		btnThongKe.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnProfile.addActionListener(this);
		updateYearCbo();
		updateMonthYearCbo();
	}

	public void addItemIntoCbDate() {
		cbDate.addItem("Ngày");
		cbDate.addItem("Tháng");
		cbDate.addItem("Năm");
	}

	public void setCurveLineChartData() throws RemoteException {
		String yearStart = cbYearStart.getSelectedItem().toString();
		String yearEnd = cbYearEnd.getSelectedItem().toString();
		for (ModelThongKe tk : thongke_dao.thongKeTheoNam(yearStart, yearEnd)) {
			lineChart.addData(new ModelChart(tk.getYear(),
					new double[] { tk.getTongDoanhThu(), tk.getDoanhThuPhong(), tk.getDoanhThuDichVu() }));
		}
		lineChart.start();
	}

	public void loadDataDoanhThuTheoNgay() throws RemoteException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // adjust this pattern to
																							// match your SQL Server
																							// date/time format
		String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);
		double tongDoanhThu = 0;
		double doanhThuDV = 0;
		int i = 0;
		for (HoaDonDatPhong hd : hoadon_dao.getHoaDonTheoNgayLapHD(formattedDateTime)) {
			i++;
			String maKM = hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKhuyenMai() : null;
			tongDoanhThu += hd.tinhTongTienThanhToan(p_Service.tinhTongTienPhongTheoMaHoaDon(hd.getMaHoaDon()),
					chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon()),
					khuyenmai_dao.getPhanTramKhuyenMaiTheoMaKM(maKM));
			doanhThuDV += chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon());
			Object[] row = { i, hd.getMaHoaDon(), hd.getNgayLapHoaDon(),
					khachhang_dao.getKhachHangTheoMaKH(hd.getKhachHang().getMaKhachHang()).getHoTen(),
					khachhang_dao.getKhachHangTheoMaKH(hd.getKhachHang().getMaKhachHang()).getSoDienThoai(),
					khuyenmai_dao.getPhanTramKhuyenMaiTheoMaKM(maKM),
					df.format(hd.tinhTongTienThanhToan(p_Service.tinhTongTienPhongTheoMaHoaDon(hd.getMaHoaDon()),
							chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon()),
							khuyenmai_dao.getPhanTramKhuyenMaiTheoMaKM(maKM))),
					nhanvien_dao.findByID(hd.getNhanVien().getMaNhanVien()).getHoTen(), hd.getNgayLapHoaDon(), };
			model.addRow(row);
		}
		if (i > 0) {
			lblTongDoanhThu.setText(df.format(tongDoanhThu));
			lblDoanhThuDichVu.setText(df.format(doanhThuDV));
			lblTongHoaDon.setText(i + "");
		}
	}

	public void clearDataDoanhThuTheoNgay() {
		model.setRowCount(0);
	}

	private void ThongKeMonth() throws RemoteException {
		pieChart.setSelectedIndex(-1);
		pieChart.clearData();
		int month = Integer.valueOf(cbMonth.getSelectedItem().toString());
		String m = "";
		if (month < 10) {
			m = "0" + month;
		} else {
			m = month + "";
		}
		int year = Integer.parseInt(cbYear.getSelectedItem().toString());
		double tongDoanhThu = 0;
		double doanhThuDV = 0;
		int i = 0;
		for (HoaDonDatPhong hd : hoadon_dao.getHoaDonTheoThang(m, year)) {
			i++;
			String maKM = hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKhuyenMai() : null;
			tongDoanhThu += hd.tinhTongTienThanhToan(p_Service.tinhTongTienPhongTheoMaHoaDon(hd.getMaHoaDon()),
					chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon()),
					khuyenmai_dao.getPhanTramKhuyenMaiTheoMaKM(maKM));
			doanhThuDV += chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon());
		}
		lblTongDoanhThu.setText(df.format(tongDoanhThu));
		lblDoanhThuDichVu.setText(df.format(doanhThuDV));
		lblTongHoaDon.setText(i + "");
		try {
			DoanhThuLoaiPhong dtlp = p_Service.tinhTongDoanhThuLoaiPhongTheoThang(m, year);
			if (dtlp != null) {
				lblDoanhThuPhongThuong.setText(df.format(dtlp.getDoanhThuPhongThuong()));
				lblDoanhThuPhongVIP.setText(df.format(dtlp.getDoanhThuPhongVIP()));
				if (dtlp.getDoanhThuPhongThuong() > 0) {
					pieChart.addData(new ModelPieChart("Doanh thu phòng thường", dtlp.getDoanhThuPhongThuong(),
							new Color(23, 126, 238)));
				}
				if (dtlp.getDoanhThuPhongVIP() > 0) {
					pieChart.addData(new ModelPieChart("Doanh thu phòng VIP", dtlp.getDoanhThuPhongVIP(),
							new Color(221, 65, 65)));
				}
			}
			double soGioHat = chitiethoadon_dao.tinhSoGioHatTheoThang(m, year);
			double soGioHatSauKhiLamTron = Math.round(soGioHat * 100.0) / 100.0;
			lblTongSoGioHat.setText(soGioHatSauKhiLamTron + "");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (doanhThuDV > 0) {
			pieChart.addData(new ModelPieChart("Doanh thu dịch vụ", doanhThuDV, new Color(47, 157, 64)));
		}
	}

	private void ThongKeYear() throws RemoteException {
		pieChart.setSelectedIndex(-1);
		pieChart.clearData();
		int year = Integer.parseInt(cbYearEnd.getSelectedItem().toString());
		double tongDoanhThu = 0;
		double doanhThuDV = 0;
		int i = 0;
		for (HoaDonDatPhong hd : hoadon_dao.getHoaDonTheoNam(year)) {
			i++;
			String maKM = hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKhuyenMai() : null;
			tongDoanhThu += hd.tinhTongTienThanhToan(p_Service.tinhTongTienPhongTheoMaHoaDon(hd.getMaHoaDon()),
					chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon()),
					khuyenmai_dao.getPhanTramKhuyenMaiTheoMaKM(maKM));
			doanhThuDV += chitietdichvu_dao.tinhTongTienDVTheoMaHoaDon(hd.getMaHoaDon());
		}
		lblTongDoanhThu.setText(df.format(tongDoanhThu));
		lblDoanhThuDichVu.setText(df.format(doanhThuDV));
		lblTongHoaDon.setText(i + "");
		try {
			DoanhThuLoaiPhong dtlp = p_Service.tinhTongDoanhThuLoaiPhongTheoNam(year);
			if (dtlp != null) {
				lblDoanhThuPhongThuong.setText(df.format(dtlp.getDoanhThuPhongThuong()));
				lblDoanhThuPhongVIP.setText(df.format(dtlp.getDoanhThuPhongVIP()));
				if (dtlp.getDoanhThuPhongThuong() > 0) {
					pieChart.addData(new ModelPieChart("Doanh thu phòng thường", dtlp.getDoanhThuPhongThuong(),
							new Color(23, 126, 238)));
				}
				if (dtlp.getDoanhThuPhongVIP() > 0) {
					pieChart.addData(new ModelPieChart("Doanh thu phòng VIP", dtlp.getDoanhThuPhongVIP(),
							new Color(221, 65, 65)));
				}
			}
			double soGioHat = chitiethoadon_dao.tinhSoGioHatTheoNam(year);
			double soGioHatSauKhiLamTron = Math.round(soGioHat * 100.0) / 100.0;
			lblTongSoGioHat.setText(soGioHatSauKhiLamTron + "");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (doanhThuDV > 0) {
			pieChart.addData(new ModelPieChart("Doanh thu dịch vụ", doanhThuDV, new Color(47, 157, 64)));
		}
	}

	private void loadDataTKKHALL() throws RemoteException {
		int i = 0;
		for (ModelThongKeKH customer : thongke_dao.getTop10KhachHangHatNhieuNhat()) {
			i++;
			Object[] row = { i, customer.getMaKH(), customer.getTenKH(), customer.getSoDienThoai(),
					customer.isGioiTinh() ? "Nam" : "Nữ", customer.getTongSoGioHat() };
			modelKH.addRow(row);
		}

	}

	private void loadDataTKKHMonth(String year, String month) throws RemoteException {
		int i = 0;
		for (ModelThongKeKH customer : thongke_dao.getTop10KhachHangHatNhieuNhatTheoThang(year, month)) {
			i++;
			Object[] row = { i, customer.getMaKH(), customer.getTenKH(), customer.getSoDienThoai(),
					customer.isGioiTinh() ? "Nam" : "Nữ", customer.getTongSoGioHat() };
			modelKH.addRow(row);
		}
	}

	private void loadDataTKKHYear(String year) throws RemoteException {
		int i = 0;
		for (ModelThongKeKH customer : thongke_dao.getTop10KhachHangHatNhieuNhatTheoNam(year)) {
			i++;
			Object[] row = { i, customer.getMaKH(), customer.getTenKH(), customer.getSoDienThoai(),
					customer.isGioiTinh() ? "Nam" : "Nữ", customer.getTongSoGioHat() };
			modelKH.addRow(row);
		}
	}

	private void clearTableKH() {
		modelKH.setRowCount(0);
	}

	private void ThongKeManyYear(int yearStart, int yearEnd) throws RemoteException {
		int tongHoaDon = 0;
		double tongDoanhThu = 0;
		double tongDoanhThuPhongThuong = 0;
		double tongDoanhThuPhongVIP = 0;
		double tongDoanhThuDichVu = 0;
		double tongSoGioHat = 0;
		try {
			for (ModelThongKeDTNhieuNam tk : thongke_dao.thongKeTheoNhieuNam(yearStart, yearEnd)) {
				tongDoanhThu += tk.getTongDoanhThu();
				tongHoaDon += tk.getTongSoHoaDon();
				tongDoanhThuPhongThuong += tk.getTongDoanhThuPhongThuong();
				tongDoanhThuPhongVIP += tk.getTongDoanhThuPhongVIP();
				tongDoanhThuDichVu += tk.getTongTienDichVu();
				tongSoGioHat += tk.getTongSoGioHat();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		lblTongDoanhThu.setText(df.format(tongDoanhThu));
		lblDoanhThuDichVu.setText(df.format(tongDoanhThuDichVu));
		lblTongHoaDon.setText(tongHoaDon + "");
		lblDoanhThuPhongThuong.setText(df.format(tongDoanhThuPhongThuong));
		lblDoanhThuPhongVIP.setText(df.format(tongDoanhThuPhongVIP));
		double soGioHatSauKhiLamTron = Math.round(tongSoGioHat * 100.0) / 100.0;
		lblTongSoGioHat.setText(soGioHatSauKhiLamTron + "");
	}

	public void updateYearCbo() throws RemoteException {
		try {
			for (ModelThongKe tk : thongke_dao.updateCboYear()) {
				cbYearStart.addItem(tk.getYear());
				cbYearEnd.addItem(tk.getYear());
				cbYear.addItem(tk.getYear());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void updateMonthYearCbo() {
		try {
			for (ModelThongKe tk : thongke_dao.updateCboMonth()) {
				cbMonth.addItem(tk.getMonth());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void updateYearKHCbo() {
		try {
			for (ModelThongKe tk : thongke_dao.updateCboYear()) {
				cbYearKH.addItem(tk.getYear());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void updateMonthYearKHCbo() {
		try {
			for (ModelThongKe tk : thongke_dao.updateCboMonth()) {
				cbMonthKH.addItem(tk.getMonth());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void resetField() {
		lblTongDoanhThu.setText("0 VNĐ");
		lblTongHoaDon.setText("0");
		lblDoanhThuPhongThuong.setText("0 VNĐ");
		lblDoanhThuPhongVIP.setText("0 VNĐ");
		lblDoanhThuDichVu.setText("0 VNĐ");
		lblTongSoGioHat.setText("0");
	}

	private CategoryDataset createDataset() {
		// Call getTop10KhachHangHatNhieuNhat to get the top 10 customers
		ArrayList<ModelThongKeKH> top10Customers = null;
		try {
			top10Customers = thongke_dao.getTop10KhachHangHatNhieuNhat();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// Create a DefaultCategoryDataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Iterate over the list of customers and add each customer's TongSoGioHat to
		// the dataset
		// with the customer's tenKH as the category key
		for (ModelThongKeKH customer : top10Customers) {
			dataset.addValue(customer.getTongSoGioHat(), "Khách hàng ", customer.getTenKH());
		}

		return dataset;
	}

	private CategoryDataset createDatasetByYear(String year) {
		// Call getTop10KhachHangHatNhieuNhat to get the top 10 customers
		ArrayList<ModelThongKeKH> top10Customers = null;
		try {
			top10Customers = thongke_dao.getTop10KhachHangHatNhieuNhatTheoNam(year);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// Create a DefaultCategoryDataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Iterate over the list of customers and add each customer's TongSoGioHat to
		// the dataset
		// with the customer's tenKH as the category key
		for (ModelThongKeKH customer : top10Customers) {
			dataset.addValue(customer.getTongSoGioHat(), "Khách hàng ", customer.getTenKH());
		}

		return dataset;
	}

	private CategoryDataset createDatasetByMonthYear(String year, String month) {
		// Call getTop10KhachHangHatNhieuNhat to get the top 10 customers
		ArrayList<ModelThongKeKH> top10Customers = null;
		try {
			top10Customers = thongke_dao.getTop10KhachHangHatNhieuNhatTheoThang(year, month);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// Create a DefaultCategoryDataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Iterate over the list of customers and add each customer's TongSoGioHat to
		// the dataset
		// with the customer's tenKH as the category key
		for (ModelThongKeKH customer : top10Customers) {
			dataset.addValue(customer.getTongSoGioHat(), "Khách hàng ", customer.getTenKH());
		}

		return dataset;
	}

	private JFreeChart createChart(final CategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createBarChart("Top 10 khách hàng có số giờ hát nhiều nhất", "Khách hàng",
				"Số giờ hát", dataset, PlotOrientation.VERTICAL, true, true, false);

		// Get the plot and the x-axis
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis xAxis = plot.getDomainAxis();

		// Rotate labels 45 degrees
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// Decrease font size
		xAxis.setTickLabelFont(new Font("Dialog", Font.BOLD, 16));

		return chart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThongKe)) {
			if (cbThongKe.getSelectedItem().toString().equals("Doanh thu")) {
				if (DataManager.getRole().equals("NV")) {
					pnContent.setVisible(false);
					pnTable.setVisible(false);
					JOptionPane.showMessageDialog(null, "Nhân viên không có quyền thống kê doanh thu");
				} else {
					pnTableKH.setVisible(false);
					cbDate.removeItem("Toàn");
					if (cbDate.getItemCount() < 3) {
						cbDate.addItem("Ngày");
					}
					cbYearStart.setVisible(true);
					cbYearEnd.setVisible(true);
					lblYearStart.setText("Năm bắt đầu");
					lblYearEnd.setText("Năm kết thúc");
					cbMonthKH.setVisible(false);
					cbYearKH.setVisible(false);
					pnBarChart.setVisible(false);
					if (cbDate.getSelectedItem().toString().equals("Ngày")) {
						clearDataDoanhThuTheoNgay();
						resetField();
						try {
							loadDataDoanhThuTheoNgay();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						try {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // adjust
																					// format
							String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);

							DoanhThuLoaiPhong dtlp = p_Service.tinhTongDoanhThuLoaiPhongTheoNgay(formattedDateTime);
							if (dtlp != null) {
								lblDoanhThuPhongThuong.setText(df.format(dtlp.getDoanhThuPhongThuong()));
								lblDoanhThuPhongVIP.setText(df.format(dtlp.getDoanhThuPhongVIP()));
							}
							double tongSoGioHat = chitiethoadon_dao.tinhSoGioHatTheoNgay(formattedDateTime);
							double soGioHatSauKhiLamTron = Math.round(tongSoGioHat * 100.0) / 100.0;
							lblTongSoGioHat.setText(soGioHatSauKhiLamTron + "");
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						if (model.getRowCount() <= 0) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
							String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);
							JOptionPane.showMessageDialog(this,
									"Không có dữ liệu thống kê của ngày: " + formattedDateTime);
						}
						cbYearStart.setEnabled(false);
						cbYearEnd.setEnabled(false);
						pnCurveLineChart.setVisible(false);
						pnPieChart.setVisible(false);
						lblDate.setText("Chọn ngày");
						dateTimePicker.setVisible(true);
						cbMonth.setVisible(false);
						cbYear.setVisible(false);
						pnContent.setVisible(true);
						pnTable.setVisible(true);
						if (model.getRowCount() <= 0) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
							String formattedDateTime = dateTimePicker.getDateTimeStrict().format(formatter);
							JOptionPane.showMessageDialog(this,
									"Không có dữ liệu thống kê của ngày: " + formattedDateTime);
						}
					} else if (cbDate.getSelectedItem().toString().equals("Tháng")) {
						if (DataManager.getRole().equals("QL")) {
							pnContent.setVisible(true);
							cbYearStart.setEnabled(false);
							cbYearEnd.setEnabled(false);
							pnCurveLineChart.setVisible(false);
							pnPieChart.setVisible(true);
							lblDate.setText("Chọn tháng");
							cbMonth.setVisible(true);
							cbYear.setVisible(true);
							pnPieChart.setVisible(true);
							dateTimePicker.setVisible(false);
							pnTable.setVisible(false);
							resetField();
							try {
								ThongKeMonth();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							if (lblTongDoanhThu.getText().equals("0 VNĐ")) {
								JOptionPane.showMessageDialog(null, "Không có dữ liệu thống kê của tháng: "
										+ cbMonth.getSelectedItem() + " năm: " + cbYear.getSelectedItem());
							}
						}
					} else if (cbDate.getSelectedItem().toString().equals("Năm")) {
						if (DataManager.getRole().equals("QL")) {
							cbMonth.setVisible(false);
							cbYear.setVisible(false);
							pnPieChart.setVisible(false);
							cbYearStart.setEnabled(true);
							cbYearEnd.setEnabled(true);
							pnContent.setVisible(false);
							pnTable.setVisible(false);
							int nambd = Integer.valueOf(cbYearStart.getSelectedItem().toString());
							int namkt = Integer.valueOf(cbYearEnd.getSelectedItem().toString());
							if (nambd == namkt) {
								pnContent.setVisible(true);
								pnCurveLineChart.setVisible(false);
								pnPieChart.setVisible(true);
								lblDate.setText("Tổng quan doanh thu năm " + namkt);
								lblDate.setSize(400, 50);
								pnPieChart.setVisible(true);
								dateTimePicker.setVisible(false);
								pnTable.setVisible(false);
								resetField();
								try {
									ThongKeYear();
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								lblChartTitle.setText("BIỂU ĐỒ THỐNG KÊ DOANH THU THEO NĂM");
								if (lblTongDoanhThu.getText().equals("0 VNĐ")) {
									JOptionPane.showMessageDialog(null, "Không có dữ liệu thống kê của năm " + nambd);
								}
							}
							if (namkt > nambd) {
								String yearStart = cbYearStart.getSelectedItem().toString();
								String yearEnd = cbYearEnd.getSelectedItem().toString();
								lineChart.clear();
								try {
									setCurveLineChartData();
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								pnContent.setVisible(true);
								pnContent.setBounds(0, 192, 400, 535);
								dateTimePicker.setVisible(false);
								lblDate.setText("Tổng quan doanh thu từ năm " + yearStart + " đến " + yearEnd);
								lblDate.setFont(new Font("Arial", Font.BOLD, 17));
								lblDate.setSize(400, 50);
								resetField();
								try {
									ThongKeManyYear(nambd, namkt);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								if (lblTongDoanhThu.getText().equals("0 VNĐ")) {
									JOptionPane.showMessageDialog(null, "Không có dữ liệu thống kê của năm " + nambd);
								}
								pnCurveLineChart.setVisible(true);
							}
							if (nambd > namkt) {
								JOptionPane.showMessageDialog(null, "Năm bắt đầu phải nhỏ hơn năm kết thúc!");
								pnPieChart.setVisible(false);
								pnContent.setVisible(false);
								pnCurveLineChart.setVisible(false);
							}
						}
					}
				}
			} else if (cbThongKe.getSelectedItem().toString().equals("Khách hàng")) {
				pnContent.setVisible(false);
				pnTable.setVisible(false);
				pnPieChart.setVisible(false);
				pnCurveLineChart.setVisible(false);
				cbYearKH.setVisible(true);
				cbDate.removeItem("Ngày");
				cbYearStart.setVisible(false);
				cbYearEnd.setVisible(false);
				lblYearStart.setText("Tháng");
				lblYearEnd.setText("Năm");
				cbMonthKH.setVisible(true);
				cbYearKH.setVisible(true);
				pnTableKH.setVisible(true);
				if (cbMonthKH.getItemCount() == 0) {
					updateMonthYearKHCbo();
				}
				if (cbYearKH.getItemCount() == 0) {
					updateYearKHCbo();
				}
				if (cbDate.getItemCount() < 3) {
					cbDate.addItem("Toàn");
				}
				if (cbDate.getSelectedItem().toString().equals("Tháng")) {
					cbMonthKH.setEnabled(true);
					cbYearKH.setEnabled(true);
					String selectedYear = cbYearKH.getSelectedItem().toString();
					String selectedMonth = cbMonthKH.getSelectedItem().toString();
					clearTableKH();
					try {
						loadDataTKKHMonth(selectedYear, selectedMonth);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					dataset = createDatasetByMonthYear(selectedYear, selectedMonth);
					if (dataset.getRowCount() == 0) {
						pnBarChart.setVisible(false);
						JOptionPane.showMessageDialog(null,
								"Không có dữ liệu thống kê cho tháng " + selectedMonth + " năm " + selectedYear);
					} else {
						barChart = createChart(dataset);
						pnBarChart.setChart(barChart);
						pnBarChart.setVisible(true);
					}
				} else if (cbDate.getSelectedItem().toString().equals("Năm")) {
					cbMonthKH.setEnabled(false);
					cbYearKH.setEnabled(true);
					String selectedYear = cbYearKH.getSelectedItem().toString();
					clearTableKH();
					try {
						loadDataTKKHYear(selectedYear);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					dataset = createDatasetByYear(selectedYear);
					if (dataset.getRowCount() == 0) {
						pnBarChart.setVisible(false);
						JOptionPane.showMessageDialog(null, "Không có dữ liệu thống kê cho năm: " + selectedYear);
					} else {
						barChart = createChart(dataset);
						pnBarChart.setChart(barChart);
						pnBarChart.setVisible(true);
					}
				} else if (cbDate.getSelectedItem().toString().equals("Toàn")) {
					clearTableKH();
					try {
						loadDataTKKHALL();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					cbMonthKH.setEnabled(false);
					cbYearKH.setEnabled(false);
					pnBarChart.setVisible(true);
					dataset = createDataset();
					barChart = createChart(dataset);
					pnBarChart.setChart(barChart);
					pnBarChart.setVisible(true);
				}
			}
		} else if (o.equals(btnLamMoi)) {
			cbThongKe.setSelectedItem("Doanh thu");
			cbDate.setSelectedItem("Ngày");
		} else if (o.equals(btnProfile)) {
			dialog_user.setVisible(true);
		}
	}
}
