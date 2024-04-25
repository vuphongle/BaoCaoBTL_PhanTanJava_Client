package app;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.CompoundBorder;

import dao.ClientConnectionService;
import dao.PhongService;
import dao.TempDatPhongServices;
import entity.Phong;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;

public class GD_TrangChu extends JFrame implements ActionListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private final JPanel panel_1 = new JPanel();
	private JButton btnDatPhong;
	public JButton btnDanhSachPhong;
	public JButton btnNhanVien;
	private JButton btnKhachHang;
	public JButton btnSanPham;
	private JButton btnHoaDon;
	private JButton btnThongKe, btnKhuyenMai;
	private JButton btnTroGiup;
	private JLabel lblKaraoke;
	private JLabel lblCurrentTime;
	private final GD_DatPhong datPhong;
	private final GD_DanhSachPhong danhSachPhong = new GD_DanhSachPhong();
	private final GD_NhanVien nhanVien = new GD_NhanVien();
	private final GD_KhachHang khachHang = new GD_KhachHang();
	private  GD_HoaDon hoaDon;
	private final GD_SanPham sanPham = new GD_SanPham();
	private final GD_ThongKe thongKe;
	private final GD_KhuyenMai khuyenMai = new GD_KhuyenMai();
	private final GD_TroGiup troGiup = new GD_TroGiup(this);
	private JPanel panel_chuaTime;
	private final TempDatPhongServices tmp_dao ;
	private Dialog_User dialog_User= new Dialog_User();
	private InetAddress ip;
	
	private ClientConnectionService clientConnectionService;
	public GD_TrangChu() throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		super("Karaoke 4T");
		
		clientConnectionService = (ClientConnectionService) Naming.lookup(DataManager.getRmiURL() + "clientConnectionServices");
		
		ip = InetAddress.getLocalHost();
		tmp_dao = (TempDatPhongServices) Naming.lookup(DataManager.getRmiURL() + "tempDatPhongServices");
		thongKe = new GD_ThongKe();
		hoaDon	 = new GD_HoaDon();
		datPhong = new GD_DatPhong(this);
		ImageIcon icon = new ImageIcon("icon\\icon_Karaoke3.jpg");
		this.setIconImage(icon.getImage());
		initialize();
	}

	public static void main(String[] args) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException{
		GD_TrangChu home = new GD_TrangChu();
		home.setVisible(true);
	}

	private void initialize() {

		// hiển thị full màn hình
		setBounds(0, 0, 1388, 768);
		//code này để tín test full màn hình
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
//        setUndecorated(true);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		


		panel_1.setBorder(new CompoundBorder());
		panel_1.setBackground(Color.CYAN);

		this.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(0, 1, 8, 1));

		Panel panel_2 = new Panel();
		panel_2.setBackground(new Color(217, 226, 231));
		panel_1.add(panel_2);

		btnDatPhong = new JButton("ĐẶT PHÒNG                  (F1)");
		btnDatPhong.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnDatPhong.setForeground(Color.BLACK);
		btnDatPhong.setBackground(Color.white);
		btnDatPhong.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnDatPhong.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnDatPhong.setIcon(new ImageIcon("icon\\icon_DatPhong.png"));
		btnDatPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDatPhong.setBorderPainted(false);
		btnDatPhong.setBackground(new Color(217, 226, 231));

		btnDanhSachPhong = new JButton("DANH SÁCH PHÒNG   (F2)");
		btnDanhSachPhong.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnDanhSachPhong.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnDanhSachPhong.setIcon(new ImageIcon("icon\\icon_DSPhong.png"));
		btnDanhSachPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDanhSachPhong.setBorderPainted(false);
		btnDanhSachPhong.setForeground(Color.BLACK);
		btnDanhSachPhong.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnDanhSachPhong.setBackground(new Color(217, 226, 231));

		btnNhanVien = new JButton("NHÂN VIÊN                    (F3)");
		btnNhanVien.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnNhanVien.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnNhanVien.setIcon(new ImageIcon("icon\\icon_NhanVien.png"));
		btnNhanVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNhanVien.setBorderPainted(false);
		btnNhanVien.setForeground(Color.BLACK);
		btnNhanVien.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNhanVien.setBackground(new Color(217, 226, 231));

		btnKhachHang = new JButton("KHÁCH HÀNG              (F4)");
		btnKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnKhachHang.setBorderPainted(false);
		btnKhachHang.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnKhachHang.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnKhachHang.setIcon(new ImageIcon("icon\\icon_KhachHang.png"));
		btnKhachHang.setForeground(Color.BLACK);
		btnKhachHang.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnKhachHang.setBackground(new Color(217, 226, 231));

		btnSanPham = new JButton("SẢN PHẨM                    (F5)");
		btnSanPham.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnSanPham.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnSanPham.setIcon(new ImageIcon("icon\\icon_SanPham.png"));
		btnSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSanPham.setBorderPainted(false);
		btnSanPham.setForeground(Color.BLACK);
		btnSanPham.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSanPham.setBackground(new Color(217, 226, 231));

		// khuyen mai
		btnKhuyenMai = new JButton("KHUYẾN MÃI               (F6)");
		btnKhuyenMai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnKhuyenMai.setBorderPainted(false);
		btnKhuyenMai.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnKhuyenMai.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnKhuyenMai.setIcon(new ImageIcon("icon\\icon_KhuyenMai.png"));
		btnKhuyenMai.setForeground(Color.BLACK);
		btnKhuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnKhuyenMai.setBackground(new Color(217, 226, 231));
		
		/// Hóa đơn
		btnHoaDon = new JButton("HÓA ĐƠN                      (F7)");
		btnHoaDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHoaDon.setBorderPainted(false);
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnHoaDon.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnHoaDon.setIcon(new ImageIcon("icon\\icon_HoaDon.png"));
		btnHoaDon.setForeground(Color.BLACK);
		btnHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnHoaDon.setBackground(new Color(217, 226, 231));

		// thống kê
		btnThongKe = new JButton("THỐNG KÊ                   (F8)");
		btnThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThongKe.setBorderPainted(false);
		btnThongKe.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnThongKe.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnThongKe.setIcon(new ImageIcon("icon\\icon_ThongKe.png"));
		btnThongKe.setForeground(Color.BLACK);
		btnThongKe.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnThongKe.setBackground(new Color(217, 226, 231));

		// Trợ giúp
		btnTroGiup = new JButton("TRỢ GIÚP");
		btnTroGiup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTroGiup.setBorderPainted(false);
		btnTroGiup.setHorizontalAlignment(SwingConstants.LEFT); // căn lề trái button
		btnTroGiup.setIconTextGap(30); // Tạo Khoảng cách giữa icon và Nội dung(text)
		btnTroGiup.setIcon(new ImageIcon("icon\\icon_TroGiup.png"));
		btnTroGiup.setForeground(Color.BLACK);
		btnTroGiup.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnTroGiup.setBackground(new Color(217, 226, 231));
		


		// user
