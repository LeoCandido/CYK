
import java.util.ArrayList;
import java.util.Scanner;

public class Cyk {

	//FUNCAO QUE FAZ AS COMBINACOES ENTRE O CONJUNTO DE VARIAVEIS
	public static ArrayList<String> combina(ArrayList<ArrayList<String>> cel,ArrayList<ArrayList<String>> cel2){
		ArrayList<String> r = new ArrayList();
		for(int i=0;i<cel.size();i++){
			for(int j=0;j<cel.get(i).size();j++){
				for(int k=0;k<cel2.get(i).size();k++){
					r.add(cel.get(i).get(j)+cel2.get(i).get(k));
				}
			}
		}
		return r;
	}

	/*FUNCAO QUE VALIDA SE A STRING DIGITADA SO POSSUI TERMINAIS DA GRAMATICA G
	CASO CONTRARIO REJEITA A STRING E ENCERRA A ANALISE*/
	public static boolean validapalavra(String palavra, Glc G){
		boolean retorno = true;
		if(palavra.length()>0)
			for(int i=0;i<palavra.length();i++){
				if(!G.terminais.contains(palavra.substring(i, i+1))){
					retorno = false;
					System.out.println("O simbolo '"+palavra.charAt(i)+"' nao esta definido na Gramatica.");
					break;
				}
			}
		else
			retorno = false;
		return retorno;   
	}

	/*FUNCAO QUE PROCESSA A STRING DIGITADA, IMPRIME A MATRIZ TRIANGULAR GERADA 
	E INFORMA SE FOI ACEITA PELA GRAMATICA.*/
	public static void processastr(String palavra,Glc g){
		ArrayList<String> M[][] = new ArrayList[palavra.length()+1][palavra.length()];
		// Para substrings de tamanho 1 - CASO BASE
		for(int i = 0;i<=palavra.length()-1;i++){
			M[palavra.length()][i]=new ArrayList();
			M[palavra.length()][i].add(palavra.substring(i,i+1));
			//envia simbolo para a funcao ckprod realizar busca da variavel que produz o simbolo
			M[palavra.length()-1][i]=g.ckprod(M[palavra.length()][i].get(0), g);

			/*Personalizando impressao de palavras de tamanho 1, para nao exibir apenas producao imediata
			ex. F--> B--> que gera " ( "*/
			if(palavra.length()==1){
				if(g.ckprod(M[0][0].get(0), g).contains(g.variaveis.get(0))){
					M[0][0].add(0,(g.variaveis.get(0)));
					M[0][0].add(1,("-->"));
				}
			}
		}

		ArrayList<ArrayList<String>> cel = new ArrayList();
		ArrayList<ArrayList<String>> cel2 = new ArrayList();
		ArrayList<String> cel3 = new ArrayList();

		/*Para substrings de tamanho 2 ate a palavra toda.
		se palavra >= 2 nao entra!*/
		for(int L=2;L<=palavra.length();L++){
			for(int j=0;j<=palavra.length()-L;j++){
				for(int i=0;i<L-1;i++){
					if(M[palavra.length()-L+i+1][j].size()==0)
						M[palavra.length()-L+i+1][j].add("");
					if(M[palavra.length()-L+i+1][i+j+1].size()==0)
						M[palavra.length()-L+i+1][i+j+1].add("");
					cel.add(M[palavra.length()-L+i+1][j]);

					/*inserindo na matriz cel2 de form invertida 
					para sempre comparar indices iguais ex. 1 com 1, 2 com 2 etc*/
					cel2.add(0,(M[palavra.length()-L+i+1][i+j+1]));
				}

				M[palavra.length()-L][j] = new ArrayList();

				/*Aqui: deve-se combinar o primeiro elemento de cel com o ultimo de cel2
				para verificar variaveis que produzem tais combinacoes*/
				for(int i=0;i<combina(cel,cel2).size();i++){
					cel3 = g.ckprod(combina(cel,cel2).get(i), g);
					for(int z=0;z<cel3.size();z++){

						//condicao para evitar que uma variavel seja adicionada 2 vezes
						if(!M[palavra.length()-L][j].contains(cel3.get(z)))
							//adicionando variavel que produz a combinacao
							M[palavra.length()-L][j].add(cel3.get(z));
					}
				}

				//limpa matrizes para gerar proximas combinacoes
				cel.clear();
				cel2.clear();
			}
		}

		//IMPRIME A MATRIZ TRIANGULAR GERADA.
		for(int i=0;i<palavra.length()+1;i++){
			System.out.println();
			for(int j=0;j<palavra.length();j++){
				if(M[i][j]!=null){
					System.out.print(M[i][j]);
					for(int e=1;e<=(10)-(2*M[i][j].size());e++){
						System.out.print(" ");
					}
				}
			}
		}

		/*TESTA SE O ARRAYLIST NA POSICAO [0][0] DA MATRIZ 
		POSSUI A VARIAVEL INICIAL. SE SIM, A PALAVRA FOI ACEITA.*/
		if(M[0][0].contains(g.variaveis.get(0)))
			System.out.println("\nPalavra ACEITA pela gramatica");
		else
			System.out.println("\nPalavra REJEITADA pela gramatica");
	}

	public static void main(String[] args) {    
		Scanner ler = new Scanner(System.in);
		String palavra; //Palavra para testar 

		Glc g = new Glc();
		System.out.println("GRAMATICA\n");    
		//IMPRIME AS PRODUCOES DA GRAMATICA.
		for(int i=0;i<g.variaveis.size();i++){
			System.out.println(g.variaveis.get(i)+" --> "+g.producoes.get(i));
		}

		System.out.println("\nDigite a palavra a ser validada: ");
		palavra = ler.nextLine();    

		if(validapalavra(palavra,g))
			processastr(palavra,g);
		else
			System.out.println("Palavra REJEITADA!");
	}
}
