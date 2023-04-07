
package DAO;

import Model.Paciente;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class PacienteDAO {
    //é um método que pertence a classe e não ao objeto.
    //o metodo static faz com que possa ser acessdo de fora da classe sem a necessidade de instanciar objeto.
    //é uma base de dados que é uma variavel do tipo arraylist(ou seja, quando desligar o software, perde-se tudo pois nao esta conectado ao
    //banco de dados
    public static ArrayList<Paciente>MinhaLista = new ArrayList<Paciente>();
    //o arraylist aqui colocado serve para fazer uma lista de objetos da classe Pacientes
    //o arraylist nao precisa definir o tamanho (espaço de memoria)
    //o arraylist so precisa de nome (MinhaLista)
    public PacienteDAO(){      
    }
    //o método MAIORID serve para gerenciar os IDs (gerando um id automaticamente)
    //primeiro busca-se do banco de dados qual é o maior ID para nao repetir e assim crie-se outro no cadastro(dessa vez esse é maior)
    public int maiorID() throws SQLException {
        int maiorID = 0;
        try{
            Statement stmt = this.getConexao().createStatement();
            //resultset volta os dados
            ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM tb_pacientes");
            res.next();
            maiorID = res.getInt("id");
            
            stmt.close();
        }catch(SQLException ec){
            
        }
        return maiorID;
    
}
    //connection faz a conexao do software com a base de dados
    public Connection getConexao(){
        Connection conexao = null; //instancia a conexao (cria a variavel da classe connection, sendo nula e com o tempo preenche)
        try{
            //carregamento do JBC driver - chama o endereço
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);//carrega a classe-arquivo
            
            //configura a conexao
            String server = "localhost";//caminho do MYSQL
            String database = "db_pacientes";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "Ana2704Luiza.";
            //o drivermanager chama o getconnection da classe connection
            conexao = DriverManager.getConnection(url, user, password);
            
            //teste de conexao com a base de dados
            if(conexao != null){
                System.out.println("Status: Conectado!");
            }else{
                System.out.println("Status: Não conectado!");
            }
            return conexao;
            
        }catch (ClassNotFoundException e){ //driver nao encontrado (esse ero aparece quand nao há instalação do driver)
            System.out.println("O driver não foi encontrado!" + e.getMessage());
            return null;
        }catch (SQLException e){ //comandos errados na hora da execução
            System.out.println("Não foi possível conectar.");
            return null;
        }
    }
    //o STATEMENT é uma implementação da interface que fornece metodos para a execução de uma instrução SQL -> ENVIA COMANDOS
    //o RESULTSET fornece uma série de métodos getters, em que puxa dados do banco de dados para a programação -> RECEBE COMANDOS
    
    //retorna a lista de Pacientes(objetos)
    public ArrayList getMinhaLista(){
        MinhaLista.clear();//limpa o arraylist (atualiza a tabela)
        try{
            //cria-se o statement e faz a função de conexão e cria o statement
            Statement stmt = this.getConexao().createStatement();
            //pega os dados da variavel satetement
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_pacientes");
            //loop de registros que chegaram. (loop do tamanho exato da tabela.
            //o next da o valor de true.
            while (res.next()){
                //coleta os dados da tabela com o GET.
                //dentro do () é o nome dos campos na base de dados.
                String endereco = res.getString("endereco");
                String data_nasc = res.getString("data_nasc");
                int id = res.getInt("id");
                String nome = res.getString("nome");
                String telefone = res.getString("telefone");
                //cria o objeto com construtor completo e adiciona no arraylist (minha lista) 
                Paciente paciente = new Paciente (endereco, data_nasc, id, nome, telefone);
                //insere todos os pacientes do banco de dados no arraylist
                MinhaLista.add(paciente);
            }
            stmt.close();//close para nao sobrecarregar o banco de dados com loop
        }catch(SQLException ex){
            
        }
        return MinhaLista;
    }
    // o PREPAREDSTATEMENT serve para colocar uma quantidade grande de parametros 
    //cadastra novo aluno
    public boolean InsertPacienteBD(Paciente obj){
        //escreve-se o comando SQL.
        //o preparedStatement seta cada um dos campos em "?" com seus tipos especificos.
        String sql = "INSERT INTO tb_pacientes(id, nome, telefone, endereco, data_nasc) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);
            //pega o comando SQL e seta os pontos de interrogação
            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getNome());
            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getEndereco());
            stmt.setString(5, obj.getData_nasc());
            
            stmt.execute();
            stmt.close();
            return true;
        }catch(SQLException erro){
            throw new RuntimeException(erro);
        }
    }
    public boolean DeletePacienteBD(int id){
        try{
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_pacientes WHERE id = " + id);
            stmt.close();
            
        }catch(SQLException erro){
            
        }
        return true;
    }
    //edita o paciente pelo seu campo ID
    public boolean UpdatePacienteBD(Paciente obj){
        
       String sql = "UPDATE tb_pacientes set nome = ?, telefone = ?, endereco = ?, data_nasc = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getTelefone());
            stmt.setString(3, obj.getEndereco());
            stmt.setString(4, obj.getData_nasc());
            stmt.setInt(5, obj.getId());
            stmt.execute();
            stmt.close();
            return true;
        }catch (SQLException erro){
            throw new RuntimeException(erro);
        }
    }
    public Paciente carregaPaciente (int id){
        Paciente paci = new Paciente(); //cria um objeto de paciente.
        paci.setId(id);
        try{
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * id FROM tb_pacientes WHERE id = " + id);
            res.next();
            
            paci.setNome(res.getString("nome"));
            paci.setTelefone(res.getString("telefone"));
            paci.setEndereco(res.getString("endereco"));
            paci.setData_nasc(res.getString("data_nasc"));
            
            stmt.close();
        }catch(SQLException erro){
            
        }return paci;
    }
}
