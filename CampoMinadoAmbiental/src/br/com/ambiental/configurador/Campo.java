package br.com.ambiental.configurador;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	
	private final int linhas;
	private final int colunas;
	
	private boolean arvores = false;
	private boolean predio = false;
	private boolean prodes = false; // marcar que o campo tem uma mina, ja dectou, deixar visualmente marcado
	

	private List<Campo> proximos = new ArrayList<>();  //campo, esta criando a relação um para n, 
	private List<CampoAux1> auxiliares = new ArrayList<>();

	Campo(int linhas, int colunas) { 
		this.linhas = linhas;
		this.colunas = colunas;
		
	}
	
	public void registrarObservador(CampoAux1 auxiliar) {
		auxiliares.add(auxiliar);
	}
	
	private void informarAuxiliares(CampoAuxAcontecimento acontecimento) {
		auxiliares.stream()
			.forEach(o -> o.acontecimentoOcorreu(this, acontecimento));
	}
	boolean acrescentarLaterais(Campo proximo ) {
		boolean linhasDiferentes = linhas != proximo.linhas; //candidato de vizinho, o que fiz no caderno 	
		boolean colunasDiferentes = colunas != proximo.colunas;
		boolean diagonal = linhasDiferentes && colunasDiferentes;
		
		int deltaLinhas = Math.abs(linhas - proximo.linhas);
		int deltaColunas = Math.abs(colunas - proximo.colunas);
	
		int deltaGeral = deltaColunas + deltaLinhas;
		
		if (deltaGeral == 1 && !diagonal) {
			proximos.add(proximo);
			return true;
		} else if(deltaGeral == 2 && diagonal) {
			proximos.add(proximo);
			return true;
		} else {
			return false;
		}
	}

	public void alternarProdes() {
		if (!arvores) {
			prodes = !prodes;
			
			if(prodes) {
				informarAuxiliares(CampoAuxAcontecimento.MARCAR);
			}else {
				informarAuxiliares(CampoAuxAcontecimento.DESMARCAR);
			}
		}
	}
	
	public boolean abrir() {
		if (!arvores && !prodes) {
			if (predio) {
				informarAuxiliares(CampoAuxAcontecimento.EXPLODIR);
				return true;
				
					
			}
			
			setArvores(true);
			
			if(LateraisSeguros()) {
				proximos.forEach(v -> v.abrir());

			}
			return true;
		}else {
			return false;
		}
	
	}
	public boolean LateraisSeguros() {
		return proximos.stream().noneMatch(v-> v.predio);
	}
	void predio_os() {
		predio = true;
	}
	public boolean isPredio() {
		return predio;
	}
	public  boolean isProdes() {
		return prodes;	
	}
		
	public void setArvores(boolean arvore) {
		this.arvores = arvore;
		
		if(arvore) {
			informarAuxiliares(CampoAuxAcontecimento.ABRIR);
		}
	}

	public boolean isAberto() {
		return arvores;
	}
	public boolean isFechado() {
		return ! isAberto();
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !predio && arvores;
		boolean protegido = predio && prodes;
		return desvendado || protegido;
	}
	public int prediosProximos() {
		return(int) proximos.stream().filter(v -> v.predio).count();

	}
	void reiniciarJogo() {
		arvores = false;
		predio = false;
		prodes= false;
		informarAuxiliares(CampoAuxAcontecimento.REINICIAR);
	}
}


