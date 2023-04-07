
package Model;

import DAO.PacienteDAO;
import java.util.ArrayList;
import java.sql.SQLException;
//coloquei extends pois a classe Paciente é filho de Pessoa, ou seja, herda métodos e atributos do mesmo
public class Paciente extends Pessoa{
    private String endereco;
    private String data_nasc;
    //o valor é colocado uma única vez e ninguém pode modificar
    //final é encapsulamento -> mantém o atributo seguro de inteferencia externas 
    //chama-se um objeto de DAO
    private final PacienteDAO dao;
    
    public Paciente(){
        this.dao = new PacienteDAO();//é colocado em todos os construtores para chamar o DAO
    }
    public Paciente(String endereco, String data_nasc){
        this.endereco = endereco;
        this.data_nasc = data_nasc;
        this.dao = new PacienteDAO();
    }
    public Paciente (String endereco, String data_nasc, int id, String nome, String telefone){
        super(id, nome, telefone);
        this.endereco = endereco;
        this.data_nasc = data_nasc;
        this.dao = new PacienteDAO();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }
    //transforma os dados no array em um string em que se possa ler
    //faz-se o override no toString para que se posso puxar dados da classe pai tambem 
    //o override pega todos os dados de pai e filho
    //override é polimorfismo (instanciação de objetos também)
    @Override
    public String toString(){
        return "\n ID: " + this.getId()
        + "\n Nome: " + this.getNome()
        + "\n Telefone: " + this.getTelefone()
        + "\n Endereço: " + this.getEndereco()
        + "\n Data de Nascimento: " + this.getData_nasc();
    }
    // *MÉTODOS PARA USO JUNTO COM O BANCO DE DADOS* -> CRUD
    
    //READ
    //retorna a lista de Pacientes(objetos)
    //o objeto Paciente poderá manipular a lista inteira de pacientes lá onde ele estiver.
    //ele puxa a variável MinhaLista para fazer a leitura
    public ArrayList getMinhaLista(){
        return dao.getMinhaLista();
        //liga-se o model com o dao
        
    }
    //CREATE
    //Cadastra novo paciente
    //instancia-se um objeto vazio de Paciente, pega e da SET preenchendo os dados e depois q este objeto esta completo
    //eu chamo esta funcao InsertPacienteBD e manda oq acabei de preencher nos parenteses do mesmo
    public boolean InsertPacienteBD(Paciente paciente){
        dao.InsertPacienteBD(paciente);
        return true;
    }
    //DELETE
    //deleta um Paciente especifico pelo seu campo ID
    public boolean DeletePacienteBD(int id){
        dao.DeletePacienteBD(id);
        return true; 
    }
    //UPADTE
    //edita um Paciente especifico pelo seu campo ID
    //primeiro carrega-se o objeto de alguma maneira, fazer uma cópia dele, alterar oq precisa-se alterar(Paciente paciente)
    public boolean UpdatePacienteBD(Paciente paciente){
        //chama o DAO e substitui os dados
        dao.UpdatePacienteBD(paciente);
        return true;
    }
    //busca no DAO o maior ID no banco de dados para comunicar para control
    //essa função retorna o resultado de DAO
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
    public Paciente carregaPaciente(int id){
        dao.carregaPaciente(id);
        return null;
    }
    
}
