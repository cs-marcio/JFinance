package br.com.setoronline.JFinance.v1_0_0.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.setoronline.JFinance.v1_0_0.DAO.ConnectionManager;

public class FrameLogin extends JFrame {
	
	private String senha; // Receberá Senha
	JLabel lb_login = new JLabel("Login: ");
	JTextField tf_login = new JTextField(30);
	JLabel lb_rotuloLogin = new JLabel("Email inválido!");
	JLabel lb_senha = new JLabel("Senha: ");
	private JPasswordField tf_senha = new JPasswordField("0123456789");
	JButton bt_login = new JButton("Login");
	JLabel lb_rotuloSenha = new JLabel("PassWord");
	
	private Connection conn = null;

	public FrameLogin() {
		super("Login Sistema");
		
		try {
			conectar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frameLogin();
	}

	private void conectar() throws Exception {
		conn = new ConnectionManager().getConn();		
	}

	public void frameLogin() {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);

		lb_login.setBackground(Color.LIGHT_GRAY);
		lb_login.setVerticalAlignment(SwingConstants.TOP);
		lb_rotuloLogin.setVisible(false);
		lb_rotuloLogin.setForeground(Color.RED);

		lb_login.setBounds(5, 5, 50, 15);
		tf_login.setBounds(5, 25, 200, 15);
		lb_senha.setBounds(5, 45, 54, 15);
		lb_rotuloLogin.setBounds(210, 25, 200, 15);
		tf_senha.setBounds(5, 65, 74, 19);
		bt_login.setBounds(5, 90, 73, 20);
		lb_rotuloSenha.setBounds(97, 67, 101, 15);

		getContentPane().add(lb_login);
		getContentPane().add(tf_login);
		tf_login.setFocusable(true);
		getContentPane().add(lb_rotuloLogin);
		getContentPane().add(lb_senha);
		getContentPane().add(tf_senha);
		getContentPane().add(bt_login);
		getContentPane().add(lb_rotuloSenha);

		lb_rotuloSenha.setForeground(Color.RED);

		tf_senha.addFocusListener(tf_senhaFocusListener);

		tf_login.addFocusListener(tf_loginFocusListener);

		bt_login.addActionListener(bt_loginAtionListener);

	}

	// Handler para o Botão de Login
	ActionListener bt_loginAtionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bt_login) {
				try {
					String sql = "SELECT * FROM JF_U001CAD WHERE CAD_PESMAI = '"
							+ tf_login.getText()
							+ "' AND CAD_CODPAS = '"
							+ senha + "'";
					Statement st = conn.createStatement();
					ResultSet rt = st.executeQuery(sql);

					if (rt != null) {
						while (rt.next()) {
							String loginRemoto = rt.getString("CAD_PESMAI");
							String senhaRemoto = rt.getString("CAD_CODPAS");
							if (loginRemoto.equals(tf_login.getText())
									&& senhaRemoto.equals(senha)) {

								lb_rotuloLogin
										.setText("Login efetuado com sucesso!");
								lb_rotuloLogin.setForeground(Color.BLUE);
								lb_rotuloLogin.setVisible(true);

								/*TelaNovoLancamento telaNovoLancamento = new TelaNovoLancamento();
								telaNovoLancamento
										.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								telaNovoLancamento.setSize(600, 600);
								telaNovoLancamento.setVisible(true);*/

							} else {
								lb_rotuloLogin
										.setText("Login ou senha incorretos!");
								lb_rotuloLogin.setVisible(true);
							}
						}
					} else {
						lb_rotuloLogin.setText("Login e senha incorretos!");
						lb_rotuloLogin.setVisible(true);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
	};

	// Ação quando o Text Field de login quando recebe/perde foco
	FocusListener tf_loginFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if ((tf_login.getText().contains("@"))
					&& (tf_login.getText().contains("."))
					&& (!tf_login.getText().contains(" "))) {

				String usuario = new String(tf_login.getText().substring(0,
						tf_login.getText().lastIndexOf('@')));

				String dominio = new String(tf_login.getText().substring(
						tf_login.getText().lastIndexOf('@') + 1,
						tf_login.getText().length()));

				if ((usuario.length() >= 1) && (!usuario.contains("@"))
						&& (dominio.contains(".")) && (!dominio.contains("@"))
						&& (dominio.indexOf(".") >= 1)
						&& (dominio.lastIndexOf(".") < dominio.length() - 1)) {

					lb_rotuloLogin.setVisible(false);
					tf_senha.setText("");

				} else {

					lb_rotuloLogin.setVisible(true);
					tf_login.requestFocus();

				}

			} else {
				lb_rotuloLogin.setVisible(true);
				tf_login.requestFocus();
			}
		}

		@Override
		public void focusGained(FocusEvent e) {

		}
	};

	// Ação quando o Text Field de senha quando recebe/perde foco
	FocusListener tf_senhaFocusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent arg0) {
			senha = new String(tf_senha.getPassword());
			lb_rotuloSenha.setText(senha);

		}

		@Override
		public void focusGained(FocusEvent arg0) {
			tf_senha.setText("");

		}
	};

}
