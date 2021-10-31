package course;

public class Gato extends Mamifero {
	private static final long serialVersionUID = 1L;

	public Gato(String nome, Integer idade, String dono) {
		super(nome, idade, dono);
	}

	@Override
	public String soar() {
		return "Miaaaou";
	}
}
