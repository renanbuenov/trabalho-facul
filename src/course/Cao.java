package course;

public class Cao extends Mamifero {
	private static final long serialVersionUID = 1L;

	public Cao(String nome, Integer idade, String dono) {
		super(nome, idade, dono);
	}

	@Override
	public String soar() {
		return "Aaau aau";
	}
}
