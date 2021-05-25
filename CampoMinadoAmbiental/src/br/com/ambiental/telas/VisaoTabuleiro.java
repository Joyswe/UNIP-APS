package br.com.ambiental.telas;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.ambiental.configurador.TabuleiroJogo;

@SuppressWarnings("serial")
public class VisaoTabuleiro extends JPanel {
	public VisaoTabuleiro(TabuleiroJogo tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas_as(), tabuleiro.getColunas_as()));
		
		tabuleiro.paraCadaCampo(c -> add(new Botoes(c)));
		tabuleiro.registrarAuxiliares(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "PARABÉNS VOCÊ GANHOU!!!  :-)");
				} else {
					JOptionPane.showMessageDialog(this, "VIX... VOCÊ PERDEU,TENTE DENOVO  :-(");
				}
				
				
				tabuleiro.reiniciarJogo();
			});		
		});
	
	}
}