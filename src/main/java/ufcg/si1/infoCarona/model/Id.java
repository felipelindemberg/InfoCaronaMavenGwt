package ufcg.si1.infoCarona.model;

/**
 * Classe singleton que gera Id's para todas as opções do sistema 
 * @author Italo Tavares
 * @author Felipe Lindemberg
 * @author Gilles Medeiros
 * @author Wallison Tavares
 *
 */
public class Id {
	
	private int numeroDeDigitos;
	private int cont = 0;
	private String ultimoId;
	public static Id instance;
	
	
	protected Id(int numeroDeDigitos){
		  this.numeroDeDigitos = numeroDeDigitos;
	}
	/**
	 * Construtor singleton da classe
	 * @param numeroDeDigitos - Recebe um int numeroDeDigitos que é o numero de digitos que um Id pode ter.
	 * @return - Retorna uma instancia da classe que gera ID
	 */
	public static Id getInstance(int numeroDeDigitos){
		if (instance == null){
			instance = new Id(numeroDeDigitos);
		}
		return instance;
	}
	
	/**
	 * Metodo que gera um Id
	 * @return - Retorna uma String com o id gerado
	 * @throws NumeroMaximoException - Caso seja atingido o numero maximo de id's é lançada a exceção.
	 */
	public String gerarId() throws NumeroMaximoException{
		String retorno = "";
		String stringCont = cont+"";
		cont++;
		
		if (stringCont.length() > numeroDeDigitos){
			throw new NumeroMaximoException();
		}
		
		for (int i = stringCont.length(); i < numeroDeDigitos; i++) {
			retorno += 0;
		}
		
		ultimoId = retorno+stringCont;
		
		return (ultimoId);
	}
	
	/**
	 * Metodo que retorna o numero de digitos para a geracao de id
	 * @return - Retorna um int com o numero de digitos para a geracao de id
	 */
	public int getNumeroDeDigitos(){
		return numeroDeDigitos;
	}
	
	/**
	 * Metoda que retorna o ultimo id gerado
	 * @return - retorna uma String com o ultimo id gerado
	 */
	public String getUltimoId(){
		return ultimoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cont;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Id)) {
			return false;
		}
		
		if (!(((Id) obj).getUltimoId().equals(this.ultimoId))){
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString(){
		String retorno = "O n�mero de digitos � " + this.numeroDeDigitos + " e o ultimo ID gerado foi " + this.ultimoId;
		return  retorno;
	}
}
