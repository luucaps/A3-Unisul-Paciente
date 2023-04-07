
package Model;

//coloquei abstract pois a classe Pessoa não pode ser instanciada, até pq tal classe serve somente para diminuir a necessidade de
//criar vários atributos com a mesma finalidade. Também coloquei abstract para que as classes filhas sejam obrigadas a implementar
//seus métodos.
//abstract é encapsulamento 
public abstract class Pessoa {
    private int id;
    private String nome;
    private String telefone;
    
    //método construtor é unicamente invocado no momento da criação do objeto através do operador new
    
    //o método construtor vazio só é colocado caso eu queira isntaciar um objeto dessa classe mas nao modificar
    //nada ou nao querer mostrar dados, ou até para usar em métodos abaixo
    public Pessoa(){
    }
    public Pessoa(int id, String nome, String telefone){
        //this. faz referencia ao id la de cima (de atributos)
        //toda vez que se usa this. é pq estou me referindo aos atributos da classe
        this.id = id;
        //esse this.id que se refere ao id de atributos ele vai receber o id do metodo construtor
        this.nome = nome;
        this.telefone = telefone;
    }
//métodos get e set pegam os dados privados la de cima (atributos) e retornam 
    //o get pega o atributo privado e o set seta o atributo (o atributo poderá ser modificado)
    
    // o get só retorna e nao tem argumento nenhum
    public int getId() {
        return id;
    }
// o set é vazio (nao retorna), so vai ativar o dado e sempre tem argumento
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
