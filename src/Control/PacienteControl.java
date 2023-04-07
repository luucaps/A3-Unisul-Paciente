
package Control;

import Model.Paciente;
import View.TabelaPacientes;
import java.sql.SQLException;
import java.util.ArrayList;
//o controlador vai fazer a mediação de entrada e saída, comanda a view e o model para que possa fazer as
//situações que o usuário solicitar.

public class PacienteControl {
    //cria-se um atributo da classe instaciada e é final pois não pode ser modificada - encapsulamento 
    //objeto de controle ligada ao modelo - objeto do tipo do model 
    //o control é um objeto de model para manipular o model
    private final Paciente control;
    //o construtor vazio da classe tem operação 
    //função de inicializar o atributo desta classe
    //quando a view chamar o PacienteControl, automaticamente ele vai instanciar um objeto de paciente
    public PacienteControl(){
        this.control = new Paciente();
        //control manipula todos os metodos de paciente
    }
    // AlunoControl receberá os dados da VIEW, deve criar um objeto Aluno e mandar este objeto devolta para Aluno para inserir em MinhaLista(DAO)
// CONTROL NÃO DEVE ACESSAR DAO DIRETAMENTE. Lá em Aluno deve existir um método que acessa DAO para inserir. InsertAlunoBD()
// Fluxo de dados entre as camadas: VIEW -> CONTROL -> MODEL -> DAO
    //TAIS OPERAÇÕES SÃO CRIADAS PARA QUE SEJAM IMPLEMENTADAS NA VIEW
    public boolean Cadastrar(String endereco, String data_nasc, String nome, String telefone) throws SQLException{
        //o paciente control pede para paciente model.
        //o ID que será cadastrado deve vir diretamente do DAO.
        int id = control.maiorID() + 1;
        System.out.println(id);
        //criando um objeto completo de Paciente com os dados vindos de um interface grafica qualquer.
        Paciente paciente = new Paciente(endereco, data_nasc, id, nome, telefone);
        //controlador (obejto de aluno) envia objeto completo p Paciente enviar para DAO.
        if(control.InsertPacienteBD(paciente)){
            return true;
        }else{
            return false;
        }
    }
    //o EDITAR recebe os dados e também o ID
    public boolean Editar (String endereco, String data_nasc, int id, String nome, String telefone){
        //cria-se um novo objeto e substitui o que ta lá por um novo
        Paciente paciente = new Paciente(endereco, data_nasc, id, nome, telefone);
        if(control.UpdatePacienteBD(paciente)){
            return true;
        }else{
            return false;
        }
    } 
    //o PacienteControl captou na interface(view)qual era o id e chama o model
    public boolean Apagar(int id){
        if(control.DeletePacienteBD(id)){
            return true;
        }else{
            return false;
        }
    }
    public Paciente LoadPaciente(int id){
       return control.carregaPaciente(id);
        
    }
    //serve para mostrar os dados com todos os objetos na view
    public ArrayList getMinhaLista(){
        return control.getMinhaLista();
    }
    //evita mensagens que a IDE manda q acha q eu devo melhorar
    @SuppressWarnings("unchecked")
    public String[][]getMatrizTabela(){
        //faz-se um arraylist de objetos de pacientes 
        ArrayList<Paciente>listaPacientes = control.getMinhaLista();
        //pega-se o tamanho da lista para se usar no critério de parada no FOR
        int tamanho = listaPacientes.size();
        //informa 5 colunas
        String TabelaPaciente[][] = new String[tamanho][5];
        //coloca-se "" em campos numericos para converter em texto
        for(int i = 0; i < tamanho; i++){
            TabelaPaciente[i][0] = listaPacientes.get(i).getId() + "";
            TabelaPaciente[i][1] = listaPacientes.get(i).getNome();
            TabelaPaciente[i][2] = listaPacientes.get(i).getTelefone();
            TabelaPaciente[i][3] = listaPacientes.get(i).getEndereco();
            TabelaPaciente[i][4] = listaPacientes.get(i).getData_nasc();
        }
        return TabelaPaciente;
    }
}
