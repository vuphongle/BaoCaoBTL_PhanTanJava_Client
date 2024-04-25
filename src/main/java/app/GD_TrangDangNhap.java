package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import dao.DangNhapServices;
import jakarta.persistence.Persistence;

public class GD_TrangDangNhap extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JLabel lblTitle;
	private final JTextField txtUsername;
	private final JTextField txtPassword;
	private final JButton btnLogin;
	private final JButton btnQuenMatKhau;
	private final JLabel lblKaeaoke;
	private DangNhapServices dangNhap_dao;
	private final GD_TrangChu gd_TrangChu;
	private String username;

	public GD_TrangDangNhap() throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		setTitle("Đăng Nhập KARAOKE 4T");
		setSize(720, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			dangNhap_dao = (DangNhapServices) Naming.lookup(DataManager.getRmiURL() + "dangNhapServices");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		gd_TrangChu = new GD_TrangChu();

		ImageIcon icon = new ImageIcon("image\\\\hinh_trangdangnhap.jpg");
		this.setIconImage(icon.getImage());

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(255, 255, 255, 255));

		lblTitle = new JLabel("KARAOKE 4T");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(5, 30, 300, 45);
		lblTitle.setForeground(Color.black);
		panel.add(lblTitle);

		txtUsername = new JTextField(20);
		txtUsername.setBounds(60, 95, 200, 25);
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
		txtUsername.setText("Tên đăng nhập");
		panel.add(txtUsername);

		txtPassword = new JPasswordField(20);
		txtPassword.setBounds(60, 135, 200, 25);
		txtPassword.setText("Son2001001");
		panel.add(txtPassword);

		btnLogin = new JButton("Đăng Nhập");
		btnLogin.setBounds(60, 175, 200, 40);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
		btnLogin.setForeground(Color.white);
		btnLogin.setBackground(Color.black);
		btnLogin.setBorder(new RoundedBorder(10));
		panel.add(btnLogin);

		btnQuenMatKhau = new JButton("Quên Mật Khẩu?");
		btnQuenMatKhau.setBounds(90, 240, 130, 25);
		btnQuenMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
		btnQuenMatKhau.setForeground(Color.black);
		btnQuenMatKhau.setBackground(Color.white);
		btnQuenMatKhau.setBorder(new RoundedBorder(10));
		panel.add(btnQuenMatKhau);

		lblKaeaoke = new JLabel("KARAOKE 4T");
		lblKaeaoke.setHorizontalAlignment(SwingConstants.CENTER);
		lblKaeaoke.setBounds(30, 330, 250, 30);
		lblKaeaoke.setFont(new Font("Arial", Font.BOLD, 18));
		lblKaeaoke.setForeground(Color.black);
		panel.add(lblKaeaoke);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("image\\hinh_trangdangnhap.jpg"));
		label.setBounds(300, 10, 370, 350);
		ImageIcon hinhgt = new ImageIcon("image\\hinh_trangdangnhap.jpg");
		Image image = hinhgt.getImage();
		Image newImage = image.getScaledInstance(370, 350, java.awt.Image.SCALE_SMOOTH);
		hinhgt = new ImageIcon(newImage);
		label.setIcon(hinhgt);
		panel.add(label);

		JLabel label1 = new JLabel("");
		label1.setIcon(new ImageIcon("image\\hinh_trangDN1.jpg"));
		label1.setBounds(0, 260, 50, 50);
		ImageIcon hinhgt1 = new ImageIcon("image\\hinh_trangDN1.jpg");
		Image image1 = hinhgt1.getImage();
		Image newImage1 = image1.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		hinhgt1 = new ImageIcon(newImage1);
		label1.setIcon(hinhgt1);
		panel.add(label1);
		getContentPane().add(panel);
		// nhấp nháy
		txtUsername.addFocusListener(new FocusListener() { // Thêm FocusListener
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsername.getText().equals("Tên đăng nhập")) {
					txtUsername.setText("2001001");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsername.getText().isEmpty()) {
					txtUsername.setText("Tên đăng nhập");
				}
			}
		});
		txtPassword.addFocusListener(new FocusListener() { // Thêm FocusListener
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPassword.getText().equals("Mật khẩu")) {
					txtPassword.setText("Son2001001");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtPassword.getText().isEmpty()) {
					txtPassword.setText("Mật khẩu");
				}
			}
		});
		setEnterKeyAction(btnLogin);
		setEnterKeyAction(btnQuenMatKhau);
		btnLogin.addActionListener(this);
		btnQuenMatKhau.addActionListener(this);
		this.getRootPane().setDefaultButton(btnLogin);

	}

	public static void main(String[] args) throws RemoteException, UnknownHostException {
		ImageIcon img = new ImageIcon("icon\\icon_Karaoke3.jpg");
		GD_TrangDangNhap trangDangNhap = null;
		try {
			trangDangNhap = new GD_TrangDangNhap();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trangDangNhap.setVisible(true);
		trangDangNhap.setIconImage(img.getImage());
	}

	private void setEnterKeyAction(JButton button) {
		// Lấy InputMap và ActionMap của nút
		InputMap inputMap = button.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = button.getActionMap();

		// Đặt phím Enter để kích hoạt nút
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		actionMap.put("Enter", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				if (o.equals(btnLogin)) {
					btnLogin.doClick();
				} else if (o.equals(btnQuenMatKhau)) {
					btnQuenMatKhau.doClick();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLogin)) {
			username = txtUsername.getText();
			char[] mk = ((JPasswordField) txtPassword).getPassword();
			String mkstr = new String(mk);

			try {
				if (dangNhap_dao.Timkiem(username, mkstr)) {
					if(DataManager.getLoadData() != null) {
						int check = 0;
						Map<String, Boolean> loadData = DataManager.getLoadData();
						for (String key : loadData.keySet()) {
							if (username.equals(key)) {
					            JOptionPane.showMessageDialog(this, "Tài khoản đang được sử dụng ở máy khác!");
					            check = 1;
					            break;
					        }
						}
						if(check == 0) {
							DataManager.addLoadData(username, false);	
							InetAddress ip = InetAddress.getLocalHost(); 
							DataManager.addMapIP_MSNV(ip.getHostAddress(), username);
							String roleName = dangNhap_dao.getRole(username, mkstr);
							if (roleName.equals("Quản lý")) {
								DataManager.setRole("QL");
								DataManager.setRolePassword("QLpassword");
							} else if (roleName.equals("Nhân viên")) {
								DataManager.setRole("NV");
								DataManager.setRolePassword("NVpassword");
								gd_TrangChu.btnNhanVien.setEnabled(false);
								gd_TrangChu.btnSanPham.setEnabled(false);
							}
							gd_TrangChu.setVisible(true);
							dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
				}
			} catch (HeadlessException | RemoteException | UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (o.equals(btnQuenMatKhau)) {
			GD_QuenMatKhau quenmk = new GD_QuenMatKhau();
			quenmk.setVisible(true);
			dispose();
		}
	}
}
