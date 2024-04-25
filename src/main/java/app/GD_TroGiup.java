package app;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class GD_TroGiup extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel lblTitle;
    private final JLabel lblProfile;
    private final JLabel lblListTitle;
    private final JLabel lblF1;
    private final JLabel lblF2;
    private final JLabel lblF3;
    private final JLabel lblF4;
    private final JLabel lblF5;
    private final JLabel lblF6;
    private final JLabel lblF7;
    private final JLabel lblF8;
    private final JLabel lblCtrl_N;
    private final JLabel lblCtrl_M;
    private final JLabel lblCtrl_K;
    private final JLabel lblCtrl_J;
    private final JLabel lblCtrl_H;
	private final JLabel lblCayTitle;
	private final JLabel lblGioiThieu;
	private final Font font_Content;
	private final JLabel lblGT1;
	private final JComponent lblGT3;
	private final JLabel lblGT2;
	private final JComponent lblGT4;
	private final JComponent lblGT5;
	private JLabel lblTaiLieu;
	private final JLabel lblLink;
	private final JLabel lblF9;
	private final JLabel lblF10;
	private final JLabel lblCtrl_R;
	private final JLabel lblCtrl_E;
	private final JLabel lblEnter;
	private final JLabel lblDelete;
	private final JLabel lblSP;
	private final JLabel lblKH;
	private final JLabel lblNV;
	private final JLabel lblHD;
	private JLabel lblDV;
	private final JLabel lblDS;
	private final JLabel lblVND;
	private JLabel lblSX;
	private final Font font_Content2;
	private final JLabel lblLink2;
	private final JLabel lblVideo;
	private final JLabel lblVideo2;
	private final JLabel lblHotLine;
	private final JLabel lblDiaChi;
	private final JTree interfaceTree;
