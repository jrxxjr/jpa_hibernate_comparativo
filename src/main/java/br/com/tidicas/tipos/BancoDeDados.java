package br.com.tidicas.tipos;

public enum BancoDeDados {
	MYSQL("jpamysql"), 
	POSTGRES("jpapostgres");
    
    private String valor;
    
    BancoDeDados(String valor){
        this.valor = valor;
    }
    
    public String getValor(){
        return valor;
    }

    public static BancoDeDados parse(String valor) {
    	BancoDeDados bancoDeDados = null; //default
        for (BancoDeDados item : BancoDeDados.values()) {
            if (item.getValor().equalsIgnoreCase(valor)) {
            	bancoDeDados = item;
                break;
            }
        }
        return bancoDeDados;
    }
}