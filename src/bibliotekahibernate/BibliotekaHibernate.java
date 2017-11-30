/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotekahibernate;
 
import java.sql.*;
import java.util.Scanner;


 
public class BibliotekaHibernate {
 
    static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_CONNECTION = "jdbc:oracle:thin:@oracle4.icis.pcz.pl:1521:oracle4";
 
    static final String DB_USER = "artsni";
    static final String DB_PASSWORD = "Artur55";
 
    public static void main(String[] argv) {
        // Local variable
       

        System.out.println("Wybierz co Cie interesi");
        System.out.println("-----------------------");
        System.out.println("0. Tabela Ksiazka");
        System.out.println("1. Tabela Osoba");
        System.out.println("2. Tabela Wydano");
        System.out.println("3. Dodaj ksiazke");
        System.out.println("4. Dodaj osobe");
        System.out.println("5. Wypozycz");
        System.out.println("6. Zwroc");
        System.out.println("7. Wypozyczone ksiazki z osobami");
        System.out.println("8. Wszystkie dostÄ™pne ksiÄ…zki");
        System.out.println("9. Ksiazki w bibliotece");
        System.out.println("10. Liczba wypozyczonych i posiadanych ksiazek");
		
        System.out.println("-----------------------");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
 
        switch (choice) {
            case 0:
                try {
                    getTable(choice);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 1:
                try {
                    getTable(choice);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                try {
                    getTable(choice);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                System.out.println("Podaj tytul");
                String tytul = scanner.next();
                System.out.println("Podaj autora");
                String autor = scanner.next();
                System.out.println("Podaj rok");
                String rok = scanner.next();
                System.out.println("Podaj liczbe stron");
                int lstron = scanner.nextInt();
                try {
                    addBook(tytul, autor, rok, lstron);
                    getTable(0);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                System.out.println("Podaj nazwisko");
                String nazwisko = scanner.next();
                System.out.println("Podaj imie");
                String imie = scanner.next();
                try {
                    addPerson(nazwisko, imie);
                    getTable(1);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            case 5:
                System.out.println("Podaj id ksiazki");
                int book = scanner.nextInt();
                System.out.println("Podaj id osoby");
                int person = scanner.nextInt();
                try {
                    borrowBook(book, person);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 6:
                break;
            case 7:
                try {
                    borrowedBooks();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 8:
                try{
                    showBooks();
                }
                catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            case 9:
                try{
                   availableBooks();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            case 10:
                try{
                   zestawienie();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                
            default:
                // The user input an unexpected choice.
        }
 
//        try {
//                addPerson();
//                addBook();
//                borrowBook();
//                returnBook();
//        } catch (SQLException e) {
//
//                System.out.println(e.getMessage());
//
//        }
 
    }
    
    private static void getTable(int id) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "";
 
        if(id==0){
            selectTableSQL = "SELECT * from Ksiazka";
        }
        if(id==1){
            selectTableSQL = "SELECT * from Osoba";
        }
        if(id==2){
            selectTableSQL = "SELECT * from Wydane";
        }
 
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
 
            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            
            while (rs.next()) {
 
                if(id == 0){
                    int idKsiazki = rs.getInt("id");
                    String tytul = rs.getString("tytul");
                    String autor = rs.getString("autor");
                    int rok = rs.getInt("rok");
                    int liczba_stron = rs.getInt("liczba_stron");
 
                    System.out.println("id ksiazki : " + idKsiazki);
                    System.out.println("Tytul : " + tytul);
                    System.out.println("Autor : " + autor);
                    System.out.println("Rok : " + rok);
                    System.out.println("Liczba stron : " + liczba_stron);
                }
                if(id == 1){
 
                    int idKsiazki = rs.getInt("id");
                    String tytul = rs.getString("nazwisko");
                    String autor = rs.getString("imie");
 
                    System.out.println("Id osoby : " + idKsiazki);
                    System.out.println("Nazwisko : " + tytul);
                    System.out.println("Imie : " + autor);
                }
                if(id == 2){
 
                    int idWydano = rs.getInt("id");
                    int idKsiazki = rs.getInt("id_ksiazki");
                    int idOsoby = rs.getInt("id_osoby");
                    Date wydano = rs.getDate("wydano");
                    Date zwrot = rs.getDate("zwrot");
 
                    System.out.println("Id wydania : " + idWydano);
                    System.out.println("Id ksiazki : " + idKsiazki);
                    System.out.println("Id osoby : " + idOsoby);
                    System.out.println("Wydano : " + wydano);
                    System.out.println("Zwrot : " + zwrot);
                }
            }
 
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        } finally {
 
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
 
    
    private static void addPerson(String nazwisko, String imie) throws SQLException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;
 
        String dodajOsobe = "{call dodajOsobe(?,?,?)}";
        
        int id = 0;
        
        try{
            id = getId("SELECT max(id) from osoba ");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
 
        try {
            dbConnection = getDBConnection();
            callableStatement = dbConnection.prepareCall(dodajOsobe);
 
            callableStatement.setInt(1, id);
            callableStatement.setString(2, nazwisko);
            callableStatement.setString(3, imie);
            callableStatement.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
 
        } finally {
            if (callableStatement != null) {
                    callableStatement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    private static void addBook(String title, String autor, String rok, int lstron) throws SQLException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;
 
        String dodajOsobe = "{call dodajksiazke(?,?,?,?,?)}";
        
        
        int id = 0;
        
        try{
            id = getId("SELECT max(id) from Ksiazka ");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        try {
            dbConnection = getDBConnection();
            callableStatement = dbConnection.prepareCall(dodajOsobe);
 
            callableStatement.setInt(1, id);
            callableStatement.setString(2, title);
            callableStatement.setString(3, autor);
            callableStatement.setString(4, rok);
            callableStatement.setInt(5, lstron);
            
            callableStatement.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
 
        } finally {
            if (callableStatement != null) {
                    callableStatement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    private static void borrowBook(int book, int person) throws SQLException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;
 
        String dodajOsobe = "{call pozycz(?,?,?)}";
        
        int id = 0;
        
        try{
            id = getId("SELECT max(id) from Wydane ");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        try {
            dbConnection = getDBConnection();
            callableStatement = dbConnection.prepareCall(dodajOsobe);
 
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, book);
            callableStatement.setInt(3, person);
            
            callableStatement.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
 
        } finally {
            if (callableStatement != null) {
                    callableStatement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    private static void returnBook() throws SQLException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;
 
        String dodajOsobe = "{call zwrot(?,?,?)}";
 
        try {
            dbConnection = getDBConnection();
            callableStatement = dbConnection.prepareCall(dodajOsobe);
 
            callableStatement.setInt(1, 1);
            callableStatement.setInt(2, 1);
            callableStatement.setInt(3, 1);
            
            callableStatement.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
 
        } finally {
            if (callableStatement != null) {
                    callableStatement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    private static void borrowedBooks() throws SQLException {
        String selectTableSQL = "select * from wydane w join ksiazka k on (w.id_ksiazki = k.id) join osoba o on (w.id_osoby = o.id)";
        Statement statement = null;
        Connection dbConnection = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
 
            while (rs.next()) {
                int idWydano = rs.getInt("id");
                int idKsiazki = rs.getInt("id_ksiazki");
                int idOsoby = rs.getInt("id_osoby");
                Date wydano = rs.getDate("wydano");
                Date zwrot = rs.getDate("zwrot");
                
                String nazwisko = rs.getString("nazwisko");
                String imie = rs.getString("imie");
                
                String tytul = rs.getString("tytul");
                String autor = rs.getString("autor");
                int rok = rs.getInt("rok");
                int liczba_stron = rs.getInt("liczba_stron");
                
                System.out.println("Id wydania : " + idWydano);
                System.out.println("Id ksiazki : " + idKsiazki);
                System.out.println("Tytul : " + tytul);
                System.out.println("Autor : " + autor);
                System.out.println("Rok : " + rok);
                System.out.println("Liczba stron : " + liczba_stron);
                System.out.println("Id osoby : " + idOsoby);
                System.out.println("Nazwisko : " + nazwisko);
                System.out.println("Imie : " + imie);
                System.out.println("Wydano : " + wydano);
                System.out.println("Zwrot : " + zwrot);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    private static void showBooks() throws SQLException {
        String selectTableSQL = "select * from wydane w join ksiazka k on (w.id_ksiazki = k.id) join osoba o on (w.id_osoby = o.id) where w.wydano is null";
        Statement statement = null;
        Connection dbConnection = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
 
            while (rs.next()) {
                int idWydano = rs.getInt("id");
                int idKsiazki = rs.getInt("id_ksiazki");
                int idOsoby = rs.getInt("id_osoby");
                Date wydano = rs.getDate("wydano");
                Date zwrot = rs.getDate("zwrot");
                
                String nazwisko = rs.getString("nazwisko");
                String imie = rs.getString("imie");
                
                String tytul = rs.getString("tytul");
                String autor = rs.getString("autor");
                int rok = rs.getInt("rok");
                int liczba_stron = rs.getInt("liczba_stron");
                
                System.out.println("Id wydania : " + idWydano);
                System.out.println("Id ksiazki : " + idKsiazki);
                System.out.println("Tytul : " + tytul);
                System.out.println("Autor : " + autor);
                System.out.println("Rok : " + rok);
                System.out.println("Liczba stron : " + liczba_stron);
                System.out.println("Id osoby : " + idOsoby);
                System.out.println("Nazwisko : " + nazwisko);
                System.out.println("Imie : " + imie);
                System.out.println("Wydano : " + wydano);
                System.out.println("Zwrot : " + zwrot);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
    
    private static int getId(String select) throws SQLException {
        int id = 0;
        String selectTableSQL = select;
        Statement statement = null;
        Connection dbConnection = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
 
            while (rs.next()) {
                id = rs.getInt("max(id)");
                id += 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
        return id;
    } 
 
    private static Connection getDBConnection() {
        Connection dbConnection = null;
 
        try {
            Class.forName(DB_DRIVER);
 
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
 
        }
 
        try {
 
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER,DB_PASSWORD);
            return dbConnection;
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
 
    
    private static void availableBooks() throws SQLException
    {
        String selectTableSQL = "select * from wydane w join ksiazka k on (w.id_ksiazki = k.id) where w.zwrot is not null";
        Statement statement = null;
        Connection dbConnection = null;
        try 
        {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            while (rs.next()) 
            {
               
                int idKsiazki = rs.getInt("id_ksiazki");
                String tytul = rs.getString("tytul");
                String autor = rs.getString("autor");
                int rok = rs.getInt("rok");
                int liczba_stron = rs.getInt("liczba_stron");
                
                
                System.out.println("Id ksiazki : " + idKsiazki);
                System.out.println("Tytul : " + tytul);
                System.out.println("Autor : " + autor);
                System.out.println("Rok : " + rok);
                System.out.println("Liczba stron : " + liczba_stron);
            
            }
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } 
        finally {
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
    
     private static void zestawienie() throws SQLException
    {
        String selectTableSQL="select imie,nazwisko, (select count(id_ksiazki) from wydane w where w.id_osoby=o.id) as wypozyczone, (select count(zwrot) from wydane w where w.id_osoby=o.id) as posiadane from osoba o";
        Statement statement = null;
        Connection dbConnection = null;
        try 
        {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            while (rs.next()) 
            {
                String nazwisko = rs.getString("nazwisko");
                String imie = rs.getString("imie");
                int wyp=rs.getInt("wypozyczone");
                int pos=rs.getInt("posiadane");
                System.out.println("Imie : " + imie);
                System.out.println("Nazwisko : " + nazwisko);
                System.out.println("Liczba wypozyczonych ksiazek : " + wyp);
                System.out.println("Liczba posiadanych ksiazek : " + pos);
            }
               
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } 
        finally {
            if (statement != null) {
                    statement.close();
            }
 
            if (dbConnection != null) {
                    dbConnection.close();
            }
        }
    }
}