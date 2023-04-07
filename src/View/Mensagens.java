
package View;

//cria uma forma de desviar os erros de exceção no controlador para mostrar na view.
//filho de exceptions.
public class Mensagens extends Exception{
    //cria um construtor 
    Mensagens(String message){
        //carrega a mensagem para o pai (exception)
        //permite que trate varios erros ao mesmo tempo.
        super(message);
    }
}