//    private GD_TrangChu home;
//    private GD_TrangChu home_CardDatPhong;
//    private GD_TrangChu home_CardDSPhong;
//    private GD_TrangChu home_CardNhanVien;
//    private GD_TrangChu home_CardKhachHang;
//    private GD_TrangChu home_CardSanPham;
//    private GD_TrangChu home_CardHoaDon;
//    private GD_TrangChu home_CardThongKe;
//    private GD_TrangChu home_CardTroGiup;
	private final GD_TrangChu trangChu;
	public GD_TroGiup(GD_TrangChu trangChu) throws RemoteException{
		this.trangChu = trangChu;
		setBackground(new Color(242, 240, 255));
		setLayout(null);
		font_Content = new Font("Arial", Font.ITALIC, 14);
		font_Content2 = new Font("Arial", Font.BOLD, 15);
		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(Color.decode("#B5E6FB"));
		pnNorth.setBounds(0, 0, 1080, 60);
		pnNorth.setLayout(null);
		lblTitle = new JLabel("TRỢ GIÚP");
		lblTitle.setBounds(501, 15, 200, 30);
		lblTitle.setForeground(Color.black);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		pnNorth.add(lblTitle);
		lblProfile = new JLabel("");
		lblProfile.setBackground(new Color(255, 165, 0));
		lblProfile.setIcon(new ImageIcon("icon\\icon_profile.png"));
		lblProfile.setBounds(1020, 7, 45, 45);
		ImageIcon iconProfile = new ImageIcon("icon\\icon_profile.png");
		iconProfile = new ImageIcon(iconProfile.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		lblProfile.setIcon(iconProfile);
		pnNorth.add(lblProfile);
		add(pnNorth);
		
//		Styling Content
		JPanel pnContent = new JPanel();
		pnContent.setBackground(new Color(255, 255, 255));
		pnContent.setBounds(0, 60, 1080, 706);
		pnContent.setLayout(null);
		pnContent.add(lblListTitle = new JLabel("HƯỚNG DẪN SỬ DỤNG PHẦN MỀM QUẢN LÝ CỬA HÀNG KARAOKE 4T"));
		lblListTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblListTitle.setBounds(32, 18, 700, 50);
		
		pnContent.add(lblCayTitle = new JLabel("CẤU TRÚC CÂY MÀN HÌNH"));
		lblCayTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblCayTitle.setBounds(760, 18, 320, 50);
		
		pnContent.add(lblGioiThieu = new JLabel("* Giới thiệu chung về phần mềm:"));
		lblGioiThieu.setFont(new Font("Arial", Font.BOLD, 18));
		lblGioiThieu.setBounds( 32, 65, 700, 25);
		pnContent.add(lblGT1 = new JLabel("Phần mềm Quản lý cửa hàng Karaoke 4T là phần mềm hỗ trợ các dịch vụ như: Đặt phòng, chuyển phòng"));
		lblGT1.setFont(font_Content);
		lblGT1.setBounds( 53, 90, 700, 17);
		pnContent.add(lblGT2 = new JLabel("thêm dịch vụ,thanh toán hóa đơn cho khách hàng.Bên cạnh đó hỗ trợ chủ cửa hàng quản lý nhân viên,phòng"));
		lblGT2.setFont(font_Content);
		lblGT2.setBounds( 32, 110, 710, 17);
		pnContent.add(lblGT3 = new JLabel("hát, dịch vụ, hóa đơn một cách thuận tiện nhất. Phần mềm còn giúp tìm phiếu, thống kê các số liệu về doanh"));
		lblGT3.setFont(font_Content);
		lblGT3.setBounds( 32, 130, 700, 17);
		pnContent.add(lblGT4 = new JLabel("thu theo từng khoảng/mốc thời gian cụ thể một cách trực quan, chính xác nhất phục vụ cho việc kinh doanh"));
		lblGT4.setFont(font_Content);
		lblGT4.setBounds( 32, 150, 700,17);		
		pnContent.add(lblGT5 = new JLabel("của cửa hàng Karaoke."));
		lblGT5.setFont(font_Content);
		lblGT5.setBounds( 32, 170, 600, 17);
		
		pnContent.add(lblTaiLieu = new JLabel("* Các tài liệu hướng dẫn phần mềm:"));
		lblTaiLieu.setFont(new Font("Arial", Font.BOLD, 18));
		lblTaiLieu.setBounds( 32, 200, 600, 40);
		pnContent.add(lblLink = new JLabel("Link web hướng dẫn: "));
		lblLink.setFont(font_Content2);
		lblLink.setBounds( 32, 240, 170, 30);
		pnContent.add(lblLink2 = new JLabel("HuongDanPhanMem\\index.html"));
		lblLink2.setFont(font_Content2);
		lblLink2.setForeground(Color.blue);
		lblLink2.setBounds( 202, 240, 470, 30);
		pnContent.add(lblVideo = new JLabel("Video hướng dẫn: "));
		lblVideo.setFont(font_Content2);
		lblVideo.setBounds( 32, 270, 140, 30);
		pnContent.add(lblVideo2 = new JLabel("https://www.youtube.com/watch?v=6yFKPBBZtTs"));
		lblVideo2.setFont(font_Content2);
		lblVideo2.setForeground(Color.blue);
		lblVideo2.setBounds( 182, 270, 510, 30);
		
		pnContent.add(lblTaiLieu = new JLabel("* Danh sách các phím tắt được sử dụng:"));
		lblTaiLieu.setFont(new Font("Arial", Font.BOLD, 18));
		lblTaiLieu.setBounds( 32, 310, 600, 40);
		JPanel pnBoxWrapper = new JPanel();
		pnBoxWrapper.setBounds(32, 350, 700, 140);
		pnBoxWrapper.setBackground(new Color(144, 183, 199));
		pnBoxWrapper.setLayout(null);
		pnBoxWrapper.add(lblF1 = new JLabel("F1: Đặt phòng"));
		lblF1.setBounds(20, 0, 100, 50);
		pnBoxWrapper.add(lblF2 = new JLabel("F2: Danh sách phòng"));
		lblF2.setBounds(160, 0, 120, 50);
		pnBoxWrapper.add(lblF3 = new JLabel("F3: Nhân viên"));
		lblF3.setBounds(320, 0, 100, 50);
		pnBoxWrapper.add(lblF4 = new JLabel("F4: Khách hàng"));
		lblF4.setBounds(440, 0, 100, 50);
		pnBoxWrapper.add(lblF5 = new JLabel("F5: Sản phẩm"));
		lblF5.setBounds(570, 0, 100, 50);
		pnBoxWrapper.add(lblF6 = new JLabel("F6: Khuyến mãi"));
		lblF6.setBounds(20, 20, 100, 50);
		pnBoxWrapper.add(lblF7 = new JLabel("F7: Hóa đơn"));
		lblF7.setBounds(160, 20, 100, 50);
		pnBoxWrapper.add(lblF8 = new JLabel("F8: Thống kê"));
		lblF8.setBounds(320, 20, 100, 50);
		pnBoxWrapper.add(lblF9 = new JLabel("F9: Tàì khoản"));
		lblF9.setBounds(440, 20, 100, 50);
		pnBoxWrapper.add(lblF10 = new JLabel("F10: Trang chủ"));
		lblF10.setBounds(570,20 , 100, 50);
		pnBoxWrapper.add(lblCtrl_N = new JLabel("CRTL + N: Đặt phòng"));
		lblCtrl_N.setBounds(20, 50, 180, 50);
		pnBoxWrapper.add(lblCtrl_M = new JLabel("CRTL + M: Đặt phòng chờ"));
		lblCtrl_M.setBounds(210, 50, 180, 50);
		pnBoxWrapper.add(lblCtrl_K = new JLabel("CTRL + K: Nhận Phòng"));
		lblCtrl_K.setBounds(400, 50, 160, 50);
		pnBoxWrapper.add(lblCtrl_J = new JLabel("CTRL + J: Hủy phòng"));
		lblCtrl_J.setBounds(560, 50, 150, 50);
		pnBoxWrapper.add(lblCtrl_H = new JLabel("CTRL + H: Thêm dịch vụ"));
		lblCtrl_H.setBounds(20, 70, 160, 50);
		pnBoxWrapper.add(lblCtrl_R = new JLabel("CTRL + R: Chuyển phòng"));
		lblCtrl_R.setBounds(210, 70, 160, 50);
		pnBoxWrapper.add(lblCtrl_E = new JLabel("CTRL + E: Thanh Toán"));
		lblCtrl_E.setBounds(400, 70, 160, 50);
		pnBoxWrapper.add(lblF1);
		pnBoxWrapper.add(lblF2);
		pnBoxWrapper.add(lblF3);
		pnBoxWrapper.add(lblF4);
		pnBoxWrapper.add(lblF5);
		pnBoxWrapper.add(lblF6);
		pnBoxWrapper.add(lblF7);
		pnBoxWrapper.add(lblF8);
		pnBoxWrapper.add(lblF9);
		pnBoxWrapper.add(lblF10);
		pnBoxWrapper.add(lblCtrl_N);
		pnBoxWrapper.add(lblCtrl_M);
		pnBoxWrapper.add(lblCtrl_K);
		pnBoxWrapper.add(lblCtrl_J);
		pnBoxWrapper.add(lblCtrl_H);
		pnBoxWrapper.add(lblCtrl_R);
		pnBoxWrapper.add(lblCtrl_E);
		pnBoxWrapper.add(lblDelete = new JLabel("Delete: Xóa dữ liệu đã chọn trong giao diện"));
		lblDelete.setBounds(80, 110, 300, 25);
		pnBoxWrapper.add(lblEnter = new JLabel("Enter: Đồng ý với các yêu cầu nghiệp vụ"));
		lblEnter.setBounds(380, 110, 300, 25);
		pnContent.add(pnBoxWrapper);

		pnContent.add(lblTaiLieu = new JLabel("* Chú thích các từ khóa:"));
		lblTaiLieu.setFont(new Font("Arial", Font.BOLD, 18));
		lblTaiLieu.setBounds( 32, 500, 700, 40);

		JPanel pnKeyWordContent = new JPanel();
		pnKeyWordContent.setBackground(new Color(144, 183, 199));
		pnKeyWordContent.setLayout(null);
		pnKeyWordContent.setBounds(32, 540, 700, 70);
		pnKeyWordContent.add(lblSP = new JLabel("SP: SẢN PHẨM"));
		lblSP.setBounds(32, 0, 120, 35);
		pnKeyWordContent.add(lblKH = new JLabel("KH: KHÁCH HÀNG"));
		lblKH.setBounds(165, 0, 120, 35);
		pnKeyWordContent.add(lblNV = new JLabel("NV: NHÂN VIÊN"));
		lblNV.setBounds(305, 0, 120, 35);
		pnKeyWordContent.add(lblHD = new JLabel("HD: HÓA ĐƠN"));
		lblHD.setBounds(455, 0, 120, 35);
		pnKeyWordContent.add(lblDV = new JLabel("DV: DỊCH VỤ"));
		lblDV.setBounds(570, 0, 120, 35);
		pnKeyWordContent.add(lblDV = new JLabel("KM: KHUYẾN MÃI"));
		lblDV.setBounds(32, 25, 120, 35);
		pnKeyWordContent.add(lblDS = new JLabel("DS: DANH SÁCH"));
		lblDS.setBounds(165, 25, 120, 35);
		pnKeyWordContent.add(lblVND = new JLabel("VND: VIỆT NAM ĐỒNG"));
		lblVND.setBounds(305, 25, 130, 35);
		pnKeyWordContent.add(lblSX = new JLabel("SX: SẢN XUẤT"));
		lblSX.setBounds(455, 25, 120, 35);
		pnKeyWordContent.add(lblSX = new JLabel("STT: SỐ THỨ TỰ"));
		lblSX.setBounds(570, 25, 120, 35);
		pnContent.add(pnKeyWordContent);

		
		JPanel pnCay = new JPanel();
		pnCay.setBackground(new Color(237, 236, 236));
		pnCay.setBounds(760, 60, 290, 550);
		pnCay.setLayout(null);
		pnContent.add(pnCay);
		// Tạo cây mô phỏng cấu trúc màn hình
        Node root = createInterfaceTree();
        DefaultMutableTreeNode rootNode = convertToTreeNode(root);
        interfaceTree = new JTree(rootNode);
        interfaceTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        interfaceTree.setBounds(10, 20, 270, 540);
        interfaceTree.setBackground(new Color(237, 236, 236));
        interfaceTree.setFont(new Font("Arial", Font.PLAIN, 15));
        pnCay.add(interfaceTree);
       
        for (int i = 0; i < interfaceTree.getRowCount(); i++) {
            interfaceTree.expandRow(i);
        }
		
		JPanel pnFooter = new JPanel();
		pnFooter.setBackground(new Color(222, 218, 226));
		pnFooter.setBounds(0, 630, 1080, 60);
		pnFooter.setLayout(null);
		pnContent.add(pnFooter);
		
		pnFooter.add(lblHotLine = new JLabel("Đường dây nóng: 0344.387.030"));
		lblHotLine.setBounds(10,10,300,20);
		lblHotLine.setFont(new Font("Arial", Font.ITALIC, 18));
		pnFooter.add(lblDiaChi = new JLabel("Số 12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, TP.HCM"));
		lblDiaChi.setBounds(620,10,500,20);
		lblDiaChi.setFont(new Font("Arial", Font.ITALIC, 18));
		
		add(pnContent);
		
		lblLink2.addMouseListener(this);
		lblVideo2.addMouseListener(this);
		// Thêm sự kiện lắng nghe cho cây màn hình
        interfaceTree.addMouseListener(this);
	}
	
	private static void openWebpage(String uri) {
		try {
			Desktop.getDesktop().browse(new URI(uri));
		} catch (IOException | URISyntaxException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void openLocalHtmlFile(String filePath) {
	    try {
	        File htmlFile = new File(filePath);

	        if (htmlFile.exists()) {
	            Desktop.getDesktop().browse(htmlFile.toURI());
	        } else {
	            System.out.println("File không tồn tại.");
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private static Node createInterfaceTree() {
        Node root = new Node("Root");

        Node loginNode = new Node("GD Đăng nhập");
        Node QuenMatKhau_Node = new Node("GD Quên mật khẩu");
        Node homeNode = new Node("GD Trang chủ");

        Node roomServiceNode = new Node("GD Quản lý Đặt phòng");
        Node roomServiceNode2 = new Node("GD Đặt phòng");
        Node Wait_Order_Node = new Node("GD Đặt phòng chờ");
        Node addServiceNode2 = new Node("GD thêm dịch vụ");
        Node paymentNode = new Node("GD Thanh toán");
        Node traDV_Node = new Node("GD Trả Dịch vụ");
        Node transferNode = new Node("GD Chuyển phòng");
        Node deleteRoomNode = new Node("GD Hủy phòng");
        Node addRoomNode = new Node("GD Nhận phòng");
        Node findOrderNode = new Node("GD Tìm phiếu đặt phòng");

        Node dSPhong_Node = new Node("GD Danh sách phòng");
        Node nhanVien_Node = new Node("GD Nhân viên");
        Node khachHang_Node = new Node("GD Khách hàng");
        Node sanPham_Node = new Node("GD Sản phẩm");
        Node khuyenMai_Node = new Node("GD Khuyến mãi");
        Node hoaDon_tNode = new Node("GD Hóa đơn");
        Node thongKe_Node = new Node("GD Thống kê");
        Node troGiup_Node = new Node("GD Trợ giúp");
        Node taiKhoan_Node = new Node("GD Tài khoản");

        root.addChild(loginNode);

        loginNode.addChild(QuenMatKhau_Node);
        loginNode.addChild(homeNode);
        
        homeNode.addChild(roomServiceNode);
        homeNode.addChild(dSPhong_Node);
        homeNode.addChild(nhanVien_Node);
        homeNode.addChild(khachHang_Node);
        homeNode.addChild(sanPham_Node);
        homeNode.addChild(khuyenMai_Node);
        homeNode.addChild(hoaDon_tNode);
        homeNode.addChild(thongKe_Node);
        homeNode.addChild(troGiup_Node);
        homeNode.addChild(taiKhoan_Node);
        
        paymentNode.addChild(traDV_Node);
        
        roomServiceNode.addChild(roomServiceNode2);
        roomServiceNode.addChild(addServiceNode2);
        roomServiceNode.addChild(Wait_Order_Node);
        roomServiceNode.addChild(transferNode);
        roomServiceNode.addChild(paymentNode);
        roomServiceNode.addChild(deleteRoomNode);
        roomServiceNode.addChild(addRoomNode);
        roomServiceNode.addChild(findOrderNode);

        return root;
    }

    private static DefaultMutableTreeNode convertToTreeNode(Node node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.getInterfaceName());
        for (Node child : node.getChildren()) {
            treeNode.add(convertToTreeNode(child));
        }
        return treeNode;
    }
	
	public static void main(String[] args) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
		 GD_TrangChu trangChuInstance = new GD_TrangChu();
         new GD_TroGiup(trangChuInstance).setVisible(true);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == lblLink2) {
			openLocalHtmlFile("HuongDanPhanMem\\index.html");
		}else if(e.getSource() == lblVideo2) {
			openWebpage("https://www.youtube.com/watch?v=6yFKPBBZtTs");
		}else if (e.getSource() == interfaceTree) {
	        // Lấy nút được chọn
	        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) interfaceTree.getLastSelectedPathComponent();

	        // Kiểm tra nếu nút không null và có chứa thông tin interface
	        if (selectedNode != null && selectedNode.getUserObject() instanceof String) {
	            String selectedInterface = (String) selectedNode.getUserObject();

	            // Xử lý logic tương ứng với nút được chọn
	            switch (selectedInterface) {
	                case "GD Đăng nhập":
					GD_TrangDangNhap dangNhap = null;
					try {
						dangNhap = new GD_TrangDangNhap();
					} catch (RemoteException | UnknownHostException | MalformedURLException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                	dangNhap.setVisible(true);
	                	setVisible(false);
	                	trangChu.setVisible(false);
	                    break;
	                case "GD Trang chủ":     
	                	trangChu.resetActiveTab();
	                	trangChu.setVisible(true);
	                	setVisible(false);
	                    break;
	                case "GD Quản lý Đặt phòng":
	                	trangChu.showDatPhongCard();
	                	setVisible(false);
	                    break;    
	                case "GD Danh sách phòng":
	                	trangChu.showDSPhongCard();
	                	setVisible(false);
	                    break;
	                    
	                case "GD Nhân viên":
	                	trangChu.showNhanVienCard();
	                	setVisible(false);
	                    break;
	                case "GD Khách hàng":
	                	trangChu.showKhachHangCard();
	                	setVisible(false);
	                    break;
	                case "GD Sản phẩm":
	                	trangChu.showSanPhamgCard();
	                	setVisible(false);
	                    break;
	                case "GD Hóa đơn":
	                	trangChu.showHoaDonCard();
	                	setVisible(false);
	                    break;
	                case "GD Thống kê":
	                	trangChu.showThongKeCard();
	                	setVisible(false);
	                    break;
	                case "GD Trợ giúp":
	                	trangChu.showTroGiupCard();
	                	setVisible(false);
	                    break;
	                case "GD Tài khoản":
					Dialog_User user = null;
					try {
						user = new Dialog_User();
					} catch (RemoteException | MalformedURLException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                	user.setVisible(true);
	                	setVisible(false);
//	                	trangChu.setVisible(false);
	                    break;
	            }
	        }
		}
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
		if(e.getSource() == lblLink2) {
			lblLink2.setText("<html><u>" + lblLink2.getText() + "</u></html>");
			lblLink2.setForeground(new Color(121, 171, 255));
		}else if(e.getSource() == lblVideo2) {
			lblVideo2.setText("<html><u>" + lblVideo2.getText() + "</u></html>");
	        lblVideo2.setForeground(new Color(121, 171, 255));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == lblLink2) {
			lblLink2.setText(lblLink2.getText());
			lblLink2.setForeground(Color.blue);
		}else if(e.getSource() == lblVideo2) {
			lblVideo2.setText(lblVideo2.getText());
	        lblVideo2.setForeground(Color.blue);
		}
	}
}
