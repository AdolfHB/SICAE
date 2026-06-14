/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.repositories;

import fei.vehicleservice.config.MyBatisUtil;
import fei.vehicleservice.model.pojos.vehiculo;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Ainz Oal Gown
 */
public class vehiculoRepository {
    
    public List<vehiculo> buscarPorUsuario(Integer idUsuario){
        SqlSession conn = null;
        
        try{
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("idUsuario", idUsuario);
            List<vehiculo> vehiculos = conn.selectList("vehiculo.buscarPorUsuario", param);
            return vehiculos;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            if(conn != null){
                conn.close();
            }
        }
    }
    
    public int existePlaca(String placa){
        SqlSession conn = null;
        try{
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("placa", placa);
            
            return conn.selectOne("vehiculo.existePlaca",param);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        } finally{
            if(conn != null){
                conn.close();
            }
        }
    }
    
    public int placaRepetida(String placa, Integer idVehiculo){
        SqlSession conn = null;
        try{
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("placa", placa);
            param.put("idVehiculo", idVehiculo);
            
            return conn.selectOne("vehiculo.placaRepetida",param);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        } finally{
            if(conn != null){
                conn.close();
            }
        }
    }
    
    public int vehiculosActivos(Integer idUsuario){
        SqlSession conn = null;
        try{
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("idUsuario", idUsuario);
            
            return conn.selectOne("vehiculo.vehiculosActivos",param);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        } finally{
            if(conn != null){
                conn.close();
            }
        }
    }
    
    public int vehiculoDeUsuario(Integer idVehiculo, Integer idUsuario) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("idVehiculo", idVehiculo);
            param.put("idUsuario", idUsuario);

            return conn.selectOne("vehiculo.vehiculoDeUsuario", param);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public boolean registrar(vehiculo vh) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("idUsuario", vh.getIdUsuario());
            param.put("claveVehiculo", vh.getClaveVehiculo());
            param.put("idModelo", vh.getIdModelo());
            param.put("placa", vh.getPlaca());
            param.put("color", vh.getColor());
            param.put("anio", vh.getAnio());
            param.put("descripcion", vh.getDescripcion());
            conn.insert("vehiculo.registrarV", param);
            conn.commit();
            return true;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean editar(Integer idVehiculo, vehiculo vh) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();

            HashMap<String, Object> param = new HashMap<>();
            param.put("idVehiculo", idVehiculo);
            param.put("idUsuario", vh.getIdUsuario());
            param.put("idModelo", vh.getIdModelo());
            param.put("placa", vh.getPlaca());
            param.put("color", vh.getColor());
            param.put("anio", vh.getAnio());
            param.put("descripcion", vh.getDescripcion());
            int filas = conn.update("vehiculo.editarV", param);
            conn.commit();
            return filas > 0;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean cambiarEstatus(Integer idVehiculo, Integer idUsuario, Integer estatus) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            HashMap<String, Object> param = new HashMap<>();
            param.put("idVehiculo", idVehiculo);
            param.put("idUsuario", idUsuario);
            param.put("estatus", estatus);
            int filas = conn.update("vehiculo.cambiarEstatus", param);
            conn.commit();
            return filas > 0;
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
