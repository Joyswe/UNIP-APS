package br.com.ambiental.telas;

import javax.swing.JFrame;

import br.com.ambiental.configurador.TabuleiroJogo;

@SuppressWarnings("serial")
public class Principal extends JFrame   {

		public Principal() {
			TabuleiroJogo tabuleiro = new TabuleiroJogo(18, 35, 45);
			VisaoTabuleiro visaoTabuleiro = new VisaoTabuleiro(tabuleiro);
			// colocar se o jogo vai ser mais facil ou mais dificil
			
			add(visaoTabuleiro);
			
			setTitle("CAMPO MINADO AMBIENTAL");
			setSize(750, 488);
			setLocationRelativeTo(rootPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(true);

		}
		public static void main(String[] args) {
			new Principal (); 
		}
}
