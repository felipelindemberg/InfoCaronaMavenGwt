package ufcg.si1.infoCarona.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExceptionSerialized extends Exception implements IsSerializable {
	
	public ExceptionSerialized() {
	
	}
	public ExceptionSerialized(String erroMsg) {
		super(erroMsg);
	}
}