//		 btnUser.setIcon(new ImageIcon("icon\\icon_User.png"));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(217, 226, 231));

		JPanel pnMargin = new JPanel();
		pnMargin.setPreferredSize(new Dimension(270, 20));
		pnMargin.setBackground(new Color(217, 226, 231));
		panel_3.add(pnMargin);
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(255, 165, 0));
		lblNewLabel_1.setIcon(new ImageIcon("image\\hinh_trangdangnhap.jpg"));
		lblNewLabel_1.setBounds(0, 100, 100, 100);
		ImageIcon hinhgt1 = new ImageIcon("image\\hinh_trangdangnhap.jpg");
		Image image1 = hinhgt1.getImage();
		Image newImage1 = image1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		hinhgt1 = new ImageIcon(newImage1);
		lblNewLabel_1.setIcon(hinhgt1);
		panel_3.add(lblNewLabel_1);

		lblKaraoke = new JLabel("KARAOKE 4T");
		lblKaraoke.setHorizontalAlignment(SwingConstants.CENTER);
		lblKaraoke.setBounds(0, 100, 100, 30);
		lblKaraoke.setFont(new Font("Arial", Font.BOLD, 14));
		lblKaraoke.setForeground(Color.black);
		panel_3.add(lblKaraoke);

		panel_chuaTime = new JPanel();
		panel_chuaTime.setBackground(Color.WHITE);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnDatPhong, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnDanhSachPhong, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnNhanVien, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnKhachHang, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnSanPham, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnKhuyenMai, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnHoaDon, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnThongKe, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(btnTroGiup, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_chuaTime, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnDatPhong, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnDanhSachPhong, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnNhanVien, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnKhachHang, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnSanPham, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnKhuyenMai, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnHoaDon, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnThongKe, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnTroGiup, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(panel_chuaTime, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(50))
		);

		// CurrentTime
		lblCurrentTime = new JLabel();
		lblCurrentTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentTime.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblCurrentTime.setForeground(Color.BLACK);
		lblCurrentTime.setBackground(Color.WHITE);
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					lblCurrentTime.setText(sdf.format(cal.getTime()));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		panel_chuaTime.add(lblCurrentTime);

		panel_2.setLayout(gl_panel_2);

		new JPanel();
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		JPanel trangChu = new JPanel();
		trangChu.setBackground(Color.black);
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("image\\TrangChu.jpg"));
		lblNewLabel_2.setBounds(0, 100, 1080, 730);
		ImageIcon hinhgt = new ImageIcon("image\\TrangChu.jpg");
		Image image = hinhgt.getImage();
		Image newImage = image.getScaledInstance(1080, 730, java.awt.Image.SCALE_SMOOTH);
		hinhgt = new ImageIcon(newImage);
		lblNewLabel_2.setIcon(hinhgt);
		trangChu.add(lblNewLabel_2);
		cardPanel.add(trangChu, "TrangChu");
		cardPanel.add(datPhong, "DatPhong");
		cardPanel.add(danhSachPhong, "DanhSachPhong");
		cardPanel.add(nhanVien, "NhanVien");
		cardPanel.add(khachHang, "KhachHang");
		cardPanel.add(hoaDon, "HoaDon");
		cardPanel.add(sanPham, "SanPham");
		cardPanel.add(khuyenMai, "KhuyenMai");
		cardPanel.add(thongKe, "ThongKe");
		cardPanel.add(troGiup, "TroGiup");
		getContentPane().add(cardPanel, BorderLayout.CENTER);
		
		btnDatPhong.addActionListener(this);
		btnDanhSachPhong.addActionListener(this);
		btnNhanVien.addActionListener(this);
		btnKhachHang.addActionListener(this);
		btnSanPham.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnThongKe.addActionListener(this);
		btnTroGiup.addActionListener(this);
		btnKhuyenMai.addActionListener(this);
		this.addWindowListener(this);
		


		Action actionF1 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnDatPhong.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "DatPhong");
		    }
		};
		Action actionF2 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnDanhSachPhong.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "DanhSachPhong");
		    }
		};
		Action actionF3 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnNhanVien.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "NhanVien");
		    }
		};
		Action actionF4 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnKhachHang.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "KhachHang");
		    }
		};
		Action actionF5 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnSanPham.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "SanPham");
		    }
		};
		Action actionF6 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnKhuyenMai.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "KhuyenMai");
		    }
		};
		Action actionF7 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnHoaDon.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "HoaDon");
		    }
		};
		Action actionF8 = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        resetActiveTab();
		        btnThongKe.setBackground(Color.decode("#F2F0FF"));
		        cardLayout.show(cardPanel, "ThongKe");
		    }
		};

		// Liên kết phím F1 với hành động
		datPhong.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "doF1");
		datPhong.getRootPane().getActionMap().put("doF1", actionF1);
		// Liên kết phím F2 với hành động
		danhSachPhong.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"), "doF2");
		danhSachPhong.getRootPane().getActionMap().put("doF2", actionF2);
		// Liên kết phím F3 với hành động
		nhanVien.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F3"), "doF3");
		nhanVien.getRootPane().getActionMap().put("doF3", actionF3);
		// Liên kết phím F4 với hành động
		khachHang.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), "doF4");
		khachHang.getRootPane().getActionMap().put("doF4", actionF4);
		// Liên kết phím F5 với hành động
		sanPham.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "doF5");
		sanPham.getRootPane().getActionMap().put("doF5", actionF5);
		// Liên kết phím F6 với hành động
		khuyenMai.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F6"), "doF6");
		khuyenMai.getRootPane().getActionMap().put("doF6", actionF6);
		// Liên kết phím F7 với hành động
		hoaDon.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F7"), "doF7");
		hoaDon.getRootPane().getActionMap().put("doF7", actionF7);
		// Liên kết phím F8 với hành động
		thongKe.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"), "doF8");
		thongKe.getRootPane().getActionMap().put("doF8", actionF8);
		
		// nhấn F9 hiển thị giao diện user
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F9) {
                	try {
						dialog_User= new Dialog_User();
					} catch (RemoteException | MalformedURLException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	dialog_User.setVisible(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_F10) {
                    // Open new window here
                	resetActiveTab();
                	cardLayout.show(cardPanel, "TrangChu");
                }
            }
        });
        requestFocusInWindow();
        setFocusable(true);
	      

	}
	
	public void showDatPhongCard() {
	    resetActiveTab();
	    btnDatPhong.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "DatPhong");
	}
	
	public void showDSPhongCard() {
	    resetActiveTab();
	    btnDanhSachPhong.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "DanhSachPhong");
	}
	
	public void showNhanVienCard() {
	    resetActiveTab();
	    btnNhanVien.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "NhanVien");
	}
	
	
	public void showKhachHangCard() {
		resetActiveTab();
		btnKhachHang.setBackground(Color.decode("#F2F0FF"));
		cardLayout.show(cardPanel, "KhachHang");
	}
	
	public void showSanPhamgCard() {
	    resetActiveTab();
	    btnSanPham.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "SanPham");
	}
	
	public void showHoaDonCard() {
	    resetActiveTab();
	    btnHoaDon.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "HoaDon");
	}
	
	public void showThongKeCard() {
	    resetActiveTab();
	    btnThongKe.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "ThongKe");
	}
	
	public void showTroGiupCard() {
	    resetActiveTab();
	    btnTroGiup.setBackground(Color.decode("#F2F0FF"));
	    cardLayout.show(cardPanel, "TroGiup");
	}
	
	public void resetActiveTab() {
		btnDatPhong.setBackground(new Color(217, 226, 231));
		btnDanhSachPhong.setBackground(new Color(217, 226, 231));
		btnNhanVien.setBackground(new Color(217, 226, 231));
		btnKhachHang.setBackground(new Color(217, 226, 231));
		btnHoaDon.setBackground(new Color(217, 226, 231));
		btnSanPham.setBackground(new Color(217, 226, 231));
		btnThongKe.setBackground(new Color(217, 226, 231));
		btnTroGiup.setBackground(new Color(217, 226, 231));
		btnKhuyenMai.setBackground(new Color(217, 226, 231));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnDatPhong)) {
			resetActiveTab();
			btnDatPhong.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "DatPhong");
			PhongService phongService;
			try {
				phongService = (PhongService) Naming.lookup(DataManager.getRmiURL() + "phongServices");
				List<Phong> rooms = phongService.getallPhongs();
//				datPhong.loadData(rooms);
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (o.equals(btnDanhSachPhong)) {
			resetActiveTab();
			btnDanhSachPhong.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "DanhSachPhong");
		} else if (o.equals(btnNhanVien)) {
			resetActiveTab();
			btnNhanVien.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "NhanVien");
		} else if (o.equals(btnKhachHang)) {
			resetActiveTab();
			btnKhachHang.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "KhachHang");
		} else if (o.equals(btnHoaDon)) {
			resetActiveTab();
			btnHoaDon.setBackground(Color.decode("#F2F0FF"));
			hoaDon.clearTableOrderList();
			try {
				hoaDon.loadOrderListData();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(DataManager.getRole().equals("NV")) {
				hoaDon.btnXoa.setEnabled(false);
				hoaDon.btnSua.setEnabled(false);
				hoaDon.btnXoa.setBackground(Color.decode("#CCCCCC"));
				hoaDon.btnSua.setBackground(Color.decode("#CCCCCC"));
			}
			cardLayout.show(cardPanel, "HoaDon");
		} else if (o.equals(btnSanPham)) {
			resetActiveTab();
			btnSanPham.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "SanPham");
		} else if (o.equals(btnThongKe)) {
			resetActiveTab();
			btnThongKe.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "ThongKe");
		} else if (o.equals(btnTroGiup)) {
			resetActiveTab();
			btnTroGiup.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "TroGiup");
		}
		//sửa lại code 
		else if (o.equals(btnKhuyenMai)) {
			resetActiveTab();
			btnKhuyenMai.setBackground(Color.decode("#F2F0FF"));
			cardLayout.show(cardPanel, "KhuyenMai");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			tmp_dao.deleteALLTempDatPhong();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Map<String, Boolean> loadData = clientConnectionService.getLoadData();
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
		String ip_tam = "";
		for (Map.Entry<String, String> entry : mapIP_MSNV.entrySet()) {
			if (entry.getKey().equals(ip.getHostAddress())) {
				mnv = entry.getValue();
				ip_tam = entry.getKey();
			}
		}
		try {
			clientConnectionService.deleteFromMapIP_MSNV(ip.getHostAddress());
			clientConnectionService.deleteFromMapLoadData(mnv);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}