package course;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PetStore {
	
	private ArrayList<Mamifero> mamiferos;

	public PetStore() {
		this.mamiferos = new ArrayList<Mamifero>();
	}
	
	// Esse método irá ler os valores que forem inseridos na caixa de entrada.
	public String[] leValores (String [] dadosIn) {
		String [] dadosOut = new String [dadosIn.length];
		
		for(int i=0; i<dadosIn.length; i++) {
			dadosOut[i] = JOptionPane.showInputDialog("Entre com "+ dadosIn[i] +": ");
		}
		return dadosOut;
	}
	
	// Método que irá imprimir a variável na frente da mensagem de input, no método acima.
	public Gato leGato() {
		String [] valores = new String[3];
		String [] nomeValores = {"Nome", "Idade", "Dono"};
		valores = leValores(nomeValores);
		
		int idade = this.retornaInteiro(valores[1]);
		
		Gato gato = new Gato(valores[0], idade, valores[2]);
		return gato;
	}
	
	public Cao leCao() {
		String [] valores = new String[3];
		String [] nomeValores = {"Nome", "Idade", "Dono"};
		valores = leValores(nomeValores);
		
		int idade = this.retornaInteiro(valores[1]);
		
		Cao gato = new Cao(valores[0], idade, valores[2]);
		return gato;
	}
	
	public int retornaInteiro(String entrada) {
		while(!this.intValido(entrada)) {
			entrada = JOptionPane.showInputDialog("Valor incorreto!\n\nDigite um número inteiro.");
		}
		return Integer.parseInt(entrada);
		
	}
	
    private boolean intValido(String s) {
        try {
            Integer.parseInt(s); // Tenta transformar uma string em inteiro
            return true;
        } catch (NumberFormatException e) { // Se não consegue transformar, erro
            return false;
        }
    }
    
    public void salvaMamiferos(ArrayList<Mamifero> mamiferos) {
    	ObjectOutputStream out = null;
    	try {
			out = new ObjectOutputStream(new FileOutputStream("c:\\temp\\pet.dados"));
			
			for (int i=0; i<this.mamiferos.size(); i++) { // Aqui nós vamos percorrer a ArrayList para index, começando no 0.
				out.writeObject(mamiferos.get(i)); // E em seguida escrever cada objeto mamífero, pegando os index da lista como paramêtro.
			} 
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Não foi salvar o arquivo!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    @SuppressWarnings("finally")
    public ArrayList<Mamifero> recuperaMamiferos(){
    	ArrayList<Mamifero> mamiferosTemp = new ArrayList<Mamifero>();
    	
    	ObjectInputStream in = null;
    	
    	try {
    		in = new ObjectInputStream(new FileInputStream("c:\\temp\\pet.dados"));
    		Object obj = null;
    		while ((obj = in.readObject()) != null) {
    			if (obj instanceof Mamifero) {
    				mamiferosTemp.add((Mamifero) obj);
    			}
    		}
    	} catch (EOFException e) {
    		System.out.println("Fim de arquivo");
    	} catch (ClassNotFoundException ex) {
    		ex.printStackTrace();
    	} catch (FileNotFoundException ex) {
    		JOptionPane.showMessageDialog(null, "Arquivo Mamíferos não existe!");
    		ex.printStackTrace();
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		try {
    			if (in != null) {
    				in.close();
    			}
    		} catch (final IOException ex) {
    			ex.printStackTrace();
    		}
    		return mamiferosTemp;
    	}
    }

	public void menuPetStore() {
		String menu = "";
        String entrada;
        int    opc1, opc2;
        do {
            menu = "Controle PetStore\n" +
                    "Opções:\n" + 
                    "1. Entrar Mamíferos\n" +
                    "2. Exibir Mamíferos\n" +
                    "3. Limpar Mamíferos\n" +
                    "4. Gravar Mamíferos\n" +
                    "5. Recuperar Mamíferos\n" +
                    "9. Sair";
            entrada = JOptionPane.showInputDialog (menu + "\n\n");
            opc1 = this.retornaInteiro(entrada);
            
            switch(opc1) {
            case 1: 
            	menu = "Entrada de animais mamíferos\n"+
            		   "Opções: \n"+
            		   "1. Cão\n"+
            		   "2. Gato\n";
            	entrada = JOptionPane.showInputDialog(menu + "\n\n");
                opc2 = this.retornaInteiro(entrada);
                
                switch(opc2) {
                case 1: mamiferos.add(leCao());
                break;
                case 2: mamiferos.add(leGato());
                break;	
                default:
                	JOptionPane.showMessageDialog(null, "Entrada Não Válida!");
                }
                break;
                
            case 2:
            	if (mamiferos.size() == 0) {
            		JOptionPane.showMessageDialog(null, "Entre com mamíferos");
            		break;
            	}
            	String dados = "";
            	for(int i=0; i<mamiferos.size(); i++) {
            		dados += mamiferos.get(i).toString() + "---------------\n";
            	}
            	JOptionPane.showMessageDialog(null, dados);
            	break;
            case 3:
            	if(mamiferos.size() == 0) {
            		JOptionPane.showMessageDialog(null, "Entre com mamíferos");
            		break;
            	}
            	mamiferos.clear();
            	JOptionPane.showConfirmDialog(null, "Dados excluídos com sucesso");
            	break;
            case 4:
            	if(mamiferos.size() == 0) {
            		JOptionPane.showMessageDialog(null, "Entre com mamíferos");
            		break;
            	}
            	salvaMamiferos(mamiferos);
            	JOptionPane.showMessageDialog(null, "Dados salvos com sucesso");
            	break;
            case 5:
            	mamiferos = recuperaMamiferos();
            	if(mamiferos.size() == 0) {
            		JOptionPane.showMessageDialog(null, "Sem dados para apresentar");
            		break;
            	}
            	JOptionPane.showMessageDialog(null, "Dados lidos com sucesso");
            	break;
            case 9:
            	JOptionPane.showConfirmDialog(null, "Fim do aplicativo PETSTORE");
            	break;
            }
        } while (opc1 != 9);	
	} 
 }


