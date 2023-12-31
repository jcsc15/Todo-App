    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Julio
 */
public class TaskController {
    
        Connection connection =  null;
        PreparedStatement statement = null;
    
    public void save (Task task){
        String sql = "INSERT INTO tasks (idProject, name, description, completed, notes, deadline, createdAt, updatedAt) Values (?, ?, ?, ?, ?, ?, ?, ?)";
       
        
        
        try {
            connection = ConnectionFactory.getConnection();
            statement =  connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6,new Date (task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" , ex);
        } finally{
                ConnectionFactory.closeConnection(connection, statement);
   
        }
    }
    
    public void update (Task task){
        
        String sql =  "UPDATE tasks SET idProject = ?, name = ?, description = ?, completed  = ?, notes = ?, deadline = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
              
        
         
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando o query
            statement =  connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6,new Date (task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9,task.getId());
            
            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
             throw new RuntimeException("Erro ao atualizar a tarefa" , ex);
        }
    }
    
    public void removeById (int taskId) {
        
        String sql = "DELETE FROM  tasks WHERE id = ?";
        
        
        try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, taskId);
                statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" , ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll (int IdProject){
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        
        ResultSet resultSet =  null;
        
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Setando o valor que corresponde ao filro de busca
            statement.setInt(1, IdProject);
            
            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();
            
            
            //Enquanto houverem valores a serem percorridos no meu resultSet
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("IdProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setisIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
                
            }    
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" , ex);
        }
        finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        
        return tasks;
    }
}
