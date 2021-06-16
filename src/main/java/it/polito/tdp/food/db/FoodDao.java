package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public void listAllPortions(Map<Integer, Portion> idMap){
		String sql = "SELECT * FROM `portion`" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getInt("portion_id"))) {
				try {
					Portion p =new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							);
					idMap.put(p.getPortion_id(), p);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
			
			conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
	
		}

	}
	
	
	public List<Portion> getVertex(Map<Integer, Portion> idMap, int c) {
		String sql = "SELECT DISTINCT * "
				+ "FROM  `portion` "
				+ "WHERE calories < ? "
				+ "GROUP BY portion_display_name ";
		List<Portion> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, c);
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(idMap.containsKey(res.getInt("portion_id"))) {
					list.add(idMap.get(res.getInt("portion_id")));

			}
		}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Portion> idMap){
		String sql = "SELECT p1.portion_id as p1, p2.portion_id as p2, count(*) AS peso "
				+ "FROM  `portion` p1, `portion` p2 "
				+ "WHERE p1.portion_id<>p2.portion_id AND p1.food_code=p2.food_code "
				+ "GROUP BY p1.portion_display_name, p2.portion_display_name";
		
	List<Adiacenza> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(idMap.containsKey(res.getInt("p1")) && idMap.containsKey(res.getInt("p2"))  ) {
					
					Portion p1 = idMap.get(res.getInt("p1"));
					Portion p2 = idMap.get(res.getInt("p2"));
					Adiacenza a = new Adiacenza(p1, p2, res.getInt("peso"));
					list.add(a);

			}
		}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
