package ufcg.si1.infoCarona.model.carona;

import java.util.LinkedList;
import java.util.List;

public class Expirada {
	
	private Carona carona;
	private String id;
	
	public Expirada(Carona carona, String id){
		this.carona = carona;
		this.id = id;
	}
	
	public Carona getCarona(){
		return this.carona;
	}
	
	public String getId(){
		return this.id;
	}
}
