/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.LoaiHD;
import UTILS.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class LoaiHDDAO extends HRDAO<LoaiHD, String>{
    String INSERT_SQL="INSERT INTO Loai_Hop_dong (MaLoaiHd, ThoiHan, TenHopDong, MoTa) VALUES (?, ?, ?, ?);";
    String UPDATE_SQL="UPDATE Loai_Hop_Dong SET TenHopDong = ?, ThoiHan = ?, MoTa = ? WHERE MaLoaiHd = ?";
    String DELETE_SQL = "DELETE FROM Loai_Hop_Dong WHERE MaLoaiHd = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Loai_Hop_Dong";
    String SELECT_BY_ID_SQL = "SELECT * FROM Loai_Hop_Dong WHERE MaLoaiHd = ?";

    @Override
    public void insert(LoaiHD entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getMaLoaiHD(),entity.getTenLoaiHD(),entity.getMoTa(),entity.getMaTruongPhong());
        } catch (SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(LoaiHD entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getTenLoaiHD(),entity.getMoTa(),entity.getMaTruongPhong(),entity.getMaLoaiHD());
        } catch (SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public LoaiHD selectById(String id) {
        List<LoaiHD> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }

    @Override
    public List<LoaiHD> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiHD> selectBySql(String sql, Object... args) {
        List<LoaiHD> ds = new ArrayList<LoaiHD>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                LoaiHD a = new LoaiHD();
                a.setMaLoaiHD(rs.getNString("MaLoaiHD"));
                a.setTenLoaiHD(rs.getNString("TenLoaiHD"));
                a.setMoTa(rs.getNString("MoTa"));
                a.setMaTruongPhong(rs.getNString("MaTruongPhong"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
}
