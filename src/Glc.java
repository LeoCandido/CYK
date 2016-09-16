
import java.util.ArrayList;

public class Glc {

	ArrayList<String> variaveis;
	ArrayList<String> terminais;
	ArrayList<ArrayList<String>> producoes;

	//CONSTRUTOR PARA INSERIR AS VARIAVEIS, TERMINAIS E PRODUCOES NA GRAMATICA.
	public Glc(){
		this.terminais = new ArrayList();
		this.variaveis = new ArrayList();
		this.terminais.add("p");
		this.terminais.add("&");
		this.terminais.add("~");
		this.terminais.add(")");
		this.terminais.add("(");

		this.variaveis.add("F");
		this.variaveis.add("E");
		this.variaveis.add("G");
		this.variaveis.add("H");
		this.variaveis.add("B");
		this.variaveis.add("A");
		this.variaveis.add("C");
		this.variaveis.add("I");
		this.variaveis.add("D");

		ArrayList<ArrayList<String>> p = new ArrayList();
		this.producoes=p;

		ArrayList<String> p0 = new ArrayList();
		p0.add("BE");
		p0.add("BI");
		p0.add("p");
		this.producoes.add(p0);

		ArrayList<String> p1 = new ArrayList();
		p1.add("FG");
		this.producoes.add(p1);

		ArrayList<String> p2 = new ArrayList();
		p2.add("AH");
		this.producoes.add(p2);

		ArrayList<String> p3 = new ArrayList();
		p3.add("FC");
		this.producoes.add(p3);

		ArrayList<String> p4 = new ArrayList();
		p4.add("(");
		this.producoes.add(p4);

		ArrayList<String> p5 = new ArrayList();
		p5.add("&");
		this.producoes.add(p5);

		ArrayList<String> p6 = new ArrayList();
		p6.add(")");
		this.producoes.add(p6);

		ArrayList<String> p7 = new ArrayList();
		p7.add("DH");
		this.producoes.add(p7);

		ArrayList<String> p8 = new ArrayList();
		p8.add("~");
		this.producoes.add(p8);
	}

	//FUNCAO QUE VALIDA SE COMBINACAO DE VARIAVEIS PASSADA EH GERADA ATRAVES DE UMA PRODUCAO DA GRAMATICA.
	//SE FOR GERADA, A VARIAVEL GERADORA EH INSERIDA NO CONJUNTO DE RETORNO DA FUNCAO.
	ArrayList<String> ckprod(String s, Glc g){
		ArrayList<String> r = new ArrayList();

		for( int i=0;i<=g.producoes.size()-1;i++){
			if(g.producoes.get(i).contains(s))
				r.add(g.variaveis.get(i));
		}
		return r;
	}
}
