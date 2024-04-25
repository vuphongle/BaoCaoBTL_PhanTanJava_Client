package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

import entity.Enum_TrangThai;
import entity.LoaiPhong;
import entity.Phong;
import entity.TempDatPhong;
import dao.LoaiPhongServices;
import dao.PhongService;
import dao.TempDatPhongServices;

public class Dialog_HienThiPhong extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private final GD_TrangChu trangChu;
	private static final long serialVersionUID = 1L;
	private final JLabel lblPhong;
    private final JLabel lblLoai;
    private final JLabel lblSucChua;
    private final JLabel lblTrangThai;
    private final JLabel lblGia;
    private final JLabel lblLoai_1;
    private final JLabel lblSucChua_1;
    private final JLabel lbltrangthai_1;
    private final JLabel lblgia_1;
    private final JLabel lblPhong_1;
	private final JButton btnDatPhong;
	private final PhongService p_Service;
	private final LoaiPhongServices lp_dao;

	private Dialog_DatPhongTrong_2 dialog_DatPhongTrong_2;
	private Dialog_DatPhongCho dialog_DatPhongCho;
	private final Phong p;
	private final LoaiPhong lp;
	private final JLabel lblSoNguoi;
	private final JTextField txtSoNguoi;
	private final TempDatPhongServices tmp_dao;
	private final JButton btn_DatPhongCho;

	public Dialog_HienThiPhong(String maPhong, GD_TrangChu trangChu) throws RemoteException, MalformedURLException, NotBoundException {
		tmp_dao =(TempDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "tempDatPhongServices");
		p_Service = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
		lp_dao = (LoaiPhongServices) Naming.lookup(DataManager.getRmiURL() + "loaiPhongServices");
		this.trangChu = trangChu;
		// kích thước
		// dialog--------------*****************************************************************
		getContentPane().setBackground(Color.WHITE);
		setSize(300, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("icon\\icon_white.png");
	    this.setIconImage(icon.getImage());

		// các
		// lbl-----------------------------------------------------------------------
		lblPhong = new JLabel("Phòng");
		lblPhong.setFont(new Font("Arial", Font.BOLD, 18));
		lblPhong.setBounds(20, 10, 100, 30);
		getContentPane().add(lblPhong);

		lblLoai = new JLabel("Loại");
		lblLoai.setFont(new Font("Arial", Font.BOLD, 18));
		lblLoai.setBounds(20, 50, 100, 30);
		getContentPane().add(lblLoai);

		lblSucChua = new JLabel("Sức chứa");
		lblSucChua.setFont(new Font("Arial", Font.BOLD, 18));
		lblSucChua.setBounds(20, 90, 100, 30);
		getContentPane().add(lblSucChua);

		lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 18));
		lblTrangThai.setBounds(20, 130, 100, 30);
		getContentPane().add(lblTrangThai);

		lblGia = new JLabel("Giá");
		lblGia.setFont(new Font("Arial", Font.BOLD, 18));
		lblGia.setBounds(20, 170, 100, 30);
		getContentPane().add(lblGia);

		// nút
		// button---------------------------------------------------------------------------
		btnDatPhong = new JButton("Đặt Phòng");
		btnDatPhong.setForeground(Color.WHITE);
		btnDatPhong.setFont(new Font("Arial", Font.BOLD, 18));
		btnDatPhong.setBackground(new Color(33, 167, 38, 255));
		btnDatPhong.setBounds(40, 210, 200, 35);
		btnDatPhong.setBackground(new Color(33, 167, 38, 255));
		btnDatPhong.setBounds(40, 250, 200, 40);
		btnDatPhong.setBorderPainted(false);
		getContentPane().add(btnDatPhong);

		lblPhong_1 = new JLabel(maPhong);
		lblPhong_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblPhong_1.setBounds(130, 10, 120, 30);
		getContentPane().add(lblPhong_1);

		p = p_Service.getPhongTheoMaPhong(maPhong);
		lp = lp_dao.getLoaiPhongTheoMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());

		lblLoai_1 = new JLabel(lp.getTenLoaiPhong());
		lblLoai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblLoai_1.setBounds(130, 50, 120, 30);
		getContentPane().add(lblLoai_1);

		lblSucChua_1 = new JLabel(lp.getSucChua() + "");
		lblSucChua_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblSucChua_1.setBounds(130, 90, 120, 30);
		getContentPane().add(lblSucChua_1);
		
		String trangThai = "";
		if(p.getTrangThai().equals(Enum_TrangThai.Trong))
			trangThai = "Trống";
		else if (p.getTrangThai().equals(Enum_TrangThai.Dang_su_dung))
			trangThai = "Đang dùng";
		else if (p.getTrangThai().equals(Enum_TrangThai.Dang_sua_chua))
			trangThai = "Bảo trì";
		else if (p.getTrangThai().equals(Enum_TrangThai.Da_xoa))
			trangThai = "Đã xóa";
		lbltrangthai_1 = new JLabel(trangThai);
		lbltrangthai_1.setFont(new Font("Arial", Font.BOLD, 15));
		lbltrangthai_1.setBounds(130, 130, 120, 30);
		getContentPane().add(lbltrangthai_1);

		lblgia_1 = new JLabel(lp.getDonGiaTheoGio() + "VNĐ");
		lblgia_1.setBackground(Color.WHITE);
		lblgia_1.setForeground(Color.RED);
		lblgia_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblgia_1.setBounds(130, 170, 120, 30);
		getContentPane().add(lblgia_1);

		lblSoNguoi = new JLabel("Số người");
		lblSoNguoi.setFont(new Font("Arial", Font.BOLD, 18));
		lblSoNguoi.setBounds(20, 210, 100, 30);
		getContentPane().add(lblSoNguoi);

		txtSoNguoi = new JTextField();
		txtSoNguoi.setFont(new Font("Arial", Font.BOLD, 15));
		txtSoNguoi.setBounds(130, 210, 100, 30);
		getContentPane().add(txtSoNguoi);
		txtSoNguoi.setColumns(10);
		
		btn_DatPhongCho = new JButton("Đặt Phòng Chờ");
		btn_DatPhongCho.setForeground(Color.WHITE);
		btn_DatPhongCho.setFont(new Font("Arial", Font.BOLD, 18));
		btn_DatPhongCho.setBorderPainted(false);
		btn_DatPhongCho.setBackground(new Color(255, 216, 0));
		btn_DatPhongCho.setBounds(40, 301, 200, 40);
		getContentPane().add(btn_DatPhongCho);
		// add sự kiện
		btnDatPhong.addActionListener(this);
		btn_DatPhongCho.addActionListener(this);
		
		
		Action datPhongAction = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        btnDatPhong.doClick();
		    }
		};

		
		Action datPhongChoAction = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        btn_DatPhongCho.doClick();
		    }
		};

		// Lấy InputMap và ActionMap của JPanel
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();

		// Thêm phím nóng cho btn
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "datPhong");
		actionMap.put("datPhong", datPhongAction);

		// Thêm phím nóng cho btn
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK), "datPhongCho");
		actionMap.put("datPhongCho", datPhongChoAction);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDatPhong)) {
			
			try {
				int songuoi=Integer.parseInt(txtSoNguoi.getText());
				if(songuoi<=0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số người hát lớn hơn 0!");
				}
				else {
					if (Integer.parseInt(txtSoNguoi.getText()) <= lp.getSucChua()) {
						TempDatPhong tmp = new TempDatPhong(p.getMaPhong(), Integer.parseInt(txtSoNguoi.getText()));
						tmp_dao.addTemp(tmp);
						dialog_DatPhongTrong_2 = new Dialog_DatPhongTrong_2(lblPhong_1.getText(), p, lp,
								Integer.parseInt(txtSoNguoi.getText()), trangChu);
						dispose();
						if(tmp_dao.getAllTemp().size() == 2) {
							DataManager.setSoDienThoaiKHDat("");
							dialog_DatPhongTrong_2.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(this, "Phòng " + p.getMaPhong() + " được thêm vào danh sách đặt phòng thành công.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Số người hát không được vượt quá sức chứa!");
					}
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "vui lòng nhập số nguyên dương!");
			}
	
		}
		else if(o.equals(btn_DatPhongCho)) {

			
			try {
				int songuoi=Integer.parseInt(txtSoNguoi.getText());
				if(songuoi<=0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số người hát lớn hơn 0!");
				}
				else {
					if (Integer.parseInt(txtSoNguoi.getText()) <= lp.getSucChua()) {
						dialog_DatPhongCho = new Dialog_DatPhongCho(lblPhong_1.getText(), p, lp, Integer.parseInt(txtSoNguoi.getText()), trangChu);
						dialog_DatPhongCho.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Số người hát không được vượt quá sức chứa!");
					}
				
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "vui lòng nhập số nguyên dương!");
			}
		
		}

	}
}
