/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.repositories;

import fei.parkingservice.config.MyBatisUtil;
import fei.parkingservice.model.pojos.Espacio;
import fei.parkingservice.model.pojos.Movimiento;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import java.util.Map;

/**
 *
 * @author Jorge
 */
public class ParkingRepository {
    
    public List<Espacio> getEspaciosLibres() {
        SqlSession conn = null;
        List<Espacio> espacios = null;
        
        try {
            conn = MyBatisUtil.getSession();
            espacios = conn.selectList("ParkingMapper.getEspaciosLibres");
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        
        return espacios;
    }
    
    public Espacio getEspacioById(Integer idEspacio) {
        SqlSession conn = null;
        Espacio espacio = null;
        try {
            conn = MyBatisUtil.getSession();
            espacio = conn.selectOne("ParkingMapper.getEspacioById", idEspacio);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
        return espacio;
    }
    
    public void ocuparEspacio(Integer idEspacio) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            conn.update("ParkingMapper.ocuparEspacio", idEspacio);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public void liberarEspacio(Integer idEspacio) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            conn.update("ParkingMapper.liberarEspacio", idEspacio);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public boolean tieneEntradaActiva(Integer idVehiculo) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            Integer count = conn.selectOne("ParkingMapper.tieneEntradaActiva", idVehiculo);
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public int countVehiculosActivosByListaIds(List<Integer> idsVehiculos) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            Map<String, Object> params = new HashMap<>();
            params.put("idsVehiculos", idsVehiculos);
            Integer count = conn.selectOne("ParkingMapper.countVehiculosActivosByListaIds", params);
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public Integer insertMovimiento(Map<String, Object> params) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            conn.insert("ParkingMapper.insertMovimiento", params);
            conn.commit();
            
            Object id = params.get("idMovimiento");
            if (id instanceof Number) {
                return ((Number) id).intValue();
            }
            return null;
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public Movimiento getMovimientoActivo(Integer idVehiculo) {
        SqlSession conn = null;
        Movimiento movimiento = null;
        try {
            conn = MyBatisUtil.getSession();
            movimiento = conn.selectOne("ParkingMapper.getMovimientoActivo", idVehiculo);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
        return movimiento;
    }

    public boolean updateMovimientoSalida(Map<String, Object> params) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            int rows = conn.update("ParkingMapper.updateMovimientoSalida", params);
            conn.commit();
            return rows > 0;
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) conn.close();
        }
    }
}