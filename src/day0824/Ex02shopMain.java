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
		super("Shop 관리");
		this.setBounds(100, 100, 200, 300);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void setDesign() {
		this.setLayout(new GridLayout(4,1)); //4행 1열의 그리드 레이아웃!
		btnAdd = new JButton("상품추가");
		btnDel = new JButton("상품삭제");
		btnUpdate = new JButton("상품수정");
		btnList = new JButton("상품출력");
		
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
		} else if(ob==btnDel){ //삭제는 확인할게 많아서 코드가 조금 길어지긴 하지만 그래두 해주는게 좋겠져??ㅎㅎㅎ
			int ans = JOptionPane.showConfirmDialog(btnDel, "정말 삭제하겠습니까?"); //버튼이 3개가 자동으로 생긴다 . yes, no, cancle. yes=0, no=1, cancle=1, 엑스를 누르면 -1이 반환.
			if(ans==0) { //즉 만약 0을 반환했으면(yes를 누르면)? 삭제!
				String num = JOptionPane.showInputDialog(btnDel, "삭제할 행번호 num을 입력해주세요."); //다이얼로그 컴포넌트가 어디에 나오게 할거냐??btnDel위치에!! 즉, this라고 인자를 주면 프레임 어딘가에 나타남
				if(num==null) { //num을 입력하는 컴포넌트에서 엑스를 누르면 null이 반환된다. null이 나온다는 것은 내가 엑스를 눌렀다는 것. 
					return; //그러니까 창을 꺼버리려고 엑스를 누른거니까(null을 반환시킨거니까) 메서드를 종료(return)시키면 된다.
				}
				int delCode = dbmodel.deleteShop(Integer.parseInt(num));
				if(delCode==0) { //행이 아무것도 선택되지 않으면 0이 반환된다.
					JOptionPane.showMessageDialog(btnDel, "해당 num이 존재하지 않습니다."); 
				} else {
					JOptionPane.showMessageDialog(btnDel, "상품이 삭제되었습니다.");
				}
			}
		} else if(ob==btnUpdate){
			String num = JOptionPane.showInputDialog(btnUpdate, "수정할 행번호 num을 입력해주세요");
			if(num==null) { // 엑스 누르면 null반환
				return;
			} else {
				ShopDTO dto = dbmodel.getData(num);
				ShopUpdateForm updateForm = new ShopUpdateForm();
				//수정폼에 데이터 넣기
				updateForm.num = dto.getNum();
				updateForm.tfSang.setText(dto.getSangpum());
				updateForm.imageName = dto.getPhoto();
				updateForm.tfSu.setText(String.valueOf(dto.getSu()));
				updateForm.tfDan.setText(String.valueOf(dto.getDan()));
				updateForm.lblPhoto.setText(dto.getPhoto());
				
				//데이터 넣은 후 수정 폼 보이게 하기
				updateForm.setVisible(true);
			}
			
		} else if(ob==btnList){
			ShopListForm list = new ShopListForm();
		}
		
		
		
	}

}
