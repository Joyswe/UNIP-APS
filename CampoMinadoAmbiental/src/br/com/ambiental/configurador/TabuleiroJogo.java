package br.com.ambiental.configurador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TabuleiroJogo implements CampoAux1 {

	private final int linhas_as;
	private final int colunas_as;
	private final int predios_os ;

	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoAcontecimento>>auxiliares =
			new ArrayList<>();

	public TabuleiroJogo(int linhas_as, int colunas_as, int predios_os) {
		this.linhas_as = linhas_as;
		this.colunas_as = colunas_as;
		this.predios_os = predios_os;

		gerarCampos();
		associarLaterais();	
		sortearPredios_os();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
		
	}
	public void registrarAuxiliares(Consumer<ResultadoAcontecimento> auxiliar) {
		auxiliares.add(auxiliar);
	}
	public void informarAuxiliaress(boolean resultado ) {
		auxiliares.stream().forEach(o -> o.accept(new ResultadoAcontecimento()));

	}
	public void abrir(int linhas, int colunas) {
		campos.parallelStream()
			.filter(c -> c.getLinhas() == linhas && c.getColunas() == colunas)
			.findFirst()
			.ifPresent(c -> c.abrir());
	
	}
	

	public void alternarProdes(int linhas, int colunas) {
		campos.parallelStream()
			.filter(c -> c.getLinhas() == linhas && c.getColunas() == colunas)
			.findFirst()
			.ifPresent(c -> c.alternarProdes());
		
	}
 

	private void gerarCampos() {
		for (int linhas = 0; linhas < linhas_as; linhas++) {
			for (int colunas = 0; colunas < colunas_as; colunas++) {
				Campo campo = new Campo(linhas, colunas);
				campo. registrarObservador(this);
				campos.add(campo);

			}
		}
	}
	private void associarLaterais() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.acrescentarLaterais(c2);

			}
		}
	}

	private void sortearPredios_os() {
		long prediosArmadas = 0;
		Predicate<Campo> predio = c -> c.isPredio();

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).predio_os();		
			prediosArmadas = campos.stream().filter(predio).count();
		} while (prediosArmadas< predios_os );
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciarJogo() {
		campos.stream().forEach(c -> c.reiniciarJogo());
		sortearPredios_os();
	}
	
	
	
	public int getLinhas_as() {
		return linhas_as;
	}
	public int getColunas_as() {
		return colunas_as;
	}

	@Override
	public void acontecimentoOcorreu(Campo campo, CampoAuxAcontecimento acontecimento) {
		if(acontecimento == CampoAuxAcontecimento.EXPLODIR) {
			mostrarPredios();
			informarAuxiliaress(false);
		}else if(objetivoAlcancado()) {
			informarAuxiliaress(true);
		}
	}
		private void mostrarPredios() {
			campos.stream()
			.filter(c -> c.isPredio())
			.filter(c -> !c.isProdes())
			.forEach(c -> c.setArvores(true));
		
		
	}
}
