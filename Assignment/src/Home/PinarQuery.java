package Home;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class PinarQuery {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String s2 = "PREFIX wd: <http://www.wikidata.org/entity/> "+
	        	  "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "+
	    		  " PREFIX wikibase: <http://wikiba.se/ontology#>"+
	    		  " PREFIX p: <http://www.wikidata.org/prop/>" +
	    		  " PREFIX ps: <http://www.wikidata.org/prop/statement/>"+
	    		  "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>"+
	    		  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
	    		  "PREFIX bd: <http://www.bigdata.com/rdf#>"+
	        "select ?item ?itemLabel "+
	        "where { "+
	         "?item wdt:P31 wd:Q146 .  "+
	         "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }"+
	        "} \n ";

      Query query = QueryFactory.create(s2); //s2 = the query above
      QueryExecution qExe = QueryExecutionFactory.sparqlService( "https://query.wikidata.org/sparql", query );
      org.apache.jena.query.ResultSet results = qExe.execSelect();
      Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db1", "root", "penguen");
      
      //ResultSetFormatter.out(System.out, results, query) ;
      for ( ; results.hasNext() ; )
      {
        QuerySolution soln = results.nextSolution() ;
        RDFNode x = soln.get("itemLabel") ;       // Get a result variable by name.
        RDFNode y = soln.get("item") ;
        
        String sql = "insert into cats values(\""+x.toString()+"\", \""+y.toString()+"\" )";
        System.out.println(sql);
        java.sql.PreparedStatement smt=conn.prepareStatement(sql);
        smt.executeUpdate(); 
       
      } 
      
	}

}
