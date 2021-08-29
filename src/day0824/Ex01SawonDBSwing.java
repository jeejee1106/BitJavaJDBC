package day0824;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import oracle.db.DbConnect;

public class Ex01SawonDBSwing extends JFrame implements ItemListener, ActionListener{
	
	DbConnect db = new DbConnect();
	
	DefaultTableModel model;
	JTable table;
	JRadioButton[] rb = new JRadioButton[6]; //���� ��ư�� ���� �迭��? ��ư�� �������ϱ�?! �׷��� ��ġ ���� ����..������ ���� �� ���ѵ�???
	JButton btnDel, btnadd;
	
	public Ex01SawonDBSwing() {
		super("�������");
		this.setBounds(700,200,800,400);
		this.setDesign();
		
		//���̺� ���� �� db�κ��� ������ ��������
		this.sawonTableWrite(1); //�޼��忡�� �Ķ���ͷ� �޴°���
		
		this.getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	//db�� �����͸� �����ͼ� ���̺� �߰�
	//��ü:1, ����:2, ����:3, ������:4, ȫ����:5, ������:6
	public void sawonTableWrite(int select) {
		String sql = "";
		if(select ==1) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon";
		} else if(select ==2) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where gender='����'";
		} else if(select ==3) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where gender='����'";			
		} else if(select ==4) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='������'";	
		} else if(select ==5) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='ȫ����'";	
		} else if(select ==6) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='������'";	
		}
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//������ ������ ���� �� ��������
			model.setRowCount(0); //���� 0���� ����ڴ�. 5������ 5���� ����ڴ�!(5���� ����ڴ�)
			
			
			while(rs.next()) {
				//���̺� �߰��� ���� ����. DB�� �ѹ��� ����� �� �ƴϸ� ��Ʈ������ ��������.
				Vector<String> data = new Vector<String>();
				data.add(rs.getString("no"));
				data.add(rs.getString("id"));
				data.add(rs.getString("name"));
				data.add(rs.getString("buseo"));
				data.add(rs.getString("gender"));
				data.add(rs.getString("pay"));
				
				//������ �����͸� ������ �߰�
				model.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
	}
	
	private void setDesign() {
		this.setLayout(null);
		String[] title = {"��ȣ", "ID", "�����", "�μ���", "����", "���޿�"};
		model = new DefaultTableModel(title, 0); //���� �ְ� �హ���� �ϴ� 0���� �߰���!
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 80, 700, 300);
		this.add(js);
		
		//���� ��ư �߰�
		ButtonGroup bg = new ButtonGroup();
		String[] rb_label = {"��ü", "����", "����", "������", "ȫ����", "������"};
		int xpos = 10;
		for(int i = 0; i<rb.length; i++) {
			rb[i] = new JRadioButton(rb_label[i], i==0?true:false);
			rb[i].setBounds(xpos, 50, 90, 30);
			rb[i].setOpaque(false); //��ư���? ���ִ� �ڵ�
			//�̺�Ʈ �߰�
			rb[i].addItemListener(this);
			bg.add(rb[i]);
			this.add(rb[i]);
			xpos+=100;
		}
		
		//��� ������ư �߰�
		btnDel = new JButton("��� ����");
		btnDel.setBounds(10, 10, 100, 30);
		btnDel.addActionListener(this);
		this.add(btnDel);
		
		//��� �߰���ư �߰�
		btnadd = new JButton("��� �߰�");
		btnadd.setBounds(130, 10, 100, 30);
		btnadd.addActionListener(this);
		this.add(btnadd);
		
	}

	public static void main(String[] args) {
		Ex01SawonDBSwing ex = new Ex01SawonDBSwing();
	}

	@Override
	public void itemStateChanged(ItemEvent e) { //������ư �̺�Ʈ �߰��ϴ� �޼���!
		Object ob = e.getSource();
		for(int i = 0; i<rb.length; i++) {
			if(rb[i]==ob) {
				this.sawonTableWrite(i+1);
			}
		}	
		
//		if(ob==rb[0]) {
//			this.sawonTableWrite(1);
//		} else if(ob==rb[1]) {
//			this.sawonTableWrite(2);
//		} else if(ob==rb[2]) {
//			this.sawonTableWrite(3);
//		} else if(ob==rb[3]) {
//			this.sawonTableWrite(4);
//		} else if(ob==rb[4]) {
//			this.sawonTableWrite(5);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //��ư �̺�Ʈ �߰��ϴ� �޼���!
		Object ob = e.getSource();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "";
		
		if(ob==btnDel) {
			//���ȣ ���. ���� ���ϰ� ���� ��ư ���� ��� -1 ��ȯ.
			int row = table.getSelectedRow();
//			System.out.println(row); //���� �� ��������(�� ����Ǵ���) Ȯ���ϱ� ���� �ܼ� ���
			//���� ������ ���
			if(row==-1) {
				JOptionPane.showMessageDialog(this, "������ ���� �������ּ���");
				return;
			}
			
			//������ ���� in(num) �� ���. �迭���� id(num) �÷��� �ε���1�� ����! �׷��� 1�� ����. 2�� ���� �ε���2 �� �ִ� �̸��� ��µ�!
			String num = (String)model.getValueAt(row, 1); //num���ٰ� ������ ��(row)�� �ε���1�� ������ ���� �־��ٰž�
//			System.out.println(num); //�� ���� �� ���� ������ �ֿܼ� �ش� ���� id �����Ͱ� ��µ�
			
			
			// num���ش��ϴ� db������ ���� �� ���̺� �ٽ� ���(��ü)
			sql = "delete from sawon where num=?"; //���� �����ϴ� �� �ڵ�
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, num); //1�� ����ǥ�� num�� �ְڴ�!
				//����
				pstmt.execute();
				//���� �� �ٷ� ��ü ������ �ٽ� �ҷ�����
				this.sawonTableWrite(1);
				//���� ��ư�� 0�� ��ü �������� �Ѿ��
				rb[0].setSelected(true);
				
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				db.dbClose(pstmt, conn);
			}
			
		} else if(ob==btnadd) {
			//��ư�� ������ �̸�, ����, �μ�, �޿��� �Է� �޾ƾ���!
			String name = JOptionPane.showInputDialog("������� �Է����ּ���");
			String gender = JOptionPane.showInputDialog("������ �Է����ּ���");
			String buseo = JOptionPane.showInputDialog("�μ�(������, ȫ����, ������)�� �Է����ּ���");
			String pay = JOptionPane.showInputDialog("�޿��� �Է����ּ���");
			sql = "insert into sawon values (seq_sawon.nextval, ?, ?, ?, ?)";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, gender);
				pstmt.setString(3, buseo);
				pstmt.setString(4, pay);
				pstmt.execute();
				
				this.sawonTableWrite(1);
				rb[0].setSelected(true);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				db.dbClose(pstmt, conn);
			}
			
			
		}
		
	}

}
