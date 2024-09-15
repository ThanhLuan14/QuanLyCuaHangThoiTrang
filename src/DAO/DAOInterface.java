/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.ArrayList;

public interface DAOInterface<T> {
	public T layDSSP1(String t);
        public ArrayList<T> selectAll();
        public int insert(T t);
}

