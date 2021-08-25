package day0824;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ex02shopMain extends JFrame implements ActionListener{
	
	JButton btnAdd, btnDel, btnUpdate, btnList;
	ShopDBModel dbmodel = new ShopDBModel();
	
	public Ex02shopMain() {
		super("Shop ����");
		this.setBounds(100, 100, 200, 300);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void setDesign() {
		this.setLayout(new GridLayout(4,1)); //4�� 1���� �׸��� ���̾ƿ�!
		btnAdd = new JButton("��ǰ�߰�");
		btnDel = new JButton("��ǰ����");
		btnUpdate = new JButton("��ǰ����");
		btnList = new JButton("��ǰ���");
		
		this.add(btnAdd);
		this.add(btnDel);
		this.add(btnUpdate);
		this.add(btnList);
		
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnList.addActionListener(this);
	}

	public static void main(String[] args) {
		Ex02shopMain ex = new Ex02shopMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob==btnAdd) {
			ShopAddForm add = new ShopAddForm();
		} else if(ob==btnDel){ //������ Ȯ���Ұ� ���Ƽ� �ڵ尡 ���� ������� ������ �׷��� ���ִ°� ������??������
			int ans = JOptionPane.showConfirmDialog(btnDel, "���� �����ϰڽ��ϱ�?"); //��ư�� 3���� �ڵ����� ����� . yes, no, cancle. yes=0, no=1, cancle=1, ������ ������ -1�� ��ȯ.
			if(ans==0) { //�� ���� 0�� ��ȯ������(yes�� ������)? ����!
				String num = JOptionPane.showInputDialog(btnDel, "������ ���ȣ num�� �Է����ּ���."); //���̾�α� ������Ʈ�� ��� ������ �Ұų�??btnDel��ġ��!! ��, this��� ���ڸ� �ָ� ������ ��򰡿� ��Ÿ��
				if(num==null) { //num�� �Է��ϴ� ������Ʈ���� ������ ������ null�� ��ȯ�ȴ�. null�� ���´ٴ� ���� ���� ������ �����ٴ� ��. 
					return; //�׷��ϱ� â�� ���������� ������ �����Ŵϱ�(null�� ��ȯ��Ų�Ŵϱ�) �޼��带 ����(return)��Ű�� �ȴ�.
				}
				int delCode = dbmodel.deleteShop(Integer.parseInt(num));
				if(delCode==0) { //���� �ƹ��͵� ���õ��� ������ 0�� ��ȯ�ȴ�.
					JOptionPane.showMessageDialog(btnDel, "�ش� num�� �������� �ʽ��ϴ�."); 
				} else {
					JOptionPane.showMessageDialog(btnDel, "��ǰ�� �����Ǿ����ϴ�.");
				}
			}
		} else if(ob==btnUpdate){
			String num = JOptionPane.showInputDialog(btnUpdate, "������ ���ȣ num�� �Է����ּ���");
			if(num==null) { // ���� ������ null��ȯ
				return;
			} else {
				ShopDTO dto = dbmodel.getData(num);
				ShopUpdateForm updateForm = new ShopUpdateForm();
				//�������� ������ �ֱ�
				updateForm.num = dto.getNum();
				updateForm.tfSang.setText(dto.getSangpum());
				updateForm.imageName = dto.getPhoto();
				updateForm.tfSu.setText(String.valueOf(dto.getSu()));
				updateForm.tfDan.setText(String.valueOf(dto.getDan()));
				updateForm.lblPhoto.setText(dto.getPhoto());
				
				//������ ���� �� ���� �� ���̰� �ϱ�
				updateForm.setVisible(true);
			}
			
		} else if(ob==btnList){
			ShopListForm list = new ShopListForm();
		}
		
		
		
	}

}
