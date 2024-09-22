package sugangSincheong;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants.EGreetingPanel;
import valueObject.VUser;

public class PHeaderPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel wecomeLabel;
	
	public PHeaderPanel() {
		this.wecomeLabel = new JLabel();
		this.add(this.wecomeLabel);		
	}
	public void intialize(VUser vUser) {
		this.wecomeLabel.setText(vUser.getName()+EGreetingPanel.greetings.getText());		
	}
}
